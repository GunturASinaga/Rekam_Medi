package com.example.rekammedisapp.ui.daftarAlergi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.temantani.db.UserAuth
import com.dicoding.temantani.db.UserPreference
import com.example.rekammedisapp.adapter.AlergiAdapter
import com.example.rekammedisapp.data.response.AlergiItem
import com.example.rekammedisapp.databinding.ActivityDaftarAlergiBinding
import com.example.rekammedisapp.databinding.ActivityHomeBinding
import com.example.rekammedisapp.model.AlergiViewModel
import com.example.rekammedisapp.model.HomeViewModel
import com.example.rekammedisapp.model.ViewModelFactory
import com.example.rekammedisapp.ui.tambahAlergi.TambahAlergiActivity

// merupakan tampilkan untuk menampilkan data Alergi user, serta menampilkan menu dari tampilan user

class DaftarAlergiActivity : AppCompatActivity() {
    // inisiasi variabel
    private lateinit var binding : ActivityDaftarAlergiBinding
    private lateinit var alergiViewModel: AlergiViewModel
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var userAuth : UserAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDaftarAlergiBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val userPref = UserPreference(this)
        userAuth = userPref.getUser()

        //ALERGI
        alergiViewModel = obtainViewModelAlergi(this)
        val recyclerViewAlergi: RecyclerView = binding.daftarAlergiListRv
        val adapterAlergi = AlergiAdapter { alergi ->
            val intent = Intent(this@DaftarAlergiActivity, TambahAlergiActivity::class.java)
//            intent.putExtra("alergi", alergi)
            startActivity(intent)
        }
        recyclerViewAlergi.adapter = adapterAlergi
        recyclerViewAlergi.layoutManager = LinearLayoutManager(this)
        alergiViewModel.getAllAlergi().observe(this) { alergis ->
//            adapterAlergi.setPenyakits(alergis)
        }
        //~ALERGI

        homeViewModel = obtainViewModelHome(this)
        homeViewModel.getHomeData("Bearer ${userAuth.token}")
        homeViewModel.homepageResponse.observe(this) { response ->
            if (response.status == "success") {
                response.message?.let { Log.e("sdf", "${response}") }
                adapterAlergi.setAlergis(response.alergi as List<AlergiItem>)
            }
        }

        binding.tambahAlergiBtn.setOnClickListener{
            startActivity(Intent(this@DaftarAlergiActivity, TambahAlergiActivity::class.java))
        }
        binding.tambahAlergiTv.setOnClickListener{
            startActivity(Intent(this@DaftarAlergiActivity, TambahAlergiActivity::class.java))
        }
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