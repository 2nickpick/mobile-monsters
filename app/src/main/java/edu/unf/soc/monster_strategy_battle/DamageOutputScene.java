package edu.unf.soc.monster_strategy_battle;

import org.andengine.opengl.font.Font;

public class DamageOutputScene extends OutputScene {

    private Monster monster; // monster taking damage
    private boolean player; // whether or not monster belongs to player
    private int damage; // amount of damage dealt

    public DamageOutputScene(String outputText, MainActivity mainActivity, Font sansSmall) {
        super(outputText, mainActivity, sansSmall);
    }

    public DamageOutputScene(String outputText, MainActivity mainActivity, Font sansSmall, Monster monster, int damage, boolean player) {
        this(outputText, mainActivity, sansSmall);
        this.monster = monster;
        this.player = player;
        this.damage = damage;
    }

    @Override
    public void onSceneReady() {
        monster.damage();

        mainActivity.updateMonsterInformation(
                monster,
                player
        );

        this.animating = false;
    }
}
