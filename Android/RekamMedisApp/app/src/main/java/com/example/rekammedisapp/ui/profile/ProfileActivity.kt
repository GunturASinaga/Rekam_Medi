package com.example.rekammedisapp.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.temantani.db.UserPreference
import com.example.rekammedisapp.R
import com.example.rekammedisapp.databinding.ActivityHomeBinding
import com.example.rekammedisapp.databinding.ActivityProfileBinding
import com.example.rekammedisapp.ui.login.LoginActivity

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding : ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.tanggalEd.setText("16 Agustus 2001")
        binding.alamatEd.setText("Jl.Yp. Arjuna, Laguboti, Toba Samosir")
        binding.kontakEd.setText("088274451992")

        binding.logout.setOnClickListener{
            val userPref = UserPreference(this)
            userPref.userLogout()
            startActivity(Intent(this@ProfileActivity, LoginActivity::class.java))
        }
    }
}