package com.mobile.hyerim.mobiletermproject.Activity.Main;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import com.mobile.hyerim.mobiletermproject.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BrandAdapter brandAdapter;
    private List<Brand> brandList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        setRecycler();
    }

    private void setRecycler() {
        brandList = new ArrayList<>();
        brandAdapter = new BrandAdapter(this,brandList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this,2); //2열
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(brandAdapter);

        prepareBrand();
    }

    private void prepareBrand() {
        int[] brands = new int[]{
                R.drawable.starbucks_logo,
                R.drawable.caffebene_logo,
                R.drawable.coffeebean_logo,
                R.drawable.starbucks_logo,
                R.drawable.caffebene_logo,
                R.drawable.coffeebean_logo,
                R.drawable.starbucks_logo,
                R.drawable.caffebene_logo,
                R.drawable.coffeebean_logo
        };

        Brand b= new Brand("Starbucks", "스타벅스", brands[0]);
        brandList.add(b);

        b= new Brand("Caffebene", "카페베네", brands[1]);
        brandList.add(b);

        b= new Brand("Coffebean", "커피빈", brands[2]);
        brandList.add(b);

        b= new Brand("Angelinus", "엔제리너스", brands[3]);
        brandList.add(b);

        b= new Brand("Yogerpresso", "요거프레소", brands[4]);
        brandList.add(b);

        b= new Brand("Ediya", "이디야", brands[5]);
        brandList.add(b);

        b= new Brand("Tomntoms", "탐앤탐스", brands[6]);
        brandList.add(b);

        b= new Brand("TwosomePlace", "투썸플레이스", brands[7]);
        brandList.add(b);

        b= new Brand("Pascucci", "파스쿠찌", brands[8]);
        brandList.add(b);

        brandAdapter.notifyDataSetChanged();
    }

    private void initView() {
        recyclerView = (RecyclerView)findViewById(R.id.main_recycler_view);
    }

    //RecyclerView item decoration - give equal margin around grid item
    //http://www.androidhive.info/2016/05/android-working-with-card-view-and-recycler-view/
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration{
        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    //Converting dp to pixel
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
