package com.example.rekammedisapp.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.rekammedisapp.R
import com.example.rekammedisapp.data.API.ApiConfig
import com.example.rekammedisapp.data.request.LoginRequest
import com.example.rekammedisapp.data.request.RegisterRequest
import com.example.rekammedisapp.data.response.LoginResponse
import com.example.rekammedisapp.data.response.RegisterResponse
import com.example.rekammedisapp.databinding.ActivityRegisterBinding
import com.example.rekammedisapp.model.LoginViewModel
import com.example.rekammedisapp.ui.login.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// Acviity yang bertujuan untuk memanaejemen proses login

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // mengatur ketikan login ditekan untuk berpindah ke menu login
        binding.loginTv.setOnClickListener{
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
        }

        // untuk mengambil data dari field yang ada dan memeberikannya ke RegisterViewmodel
        binding.registerBtn.setOnClickListener{
            val email = binding.emailTelpEd.text.toString()
            val alamat = binding.alamatEd.text.toString()
            val username = binding.namaEd.text.toString()
            val tanggal = binding.tanggalEd.text.toString()
            val password = binding.passwordEd.text.toString()

            // Call the register function with the input data
            register(email, alamat, username, tanggal, password)
        }
    }

    // fungsi untuk melakukan login dengan cara menghubungkan ke API
    private fun register(email: String, alamat: String, username: String, tanggal: String, password: String) {
        val registerRequest = RegisterRequest(
            username = username,
            birthDate = tanggal,
            alamat = alamat,
            kontak = email,
            password = password
        )

        val client = ApiConfig.getApiService().register(registerRequest)
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        // Handle successful registration response here
                        if(responseBody.status == "success"){
                            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                        } else{
                            Toast.makeText(this@RegisterActivity, "${responseBody.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Log.e("RegisterResponse", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.e("RegisterResponse", "onFailure: ${t.message}")
            }
        })
    }
}