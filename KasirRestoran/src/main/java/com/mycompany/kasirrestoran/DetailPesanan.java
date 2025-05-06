/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kasirrestoran;

/**
 *
 * @author Lita Alentina
 */
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

