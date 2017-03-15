package com.mobile.hyerim.mobiletermproject.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.mobile.hyerim.mobiletermproject.adapters.BrandAdapter;
import com.mobile.hyerim.mobiletermproject.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    final static String TAG = "MainAcitivity";
    private Intent intent;

    public final int MY_PERMISSIONS_REQUEST_READ_CALENDER = 1;
    public final int MY_PERMISSIONS_REQUEST_WRITE_CALENDER = 2;
    public final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 3;

    private GridView gridView;
    private String[] gridViewStringKo = {
            "스타벅스", "엔제리너스", "요거프레소",
            "이디야", "카페베네", "커피빈",
            "탐앤탐스", "파스쿠찌", "투썸플레이스"
    };

    private String[] gridViewStringEng = {
            "Starbucks", "Angelinus", "Yogerpresso",
            "Ediya", "Caffebene", "CoffeBean",
            "Tomntoms", "Pascucci", "A Twosome Place"
    };
    private int[] gridViewImageId= {
            R.drawable.starbucks_logo, R.drawable.angelinus_logo, R.drawable.yogerpresso_logo,
            R.drawable.ediya_logo, R.drawable.caffebene_logo, R.drawable.coffeebean_logo,
            R.drawable.tomntoms_logo, R.drawable.pascucci_logo, R.drawable.twosomeplace_logo
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        permissionCheck();
        initView();
        setGridView();

    }

    //그리드뷰 데이터 설정
    private void setGridView() {
        BrandAdapter brandAdapter = new BrandAdapter(MainActivity.this, gridViewStringKo, gridViewStringEng, gridViewImageId);
        gridView.setAdapter(brandAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                intent = new Intent(getApplicationContext(), ProductListActivity.class); //캘린더화면으로 이동
                intent.putExtra("brand",gridViewStringEng[position]);
                startActivity(intent);
            }
        });
    }

    private void permissionCheck() {

        if((ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_CALENDAR)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            }else{

                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_CALENDAR},
                        MY_PERMISSIONS_REQUEST_READ_CALENDER);

            }
            if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.WRITE_CALENDAR)){

            }else {

                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.WRITE_CALENDAR},
                        MY_PERMISSIONS_REQUEST_WRITE_CALENDER);
            }
            if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)){

            }else {

                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            }
        } else {
            // READ_EXTERNAL_STORAGE 권한이 있는 것이므로
            // Public Directory에 접근할 수 있고 거기에 있는 파일을 읽을 수 있다

        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CALENDER: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // READ_EXTERNAL_STORAGE 권한을 얻었으므로
                    // 관련 작업을 수행할 수 있다

                } else {

                    // 권한을 얻지 못 하였으므로 파일 읽기를 할 수 없다
                    // 적절히 대처한다

                }
                return;
            }
            case MY_PERMISSIONS_REQUEST_WRITE_CALENDER: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // READ_EXTERNAL_STORAGE 권한을 얻었으므로
                    // 관련 작업을 수행할 수 있다

                } else {

                    // 권한을 얻지 못 하였으므로 파일 읽기를 할 수 없다
                    // 적절히 대처한다

                }
                return;
            }
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // READ_EXTERNAL_STORAGE 권한을 얻었으므로
                    // 관련 작업을 수행할 수 있다

                } else {

                    // 권한을 얻지 못 하였으므로 파일 읽기를 할 수 없다
                    // 적절히 대처한다

                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    private void initView() {
        //Navigation Drawer Activity 생성 시 만들어지는 코드
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //gridView 뷰 설정
        gridView = (GridView) findViewById(R.id.main_grid_view);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_calender) {
            intent = new Intent(this, CalenderActivity.class); //캘린더화면으로 이동
            startActivity(intent);
        } else if (id == R.id.nav_bookmark) {
            intent = new Intent(this, BookmarkActivity.class); //즐겨찾기화면으로 이동
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
