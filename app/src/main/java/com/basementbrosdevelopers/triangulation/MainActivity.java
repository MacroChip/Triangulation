package com.basementbrosdevelopers.triangulation;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(getClass().toString(), new LocationMatrix().toString());
        ViewGroup mainView = findViewById(R.id.main);
        createTriangle(mainView, R.drawable.ic_triangleblue1);
        createTriangle(mainView, R.drawable.ic_triangleblue2);
        createTriangle(mainView, R.drawable.ic_trianglegreen1);
        createTriangle(mainView, R.drawable.ic_trianglegreen2);
        createTriangle(mainView, R.drawable.ic_trianglemagenta);
        createTriangle(mainView, R.drawable.ic_triangleorange);
        createTriangle(mainView, R.drawable.ic_trianglepink1);
        createTriangle(mainView, R.drawable.ic_trianglepink2);
        createTriangle(mainView, R.drawable.ic_trianglepurple);
    }

    private void createTriangle(ViewGroup mainView, int drawable) {
        ImageView triangle = new ImageView(this);
        triangle.setImageResource(drawable);
        mainView.addView(triangle);
    }
}
