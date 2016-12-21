package com.mobile.hyerim.mobiletermproject.Activity.ProductList;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobile.hyerim.mobiletermproject.Data.Product;
import com.mobile.hyerim.mobiletermproject.R;

import java.util.List;

/**
 * Created by HYERIM on 2016-12-22.
 */

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.MyViewHolder> {
    private List<Product> productList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView brand, name,  price, calorie;

        public MyViewHolder(View view) {
            super(view);
            brand = (TextView) view.findViewById(R.id.product_list_tv_brand);
            name = (TextView) view.findViewById(R.id.product_list_tv_item);
            price = (TextView) view.findViewById(R.id.product_list_tv_price);
            calorie = (TextView) view.findViewById(R.id.product_list_tv_calorie);
        }
    }


    public ProductListAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.brand.setText(product.getBrand());
        holder.name.setText(product.getName());
        holder.price.setText(product.getPrice() + "원");
        holder.calorie.setText(product.getCalorie() + "kcal");
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
