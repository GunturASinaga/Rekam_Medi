package com.example.rekammedisapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rekammedisapp.data.response.AlergiItem
import com.example.rekammedisapp.databinding.ItemAlergiBinding

// Meruapkan adapter untuk recycler view yang menampilkan alergi

class AlergiAdapter(
    private val onItemClick: (AlergiItem) -> Unit
) : RecyclerView.Adapter<AlergiAdapter.AlergiViewHolder>() {

    private var alergis: List<AlergiItem> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlergiViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAlergiBinding.inflate(inflater, parent, false)
        return AlergiViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlergiViewHolder, position: Int) {
        val alergi = alergis[position]
        holder.bind(alergi)

        holder.itemView.setOnClickListener {
            onItemClick(alergi)
        }
    }

    override fun getItemCount(): Int {
        return alergis.size
    }

    fun setAlergis(alergis: List<AlergiItem>) {
        this.alergis = alergis
        notifyDataSetChanged()
    }

    inner class AlergiViewHolder(private val binding: ItemAlergiBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(alergi: AlergiItem) {
            binding.alergiNameTv.text = "• ${alergi.nama}"
        }
    }
}
