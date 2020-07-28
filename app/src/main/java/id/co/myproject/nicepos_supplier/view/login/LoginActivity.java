package id.co.myproject.nicepos_supplier.view.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import id.co.myproject.nicepos_supplier.R;

import android.os.Bundle;
import android.widget.FrameLayout;

public class LoginActivity extends AppCompatActivity {

    FrameLayout frameLayout;
    //    public static boolean onResetPasswordFragment = false;
    public static boolean setSignUpFragment = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        frameLayout = findViewById(R.id.frame_login);
        if(setSignUpFragment){
            setSignUpFragment = false;
            setFragment(new SignUpFragment());
        }else {
            setFragment(new SignInFragment());
        }



    }


    private void setFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(frameLayout.getId(), fragment);
        transaction.commit();
    }
}
