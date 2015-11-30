package edu.unf.soc.monster_strategy_battle;

import org.andengine.opengl.font.Font;

public class ReturnOutputScene extends OutputScene {

    private Monster monster; // monster to be returned
    private boolean player; // whether or not monster belongs to player

    public ReturnOutputScene(String outputText, MainActivity mainActivity, Font sansSmall) {
        super(outputText, mainActivity, sansSmall);
    }

    public ReturnOutputScene(String outputText, MainActivity mainActivity, Font sansSmall, Monster monster, boolean player) {
        this(outputText, mainActivity, sansSmall);
        this.monster = monster;
        this.player = player;
    }

    @Override
    public void onSceneReady() {
        monster.recall(mainActivity.gameScene, player);
        mainActivity.updateMonsterInformation(monster, player);
        this.animating = false;
    }
}
