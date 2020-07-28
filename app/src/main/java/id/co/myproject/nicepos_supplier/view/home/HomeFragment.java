package id.co.myproject.nicepos_supplier.view.home;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import id.co.myproject.nicepos_supplier.BuildConfig;
import id.co.myproject.nicepos_supplier.R;
import id.co.myproject.nicepos_supplier.adapter.BarangAdapter;
import id.co.myproject.nicepos_supplier.model.Barang;
import id.co.myproject.nicepos_supplier.model.Supplier;
import id.co.myproject.nicepos_supplier.request.ApiRequest;
import id.co.myproject.nicepos_supplier.request.RetrofitRequest;
import id.co.myproject.nicepos_supplier.view.Profil.ProfilActivity;
import id.co.myproject.nicepos_supplier.view.login.LoginActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    RecyclerView rv_home;
    LinearLayout lv_empty;
    ApiRequest apiRequest;
    SharedPreferences sharedPreferences;
    BarangAdapter barangAdapter;
    ImageView iv_supplier;
    int id_supplier;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        sharedPreferences = getActivity().getSharedPreferences("supplier", Context.MODE_PRIVATE);
        id_supplier = sharedPreferences.getInt("id_supplier", 0);

        rv_home = view.findViewById(R.id.rv_home);
        iv_supplier = view.findViewById(R.id.iv_supplier);
        lv_empty = view.findViewById(R.id.lv_empty);

        rv_home.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        barangAdapter = new BarangAdapter(getActivity());
        rv_home.setAdapter(barangAdapter);

        iv_supplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ProfilActivity.class);
                getActivity().startActivity(intent);
            }
        });

    }

    private void loadDataHome() {

        Call<List<Barang>> getBarang = apiRequest.getBarangRequest(id_supplier);
        getBarang.enqueue(new Callback<List<Barang>>() {
            @Override
            public void onResponse(Call<List<Barang>> call, Response<List<Barang>> response) {
                if (response.isSuccessful()){
                    List<Barang> barangList = response.body();
                    barangAdapter.setBarangList(barangList);
                    if (barangList.size() <= 0){
                        lv_empty.setVisibility(View.VISIBLE);
                    }else {
                        lv_empty.setVisibility(View.INVISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Barang>> call, Throwable t) {
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadDataSupplier(){
        Call<Supplier> supplierCall = apiRequest.getSupplierItemRequest(String.valueOf(id_supplier));
        supplierCall.enqueue(new Callback<Supplier>() {
            @Override
            public void onResponse(Call<Supplier> call, Response<Supplier> response) {
                if (response.isSuccessful()){
                    Supplier supplier = response.body();
                    String avatar = supplier.getAvatar();
                    if(avatar.equals("")){
                        avatar = "supplier.png";
                    }
                    Glide.with(getActivity()).load(BuildConfig.BASE_URL_GAMBAR + "user/" + avatar).into(iv_supplier);
                }
            }

            @Override
            public void onFailure(Call<Supplier> call, Throwable t) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadDataHome();
        loadDataSupplier();
    }
}
