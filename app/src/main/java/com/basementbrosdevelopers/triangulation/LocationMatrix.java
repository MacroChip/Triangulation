package com.basementbrosdevelopers.triangulation;

import android.util.Log;

public class LocationMatrix {

    public static void main(String args[])
    {
        Tuple mat[][] = {
                { ranSquare(), ranSquare(), ranSquare(), ranSquare() },
                { ranSquare(), ranSquare(), ranSquare(), ranSquare() },
                { ranSquare(), ranSquare(), ranSquare(), ranSquare() },
                { ranSquare(), ranSquare(), ranSquare(), ranSquare() }
        };

        Tuple test = new Tuple(69, 420);
        test.squareSwap();
        Log.d("locationMatrix", test.toString() + "XD");
        print2D(mat);
    }

    public static Tuple ranSquare() {
        int ranValA = (int) (4*Math.random());
        int ranValB = (int) (4*Math.random());
        Tuple square = new Tuple(ranValA,ranValB);
        return square;
    }

    public static void print2D(Tuple mat[][])
    {
        // Loop through all rows
        for (int i = 0; i < mat.length; i++)

            // Loop through all elements of current row
            for (int j = 0; j < mat[i].length; j++)
                Log.d("locationMatrix", mat[i][j].toString() + "; " + i + " " + j);
    }

}