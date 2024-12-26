package com.example.datasekolah;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class SiswaTampilDetail extends AppCompatActivity implements View.OnClickListener {

    // Deklarasi variable berdasarkan widget dari view
    private EditText TextIdSiswa;
    private EditText TextNis;
    private EditText TextNama;
    private EditText TextAlamat;
    private EditText TextJk;
    private Button TombolUpdate;
    private Button TombolDelete;
    private String idsiswa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_siswa_tampil_detail);

        Intent intent = getIntent();
        idsiswa = intent.getStringExtra(Konfigurasi.SISWA_ID);

        // Memanggil widget
        TextIdSiswa = findViewById(R.id.editTextIdSiswa);
        TextNis = findViewById(R.id.editTextNis);
        TextNama = findViewById(R.id.editTextNamaSiswa);
        TextAlamat = findViewById(R.id.editTextAlamat);
        TextJk = findViewById(R.id.editTextJk);

        TombolUpdate = findViewById(R.id.buttonUpdate);
        TombolDelete = findViewById(R.id.buttonDelete);

        TombolUpdate.setOnClickListener(this);
        TombolDelete.setOnClickListener(this);

        TextIdSiswa.setText(idsiswa);

        ambilSiswa();
    }

    // METHOD UNTUK MENGAMBIL DATA SISWA
    private void ambilSiswa() {
        class AmbilSiswa extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(SiswaTampilDetail.this, "Fetching...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                tampilSiswa(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                return rh.sendGetRequestParam(Konfigurasi.URL_SISWA_TAMPIL_DETAIL, idsiswa);
            }
        }
        AmbilSiswa as = new AmbilSiswa();
        as.execute();
    }

    // METHOD UNTUK MENAMPILKAN DATA SISWA
    private void tampilSiswa(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            String idsiswa = c.getString(Konfigurasi.TAG_IDSISWA);
            String nis = c.getString(Konfigurasi.TAG_NIS);
            String namasiswa = c.getString(Konfigurasi.TAG_NAMASISWA);
            String alamat = c.getString(Konfigurasi.TAG_ALAMAT);
            String jk = c.getString(Konfigurasi.TAG_JK);

            TextIdSiswa.setText(idsiswa);
            TextIdSiswa.setEnabled(false);  // Tidak bisa diedit
            TextNis.setText(nis);
            TextNama.setText(namasiswa);
            TextAlamat.setText(alamat);
            TextJk.setText(jk);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Gagal memuat data", Toast.LENGTH_LONG).show();
        }
    }

    // METHOD AKSI KETIKA TOMBOL DI KLIK
    @Override
    public void onClick(View v) {
        if (v == TombolUpdate) {
            updateSiswa();
        }

        if (v == TombolDelete) {
            confirmDeleteSiswa();
        }
    }

    // METHOD KONFIRMASI HAPUS SISWA
    private void confirmDeleteSiswa() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Apakah Kamu Yakin Ingin Menghapus Siswa ini?");
        alertDialogBuilder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                deleteSiswa();
            }
        });

        alertDialogBuilder.setNegativeButton("Tidak", null);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    // METHOD DELETE SISWA
    private void deleteSiswa() {
        class DeleteEmployee extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(SiswaTampilDetail.this, "Deleting...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(SiswaTampilDetail.this, s, Toast.LENGTH_LONG).show();
                // Kembali ke halaman daftar siswa
                startActivity(new Intent(SiswaTampilDetail.this, SiswaTampil.class));
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                return rh.sendGetRequestParam(Konfigurasi.URL_SISWA_HAPUS, idsiswa);
            }
        }
        DeleteEmployee de = new DeleteEmployee();
        de.execute();
    }

    // METHOD UPDATE SISWA
    private void updateSiswa() {
        final String nis = TextNis.getText().toString().trim();
        final String namasiswa = TextNama.getText().toString().trim();
        final String alamat = TextAlamat.getText().toString().trim();
        final String jk = TextJk.getText().toString().trim();

        class UpdateSiswa extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(SiswaTampilDetail.this, "Updating...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(SiswaTampilDetail.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(Konfigurasi.KEY_SISWA_IDSISWA, idsiswa);
                hashMap.put(Konfigurasi.KEY_SISWA_NIS, nis);
                hashMap.put(Konfigurasi.KEY_SISWA_NAMASISWA, namasiswa);
                hashMap.put(Konfigurasi.KEY_SISWA_ALAMAT, alamat);
                hashMap.put(Konfigurasi.KEY_SISWA_JK, jk);

                RequestHandler rh = new RequestHandler();
                return rh.sendPostRequest(Konfigurasi.URL_SISWA_EDIT, hashMap);
            }
        }

        UpdateSiswa us = new UpdateSiswa();
        us.execute();
    }
}
