package com.basementbrosdevelopers.triangulation;

import java.io.Serializable;

public class Scoreboard implements Serializable {

    public static final String SERIALIZATION_KEY = "scoreboard";
    private int currentPoints;
    private int hiScore;

    public void add() {
        currentPoints++;
    }

    public void end() {
        if (currentPoints > hiScore) {
            hiScore = currentPoints;
        }
    }

    public int getCurrentPoints() {
        return currentPoints;
    }

    public int getHiScore() {
        return hiScore;
    }
}
