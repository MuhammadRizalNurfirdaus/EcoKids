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
- **Smart Pause**: Otomatis senyap saat fokus mengerjakan Kuis (kembali berjalan saat slide/keluar)
- **Robust**: Menggunakan timer 3 detik untuk menangani lag pada transisi (khusus emulator lambat)

### 🎨 Tampilan (UI)
- **Responsif**: Layout aman untuk berbagai ukuran layar (ScrollView)
- **Child-Friendly**: Teks **Hitam Tebal** untuk kemudahan membaca, warna dinamis (teks "Merah" berwarna Merah)

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

## 🛠️ Tech Stack & Build System

| Komponen | Teknologi/Versi |
|----------|-----------------|
| **Bahasa** | **Kotlin** 1.9.0 |
| **Build System** | **Gradle** 8.0 (Android Gradle Plugin) |
| **UI** | Android XML Layouts (Responsive + ScrollView) |
| **Database** | SQLite (SQLiteOpenHelper) |
| **Architecture** | Single Activity Pattern + BaseActivity |
| **Min SDK** | 24 (Android 7.0 - Nougat) |
| **Target SDK** | 33 (Android 13 - Tiramisu) |

---

## 📱 File APK

Aplikasi yang sudah di-build dapat diunduh melalui link berikut:

📥 **[Download EcoKids APK](app/build/outputs/apk/debug/app-debug.apk)**

*(Lokasi file: `app/build/outputs/apk/debug/app-debug.apk`)*

---

## 🚀 Cara Menjalankan

1. Clone repository ini
2. Buka dengan **Android Studio Giraffe** atau lebih baru
3. Tunggu proses **Sync Gradle** selesai (download dependencies)
4. Pastikan SDK Android minimal versi 24 sudah terinstall
5. Run pada emulator atau device Android (Disarankan Mode Portrait)

---

## 📄 License

Project ini dibuat untuk memenuhi tugas **Mata Kuliah Bahasa Pemrograman 3**.

---

## 👨‍💻 Developer

**Muhammad Rizal Nurfirdaus**  
NIM: 20230810088  
TINFC-2023-04

Dibuat dengan ❤️ untuk anak-anak Indonesia 🇮🇩
