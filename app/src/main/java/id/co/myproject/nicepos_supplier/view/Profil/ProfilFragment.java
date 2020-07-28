package id.co.myproject.nicepos_supplier.view.Profil;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import id.co.myproject.nicepos_supplier.BuildConfig;
import id.co.myproject.nicepos_supplier.R;
import id.co.myproject.nicepos_supplier.model.Supplier;
import id.co.myproject.nicepos_supplier.request.ApiRequest;
import id.co.myproject.nicepos_supplier.request.RetrofitRequest;
import id.co.myproject.nicepos_supplier.view.login.LoginActivity;
import jp.wasabeef.glide.transformations.BlurTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.co.myproject.nicepos_supplier.view.login.SignInFragment.KEY_ID_SUPPLIER;
import static id.co.myproject.nicepos_supplier.view.login.SignInFragment.KEY_LOGIN_SHARED_PREF;
import static id.co.myproject.nicepos_supplier.view.login.SignInFragment.KEY_LOGIN_STATUS;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfilFragment extends Fragment {

    ImageView ivUser, ivBackground, ivSetting, ivBack;
    TextView tvUser, tvEmail;
    Button btnLogOut;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ApiRequest apiRequest;
    int idSupplier;
    public static boolean statusUpdate = false;

    public ProfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profil, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        sharedPreferences = getActivity().getSharedPreferences(KEY_LOGIN_SHARED_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        idSupplier = sharedPreferences.getInt(KEY_ID_SUPPLIER, 0);

        ivUser = view.findViewById(R.id.iv_user);
        ivBack = view.findViewById(R.id.iv_back);
        ivSetting = view.findViewById(R.id.iv_setting);
        ivBackground = view.findViewById(R.id.iv_background);
        tvUser = view.findViewById(R.id.tv_user);
        tvEmail = view.findViewById(R.id.tv_email);
        btnLogOut = view.findViewById(R.id.btn_log_out);
        ivSetting.setVisibility(View.VISIBLE);
        ivSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditProfilFragment editProfilFragment = new EditProfilFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("id_supplier", idSupplier);
                editProfilFragment.setArguments(bundle);
                ((ProfilActivity)view.getContext()).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fm_profil, editProfilFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });


        loadDataSupplier();

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });
    }

    private void signOut() {
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Proses ...");
        progressDialog.show();
        editor.putBoolean(KEY_LOGIN_STATUS, false);
        editor.putInt(KEY_ID_SUPPLIER, 0);
        editor.commit();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void loadDataSupplier(){
        Call<Supplier> supplierCall = apiRequest.getSupplierItemRequest(String.valueOf(idSupplier));
        supplierCall.enqueue(new Callback<Supplier>() {
            @Override
            public void onResponse(Call<Supplier> call, Response<Supplier> response) {
                if (response.isSuccessful()){
                    Supplier supplier = response.body();
                    String avatar = supplier.getAvatar();
                    String image = "";
                    tvUser.setText(supplier.getNamaSupplier());
                    tvEmail.setText(supplier.getEmail());
                    if(avatar.equals("")){
                        Glide.with(getActivity()).load(R.drawable.supplier_icon).into(ivUser);
                        Glide.with(getActivity()).load(R.drawable.supplier_icon).transform(new BlurTransformation(25,3)).into(ivBackground);
                    }else {
                        image = BuildConfig.BASE_URL_GAMBAR+"user/"+avatar;
                        Glide.with(getActivity()).load(image).into(ivUser);
                        Glide.with(getActivity()).load(image).transform(new BlurTransformation(25,3)).into(ivBackground);
                    }
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
        if (statusUpdate) {
            loadDataSupplier();
        }

    }
}
