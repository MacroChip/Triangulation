package com.basementbrosdevelopers.triangulation;

public class SquareSwapModel {
    public static final String SERIALIZATION_KEY = "square_swap_model";
    private int y = -1;
    private int x = -1;

    public void setOrigin(int y, int x) {
        this.y = y;
        this.x = x;
    }

    public boolean hasOriginSet() {
        return x != -1;
    }

    public void clearOrigin() {
        y = -1;
        x = -1;
    }

    public int getJOrigin() {
        return y;
    }

    public int getIOrigin() {
        return x;
    }
}
