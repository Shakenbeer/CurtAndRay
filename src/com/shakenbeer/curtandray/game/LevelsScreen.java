package com.shakenbeer.curtandray.game;

import java.util.List;

import android.graphics.Rect;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Input.TouchEvent;
import com.badlogic.androidgames.framework.Screen;

public class LevelsScreen extends Screen {


    public LevelsScreen(Game game) {
        super(game);
    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();
        int len = touchEvents.size();
        
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP && inBounds(event, 54, 40, 656, 980)) {
                int selected = (event.y - 40) / 140 * 4 + (event.x - 54) / 164 + 1;
                if (Settings.soundEnabled) {
                    Assets.INSTANCE.getSoundClick().play(1);
                }
                if (selected <= Settings.level) {
                    game.setScreen(new GameScreen(game, selected));
                }
            };
            
        }

    }

    @Override
    public void present(float deltaTime) {
        Graphics graphics = game.getGraphics();
        
        graphics.drawPixmap(Assets.INSTANCE.getBackground(), 0, 0);
        
        for (int i = 0; i < Settings.level; i++) {
            graphics.drawPixmap(Assets.INSTANCE.getLevelNum(), i % 4 * 164 + 54, i / 4 * 140 + 40);
        }
        for (int i = Settings.level; i < 28; i++) {
            graphics.drawPixmap(Assets.INSTANCE.getLevelNumClosed(), i % 4 * 164 + 54, i / 4 * 140 + 40);
        }
        
        for (int i = 0; i < 28; i++) {
            drawText(graphics, String.valueOf(i+1), i % 4 * 164 + 54, i / 4 * 140 + 50);
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
