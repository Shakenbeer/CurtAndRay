package com.shakenbeer.curtandray.game;

import java.util.List;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Input.TouchEvent;
import com.badlogic.androidgames.framework.Screen;

public class HowToScreen1 extends Screen {

    public HowToScreen1(Game game) {
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
                if (event.x > 648 && event.y > 1084) {
                    game.setScreen(new HowToScreen2(game));
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
        graphics.drawPixmap(Assets.INSTANCE.backgrounds[0], 0, 0);
        graphics.drawPixmap(Assets.INSTANCE.getScreenHowTo1(), 34, 0);
        graphics.drawPixmap(Assets.INSTANCE.getButtonArrowRight(), 648, 1084);

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
