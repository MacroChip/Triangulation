package com.basementbrosdevelopers.triangulation.scoreboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.basementbrosdevelopers.triangulation.R

class ScoreboardActivity : AppCompatActivity() {
    companion object {
        @JvmStatic
        fun intent(context: Context): Intent {
            return Intent(context, ScoreboardActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.scoreboard)
        val scoreboardText = findViewById<TextView>(R.id.scoreboard)
        scoreboardText.text = "Sample text"
    }
}