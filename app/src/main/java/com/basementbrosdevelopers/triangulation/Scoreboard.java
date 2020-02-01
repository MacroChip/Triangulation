package com.basementbrosdevelopers.triangulation;

public class Scoreboard {

    private int currentPoints;
    private int hi_score;
    private int last_score;

    public void add() {
        currentPoints++;
    }

    public void end() {
        last_score = currentPoints;
        if (currentPoints > hi_score) {
            hi_score = currentPoints;
        }
    }



}
