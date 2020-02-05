package com.basementbrosdevelopers.triangulation;

import androidx.annotation.DrawableRes;

import java.util.HashMap;
import java.util.Map;

public class GraphicsManager {

    public static final int WILDCARD_INDEX;

    private static final Map<Integer, Integer> graphicsMap = new HashMap<>(); // Need to provide a SparseIntArray in JVM tests

    static {
        graphicsMap.put(0, R.drawable.ic_triangleblue2);
        graphicsMap.put(1, R.drawable.ic_trianglegreen1);
        graphicsMap.put(2, R.drawable.ic_triangleorange);
        graphicsMap.put(3, R.drawable.ic_trianglepink2);
        graphicsMap.put(4, R.drawable.ic_trianglepurple);
        graphicsMap.put(5, R.drawable.ic_trianglewild);
        WILDCARD_INDEX = graphicsMap.size() - 1;
    }

    @DrawableRes
    public static int getDrawableId(int triangleValue) {
        return graphicsMap.get(triangleValue);
    }
}
