package com.example.ecokids

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MateriActivity : AppCompatActivity() {

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
        } else {
            tvTitle.text = getString(R.string.title_fruits)
            // tvTitle.setBackgroundColor(resources.getColor(R.color.kid_orange, theme)) // Removed background color for transparent
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
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
