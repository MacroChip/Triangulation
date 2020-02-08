package com.basementbrosdevelopers.triangulation;

public class Energy {

    public static final int ENERGY_COST = 5;
    public static final int ENERGY_GAIN = 1;
    private int energyLevel = 1;

    public void loseEnergy() {
        energyLevel -= ENERGY_COST;
    }

    public void gainEnergy() {
        energyLevel += ENERGY_GAIN;
    }

    public int getEnergy() {
        return energyLevel;
    }

    public boolean canSwapSquares() {
        return energyLevel >= ENERGY_COST;
    }
}
