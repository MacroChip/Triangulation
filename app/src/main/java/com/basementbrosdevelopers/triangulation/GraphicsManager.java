package com.basementbrosdevelopers.triangulation;

import android.util.SparseIntArray;

import androidx.annotation.DrawableRes;

public class GraphicsManager {

    private static final SparseIntArray graphicsMap = new SparseIntArray();

    static {
        graphicsMap.put(0, R.drawable.ic_triangleblue2);
        graphicsMap.put(1, R.drawable.ic_trianglegreen1);
        graphicsMap.put(2, R.drawable.ic_triangleorange);
        graphicsMap.put(3, R.drawable.ic_trianglepink2);
        graphicsMap.put(4, R.drawable.ic_trianglepurple);
    }

    @DrawableRes
    public static int getDrawableId(int triangleValue) {
        return graphicsMap.get(triangleValue);
    }
}
