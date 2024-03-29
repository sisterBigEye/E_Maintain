package com.yy.electric.maintenance.base;

import android.view.LayoutInflater;
import android.view.View;

import com.yy.electric.maintenance.widget.SkidLayout;

public abstract class SkidBaseActivity extends BaseActivity{

    @Override
    public void setContentView(int layoutResID) {
        SkidLayout layout = new SkidLayout(this);
        layout.addView(LayoutInflater.from(this).inflate(layoutResID, layout, false));
        super.setContentView(layout);
    }

    @Override
    public void setContentView(View view) {
        SkidLayout layout = new SkidLayout(this);
        layout.addView(view);
        super.setContentView(layout);
    }
}
