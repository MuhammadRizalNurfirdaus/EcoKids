package com.example.ecokids

import android.content.Context
import android.media.MediaPlayer

object MusicManager {
    private var mediaPlayer: MediaPlayer? = null
    var isMusicEnabled = true

    fun playMusic(context: Context) {
        if (!isMusicEnabled) return
        
        if (mediaPlayer == null) {
            try {
                // Ensure R.raw.bgm_ecokids exists.
                mediaPlayer = MediaPlayer.create(context, R.raw.bgm_ecokids)
                
                if (mediaPlayer == null) {
                    android.widget.Toast.makeText(context, "Gagal memuat lagu (null)", android.widget.Toast.LENGTH_SHORT).show()
                    return
                }

                // Ensure it uses the music stream for volume control
                val audioAttributes = android.media.AudioAttributes.Builder()
                    .setUsage(android.media.AudioAttributes.USAGE_MEDIA)
                    .setContentType(android.media.AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
                mediaPlayer?.setAudioAttributes(audioAttributes)
                
                // Set volume to max relative to system volume
                mediaPlayer?.setVolume(1.0f, 1.0f)
                mediaPlayer?.isLooping = true
            } catch (e: Exception) {
                e.printStackTrace()
                android.widget.Toast.makeText(context, "Error init: ${e.message}", android.widget.Toast.LENGTH_SHORT).show()
            }
        }

        if (mediaPlayer?.isPlaying == false) {
            try {
                mediaPlayer?.start()
            } catch (e: Exception) {
                e.printStackTrace()
                android.widget.Toast.makeText(context, "Gagal memutar: ${e.message}", android.widget.Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun pauseMusic() {
        if (mediaPlayer?.isPlaying == true) {
            mediaPlayer?.pause()
        }
    }

    fun stopMusic() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
