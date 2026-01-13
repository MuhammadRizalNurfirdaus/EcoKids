package com.example.ecokids

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MateriActivity : BaseActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var tvTitle: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_materi)

        dbHelper = DatabaseHelper(this)
        recyclerView = findViewById(R.id.recyclerView)
        tvTitle = findViewById(R.id.tvTitle)
        
        recyclerView.layoutManager = LinearLayoutManager(this)

        findViewById<android.widget.ImageButton>(R.id.btnBack).setOnClickListener {
            finish()
        }

        val type = intent.getStringExtra("TYPE")
        
        if (type == "ANIMALS") {
            tvTitle.text = getString(R.string.title_animals)
            // tvTitle.setBackgroundColor(resources.getColor(R.color.kid_primary, theme)) // Removed background color for transparent
        } else {
            tvTitle.text = getString(R.string.title_fruits)
            // tvTitle.setBackgroundColor(resources.getColor(R.color.kid_orange, theme)) // Removed background color for transparent
            loadFruits()
        }

        // Parent Mode Check for Add Button
        val prefs = getSharedPreferences("EcoKidsPrefs", MODE_PRIVATE)
        val isParentMode = prefs.getBoolean("PARENT_MODE_ACTIVE", false)
        val btnAdd = findViewById<android.widget.ImageButton>(R.id.btnAdd)

        if (isParentMode) {
            btnAdd.visibility = android.view.View.VISIBLE
            btnAdd.setOnClickListener {
                showAddDialog(type ?: "")
            }
        } else {
            btnAdd.visibility = android.view.View.GONE
        }
    }

    private fun loadAnimals() {
        val animals = dbHelper.getAllAnimals()
        recyclerView.adapter = MateriAdapter(animals) { item ->
            val animal = item as Animal
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("ID", animal.id)
            intent.putExtra("TYPE", "ANIMALS")
            intent.putExtra("NAME", animal.name)
            intent.putExtra("SUBTITLE", "Habitat: ${animal.habitat}")
            intent.putExtra("SUBTITLE_VALUE", animal.habitat)
            intent.putExtra("DESC", animal.description)
            intent.putExtra("IMAGE", animal.imageResId)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun loadFruits() {
        val fruits = dbHelper.getAllFruits()
        recyclerView.adapter = MateriAdapter(fruits) { item ->
            val fruit = item as Fruit
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("ID", fruit.id)
            intent.putExtra("TYPE", "FRUITS")
            intent.putExtra("NAME", fruit.name)
            intent.putExtra("SUBTITLE", "Warna: ${fruit.color}")
            intent.putExtra("SUBTITLE_VALUE", fruit.color)
            intent.putExtra("DESC", fruit.description)
            intent.putExtra("IMAGE", fruit.imageResId)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }
    
    // Refresh list when returning from DetailActivity
    override fun onResume() {
        super.onResume()
        val type = intent.getStringExtra("TYPE")
        if (type == "ANIMALS") {
            loadAnimals()
        } else {
            loadFruits()
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
        
        // Adjust width standard
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

    private fun showAddDialog(type: String) {
        val dialogView = android.view.LayoutInflater.from(this).inflate(R.layout.dialog_edit_item, null)
        val etName = dialogView.findViewById<android.widget.EditText>(R.id.etName)
        val etSubtitle = dialogView.findViewById<android.widget.EditText>(R.id.etSubtitle)
        val etDescription = dialogView.findViewById<android.widget.EditText>(R.id.etDescription)
        val tvSubtitleLabel = dialogView.findViewById<android.widget.TextView>(R.id.tvSubtitleLabel)
        val tvTitleDialog = dialogView.findViewById<android.widget.TextView>(R.id.tvDialogTitle) // Assuming ID exists or is default Dialog Title but layout uses custom

        // Check layout file dialog_edit_item.xml if it has title TextView. Usually it does.
        // Assuming dialog_edit_item.xml structure based on previous edits.
        
        // Image Picker Logic
        val ivEditImage = dialogView.findViewById<android.widget.ImageView>(R.id.ivEditImage)
        val btnChangeImage = dialogView.findViewById<android.view.View>(R.id.btnChangeImage)
        var selectedImageResId = R.drawable.logoecokids // Default
        
        btnChangeImage.setOnClickListener {
            showImagePickerDialog(selectedImageResId) { newImageId ->
                selectedImageResId = newImageId
                ivEditImage.setImageResource(newImageId)
            }
        }
        
        // Adjust UI
        dialogView.findViewById<android.widget.Button>(R.id.btnSave).text = "Tambah"
        
        if (type == "ANIMALS") {
            tvSubtitleLabel.text = "🏠 Habitat"
            etSubtitle.hint = getString(R.string.hint_habitat)
        } else {
            tvSubtitleLabel.text = "🎨 Warna"
            etSubtitle.hint = getString(R.string.hint_color)
        }

        val dialog = android.app.AlertDialog.Builder(this)
            .setView(dialogView)
            .create()
            
        // Transparent background for dialog rounded corners
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        dialogView.findViewById<android.widget.Button>(R.id.btnCancel).setOnClickListener {
            dialog.dismiss()
        }

        dialogView.findViewById<android.widget.Button>(R.id.btnSave).setOnClickListener {
            val name = etName.text.toString().trim()
            val subtitle = etSubtitle.text.toString().trim()
            val desc = etDescription.text.toString().trim()

            if (name.isEmpty() || subtitle.isEmpty() || desc.isEmpty()) {
                android.widget.Toast.makeText(this, "Semua field harus diisi ya! 📝", android.widget.Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            var result: Long = -1
            if (type == "ANIMALS") {
                result = dbHelper.addAnimal(name, subtitle, desc, selectedImageResId)
            } else {
                result = dbHelper.addFruit(name, subtitle, desc, selectedImageResId)
            }

            if (result > -1) {
                android.widget.Toast.makeText(this, "Berhasil menambah data! 🎉", android.widget.Toast.LENGTH_SHORT).show()
                dialog.dismiss()
                onResume() // Refresh list
            } else {
                android.widget.Toast.makeText(this, "Gagal menambah data 😢", android.widget.Toast.LENGTH_SHORT).show()
            }
        }
        dialog.show()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
