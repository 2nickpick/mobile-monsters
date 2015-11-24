package edu.unf.soc.monster_strategy_battle;


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
}
