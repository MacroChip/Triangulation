package com.basementbrosdevelopers.triangulation;

import java.util.Arrays;

import static com.basementbrosdevelopers.triangulation.NumberUtilsKt.areAllTheSame;
import static com.basementbrosdevelopers.triangulation.NumberUtilsKt.filterOutWildCards;

public class LocationMatrix {

    public final Square[][] matrix;

    public LocationMatrix() {
        matrix = new Square[][]{
                {new Square(), new Square(), new Square(), new Square()},
                {new Square(), new Square(), new Square(), new Square()},
                {new Square(), new Square(), new Square(), new Square()},
                {new Square(), new Square(), new Square(), new Square()}
        };
    }

    public LocationMatrix(Square[][] matrix) {
        this.matrix = matrix;
    }

    public LocationMatrix deepCopy() {
        Square[][] matrix = new Square[this.matrix.length][this.matrix[0].length];
        for (int y = 0; y < this.matrix.length; y++) {
            Square[] row = this.matrix[y];
            for (int x = 0; x < row.length; x++) {
                Square square = row[x];
                matrix[y][x] = square.deepCopy();
            }
        }
        return new LocationMatrix(matrix);
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
        if (i < matrix[0].length - 1) {
            if (j % 2 == i % 2) {
                if (j < matrix.length - 1) {
                    int o = matrix[j][i].getRight();
                    int x = matrix[j][i + 1].getLeft();
                    int y = matrix[j + 1][i].getRight();
                    int xy = matrix[j + 1][i + 1].getLeft();
                    return isARhombus(o, x, y, xy);
                }
            } else {
                if (j > 0) {
                    int o = matrix[j][i].getRight();
                    int x = matrix[j][i + 1].getLeft();
                    int y = matrix[j - 1][i].getRight();
                    int xy = matrix[j - 1][i + 1].getLeft();
                    return isARhombus(o, x, y, xy);
                }
            }
        }
        return false;
    }

    private static boolean isARhombus(int o, int x, int y, int xy) {
        return areAllTheSame(filterOutWildCards(Arrays.asList(o, x, y, xy)));
    }

    public boolean checkLeftRhombus(int j, int i) {
        if (i > 0) {
            if (j % 2 == i % 2) {
                if (j > 0) {
                    int o = matrix[j][i].getLeft();
                    int x = matrix[j][i - 1].getRight();
                    int y = matrix[j - 1][i].getLeft();
                    int xy = matrix[j - 1][i - 1].getRight();
                    return isARhombus(o, x, y, xy);
                }
            } else {
                if (j < matrix.length - 1) {
                    int o = matrix[j][i].getLeft();
                    int x = matrix[j][i - 1].getRight();
                    int y = matrix[j + 1][i].getLeft();
                    int xy = matrix[j + 1][i - 1].getRight();
                    return isARhombus(o, x, y, xy);
                }
            }
        }
        return false;
    }

    public void replaceRightRhombus(int j, int i) {
        if (j % 2 == i % 2) {
            matrix[j][i].newRightTriangle();
            matrix[j][i + 1].newLeftTriangle();
            matrix[j + 1][i].newRightTriangle();
            matrix[j + 1][i + 1].newLeftTriangle();
        } else {
            matrix[j][i].newRightTriangle();
            matrix[j][i + 1].newLeftTriangle();
            matrix[j - 1][i].newRightTriangle();
            matrix[j - 1][i + 1].newLeftTriangle();
        }
    }

    public void replaceLeftRhombus(int j, int i) {
        if (j % 2 == i % 2) {
            matrix[j][i].newLeftTriangle();
            matrix[j][i - 1].newRightTriangle();
            matrix[j - 1][i].newLeftTriangle();
            matrix[j - 1][i - 1].newRightTriangle();
        } else {
            matrix[j][i].newLeftTriangle();
            matrix[j][i - 1].newRightTriangle();
            matrix[j + 1][i].newLeftTriangle();
            matrix[j + 1][i - 1].newRightTriangle();
        }
    }

    public boolean isInGridlock() {
        return false;
    }
}