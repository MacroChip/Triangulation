package com.basementbrosdevelopers.triangulation;

import android.util.SparseIntArray;

import androidx.annotation.DrawableRes;

public class GraphicsManager {

    private static final SparseIntArray graphicsMap = new SparseIntArray();

    static {
        graphicsMap.put(0, R.drawable.ic_triangleblue1);
        graphicsMap.put(1, R.drawable.ic_triangleblue2);
        graphicsMap.put(2, R.drawable.ic_trianglegreen1);
        graphicsMap.put(3, R.drawable.ic_trianglegreen2);
        graphicsMap.put(4, R.drawable.ic_trianglemagenta);
        graphicsMap.put(5, R.drawable.ic_triangleorange);
        graphicsMap.put(6, R.drawable.ic_trianglepink1);
        graphicsMap.put(7, R.drawable.ic_trianglepink2);
        graphicsMap.put(8, R.drawable.ic_trianglepurple);
    }

    @DrawableRes
    public static int getDrawableId(int triangleValue) {
        return graphicsMap.get(triangleValue);
    }
}
