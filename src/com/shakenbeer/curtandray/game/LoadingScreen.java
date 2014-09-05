package com.shakenbeer.curtandray.game;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import com.badlogic.androidgames.framework.Audio;
import com.badlogic.androidgames.framework.FileIO;
import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Graphics.PixmapFormat;
import com.badlogic.androidgames.framework.Screen;

public class LoadingScreen extends Screen {

    public LoadingScreen(Game game) {
        super(game);
    }

    @Override
    public void update(float deltaTime) {
        Assets assets = Assets.INSTANCE;

        Graphics g = game.getGraphics();

        assets.setBackground(g.newPixmap("background.png", PixmapFormat.RGB565));

        assets.setButtonArrowLeft(g.newPixmap("button_arrow_left.png", PixmapFormat.ARGB4444));
        assets.setButtonArrowRight(g.newPixmap("button_arrow_right.png", PixmapFormat.ARGB4444));
        assets.setButtonHighscores(g.newPixmap("button_highscores.png", PixmapFormat.ARGB4444));
        assets.setButtonHowTo(g.newPixmap("button_how_to.png", PixmapFormat.ARGB4444));
        assets.setButtonPlay(g.newPixmap("button_play.png", PixmapFormat.ARGB4444));
        assets.setButtonSoundOff(g.newPixmap("button_sound_off.png", PixmapFormat.ARGB4444));
        assets.setButtonSoundOn(g.newPixmap("button_sound_on.png", PixmapFormat.ARGB4444));
        assets.setButtonStart(g.newPixmap("button_start.png", PixmapFormat.ARGB4444));
        assets.setButtonClose(g.newPixmap("button_close.png", PixmapFormat.ARGB4444));
        assets.setButtonPause(g.newPixmap("button_pause.png", PixmapFormat.ARGB4444));

        assets.setCurt(g.newPixmap("curt.png", PixmapFormat.ARGB4444));
        assets.setDigits(g.newPixmap("digits.png", PixmapFormat.ARGB4444));
        assets.setFlag(g.newPixmap("flag.png", PixmapFormat.ARGB4444));
        assets.setLogo(g.newPixmap("logo.png", PixmapFormat.ARGB4444));
        assets.setMine(g.newPixmap("mine.png", PixmapFormat.ARGB4444));
        assets.setNextLevel(g.newPixmap("next_level.png", PixmapFormat.ARGB4444));
        assets.setRay(g.newPixmap("ray.png", PixmapFormat.ARGB4444));

        assets.setScreenHowTo1(g.newPixmap("screen_how_to_1.png", PixmapFormat.ARGB4444));
        assets.setScreenHowTo2(g.newPixmap("screen_how_to_2.png", PixmapFormat.ARGB4444));
        assets.setScreenLevel(g.newPixmap("screen_level.png", PixmapFormat.ARGB4444));
        assets.setScreenPause(g.newPixmap("screen_pause.png", PixmapFormat.ARGB4444));
        
        assets.setLevelNum(g.newPixmap("level_num.png", PixmapFormat.ARGB4444));
        assets.setLevelNumClosed(g.newPixmap("level_num_closed.png", PixmapFormat.ARGB4444));

        Audio a = game.getAudio();

        assets.setSoundClick(a.newSound("click.ogg"));
        assets.setSoundHideMine(a.newSound("hide_mine.ogg"));
        assets.setSoundSetupFlag(a.newSound("setup_flag.ogg"));
        assets.setSoundSetupMine(a.newSound("setup_mine.ogg"));
        assets.setSoundWin(a.newSound("win.ogg"));
        
        FileIO fio = game.getFileIO();
        
        Settings.load(fio);
        
        InputStream in = null;
        try {
            in = fio.readAsset("levels");
            Assets.INSTANCE.setLevels(IOUtils.readLines(in));
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load levels from asset");
        } finally {
            if (in != null) {
                IOUtils.closeQuietly(in);
            }
        }
        
        
        
        
        game.setScreen(new MainMenuScreen(game));
        
        
    }

    @Override
    public void present(float deltaTime) {
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

}
