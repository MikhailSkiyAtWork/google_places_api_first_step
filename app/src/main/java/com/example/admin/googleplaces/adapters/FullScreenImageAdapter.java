package com.example.admin.googleplaces.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.admin.googleplaces.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mikhail Valuyskiy on 23.07.2015.
 */
public class FullScreenImageAdapter extends PagerAdapter {

    private Context activity_;
    private List<Bitmap> photos_ = new ArrayList<>();
    private LayoutInflater inflater_;

    public FullScreenImageAdapter(Context activity, ArrayList<Bitmap> photos) {
        this.activity_ = activity;
        this.photos_ = photos;
    }

    @Override
    public int getCount() {
        return this.photos_.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imgDisplay;

        inflater_ = (LayoutInflater) activity_.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater_.inflate(R.layout.fullscreen_item, container, false);
        imgDisplay = (ImageView) viewLayout.findViewById(R.id.imgDisplay);
        imgDisplay.setImageBitmap(photos_.get(position));
        ((ViewPager) container).addView(viewLayout);

        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);
    }
}
