package com.shakenbeer.curtandray.game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Input.TouchEvent;

public class GameController {

    private static final int HIDED_OPACITY = 90;
    private static final int START_Y = 1070;
    private static final int START_X = 478;
    private static final int ARROW_RIGHT_Y = 1070;
    private static final int ARROW_RIGHT_X = 180;
    private static final int ARROW_LEFT_Y = 1070;
    private static final int ARROW_LEFT_X = 30;
    private static final int NEXT_LEVEL_Y = 0;
    private static final int NEXT_LEVEL_X = 34;
    private static final int PLAY_BOUND_BOTTOM = 1050;
    private static final int PLAY_BOUND_TOP = 100;
    private static final int FLAG_PIVOT_Y = 72;
    private static final int FLAG_PIVOT_X = 9;
    private static final int MINE_RADIUS = 32;
    private static final int MINE_PIVOT_Y = 32;
    private static final int MINE_PIVOT_X = 32;
    private static final int TARGET_RADIUS = 100;
    private static final int DEFAULT_CHAR_SPEED = 120000;
    private static final int RAY_INIT_Y = 66;
    private static final int RAY_INIT_X = 384;
    private static final int CURT_INIT_Y = 1115;
    private static final int CURT_INIT_X = 384;
    private static final int CHAR_RADIUS = 32;
    private static final int CHAR_PIVOT_Y = 66;
    private static final int CHAR_PIVOT_X = 55;

    enum GameStage {
        Ray, RayHide, BuildPath, Curt, LevelStart, LevelPaused, LevelFailed
    }

    GameStage stage = GameStage.LevelStart;
    GameStage beforePause;

    Game game;
    int level;

    final InterfaceObject nextLevel;
    final InterfaceObject arrowLeft;
    final InterfaceObject arrowRight;
    final InterfaceObject start;
    final InterfaceObject pause;

    final GameObject curt;
    final GameObject ray;
    final List<GameObject> chars;
    final List<GameObject> mines;
    final List<GameObject> flags;
    final List<GameObject> removed;
    Queue<int[]> minePos;
    int[] rayTarget;
    int[] curtTarget;

    public GameController(Game game, int level) {
        this.game = game;
        this.level = level;

        nextLevel = new InterfaceObject(NEXT_LEVEL_X, NEXT_LEVEL_Y, Assets.INSTANCE.getNextLevel());
        arrowLeft = new InterfaceObject(ARROW_LEFT_X, ARROW_LEFT_Y, Assets.INSTANCE.getButtonArrowLeft());
        arrowRight = new InterfaceObject(ARROW_RIGHT_X, ARROW_RIGHT_Y, Assets.INSTANCE.getButtonArrowRight());
        start = new InterfaceObject(START_X, START_Y, Assets.INSTANCE.getButtonStart());
        pause = new InterfaceObject(0, 0, Assets.INSTANCE.getButtonPause());

        curt = new GameObject(CHAR_PIVOT_X, CHAR_PIVOT_Y, CHAR_RADIUS, Assets.INSTANCE.getCurt());

        ray = new GameObject(CHAR_PIVOT_X, CHAR_PIVOT_Y, CHAR_RADIUS, Assets.INSTANCE.getRay());

        chars = new ArrayList<>();
        minePos = new LinkedList<int[]>();
        mines = new LinkedList<GameObject>();
        flags = new LinkedList<GameObject>();
        removed = new LinkedList<GameObject>();

        initLevel();
    }

    void initLevel() {
        curt.posX = CURT_INIT_X;
        curt.posY = CURT_INIT_Y;
        curt.angle = 0;

        ray.posX = RAY_INIT_X;
        ray.posY = RAY_INIT_Y;
        ray.angle = 180;

        chars.add(curt);
        chars.add(ray);

        minePos.clear();
        mines.clear();
        flags.clear();
        removed.clear();

        curtTarget = null;
        rayTarget = null;

        loadMines();
    }
    
    private void loadMines() {

        String levelString = Assets.INSTANCE.getLevels().get(level - 1);

        String[] levelArray = levelString.split(",");

        int len = levelArray.length - levelArray.length % 2;

        for (int i = 0; i < len;) {
            int x = Integer.parseInt(levelArray[i++]);
            int y = Integer.parseInt(levelArray[i++]);
            minePos.add(new int[] { x, y });
        }
    }

