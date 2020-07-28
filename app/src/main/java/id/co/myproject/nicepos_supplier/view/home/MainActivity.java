package id.co.myproject.nicepos_supplier.view.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import id.co.myproject.nicepos_supplier.R;
import id.co.myproject.nicepos_supplier.view.home.notification.NotifikasiFragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.irfaan008.irbottomnavigation.SpaceItem;
import com.irfaan008.irbottomnavigation.SpaceNavigationView;
import com.irfaan008.irbottomnavigation.SpaceOnClickListener;

import static id.co.myproject.nicepos_supplier.util.Helper.TYPE_INTENT_ADD;
import static id.co.myproject.nicepos_supplier.util.Helper.TYPE_INTENT_EDIT;

public class MainActivity extends AppCompatActivity {
    FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frameLayout = findViewById(R.id.frame_home);

        SpaceNavigationView spaceNavigationView = (SpaceNavigationView) findViewById(R.id.space);
        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
        spaceNavigationView.addSpaceItem(new SpaceItem("", R.drawable.ic_apps_black_24dp));
        spaceNavigationView.addSpaceItem(new SpaceItem("", R.drawable.ic_notifications_black_24dp));

        setFrament(new HomeFragment());

        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                Intent intent = new Intent(MainActivity.this, InputBarangActivity.class);
                intent.putExtra("type_intent", TYPE_INTENT_ADD);
                startActivity(intent);
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                if (itemIndex == 1){
                    setFrament(new NotifikasiFragment());
                }else {
                    setFrament(new HomeFragment());
                }
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
            }
        });

    }

    private void setFrament(Fragment frament){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(frameLayout.getId(), frament);
        transaction.commit();
    }
}
