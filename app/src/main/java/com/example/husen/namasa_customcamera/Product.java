package com.example.husen.namasa_customcamera;

/**
 * Created by husen on 07/02/18.
 */

public class Product {

    private String title;
    private int harga;
    private String deskripsi;
    private String penjualName;
    private String penjualPhoto;

    public Product(String title, int harga, String deskripsi) {
        this.title = title;
        this.harga = harga;
        this.deskripsi = deskripsi;
    }

    public String getTitle() {
        return title;
    }

    public int getHarga() {
        return harga;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getPenjualName() {
        return penjualName;
    }

    public String getPenjualPhoto() {
        return penjualPhoto;
    }
}
