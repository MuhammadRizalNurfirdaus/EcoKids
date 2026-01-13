package com.example.ecokids

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    override fun onResume() {
        super.onResume()
        // Cancel scheduled pause and resume music
        // We only play if SOUND_ENABLED is true. MusicManager.playMusic handles this check too, 
        // but checking prefs here avoids unnecessary calls if we want.
        // However, MusicManager has 'isMusicEnabled' property but it might not be sync with Prefs on app restart.
        // So we sync it.
        val prefs = getSharedPreferences("EcoKidsPrefs", MODE_PRIVATE)
        val isSoundEnabled = prefs.getBoolean("SOUND_ENABLED", true)
        MusicManager.isMusicEnabled = isSoundEnabled
        
        if (isSoundEnabled) {
            MusicManager.playMusic(this)
        }
    }

    override fun onPause() {
        super.onPause()
        // Schedule pause when leaving activity
        MusicManager.schedulePause()
    }
}
