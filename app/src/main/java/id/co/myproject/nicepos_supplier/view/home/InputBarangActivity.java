package id.co.myproject.nicepos_supplier.view.home;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import id.co.myproject.nicepos_supplier.BuildConfig;
import id.co.myproject.nicepos_supplier.R;
import id.co.myproject.nicepos_supplier.model.Barang;
import id.co.myproject.nicepos_supplier.model.Value;
import id.co.myproject.nicepos_supplier.request.ApiRequest;
import id.co.myproject.nicepos_supplier.request.RetrofitRequest;
import id.co.myproject.nicepos_supplier.util.ConvertBitmap;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.common.api.Api;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static id.co.myproject.nicepos_supplier.util.Helper.GALLERY_REQUEST;
import static id.co.myproject.nicepos_supplier.util.Helper.TYPE_INTENT_ADD;
import static id.co.myproject.nicepos_supplier.util.Helper.TYPE_INTENT_EDIT;

public class InputBarangActivity extends AppCompatActivity implements ConvertBitmap{

    Spinner sp_satuan;
    ApiRequest apiRequest;
    TextView tv_satuan;
    EditText et_nama_barang, et_harga, et_deskiripsi;
    ElegantNumberButton btn_stok;
    TextView tv_ganti_gambar;
    ImageView iv_barang, iv_back;
    FloatingActionButton fb_upload, fb_edit;
    ProgressDialog progressDialog;
    LinearLayout btnLayoutAddImage;
    Bitmap bitmap;
    String image;
    int idSupplier, type_intent;
    String id_barang;
    List<String> filterList;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_barang);

        et_nama_barang = findViewById(R.id.et_nama_barang);
        et_harga = findViewById(R.id.et_harga);
        sp_satuan = findViewById(R.id.sp_satuan);
        btn_stok = findViewById(R.id.btn_stok);
        btnLayoutAddImage = findViewById(R.id.layout_btn_add_image);
        iv_barang = findViewById(R.id.iv_barang);
        iv_back = findViewById(R.id.iv_back);
        tv_ganti_gambar = findViewById(R.id.tv_ganti_gambar);
        fb_upload = findViewById(R.id.fb_upload);
        fb_edit = findViewById(R.id.fb_edit);
        tv_satuan = findViewById(R.id.tv_satuan);
        et_deskiripsi = findViewById(R.id.et_deskripsi);

        sharedPreferences = getSharedPreferences("supplier", Context.MODE_PRIVATE);
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        idSupplier = sharedPreferences.getInt("id_supplier", 0);
        Toast.makeText(this, "Id supplier : "+idSupplier, Toast.LENGTH_SHORT).show();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Proses ...");

        filterList = new ArrayList<>();
        filterList.add("gram");
        filterList.add("liter");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.item_spinner, R.id.weekofday, filterList);
        sp_satuan.setAdapter(adapter);

        sp_satuan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tv_satuan.setText(sp_satuan.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnLayoutAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, GALLERY_REQUEST);
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        type_intent = getIntent().getIntExtra("type_intent", 0);
        id_barang = getIntent().getStringExtra("id_barang");
        if(type_intent == TYPE_INTENT_EDIT){
            loadDataBarang();
            fb_edit.setVisibility(View.VISIBLE);
            fb_upload.setVisibility(View.GONE);
        }
        fb_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputMenu();
            }
        });

        fb_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateBarang();
            }
        });

        tv_ganti_gambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, GALLERY_REQUEST);
            }
        });

        et_nama_barang.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                cekInput();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void updateBarang() {
        progressDialog.show();
        Call<Value> editBarangItem = apiRequest.editKategoriRequest(
                id_barang,
                et_nama_barang.getText().toString(),
                et_deskiripsi.getText().toString(),
                et_harga.getText().toString(),
                image,
                btn_stok.getNumber(),
                tv_satuan.getText().toString()
        );
        editBarangItem.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(InputBarangActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    if (response.body().getValue() == 1) {
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                Toast.makeText(InputBarangActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadDataBarang() {
        Call<Barang> getBarangItem = apiRequest.getBarangItemRequest(idSupplier, id_barang);
        getBarangItem.enqueue(new Callback<Barang>() {
            @Override
            public void onResponse(Call<Barang> call, Response<Barang> response) {
                if (response.isSuccessful()){
                    Barang barang = response.body();
                    setData(barang);
//                    Toast.makeText(InputBarangActivity.this, ""+barang.getHarga(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Barang> call, Throwable t) {
                Toast.makeText(InputBarangActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setData(Barang barang) {
        iv_barang.setVisibility(View.VISIBLE);
        btnLayoutAddImage.setVisibility(View.INVISIBLE);
        tv_ganti_gambar.setVisibility(View.VISIBLE);
        Glide.with(this).load(BuildConfig.BASE_URL_GAMBAR + "barang/" + barang.getGambar()).into(iv_barang);
        et_nama_barang.setText(barang.getNamaBarang());
        et_harga.setText(String.valueOf(barang.getHarga()));
        et_deskiripsi.setText(barang.getDeskripsi());
        btn_stok.setNumber(String.valueOf((int)barang.getStok()));
        tv_satuan.setText(barang.getSatuan());
    }

    private void inputMenu() {
        progressDialog.show();
        Call<Value> inputBarang = apiRequest.inputBarangRequest(
                idSupplier,
                et_nama_barang.getText().toString(),
                et_deskiripsi.getText().toString(),
                et_harga.getText().toString(),
                image,
                btn_stok.getNumber(),
                tv_satuan.getText().toString()
        );
        inputBarang.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(InputBarangActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    if (response.body().getValue() == 1){
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                Toast.makeText(InputBarangActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cekInput(){
        if (bitmap != null){
            if (!TextUtils.isEmpty(et_nama_barang.getText().toString())){
                fb_upload.setEnabled(true);
            }else {
                fb_upload.setEnabled(false);
            }
        }else {
            fb_upload.setEnabled(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST){
            if (resultCode == RESULT_OK && data != null){
                Uri imageResepUri = data.getData();
                iv_barang.setVisibility(View.VISIBLE);
                btnLayoutAddImage.setVisibility(View.INVISIBLE);
                tv_ganti_gambar.setVisibility(View.VISIBLE);
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageResepUri);
                    iv_barang.setImageBitmap(bitmap);
                    cekInput();
                    progressDialog.show();
                    new LoadBitmapConvertCallback(InputBarangActivity.this, this).execute();
                    progressDialog.dismiss();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void bitmapToString(String imgConvert) {
        image = imgConvert;
    }

    private class LoadBitmapConvertCallback extends AsyncTask<Void, Void, String> {
        private WeakReference<Context> weakContext;
        private WeakReference<ConvertBitmap> weakInsert;

        public LoadBitmapConvertCallback(Context context, ConvertBitmap insertKategori) {
            this.weakContext = new WeakReference<>(context);
            this.weakInsert = new WeakReference<>(insertKategori);
        }

        @Override
        protected String doInBackground(Void... voids) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);
            byte[] imgByte = byteArrayOutputStream.toByteArray();
            String imageBitmap = Base64.encodeToString(imgByte,Base64.DEFAULT);
            return imageBitmap;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakContext.get();
        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
            weakInsert.get().bitmapToString(aVoid);
        }
    }
}
