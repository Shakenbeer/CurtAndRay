package com.shakenbeer.curtandray.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.badlogic.androidgames.framework.Input.TouchEvent;

public class GameController {
    static final float TICK_INITIAL = 0.5f;

    enum GameStage {
        Ray, RayHide, BuildPath, Curt
    }

    GameStage stage = GameStage.Ray;
    int level = 1;
    final MovingObject curt;
    final MovingObject ray;
    final List<MovingObject> chars;
    final List<StaticObject> mines;
    final List<StaticObject> flags;
    Queue<int[]> minePos;
    int[] rayTarget;
    ImageInfo mineImage = new ImageInfo(32, 32, 64, 64, 32);

    public GameController() {
        ImageInfo info = new ImageInfo(55, 66, 110, 122, 32);

        curt = new MovingObject(384, 1115, 0, 0, 0, info);
        curt.imageInfo = info;
        curt.pixmap = Assets.INSTANCE.getCurt();

        ray = new MovingObject(384, 66, 0, 0, 180, info);
        ray.imageInfo = info;
        ray.pixmap = Assets.INSTANCE.getRay();

        chars = new ArrayList<>();
        chars.add(curt);
        chars.add(ray);

        mines = new LinkedList<StaticObject>();
        generateMines();
        flags = new ArrayList<StaticObject>();
    }

    private void generateMines() {
        minePos = new LinkedList<int[]>();
        minePos.add(new int[] { 350, 550 });
        minePos.add(new int[] { 100, 250 });
        minePos.add(new int[] { 700, 1000 });

    }

    public void update(List<TouchEvent> touchEvents, float deltaTime) {
        if (stage == GameStage.Ray) {
            float[] rv;

            if (rayTarget != null) {
                rv = directionVector();
            } else {
                rayTarget = minePos.remove();
                rv = directionVector();
                changeDirection(ray, rv);
            }

            if (rv[0] * rv[0] + rv[1] * rv[1] < 25) {
                StaticObject mine = new StaticObject(rayTarget[0], rayTarget[1], mineImage);
                mine.pixmap = Assets.INSTANCE.getMine();
                mines.add(mine);
                rayTarget = null;
                if (Settings.soundEnabled) {
                    Assets.INSTANCE.getSoundSetupMine().play(1);
                }
                if (minePos.isEmpty()) {
                    stage = GameStage.RayHide;
                }
            }
            ray.move(deltaTime);
            
        } else if (stage == GameStage.RayHide) {
            float[] rv;
            StaticObject mine;

            if (rayTarget != null) {
                rv = directionVector();
            } else {
                mine = mines.get(0);
                rayTarget = new int[] { mine.posX, mine.posY };
                rv = directionVector();
                changeDirection(ray, rv);
            }

            if (rv[0] * rv[0] + rv[1] * rv[1] < 25) {
                minePos.add(new int[] { rayTarget[0], rayTarget[1] });
                mines.remove(0);
                rayTarget = null;
                if (Settings.soundEnabled) {
                    Assets.INSTANCE.getSoundHideMine().play(1);
                }
                if (mines.isEmpty()) {
                    chars.remove(ray);
                    stage = GameStage.BuildPath;
                }
            }
            ray.move(deltaTime);

        }

    }

    private void changeDirection(MovingObject mo, float[] rv) {
        float k = (float) Math.sqrt(mo.absVel / (rv[0] * rv[0] + rv[1] * rv[1]));
        mo.velX = rv[0] * k;
        mo.velY = rv[1] * k;
        mo.angle = (float) Math.toDegrees(Math.atan2(mo.velY, mo.velX) + Math.PI / 2);
    }

    private float[] directionVector() {
        return new float[] { rayTarget[0] - ray.posX, rayTarget[1] - ray.posY };
    }

    void startRay() {
        ray.absVel = 100000;
    }
}
