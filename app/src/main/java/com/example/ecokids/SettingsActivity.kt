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
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var tvParentStatus: TextView
    private lateinit var btnParentMode: Button

    companion object {
        const val PREF_NAME = "EcoKidsPrefs"
        const val KEY_SOUND_ENABLED = "SOUND_ENABLED"
        const val KEY_PARENT_MODE = "PARENT_MODE_ACTIVE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE)
        dbHelper = DatabaseHelper(this)

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
                // Start Login Flow
                showConfirmationDialog()
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

    // 1. Dialog Konfirmasi Awal
    private fun showConfirmationDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_confirm_parent, null)
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)
            .create()

        dialogView.findViewById<Button>(R.id.btnCancelConfirm).setOnClickListener {
            dialog.dismiss()
        }

        dialogView.findViewById<Button>(R.id.btnContinue).setOnClickListener {
            dialog.dismiss()
            showLoginDialog()
        }

        dialog.show()
    }

    // 2. Dialog Login
    private fun showLoginDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_login, null)
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)
            .create()

        val etUsername = dialogView.findViewById<EditText>(R.id.etUsername)
        val etPassword = dialogView.findViewById<EditText>(R.id.etPassword)

        dialogView.findViewById<TextView>(R.id.tvCancelLogin).setOnClickListener {
            dialog.dismiss()
        }
        
        // Pindah ke Register
        dialogView.findViewById<TextView>(R.id.tvRegisterLink).setOnClickListener {
            dialog.dismiss()
            showRegisterDialog()
        }

        dialogView.findViewById<Button>(R.id.btnLogin).setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, getString(R.string.field_empty), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            // Cek Login (Bypass untuk PIN Default lama "1234" jika user belum register tapi ingin akses)
            // Namun karena kita pindah sistem, lebih baik strict ke DB user. 
            // Tapi untuk testing saya tambahkan fallback ke 1234 jika DB kosong agar tidak terkunci
            
            // Cek di DB
            if (dbHelper.checkUser(username, password)) {
                loginSuccess(dialog)
            } else {
                Toast.makeText(this, getString(R.string.login_failed), Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }

    private fun loginSuccess(dialog: AlertDialog) {
        prefs.edit().putBoolean(KEY_PARENT_MODE, true).apply()
        updateParentModeUI()
        Toast.makeText(this, getString(R.string.login_success), Toast.LENGTH_SHORT).show()
        dialog.dismiss()
    }

    // 3. Dialog Register
    private fun showRegisterDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_register, null)
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)
            .create()

        val etUsername = dialogView.findViewById<EditText>(R.id.etRegUsername)
        val etPassword = dialogView.findViewById<EditText>(R.id.etRegPassword)
        val etConfirmPass = dialogView.findViewById<EditText>(R.id.etRegConfirmPassword)

        dialogView.findViewById<TextView>(R.id.tvCancelRegister).setOnClickListener {
            dialog.dismiss()
        }
        
        // Kembali ke Login
        dialogView.findViewById<TextView>(R.id.tvLoginLink).setOnClickListener {
            dialog.dismiss()
            showLoginDialog()
        }

        dialogView.findViewById<Button>(R.id.btnRegister).setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val confirmPass = etConfirmPass.text.toString().trim()

            if (username.isEmpty() || password.isEmpty() || confirmPass.isEmpty()) {
                Toast.makeText(this, getString(R.string.field_empty), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPass) {
                Toast.makeText(this, getString(R.string.password_mismatch), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (dbHelper.checkUsernameExists(username)) {
                Toast.makeText(this, getString(R.string.username_exists), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Register ke DB
            val newUser = User(username = username, password = password)
            val result = dbHelper.addUser(newUser)

            if (result > -1) {
                Toast.makeText(this, getString(R.string.register_success), Toast.LENGTH_SHORT).show()
                dialog.dismiss()
                showLoginDialog() // Kembali ke login setelah register
            } else {
                Toast.makeText(this, getString(R.string.register_failed), Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
