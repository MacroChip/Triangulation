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

    public int getWidth() {
        return matrix[0].length;
    }

    public int getHeight() {
        return matrix.length;
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

}