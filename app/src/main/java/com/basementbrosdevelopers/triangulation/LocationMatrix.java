package com.basementbrosdevelopers.triangulation;

public class LocationMatrix {

    public final Square[][] matrix;
    public Scoreboard scoreboard;

    public LocationMatrix(Scoreboard scoreboard) {
        matrix = new Square[][]{
                {new Square(), new Square(), new Square(), new Square()},
                {new Square(), new Square(), new Square(), new Square()},
                {new Square(), new Square(), new Square(), new Square()},
                {new Square(), new Square(), new Square(), new Square()}
        };
        this.scoreboard = scoreboard;
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

        if (j % 2 == 0 && i % 2 == 0) {
            int o = matrix[j][i].getRightTriangle();
            int x = matrix[j][i + 1].getLeftTriangle();
            int y = matrix[j + 1][i].getRightTriangle();
            int xy = matrix[j + 1][i + 1].getLeftTriangle();
            if (o == x && o == y && o == xy) {
                replaceRightRhombus(j, i);
                scoreboard.add();
                return true;
            } else {
                return false;
            }
        } else {
            int o = matrix[j][i].getRightTriangle();
            int x = matrix[j][i + 1].getLeftTriangle();
            int y = matrix[j - 1][i].getRightTriangle();
            int xy = matrix[j - 1][i + 1].getLeftTriangle();
            if (o == x && o == y && o == xy) {
                replaceRightRhombus(j, i);
                scoreboard.add();
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean checkLeftRhombus(int j, int i) {
        if (j % 2 == 0 && i % 2 == 0) {
            int o = matrix[j][i].getLeftTriangle();
            int x = matrix[j][i - 1].getRightTriangle();
            int y = matrix[j - 1][i].getLeftTriangle();
            int xy = matrix[j - 1][i - 1].getRightTriangle();
            if (o == x && o == y && o == xy) {
                replaceLeftRhombus(j, i);
                scoreboard.add();
                return true;
            } else {
                return false;
            }
        } else {
            int o = matrix[j][i].getLeftTriangle();
            int x = matrix[j][i - 1].getRightTriangle();
            int y = matrix[j + 1][i].getLeftTriangle();
            int xy = matrix[j + 1][i - 1].getRightTriangle();
            if (o == x && o == y && o == xy) {
                replaceLeftRhombus(j, i);
                scoreboard.add();
                return true;
            } else {
                return false;
            }
        }
    }

    private void replaceRightRhombus(int j, int i) {
        if (j % 2 == 0 && i % 2 == 0) {
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

    private void replaceLeftRhombus(int j, int i) {
        if (j % 2 == 0 && i % 2 == 0) {
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
}