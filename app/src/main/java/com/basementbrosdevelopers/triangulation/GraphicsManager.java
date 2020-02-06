package com.basementbrosdevelopers.triangulation;

import androidx.annotation.DrawableRes;

import java.util.HashMap;
import java.util.Map;

public class GraphicsManager {

    public static final int WILDCARD_INDEX;

    private static final Map<Integer, Integer> graphicsMap = new HashMap<>(); // Need to provide a SparseIntArray in JVM tests

    static {
        graphicsMap.put(0, R.drawable.ic_triangleroundedblue1);
        graphicsMap.put(1, R.drawable.ic_triangleroundedblue2);
        graphicsMap.put(2, R.drawable.ic_triangleroundedgold);
        graphicsMap.put(3, R.drawable.ic_triangleroundedgreen);
        graphicsMap.put(4, R.drawable.ic_triangleroundedgrey);
        graphicsMap.put(5, R.drawable.ic_triangleroundedwild);
        WILDCARD_INDEX = graphicsMap.size() - 1;
    }

    @DrawableRes
    public static int getDrawableId(int triangleValue) {
        return graphicsMap.get(triangleValue);
    }
}
