# UTS Pemrograman Berorientasi Objek 2

**Mata Kuliah**: Pemrograman Berorientasi Objek 2  
**Dosen Pengampu**: Muhammad Ikhwan Fathulloh  

## Profil  
- **Nama**: Lita Alentina  
- **NIM**: 23552011097  
- **Studi Kasus**: Sistem Kasir Restoran  

---

## Judul Studi Kasus  
**Pengembangan Aplikasi Kasir Restoran Berbasis Java dan MySQL**

---

## Penjelasan Studi Kasus  

Studi kasus ini mengembangkan aplikasi kasir restoran berbasis Java menggunakan konsep **Pemrograman Berorientasi Objek (OOP)** dan **JDBC**.  
Aplikasi ini memungkinkan pengguna (kasir) untuk:

- Menambah menu makanan dan minuman  
- Membuat pesanan berdasarkan meja dan pilihan menu  
- Melihat daftar pesanan beserta statusnya  
- Melakukan pembayaran pesanan  
- Menghapus menu atau pesanan  

Semua data disimpan dan dikelola dalam database MySQL melalui koneksi JDBC.

---

## Penjelasan Studi Kasus Berdasarkan Konsep CRUD  

| Operasi | Penjelasan |
|--------|------------|
| **Create** | Tambah menu baru (`tambahMenu`), buat pesanan baru (`buatPesanan`) |
| **Read** | Tampilkan daftar menu (`tampilkanMenu`), tampilkan seluruh pesanan (`tampilkanPesanan`) |
| **Update** | Ubah status pesanan saat pembayaran (`bayarPesanan`) |
| **Delete** | Hapus menu (`hapusMenu`), hapus pesanan (`hapusPesanan`) |

Seluruh operasi CRUD dilakukan melalui query SQL menggunakan JDBC (`PreparedStatement`, `Statement`).

---

## Penjelasan 4 Pilar OOP dalam Studi Kasus

### 1. Inheritance  
Konsep **Inheritance** digunakan saat `Makanan` dan `Minuman` diturunkan dari class abstract `ItemMakanan`.

```java
public class Makanan extends ItemMakanan { ... }
public class Minuman extends ItemMakanan { ... }
```

### 2. Encapsulation  
Encapsulation digunakan untuk membungkus data, seperti pada class `DetailPesanan`, dengan atribut bersifat private dan hanya dapat diakses melalui getter.

```java
private ItemMakanan menu;
private int jumlah;

public ItemMakanan getMenu() { return menu; }
public int getJumlah() { return jumlah; }
```

### 3. Polymorphism  
Polymorphism ditunjukkan pada method `hitungHarga(int jumlah)` yang diimplementasikan secara berbeda oleh subclass `Makanan` dan `Minuman`.

```java
// Di abstract class
public abstract double hitungHarga(int jumlah);

// Di subclass
@Override
public double hitungHarga(int jumlah) {
    return harga * jumlah;
}
```

### 4. Abstract  
Class `ItemMakanan` adalah class abstrak yang tidak dapat diinstansiasi langsung dan digunakan sebagai dasar untuk jenis menu.

```java
public abstract class ItemMakanan {
    ...
    public abstract double hitungHarga(int jumlah);
}
```

---

## Struktur Database

**Database**: `kasir_restoran`  
Tabel-tabel utama:

- `menu` (`id`, `nama`, `harga`, `jenis`)
- `pesanan` (`id`, `meja`, `status`)
- `detail_pesanan` (`pesanan_id`, `menu_id`, `jumlah`)

```sql
CREATE DATABASE kasir_restoran;
USE kasir_restoran;

CREATE TABLE menu (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nama VARCHAR(255),
    harga DOUBLE,
    jenis VARCHAR(50)
);

CREATE TABLE pesanan (
    id INT AUTO_INCREMENT PRIMARY KEY,
    meja INT,
    status VARCHAR(50)
);

CREATE TABLE detail_pesanan (
    pesanan_id INT,
    menu_id INT,
    jumlah INT,
    FOREIGN KEY (pesanan_id) REFERENCES pesanan(id),
    FOREIGN KEY (menu_id) REFERENCES menu(id)
);
```

---

## Demo Proyek

- **GitHub**: https://github.com/LitaAlentina287/UTS_PBO2_TIF-K-23B_23552011097.git  
- **YouTube**: https://youtu.be/tODA-1ikwcg?si=T0TffNRm8WglQ2sT

---

## Daftar File Source Code

- `DatabaseConnection.java` – Koneksi JDBC  
- `ItemMakanan.java` – Abstract class menu  
- `Makanan.java` – Subclass makanan  
- `Minuman.java` – Subclass minuman  
- `DetailPesanan.java` – Detail item pesanan  
- `Pesanan.java` – Kumpulan detail pesanan  
- `KasirRestoran.java` – Logika sistem (CRUD)  
- `Main.java` – Tampilan CLI dan interaksi pengguna  
