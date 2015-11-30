package edu.unf.soc.monster_strategy_battle;

import org.andengine.opengl.font.Font;

public class SimpleOutputScene extends OutputScene {

    public SimpleOutputScene(String outputText, MainActivity mainActivity, Font sansSmall) {
        super(outputText, mainActivity, sansSmall);
    }

    @Override
    public void onSceneReady() {
        this.animating = false;
    }
}
