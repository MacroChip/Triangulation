package com.basementbrosdevelopers.triangulation;

public class Square {

    private int leftTriangle;
    private int rightTriangle;

    public Square(int leftTriangle, int rightTriangle) {
        this.leftTriangle = leftTriangle;
        this.rightTriangle = rightTriangle;
    }

    public int getLeftTriangle() {
        return leftTriangle;
    }

    public int getRightTriangle() {
        return rightTriangle;
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
