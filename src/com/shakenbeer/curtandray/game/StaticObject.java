package com.shakenbeer.curtandray.game;

import com.badlogic.androidgames.framework.Pixmap;

public class StaticObject {
    int posX;
    int posY;
    ImageInfo imageInfo;
    Pixmap pixmap;

    public StaticObject(int posX, int posY, ImageInfo imageInfo) {
        this.posX = posX;
        this.posY = posY;
        this.imageInfo = imageInfo;
    }

    float translationX() {
        return posX - imageInfo.centerX;
    }

    float translationY() {
        return posY - imageInfo.centerY;
    }

}
