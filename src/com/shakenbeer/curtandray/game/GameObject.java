package com.shakenbeer.curtandray.game;

import com.badlogic.androidgames.framework.Pixmap;

public class GameObject {
    final int pivotX;
    final int pivotY;
    final int radius;
    final Pixmap pixmap;
    
    float posX;
    float posY;
    float velX;
    float velY;
    float velNormSqr;
    float angle;

    public GameObject(int pivotX, int pivotY, int radius, Pixmap pixmap) {
		this.pivotX = pivotX;
		this.pivotY = pivotY;
		this.radius = radius;
		this.pixmap = pixmap;
	}

	float translationX() {
        return posX - pivotX;
    }

    float translationY() {
        return posY - pivotY;
    }
}
