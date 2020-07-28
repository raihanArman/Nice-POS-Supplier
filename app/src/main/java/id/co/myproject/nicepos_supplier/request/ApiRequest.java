package id.co.myproject.nicepos_supplier.request;

import java.util.List;

import id.co.myproject.nicepos_supplier.model.Barang;
import id.co.myproject.nicepos_supplier.model.Notification;
import id.co.myproject.nicepos_supplier.model.Pesanan;
import id.co.myproject.nicepos_supplier.model.Supplier;
import id.co.myproject.nicepos_supplier.model.Value;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiRequest {

    @FormUrlEncoded
    @POST("supplier/input_supplier.php")
    Call<Value> inputSupplierRequest(
            @Field("email") String email,
            @Field("password") String password,
            @Field("nama") String nama,
            @Field("alamat") String alamat,
            @Field("logo") String logo,
            @Field("no_telp") String noTelp
    );

    @FormUrlEncoded
    @POST("supplier/cek_supplier.php")
    Call<Value> cekSupplierRequest(
            @Field("email") String email
    );


//    Proses Barang
    @FormUrlEncoded
    @POST("supplier/input_barang.php")
    Call<Value> inputBarangRequest(
            @Field("id_supplier") int idSupplier,
            @Field("nama_barang") String namaBarang,
            @Field("deskripsi") String deskripsi,
            @Field("harga") String harga,
            @Field("gambar") String gambar,
            @Field("stok") String stok,
            @Field("satuan") String satuan
    );

    @FormUrlEncoded
    @POST("supplier/edit_barang.php")
    Call<Value> editKategoriRequest(
            @Field("id_barang") String idBarang,
            @Field("nama_barang") String namaBarang,
            @Field("deskripsi") String deskripsi,
            @Field("harga") String harga,
            @Field("gambar") String gambar,
            @Field("stok") String stok,
            @Field("satuan") String satuan
    );

    @FormUrlEncoded
    @POST("supplier/hapus_barang.php")
    Call<Value> hapusBarangRequest(
            @Field("id_barang") String idBarang
    );

    @GET("supplier/tampil_barang.php")
    Call<List<Barang>> getBarangRequest(
            @Query("id_supplier") int idSupplier
    );

    @GET("supplier/tampil_barang.php")
    Call<Barang> getBarangItemRequest(
            @Query("id_supplier") int idSupplier,
            @Query("id_barang") String idBarang
    );



//    Notification Proses
    @GET("supplier/tampil_notif_supplier.php")
    Call<List<Notification>> getNotifSupplierRequest(
            @Query("id_supplier") int idSupplier
    );

    @GET("supplier/tampil_notif_supplier_item.php")
    Call<Notification> getNotifSupplierItemRequest(
            @Query("id_supplier") int idSupplier,
            @Query("id_request") String idRequest
    );

    @GET("supplier/tampil_pesanan.php")
    Call<List<Pesanan>> getPesananSupplierRequest(
            @Query("id_request") String idRequest
    );

    @FormUrlEncoded
    @POST("supplier/proses_request.php")
    Call<Value> prosesRequest(
            @Field("id_request") String idRequest
    );

    @FormUrlEncoded
    @POST("supplier/batal_request.php")
    Call<Value> batalRequest(
            @Field("id_request") String idRequest
    );

    @FormUrlEncoded
    @POST("supplier/cek_verifikasi_email.php")
    Call<Value> cekVerifikasi(
            @Field("email") String email,
            @Field("level") String level
    );


    @FormUrlEncoded
    @POST("tambah_pesan_admin.php")
    Call<Value> tambahPesanAdminRequest(
            @Field("id_user") int id_user,
            @Field("nama_user") String nama_user,
            @Field("email") String email,
            @Field("level") String level,
            @Field("isi") String isi
    );
//    Login supplier
    @FormUrlEncoded
    @POST("login_supplier.php")
    Call<Value> loginSupplierRequest(
            @Field("email") String email,
            @Field("password") String password
    );
//    Login supplier

    @GET("supplier/tampil_supplier.php")
    Call<Supplier> getSupplierItemRequest(
            @Query("id_supplier") String idSupplier
    );


    @FormUrlEncoded
    @POST("supplier/edit_profil_supplier.php")
    Call<Value> ediProfilSupplierRequest(
            @Field("id_supplier") int idSupplier,
            @Field("nama_supplier") String namaSupplier,
            @Field("alamat") String alamat,
            @Field("no_telp") String no_telp,
            @Field("avatar") String avatar
    );

    @FormUrlEncoded
    @POST("supplier/lupa_password_supplier.php")
    Call<Value> lupaPasswordSupplierRequest(
            @Field("id_supplier") int idSupplier,
            @Field("password") String password
    );
}
