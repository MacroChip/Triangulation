package com.basementbrosdevelopers.triangulation

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

fun showNotEnoughEnergyDialog(context: Context): DialogInterface {
    val currentDialog = AlertDialog.Builder(context)
            .setTitle("Not enough energy")
            .setMessage("You need " + Energy.ENERGY_COST + " energy to swap squares. Create more single color squares to gain energy.")
            .setPositiveButton("Ok", null)
            .create()
    currentDialog.show()
    return currentDialog
}

fun showGridlockedDialog(context: Context, okAction: DialogInterface.OnClickListener): DialogInterface {
    val currentDialog = AlertDialog.Builder(context)
            .setTitle("Game Over")
            .setMessage("Good game! There are no combinations of triangles that can lead to a scoring square.")
            .setPositiveButton(R.string.new_game, okAction)
            .create()
    currentDialog.show()
    return currentDialog
}

fun showInstructions(context: Context): DialogInterface {
    val currentDialog = AlertDialog.Builder(context)
            .setTitle("Instructions")
            .setMessage("Goal: Make a square with four triangles of the same color.\nHow to play: Tap a triangle to swap it with its partner triangle. Long tap a square to start a swap with another square, then choose the destination square. Square swapping requires energy but triangle swapping does not.")
            .setPositiveButton("Start", null)
            .create()
    currentDialog.show()
    return currentDialog
}
