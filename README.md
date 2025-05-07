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


```

---

## Struktur Proyek

```
src/
└── com.mycompany.kasirrestoran/
    ├── DatabaseConnection.java
    ├── DetailPesanan.java <-- OOP: Encapsulation
    ├── ItemMakanan.java     <-- OOP: Abstraction
    ├── Makanan.java         <-- OOP: Inheritance & Polymorphism
    ├── Minuman.java         <-- OOP: Inheritance & Polymorphism
    ├── Pesanan.java         <-- OOP: Encapsulation
    ├── KasirRestoran.java   <-- OOP: CRUD dan Interaksi DB
    └── Main.java
```

---

## Source Code

### 1. `DatabaseConnection.java`

```java
package com.mycompany.kasirrestoran;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/kasir_restoran";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection connection;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
```

### 2. `ItemMakanan.java` *(OOP: Abstraction)*

```java
package com.mycompany.kasirrestoran;

public abstract class ItemMakanan {
    protected int id;
    protected String nama;
    protected double harga;

    public ItemMakanan(int id, String nama, double harga) {
        this.id = id;
        this.nama = nama;
        this.harga = harga;
    }

    public int getId() { return id; }
    public String getNama() { return nama; }
    public double getHarga() { return harga; }

    public abstract double hitungHarga(int jumlah); // Polymorphism
}
```

### 3. `Makanan.java` *(OOP: Inheritance & Polymorphism)*

```java
package com.mycompany.kasirrestoran;

public class Makanan extends ItemMakanan {
    public Makanan(int id, String nama, double harga) {
        super(id, nama, harga);
    }

    @Override
    public double hitungHarga(int jumlah) {
        return harga * jumlah;
    }
}
```

### 4. `Minuman.java` *(OOP: Inheritance & Polymorphism)*

```java
package com.mycompany.kasirrestoran;

public class Minuman extends ItemMakanan {
    public Minuman(int id, String nama, double harga) {
        super(id, nama, harga);
    }

    @Override
    public double hitungHarga(int jumlah) {
        return harga * jumlah;
    }
}
```

### 5. `DetailPesanan.java` *(OOP: Encapsulation)*

```java
package com.mycompany.kasirrestoran;

public class DetailPesanan {
    private ItemMakanan menu;
    private int jumlah;

    public DetailPesanan(ItemMakanan menu, int jumlah) {
        this.menu = menu;
        this.jumlah = jumlah;
    }

    public ItemMakanan getMenu() { return menu; }
    public int getJumlah() { return jumlah; }

    public double getTotalHarga() {
        return menu.hitungHarga(jumlah);
    }
}
```

### 6. `Pesanan.java` *(OOP: Encapsulation)*

```java
package com.mycompany.kasirrestoran;

import java.util.ArrayList;
import java.util.List;

public class Pesanan {
    private int id;
    private int meja;
    private String status;
    private List<DetailPesanan> detailList = new ArrayList<>();

    public Pesanan(int id, int meja, String status) {
        this.id = id;
        this.meja = meja;
        this.status = status;
    }

    public int getId() { return id; }
    public int getMeja() { return meja; }
    public String getStatus() { return status; }
    public List<DetailPesanan> getDetailList() { return detailList; }

    public void tambahDetail(DetailPesanan detail) {
        detailList.add(detail);
    }
}
```

### 7. `KasirRestoran.java`

```java
package com.mycompany.kasirrestoran;

// Kode implementasi CRUD Menu dan Pesanan
// Termasuk operasi insert, delete, select, dan update dengan JDBC
// Kelas ini merupakan penghubung logika bisnis dengan database
import java.sql.*;
import java.util.*;

public class KasirRestoran {
    private Connection conn;

    public KasirRestoran() {
        conn = DatabaseConnection.getConnection();
    }

