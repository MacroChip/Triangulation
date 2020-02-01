package com.basementbrosdevelopers.triangulation;

public class Square {

    private static final double HIGHEST_TRIANGLE_VALUE = 5.27;
    private int leftTriangle;
    private int rightTriangle;

    public Square() {
        this.leftTriangle = (int) (HIGHEST_TRIANGLE_VALUE * Math.random());
        this.rightTriangle = (int) (HIGHEST_TRIANGLE_VALUE * Math.random());
    }

    public int getLeftTriangle() {
        return leftTriangle;
    }

    public int getRightTriangle() {
        return rightTriangle;
    }

    public void newLeftTriangle() {
        leftTriangle = (int) (HIGHEST_TRIANGLE_VALUE * Math.random());
    }

    public void newRightTriangle() {
        rightTriangle = (int) (HIGHEST_TRIANGLE_VALUE * Math.random());
    }

    public void swap() {
        int leftOriginal = leftTriangle;
        leftTriangle = rightTriangle;
        rightTriangle = leftOriginal;
    }

    @Override
    public String toString() {
        return leftTriangle + "," + rightTriangle;
    }
}
