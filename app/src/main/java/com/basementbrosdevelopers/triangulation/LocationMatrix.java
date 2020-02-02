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

    public void checkRightRhombus(int j, int i) {
        if (i < matrix[0].length) {
            if (j % 2 == i % 2) {
                if (j < matrix.length) {
                    int o = matrix[j][i].getRight();
                    int x = matrix[j][i + 1].getLeft();
                    int y = matrix[j + 1][i].getRight();
                    int xy = matrix[j + 1][i + 1].getLeft();
                    if (o == x && o == y && o == xy) {
                        replaceRightRhombus(j, i);
                        scoreboard.add();
                    }
                }
            } else {
                if (j > 0) {
                    int o = matrix[j][i].getRight();
                    int x = matrix[j][i + 1].getLeft();
                    int y = matrix[j - 1][i].getRight();
                    int xy = matrix[j - 1][i + 1].getLeft();
                    if (o == x && o == y && o == xy) {
                        replaceRightRhombus(j, i);
                        scoreboard.add();
                    }
                }
            }
        }
    }

    public void checkLeftRhombus(int j, int i) {
        if (i > 0) {
            if (j % 2 == i % 2) {
                if (j > 0) {
                    int o = matrix[j][i].getLeft();
                    int x = matrix[j][i - 1].getRight();
                    int y = matrix[j - 1][i].getLeft();
                    int xy = matrix[j - 1][i - 1].getRight();
                    if (o == x && o == y && o == xy) {
                        replaceLeftRhombus(j, i);
                        scoreboard.add();
                    }
                }
            } else {
                if (j < matrix.length) {
                    int o = matrix[j][i].getLeft();
                    int x = matrix[j][i - 1].getRight();
                    int y = matrix[j + 1][i].getLeft();
                    int xy = matrix[j + 1][i - 1].getRight();
                    if (o == x && o == y && o == xy) {
                        replaceLeftRhombus(j, i);
                        scoreboard.add();
                    }
                }
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