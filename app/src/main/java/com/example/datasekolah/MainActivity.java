package com.example.datasekolah;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Deklarasi untuk variabel view
    private EditText TextNis;
    private EditText TextNama;
    private EditText TextAlamat;
    private EditText TextJk;
    private Button buttonTambah;
    private Button buttonTampil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi view
        TextNis = findViewById(R.id.editTextNis);
        TextNama = findViewById(R.id.editTextNamaSiswa);
        TextAlamat = findViewById(R.id.editTextAlamat);
        TextJk = findViewById(R.id.editTextJk);

        buttonTambah = findViewById(R.id.TombolTambah);
        buttonTampil = findViewById(R.id.TombolTampil);

        // Set listener untuk tombol
        buttonTambah.setOnClickListener(this);
        buttonTampil.setOnClickListener(this);
    }

    // Fungsi untuk menambahkan siswa (CREATE)
    private void SiswaTambah() {
        final String nis = TextNis.getText().toString().trim();
        final String namasiswa = TextNama.getText().toString().trim();
        final String alamat = TextAlamat.getText().toString().trim();
        final String jk = TextJk.getText().toString().trim();

        // Validasi input sebelum mengirim data
        if (nis.isEmpty() || namasiswa.isEmpty() || alamat.isEmpty() || jk.isEmpty()) {
            Toast.makeText(MainActivity.this, "Semua kolom harus diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        // Menjalankan AsyncTask untuk mengirim data
        class SiswaTambah extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this, "Menambahkan...", "Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                // Menampilkan pesan dari server
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                // Menyiapkan data untuk dikirim ke server
                HashMap<String, String> params = new HashMap<>();
                params.put(Konfigurasi.KEY_SISWA_NIS, nis);
                params.put(Konfigurasi.KEY_SISWA_NAMASISWA, namasiswa);
                params.put(Konfigurasi.KEY_SISWA_ALAMAT, alamat);
                params.put(Konfigurasi.KEY_SISWA_JK, jk);

                // Mengirim request ke server dan menerima response
                RequestHandler rh = new RequestHandler();
                return rh.sendPostRequest(Konfigurasi.URL_SISWA_TAMBAH, params);
            }
        }

        SiswaTambah st = new SiswaTambah();
        st.execute();
    }

    @Override
    public void onClick(View v) {
        if (v == buttonTambah) {
            SiswaTambah();
        }

        if (v == buttonTampil) {
            // Menampilkan daftar siswa
            startActivity(new Intent(this, SiswaTampil.class));
        }
    }
}
