package com.basementbrosdevelopers.triangulation;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RawRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    public static final int DO_NOT_WAIT = 0;
    public static final int VIBRATION_TIME = 15;
    public static final int DO_NOT_REPEAT = -1;
    private ViewGroup mainView;
    private Scoreboard scoreboard = new Scoreboard();
    private Energy energy = new Energy();
    private LocationMatrix locationMatrix;
    private Vibrator vibrator;
    private SquareSwapModel squareSwapModel = new SquareSwapModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showInstructions();
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        mainView = findViewById(R.id.main);
        newGame();
    }

    private void showInstructions() {
        new AlertDialog.Builder(this)
                .setTitle("Instructions")
                .setMessage("Goal: Make a square with four triangles of the same color.\nHow to play: Tap a triangle to swap it with its partner triangle. Long tap a square to start a swap with another square, then choose the destination square. Square swapping requires energy but triangle swapping does not.")
                .setPositiveButton("Start", null)
                .create()
                .show();
    }

    private void newGame() {
        locationMatrix = new LocationMatrix();
        energy = new Energy();
        squareSwapModel = new SquareSwapModel();
        scoreboard = new Scoreboard();
        Log.d(getClass().toString(), locationMatrix.toString());
        redraw();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.new_game) {
            newGame();
            return true;
        }
        if (item.getItemId() == R.id.instructions) {
            showInstructions();
            return true;
        }
        if (item.getItemId() == R.id.dump_matrix) {
            Log.d(getClass().getName(), locationMatrix.toString());
            return true;
        }
        return super.onOptionsItemSelected(item);
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
        FrameLayout frame = makeFrameLayout(y, x);
        ImageView leftTriangle = createTriangle(frame, square.getLeft(), y, x);
        leftTriangle.setRotation(180f);
        createTriangle(frame, square.getRight(), y, x);
        linearLayout.addView(frame);
    }

    @NotNull
    private FrameLayout makeFrameLayout(int y, int x) {
        FrameLayout frame = new FrameLayout(this);
        if (squareSwapModel.getJOrigin() == y && squareSwapModel.getIOrigin() == x) {
            frame.setBackgroundColor(Color.MAGENTA);
        }
        return frame;
    }

    private void createInvertedSquare(LinearLayout linearLayout, Square square, int y, int x) {
        FrameLayout frame = makeFrameLayout(y, x);
        ImageView leftTriangle = createTriangle(frame, square.getLeft(), y, x);
        leftTriangle.setRotation(90f);
        ImageView rightTriangle = createTriangle(frame, square.getRight(), y, x);
        rightTriangle.setRotation(-90f);
        linearLayout.addView(frame);
    }

    private ImageView createTriangle(ViewGroup parentView, int triangleValue, int y, int x) {
        ImageView triangle = new ImageView(this);
        triangle.setImageResource(GraphicsManager.getDrawableId(triangleValue));
        triangle.setOnLongClickListener(v -> {
            if (energy.canSwapSquares()) {
                squareSwapModel.setOrigin(y, x);
                redraw();
            } else {
                new AlertDialog.Builder(this)
                        .setTitle("Not enough energy")
                        .setMessage("Create more single color squares to gain energy.")
                        .setPositiveButton("Ok", null)
                        .create()
                        .show();
            }
            return true;
        });
        triangle.setOnClickListener(v -> {
            Log.d(MainActivity.this.getClass().toString(), "Clicking x: " + x + ", y: " + y);
            if (squareSwapModel.hasOriginSet()) {
                locationMatrix.swap(squareSwapModel.getJOrigin(), squareSwapModel.getIOrigin(), y, x);
                energy.loseEnergy();
                checkAfterSwapConditions(y, x);
                checkAfterSwapConditions(squareSwapModel.getJOrigin(), squareSwapModel.getIOrigin());
                squareSwapModel.clearOrigin();
                redraw();
                return;
            }
            giveTapFeedback();
            locationMatrix.matrix[y][x].swap();
            checkAfterSwapConditions(y, x);
            redraw();
            if (locationMatrix.isInGridlock()) { //TODO: Gridlock really only happens when energy is 0 as well
                showGridlockedDialog();
            }
        });
        parentView.addView(triangle);
        return triangle;
    }

    private void showGridlockedDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Game Over")
                .setMessage("Good game! There are no combinations of triangles that can lead to a scoring square.")
                .setPositiveButton(R.string.new_game, (dialog, which) -> newGame())
                .create()
                .show();
    }

    private void redraw() {
        setTitle("Energy: " + energy.getEnergy());
        mainView.removeAllViews();
        createView();
    }

    private void checkAfterSwapConditions(int y, int x) {
        boolean leftConditionMet = locationMatrix.checkLeftRhombus(y, x);
        if (leftConditionMet) {
            locationMatrix.replaceLeftRhombus(y, x);
            energy.gainEnergy();
            scoreboard.add();
        }
        boolean rightConditionMet = locationMatrix.checkRightRhombus(y, x);
        if (rightConditionMet) {
            locationMatrix.replaceRightRhombus(y, x);
            energy.gainEnergy();
            scoreboard.add();
        }
        if (leftConditionMet || rightConditionMet) {
            playSound(R.raw.ree);
        }
    }

    private void giveTapFeedback() {
        playSound(R.raw.woosh);
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

    private void playSound(@RawRes int soundId) {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, soundId);
        mediaPlayer.setOnCompletionListener(MediaPlayer::release);
        mediaPlayer.start();
    }
}
