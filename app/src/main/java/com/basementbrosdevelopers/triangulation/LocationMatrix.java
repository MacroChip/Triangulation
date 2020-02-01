package com.basementbrosdevelopers.triangulation;

import android.util.Log;

public class LocationMatrix {

    public static void main(String args[])
    {
        Square mat[][] = {
                { ranSquare(), ranSquare(), ranSquare(), ranSquare() },
                { ranSquare(), ranSquare(), ranSquare(), ranSquare() },
                { ranSquare(), ranSquare(), ranSquare(), ranSquare() },
                { ranSquare(), ranSquare(), ranSquare(), ranSquare() }
        };

        Square test = new Square(69, 420);
        test.swap();
        Log.d("locationMatrix", test.toString() + "XD");
        print2D(mat);
    }

    public static Square ranSquare() {
        int ranValA = (int) (4*Math.random());
        int ranValB = (int) (4*Math.random());
        Square square = new Square(ranValA,ranValB);
        return square;
    }

    public static void print2D(Square mat[][])
    {
        // Loop through all rows
        for (int i = 0; i < mat.length; i++)

            // Loop through all elements of current row
            for (int j = 0; j < mat[i].length; j++)
                Log.d("locationMatrix", mat[i][j].toString() + "; " + i + " " + j);
    }

}