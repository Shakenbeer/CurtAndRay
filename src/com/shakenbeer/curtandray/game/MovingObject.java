package com.shakenbeer.curtandray.game;

import com.badlogic.androidgames.framework.Pixmap;

public class MovingObject {
    float posX;
    float posY;
    float velX;
    float velY;
    float angle;
    ImageInfo imageInfo;
    Pixmap pixmap;
    float transX;
    float transY;
    float velNormSquare;

    public MovingObject(float posX, float posY, float velX, float velY, float angle, ImageInfo imageInfo) {
        this.posX = posX;
        this.posY = posY;
        this.velX = velX;
        this.velY = velY;
        this.angle = angle;
        this.imageInfo = imageInfo;
    }

    float translationX() {
        return posX - imageInfo.centerX;
    }

    float translationY() {
        return posY - imageInfo.centerY;
    }
    
    void move(float deltaTime) {        
        posX += velX * deltaTime;
        posY += velY * deltaTime;
    }

}
