package id.co.myproject.nicepos_supplier.view.Profil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import id.co.myproject.nicepos_supplier.R;

import android.os.Bundle;

public class ProfilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fm_profil, new ProfilFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
