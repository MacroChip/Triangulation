package com.basementbrosdevelopers.triangulation;

public class Tuple {

    private int leftTriangle;
    private int rightTriangle;

    public Tuple(int leftTriangle, int rightTriangle) {
        this.leftTriangle = leftTriangle;
        this.rightTriangle = rightTriangle;
    }

    public int getLeftTriangle() {
        return leftTriangle;
    }

    public int getRightTriangle() {
        return rightTriangle;
    }

    public void squareSwap() {
        int leftOriginal = leftTriangle;
        leftTriangle = rightTriangle;
        rightTriangle = leftOriginal;
    }

    @Override
    public String toString() {
        return leftTriangle + "," + rightTriangle;
    }
}
