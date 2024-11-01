package com.ibo.matchgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class GameoverActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gameover)

        val gameMode = intent.getIntExtra("Mode", 0)
        val totalScore = findViewById<TextView>(R.id.finalScore)
        totalScore.text = intent.getStringExtra("Score")

        val textV = findViewById<TextView>(R.id.textView)
        val win = intent.getBooleanExtra("Win", true)
        if (win){
            val textV = findViewById<TextView>(R.id.textView)
            textV.text = "Tebrikler!"
        }else{
            textV.text = "Oyun Bitti!"
        }

        val retryButton = findViewById<Button>(R.id.retryButton)
        retryButton.setOnClickListener{
            if (gameMode == 2){
                startActivity(Intent(this, Card2x2Activity::class.java))
                finish()
            }else if(gameMode == 4){
                startActivity(Intent(this, Card4x4Activity::class.java))
                finish()
            }else if(gameMode == 6){
                startActivity(Intent(this, Card6x6Activity::class.java))
                finish()
            }
        }
        val menuButton = findViewById<Button>(R.id.menuButton)
        menuButton.setOnClickListener{
            finish()
        }
    }
}