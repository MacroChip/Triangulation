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

}