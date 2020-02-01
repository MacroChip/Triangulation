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
        for (Square[] row : locationMatrix.matrix) {
            createRow(row);
        }
    }

    private void createRow(Square[] row) {
        LinearLayout linearLayout = new LinearLayout(this);
        mainView.addView(linearLayout);
        for (Square square : row) {
            createSquare(linearLayout, square);
        }
    }

    private void createSquare(LinearLayout linearLayout, Square square) {
        createRotatedTriangle(linearLayout, R.drawable.ic_triangleorange);
        createTriangle(linearLayout, R.drawable.ic_triangleorange);
    }

    private void createRotatedTriangle(LinearLayout linearLayout, int drawable) {
        ImageView triangle = createTriangle(linearLayout, drawable);
        triangle.setRotation(180f);
    }

    private ImageView createTriangle(ViewGroup parentView, int drawable) {
        ImageView triangle = new ImageView(this);
        triangle.setImageResource(drawable);
        parentView.addView(triangle);
        return triangle;
    }
}
