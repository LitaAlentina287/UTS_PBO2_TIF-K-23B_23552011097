/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kasirrestoran;

/**
 *
 * @author Lita Alentina
 */
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

