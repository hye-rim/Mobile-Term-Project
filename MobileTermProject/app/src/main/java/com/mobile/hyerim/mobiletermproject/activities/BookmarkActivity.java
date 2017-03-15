package com.mobile.hyerim.mobiletermproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.mobile.hyerim.mobiletermproject.adapters.BookmarkAdapter;
import com.mobile.hyerim.mobiletermproject.listeners.ClickListener;
import com.mobile.hyerim.mobiletermproject.controllers.BookmarkDBHelper;
import com.mobile.hyerim.mobiletermproject.models.Product;
import com.mobile.hyerim.mobiletermproject.R;
import com.mobile.hyerim.mobiletermproject.listeners.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

public class BookmarkActivity extends AppCompatActivity {
    private static final String TAG = "BookmarkActivity";
    private Intent intent;

    private String fileName;
    private String brand;
    private List<Product> productList;
    private RecyclerView recyclerView;
    private BookmarkAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_bookmark);
        initView();
        readProductListDB();
        initRecyclerView();

    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView)findViewById(R.id.product_list_recycler_view);
    }

    private void readProductListDB() {
        productList = new ArrayList<Product>();

        BookmarkDBHelper dbHelper = new BookmarkDBHelper(getApplicationContext(), "Bookmark.db", null, 1);
        String result = dbHelper.getResult();
        Log.d(TAG,result);
        if( !result.isEmpty() ){
            String[] bookmarkList = result.split("\n");
            for(String bookmark : bookmarkList){
                Log.d(TAG,bookmark);
                String bookmarkInfo[] = bookmark.split("/");
                productList.add( new Product(bookmarkInfo[0], bookmarkInfo[1], Integer.parseInt(bookmarkInfo[2]),
                        Integer.parseInt(bookmarkInfo[3])) );
            }
        }else{
            productList.add( new Product("","", 0, 0) );
        }
    }

    private void initRecyclerView() {
        mAdapter = new BookmarkAdapter(productList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Product product = productList.get(position);
                intent = new Intent(getApplicationContext(), ProductActivity.class); //상품화면으로 이동
                intent.putExtra("item", product);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        readProductListDB();
        initRecyclerView();
    }

    @Override
    public boolean onSupportNavigateUp(){
        //code it to launch an intent to the activity you want
        finish();
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
