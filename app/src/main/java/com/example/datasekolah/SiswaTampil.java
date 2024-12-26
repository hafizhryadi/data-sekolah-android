package com.example.datasekolah;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SiswaTampil extends AppCompatActivity implements ListView.OnItemClickListener {

    // Deklarasi variabel
    private ListView listView;
    private String JSON_STRING;
    Button BacktoTambah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_siswa_tampil);

        // Inisialisasi view
        listView = findViewById(R.id.listViewSiswa);
        listView.setOnItemClickListener(this);

        BacktoTambah = findViewById(R.id.TombolBackToTambah);
        BacktoTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SiswaTampil.this, MainActivity.class));
            }
        });

        ambilJSON(); // Menampilkan data setelah mengambil JSON
    }

    // Fungsi untuk mengambil data JSON dari server
    private void ambilJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(SiswaTampil.this, "Mengambil Data", "Mohon Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showSiswa(); // Menampilkan data siswa di ListView
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                return rh.sendGetRequest(Konfigurasi.URL_SISWA_TAMPIL); // Kirim request GET
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    // Fungsi untuk menampilkan siswa di ListView
    private void showSiswa() {
        JSONObject jsonObject;
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);

            // Iterasi data siswa dan menambahkannya ke list
            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String idsiswa = jo.getString(Konfigurasi.TAG_IDSISWA);
                String nis = jo.getString(Konfigurasi.TAG_NIS);
                String namasiswa = jo.getString(Konfigurasi.TAG_NAMASISWA);
                String alamat = jo.getString(Konfigurasi.TAG_ALAMAT);
                String jk = jo.getString(Konfigurasi.TAG_JK);

                HashMap<String, String> siswa = new HashMap<>();
                siswa.put(Konfigurasi.TAG_IDSISWA, idsiswa);
                siswa.put(Konfigurasi.TAG_NIS, nis);
                siswa.put(Konfigurasi.TAG_NAMASISWA, namasiswa);
                siswa.put(Konfigurasi.TAG_ALAMAT, alamat);
                siswa.put(Konfigurasi.TAG_JK, jk);
                list.add(siswa); // Tambahkan siswa ke dalam list
            }

            // Membuat adapter untuk ListView
            ListAdapter adapter = new SimpleAdapter(
                    SiswaTampil.this,
                    list,
                    R.layout.list_siswa,
                    new String[]{Konfigurasi.TAG_IDSISWA, Konfigurasi.TAG_NIS, Konfigurasi.TAG_NAMASISWA, Konfigurasi.TAG_ALAMAT, Konfigurasi.TAG_JK},
                    new int[]{R.id.textListIdSiswa, R.id.textListNis, R.id.textListNamaSiswa, R.id.textListAlamat, R.id.textListJk}
            );

            // Set adapter ke ListView
            listView.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace(); // Tangani error JSON
        }
    }

    // Fungsi untuk menangani item click di ListView
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Ambil data siswa_id dan buka halaman detail siswa
        Intent intent = new Intent(this, SiswaTampilDetail.class);
        HashMap<String, String> map = (HashMap) parent.getItemAtPosition(position);
        String siswa_id = map.get(Konfigurasi.TAG_IDSISWA).toString();
        intent.putExtra(Konfigurasi.SISWA_ID, siswa_id);
        startActivity(intent); // Buka halaman detail siswa
    }
}
