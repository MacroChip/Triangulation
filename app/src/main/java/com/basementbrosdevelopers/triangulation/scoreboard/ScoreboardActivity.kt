package com.basementbrosdevelopers.triangulation.scoreboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.basementbrosdevelopers.triangulation.R
import com.basementbrosdevelopers.triangulation.Scoreboard

class ScoreboardActivity : AppCompatActivity() {
    companion object {
        @JvmStatic
        fun intent(context: Context, scoreboard: Scoreboard): Intent {
            return Intent(context, ScoreboardActivity::class.java)
                    .putExtra(Scoreboard.SERIALIZATION_KEY, scoreboard)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.scoreboard)
        val scoreboard = intent.getSerializableExtra(Scoreboard.SERIALIZATION_KEY) as Scoreboard
        findViewById<TextView>(R.id.score).text = "High Score: ${scoreboard.hiScore}"
        findViewById<TextView>(R.id.energy_cost).text = "Energy cost per swap for high score: "
        findViewById<TextView>(R.id.energy_gain).text = "Energy gain per score for high score: "
    }
}
