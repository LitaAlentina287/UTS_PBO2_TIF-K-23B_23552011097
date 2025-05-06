/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kasirrestoran;

/**
 *
 * @author Lita Alentina
 */
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
