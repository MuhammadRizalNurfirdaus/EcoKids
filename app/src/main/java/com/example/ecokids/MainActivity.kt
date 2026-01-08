package com.example.ecokids

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnAnimals).setOnClickListener {
            val intent = Intent(this, MateriActivity::class.java)
            intent.putExtra("TYPE", "ANIMALS")
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        findViewById<Button>(R.id.btnFruits).setOnClickListener {
            val intent = Intent(this, MateriActivity::class.java)
            intent.putExtra("TYPE", "FRUITS")
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        findViewById<Button>(R.id.btnQuiz).setOnClickListener {
            startActivity(Intent(this, QuizActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        findViewById<Button>(R.id.btnSettings).setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }
    override fun onResume() {
        super.onResume()
        val prefs = getSharedPreferences("EcoKidsPrefs", MODE_PRIVATE)
        if (prefs.getBoolean("SOUND_ENABLED", true)) {
            MusicManager.playMusic(this)
        }
    }
}