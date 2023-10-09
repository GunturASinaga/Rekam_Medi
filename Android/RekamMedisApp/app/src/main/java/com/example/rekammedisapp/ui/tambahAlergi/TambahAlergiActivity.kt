package com.example.rekammedisapp.ui.tambahAlergi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.dicoding.temantani.db.UserAuth
import com.dicoding.temantani.db.UserPreference
import com.example.rekammedisapp.data.response.AlergiItem
import com.example.rekammedisapp.data.response.PenyakitItem
import com.example.rekammedisapp.database.alergi.Alergi
import com.example.rekammedisapp.database.penyakit.Penyakit
import com.example.rekammedisapp.databinding.ActivityTambahAlergiBinding
import com.example.rekammedisapp.model.AlergiAPIViewModel
import com.example.rekammedisapp.model.AlergiViewModel
import com.example.rekammedisapp.model.HomeViewModel
import com.example.rekammedisapp.model.PenyakitViewModel
import com.example.rekammedisapp.model.ViewModelFactory
import com.example.rekammedisapp.ui.home.HomeActivity

// Activity untuk menampilkan detail dan juga field yang dibutuhkan untuk melakukan penambahan data penyakit

class TambahAlergiActivity : AppCompatActivity() {
    private lateinit var binding : ActivityTambahAlergiBinding
    private lateinit var alergiViewModel: AlergiViewModel
    private lateinit var alergiAPIViewModel: AlergiAPIViewModel
    private lateinit var userAuth : UserAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahAlergiBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val userPref = UserPreference(this)
        userAuth = userPref.getUser()

        alergiAPIViewModel = obtainViewModel(this)

        val alergi: AlergiItem? = intent.getParcelableExtra("alergi")

        if(alergi != null){
            binding.judul.text = "Edit Alergi"
            binding.namaAlergiEd.setText(alergi.nama)
        }

        alergiViewModel = obtainViewModelAlergi(this)

        // aksi ketika simpan perubahan dilakukan, akan mengecek ke alergi API dan melakukan perubahan datan
        binding.simpanPerubahanBtn.setOnClickListener{
            val nama = binding.namaAlergiEd.text.toString()
            if (alergi != null) {
                // Untuk  mengedit
                alergi.id?.let { it1 -> alergiAPIViewModel.editAlergi(it1, "Bearer ${userAuth.token}", nama) }
            } else {
                // Insert a new alergi object
                alergiAPIViewModel.tambahAlergi("Bearer ${userAuth.token}", nama)
            }
            startActivity(Intent(this@TambahAlergiActivity, HomeActivity::class.java))
        }

        // Berhasil menambah alergi
        alergiAPIViewModel.alergiResponse.observe(this) { response ->
            if (response.status == "success") {
                response.message?.let { Log.e("asd", "${response}") }
                Toast.makeText(this, "Berhasil aksi", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@TambahAlergiActivity, HomeActivity::class.java))
            }
        }

        // ketika tomboh hapus ditekan
        binding.deletePenyakitBtn.setOnClickListener{
            if(alergi != null){
                alergi.id?.let { it1 -> alergiAPIViewModel.hapusAlergi(it1, "Bearer ${userAuth.token}") }
                Toast.makeText(this, "Data Alergi Dihapus", Toast.LENGTH_SHORT).show()
            }
            startActivity(Intent(this@TambahAlergiActivity, HomeActivity::class.java))
        }
    }

    private fun obtainViewModelAlergi(activity: AppCompatActivity): AlergiViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(AlergiViewModel::class.java)
    }
    private fun obtainViewModel(activity: AppCompatActivity): AlergiAPIViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(AlergiAPIViewModel::class.java)
    }
}