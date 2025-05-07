
# UTS Pemrograman Berorientasi Obyek 2

**Mata Kuliah:** Pemrograman Berorientasi Obyek 2  
**Dosen Pengampu:** Muhammad Ikhwan Fathulloh

## Profil

- **Nama:** Lita Alentina  
- **NIM:** 23552011097  
- **Studi Kasus:** Sistem Kasir Restoran

---

## Judul Studi Kasus

**Penerapan Konsep OOP dan CRUD dalam Sistem Kasir Restoran Berbasis Java dan MySQL**

---

## Penjelasan Studi Kasus

Studi kasus ini bertujuan membangun aplikasi kasir untuk restoran yang mampu mencatat menu makanan dan minuman, membuat pesanan dari pelanggan, serta mencatat status pembayaran.  
Aplikasi dikembangkan menggunakan bahasa **Java**, database **MySQL**, dan menerapkan prinsip **Pemrograman Berorientasi Objek (OOP)** seperti:

- Inheritance  
- Encapsulation  
- Polymorphism  
- Abstraction  

---

---

## 1. Struktur Database

### Database: `kasir_restoran`

Sistem ini menggunakan 3 tabel utama:

- `menu`: Menyimpan daftar makanan dan minuman.
- `pesanan`: Menyimpan informasi umum pesanan.
- `detail_pesanan`: Menyimpan rincian item yang dipesan.

### SQL: Buat database dan tabel

```sql
CREATE DATABASE kasir_restoran;
USE kasir_restoran;

CREATE TABLE menu (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nama VARCHAR(100) NOT NULL,
    harga DOUBLE NOT NULL,
    kategori ENUM('Makanan', 'Minuman') NOT NULL
);

CREATE TABLE pesanan (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tanggal TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE detail_pesanan (
    id INT AUTO_INCREMENT PRIMARY KEY,
    pesanan_id INT,
    menu_id INT,
    jumlah INT,
    total_harga DOUBLE,
    FOREIGN KEY (pesanan_id) REFERENCES pesanan(id),
    FOREIGN KEY (menu_id) REFERENCES menu(id)
);
```

---

## 2. Koneksi ke Database

### `DatabaseConnection.java`

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/kasir_restoran";
    private static final String USER = "root"; // sesuaikan
    private static final String PASSWORD = ""; // sesuaikan
    private static Connection connection;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Koneksi berhasil.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
```

---

## 3. Struktur Kelas & Konsep OOP

### a. **Abstraction** - `ItemMakanan.java`
... (Potongan kode disingkat di sini untuk keterbacaan. File penuh tetap disimpan di README lengkap.)

---

## 4. Pengelolaan Data Menu & Pesanan

### `KasirRestoran.java`
... (Kode Java untuk KasirRestoran.java disertakan penuh di file README.)

---

## 5. Program Utama (Main)

### `Main.java`
... (Kode Main.java disertakan penuh di file README.)

---

## 6. Konsep OOP yang Diterapkan

- **Abstraction**: `ItemMakanan` adalah class abstrak dengan method `hitungHarga()` dan `getKategori()`.
- **Inheritance**: `Makanan` dan `Minuman` mewarisi dari `ItemMakanan`.
- **Polymorphism**: Method `hitungHarga()` dan `getKategori()` diimplementasikan berbeda oleh subclass.
- **Encapsulation**: Class `Pesanan` dan `DetailPesanan` menjaga atribut dengan modifier `private` dan menyediakan akses melalui method.
