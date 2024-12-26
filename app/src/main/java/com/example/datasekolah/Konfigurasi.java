package com.example.datasekolah;

public class Konfigurasi {

    // URL Skrip CRUD PHP yang digunakan untuk koneksi antara aplikasi Android dan server
    // Penting! Gantilah 'localhost' dengan IP yang sesuai jika server PHP tidak berjalan di mesin yang sama

    // URL untuk operasi CRUD siswa
    public static final String URL_SISWA_TAMBAH =
            "http://192.168.1.6/data-sekolah/siswatambah.php";  // URL untuk tambah siswa
    public static final String URL_SISWA_TAMPIL =
            "http://192.168.1.6/data-sekolah/siswatampil.php";  // URL untuk menampilkan data siswa

    public static final String URL_SISWA_TAMPIL_DETAIL =
            "http://192.168.1.6/data-sekolah/siswa_tampil_detail.php?idsiswa=";  // URL untuk menampilkan detail siswa

    public static final String URL_SISWA_EDIT =
            "http://192.168.1.6/data-sekolah/siswa_edit.php";  // URL untuk mengedit data siswa

    public static final String URL_SISWA_HAPUS =
            "http://192.168.1.6/data-sekolah/siswa_hapus.php?idsiswa=";  // URL untuk menghapus siswa berdasarkan ID

    // Kunci yang digunakan untuk mengirim data ke skrip PHP (POST request)
    public static final String KEY_SISWA_IDSISWA = "idsiswa";  // Kunci untuk ID siswa
    public static final String KEY_SISWA_NIS = "nis";  // Kunci untuk NIS siswa
    public static final String KEY_SISWA_NAMASISWA = "namasiswa";  // Kunci untuk nama siswa
    public static final String KEY_SISWA_ALAMAT = "alamat";  // Kunci untuk alamat siswa
    public static final String KEY_SISWA_JK = "jk";  // Kunci untuk jenis kelamin siswa

    // JSON Tags yang digunakan untuk memparsing JSON response dari server
    public static final String TAG_JSON_ARRAY = "result";  // Nama array utama pada response JSON
    public static final String TAG_IDSISWA = "idsiswa";  // Tag untuk ID siswa dalam JSON
    public static final String TAG_NIS = "nis";  // Tag untuk NIS siswa dalam JSON
    public static final String TAG_NAMASISWA = "namasiswa";  // Tag untuk nama siswa dalam JSON
    public static final String TAG_ALAMAT = "alamat";  // Tag untuk alamat siswa dalam JSON
    public static final String TAG_JK = "jk";  // Tag untuk jenis kelamin siswa dalam JSON

    // ID Siswa (untuk penggunaan lain)
    public static final String SISWA_ID = "siswa_id";  // ID unik untuk siswa
}
