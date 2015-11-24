package edu.unf.soc.monster_strategy_battle;

import java.util.ArrayList;


public class MonsterType {
    private String name;
    private ArrayList<MonsterType> resistances = new ArrayList<>();
    private ArrayList<MonsterType> weaknesses = new ArrayList<>();

    public MonsterType(String name, ArrayList<MonsterType> resistances, ArrayList<MonsterType> weaknesses) {
        this.name = name;
        this.resistances = resistances;
        this.weaknesses = weaknesses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
