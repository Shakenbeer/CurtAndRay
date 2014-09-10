package com.shakenbeer.curtandray.game;

import java.util.List;

import android.graphics.Color;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Input.TouchEvent;
import com.badlogic.androidgames.framework.Screen;

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
                if (soundOptionTouched(event)) {
                    Settings.soundEnabled = !Settings.soundEnabled;
                    if (Settings.soundEnabled) {
                        Assets.INSTANCE.getSoundClick().play(1);
                    }
                    return;
                }
                if (playTouched(event)) {
                    game.setScreen(new LevelsScreen(game, 1));
                    if (Settings.soundEnabled) {
                        Assets.INSTANCE.getSoundClick().play(1);
                    }
                    return;
                }
                if (howToTouched(event)) {
                    game.setScreen(new HowToScreen1(game));
                    if (Settings.soundEnabled) {
                        Assets.INSTANCE.getSoundClick().play(1);
                    }
                    return;
                }
                if (modeOptionTouched(event)) {
                    Settings.hardMode = !Settings.hardMode;
                    if (Settings.soundEnabled) {
                        Assets.INSTANCE.getSoundClick().play(1);
                    }
                    return;
                }
                if (speedTouched(event)) {
                    Settings.gameSpeed++;
                    if (Settings.gameSpeed > 5) {
                        Settings.gameSpeed = 1;
                    }
                    if (Settings.soundEnabled) {
                        Assets.INSTANCE.getSoundClick().play(1);
                    }
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
        if (Settings.hardMode) {
            graphics.drawPixmap(Assets.INSTANCE.getButtonHardModeOn(), 628, 1044);
        } else {
            graphics.drawPixmap(Assets.INSTANCE.getButtonHardModeOff(), 628, 1044);
        }
        graphics.drawPixmap(Assets.INSTANCE.getButtonSpeed(), 488, 1044);
        graphics.drawText(String.valueOf(Settings.gameSpeed), 545, 1175, 65, Color.WHITE);
        
        graphics.drawPixmap(Assets.INSTANCE.getPresent(), 164, 1090);
        graphics.drawText(String.valueOf(Settings.presentsCollected), 260, 1170, 70, Color.parseColor("#00779E"));

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
    
    private boolean soundOptionTouched(TouchEvent event) {
        return inBounds(event, 0, 1044, 140, 140);
    }
    
    private boolean playTouched(TouchEvent event) {
        return inBounds(event, 134, 600, 500, 200);
    }
    
    private boolean howToTouched(TouchEvent event) {
        return inBounds(event, 184, 850, 400, 150);
    }
    
    private boolean modeOptionTouched(TouchEvent event) {
        return inBounds(event, 628, 1044, 140, 140);
    }
    
    private boolean speedTouched(TouchEvent event) {
        return inBounds(event, 488, 1044, 140, 140);
    }

    private boolean inBounds(TouchEvent event, int x, int y, int width, int height) {
        if (event.x > x && event.x < x + width - 1 && event.y > y && event.y < y + height - 1)
            return true;
        else
            return false;
    }

}
