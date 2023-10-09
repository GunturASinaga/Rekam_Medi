package com.example.rekammedisapp.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.temantani.db.UserAuth
import com.dicoding.temantani.db.UserPreference
import com.example.rekammedisapp.adapter.AlergiAdapter
import com.example.rekammedisapp.adapter.ObatAdapter
import com.example.rekammedisapp.adapter.PenyakitAdapter
import com.example.rekammedisapp.data.response.AlergiItem
import com.example.rekammedisapp.data.response.PenyakitItem
import com.example.rekammedisapp.databinding.ActivityHomeBinding
import com.example.rekammedisapp.model.AlergiViewModel
import com.example.rekammedisapp.model.HomeViewModel
import com.example.rekammedisapp.model.PenyakitViewModel
import com.example.rekammedisapp.model.ViewModelFactory
import com.example.rekammedisapp.ui.daftarAlergi.DaftarAlergiActivity
import com.example.rekammedisapp.ui.detailPenyakit.DetailPenyakitActivity
import com.example.rekammedisapp.ui.profile.ProfileActivity
import com.example.rekammedisapp.ui.riwayatPenyakit.RiwayatPenyakitActivity
import com.example.rekammedisapp.ui.tambahAlergi.TambahAlergiActivity

// Home Activity bertujuan sebagai homepage untuk menampiilkan informasi ringkas dan cepat mengenai penyakit dan juga alergi
// Home mempunyai tombol cepat untuk menambahkan penyakit dan alergi

class HomeActivity : AppCompatActivity() {
    // inisialisasi variabel
    private lateinit var binding : ActivityHomeBinding
    private lateinit var penyakitViewModel: PenyakitViewModel
    private lateinit var alergiViewModel: AlergiViewModel
    private lateinit var homeViewModel: HomeViewModel

    private lateinit var userAuth : UserAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // menentukan status login dan mengambil username
        val userPref = UserPreference(this)
        userAuth = userPref.getUser()
        binding.nama.text = userAuth.name

        // menampilkan data di recyclerview
        val recyclerView: RecyclerView = binding.rvPenyakit
        val penyakitAdapter = PenyakitAdapter { penyakitItem ->
            val intent = Intent(this@HomeActivity, DetailPenyakitActivity::class.java)
            intent.putExtra("penyakit", penyakitItem)
            startActivity(intent)
        }
        recyclerView.adapter = penyakitAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val recyclerViewAlergi: RecyclerView = binding.daftarAlergiListRv
        val adapterAlergi = AlergiAdapter { alergi ->
            val intent = Intent(this@HomeActivity, TambahAlergiActivity::class.java)
            intent.putExtra("alergi", alergi)
            startActivity(intent)
        }
        recyclerViewAlergi.adapter = adapterAlergi
        recyclerViewAlergi.layoutManager = LinearLayoutManager(this)

        // HOME
        homeViewModel = obtainViewModelHome(this)
        homeViewModel.getHomeData("Bearer ${userAuth.token}")
        homeViewModel.homepageResponse.observe(this) { response ->
            if (response.status == "success") {
                response.message?.let { Log.e("sd", "${response}") }
                penyakitAdapter.setPenyakitItems(response.penyakit as List<PenyakitItem>) // Corrected line
                adapterAlergi.setAlergis(response.alergi as List<AlergiItem>)
            }
        }

        //ALERGI
        alergiViewModel = obtainViewModelAlergi(this)

        //PENYAKIT
        penyakitViewModel = obtainViewModel(this)

        val adapter = PenyakitAdapter { penyakit ->
            val intent = Intent(this@HomeActivity, DetailPenyakitActivity::class.java)
//            intent.putExtra("penyakit", penyakit)
            startActivity(intent)
        }

        //~END PENYAKIT
        //UNTUK MENAMBAH ALERGI
        binding.btnAlergi.setOnClickListener{
            startActivity(Intent(this@HomeActivity, DaftarAlergiActivity::class.java))
        }
        binding.tambahAlergiBtn.setOnClickListener{
            startActivity(Intent(this@HomeActivity, TambahAlergiActivity::class.java))
        }
        binding.tambahAlergiTv.setOnClickListener{
            startActivity(Intent(this@HomeActivity, TambahAlergiActivity::class.java))
        }
        binding.alergi.setOnClickListener{
            startActivity(Intent(this@HomeActivity, DaftarAlergiActivity::class.java))
        }
        //END UNTUK MENAMBAH ALERGI

        //UNTUK MENAMBAH PENYAKIT
        val launchDetailPenyakitActivity = View.OnClickListener {
            startActivity(Intent(this@HomeActivity, DetailPenyakitActivity::class.java))
        }
        binding.tambahPenyakitBtn.setOnClickListener(launchDetailPenyakitActivity)
        binding.tambahPenyakitTv.setOnClickListener(launchDetailPenyakitActivity)
        //~END UNTUK MENAMBAH PENYAKIT

        //DAFTAR LENGKAP PENYAKIT
        binding.daftarPenyakitTv.setOnClickListener{
            startActivity(Intent(this@HomeActivity, RiwayatPenyakitActivity::class.java))
        }
        binding.btnRiwayatPenyakit.setOnClickListener{
            startActivity(Intent(this@HomeActivity, RiwayatPenyakitActivity::class.java))
        }
        //~DAFTAR LENGKAP PENYAKIT

        binding.profile.setOnClickListener{
            startActivity(Intent(this@HomeActivity, ProfileActivity::class.java))
        }

    }

    private fun obtainViewModel(activity: AppCompatActivity): PenyakitViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(PenyakitViewModel::class.java)
    }

    private fun obtainViewModelAlergi(activity: AppCompatActivity): AlergiViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(AlergiViewModel::class.java)
    }

    private fun obtainViewModelHome(activity: AppCompatActivity): HomeViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(HomeViewModel::class.java)
    }
}