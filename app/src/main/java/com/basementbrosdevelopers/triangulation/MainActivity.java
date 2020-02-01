package com.basementbrosdevelopers.triangulation;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ViewGroup mainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LocationMatrix locationMatrix = new LocationMatrix();
        Log.d(getClass().toString(), locationMatrix.toString());
        mainView = findViewById(R.id.main);
        Square[][] matrix = locationMatrix.matrix;
        for (int i = 0; i < matrix.length; i++) {
            Square[] row = matrix[i];
            if (i % 2 == 0) {
                createRow(row);
            } else {
                createInvertedRow(row);
            }
        }
    }

    private void createRow(Square[] row) {
        LinearLayout linearLayout = new LinearLayout(this);
        mainView.addView(linearLayout);
        for (int i = 0; i < row.length; i++) {
            Square square = row[i];
            if (i % 2 == 0) {
                createSquare(linearLayout, square);
            } else {
                createInvertedSquare(linearLayout, square);
            }
        }
    }

    private void createInvertedRow(Square[] row) {
        LinearLayout linearLayout = new LinearLayout(this);
        mainView.addView(linearLayout);
        for (int i = 0; i < row.length; i++) {
            Square square = row[i];
            if (i % 2 == 0) {
                createInvertedSquare(linearLayout, square);
            } else {
                createSquare(linearLayout, square);
            }
        }
    }

    private void createSquare(LinearLayout linearLayout, Square square) {
        createRotatedTriangle(linearLayout, R.drawable.ic_triangleorange);
        createTriangle(linearLayout, R.drawable.ic_triangleorange);
    }

    private void createInvertedSquare(LinearLayout linearLayout, Square square) {
        ImageView triangle = createTriangle(linearLayout, R.drawable.ic_triangleorange);
        triangle.setRotation(90f);
        ImageView rotatedTriangle = createTriangle(linearLayout, R.drawable.ic_triangleorange);
        rotatedTriangle.setRotation(-90f);
    }

    private ImageView createTriangle(ViewGroup parentView, int drawable) {
        ImageView triangle = new ImageView(this);
        triangle.setImageResource(drawable);
        parentView.addView(triangle);
        return triangle;
    }

    private ImageView createRotatedTriangle(LinearLayout linearLayout, int drawable) {
        ImageView triangle = createTriangle(linearLayout, drawable);
        triangle.setRotation(180f);
        return triangle;
    }
}
