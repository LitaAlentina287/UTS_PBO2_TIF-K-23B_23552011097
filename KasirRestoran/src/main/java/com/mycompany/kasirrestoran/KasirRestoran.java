/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.kasirrestoran;

/**
 *
 * @author Lita Alentina
 */
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

