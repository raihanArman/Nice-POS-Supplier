package id.co.myproject.nicepos_supplier.view.home.notification;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import id.co.myproject.nicepos_supplier.R;
import id.co.myproject.nicepos_supplier.adapter.NotifAdapter;
import id.co.myproject.nicepos_supplier.model.Notification;
import id.co.myproject.nicepos_supplier.request.ApiRequest;
import id.co.myproject.nicepos_supplier.request.RetrofitRequest;
import id.co.myproject.nicepos_supplier.util.TimeStampFormatter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotifikasiFragment extends Fragment {

    RecyclerView rv_notif;
    ApiRequest apiRequest;
    int id_supplier;
    SharedPreferences sharedPreferences;
    NotifAdapter notifAdapter;
    LinearLayout lv_empty;


    public NotifikasiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notifikasi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv_notif = view.findViewById(R.id.rv_notif);
        lv_empty = view.findViewById(R.id.lv_empty);
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        sharedPreferences = getActivity().getSharedPreferences("supplier", Context.MODE_PRIVATE);
        id_supplier = sharedPreferences.getInt("id_supplier", 0);

        notifAdapter = new NotifAdapter(getActivity(), new TimeStampFormatter());
        rv_notif.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_notif.setAdapter(notifAdapter);


        loadDataNotif();
        
    }

    private void loadDataNotif() {
        Call<List<Notification>> getNotification = apiRequest.getNotifSupplierRequest(id_supplier);
        getNotification.enqueue(new Callback<List<Notification>>() {
            @Override
            public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response) {
                if (response.isSuccessful()){
                    List<Notification> notificationList = response.body();
                    notifAdapter.setNotificationList(notificationList);
                    if (notificationList.size() <= 0){
                        lv_empty.setVisibility(View.VISIBLE);
                    }else {
                        lv_empty.setVisibility(View.INVISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Notification>> call, Throwable t) {
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
