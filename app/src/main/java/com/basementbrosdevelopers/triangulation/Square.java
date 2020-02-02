package com.basementbrosdevelopers.triangulation;

public class Square {

    private static final double HIGHEST_TRIANGLE_VALUE = 5.27;
    private int left;
    private int right;

    public Square() {
        this.left = (int) (HIGHEST_TRIANGLE_VALUE * Math.random());
        this.right = (int) (HIGHEST_TRIANGLE_VALUE * Math.random());
    }

    public Square(int left, int right) {
        this.left = left;
        this.right = right;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public void newLeftTriangle() {
        left = (int) (HIGHEST_TRIANGLE_VALUE * Math.random());
    }

    public void newRightTriangle() {
        right = (int) (HIGHEST_TRIANGLE_VALUE * Math.random());
    }

    public void swap() {
        int leftOriginal = left;
        left = right;
        right = leftOriginal;
    }

    @Override
    public String toString() {
        return left + "," + right;
    }
}
