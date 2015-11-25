package edu.unf.soc.monster_strategy_battle;

import java.util.ArrayList;


public class MonsterType {
    private String name;
    private ArrayList<String> resistances = new ArrayList<>();
    private ArrayList<String> weaknesses = new ArrayList<>();
    private ArrayList<String> immunities = new ArrayList<>();

    public MonsterType(String name) {
        this.name = name;

        switch(this.name) {
            case "Fire":
                this.weaknesses.add("Water");
                this.weaknesses.add("Ground");
                this.weaknesses.add("Rock");

                this.resistances.add("Fire");
                this.resistances.add("Grass");
                this.resistances.add("Bug");
                break;
            case "Grass":
                this.weaknesses.add("Fire");
                this.weaknesses.add("Ice");
                this.weaknesses.add("Flying");
                this.weaknesses.add("Bug");
                this.weaknesses.add("Poison");

                this.resistances.add("Water");
                this.resistances.add("Electric");
                this.resistances.add("Grass");
                this.resistances.add("Ground");

                break;
            case "Water":
                this.weaknesses.add("Electric");
                this.weaknesses.add("Grass");

                this.resistances.add("Water");
                this.resistances.add("Fire");
                this.resistances.add("Ice");
                break;
        }
        this.resistances = resistances;
        this.weaknesses = weaknesses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSpecial() {
        ArrayList<String> specialTypes = new ArrayList<>();
        specialTypes.add("Psychic");
        specialTypes.add("Grass");
        specialTypes.add("Dragon");
        specialTypes.add("Fire");
        specialTypes.add("Water");
        specialTypes.add("Electric");
        specialTypes.add("Ice");
        return specialTypes.indexOf(this.name) >= 0;
    }

    public ArrayList<String> getResistances() {
        return resistances;
    }

    public void setResistances(ArrayList<String> resistances) {
        this.resistances = resistances;
    }

    public ArrayList<String> getWeaknesses() {
        return weaknesses;
    }

    public void setWeaknesses(ArrayList<String> weaknesses) {
        this.weaknesses = weaknesses;
    }
}
