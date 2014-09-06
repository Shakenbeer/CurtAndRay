package com.shakenbeer.curtandray.game;

import java.util.List;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Input.TouchEvent;
import com.badlogic.androidgames.framework.Screen;
import com.shakenbeer.curtandray.game.GameController.GameStage;

public class GameScreen extends Screen {

    GameController controller;

    public GameScreen(Game game, int level) {
        super(game);
        controller = new GameController(game, level);
    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();

        controller.update(touchEvents, deltaTime);

    }

    @Override
    public void present(float deltaTime) {
        Graphics graphics = game.getGraphics();
        drawCommon(graphics);
        
        if (controller.stage == GameStage.LevelStart) {
            graphics.drawPixmap(Assets.INSTANCE.getScreenLevel(), 84, 400);
            drawText(graphics, String.valueOf(controller.level), 492, 435);
        }
        if (controller.stage == GameStage.LevelPaused) {
            graphics.drawPixmap(Assets.INSTANCE.getScreenPause(), 182, 450);
        }
    }

    @Override
    public void pause() {
        Settings.level = controller.level;
        Settings.save(game.getFileIO());
        controller.stage = GameStage.LevelPaused;
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    private void drawCommon(Graphics graphics) {

        graphics.drawPixmap(Assets.INSTANCE.getBackground(), 0, 0);
        draw(graphics, controller.nextLevel);
        draw(graphics, controller.arrowLeft);
        draw(graphics, controller.arrowRight);
        draw(graphics, controller.start);
        draw(graphics, controller.pause);

        int len = controller.mines.size();
        for (int i = 0; i < len; i++) {
            GameObject mo = controller.mines.get(i);
            graphics.drawPixmap(mo.pixmap, (int) mo.translationX(), (int) mo.translationY());
        }

        len = controller.chars.size();
        for (int i = 0; i < len; i++) {
            GameObject mo = controller.chars.get(i);
            graphics.drawPixmap(mo.pixmap, (int) mo.translationX(), (int) mo.translationY(), (int) mo.posX,
                    (int) mo.posY, mo.angle);
        }

        len = controller.flags.size();
        for (int i = 0; i < len; i++) {
            GameObject mo = controller.flags.get(i);
            graphics.drawPixmap(mo.pixmap, (int) mo.translationX(), (int) mo.translationY());
        }
    }

    private void draw(Graphics graphics, InterfaceObject io) {
        graphics.drawPixmap(io.pixmap, io.x, io.y);
    }

    private void drawText(Graphics g, String line, int x, int y) {
        int len = line.length();
        for (int i = 0; i < len; i++) {
            char character = line.charAt(i);
            int n = character - '0';
            g.drawPixmap(Assets.INSTANCE.getDigits(), x, y, n * 82, 0, 82, 130);
            x += 82;
        }
    }
}
