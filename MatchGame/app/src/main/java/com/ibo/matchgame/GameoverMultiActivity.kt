package com.ibo.matchgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class GameoverMultiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gameover_multi)

        val gameMode = intent.getIntExtra("Mode", 0)
        val totalScore = findViewById<TextView>(R.id.finalScore)
        totalScore.text = intent.getStringExtra("Score1")
        val totalScore2 = findViewById<TextView>(R.id.finalScore2)
        totalScore2.text = intent.getStringExtra("Score2")

        val win = intent.getIntExtra("Win", 0)
        if (win == 1){
            findViewById<TextView>(R.id.textView).text = "1. Oyuncu Kazandı!"
        }else if (win == 2){
            findViewById<TextView>(R.id.textView).text = "2. Oyuncu Kazandı!"
        }else if (win == 3){
            findViewById<TextView>(R.id.textView).text = "Berabere!"
        }

        val retryButton = findViewById<Button>(R.id.retryButton)
        retryButton.setOnClickListener{
            if (gameMode == 2){
                startActivity(Intent(this, Card2x2MultiActivity::class.java))
                finish()
            }else if(gameMode == 4){
                startActivity(Intent(this, Card4x4MultiActivity::class.java))
                finish()
            }else if(gameMode == 6){
                startActivity(Intent(this, Card6x6MultiActivity::class.java))
                finish()
            }
        }
        val menuButton = findViewById<Button>(R.id.menuButton)
        menuButton.setOnClickListener{
            finish()
        }
    }
}