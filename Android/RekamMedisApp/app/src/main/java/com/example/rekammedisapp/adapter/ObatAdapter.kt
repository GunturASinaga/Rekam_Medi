package com.example.rekammedisapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rekammedisapp.data.response.AlergiItem
import com.example.rekammedisapp.data.response.DataItemObat
import com.example.rekammedisapp.databinding.ItemAlergiBinding
import com.example.rekammedisapp.databinding.ItemObatBinding

// Meruapkan adapter untuk recycler view yang menampilkan obat

class ObatAdapter(
    private val onItemClick: (DataItemObat) -> Unit
) : RecyclerView.Adapter<ObatAdapter.ObatViewHolder>() {

    private var obats: List<DataItemObat> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObatViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemObatBinding.inflate(inflater, parent, false)
        return ObatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ObatViewHolder, position: Int) {
        val obat = obats[position]
        holder.bind(obat)

        holder.itemView.setOnClickListener {
            onItemClick(obat)
        }
    }

    override fun getItemCount(): Int {
        return obats.size
    }

    fun setObats(obats: List<DataItemObat>) {
        this.obats = obats
        notifyDataSetChanged()
    }

    inner class ObatViewHolder(private val binding: ItemObatBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(obat: DataItemObat) {
            binding.obatNameTv.text = "• ${obat.namaObat}"
        }
    }
}
