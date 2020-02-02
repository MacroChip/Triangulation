package com.basementbrosdevelopers.triangulation;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static final int DO_NOT_WAIT = 0;
    public static final int VIBRATION_TIME = 15;
    public static final int DO_NOT_REPEAT = -1;
    private ViewGroup mainView;
    private Scoreboard scoreboard = new Scoreboard();
    private LocationMatrix locationMatrix;
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        locationMatrix = new LocationMatrix();
        Log.d(getClass().toString(), locationMatrix.toString());
        mainView = findViewById(R.id.main);
        createView();
    }

    private void createView() {
        for (int i = 0; i < locationMatrix.matrix.length; i++) {
            Square[] row = locationMatrix.matrix[i];
            if (i % 2 == 0) {
                createRow(row, i);
            } else {
                createInvertedRow(row, i);
            }
        }
    }

    private void createRow(Square[] row, int y) {
        LinearLayout linearLayout = new LinearLayout(this);
        mainView.addView(linearLayout);
        for (int i = 0; i < row.length; i++) {
            Square square = row[i];
            if (i % 2 == 0) {
                createSquare(linearLayout, square, y, i);
            } else {
                createInvertedSquare(linearLayout, square, y, i);
            }
        }
    }

    private void createInvertedRow(Square[] row, int y) {
        LinearLayout linearLayout = new LinearLayout(this);
        mainView.addView(linearLayout);
        for (int i = 0; i < row.length; i++) {
            Square square = row[i];
            if (i % 2 == 0) {
                createInvertedSquare(linearLayout, square, y, i);
            } else {
                createSquare(linearLayout, square, y, i);
            }
        }
    }

    private void createSquare(LinearLayout linearLayout, Square square, int y, int x) {
        FrameLayout frame = new FrameLayout(this);
        ImageView leftTriangle = createTriangle(frame, square.getLeft(), y, x);
        leftTriangle.setRotation(180f);
        createTriangle(frame, square.getRight(), y, x);
        linearLayout.addView(frame);
    }

    private void createInvertedSquare(LinearLayout linearLayout, Square square, int y, int x) {
        FrameLayout frame = new FrameLayout(this);
        ImageView leftTriangle = createTriangle(frame, square.getLeft(), y, x);
        leftTriangle.setRotation(90f);
        ImageView rightTriangle = createTriangle(frame, square.getRight(), y, x);
        rightTriangle.setRotation(-90f);
        linearLayout.addView(frame);
    }

    private ImageView createTriangle(ViewGroup parentView, int triangleValue, int y, int x) {
        ImageView triangle = new ImageView(this);
        triangle.setImageResource(GraphicsManager.getDrawableId(triangleValue));
        triangle.setOnClickListener(v -> {
            Log.d(MainActivity.this.getClass().toString(), "Clicking x: " + x + ", y: " + y);
            giveTapFeedback();
            locationMatrix.matrix[y][x].swap();
            checkAfterSwapConditions(y, x);
            if (locationMatrix.isInGridlock()) {
                Log.i(MainActivity.this.getClass().toString(), "Gridlocked!");
            }
            redraw();
        });
        parentView.addView(triangle);
        return triangle;
    }

    private void redraw() {
        mainView.removeAllViews();
        createView();
    }

    private void checkAfterSwapConditions(int y, int x) {
        boolean leftConditionMet = locationMatrix.checkLeftRhombus(y, x);
        if (leftConditionMet) {
            locationMatrix.replaceLeftRhombus(y, x);
            scoreboard.add();
        }
        boolean rightConditionMet = locationMatrix.checkRightRhombus(y, x);
        if (rightConditionMet) {
            locationMatrix.replaceRightRhombus(y, x);
            scoreboard.add();
        }
    }

    private void giveTapFeedback() {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.woosh);
        mediaPlayer.setOnCompletionListener(MediaPlayer::release);
        mediaPlayer.start();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Log.d(getClass().toString(), "Using modern vibrator");
                vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_TICK));
            }
        } else {
            Log.d(getClass().toString(), "Using legacy vibrator");
            vibrator.vibrate(new long[]{DO_NOT_WAIT, VIBRATION_TIME}, DO_NOT_REPEAT);
        }
    }
}
