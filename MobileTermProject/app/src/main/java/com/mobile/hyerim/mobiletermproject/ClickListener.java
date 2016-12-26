package com.mobile.hyerim.mobiletermproject;

import android.view.View;

/**
 * Created by HYERIM on 2016-12-22.
 */
public interface ClickListener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);
}
