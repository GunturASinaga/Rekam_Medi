package com.example.rekammedisapp.ui.detailPenyakit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.temantani.db.UserAuth
import com.dicoding.temantani.db.UserPreference
import com.example.rekammedisapp.adapter.ObatAdapter
import com.example.rekammedisapp.data.request.PenyakitRequestBody
import com.example.rekammedisapp.data.response.DataItem
import com.example.rekammedisapp.data.response.DataItemObat
import com.example.rekammedisapp.data.response.PenyakitItem
import com.example.rekammedisapp.databinding.ActivityDetailPenyakitBinding
import com.example.rekammedisapp.model.ObatViewModel
import com.example.rekammedisapp.model.PenyakitApiViewModel
import com.example.rekammedisapp.model.ViewModelFactory
import com.example.rekammedisapp.ui.home.HomeActivity
import com.example.rekammedisapp.ui.tambahObat.TambahObatActivity

// Bertujuan untuk menampilkan detail penyakit, dan menampilkan tombol menu untuk menyimpan perubahan ataupun menghapus penyakit

class DetailPenyakitActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailPenyakitBinding
    private lateinit var penyakitapiViewModel: PenyakitApiViewModel
    private lateinit var obatViewModel : ObatViewModel
    private lateinit var userAuth : UserAuth
    private lateinit var penyakitawal : PenyakitRequestBody

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityDetailPenyakitBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        penyakitapiViewModel = obtainViewModel(this@DetailPenyakitActivity)
        obatViewModel = obtainViewModelObat(this@DetailPenyakitActivity)

        // mengecek status login
        val userPref = UserPreference(this)
        userAuth = userPref.getUser()

        val penyakit: PenyakitItem? = intent.getParcelableExtra("penyakit")
        if(penyakit != null){ // untuk mengedit
            Log.e("cobapenyakit", "memasuki menu edit")

        } else{ // untuk memasukkan yang baru
            Log.e("cobapenyakit", "memasuki menu tambah")

        }
        if (penyakit != null) {
            penyakit.id?.let { id ->
                userAuth.token?.let { authToken ->
                    val fullAuthToken = "Bearer $authToken"
                    penyakitapiViewModel.getPenyakitTunggal(id, fullAuthToken)
                }
            }
        }

        // menampilkan data penyakit
        penyakitapiViewModel.penyakitResponse.observe(this) { response ->
            if (response.status == "success") {
                val penyakit = response.data?.get(0)
                if (penyakit != null) {
                    penyakitawal = penyakit.toPenyakitRequestBody()
                    binding.namaPenyakitTv.setText(penyakit.nama.orEmpty())
                    binding.gejalaEd.setText(penyakit.gejala.orEmpty())
                    binding.awalEd.setText(penyakit.tanggalMulai.orEmpty())
                    binding.diobatiEd.setText(penyakit.tanggalSelesai.orEmpty())
                    binding.tindakanMedisEd.setText(penyakit.tindakanMedis.orEmpty())
                    binding.hasilEd.setText(penyakit.hasil.orEmpty())
                    penyakit.id?.let { obatViewModel.getObatTunggal(it, "Bearer ${userAuth.token}") }
                }
            }
        }

        // menampilkan data ke recycler view dalam bentuk list

        val recyclerView: RecyclerView = binding.rvObat
        val adapter = ObatAdapter { obat ->
            val intent = Intent(this@DetailPenyakitActivity, TambahObatActivity::class.java)
            intent.putExtra("obat", obat)
            startActivity(intent)
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // menampilkan data obat
        obatViewModel.obatResponse.observe(this){response ->
            if(response.status == "success"){
                Log.e("cobaPenyakit", "Hasil dari Obat ${response.data}")
                adapter.setObats(response.data as List<DataItemObat>)
            }
        }

        penyakitapiViewModel.alergiResponse.observe(this) { response ->
            if (response.status == "success") {
                Toast.makeText(this, "Data penyakit diupdate", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@DetailPenyakitActivity, HomeActivity::class.java))
            }
        }

        penyakitapiViewModel.updateResponse.observe(this) { response ->
            if (response.status == "success") {
                Toast.makeText(this, "Data penyakit diupdate", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@DetailPenyakitActivity, HomeActivity::class.java))
            }
        }

        binding.simpanPerubahanBtn.setOnClickListener{ // mengatur aksi untuk simpan perubahan data penyakti
            if(penyakit != null){ // untuk mengedit
                Log.e("cobapenyakit", "memasuki menu edit")
                editData()
            } else{ // untuk memasukkan yang baru
                Log.e("cobapenyakit", "memasuki menu tambah")
               insertNew()
            }
        }

        binding.deletePenyakitBtn.setOnClickListener{ // mengatur aksi untuk menghapus penyakti
            if (penyakit != null) {
                penyakit.id?.let { it1 -> penyakitapiViewModel.hapusPenyakit(it1, "Bearer ${userAuth.token}") }
                Toast.makeText(this, "Data Penyakit Dihapus", Toast.LENGTH_SHORT).show()
            }
            startActivity(Intent(this@DetailPenyakitActivity, HomeActivity::class.java))
        }

        binding.namaPenyakitTv.setOnClickListener {// mengatur tampilan ketika klick nama penyakti agar dikosongkan
            val defaultText = "Nama Penyakit"
            val currentText = binding.namaPenyakitTv.text.toString()
            if (currentText == defaultText) {
                binding.namaPenyakitTv.text.clear()
            }
        }

        binding.tambahObat.setOnClickListener{ // ketikan tambah obat di klik
            val toTambah = Intent(this@DetailPenyakitActivity, TambahObatActivity::class.java)
            if (penyakit != null) {
                toTambah.putExtra("idPenyakit", penyakit.id)
                startActivity(toTambah)
            }
        }
    }

    // fungsi untuk mengedit data penyakit, dengan cara mengambil data baru dari penyakti
    private fun editData() {
        val newName = binding.namaPenyakitTv.text.toString()
        val newGejala = binding.gejalaEd.text.toString()
        val newAwal = binding.awalEd.text.toString()
        val newAkhir = binding.diobatiEd.text.toString()
        val newTindakanMedis = binding.tindakanMedisEd.text.toString()
        val newHasil = binding.hasilEd.text.toString()

        val penyakit = penyakitawal
        if (penyakit != null) {
            if (newName != penyakit.nama || newGejala != penyakit.gejala || newAwal != penyakit.tanggal_mulai ||
                newAkhir != penyakit.tanggal_selesai || newTindakanMedis != penyakit.tindakan_medis || newHasil != penyakit.hasil
            ) {
                // At least one field has changed, proceed with update
                val penyakitid: PenyakitItem? = intent.getParcelableExtra("penyakit")
                val requestBody = PenyakitRequestBody(
                    nama = newName,
                    gejala = newGejala,
                    tanggal_mulai = newAwal,
                    tindakan_medis = newTindakanMedis,
                    tanggal_selesai = newAkhir,
                    hasil = newHasil
                )

                val authToken = "Bearer ${userAuth.token}"
                penyakitid?.id?.let { penyakitapiViewModel.editPenyakit(authToken, requestBody, it) }
            }
        }
    }

    // fungsi untuk menambahkan data penyakit baru
    private fun insertNew() {
        val nama = binding.namaPenyakitTv.text.toString()
        val gejala = binding.gejalaEd.text.toString()
        val durasi = binding.awalEd.text.toString()
        val tindakanMedis = binding.tindakanMedisEd.text.toString()
        val hasil = binding.hasilEd.text.toString()
        val akhir = binding.diobatiEd.text.toString()

        val requestBody = PenyakitRequestBody(
            nama = nama,
            gejala = gejala,
            tanggal_mulai = durasi,
            tindakan_medis = tindakanMedis,
            tanggal_selesai = akhir,
            hasil = hasil
        )

        val authToken = "Bearer ${userAuth.token}"
        penyakitapiViewModel.tambahPenyakit(authToken, requestBody)
    }

    // Mengakses view model
    private fun obtainViewModel(activity: AppCompatActivity): PenyakitApiViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(PenyakitApiViewModel::class.java)
    }

    private fun obtainViewModelObat(activity: AppCompatActivity): ObatViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(ObatViewModel::class.java)
    }


    fun DataItem.toPenyakitRequestBody(): PenyakitRequestBody {
        return PenyakitRequestBody(
            nama = this.nama.orEmpty(),
            gejala = this.gejala.orEmpty(),
            tanggal_mulai = this.tanggalMulai.orEmpty(),
            tindakan_medis = this.tindakanMedis.orEmpty(),
            tanggal_selesai = this.tanggalSelesai,
            hasil = this.hasil
        )
    }

}