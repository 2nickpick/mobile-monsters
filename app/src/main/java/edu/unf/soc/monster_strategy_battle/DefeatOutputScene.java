package edu.unf.soc.monster_strategy_battle;

import org.andengine.opengl.font.Font;

public class DefeatOutputScene extends OutputScene {

    public DefeatOutputScene(String outputText, MainActivity mainActivity, Font sansSmall) {
        super(outputText, mainActivity, sansSmall);
    }

    @Override
    public void onSceneReady() {
        mainActivity.battleMusic.pause();
        mainActivity.defeatMusic.seekTo(0);
        mainActivity.defeatMusic.play();
        this.animating = false;
    }
}
