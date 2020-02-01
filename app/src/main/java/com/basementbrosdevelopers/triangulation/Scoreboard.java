package com.basementbrosdevelopers.triangulation;

public class Scoreboard {

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
