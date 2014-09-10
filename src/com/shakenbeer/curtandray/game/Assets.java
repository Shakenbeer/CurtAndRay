package com.shakenbeer.curtandray.game;

import java.util.List;

import com.badlogic.androidgames.framework.Pixmap;
import com.badlogic.androidgames.framework.Sound;

public enum Assets {
    INSTANCE;

    private Pixmap buttonArrowLeft;
    private Pixmap buttonArrowRight;
    private Pixmap curt;
    private Pixmap digits;
    private Pixmap flag;
    private Pixmap buttonHighscores;
    private Pixmap buttonHowTo;
    private Pixmap screenHowTo1;
    private Pixmap screenHowTo2;
    private Pixmap screenLevel;
    private Pixmap logo;
    private Pixmap mine;
    private Pixmap nextLevel;
    private Pixmap screenPause;
    private Pixmap buttonPlay;
    private Pixmap ray;
    private Pixmap background;
    private Pixmap buttonSoundOn;
    private Pixmap buttonSoundOff;
    private Pixmap buttonStart;
    private Pixmap buttonClose;
    private Pixmap buttonPause;
    private Pixmap levelNum;
    private Pixmap levelNumClosed;
    private Pixmap levelFailed;
    private Pixmap buttonHardModeOff;
    private Pixmap buttonHardModeOn;
    private Pixmap present;

    private Sound soundClick;
    private Sound soundHideMine;
    private Sound soundSetupFlag;
    private Sound soundSetupMine;
    private Sound soundWin;
    private Sound soundLose;
    private Sound soundWoosh;
    private Sound soundPresent;
    
    private List<String> levels;

    public Pixmap getButtonArrowLeft() {
        return buttonArrowLeft;
    }

    public void setButtonArrowLeft(Pixmap buttonArrowLeft) {
        this.buttonArrowLeft = buttonArrowLeft;
    }

    public Pixmap getButtonArrowRight() {
        return buttonArrowRight;
    }

    public void setButtonArrowRight(Pixmap buttonArrowRight) {
        this.buttonArrowRight = buttonArrowRight;
    }

    public Pixmap getCurt() {
        return curt;
    }

    public void setCurt(Pixmap curt) {
        this.curt = curt;
    }

    public Pixmap getDigits() {
        return digits;
    }

    public void setDigits(Pixmap digits) {
        this.digits = digits;
    }

    public Pixmap getFlag() {
        return flag;
    }

    public void setFlag(Pixmap flag) {
        this.flag = flag;
    }

    public Pixmap getButtonHighscores() {
        return buttonHighscores;
    }

    public void setButtonHighscores(Pixmap buttonHighscores) {
        this.buttonHighscores = buttonHighscores;
    }

    public Pixmap getButtonHowTo() {
        return buttonHowTo;
    }

    public void setButtonHowTo(Pixmap buttonHowTo) {
        this.buttonHowTo = buttonHowTo;
    }

    public Pixmap getScreenHowTo1() {
        return screenHowTo1;
    }

    public void setScreenHowTo1(Pixmap screenHowTo1) {
        this.screenHowTo1 = screenHowTo1;
    }

    public Pixmap getScreenHowTo2() {
        return screenHowTo2;
    }

    public void setScreenHowTo2(Pixmap screenHowTo2) {
        this.screenHowTo2 = screenHowTo2;
    }

    public Pixmap getScreenLevel() {
        return screenLevel;
    }

    public void setScreenLevel(Pixmap screenLevel) {
        this.screenLevel = screenLevel;
    }

    public Pixmap getLogo() {
        return logo;
    }

    public void setLogo(Pixmap logo) {
        this.logo = logo;
    }

    public Pixmap getMine() {
        return mine;
    }

    public void setMine(Pixmap mine) {
        this.mine = mine;
    }

    public Pixmap getNextLevel() {
        return nextLevel;
    }

    public void setNextLevel(Pixmap nextLevel) {
        this.nextLevel = nextLevel;
    }

    public Pixmap getScreenPause() {
        return screenPause;
    }

    public void setScreenPause(Pixmap screenPause) {
        this.screenPause = screenPause;
    }

