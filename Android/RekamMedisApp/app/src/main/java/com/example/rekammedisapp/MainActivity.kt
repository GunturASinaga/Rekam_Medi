package com.example.rekammedisapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.dicoding.temantani.db.UserAuth
import com.dicoding.temantani.db.UserPreference
import com.example.rekammedisapp.ui.detailPenyakit.DetailPenyakitActivity
import com.example.rekammedisapp.ui.home.HomeActivity
import com.example.rekammedisapp.ui.login.LoginActivity
import com.example.rekammedisapp.ui.profile.ProfileActivity
import com.example.rekammedisapp.ui.register.RegisterActivity

// Merupakan akses awal untuk mennetukan tampilan berdasarkan status login dari user

class MainActivity : AppCompatActivity() {
    private lateinit var userAuth : UserAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userPref = UserPreference(this)
        userAuth = userPref.getUser()

        if(userAuth.name == ""){ // ketikan user belum terauthentifikasi
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        } else{ // saat user sudah terautentifikasi
            startActivity(Intent(this@MainActivity, HomeActivity::class.java))
        }

    }
}