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

class DetailActivity : BaseActivity() {

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
        val tvName = findViewById<TextView>(R.id.tvDetailName)
        val tvSubtitle = findViewById<TextView>(R.id.tvDetailHabitat)
        val tvDesc = findViewById<TextView>(R.id.tvDetailDesc)

        tvName.text = name
        tvSubtitle.text = subtitle
        tvDesc.text = desc
        
        // --- VISIBILITY & COLOR LOGIC ---
        // Force Name to Black and Bold for visibility
        tvName.setTextColor(android.graphics.Color.BLACK)
        tvName.typeface = android.graphics.Typeface.DEFAULT_BOLD
        
        if (itemType == "ANIMALS") {
            // Animals: Habitat Black and Bold
            tvSubtitle.setTextColor(android.graphics.Color.BLACK)
            tvSubtitle.typeface = android.graphics.Typeface.DEFAULT_BOLD
        } else {
            // Fruits: Name Black (already set), Subtitle matches Fruit Color (e.g. Merah -> Red)
            
            // Extract color word (assuming subtitle format "Warna: [Color]")
            val colorVal = currentSubtitleValue.trim()
            val colorCode = getColorFromString(colorVal)
            
            tvSubtitle.setTextColor(colorCode)
            // Make it bold too for better readability
            tvSubtitle.typeface = android.graphics.Typeface.DEFAULT_BOLD
        }

        // Description always Black and Bold for readability
        tvDesc.setTextColor(android.graphics.Color.BLACK)
        tvDesc.typeface = android.graphics.Typeface.DEFAULT_BOLD

        if (imageResId != 0) {
            findViewById<ImageView>(R.id.imgDetail).setImageResource(imageResId)
        }
    }

    private fun getColorFromString(colorName: String): Int {
        return when (colorName.lowercase()) {
            "merah", "red" -> android.graphics.Color.RED
            "kuning", "yellow" -> android.graphics.Color.parseColor("#FFD700") // Gold/Yellow
            "hijau", "green" -> android.graphics.Color.parseColor("#006400") // Dark Green for visibility on green bg? 
            // Wait, bg is green. Dark Green text might be hard. Let's use darker green or keep it consistent.
            // Request: "sesuaikan dengan warna buah". 
            // If bg is light green, dark green text is OK.
            
            "ungu", "purple" -> android.graphics.Color.parseColor("#800080")
            "oranye", "orange", "jingga" -> android.graphics.Color.parseColor("#FF4500") // OrangeRed
            "biru", "blue" -> android.graphics.Color.BLUE
            "hitam", "black" -> android.graphics.Color.BLACK
            "putih", "white" -> android.graphics.Color.WHITE // Might be invisible on light bg
            "cokelat", "brown" -> android.graphics.Color.parseColor("#8B4513")
            else -> android.graphics.Color.BLACK // Default
        }
    }

    private val availableImages = listOf(
        R.drawable.img_kucing, R.drawable.img_anjing, R.drawable.img_sapi, R.drawable.img_kambing,
        R.drawable.img_ayam, R.drawable.img_ikan, R.drawable.img_burung, R.drawable.img_kelinci,
        R.drawable.img_singa, R.drawable.img_gajah,
        R.drawable.img_apel, R.drawable.img_pisang, R.drawable.img_jeruk, R.drawable.img_mangga,
        R.drawable.img_anggur, R.drawable.img_stroberi, R.drawable.img_semangka, R.drawable.img_nanas,
        R.drawable.img_pepaya, R.drawable.img_alpukat,
        R.drawable.logoecokids
    )

    private fun showImagePickerDialog(currentImage: Int, onSelected: (Int) -> Unit) {
        val dialog = android.app.Dialog(this)
        dialog.setContentView(R.layout.dialog_image_picker)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        
        dialog.window?.setLayout(
             (resources.displayMetrics.widthPixels * 0.9).toInt(),
             android.view.ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val rv = dialog.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rvImagePicker)
        rv.layoutManager = androidx.recyclerview.widget.GridLayoutManager(this, 3)
        rv.adapter = ImagePickerAdapter(this, availableImages) { selectedResId ->
            onSelected(selectedResId)
            dialog.dismiss()
        }
        
        dialog.findViewById<android.view.View>(R.id.btnCancelPicker).setOnClickListener {
            dialog.dismiss()
        }
        
        dialog.show()
    }

    private fun showEditDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_item, null)
        
        val etName = dialogView.findViewById<EditText>(R.id.etName)
        val etSubtitle = dialogView.findViewById<EditText>(R.id.etSubtitle)
        val etDescription = dialogView.findViewById<EditText>(R.id.etDescription)
        val tvSubtitleLabel = dialogView.findViewById<TextView>(R.id.tvSubtitleLabel)
        
        // Image Picker Logic
        val ivEditImage = dialogView.findViewById<ImageView>(R.id.ivEditImage)
        val btnChangeImage = dialogView.findViewById<View>(R.id.btnChangeImage)
        var selectedImageRes = imageRes

        if (selectedImageRes != 0) ivEditImage.setImageResource(selectedImageRes)
        
        btnChangeImage.setOnClickListener {
            showImagePickerDialog(selectedImageRes) { newImg ->
                selectedImageRes = newImg
                ivEditImage.setImageResource(newImg)
            }
        }
        
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
            
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

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
            val result = if (itemType == "ANIMALS") {
                dbHelper.updateAnimal(itemId, newName, newSubtitle, newDesc, selectedImageRes)
            } else {
                dbHelper.updateFruit(itemId, newName, newSubtitle, newDesc, selectedImageRes)
            }

            if (result > 0) {
                // Update tampilan
                currentName = newName
                currentSubtitleValue = newSubtitle
                currentDesc = newDesc
                
                val displaySubtitle = if (itemType == "ANIMALS") {
                    "Habitat: $newSubtitle"
                } else {
                    "Warna: $newSubtitle"
                }
                
                updateUI(newName, displaySubtitle, newDesc, selectedImageRes)
                imageRes = selectedImageRes // Persist changes
                
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
            val result = if (itemType == "ANIMALS") {
                dbHelper.deleteAnimal(itemId)
            } else {
                dbHelper.deleteFruit(itemId)
            }

            if (result > 0) {
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
