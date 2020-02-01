package com.basementbrosdevelopers.triangulation;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ViewGroup mainView;
    private Scoreboard allScores = new Scoreboard();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LocationMatrix locationMatrix = new LocationMatrix(allScores);
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
        FrameLayout frame = new FrameLayout(this);
        ImageView leftTriangle = createTriangle(frame, square.getLeftTriangle());
        leftTriangle.setRotation(180f);
        createTriangle(frame, square.getRightTriangle());
        linearLayout.addView(frame);
    }

    private void createInvertedSquare(LinearLayout linearLayout, Square square) {
        FrameLayout frame = new FrameLayout(this);
        ImageView leftTriangle = createTriangle(frame, square.getLeftTriangle());
        leftTriangle.setRotation(90f);
        ImageView rightTriangle = createTriangle(frame, square.getRightTriangle());
        rightTriangle.setRotation(-90f);
        linearLayout.addView(frame);
    }

    private ImageView createTriangle(ViewGroup parentView, int triangleValue) {
        ImageView triangle = new ImageView(this);
        triangle.setImageResource(GraphicsManager.getDrawableId(triangleValue));
        parentView.addView(triangle);
        return triangle;
    }
}
