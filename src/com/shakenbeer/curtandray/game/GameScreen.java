package com.shakenbeer.curtandray.game;

import java.util.List;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Input.TouchEvent;
import com.badlogic.androidgames.framework.Screen;

public class GameScreen extends Screen {

    enum GameState {
        Ready, Running, Paused, GameOver
    }

    GameState state = GameState.Ready;
    GameController controller;

    public GameScreen(Game game) {
        super(game);
        controller = new GameController();
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
                Assets.INSTANCE.getSoundClick().play(1);
            }
            state = GameState.Running;
            controller.startRay();
        }
    }

    private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
        controller.update(touchEvents, deltaTime);

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
        graphics.drawPixmap(Assets.INSTANCE.getScreenLevel(), 184, 450);

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
        graphics.drawPixmap(Assets.INSTANCE.getButtonPause(), 0, 0);
        graphics.drawPixmap(Assets.INSTANCE.getButtonArrowLeft(), 30, 1070);
        graphics.drawPixmap(Assets.INSTANCE.getButtonArrowRight(), 180, 1070);
        graphics.drawPixmap(Assets.INSTANCE.getButtonStart(), 478, 1070);

        int len = controller.mines.size();
        for (int i = 0; i < len; i++) {
            StaticObject mo = controller.mines.get(i);
            graphics.drawPixmap(mo.pixmap, (int) mo.translationX(), (int) mo.translationY());
        }

        len = controller.chars.size();
        for (int i = 0; i < len; i++) {
            MovingObject mo = controller.chars.get(i);
            graphics.drawPixmap(mo.pixmap, (int) mo.translationX(), (int) mo.translationY(), (int) mo.posX,
                    (int) mo.posY, mo.angle);
        }

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

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
