package com.basementbrosdevelopers.triangulation;

public class Tuple {

    private int leftTriangle;
    private int rightTriangle;

    public Tuple(int i, int j) {
        leftTriangle = i;
        rightTriangle = j;
    }

    public int getLeftTriangle() {
        return leftTriangle;
    }

    public int getRightTriangle() {
        return rightTriangle;
    }

    public void setLeftTriangle(int i) {
        leftTriangle = i;
    }

    public void setRightTriangle(int i) {
        rightTriangle = i;
    }

    public String print() {
        return leftTriangle + "," + rightTriangle;
    }
}
