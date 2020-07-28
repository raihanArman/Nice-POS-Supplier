package id.co.myproject.nicepos_supplier.view.login;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import id.co.myproject.nicepos_supplier.R;
import id.co.myproject.nicepos_supplier.model.Value;
import id.co.myproject.nicepos_supplier.request.ApiRequest;
import id.co.myproject.nicepos_supplier.request.RetrofitRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {
    private TextView tvLogin;
    private EditText etNama, etEmail, etPassword, etConfirmPassword, et_alamat, et_no_telp;
    private Button btnDaftar;
    private FrameLayout parentFrameLayout;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
    private ProgressDialog progressDialog;
    public static final String TAG = SignUpFragment.class.getSimpleName();
    ApiRequest apiRequest;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvLogin = view.findViewById(R.id.tv_login);
        etNama = view.findViewById(R.id.et_nama);
        etEmail = view.findViewById(R.id.et_email);
        etPassword = view.findViewById(R.id.et_password);
        etConfirmPassword = view.findViewById(R.id.et_confirm_password);
        et_alamat = view.findViewById(R.id.et_alamat);
        et_no_telp = view.findViewById(R.id.et_no_telp);
        btnDaftar = view.findViewById(R.id.btn_sign_up);
        parentFrameLayout = getActivity().findViewById(R.id.frame_login);

        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);

        sharedPreferences = getActivity().getSharedPreferences("supplier", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Proses ...");

        etNama.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInput();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInput();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInput();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInput();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_alamat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInput();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_no_telp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInput();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                daftar();
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new SignInFragment());
            }
        });
    }

    private void checkInput() {
        if (!TextUtils.isEmpty(etNama.getText())) {
            if (!TextUtils.isEmpty(etEmail.getText())) {
                if (!TextUtils.isEmpty(et_alamat.getText())){
                    if (!TextUtils.isEmpty(et_no_telp.getText())){
                        if (!TextUtils.isEmpty(etPassword.getText()) && etPassword.length() >= 8) {
                            if (!TextUtils.isEmpty(etConfirmPassword.getText())) {
                                btnDaftar.setEnabled(true);
                            } else {
                                btnDaftar.setEnabled(false);
                            }
                        } else {
                            btnDaftar.setEnabled(false);
                        }
                    }else {
                        btnDaftar.setEnabled(false);
                    }
                }else {
                    btnDaftar.setEnabled(false);
                }
            } else {
                btnDaftar.setEnabled(false);
            }
        } else {
            btnDaftar.setEnabled(false);
        }
    }

    private void daftar(){
        final String nama = etNama.getText().toString();
        final String email = etEmail.getText().toString();
        final String password = etPassword.getText().toString();
        final String alamat = et_alamat.getText().toString();
        final String no_tel  = et_no_telp.getText().toString();
        Drawable customIconError = getResources().getDrawable(R.drawable.custom_error_icon);
        customIconError.setBounds(0,0,customIconError.getIntrinsicWidth(), customIconError.getIntrinsicHeight());
        if (etEmail.getText().toString().matches(emailPattern)){
            if (etPassword.getText().toString().equals(etConfirmPassword.getText().toString())){
                progressDialog.show();
                Call<Value> cekEmail = apiRequest.cekSupplierRequest(email);
                cekEmail.enqueue(new Callback<Value>() {
                    @Override
                    public void onResponse(Call<Value> call, Response<Value> response) {
                        if (response.isSuccessful()){
                            if (response.body().getValue() == 1){
                                progressDialog.dismiss();
                                btnDaftar.setEnabled(true);
                                Toast.makeText(getActivity(), "Email sudah ada, gunakan email yang lain", Toast.LENGTH_SHORT).show();
                            }else if (response.body().getValue() == 0){
                                btnDaftar.setEnabled(false);
                                Call<Value> inputUser = apiRequest.inputSupplierRequest(email, password, nama, alamat, "", no_tel);
                                inputUser.enqueue(new Callback<Value>() {
                                    @Override
                                    public void onResponse(Call<Value> call, Response<Value> response) {
                                        if (response.body().getValue() == 0){
                                            int idSupplier = response.body().getIdSupplier();
                                            Toast.makeText(getActivity(), ""+idSupplier, Toast.LENGTH_SHORT).show();
                                            Call<Value> inputPesanAdmin = apiRequest.tambahPesanAdminRequest(idSupplier,nama, email, "supplier", "Ingin bergabung sebagai Supplier");
                                            inputPesanAdmin.enqueue(new Callback<Value>() {
                                                @Override
                                                public void onResponse(Call<Value> call, Response<Value> response) {
                                                    if (response.isSuccessful()){
                                                        Toast.makeText(getActivity(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                                        if (response.body().getValue() == 1){
                                                            progressDialog.dismiss();
                                                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                                            builder.setMessage("Tunggu konfirmasi dari admin");
                                                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                                    setFragment(new SignInFragment());
                                                                    dialogInterface.dismiss();
                                                                }
                                                            });
                                                            AlertDialog alertDialog = builder.create();
                                                            alertDialog.show();
                                                        }
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<Value> call, Throwable t) {
                                                    Toast.makeText(getActivity(), "Error input pesan admin : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }else {
                                            progressDialog.dismiss();
                                            Toast.makeText(getActivity(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Value> call, Throwable t) {
                                        Toast.makeText(getActivity(), "Error input user : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Value> call, Throwable t) {
                        Toast.makeText(getActivity(), "Error cek email : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }else {
                etPassword.setError("Password tidak cocok", customIconError);
            }
        }else {
            etEmail.setError("Email tidak cocok", customIconError);
        }
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(parentFrameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }

}
