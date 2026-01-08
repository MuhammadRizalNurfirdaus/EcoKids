package com.example.ecokids

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class ImagePickerAdapter(
    private val context: Context,
    private val images: List<Int>,
    private val onImageSelected: (Int) -> Unit
) : RecyclerView.Adapter<ImagePickerAdapter.ImageViewHolder>() {

    class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivItemImage: ImageView = view.findViewById(R.id.ivItemImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_image_picker, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageResId = images[position]
        holder.ivItemImage.setImageResource(imageResId)
        
        holder.itemView.setOnClickListener {
            onImageSelected(imageResId)
        }
    }

    override fun getItemCount(): Int = images.size
}