    public void tambahMenu(String nama, double harga, String jenis) {
        String sql = "INSERT INTO menu (nama, harga, jenis) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nama);
            stmt.setDouble(2, harga);
            stmt.setString(3, jenis);
            stmt.executeUpdate();
            System.out.println("Menu berhasil ditambahkan!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void tampilkanMenu() {
        String sql = "SELECT * FROM menu";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " | " + rs.getString("nama") + " | Rp" + rs.getDouble("harga") + " | " + rs.getString("jenis"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void buatPesanan(int meja, Map<Integer, Integer> daftarMenu) {
        try {
            conn.setAutoCommit(false);

            PreparedStatement psPesanan = conn.prepareStatement(
                "INSERT INTO pesanan (meja, status) VALUES (?, 'belum bayar')",
                Statement.RETURN_GENERATED_KEYS
            );
            psPesanan.setInt(1, meja);
            psPesanan.executeUpdate();
            ResultSet rs = psPesanan.getGeneratedKeys();
            int pesananId = 0;
            if (rs.next()) pesananId = rs.getInt(1);

            PreparedStatement psDetail = conn.prepareStatement(
                "INSERT INTO detail_pesanan (pesanan_id, menu_id, jumlah) VALUES (?, ?, ?)"
            );
            for (Map.Entry<Integer, Integer> entry : daftarMenu.entrySet()) {
                psDetail.setInt(1, pesananId);
                psDetail.setInt(2, entry.getKey());
                psDetail.setInt(3, entry.getValue());
                psDetail.addBatch();
            }
            psDetail.executeBatch();

            conn.commit();
            System.out.println("Pesanan berhasil dibuat!");
        } catch (SQLException e) {
            try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
        } finally {
            try { conn.setAutoCommit(true); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    public void bayarPesanan(int idPesanan) {
        String sql = "UPDATE pesanan SET status='sudah bayar' WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPesanan);
            stmt.executeUpdate();
            System.out.println("Pesanan telah dibayar.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void tampilkanPesanan() {
        String sql = """
            SELECT p.id, p.meja, p.status, m.nama, dp.jumlah, m.harga
            FROM pesanan p
            JOIN detail_pesanan dp ON p.id = dp.pesanan_id
            JOIN menu m ON dp.menu_id = m.id
            ORDER BY p.id
        """;
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            int lastId = -1;
            while (rs.next()) {
                int id = rs.getInt("id");
                if (id != lastId) {
                    System.out.println("\nPesanan #" + id + " (Meja: " + rs.getInt("meja") + ", Status: " + rs.getString("status") + ")");
                    lastId = id;
                }
                System.out.println(" - " + rs.getString("nama") + " x" + rs.getInt("jumlah") + " = Rp" + (rs.getDouble("harga") * rs.getInt("jumlah")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void hapusMenu(int id) {
        String sql = "DELETE FROM menu WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Menu berhasil dihapus.");
            } else {
                System.out.println("Menu tidak ditemukan.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void hapusPesanan(int id) {
        try {
            conn.setAutoCommit(false);

            PreparedStatement deleteDetail = conn.prepareStatement("DELETE FROM detail_pesanan WHERE pesanan_id = ?");
            deleteDetail.setInt(1, id);
            deleteDetail.executeUpdate();

            PreparedStatement deletePesanan = conn.prepareStatement("DELETE FROM pesanan WHERE id = ?");
            deletePesanan.setInt(1, id);
            int rows = deletePesanan.executeUpdate();

            conn.commit();

            if (rows > 0) {
                System.out.println("Pesanan berhasil dihapus.");
            } else {
                System.out.println("Pesanan tidak ditemukan.");
            }
        } catch (SQLException e) {
            try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
        } finally {
            try { conn.setAutoCommit(true); } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}
```

### 8. `Main.java`

```java
package com.mycompany.kasirrestoran;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        KasirRestoran kasir = new KasirRestoran();

        while (true) {
            System.out.println("\n=== Menu Kasir Restoran ===");
            System.out.println("1. Tambah Menu");
            System.out.println("2. Lihat Menu");
            System.out.println("3. Buat Pesanan");
            System.out.println("4. Bayar Pesanan");
            System.out.println("5. Lihat Semua Pesanan");
            System.out.println("6. Hapus Menu");
            System.out.println("7. Hapus Pesanan");
            System.out.println("8. Keluar");
            System.out.print("Pilih: ");
            int pilihan = sc.nextInt();

            s           switch (pilihan) {
                case 1 -> {
                    sc.nextLine();
                    System.out.print("Nama: ");
                    String nama = sc.nextLine();
                    System.out.print("Harga: ");
                    double harga = sc.nextDouble();
                    sc.nextLine();
                    System.out.print("Jenis (makanan/minuman): ");
                    String jenis = sc.nextLine();
                    kasir.tambahMenu(nama, harga, jenis);
                }
                case 2 -> kasir.tampilkanMenu();
                case 3 -> {
                    System.out.print("Nomor Meja: ");
                    int meja = sc.nextInt();
                    Map<Integer, Integer> daftarMenu = new HashMap<>();
                    while (true) {
                        System.out.print("ID Menu (0 untuk selesai): ");
                        int id = sc.nextInt();
                        if (id == 0) break;
                        System.out.print("Jumlah: ");
                        int jumlah = sc.nextInt();
                        daftarMenu.put(id, jumlah);
                    }
                    kasir.buatPesanan(meja, daftarMenu);
                }
                case 4 -> {
                    System.out.print("ID Pesanan yang dibayar: ");
                    int id = sc.nextInt();
                    kasir.bayarPesanan(id);
                }
                case 5 -> kasir.tampilkanPesanan();
                case 6 -> {
                    System.out.print("ID Menu yang akan dihapus: ");
                    int id = sc.nextInt();
                    kasir.hapusMenu(id);
                }
                case 7 -> {
                    System.out.print("ID Pesanan yang akan dihapus: ");
                    int id = sc.nextInt();
                    kasir.hapusPesanan(id);
                }
                case 8 -> System.exit(0);
                default -> System.out.println("Pilihan tidak valid.");
            }
        }
    }
}
```

---

## Konsep OOP yang Diterapkan

| Konsep            | Contoh Kelas                                               |
| ----------------- | ---------------------------------------------------------- |
| **Abstraction**   | `ItemMakanan` (abstract class)                             |
| **Inheritance**   | `Makanan`, `Minuman` dari `ItemMakanan`                    |
| **Polymorphism**  | Override `hitungHarga()` di `Makanan`, `Minuman`           |
| **Encapsulation** | `Pesanan`, `DetailPesanan` (atribut privat, getter/setter) |

---

## Cara Menjalankan

1. Buat database dan tabel di MySQL sesuai struktur di atas.
2. Sesuaikan konfigurasi koneksi di `DatabaseConnection.java`.
3. Compile semua file `.java` dalam satu proyek.
4. Jalankan `Main.java`.

---

