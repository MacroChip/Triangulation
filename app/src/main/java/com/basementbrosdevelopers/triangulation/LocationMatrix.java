package com.basementbrosdevelopers.triangulation;

public class LocationMatrix {

    private Square[][] matrix;

    public LocationMatrix() {
        matrix = new Square[][]{
                {ranSquare(), ranSquare(), ranSquare(), ranSquare()},
                {ranSquare(), ranSquare(), ranSquare(), ranSquare()},
                {ranSquare(), ranSquare(), ranSquare(), ranSquare()},
                {ranSquare(), ranSquare(), ranSquare(), ranSquare()}
        };
    }

    private static Square ranSquare() {
        int ranValA = (int) (4 * Math.random());
        int ranValB = (int) (4 * Math.random());
        return new Square(ranValA, ranValB);
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder(" ").append("\n");
        for (Square[] row : matrix) {
            for (Square square : row) {
                string.append(square).append(" ");
            }
            string.append("\n");
        }
        return string.toString();
    }

    public boolean checkRightRhombus(int j, int i) {
        int o = matrix[j][i].getRightTriangle();
        int x = matrix[j][i+1].getLeftTriangle();
        int y = matrix[j+1][i].getRightTriangle();
        int xy = matrix[j+1][i+1].getLeftTriangle();
        if (o == x && o == y && o == xy) {
            replaceRightRhombus(j, i);
            return true;
        } else {
            return false;
        }
    }

    public boolean checkLeftRhombus(int j, int i) {
        int o = matrix[j][i].getLeftTriangle();
        int x = matrix[j][i-1].getRightTriangle();
        int y = matrix[j-1][i].getLeftTriangle();
        int xy = matrix[j-1][i-1].getRightTriangle();
        if (o == x && o == y && o == xy) {
            replaceLeftRhombus(j, i);
            return true;
        } else {
            return false;
        }
    }

    public void replaceRightRhombus(int j, int i) {
        matrix[j][i].newRightTriangle();
        matrix[j][i+1].newLeftTriangle();
        matrix[j+1][i].newRightTriangle();
        matrix[j+1][i+1].newLeftTriangle();
    }

    public void replaceLeftRhombus(int j, int i) {
        matrix[j][i].newLeftTriangle();
        matrix[j][i-1].newRightTriangle();
        matrix[j-1][i].newLeftTriangle();
        matrix[j-1][i-1].newRightTriangle();
    }
}