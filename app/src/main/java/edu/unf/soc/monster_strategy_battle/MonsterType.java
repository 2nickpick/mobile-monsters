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
                this.resistances.add("Ice");
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
            case "Normal":
                this.weaknesses.add("Fighting");

                this.immunities.add("Ghost");
                break;
            case "Bug":
                this.weaknesses.add("Fire");
                this.weaknesses.add("Flying");
                this.weaknesses.add("Rock");

                this.resistances.add("Grass");
                this.resistances.add("Fighting");
                this.resistances.add("Ground");
                break;
            case "Electric":
                this.weaknesses.add("Ground");

                this.resistances.add("Electric");
                this.resistances.add("Flying");
                break;
            case "Ice":
                this.weaknesses.add("Fire");
                this.weaknesses.add("Fighting");
                this.weaknesses.add("Rock");

                this.resistances.add("Ice");
                break;
            case "Fighting":
                this.weaknesses.add("Psychic");
                this.weaknesses.add("Flying");

                this.resistances.add("Bug");
                this.resistances.add("Rock");
                break;
            case "Poison":
                this.weaknesses.add("Psychic");
                this.weaknesses.add("Ground");

                this.resistances.add("Poison");
                this.resistances.add("Grass");
                this.resistances.add("Fighting");
                this.resistances.add("Bug");
                break;
            case "Ground":
                this.weaknesses.add("Water");
                this.weaknesses.add("Grass");
                this.weaknesses.add("Ice");

                this.resistances.add("Poison");
                this.resistances.add("Rock");

                this.immunities.add("Electric");
                break;
            case "Flying":
                this.weaknesses.add("Electric");
                this.weaknesses.add("Rock");
                this.weaknesses.add("Ice");

                this.resistances.add("Grass");
                this.resistances.add("Fighting");
                this.resistances.add("Bug");

                this.immunities.add("Ground");
                break;
            case "Psychic":
                this.weaknesses.add("Bug");
                this.weaknesses.add("Ghost");

                this.resistances.add("Fighting");
                this.resistances.add("Psychic");
                break;
            case "Rock":
                this.weaknesses.add("Water");
                this.weaknesses.add("Grass");
                this.weaknesses.add("Fighting");
                this.weaknesses.add("Ground");

                this.resistances.add("Normal");
                this.resistances.add("Fire");
                this.resistances.add("Poison");
                this.resistances.add("Flying");
                break;
            case "Ghost":
                this.weaknesses.add("Ghost");

                this.resistances.add("Poison");
                this.resistances.add("Bug");

                this.immunities.add("Normal");
                this.immunities.add("Fighting");
                break;
            case "Dragon":
                this.weaknesses.add("Ice");
                this.weaknesses.add("Dragon");

                this.resistances.add("Fire");
                this.resistances.add("Water");
                this.resistances.add("Grass");
                this.resistances.add("Electric");

                break;
        }
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

    public ArrayList<String> getImmunities() {
        return immunities;
    }

    public void setImmunities(ArrayList<String> immunities) {
        this.immunities = immunities;
    }
}
