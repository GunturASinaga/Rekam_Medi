package com.example.rekammedisapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rekammedisapp.data.response.PenyakitItem
import com.example.rekammedisapp.databinding.ItemPenyakitBinding

// Meruapkan adapter untuk recycler view yang menampilkan penyakit

class PenyakitAdapter(
    private val onItemClick: (PenyakitItem) -> Unit
) : RecyclerView.Adapter<PenyakitAdapter.PenyakitViewHolder>() {

    private var penyakitItems: List<PenyakitItem> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PenyakitViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPenyakitBinding.inflate(inflater, parent, false)
        return PenyakitViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PenyakitViewHolder, position: Int) {
        val penyakitItem = penyakitItems[position]
        holder.bind(penyakitItem)

        holder.itemView.setOnClickListener {
            onItemClick(penyakitItem)
        }
    }

    override fun getItemCount(): Int {
        return penyakitItems.size
    }

    fun setPenyakitItems(penyakitItems: List<PenyakitItem>) {
        this.penyakitItems = penyakitItems
        notifyDataSetChanged()
    }

    inner class PenyakitViewHolder(private val binding: ItemPenyakitBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(penyakitItem: PenyakitItem) {
            binding.penyakitNameTv.text = penyakitItem.nama
            if (penyakitItem.tanggalSelesai.isNullOrEmpty()) {
                binding.duration.text = "${penyakitItem.tanggalMulai} - sekarang"
            } else {
                binding.duration.text = "${penyakitItem.tanggalMulai} - ${penyakitItem.tanggalSelesai}"
            }
        }
    }
}
