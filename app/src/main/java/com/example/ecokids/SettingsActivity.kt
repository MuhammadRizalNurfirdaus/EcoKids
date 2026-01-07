package com.example.ecokids

import android.app.AlertDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences
    private lateinit var tvParentStatus: TextView
    private lateinit var btnParentMode: Button

    companion object {
        const val PREF_NAME = "EcoKidsPrefs"
        const val KEY_SOUND_ENABLED = "SOUND_ENABLED"
        const val KEY_PARENT_MODE = "PARENT_MODE_ACTIVE"
        const val DEFAULT_PIN = "1234"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE)

        val switchSound = findViewById<Switch>(R.id.switchSound)
        tvParentStatus = findViewById(R.id.tvParentStatus)
        btnParentMode = findViewById(R.id.btnParentMode)

        findViewById<android.widget.ImageButton>(R.id.btnBack).setOnClickListener {
            finish()
        }

        // Sound settings
        switchSound.isChecked = prefs.getBoolean(KEY_SOUND_ENABLED, true)
        switchSound.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean(KEY_SOUND_ENABLED, isChecked).apply()

            MusicManager.isMusicEnabled = isChecked
            if (isChecked) {
                MusicManager.playMusic(this)
            } else {
                MusicManager.pauseMusic()
            }

            val msg = if (isChecked) "Suara ON" else "Suara OFF"
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }

        // Parent Mode
        updateParentModeUI()

        btnParentMode.setOnClickListener {
            val isParentMode = prefs.getBoolean(KEY_PARENT_MODE, false)
            if (isParentMode) {
                // Logout
                prefs.edit().putBoolean(KEY_PARENT_MODE, false).apply()
                updateParentModeUI()
                Toast.makeText(this, getString(R.string.logout_success), Toast.LENGTH_SHORT).show()
            } else {
                // Show PIN dialog
                showPinDialog()
            }
        }
    }

    private fun updateParentModeUI() {
        val isParentMode = prefs.getBoolean(KEY_PARENT_MODE, false)
        if (isParentMode) {
            tvParentStatus.text = getString(R.string.parent_mode_active)
            btnParentMode.text = getString(R.string.btn_logout_parent)
            btnParentMode.setBackgroundResource(R.drawable.bg_button_delete)
        } else {
            tvParentStatus.text = getString(R.string.parent_mode_inactive)
            btnParentMode.text = getString(R.string.btn_login_parent)
            btnParentMode.setBackgroundResource(R.drawable.bg_button_edit)
        }
    }

    private fun showPinDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_pin, null)
        val etPin = dialogView.findViewById<EditText>(R.id.etPin)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        dialogView.findViewById<Button>(R.id.btnCancelPin).setOnClickListener {
            dialog.dismiss()
        }

        dialogView.findViewById<Button>(R.id.btnConfirmPin).setOnClickListener {
            val enteredPin = etPin.text.toString()

            if (enteredPin == DEFAULT_PIN) {
                // PIN correct
                prefs.edit().putBoolean(KEY_PARENT_MODE, true).apply()
                updateParentModeUI()
                Toast.makeText(this, getString(R.string.pin_success), Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            } else {
                // PIN wrong
                Toast.makeText(this, getString(R.string.pin_wrong), Toast.LENGTH_SHORT).show()
                etPin.text.clear()
            }
        }

        dialog.show()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
