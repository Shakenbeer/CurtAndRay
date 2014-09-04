package com.shakenbeer.curtandray.game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.badlogic.androidgames.framework.Input.TouchEvent;

public class GameController {
    static final float TICK_INITIAL = 0.5f;

    enum GameStage {
        Ray, RayHide, BuildPath, Curt, LevelStart, LevelPaused
    }

    GameStage stage = GameStage.LevelStart;
    int level;
    final MovingObject curt;
    final MovingObject ray;
    final List<MovingObject> chars;
    final List<StaticObject> mines;
    final List<StaticObject> flags;
    final List<StaticObject> removed;
    Queue<int[]> minePos;
    int[] rayTarget;
    int[] curtTarget;
    ImageInfo mineImage = new ImageInfo(32, 32, 64, 64, 32);
    ImageInfo flagImage = new ImageInfo(9, 72, 62, 80, 32);

    public GameController(int level) {
        this.level = level;
        
        ImageInfo info = new ImageInfo(55, 66, 110, 122, 32);

        curt = new MovingObject(384, 1115, 0, 0, 0, info);
        curt.imageInfo = info;
        curt.pixmap = Assets.INSTANCE.getCurt();

        ray = new MovingObject(384, 66, 0, 0, 180, info);
        ray.imageInfo = info;
        ray.pixmap = Assets.INSTANCE.getRay();

        chars = new ArrayList<>();

        minePos = new LinkedList<int[]>();
        mines = new LinkedList<StaticObject>();
        flags = new ArrayList<StaticObject>();
        removed = new ArrayList<StaticObject>();
    }

    void initLevel() {
        curt.posX = 384;
        curt.posY = 1115;

        ray.posX = 384;
        ray.posY = 66;

        chars.add(curt);
        chars.add(ray);

        minePos.clear();
        mines.clear();
        flags.clear();
        removed.clear();

        generateMines();
    }

    public void update(List<TouchEvent> touchEvents, float deltaTime) {
        if (stage == GameStage.LevelStart) {
            if (touchEvents.size() > 0) {
                initLevel();
                stage = GameStage.Ray;
                ray.velNormSquare = 90000;
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

    }

    private void generateMines() {
        minePos.add(new int[] { 100, 200 });
        minePos.add(new int[] { 100, 800 });
        minePos.add(new int[] { 524, 376 });
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

        if (rv[0] * rv[0] + rv[1] * rv[1] < 25) {
            StaticObject mine = new StaticObject(rayTarget[0], rayTarget[1], mineImage);
            mine.pixmap = Assets.INSTANCE.getMine();
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
        ray.move(deltaTime);
    }

    private void updateRayHideStage(float deltaTime) {
        float[] rv;
        StaticObject mine;

        if (rayTarget != null) {
            rv = directionVector(ray, rayTarget);
        } else {
            mine = mines.get(0);
            rayTarget = new int[] { mine.posX, mine.posY };
            rv = directionVector(ray, rayTarget);
            changeDirection(ray, rv);
        }

        if (rv[0] * rv[0] + rv[1] * rv[1] < 25) {
            minePos.add(new int[] { rayTarget[0], rayTarget[1] });
            mines.remove(0);
            ray.posX = rayTarget[0];
            ray.posY = rayTarget[1];
            rayTarget = null;
            if (Settings.soundEnabled) {
                Assets.INSTANCE.getSoundHideMine().play(1);
            }
            if (mines.isEmpty()) {
                ray.velNormSquare = 0;
                chars.remove(ray);
                stage = GameStage.BuildPath;
            }
        }
        ray.move(deltaTime);
    }

    private void updateBuildPathStage(List<TouchEvent> touchEvents, float deltaTime) {
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {
                if (event.y > 100 && event.y < 1050) {
                    StaticObject flag = new StaticObject(event.x, event.y, flagImage);
                    flag.pixmap = Assets.INSTANCE.getFlag();
                    flags.add(flag);
                    if (Settings.soundEnabled) {
                        Assets.INSTANCE.getSoundSetupFlag().play(1);
                    }
                    removed.clear();
                }
                if (inBounds(event, 30, 1070, 120, 100)) {
                    if (!flags.isEmpty()) {
                        removed.add(flags.remove(flags.size() - 1));
                    }
                    if (Settings.soundEnabled) {
                        Assets.INSTANCE.getSoundClick().play(1);
                    }
                }
                if (inBounds(event, 180, 1070, 120, 100)) {
                    if (!removed.isEmpty()) {
                        flags.add(removed.remove(removed.size() - 1));
                    }
                    if (Settings.soundEnabled) {
                        Assets.INSTANCE.getSoundClick().play(1);
                    }
                }
                if (inBounds(event, 478, 1070, 250, 100)) {
                    curt.velNormSquare = 90000;
                    stage = GameStage.Curt;
                    int mCount = minePos.size();
                    for (int j = 0; j < mCount; j++) {
                        int[] pos = minePos.poll();
                        StaticObject mine = new StaticObject(pos[0], pos[1], mineImage);
                        mine.pixmap = Assets.INSTANCE.getMine();
                        mines.add(mine);
                    }
                    if (Settings.soundEnabled) {
                        Assets.INSTANCE.getSoundClick().play(1);
                    }
                }
            }
        }

    }

    private void updateCurtStage(float deltaTime) {
        float[] rv;

        checkCollisions();
        if (curtTarget != null) {
            rv = directionVector(curt, curtTarget);
        } else {
            if (!flags.isEmpty()) {
                StaticObject flag = flags.get(0);
                curtTarget = new int[] { flag.posX, flag.posY };
            } else {
                curtTarget = new int[] { (int) curt.posX, -200 };
            }
            rv = directionVector(curt, curtTarget);
            changeDirection(curt, rv);
        }

        if (rv[0] * rv[0] + rv[1] * rv[1] < 25) {
            curt.posX = curtTarget[0];
            curt.posY = curtTarget[1];
            flags.remove(0);
            curtTarget = null;
        }

        if (curt.posY < 61) {
            curt.velNormSquare = 0;
            chars.remove(curt);
            level++;
            stage = GameStage.LevelStart;
            if (Settings.soundEnabled) {
                Assets.INSTANCE.getSoundWin().play(1);
            }
        }

        curt.move(deltaTime);
    }

    private void checkCollisions() {
        // TODO Auto-generated method stub

    }

    private boolean inBounds(TouchEvent event, int x, int y, int width, int height) {
        if (event.x > x && event.x < x + width - 1 && event.y > y && event.y < y + height - 1)
            return true;
        else
            return false;
    }

    private void changeDirection(MovingObject mo, float[] rv) {
        float k = (float) Math.sqrt(mo.velNormSquare / (rv[0] * rv[0] + rv[1] * rv[1]));
        mo.velX = rv[0] * k;
        mo.velY = rv[1] * k;
        mo.angle = (float) Math.toDegrees(Math.atan2(mo.velY, mo.velX) + Math.PI / 2);
    }

    private float[] directionVector(MovingObject mo, int[] target) {
        return new float[] { target[0] - mo.posX, target[1] - mo.posY };
    }
}
