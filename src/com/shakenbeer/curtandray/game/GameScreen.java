package com.shakenbeer.curtandray.game;

import java.util.List;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Input.TouchEvent;
import com.badlogic.androidgames.framework.Screen;
import com.shakenbeer.curtandray.game.GameController.GameStage;

public class GameScreen extends Screen {

    enum GameState {
        Ready, Running, Paused, GameOver
    }

    GameState state = GameState.Ready;
    GameController controller;

    public GameScreen(Game game, int level) {
        super(game);
        controller = new GameController(level);
    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();

        if (state == GameState.Ready)
            updateReady(touchEvents);
        if (state == GameState.Running)
            updateRunning(touchEvents, deltaTime);
        if (state == GameState.Paused)
            updatePaused(touchEvents);
        if (state == GameState.GameOver)
            updateGameOver(touchEvents);
    }

    private void updateReady(List<TouchEvent> touchEvents) {
        if (touchEvents.size() > 0) {
            if (Settings.soundEnabled) {
                state = GameState.Running;
                Assets.INSTANCE.getSoundClick().play(1);
            }
            controller.update(touchEvents, 0);
        }
    }

    private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
        controller.update(touchEvents, deltaTime);
        if (controller.stage == GameStage.LevelStart) {
            state = GameState.Ready;
        }
    }

    private void updatePaused(List<TouchEvent> touchEvents) {
        // TODO Auto-generated method stub

    }

    private void updateGameOver(List<TouchEvent> touchEvents) {
        // TODO Auto-generated method stub

    }

    @Override
    public void present(float deltaTime) {
        Graphics graphics = game.getGraphics();
        drawCommon(graphics);
        if (state == GameState.Ready)
            drawReadyUI();
        if (state == GameState.Running)
            drawRunningUI();
        if (state == GameState.Paused)
            drawPausedUI();
        if (state == GameState.GameOver)
            drawGameOverUI();

    }

    private void drawReadyUI() {
        Graphics graphics = game.getGraphics();
        graphics.drawPixmap(Assets.INSTANCE.getScreenLevel(), 84, 400);
        drawText(graphics, String.valueOf(controller.level), 492, 435);

    }
    
    public void drawText(Graphics g, String line, int x, int y) {
        int len = line.length();
        for (int i = 0; i < len; i++) {
            char character = line.charAt(i);            
            int n = character - '0';            
            g.drawPixmap(Assets.INSTANCE.getDigits(), x, y, n * 82, 0, 82, 130);
            x += 82;
        }
    }

    private void drawRunningUI() {
        // TODO Auto-generated method stub

    }

    private void drawPausedUI() {
        // TODO Auto-generated method stub

    }

    private void drawGameOverUI() {
        // TODO Auto-generated method stub

    }

    private void drawCommon(Graphics graphics) {
        
        graphics.drawPixmap(Assets.INSTANCE.getBackground(), 0, 0);
        graphics.drawPixmap(Assets.INSTANCE.getNextLevel(), 34, 0);
//        graphics.drawPixmap(Assets.INSTANCE.getButtonPause(), 0, 0);
        graphics.drawPixmap(Assets.INSTANCE.getButtonArrowLeft(), 30, 1070);
        graphics.drawPixmap(Assets.INSTANCE.getButtonArrowRight(), 180, 1070);
        graphics.drawPixmap(Assets.INSTANCE.getButtonStart(), 478, 1070);

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

    @Override
    public void pause() {
        Settings.level = controller.level;
        Settings.save(game.getFileIO());

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }

}
