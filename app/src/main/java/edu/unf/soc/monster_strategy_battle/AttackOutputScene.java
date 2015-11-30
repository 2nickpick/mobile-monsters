package edu.unf.soc.monster_strategy_battle;

import org.andengine.opengl.font.Font;

import java.util.Random;

public class AttackOutputScene extends OutputScene {

    private Monster monster; // monster performing attack
    private Monster targetMonster; // monster receiving damage
    private boolean player; // whether or not monster belongs to player
    private Attack attack; // attack chosen by monster

    public AttackOutputScene(String outputText, MainActivity mainActivity, Font sansSmall) {
        super(outputText, mainActivity, sansSmall);
    }

    public AttackOutputScene(String outputText, MainActivity mainActivity, Font sansSmall, Monster monster, Monster targetMonster, Attack attack, boolean player) {
        this(outputText, mainActivity, sansSmall);
        this.monster = monster;
        this.targetMonster = targetMonster;
        this.attack = attack;
        this.player = player;
    }

    @Override
    public void onSceneReady() {

        monster.attack(player);


        this.animating = false;

    }
}
