package com.example.ecokids

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MateriAdapter(
    private val items: List<Any>,
    private val onClick: (Any) -> Unit
) : RecyclerView.Adapter<MateriAdapter.MateriViewHolder>() {

    class MateriViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgItem: ImageView = view.findViewById(R.id.imgItem)
        val tvName: TextView = view.findViewById(R.id.tvName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MateriViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_materi, parent, false)
        return MateriViewHolder(view)
    }

    override fun onBindViewHolder(holder: MateriViewHolder, position: Int) {
        val item = items[position]
        if (item is Animal) {
            holder.tvName.text = item.name
            // Using setImageResource for simplicity as requested, in real app prefer Glide/Coil
            if (item.imageResId != 0) {
                holder.imgItem.setImageResource(item.imageResId)
            } else {
                holder.imgItem.setImageResource(R.drawable.logoecokids) // Fallback
            }
        } else if (item is Fruit) {
            holder.tvName.text = item.name
            if (item.imageResId != 0) {
                holder.imgItem.setImageResource(item.imageResId)
            } else {
                holder.imgItem.setImageResource(R.drawable.logoecokids) // Fallback
            }
        }
        
        holder.itemView.setOnClickListener { onClick(item) }
    }

    override fun getItemCount() = items.size
}
