package com.mobile.hyerim.mobiletermproject.Activity.Product;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.mobile.hyerim.mobiletermproject.Data.BookmarkDBHelper;
import com.mobile.hyerim.mobiletermproject.Data.Product;
import com.mobile.hyerim.mobiletermproject.R;

public class ProductActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "ProductActivity";
    Product product;
    boolean isBookmark = false;
    private Menu menu;

    private TextView textViewBrand, textViewName, textViewCalorei, textViewPrice;

    BookmarkDBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        product = (Product) getIntent().getSerializableExtra("item");
        initView();
        Log.d(TAG, product.toString());
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

        dbHelper = new BookmarkDBHelper(getApplicationContext(), "Bookmark.db", null, 1);

        textViewBrand = (TextView)findViewById(R.id.text_menu_tv_brand);
        textViewBrand.setText(product.getBrand());
        textViewName = (TextView)findViewById(R.id.text_menu_tv_item);
        textViewName.setText(product.getName());
        textViewCalorei = (TextView)findViewById(R.id.product_tv_kcal);
        textViewCalorei.setText(product.getCalorie()+"kcal");
        textViewPrice = (TextView)findViewById(R.id.product_tv_price);
        textViewPrice.setText(product.getPrice()+"원");
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
        this.menu = menu;
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.product, menu);

        MenuItem item = menu.getItem(0);
        String result = dbHelper.getResult();
        if(result.contains(product.getName())) {
            isBookmark = true;
        }
        if(!isBookmark)
            item.setIcon(getResources().getDrawable(R.drawable.ic_menu_bookmark_white));
        else
            item.setIcon(getResources().getDrawable(R.drawable.ic_menu_bookmark_primary));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_bookmark) {
            //Add This line in your code
            if(isBookmark) {
                item.setIcon(getResources().getDrawable(R.drawable.ic_menu_bookmark_white));

                dbHelper.delete(product.getName());
                Log.d(TAG,dbHelper.getResult());

                isBookmark = !isBookmark;
            }
            else {
                item.setIcon(getResources().getDrawable(R.drawable.ic_menu_bookmark_primary));

                dbHelper.insert(product);
                Log.d(TAG,dbHelper.getResult());

                isBookmark = !isBookmark;
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
