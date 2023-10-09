package com.example.rekammedisapp.ui.tambahObat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.dicoding.temantani.db.UserAuth
import com.dicoding.temantani.db.UserPreference
import com.example.rekammedisapp.data.response.AlergiItem
import com.example.rekammedisapp.data.response.DataItemObat
import com.example.rekammedisapp.data.response.PenyakitItem
import com.example.rekammedisapp.database.alergi.Alergi
import com.example.rekammedisapp.database.penyakit.Penyakit
import com.example.rekammedisapp.databinding.ActivityTambahAlergiBinding
import com.example.rekammedisapp.databinding.ActivityTambahObatBinding
import com.example.rekammedisapp.model.AlergiAPIViewModel
import com.example.rekammedisapp.model.AlergiViewModel
import com.example.rekammedisapp.model.HomeViewModel
import com.example.rekammedisapp.model.ObatViewModel
import com.example.rekammedisapp.model.PenyakitViewModel
import com.example.rekammedisapp.model.ViewModelFactory
import com.example.rekammedisapp.ui.home.HomeActivity
import com.example.rekammedisapp.ui.riwayatPenyakit.RiwayatPenyakitActivity

// Acitivity untuk mengatur tampilan pada saat menamba obat

class TambahObatActivity : AppCompatActivity() {
    private lateinit var binding : ActivityTambahObatBinding
    private lateinit var alergiViewModel: AlergiViewModel
    private lateinit var obatviewModel: ObatViewModel
    private lateinit var userAuth : UserAuth
    private var idPenyakit : Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahObatBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //  mengambil token dan infomrasi mengenai user
        val userPref = UserPreference(this)
        userAuth = userPref.getUser()

        obatviewModel = obtainViewModel(this)

        val obat: DataItemObat? = intent.getParcelableExtra("obat")
        idPenyakit = intent.getIntExtra("idPenyakit", -1)

        if(obat != null){
            binding.judul.text = "Edit Alergi"
            binding.namaAlergiEd.setText(obat.namaObat)
        }

        alergiViewModel = obtainViewModelAlergi(this)

        // mengatur aksi ketika simpanperubahan ditekan, yaitu memasukkan ke dalam database
        binding.simpanPerubahanBtn.setOnClickListener{
            val nama = binding.namaAlergiEd.text.toString()
            if (obat != null) {
                // Untuk  mengedit
                obat.id?.let { it1 -> obatviewModel.editObat(it1, "Bearer ${userAuth.token}", nama) }
            } else {
                // Insert a new alergi object
                obatviewModel.tambahObat(idPenyakit, "Bearer ${userAuth.token}", nama)
            }
            startActivity(Intent(this, RiwayatPenyakitActivity::class.java))
        }

        obatviewModel.obat.observe(this) { response ->
            if (response.status == "success") {
                response.message?.let { Log.e("asdf", "${response}") }
                Toast.makeText(this, "Berhasil aksi", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@TambahObatActivity, HomeActivity::class.java))
            }
        }

        binding.deletePenyakitBtn.setOnClickListener{
            if(obat != null){
                obat.id?.let { it1 -> obatviewModel.hapusObat(it1, "Bearer ${userAuth.token}") }
                Toast.makeText(this, "Data Alergi Dihapus", Toast.LENGTH_SHORT).show()
            }
            startActivity(Intent(this@TambahObatActivity, HomeActivity::class.java))
        }
    }

    private fun obtainViewModelAlergi(activity: AppCompatActivity): AlergiViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(AlergiViewModel::class.java)
    }
    private fun obtainViewModel(activity: AppCompatActivity): ObatViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(ObatViewModel::class.java)
    }
}