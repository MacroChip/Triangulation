package com.basementbrosdevelopers.triangulation;

import org.jetbrains.annotations.NotNull;

public class Square {

    private static final double HIGHEST_TRIANGLE_VALUE = 5.27;
    private int left;
    private int right;

    public Square() {
        do {
            this.left = (int) (HIGHEST_TRIANGLE_VALUE * Math.random());
            this.right = (int) (HIGHEST_TRIANGLE_VALUE * Math.random());
        } while (left == right);
    }

    public Square(int left, int right) {
        this.left = left;
        this.right = right;
    }

    public Square deepCopy() {
        return new Square(left, right);
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public void setLeft(int newLeft) {
        left = newLeft;
    }

    public void setRight(int newRight) {
        right = newRight;
    }

    public void newLeftTriangle() {
        do {
            left = (int) (HIGHEST_TRIANGLE_VALUE * Math.random());
        } while (left == right);
    }

    public void newRightTriangle() {
        do {
            right = (int) (HIGHEST_TRIANGLE_VALUE * Math.random());
        } while (left == right);
    }

    public void swap() {
        int leftOriginal = left;
        left = right;
        right = leftOriginal;
    }

    @NotNull
    @Override
    public String toString() {
        return left + "," + right;
    }
}
