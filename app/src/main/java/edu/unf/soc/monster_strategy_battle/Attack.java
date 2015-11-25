package edu.unf.soc.monster_strategy_battle;


import java.util.ArrayList;

public class Attack {
    private String name;
    private int power;
    private float accuracy;
    private MonsterType type;

    public Attack(String name, int power, float accuracy, MonsterType type) {
        this.name = name;
        this.power = power;
        this.accuracy = accuracy;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MonsterType getType() {
        return type;
    }

    public void setType(MonsterType type) {
        this.type = type;
    }

    public boolean isSpecial() {
        return this.type.isSpecial();

    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }
}
