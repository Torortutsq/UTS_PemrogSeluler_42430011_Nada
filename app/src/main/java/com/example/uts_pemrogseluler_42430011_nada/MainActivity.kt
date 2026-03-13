package com.example.uts_pemrogseluler_42430011_nada

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var etNamaDosen: EditText
    private lateinit var spinnerSemester: Spinner
    private lateinit var etJumlahMahasiswa: EditText
    private lateinit var btnGenerate: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etNamaDosen       = findViewById(R.id.etNamaDosen)
        spinnerSemester   = findViewById(R.id.spinnerSemester)
        etJumlahMahasiswa = findViewById(R.id.etJumlahMahasiswa)
        btnGenerate       = findViewById(R.id.btnGenerate)

        etNamaDosen.setHintTextColor(Color.parseColor("#80A8C9B5"))
        etJumlahMahasiswa.setHintTextColor(Color.parseColor("#80A8C9B5"))

        val listSemester = listOf(
            "Pilih Semester",
            "Semester 1", "Semester 2",
            "Semester 3", "Semester 4",
            "Semester 5", "Semester 6",
            "Semester 7", "Semester 8"
        )
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listSemester)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSemester.adapter = adapter

        btnGenerate.setOnClickListener {
            validateAndNavigate()
        }
    }

    private fun validateAndNavigate() {
        val namaDosen = etNamaDosen.text.toString().trim()
        val semester  = spinnerSemester.selectedItem.toString()
        val jumlahStr = etJumlahMahasiswa.text.toString().trim()

        if (namaDosen.isEmpty()) {
            etNamaDosen.error = "Nama dosen tidak boleh kosong!"
            etNamaDosen.requestFocus()
            return
        }

        if (semester == "Pilih Semester") {
            Toast.makeText(this, "Pilih semester terlebih dahulu!", Toast.LENGTH_SHORT).show()
            return
        }

        if (jumlahStr.isEmpty()) {
            etJumlahMahasiswa.error = "Jumlah mahasiswa tidak boleh kosong!"
            etJumlahMahasiswa.requestFocus()
            return
        }

        val jumlahMahasiswa = jumlahStr.toIntOrNull()

        if (jumlahMahasiswa == null) {
            etJumlahMahasiswa.error = "Masukkan angka yang valid!"
            etJumlahMahasiswa.requestFocus()
            return
        }

        if (jumlahMahasiswa <= 0) {
            etJumlahMahasiswa.error = "Jumlah mahasiswa harus lebih dari 0!"
            etJumlahMahasiswa.requestFocus()
            Toast.makeText(this, "Jumlah mahasiswa tidak boleh 0!", Toast.LENGTH_SHORT).show()
            return
        }

        if (jumlahMahasiswa > 50) {
            etJumlahMahasiswa.error = "Maksimal 50 mahasiswa!"
            etJumlahMahasiswa.requestFocus()
            Toast.makeText(this, "Maksimal 50 mahasiswa per kelas!", Toast.LENGTH_SHORT).show()
            return
        }

        val intent = Intent(this, PanelActivity::class.java)
        intent.putExtra("NAMA_DOSEN", namaDosen)
        intent.putExtra("SEMESTER", semester)
        intent.putExtra("JUMLAH_MAHASISWA", jumlahMahasiswa)
        startActivity(intent)
    }
}