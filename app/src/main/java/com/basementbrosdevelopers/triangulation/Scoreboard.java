package com.basementbrosdevelopers.triangulation;

public class Scoreboard {

    public static final String SERIALIZATION_KEY = "scoreboard";
    private int currentPoints;
    private int hiScore;
    private int lastScore;

    public void add() {
        currentPoints++;
    }

    public void end() {
        lastScore = currentPoints;
        if (currentPoints > hiScore) {
            hiScore = currentPoints;
        }
    }


}
