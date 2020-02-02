package com.basementbrosdevelopers.triangulation;

public class Energy {

    private int energyLevel = 1;

    public void loseEnergy() {
        energyLevel -= 1;
    }

    public void gainEnergy() {
        energyLevel += 1;
    }
}
