package com.example.ecokids

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    
    private var itemId: Int = 0
    private var itemType: String = ""
    private var currentName: String = ""
    private var currentSubtitleValue: String = ""
    private var currentDesc: String = ""
    private var imageRes: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        dbHelper = DatabaseHelper(this)

        // Ambil data dari intent
        itemId = intent.getIntExtra("ID", 0)
        itemType = intent.getStringExtra("TYPE") ?: ""
        currentName = intent.getStringExtra("NAME") ?: ""
        currentSubtitleValue = intent.getStringExtra("SUBTITLE_VALUE") ?: ""
        currentDesc = intent.getStringExtra("DESC") ?: ""
        imageRes = intent.getIntExtra("IMAGE", 0)
        
        val subtitle = intent.getStringExtra("SUBTITLE") ?: ""

        // Set tampilan
        findViewById<android.widget.ImageButton>(R.id.btnBack).setOnClickListener {
            finish()
        }

        updateUI(currentName, subtitle, currentDesc, imageRes)

        // Cek apakah Mode Guru aktif
        val prefs = getSharedPreferences("EcoKidsPrefs", MODE_PRIVATE)
        val isParentMode = prefs.getBoolean("PARENT_MODE_ACTIVE", false)

        val btnEdit = findViewById<Button>(R.id.btnEdit)
        val btnDelete = findViewById<Button>(R.id.btnDelete)
        val buttonsLayout = findViewById<LinearLayout>(R.id.layoutButtons)

        if (isParentMode) {
            // Mode Guru aktif - tampilkan tombol Edit & Hapus
            buttonsLayout.visibility = View.VISIBLE
            
            btnEdit.setOnClickListener {
                showEditDialog()
            }

            btnDelete.setOnClickListener {
                showDeleteDialog()
            }
        } else {
            // Mode anak - sembunyikan tombol Edit & Hapus
            buttonsLayout.visibility = View.GONE
        }
    }

    private fun updateUI(name: String, subtitle: String, desc: String, imageResId: Int) {
        findViewById<TextView>(R.id.tvDetailName).text = name
        findViewById<TextView>(R.id.tvDetailHabitat).text = subtitle
        findViewById<TextView>(R.id.tvDetailDesc).text = desc
        
        if (imageResId != 0) {
            findViewById<ImageView>(R.id.imgDetail).setImageResource(imageResId)
        }
    }

    private fun showEditDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_item, null)
        
        val etName = dialogView.findViewById<EditText>(R.id.etName)
        val etSubtitle = dialogView.findViewById<EditText>(R.id.etSubtitle)
        val etDescription = dialogView.findViewById<EditText>(R.id.etDescription)
        val tvSubtitleLabel = dialogView.findViewById<TextView>(R.id.tvSubtitleLabel)
        
        // Set label berdasarkan type
        if (itemType == "ANIMALS") {
            tvSubtitleLabel.text = "🏠 Habitat"
            etSubtitle.hint = getString(R.string.hint_habitat)
        } else {
            tvSubtitleLabel.text = "🎨 Warna"
            etSubtitle.hint = getString(R.string.hint_color)
        }
        
        // Isi data yang ada
        etName.setText(currentName)
        etSubtitle.setText(currentSubtitleValue)
        etDescription.setText(currentDesc)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        dialogView.findViewById<Button>(R.id.btnCancel).setOnClickListener {
            dialog.dismiss()
        }

        dialogView.findViewById<Button>(R.id.btnSave).setOnClickListener {
            val newName = etName.text.toString().trim()
            val newSubtitle = etSubtitle.text.toString().trim()
            val newDesc = etDescription.text.toString().trim()

            if (newName.isEmpty() || newSubtitle.isEmpty() || newDesc.isEmpty()) {
                Toast.makeText(this, "Semua field harus diisi ya! 📝", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Update ke database
            val success = if (itemType == "ANIMALS") {
                dbHelper.updateAnimal(itemId, newName, newSubtitle, newDesc)
            } else {
                dbHelper.updateFruit(itemId, newName, newSubtitle, newDesc)
            }

            if (success) {
                // Update tampilan
                currentName = newName
                currentSubtitleValue = newSubtitle
                currentDesc = newDesc
                
                val displaySubtitle = if (itemType == "ANIMALS") {
                    "Habitat: $newSubtitle"
                } else {
                    "Warna: $newSubtitle"
                }
                
                updateUI(newName, displaySubtitle, newDesc, imageRes)
                Toast.makeText(this, getString(R.string.toast_saved), Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Gagal menyimpan 😢", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }

    private fun showDeleteDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_confirm_delete, null)
        
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        dialogView.findViewById<Button>(R.id.btnNo).setOnClickListener {
            dialog.dismiss()
        }

        dialogView.findViewById<Button>(R.id.btnYesDelete).setOnClickListener {
            // Hapus dari database
            val success = if (itemType == "ANIMALS") {
                dbHelper.deleteAnimal(itemId)
            } else {
                dbHelper.deleteFruit(itemId)
            }

            if (success) {
                Toast.makeText(this, getString(R.string.toast_deleted), Toast.LENGTH_SHORT).show()
                dialog.dismiss()
                // Kembali ke halaman sebelumnya
                setResult(RESULT_OK)
                finish()
            } else {
                Toast.makeText(this, "Gagal menghapus 😢", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
