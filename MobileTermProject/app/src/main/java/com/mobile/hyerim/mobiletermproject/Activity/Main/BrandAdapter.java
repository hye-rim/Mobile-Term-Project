package com.mobile.hyerim.mobiletermproject.Activity.Main;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobile.hyerim.mobiletermproject.R;

import java.util.List;

/**
 * Created by HYERIM on 2016-12-13.
 */

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.MyViewHolder> {
    private Context mContext;
    private List<Brand> brandList;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView titleEng, titleKo;
        public ImageView brandImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            brandImage = (ImageView) itemView.findViewById(R.id.brand_image);
            titleEng = (TextView)itemView.findViewById(R.id.brand_name_eng);
            titleKo = (TextView)itemView.findViewById(R.id.brand_name_ko);
        }
    }

    public BrandAdapter(Context mContext, List<Brand> brandList) {
        this.mContext = mContext;
        this.brandList = brandList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.brand_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Brand brand = brandList.get(position);
        holder.titleEng.setText(brand.getBrandNameEng());
        holder.titleKo.setText(brand.getBrandNameKo());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.brandImage.setImageDrawable(mContext.getDrawable(brand.getImageResourceId()));
        }else{
            holder.brandImage.setImageDrawable(mContext.getResources().getDrawable(brand.getImageResourceId()));
        }
    }

    @Override
    public int getItemCount() {
        return brandList.size();
    }

}
