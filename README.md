# 🌿 EcoKids - Aplikasi Edukasi Anak

Aplikasi edukasi Android untuk anak-anak belajar mengenal **hewan** dan **buah-buahan** dengan cara yang menyenangkan!

---

## 👤 Identitas Mahasiswa

| Keterangan | Detail |
|------------|--------|
| **Nama** | Muhammad Rizal Nurfirdaus |
| **NIM** | 20230810088 |
| **Kelas** | TINFC-2023-04 |
| **Mata Kuliah** | Bahasa Pemrograman 3 |
| **Dosen Pengampu** | Rio Andriyat Krisdiawan, M.Kom |

---

## 📱 Tentang Aplikasi

**EcoKids** adalah aplikasi edukasi berbasis Android yang dirancang khusus untuk anak-anak agar dapat belajar mengenal hewan dan buah-buahan dengan cara yang interaktif dan menyenangkan. Aplikasi ini dilengkapi dengan fitur quiz untuk menguji pemahaman anak serta sistem **Mode Guru/Orang Tua** yang memungkinkan pendidik atau orang tua untuk mengelola materi pembelajaran.

### 🎯 Tujuan Aplikasi
- Membantu anak-anak belajar mengenal berbagai jenis hewan dan habitat-nya
- Memperkenalkan berbagai macam buah-buahan beserta manfaatnya
- Menyediakan quiz interaktif untuk menguji pemahaman anak
- Memberikan kontrol kepada guru/orang tua untuk mengelola konten pembelajaran

---

## ✨ Fitur Utama

### 🐶 Materi Hewan
- Belajar mengenal berbagai hewan (kucing, anjing, sapi, gajah, singa, dll)
- Informasi habitat dan deskripsi yang mudah dipahami anak

### 🍎 Materi Buah
- Belajar mengenal berbagai buah (apel, pisang, jeruk, semangka, mangga, dll)
- Informasi warna dan manfaat buah untuk kesehatan

### 🎮 Quiz Interaktif
- Quiz dengan 4 level kesulitan (A, B, C, D)
- Gambar menarik untuk setiap pertanyaan
- Feedback langsung dengan emoji menyenangkan

### 🎵 Musik & Audio
- **Seamless Backsound**: Musik latar berjalan tanpa henti antar menu
- **Smart Pause**: Otomatis senyap saat fokus mengerjakan Kuis

### ⚙️ Pengaturan
- **Mode Guru/Orang Tua** dengan sistem Login & Register
- Manajemen Kuis Manual (Tambah/Edit/Hapus Soal)
- Kustomisasi Materi (Pilih Ikon Lucu)

---

## 🔐 Mode Guru/Orang Tua (Keamanan Tingkat Lanjut)

Fitur ini dilindungi dengan sistem autentikasi lengkap untuk memastikan anak-anak tidak dapat mengubah konten pembelajaran secara tidak sengaja.

| Fitur | Mode Anak | Mode Guru |
|-------|:---------:|:---------:|
| Lihat Materi | ✅ | ✅ |
| Quiz | ✅ | ✅ |
| Edit Materi | ❌ | ✅ |
| Hapus Materi | ❌ | ✅ |
| Kelola Kuis | ❌ | ✅ |

### Alur Aktivasi Mode Guru:
1. Buka **Pengaturan** ⚙️
2. Tap tombol **"Masuk"** pada bagian Mode Guru
3. **Konfirmasi**: Akan muncul dialog peringatan bahwa fitur ini khusus dewasa
4. **Login/Register**:
   - Jika sudah punya akun: Masukkan Username & Password
   - Jika belum punya akun: Tap "Daftar disini" untuk membuat akun baru
5. Setelah Login berhasil, tombol Edit/Hapus akan muncul di halaman detail materi

---

## 🛠️ Tech Stack

| Komponen | Teknologi |
|----------|-----------|
| Bahasa | Kotlin |
| UI | Android XML Layouts (Responsive) |
| Database | SQLite (SQLiteOpenHelper) |
| Architecture | Single Activity Pattern + BaseActivity |
| Min SDK | 24 (Android 7.0) |

---

## 📋 Kriteria Penilaian - Fungsionalitas (30%)

| No | Indikator Penilaian | Status |
|:--:|---------------------|:------:|
| B1 | Aplikasi dibuat dengan Kotlin | ✅ |
| B2 | Minimal 2 Activity digunakan | ✅ (8 Activity) |
| B3 | Intent Explicit berjalan | ✅ |
| B4 | RecyclerView tampil dengan benar | ✅ |
| B5 | RecyclerView terhubung dengan Adapter | ✅ |
| B6 | Create data (Insert SQLite) | ✅ |
| B7 | Read data (Tampil di RecyclerView) | ✅ |
| B8 | Update data | ✅ |
| B9 | Delete data | ✅ |
| B10 | Tidak ada crash saat dijalankan | ✅ |

---

## 📂 Struktur Project

```
app/src/main/
├── java/com/example/ecokids/
│   ├── MainActivity.kt           # Halaman utama
│   ├── BaseActivity.kt           # Base Class (Music Lifecycle)
│   ├── SplashActivity.kt         # Splash screen
│   ├── MateriActivity.kt         # Daftar materi (RecyclerView)
│   ├── DetailActivity.kt         # Detail materi + Edit/Delete
│   ├── QuizActivity.kt           # Quiz interaktif (Player)
│   ├── ManageQuizActivity.kt     # Kelola Kuis Manual (Admin)
│   ├── SettingsActivity.kt       # Pengaturan + Logic Login/Register
│   ├── DatabaseHelper.kt         # SQLite CRUD operations (+ User Table)
│   ├── MateriAdapter.kt          # RecyclerView Adapter
│   ├── QuizManageAdapter.kt      # Adapter List Kuis
│   ├── ImagePickerAdapter.kt     # Adapter Pilihan Gambar
│   ├── Models.kt                 # Data classes (Animal, Fruit, Quiz, User)
│   └── MusicManager.kt           # Background music manager
│
└── res/
    ├── layout/                   # XML layouts (Activities & Dialogs)
    ├── drawable/                 # Icons, backgrounds
    └── values/                   # Colors, strings
```

---

## 🚀 Cara Menjalankan

1. Clone repository ini
2. Buka dengan Android Studio
3. Sync Gradle
4. Run pada emulator atau device Android

---

## 📄 License

Project ini dibuat untuk memenuhi tugas **Mata Kuliah Bahasa Pemrograman 3**.

---

## 👨‍💻 Developer

**Muhammad Rizal Nurfirdaus**  
NIM: 20230810088  
TINFC-2023-04

Dibuat dengan ❤️ untuk anak-anak Indonesia 🇮🇩