    public void update(List<TouchEvent> touchEvents, float deltaTime) {
        if (stage == GameStage.LevelStart) {
            if (touchEvents.size() > 0) {
                if (Settings.soundEnabled) {
                    Assets.INSTANCE.getSoundClick().play(1);
                }
                stage = GameStage.Ray;
                ray.velNormSqr = DEFAULT_CHAR_SPEED;
            }
        }
        if (stage != GameStage.LevelStart) {
            int len = touchEvents.size();
            for (int i = 0; i < len; i++) {
                TouchEvent event = touchEvents.get(i);
                if (event.type == TouchEvent.TOUCH_UP) {
                    if (inBounds(event, pause)) {
                        beforePause = stage;
                        stage = GameStage.LevelPaused;
                    }
                }
            }
        }
        if (stage == GameStage.Ray) {
            updateRayStage(deltaTime);
        } else if (stage == GameStage.RayHide) {
            updateRayHideStage(deltaTime);
        } else if (stage == GameStage.BuildPath) {
            updateBuildPathStage(touchEvents, deltaTime);
        } else if (stage == GameStage.Curt) {
            updateCurtStage(deltaTime);
        }
        if (stage == GameStage.LevelPaused) {
            updateStatePaused(touchEvents);
        }

    }

    private void updateStatePaused(List<TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {
                if (event.x > 182 && event.x < 582 - 1 && event.y > 450 && event.y < 600 - 1) {
                    stage = beforePause;
                }

                if (event.x > 182 && event.x < 582 - 1 && event.y > 600 && event.y < 750 - 1) {
                    game.setScreen(new MainMenuScreen(game));
                }
            }
        }

    }

    private void updateRayStage(float deltaTime) {
        float[] rv;

        if (rayTarget != null) {
            rv = directionVector(ray, rayTarget);
        } else {
            rayTarget = minePos.remove();
            rv = directionVector(ray, rayTarget);
            changeDirection(ray, rv);
        }

        if (rv[0] * rv[0] + rv[1] * rv[1] < TARGET_RADIUS) {
            GameObject mine = new GameObject(MINE_PIVOT_X, MINE_PIVOT_Y, MINE_RADIUS, Assets.INSTANCE.getMine());
            mine.posX = rayTarget[0];
            mine.posY = rayTarget[1];
            mines.add(mine);
            ray.posX = rayTarget[0];
            ray.posY = rayTarget[1];
            rayTarget = null;
            if (Settings.soundEnabled) {
                Assets.INSTANCE.getSoundSetupMine().play(1);
            }
            if (minePos.isEmpty()) {
                stage = GameStage.RayHide;
            }
        }
        move(ray, deltaTime);
    }

    private void updateRayHideStage(float deltaTime) {
        float[] rv;
        GameObject mine;

        if (rayTarget != null) {
            rv = directionVector(ray, rayTarget);
        } else {
            mine = mines.get(0);
            rayTarget = new int[] { (int) mine.posX, (int) mine.posY };
            rv = directionVector(ray, rayTarget);
            changeDirection(ray, rv);
        }

        if (rv[0] * rv[0] + rv[1] * rv[1] < TARGET_RADIUS) {
            minePos.add(new int[] { rayTarget[0], rayTarget[1] });
            mine = mines.remove(0);
            mine.opacity = HIDED_OPACITY;
            mines.add(mine);
            ray.posX = rayTarget[0];
            ray.posY = rayTarget[1];
            rayTarget = null;
            if (Settings.soundEnabled) {
                Assets.INSTANCE.getSoundHideMine().play(1);
            }
            if (mines.get(0).opacity == HIDED_OPACITY) {
                ray.velNormSqr = 0;
                chars.remove(ray);
                mines.clear();
                stage = GameStage.BuildPath;
            }
        }
        move(ray, deltaTime);
    }

    private void updateBuildPathStage(List<TouchEvent> touchEvents, float deltaTime) {
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {
                if (event.y > PLAY_BOUND_TOP && event.y < PLAY_BOUND_BOTTOM) {
                    GameObject flag = new GameObject(FLAG_PIVOT_X, FLAG_PIVOT_Y, 0, Assets.INSTANCE.getFlag());
                    flag.posX = event.x;
                    flag.posY = event.y;
                    flags.add(flag);
                    if (Settings.soundEnabled) {
                        Assets.INSTANCE.getSoundSetupFlag().play(1);
                    }
                    removed.clear();
                }
                if (inBounds(event, arrowLeft)) {
                    if (!flags.isEmpty()) {
                        removed.add(flags.remove(flags.size() - 1));
                    }
                    if (Settings.soundEnabled) {
                        Assets.INSTANCE.getSoundClick().play(1);
                    }
                }
                if (inBounds(event, arrowRight)) {
                    if (!removed.isEmpty()) {
                        flags.add(removed.remove(removed.size() - 1));
                    }
                    if (Settings.soundEnabled) {
                        Assets.INSTANCE.getSoundClick().play(1);
                    }
                }
                if (inBounds(event, start)) {
                    if (Settings.soundEnabled) {
                        Assets.INSTANCE.getSoundClick().play(1);
                    }
                    int mCount = minePos.size();
                    for (int j = 0; j < mCount; j++) {
                        int[] pos = minePos.poll();
                        GameObject mine = new GameObject(MINE_PIVOT_X, MINE_PIVOT_Y, MINE_RADIUS,
                                Assets.INSTANCE.getMine());
                        mine.posX = pos[0];
                        mine.posY = pos[1];
                        mines.add(mine);
                    }
                    stage = GameStage.Curt;
                    curt.velNormSqr = DEFAULT_CHAR_SPEED;
                }
            }
        }

    }

    private void updateCurtStage(float deltaTime) {
        float[] rv;

        if (curtTarget != null) {
            rv = directionVector(curt, curtTarget);
        } else {
            if (!flags.isEmpty()) {
                GameObject flag = flags.get(0);
                curtTarget = new int[] { (int) flag.posX, (int) flag.posY };
            } else {
                curtTarget = new int[] { (int) curt.posX, -200 };
            }
            rv = directionVector(curt, curtTarget);
            changeDirection(curt, rv);
        }

        if (rv[0] * rv[0] + rv[1] * rv[1] < TARGET_RADIUS) {
            curt.posX = curtTarget[0];
            curt.posY = curtTarget[1];
            flags.remove(0);
            curtTarget = null;
        }

        if (curt.posY < -curt.pixmap.getHeight()) {
            curt.velNormSqr = 0;
            chars.remove(curt);
            level++;
            stage = GameStage.LevelStart;
            initLevel();
            if (Settings.soundEnabled) {
                Assets.INSTANCE.getSoundWin().play(1);
            }
        }

        move(curt, deltaTime);
        
        if (checkCollisions()) {
            stage = GameStage.LevelStart;
            initLevel();
        }
    }

    private void move(GameObject mo, float deltaTime) {
        mo.posX += mo.velX * deltaTime;
        mo.posY += mo.velY * deltaTime;
    }
    
    private boolean checkCollisions() {
        int len = mines.size();
        for (int i = 0; i < len; i++) {
            GameObject mine = mines.get(i);
            if (collide(curt, mine)) {
                return true;
            }
        }
        return false;
    }

    public boolean collide(GameObject go1, GameObject go2) {
        float distSqr = (go1.posX - go2.posX) * (go1.posX - go2.posX) + (go1.posY - go2.posY) * (go1.posY - go2.posY);
        return (distSqr < (go1.radius + go2.radius) * (go1.radius + go2.radius));
    }

    private boolean inBounds(TouchEvent event, InterfaceObject io) {
        if (event.x > io.x && event.x < io.x + io.pixmap.getWidth() - 1 && event.y > io.y
                && event.y < io.y + io.pixmap.getHeight() - 1)
            return true;
        else
            return false;
    }

    private void changeDirection(GameObject mo, float[] rv) {
        float k = (float) Math.sqrt(mo.velNormSqr / (rv[0] * rv[0] + rv[1] * rv[1]));
        mo.velX = rv[0] * k;
        mo.velY = rv[1] * k;
        mo.angle = (float) Math.toDegrees(Math.atan2(mo.velY, mo.velX) + Math.PI / 2);
    }

    private float[] directionVector(GameObject mo, int[] target) {
        return new float[] { target[0] - mo.posX, target[1] - mo.posY };
    }
}
