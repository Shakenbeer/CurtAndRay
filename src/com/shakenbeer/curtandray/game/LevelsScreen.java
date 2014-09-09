package com.shakenbeer.curtandray.game;

import java.util.List;

import android.graphics.Rect;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Input.TouchEvent;
import com.badlogic.androidgames.framework.Pixmap;
import com.badlogic.androidgames.framework.Screen;

public class LevelsScreen extends Screen {

    private static final int LEVELS_PER_PAGE = 28;
    private static final int PAGE_COUNT = 2;
    Pixmap arrowRight = Assets.INSTANCE.getButtonArrowRight();
    Pixmap arrowLeft = Assets.INSTANCE.getButtonArrowLeft();
    int page;

    public LevelsScreen(Game game, int page) {
        super(game);
        this.page = page;
    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();
        int len = touchEvents.size();

        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {
                if (inBounds(event, 54, 40, 656, 980)) {
                    int selected = (event.y - 40) / 140 * 4 + (event.x - 54) / 164 + 1 + LEVELS_PER_PAGE * (page - 1);
                    if (Settings.soundEnabled) {
                        Assets.INSTANCE.getSoundClick().play(1);
                    }
                    if (selected <= Settings.currentLevel) {
                        game.setScreen(new GameScreen(game, selected));
                    }
                }
                if (page < PAGE_COUNT
                        && inBounds(event, 768 - arrowRight.getWidth(), 1184 - arrowRight.getHeight(),
                                arrowRight.getWidth(), arrowRight.getHeight())) {
                    if (Settings.soundEnabled) {
                        Assets.INSTANCE.getSoundClick().play(1);
                    }
                    game.setScreen(new LevelsScreen(game, page + 1));
                }
                if (page > 1
                        && inBounds(event, 0, 1184 - arrowRight.getHeight(), arrowRight.getWidth(),
                                arrowRight.getHeight())) {
                    if (Settings.soundEnabled) {
                        Assets.INSTANCE.getSoundClick().play(1);
                    }
                    game.setScreen(new LevelsScreen(game, page - 1));
                }
            }

        }

    }

    @Override
    public void present(float deltaTime) {
        Graphics graphics = game.getGraphics();

        graphics.drawPixmap(Assets.INSTANCE.getBackground(), 0, 0);

        if (page < PAGE_COUNT) {
            graphics.drawPixmap(arrowRight, 768 - arrowRight.getWidth(), 1184 - arrowRight.getHeight());
        }
        if (page > 1) {
            graphics.drawPixmap(arrowLeft, 0, 1184 - arrowRight.getHeight());
        }

        for (int i = 0; i < LEVELS_PER_PAGE; i++) {
            if (i + 1 + LEVELS_PER_PAGE * (page - 1) <= Settings.currentLevel) {
                graphics.drawPixmap(Assets.INSTANCE.getLevelNum(), i % 4 * 164 + 54, i / 4 * 140 + 40);
            } else {
                graphics.drawPixmap(Assets.INSTANCE.getLevelNumClosed(), i % 4 * 164 + 54, i / 4 * 140 + 40);
            }
        }

        for (int i = 0; i < LEVELS_PER_PAGE; i++) {
            drawText(graphics, String.valueOf(i + LEVELS_PER_PAGE * (page - 1) + 1), i % 4 * 164 + 54, i / 4 * 140 + 50);
        }

    }

    public void drawText(Graphics g, String line, int x, int y) {
        int len = line.length();
        for (int i = 0; i < len; i++) {
            char character = line.charAt(i);
            int n = character - '0';
            Rect src = new Rect(n * 82, 0, n * 82 + 82, 130);
            Rect dst = new Rect(x + 10, y + 10, x + 62, y + 110);
            g.drawPixmap(Assets.INSTANCE.getDigits(), src, dst);
            x += 82;
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    private boolean inBounds(TouchEvent event, int x, int y, int width, int height) {
        if (event.x > x && event.x < x + width - 1 && event.y > y && event.y < y + height - 1)
            return true;
        else
            return false;
    }

}
