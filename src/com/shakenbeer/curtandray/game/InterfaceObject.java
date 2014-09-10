package com.shakenbeer.curtandray.game;

import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Pixmap;
import com.badlogic.androidgames.framework.Input.TouchEvent;

public class InterfaceObject {
    int x;
    int y;
    Pixmap pixmap;

    public InterfaceObject(int x, int y, Pixmap pixmap) {
        this.x = x;
        this.y = y;
        this.pixmap = pixmap;
    }

    public boolean touched(TouchEvent event) {
        if (event.x > x && event.x < x + pixmap.getWidth() - 1 && event.y > y && event.y < y + pixmap.getHeight() - 1)
            return true;
        else
            return false;
    }
    
    public void draw(Graphics graphics) {
        graphics.drawPixmap(pixmap, x, y);
    }

}
