package edu.unf.soc.monster_strategy_battle;

import org.andengine.opengl.font.Font;

public class ReleaseOutputScene extends OutputScene {

    private Monster monster; // monster to be released
    private boolean player; // whether or not monster belongs to player

    public ReleaseOutputScene(String outputText, MainActivity mainActivity, Font sansSmall) {
        super(outputText, mainActivity, sansSmall);
    }

    public ReleaseOutputScene(String outputText, MainActivity mainActivity, Font sansSmall, Monster monster, boolean player) {
        this(outputText, mainActivity, sansSmall);
        this.monster = monster;
        this.player = player;
    }

    @Override
    public void onSceneReady() {
        monster.release(mainActivity.gameScene, player);
        mainActivity.updateMonsterInformation(monster, player);
        this.animating = false;
    }
}
