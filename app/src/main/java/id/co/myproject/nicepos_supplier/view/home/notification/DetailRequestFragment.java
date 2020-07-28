package id.co.myproject.nicepos_supplier.view.home.notification;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import id.co.myproject.nicepos_supplier.BuildConfig;
import id.co.myproject.nicepos_supplier.R;
import id.co.myproject.nicepos_supplier.adapter.PesananAdapter;
import id.co.myproject.nicepos_supplier.model.Notification;
import id.co.myproject.nicepos_supplier.model.Pesanan;
import id.co.myproject.nicepos_supplier.model.Value;
import id.co.myproject.nicepos_supplier.request.ApiRequest;
import id.co.myproject.nicepos_supplier.request.RetrofitRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailRequestFragment extends Fragment {

    TextView tv_user, tv_cafe, tv_terima, tv_batalkan;
    ImageView iv_user, iv_back;
    RecyclerView rv_request;
    ApiRequest apiRequest;
    SharedPreferences sharedPreferences;
    PesananAdapter pesananAdapter;
    ProgressDialog progressDialog;

    public DetailRequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_request, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        sharedPreferences = getActivity().getSharedPreferences("supplier", Context.MODE_PRIVATE);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Proses ...");

        tv_user = view.findViewById(R.id.tv_user);
        tv_cafe = view.findViewById(R.id.tv_cafe);
        tv_terima = view.findViewById(R.id.tv_terima);
        tv_batalkan = view.findViewById(R.id.tv_batalkan);
        iv_user = view.findViewById(R.id.iv_user);
        iv_back = view.findViewById(R.id.iv_back);
        rv_request = view.findViewById(R.id.rv_request);
        String idRequest = getArguments().getString("id_request");
        int idSupplier = sharedPreferences.getInt("id_supplier", 0);

        rv_request.setLayoutManager(new LinearLayoutManager(getActivity()));
        pesananAdapter = new PesananAdapter(getActivity());
        rv_request.setAdapter(pesananAdapter);

        loadDataRequestItem(idSupplier,idRequest);
        loadDataPesanan(idRequest);

        tv_terima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prosesRequestBtn(idRequest);
            }
        });

        tv_batalkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                batalRequestBtn(idRequest);
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStackImmediate();
            }
        });
    }

    private void prosesRequestBtn(String idRequest) {
        progressDialog.show();
        Call<Value> prosesRequest = apiRequest.prosesRequest(idRequest);
        prosesRequest.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();
                    if(response.body().getValue() == 1){
                        Toast.makeText(getActivity(), "Berhasil mengudate", Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager().popBackStackImmediate();
                    }else {
                        Toast.makeText(getActivity(), "Gagal mengudate", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void batalRequestBtn(String idRequest) {
        progressDialog.show();
        Call<Value> batalRequest = apiRequest.batalRequest(idRequest);
        batalRequest.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();
                    if(response.body().getValue() == 1){
                        getActivity().getSupportFragmentManager().popBackStackImmediate();
                        Toast.makeText(getActivity(), "Berhasil mengudate", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getActivity(), "Gagal mengudate", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadDataPesanan(String idRequest) {
        Call<List<Pesanan>> getPesanan = apiRequest.getPesananSupplierRequest(idRequest);
        getPesanan.enqueue(new Callback<List<Pesanan>>() {
            @Override
            public void onResponse(Call<List<Pesanan>> call, Response<List<Pesanan>> response) {
                if (response.isSuccessful()){
                    List<Pesanan> pesananList = response.body();
                    pesananAdapter.setPesananList(pesananList);
                }
            }

            @Override
            public void onFailure(Call<List<Pesanan>> call, Throwable t) {
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadDataRequestItem(int idSupplier, String idRequest) {
        Call<Notification> notificationCall = apiRequest.getNotifSupplierItemRequest(idSupplier, idRequest);
        notificationCall.enqueue(new Callback<Notification>() {
            @Override
            public void onResponse(Call<Notification> call, Response<Notification> response) {
                if (response.isSuccessful()){
                    Notification notification = response.body();
                    setData(notification);
                }
            }

            @Override
            public void onFailure(Call<Notification> call, Throwable t) {

            }
        });
    }

    private void setData(Notification notification) {
        Glide.with(getActivity()).load(BuildConfig.BASE_URL_GAMBAR+"cafe/"+notification.getGambar()).into(iv_user);
        tv_user.setText(notification.getNamaUser());
        tv_cafe.setText(notification.getNamaCafe());
        if (notification.getStatus().equals("lunas")){
            tv_batalkan.setVisibility(View.GONE);
            tv_terima.setVisibility(View.GONE);
        }
    }
}
