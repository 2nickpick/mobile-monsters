package edu.unf.soc.monster_strategy_battle;

import org.andengine.opengl.font.Font;

public class FaintOutputScene extends OutputScene {

    private Monster monster; // monster performing attack
    private boolean player; // whether or not monster belongs to player

    public FaintOutputScene(String outputText, MainActivity mainActivity, Font sansSmall) {
        super(outputText, mainActivity, sansSmall);
    }

    public FaintOutputScene(String outputText, MainActivity mainActivity, Font sansSmall, Monster monster, boolean player) {
        this(outputText, mainActivity, sansSmall);
        this.monster = monster;
        this.player = player;
    }

    @Override
    public void onSceneReady() {
        monster.faint(player);
        this.animating = false;
    }
}
