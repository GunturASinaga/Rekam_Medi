package com.example.rekammedisapp.ui.riwayatPenyakit

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
import com.example.rekammedisapp.R
import com.example.rekammedisapp.adapter.PenyakitAdapter
import com.example.rekammedisapp.data.response.AlergiItem
import com.example.rekammedisapp.data.response.PenyakitItem
import com.example.rekammedisapp.databinding.ActivityRegisterBinding
import com.example.rekammedisapp.databinding.ActivityRiwayatPenyakitBinding
import com.example.rekammedisapp.model.HomeViewModel
import com.example.rekammedisapp.model.PenyakitViewModel
import com.example.rekammedisapp.model.ViewModelFactory
import com.example.rekammedisapp.ui.detailPenyakit.DetailPenyakitActivity

// Activity untuk menampilkan list riwayat penyakit

class RiwayatPenyakitActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRiwayatPenyakitBinding
    private lateinit var penyakitViewModel: PenyakitViewModel
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var userAuth : UserAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRiwayatPenyakitBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // mengecek apakah user sudah melakukan login
        val userPref = UserPreference(this)
        userAuth = userPref.getUser()

        penyakitViewModel = obtainViewModel(this)

        // menampilkan daftar penyakti dalama bentuk list
        val recyclerView: RecyclerView = binding.rvPenyakit
        val adapter = PenyakitAdapter { penyakit ->
            val intent = Intent(this@RiwayatPenyakitActivity, DetailPenyakitActivity::class.java)
            intent.putExtra("penyakit", penyakit)
            startActivity(intent)
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

//        penyakitViewModel.getAllPenyakit().observe(this, { penyakits ->
//            adapter.setPenyakits(penyakits)
//        })

        homeViewModel = obtainViewModelHome(this)
        homeViewModel.getHomeData("Bearer ${userAuth.token}")
        homeViewModel.homepageResponse.observe(this) { response ->
            if (response.status == "success") {
                response.message?.let { Log.e("asdf", "${response}") }
                adapter.setPenyakitItems(response.penyakit as List<PenyakitItem>)
            }
        }

        val launchDetailPenyakitActivity = View.OnClickListener {
            startActivity(Intent(this@RiwayatPenyakitActivity, DetailPenyakitActivity::class.java))
        }

        binding.tambahPenyakitBtn.setOnClickListener(launchDetailPenyakitActivity)
        binding.tambahPenyakitTv.setOnClickListener(launchDetailPenyakitActivity)

    }

    private fun obtainViewModel(activity: AppCompatActivity): PenyakitViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(PenyakitViewModel::class.java)
    }
    private fun obtainViewModelHome(activity: AppCompatActivity): HomeViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(HomeViewModel::class.java)
    }
}