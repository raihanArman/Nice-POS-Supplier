package id.co.myproject.nicepos_supplier.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Notification {
    @SerializedName("id_request")
    @Expose
    private String idRequest;

    @SerializedName("idSupplier")
    @Expose
    private String idSupplier;

    @SerializedName("id_cafe")
    @Expose
    private String idCafe;

    @SerializedName("total_harga")
    @Expose
    private String totalHarga;

    @SerializedName("tanggal")
    @Expose
    private Date tanggal;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("nama_user")
    @Expose
    private String namaUser;

    @SerializedName("nama_cafe")
    @Expose
    private String namaCafe;

    @SerializedName("gambar")
    @Expose
    private String gambar;

    @SerializedName("jumlah_pesan")
    @Expose
    private String jumlahPesan;

    public Notification(String idRequest, String idSupplier, String idCafe, String totalHarga, Date tanggal, String status, String namaUser, String namaCafe, String jumlahPesan) {
        this.idRequest = idRequest;
        this.idSupplier = idSupplier;
        this.idCafe = idCafe;
        this.totalHarga = totalHarga;
        this.tanggal = tanggal;
        this.status = status;
        this.namaUser = namaUser;
        this.namaCafe = namaCafe;
        this.jumlahPesan = jumlahPesan;
    }

    public String getIdRequest() {
        return idRequest;
    }

    public String getIdSupplier() {
        return idSupplier;
    }

    public String getIdCafe() {
        return idCafe;
    }

    public String getTotalHarga() {
        return totalHarga;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public String getStatus() {
        return status;
    }

    public String getNamaUser() {
        return namaUser;
    }

    public String getNamaCafe() {
        return namaCafe;
    }

    public String getJumlahPesan() {
        return jumlahPesan;
    }

    public String getGambar() {
        return gambar;
    }
}
