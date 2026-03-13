package com.example.uts_pemrogseluler_42430011_nada

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class PanelActivity : AppCompatActivity() {

    private lateinit var tvInfoHeader: TextView
    private lateinit var etNilaiKehadiran: EditText
    private lateinit var etNilaiTugas: EditText
    private lateinit var etNilaiUTS: EditText
    private lateinit var etNilaiUAS: EditText
    private lateinit var btnGenerateNilai: Button
    private lateinit var containerHasil: LinearLayout

    private var namaDosen = ""
    private var semester = ""
    private var jumlahMahasiswa = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_panel)

        // MODUL 4: Menerima Data dari Intent
        namaDosen = intent.getStringExtra("NAMA_DOSEN") ?: "Tidak diketahui"
        semester = intent.getStringExtra("SEMESTER") ?: "Tidak diketahui"
        jumlahMahasiswa = intent.getIntExtra("JUMLAH_MAHASISWA", 0)

        tvInfoHeader = findViewById(R.id.tvInfoHeader)
        etNilaiKehadiran = findViewById(R.id.etNilaiKehadiran)
        etNilaiTugas = findViewById(R.id.etNilaiTugas)
        etNilaiUTS = findViewById(R.id.etNilaiUTS)
        etNilaiUAS = findViewById(R.id.etNilaiUAS)
        btnGenerateNilai = findViewById(R.id.btnGenerateNilai)
        containerHasil = findViewById(R.id.containerHasil)

        tvInfoHeader.text = "Dosen: $namaDosen  |  Semester: $semester"

        btnGenerateNilai.setOnClickListener {
            generateLembarPenilaian()
        }
    }

    private fun generateLembarPenilaian() {
        val kehadiranStr = etNilaiKehadiran.text.toString().trim()
        val tugasStr = etNilaiTugas.text.toString().trim()
        val utsStr = etNilaiUTS.text.toString().trim()
        val uasStr = etNilaiUAS.text.toString().trim()

        if (kehadiranStr.isEmpty() || tugasStr.isEmpty() || utsStr.isEmpty() || uasStr.isEmpty()) {
            Toast.makeText(this, "Semua nilai harus diisi!", Toast.LENGTH_SHORT).show()
            return
        }

        val nilaiKehadiran = kehadiranStr.toDouble()
        val nilaiTugas = tugasStr.toDouble()
        val nilaiUTS = utsStr.toDouble()
        val nilaiUAS = uasStr.toDouble()

        if (nilaiKehadiran < 0 || nilaiKehadiran > 100 ||
            nilaiTugas < 0 || nilaiTugas > 100 ||
            nilaiUTS < 0 || nilaiUTS > 100 ||
            nilaiUAS < 0 || nilaiUAS > 100) {
            Toast.makeText(this, "Nilai harus antara 0 - 100!", Toast.LENGTH_SHORT).show()
            return
        }

        containerHasil.removeAllViews()

        // MODUL 5: FOR LOOP - Generate tiap mahasiswa
        for (i in 1..jumlahMahasiswa) {

            // Hitung nilai akhir: Kehadiran 10% | Tugas 20% | UTS 30% | UAS 40%
            val nilaiAkhir = (nilaiKehadiran * 0.10) +
                    (nilaiTugas * 0.20) +
                    (nilaiUTS * 0.30) +
                    (nilaiUAS * 0.40)

            // MODUL 5: IF-ELSE - Menentukan Grade & Status Kelulusan
            val grade: String
            val status: String
            val statusColor: Int

            if (nilaiAkhir >= 80) {
                grade = "A"
                status = "LULUS"
                statusColor = 0xFF2E7D32.toInt()
            } else if (nilaiAkhir >= 70) {
                grade = "B"
                status = "LULUS"
                statusColor = 0xFF388E3C.toInt()
            } else if (nilaiAkhir >= 60) {
                grade = "C"
                status = "LULUS"
                statusColor = 0xFFF57F17.toInt()
            } else if (nilaiAkhir >= 50) {
                grade = "D"
                status = "BATAS"
                statusColor = 0xFFE65100.toInt()
            } else {
                grade = "E"
                status = "TIDAK LULUS"
                statusColor = 0xFFC62828.toInt()
            }

            // IF-ELSE untuk status kehadiran
            val statusKehadiran: String = if (nilaiKehadiran >= 75) {
                "Kehadiran: OK"
            } else {
                "Kehadiran: KURANG"
            }

            val itemView = LayoutInflater.from(this)
                .inflate(R.layout.item_mahasiswa, containerHasil, false)

            val tvNomor = itemView.findViewById<TextView>(R.id.tvNomor)
            val tvNamaMahasiswa = itemView.findViewById<TextView>(R.id.tvNamaMahasiswa)
            val tvNilaiDetail = itemView.findViewById<TextView>(R.id.tvNilaiDetail)
            val tvNilaiAkhir = itemView.findViewById<TextView>(R.id.tvNilaiAkhir)
            val tvGrade = itemView.findViewById<TextView>(R.id.tvGrade)
            val tvStatus = itemView.findViewById<TextView>(R.id.tvStatus)

            tvNomor.text = i.toString()
            tvNamaMahasiswa.text = "Mahasiswa $i — $semester"
            tvNilaiDetail.text = "H:${nilaiKehadiran.toInt()} | T:${nilaiTugas.toInt()} | UTS:${nilaiUTS.toInt()} | UAS:${nilaiUAS.toInt()}  |  $statusKehadiran"
            tvNilaiAkhir.text = String.format("%.1f", nilaiAkhir)
            tvGrade.text = "Grade $grade"
            tvGrade.setTextColor(statusColor)
            tvStatus.text = status
            tvStatus.setTextColor(statusColor)

            containerHasil.addView(itemView)
        }

        Toast.makeText(this, "Berhasil generate $jumlahMahasiswa lembar penilaian!", Toast.LENGTH_SHORT).show()
    }
}