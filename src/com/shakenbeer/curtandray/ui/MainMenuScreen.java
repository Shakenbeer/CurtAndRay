package com.shakenbeer.curtandray.ui;

import java.util.List;

import android.text.method.Touch;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Screen;
import com.badlogic.androidgames.framework.Input.TouchEvent;
import com.shakenbeer.curtandray.assets.Assets;
import com.shakenbeer.curtandray.assets.Settings;

public class MainMenuScreen extends Screen {

    public MainMenuScreen(Game game) {
        super(game);
    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();
        int len = touchEvents.size();

        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {
                if (inBounds(event, 0, game.getGraphics().getHeight() - 120, 108, 120)) {
                    Settings.soundEnabled = !Settings.soundEnabled;
                    if (Settings.soundEnabled) {
                        Assets.INSTANCE.getSoundClick().play(1);
                    }
                    return;
                }
                if (inBounds(event, 134, 600, 500, 200)) {
                    game.setScreen(new GameScreen(game));
                    if (Settings.soundEnabled) {
                        Assets.INSTANCE.getSoundClick().play(1);
                    }
                    return;
                }
                if (inBounds(event, 184, 850, 400, 150)) {
                    game.setScreen(new HowToScreen1(game));
                    if (Settings.soundEnabled) {
                        Assets.INSTANCE.getSoundClick().play(1);
                    }
                    return;
                }
            }

        }

    }

    @Override
    public void present(float deltaTime) {
        Graphics graphics = game.getGraphics();

        graphics.drawPixmap(Assets.INSTANCE.getBackground(), 0, 0);
        graphics.drawPixmap(Assets.INSTANCE.getLogo(), 34, 30);
        graphics.drawPixmap(Assets.INSTANCE.getButtonPlay(), 134, 600);
        graphics.drawPixmap(Assets.INSTANCE.getButtonHowTo(), 184, 850);
        if (Settings.soundEnabled) {
            graphics.drawPixmap(Assets.INSTANCE.getButtonSoundOn(), 0, 1044);
        } else {
            graphics.drawPixmap(Assets.INSTANCE.getButtonSoundOff(), 0, 1044);
        }

    }

    @Override
    public void pause() {
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

    private boolean inBounds(TouchEvent event, int x, int y, int width, int height) {
        if (event.x > x && event.x < x + width - 1 && event.y > y && event.y < y + height - 1)
            return true;
        else
            return false;
    }

}
