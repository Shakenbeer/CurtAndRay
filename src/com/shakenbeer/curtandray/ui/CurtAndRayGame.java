package com.shakenbeer.curtandray.ui;

import com.badlogic.androidgames.framework.Screen;
import com.badlogic.androidgames.framework.impl.AndroidGame;
import com.shakenbeer.curtandray.assets.LoadingScreen;

public class CurtAndRayGame extends AndroidGame {

    @Override
    public Screen getStartScreen() {
        //asd
        return new LoadingScreen(this);
    }   
}
