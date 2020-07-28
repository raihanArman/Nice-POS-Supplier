package id.co.myproject.nicepos_supplier.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pesanan {
    @SerializedName("id_pesanan")
    @Expose
    private String idPesanan;

    @SerializedName("id_request")
    @Expose
    private String idRequest;

    @SerializedName("id_barang")
    @Expose
    private String idBarang;

    @SerializedName("qty")
    @Expose
    private String qty;

    @SerializedName("sub_total")
    @Expose
    private String subTotal;

    @SerializedName("nama_barang")
    @Expose
    private String namaBarang;

    @SerializedName("satuan")
    @Expose
    private String satuan;

    public Pesanan(String idPesanan, String idRequest, String idBarang, String qty, String subTotal, String namaBarang, String satuan) {
        this.idPesanan = idPesanan;
        this.idRequest = idRequest;
        this.idBarang = idBarang;
        this.qty = qty;
        this.subTotal = subTotal;
        this.namaBarang = namaBarang;
        this.satuan = satuan;
    }

    public String getIdPesanan() {
        return idPesanan;
    }

    public String getIdRequest() {
        return idRequest;
    }

    public String getIdBarang() {
        return idBarang;
    }

    public String getQty() {
        return qty;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public String getSatuan() {
        return satuan;
    }
}