    public Pixmap getButtonPlay() {
        return buttonPlay;
    }

    public void setButtonPlay(Pixmap buttonPlay) {
        this.buttonPlay = buttonPlay;
    }

    public Pixmap getRay() {
        return ray;
    }

    public void setRay(Pixmap ray) {
        this.ray = ray;
    }

    public Pixmap getBackground() {
        return background;
    }

    public void setBackground(Pixmap background) {
        this.background = background;
    }

    public Pixmap getButtonSoundOn() {
        return buttonSoundOn;
    }

    public void setButtonSoundOn(Pixmap buttonSoundOn) {
        this.buttonSoundOn = buttonSoundOn;
    }

    public Pixmap getButtonSoundOff() {
        return buttonSoundOff;
    }

    public void setButtonSoundOff(Pixmap buttonSoundOff) {
        this.buttonSoundOff = buttonSoundOff;
    }

    public Pixmap getButtonStart() {
        return buttonStart;
    }

    public void setButtonStart(Pixmap buttonStart) {
        this.buttonStart = buttonStart;
    }

    public Pixmap getButtonClose() {
        return buttonClose;
    }

    public void setButtonClose(Pixmap buttonClose) {
        this.buttonClose = buttonClose;
    }

    public Pixmap getButtonPause() {
        return buttonPause;
    }

    public void setButtonPause(Pixmap buttonPause) {
        this.buttonPause = buttonPause;
    }

    public Pixmap getLevelNum() {
        return levelNum;
    }

    public void setLevelNum(Pixmap levelNum) {
        this.levelNum = levelNum;
    }

    public Pixmap getLevelNumClosed() {
        return levelNumClosed;
    }

    public void setLevelNumClosed(Pixmap levelNumClosed) {
        this.levelNumClosed = levelNumClosed;
    }

    public Pixmap getLevelFailed() {
        return levelFailed;
    }

    public void setLevelFailed(Pixmap levelFailed) {
        this.levelFailed = levelFailed;
    }

    public Pixmap getButtonHardModeOff() {
        return buttonHardModeOff;
    }

    public void setButtonHardModeOff(Pixmap buttonHardModeOff) {
        this.buttonHardModeOff = buttonHardModeOff;
    }

    public Pixmap getButtonHardModeOn() {
        return buttonHardModeOn;
    }

    public void setButtonHardModeOn(Pixmap buttonHardModeOn) {
        this.buttonHardModeOn = buttonHardModeOn;
    }
    
    public Pixmap getPresent() {
        return present;
    }

    public void setPresent(Pixmap present) {
        this.present = present;
    }

    public Sound getSoundClick() {
        return soundClick;
    }

    public void setSoundClick(Sound soundClick) {
        this.soundClick = soundClick;
    }

    public Sound getSoundHideMine() {
        return soundHideMine;
    }

    public void setSoundHideMine(Sound soundHideMine) {
        this.soundHideMine = soundHideMine;
    }

    public Sound getSoundSetupFlag() {
        return soundSetupFlag;
    }

    public void setSoundSetupFlag(Sound soundSetupFlag) {
        this.soundSetupFlag = soundSetupFlag;
    }

    public Sound getSoundSetupMine() {
        return soundSetupMine;
    }

    public void setSoundSetupMine(Sound soundSetupMine) {
        this.soundSetupMine = soundSetupMine;
    }

    public Sound getSoundWin() {
        return soundWin;
    }

    public void setSoundWin(Sound soundWin) {
        this.soundWin = soundWin;
    }

    public List<String> getLevels() {
        return levels;
    }

    public void setLevels(List<String> levels) {
        this.levels = levels;
    }

    public Sound getSoundLose() {
        return soundLose;
    }

    public void setSoundLose(Sound soundLose) {
        this.soundLose = soundLose;
    }

    public Sound getSoundWoosh() {
        return soundWoosh;
    }

    public void setSoundWoosh(Sound soundWoosh) {
        this.soundWoosh = soundWoosh;
    }

    public Sound getSoundPresent() {
        return soundPresent;
    }

    public void setSoundPresent(Sound soundPresent) {
        this.soundPresent = soundPresent;
    }    
    
}
