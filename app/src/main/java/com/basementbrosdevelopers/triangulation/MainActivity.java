package com.basementbrosdevelopers.triangulation;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LocationMatrix.main(null);
        ViewGroup mainView = findViewById(R.id.main);
        createTriangle(mainView, R.drawable.ic_triangleorange);
        createTriangle(mainView, R.drawable.ic_triangleorange);
    }

    private void createTriangle(ViewGroup mainView, int drawable) {
        ImageView triangle = new ImageView(this);
        triangle.setImageResource(drawable);
        mainView.addView(triangle);
    }
}
