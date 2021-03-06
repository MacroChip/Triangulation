package com.basementbrosdevelopers.triangulation;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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
import androidx.appcompat.app.AppCompatActivity;

import com.basementbrosdevelopers.triangulation.squareswapping.SelectedTriangleShrinkAnimation;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import static com.basementbrosdevelopers.triangulation.DialogsKt.showCredits;
import static com.basementbrosdevelopers.triangulation.DialogsKt.showGridlockedDialog;
import static com.basementbrosdevelopers.triangulation.DialogsKt.showInstructions;
import static com.basementbrosdevelopers.triangulation.DialogsKt.showNotEnoughEnergyDialog;

public class MainActivity extends AppCompatActivity {

    public static final int DO_NOT_WAIT = 0;
    public static final int VIBRATION_TIME = 15;
    public static final int DO_NOT_REPEAT = -1;
    private static final String SHARED_PREFERENCES_NAME = "game_state";
    private Gson gson = new Gson();
    private int animationDuration;
    private ViewGroup mainView;
    private Vibrator vibrator;
    private Scoreboard scoreboard;
    private Energy energy;
    private LocationMatrix locationMatrix;
    private SquareSwapModel squareSwapModel;
    private DialogInterface currentDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentDialog = showInstructions(this);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        mainView = findViewById(R.id.main);
        animationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);
        newGame();
    }

    @Override
    protected void onSaveInstanceState(@NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(getClass().getName(), "saving state");
        getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
                .edit()
                .putString(Scoreboard.SERIALIZATION_KEY, gson.toJson(scoreboard))
                .putString(Energy.SERIALIZATION_KEY, gson.toJson(energy))
                .putString(LocationMatrix.SERIALIZATION_KEY, gson.toJson(locationMatrix))
                .putString(SquareSwapModel.SERIALIZATION_KEY, gson.toJson(squareSwapModel))
                .apply();
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        scoreboard = gson.fromJson(sharedPreferences.getString(Scoreboard.SERIALIZATION_KEY, null), Scoreboard.class);
        energy = gson.fromJson(sharedPreferences.getString(Energy.SERIALIZATION_KEY, null), Energy.class);
        locationMatrix = gson.fromJson(sharedPreferences.getString(LocationMatrix.SERIALIZATION_KEY, null), LocationMatrix.class);
        squareSwapModel = gson.fromJson(sharedPreferences.getString(SquareSwapModel.SERIALIZATION_KEY, null), SquareSwapModel.class);
        Log.d(getClass().getName(), "Restored matrix: " + locationMatrix);
        redraw();
    }

    @Override
    protected void onDestroy() {
        if (currentDialog != null) {
            currentDialog.dismiss();
        }
        super.onDestroy();
    }

    private void newGame() {
        locationMatrix = new LocationMatrix();
        energy = new Energy();
        squareSwapModel = new SquareSwapModel();
        scoreboard = new Scoreboard();
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
            currentDialog = showInstructions(this);
            return true;
        }
        if (item.getItemId() == R.id.dump_matrix) {
            Log.d(getClass().getName(), locationMatrix.toString());
            return true;
        }
        if (item.getItemId() == R.id.give_energy) {
            energy.gainEnergy();
            redraw();
            return true;
        }
        if (item.getItemId() == R.id.credits) {
            currentDialog = showCredits(this);
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
        ImageView rightTriangle = createTriangle(frame, square.getRight(), y, x);
        shrinkIfSelected(y, x, leftTriangle, rightTriangle);
        linearLayout.addView(frame);
    }

    private void shrinkIfSelected(int y, int x, ImageView leftTriangle, ImageView rightTriangle) {
        if (squareSwapModel.getJOrigin() == y && squareSwapModel.getIOrigin() == x) {
            new SelectedTriangleShrinkAnimation(animationDuration, leftTriangle).play();
            new SelectedTriangleShrinkAnimation(animationDuration, rightTriangle).play();
        }
    }

    @NotNull
    private FrameLayout makeFrameLayout(int y, int x) {
        FrameLayout frame = new FrameLayout(this);
        if (squareSwapModel.getJOrigin() == y && squareSwapModel.getIOrigin() == x) {
            frame.setBackgroundResource(R.drawable.ic_triangleroundedsquareselect);
        }
        return frame;
    }

    private void createInvertedSquare(LinearLayout linearLayout, Square square, int y, int x) {
        FrameLayout frame = makeFrameLayout(y, x);
        ImageView leftTriangle = createTriangle(frame, square.getLeft(), y, x);
        leftTriangle.setRotation(90f);
        ImageView rightTriangle = createTriangle(frame, square.getRight(), y, x);
        rightTriangle.setRotation(-90f);
        shrinkIfSelected(y, x, leftTriangle, rightTriangle);
        linearLayout.addView(frame);
    }

    private ImageView createTriangle(ViewGroup parentView, int triangleValue, int y, int x) {
        ImageView triangle = new ImageView(this);
        triangle.setImageResource(GraphicsManager.getDrawableId(triangleValue));
        triangle.setOnLongClickListener(v -> {
            if (squareSwapModel.getJOrigin() == y && squareSwapModel.getIOrigin() == x) {
                squareSwapModel.clearOrigin();
                redraw();
            } else if (energy.canSwapSquares()) {
                squareSwapModel.setOrigin(y, x);
                redraw();
            } else {
                currentDialog = showNotEnoughEnergyDialog(this);
            }
            return true;
        });
        triangle.setOnClickListener(v -> {
            Log.d(MainActivity.this.getClass().toString(), "Clicking x: " + x + ", y: " + y);
            if (squareSwapModel.hasOriginSet()) {
                locationMatrix.swap(squareSwapModel.getJOrigin(), squareSwapModel.getIOrigin(), y, x);
                energy.loseEnergy();
                checkAfterSwapConditions(squareSwapModel.getJOrigin(), squareSwapModel.getIOrigin());
                squareSwapModel.clearOrigin();
            } else {
                locationMatrix.matrix[y][x].swap();
            }
            giveTapFeedback();
            checkAfterSwapConditions(y, x);
            redraw();
            if (locationMatrix.isInGridlock() && energy.getEnergy() < Energy.ENERGY_COST) {
                currentDialog = showGridlockedDialog(this, (dialog, button) -> newGame());
            }
        });
        parentView.addView(triangle);
        return triangle;
    }

    private void redraw() {
        setTitle("Energy: " + energy.getEnergy());
        mainView.removeAllViews();
        createView();
        Log.d(getClass().toString(), locationMatrix.toString());
    }

    private void checkAfterSwapConditions(int y, int x) {
        Log.d(getClass().getName(), "Checking score condition y: " + y + " x: " + x);
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
