package id.co.myproject.nicepos_supplier.view.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import id.co.myproject.nicepos_supplier.BuildConfig;
import id.co.myproject.nicepos_supplier.R;
import id.co.myproject.nicepos_supplier.model.Barang;
import id.co.myproject.nicepos_supplier.model.Value;
import id.co.myproject.nicepos_supplier.request.ApiRequest;
import id.co.myproject.nicepos_supplier.request.RetrofitRequest;
import id.co.myproject.nicepos_supplier.util.TimeStampFormatter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import static id.co.myproject.nicepos_supplier.util.Helper.TYPE_INTENT_EDIT;
import static id.co.myproject.nicepos_supplier.util.Helper.rupiahFormat;

public class DetailBarangActivity extends AppCompatActivity {

    public static String id_barang;
    ApiRequest apiRequest;
    int idSupplier;
    String idBarang;
    ImageView iv_back;
    Toolbar toolbar;
    SharedPreferences sharedPreferences;
    ImageView iv_barang;
    TextView tv_barang, tv_harga, tv_stok, tv_deskripsi, tv_tanggal;

    private CollapsingToolbarLayout collapsingToolbarLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_barang);
        
        tv_barang = findViewById(R.id.tv_barang);
        tv_tanggal = findViewById(R.id.tv_tanggal);
        tv_deskripsi = findViewById(R.id.tv_deskripsi);
        tv_harga = findViewById(R.id.tv_harga);
        tv_stok = findViewById(R.id.tv_stok);
        iv_back = findViewById(R.id.iv_back);
        toolbar = findViewById(R.id.toolbar);
        iv_barang = findViewById(R.id.iv_barang);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        
        collapsingToolbarLayout = findViewById(R.id.collapse_toolbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        sharedPreferences = getSharedPreferences("supplier", Context.MODE_PRIVATE);
        idSupplier = sharedPreferences.getInt("id_supplier", 0);
        idBarang = getIntent().getStringExtra("id_barang");


        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDataBarangItem();
    }

    private void loadDataBarangItem() {
        Call<Barang> getBarangItem = apiRequest.getBarangItemRequest(idSupplier, idBarang);
        getBarangItem.enqueue(new Callback<Barang>() {
            @Override
            public void onResponse(Call<Barang> call, Response<Barang> response) {
                if (response.isSuccessful()){
                    Barang barang = response.body();
                    setData(barang);
                }
            }

            @Override
            public void onFailure(Call<Barang> call, Throwable t) {
                Toast.makeText(DetailBarangActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setData(Barang barang) {
        TimeStampFormatter timeStampFormatter = new TimeStampFormatter();
        idBarang = barang.getIdBarang();
        tv_barang.setText(barang.getNamaBarang());
        tv_tanggal.setText(timeStampFormatter.format(barang.getTanggal()));
        tv_harga.setText(rupiahFormat(barang.getHarga())+"/"+barang.getSatuan());
        tv_stok.setText(String.valueOf(barang.getStok())+" "+barang.getSatuan());
        tv_deskripsi.setText(barang.getDeskripsi());
        Glide.with(this).load(BuildConfig.BASE_URL_GAMBAR + "barang/" + barang.getGambar()).into(iv_barang);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.update_process){
            Intent intent = new Intent(DetailBarangActivity.this, InputBarangActivity.class);
            intent.putExtra("id_barang", idBarang);
            intent.putExtra("type_intent", TYPE_INTENT_EDIT);
            startActivity(intent);
        }else if(id == R.id.delete_process){
            deleteProses();
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteProses() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Yakin ingin menghapus barang ini ? ");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Call<Value> deleteBarang = apiRequest.hapusBarangRequest(idBarang);
                deleteBarang.enqueue(new Callback<Value>() {
                    @Override
                    public void onResponse(Call<Value> call, Response<Value> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(DetailBarangActivity.this, ""+response.message(), Toast.LENGTH_SHORT).show();
                            if (response.body().getValue() == 1){
                                finish();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Value> call, Throwable t) {
                        Toast.makeText(DetailBarangActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
}
