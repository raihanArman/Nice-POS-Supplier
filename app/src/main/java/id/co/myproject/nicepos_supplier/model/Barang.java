package id.co.myproject.nicepos_supplier.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Barang {
    @SerializedName("id_barang")
    @Expose
    private String idBarang;

    @SerializedName("nama_barang")
    @Expose
    private String namaBarang;


    @SerializedName("deskripsi")
    @Expose
    private String deskripsi;


    @SerializedName("harga")
    @Expose
    private int harga;

    @SerializedName("stok")
    @Expose
    private double stok;

    @SerializedName("satuan")
    @Expose
    private String satuan;

    @SerializedName("gambar")
    @Expose
    private String gambar;

    @SerializedName("tanggal")
    @Expose
    private Date tanggal;


    public Barang(String idBarang, String namaBarang, int harga, double stok, String satuan, Date tanggal) {
        this.idBarang = idBarang;
        this.namaBarang = namaBarang;
        this.harga = harga;
        this.stok = stok;
        this.satuan = satuan;
        this.tanggal = tanggal;
    }

    public String getIdBarang() {
        return idBarang;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public int getHarga() {
        return harga;
    }

    public double getStok() {
        return stok;
    }

    public String getSatuan() {
        return satuan;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public String getGambar() {
        return gambar;
    }

    public String getDeskripsi() {
        return deskripsi;
    }
}
