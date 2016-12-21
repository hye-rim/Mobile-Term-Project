package com.mobile.hyerim.mobiletermproject.Activity.ProductList;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.mobile.hyerim.mobiletermproject.Activity.Menu.BookmarkActivity;
import com.mobile.hyerim.mobiletermproject.Activity.Menu.CalenderActivity;
import com.mobile.hyerim.mobiletermproject.ClickListener;
import com.mobile.hyerim.mobiletermproject.Data.Product;
import com.mobile.hyerim.mobiletermproject.R;
import com.mobile.hyerim.mobiletermproject.RecyclerTouchListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProductListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "ProductListActivity";
    private Intent intent;

    private String fileName;
    private String brand;
    private List<Product> productList;
    private RecyclerView recyclerView;
    private ProductListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        initView();
        readProductListTextFile();
        initRecyclerView();
    }
    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        recyclerView = (RecyclerView)findViewById(R.id.product_list_recycler_view);
    }

    private void readProductListTextFile() {
        brand = getIntent().getStringExtra("brand");
        fileName = brand + ".txt";
        productList = new ArrayList<Product>();
        Log.d(TAG,fileName);
        BufferedReader reader = null;

        try {
            AssetManager assetManager = getAssets();
            String[] files = assetManager.list("");
            Log.d(TAG, files[0]);
            InputStream input = assetManager.open(fileName);

            reader = new BufferedReader(
                    new InputStreamReader(input, "EUC-KR"));

            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                //process line
                String[] inputData = mLine.split("/");
                productList.add( new Product(brand, inputData[0], Integer.parseInt(inputData[1]),
                        Integer.parseInt(inputData[2])) );
            }
        } catch (IOException e) {
            Log.d(TAG,"IOException");
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }

    }

    private void initRecyclerView() {
        mAdapter = new ProductListAdapter(productList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Product product = productList.get(position);
                Toast.makeText(getApplicationContext(), product.getName() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.product_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        boolean returnValue = super.onOptionsItemSelected(item);

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.action_name_high:
                Collections.sort(productList, new Comparator<Product>(){
                    public int compare(Product obj1, Product obj2){ //이름차순
                        return  obj1.getName().compareTo(obj2.getName());
                    }
                });
                mAdapter.notifyDataSetChanged();
                returnValue = true;
                break;
            case R.id.action_name_low:
                Collections.sort(productList, new Comparator<Product>(){
                    public int compare(Product obj1, Product obj2){ //이름차순 - 가나다역순
                        return  obj2.getName().compareTo(obj1.getName());
                    }
                });
                mAdapter.notifyDataSetChanged();
                returnValue = true;
                break;
            case R.id.action_price_high:
                Collections.sort(productList, new Comparator<Product>(){
                    public int compare(Product obj1, Product obj2){ //가격 높은순 - 내림차순
                        return  (obj1.getPrice() > obj2.getPrice()) ? -1: (obj1.getPrice() > obj2.getPrice()) ? 1: 0;
                    }
                });
                mAdapter.notifyDataSetChanged();
                returnValue = true;
                break;
            case R.id.action_price_low:
                Collections.sort(productList, new Comparator<Product>(){
                    public int compare(Product obj1, Product obj2){ //가격 낮은순 - 오름차순
                        return  (obj1.getPrice() < obj2.getPrice()) ? -1: (obj1.getPrice() > obj2.getPrice()) ? 1: 0;
                    }
                });
                mAdapter.notifyDataSetChanged();
                returnValue = true;
                break;
            case R.id.action_calorie_high:
                Collections.sort(productList, new Comparator<Product>(){
                    public int compare(Product obj1, Product obj2){ //칼로리 높은순 - 내림차순
                        return  (obj1.getCalorie() > obj2.getCalorie()) ? -1: (obj1.getCalorie() > obj2.getCalorie()) ? 1: 0;
                    }
                });
                mAdapter.notifyDataSetChanged();
                returnValue = true;
                break;
            case R.id.action_calorie_low:
                Collections.sort(productList, new Comparator<Product>(){
                    public int compare(Product obj1, Product obj2){ //칼로리 높은순 - 내림차순
                        return  (obj1.getCalorie() < obj2.getCalorie()) ? -1: (obj1.getCalorie() > obj2.getCalorie()) ? 1: 0;
                    }
                });
                mAdapter.notifyDataSetChanged();
                returnValue = true;
                break;
        }

        return returnValue;
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
