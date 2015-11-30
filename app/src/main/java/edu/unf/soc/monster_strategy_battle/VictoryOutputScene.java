package edu.unf.soc.monster_strategy_battle;

import org.andengine.opengl.font.Font;

public class VictoryOutputScene extends OutputScene {

    public VictoryOutputScene(String outputText, MainActivity mainActivity, Font sansSmall) {
        super(outputText, mainActivity, sansSmall);
    }

    @Override
    public void onSceneReady() {
        mainActivity.battleMusic.pause();
        mainActivity.victoryMusic.seekTo(0);
        mainActivity.victoryMusic.play();

        this.animating = false;
    }
}
