package com.basementbrosdevelopers.triangulation;

import android.util.Log;

public class LocationMatrix {

    public static void main(String args[])
    {
        int mat[][] = {
                { color(), color(), color(), color() },
                { color(), color(), color(), color() },
                { color(), color(), color(), color() },
                { color(), color(), color(), color() }
        };
        print2D(mat);
    }

    public static int color() {
        int ranVal = (int) (4*Math.random());
        return ranVal; //Matrices can't accept non-numbers
    }

    public static void print2D(int mat[][])
    {
        // Loop through all rows
        for (int i = 0; i < mat.length; i++)

            // Loop through all elements of current row
            for (int j = 0; j < mat[i].length; j++)
                Log.d("locationMatrix", mat[i][j] + " " + i + " " + j);
    }

    public static void swap(int x, int y) {
        
    }
}