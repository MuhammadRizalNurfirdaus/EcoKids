package com.example.ecokids

import android.content.Context
import android.media.MediaPlayer

object MusicManager {
    private var mediaPlayer: MediaPlayer? = null
    var isMusicEnabled = true
    private val handler = android.os.Handler(android.os.Looper.getMainLooper())
    private val pauseRunnable = Runnable { pauseMusic() }

    fun playMusic(context: Context) {
        // Cancel any scheduled pause since we are resuming/playing
        handler.removeCallbacks(pauseRunnable)

        if (!isMusicEnabled) return

        if (mediaPlayer == null) {
            try {
                mediaPlayer = MediaPlayer.create(context, R.raw.bgm_ecokids)
                
                if (mediaPlayer == null) {
                    return
                }

                // setAudioAttributes called AFTER create() causes "State 8" error because it's already PREPARED.
                // We skip it as create() defaults to MUSIC stream which is what we want.
                
                mediaPlayer?.setVolume(0.5f, 0.5f) // Slightly lower volume for BGM
                mediaPlayer?.isLooping = true
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        try {
            if (mediaPlayer?.isPlaying == false) {
                mediaPlayer?.start()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun pauseMusic() {
        try {
            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer?.pause()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun schedulePause() {
        // Delay pause to allow Activity transition without stopping music
        // Increased delay to 3s because emulator/low-end devices might lag during transition
        handler.removeCallbacks(pauseRunnable)
        handler.postDelayed(pauseRunnable, 3000) 
    }

    fun stopMusic() {
        handler.removeCallbacks(pauseRunnable)
        try {
            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer?.stop()
            }
            mediaPlayer?.release()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        mediaPlayer = null
    }
}
