
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

// ===========================
// 1. DatabaseConnection.java (Encapsulation)
// ===========================
package com.mycompany.kasirrestoran;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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

// ===========================
// 2. ItemMakanan.java (Abstraction, Polymorphism)
// ===========================
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

// ===========================
// 3. Makanan.java (Inheritance)
// ===========================
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

// ===========================
// 4. Minuman.java (Inheritance)
// ===========================
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

// ===========================
// 5. DetailPesanan.java (Encapsulation)
// ===========================
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

// ===========================
// 6. Pesanan.java (Encapsulation)
// ===========================
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

// ===========================
// 7. KasirRestoran.java (Encapsulation, CRUD Operations)
// ===========================
package com.mycompany.kasirrestoran;

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

// ===========================
// 8. Main.java (Driver Program)
// ===========================
package com.mycompany.kasirrestoran;

import java.util.*;

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

            switch (pilihan) {
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

## 6. Konsep OOP yang Diterapkan

- **Abstraction**: `ItemMakanan` adalah class abstrak dengan method `hitungHarga()` dan `getKategori()`.
- **Inheritance**: `Makanan` dan `Minuman` mewarisi dari `ItemMakanan`.
- **Polymorphism**: Method `hitungHarga()` dan `getKategori()` diimplementasikan berbeda oleh subclass.
- **Encapsulation**: Class `Pesanan` dan `DetailPesanan` menjaga atribut dengan modifier `private` dan menyediakan akses melalui method.
