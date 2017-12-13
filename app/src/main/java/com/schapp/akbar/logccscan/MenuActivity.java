package com.schapp.akbar.logccscan;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import com.goka.blurredgridmenu.BlurredGridMenuConfig;
import com.goka.blurredgridmenu.GridMenu;
import com.goka.blurredgridmenu.GridMenuFragment;

public class MenuActivity extends AppCompatActivity {



    private GridMenuFragment mGridMenuFragment;

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
    private void makeBlurConfig() {
        BlurredGridMenuConfig
                .build(new BlurredGridMenuConfig.Builder()
                        .radius(1)
                        .downsample(1)
                        .overlayColor(Color.parseColor("akbar")));

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        int backgroundResourceId = R.drawable.back_dbs;
        mGridMenuFragment = GridMenuFragment.newInstance(backgroundResourceId);
        setupGridMenu();
                FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
                tx.replace(R.id.main_frame, mGridMenuFragment);
                tx.addToBackStack(null);
                tx.commit();

        mGridMenuFragment.setOnClickMenuListener(new GridMenuFragment.OnClickMenuListener() {
            @Override
            public void onClickMenu(GridMenu gridMenu, int position) {

                String _Val=gridMenu.getTitle();

                switch (_Val)
                {
                    case "Scan IBD":
                        Intent IRcv=new Intent(MenuActivity.this, RCVActivity.class);
                        startActivity(IRcv);
                        break;
                    case "Putway IBD":
                        Intent IPtw=new Intent(MenuActivity.this, PTW.class);
                        startActivity(IPtw);
                        break;
                    case "Picking OBD":
                        Intent IPck=new Intent(MenuActivity.this, PickingList.class);
                        startActivity(IPck);
                        break;
                    case "Logout":
                        finish();
                        break;
                }


            }
        });
    }





        private void setupGridMenu() {
            List<GridMenu> menus = new ArrayList<>();
            menus.add(new GridMenu("Scan IBD", R.drawable.rsz_barcode));
            menus.add(new GridMenu("Putway IBD", R.drawable.rsz_putway));
            menus.add(new GridMenu("Picking OBD", R.drawable.rsz_picking));
            menus.add(new GridMenu("Stock Check", R.drawable.rsz_stock));
            menus.add(new GridMenu("Logout", R.drawable.rsz_power));

            mGridMenuFragment.setupMenu(menus);
        }
}
