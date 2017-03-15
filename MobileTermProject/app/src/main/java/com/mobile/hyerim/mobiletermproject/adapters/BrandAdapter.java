package com.mobile.hyerim.mobiletermproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobile.hyerim.mobiletermproject.R;

/**
 * Created by HYERIM on 2016-12-13.
 */

public class BrandAdapter extends BaseAdapter {
    private Context mContext;
    private final String[] gridViewStringKo;
    private final String[] gridViewStringEng;
    private final int[] gridViewImageId;

    public BrandAdapter(Context context, String[] gridViewStringKo, String[] gridViewStringEng, int[] gridViewImageId) {
        mContext = context;
        this.gridViewStringKo = gridViewStringKo;
        this.gridViewStringEng = gridViewStringEng;
        this.gridViewImageId = gridViewImageId;
    }

    @Override
    public int getCount() {
        return gridViewStringKo.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View gridViewAndroid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            gridViewAndroid = new View(mContext);
            gridViewAndroid = inflater.inflate(R.layout.brand_grid, null);

            TextView textViewKo = (TextView) gridViewAndroid.findViewById(R.id.brand_name_ko);
            TextView textViewEng = (TextView) gridViewAndroid.findViewById(R.id.brand_name_eng);
            ImageView imageView = (ImageView) gridViewAndroid.findViewById(R.id.brand_image);

            textViewKo.setText(gridViewStringKo[i]);
            textViewEng.setText(gridViewStringEng[i]);
            imageView.setImageResource(gridViewImageId[i]);
        } else {
            gridViewAndroid = (View) convertView;
        }

        return gridViewAndroid;
    }
}
