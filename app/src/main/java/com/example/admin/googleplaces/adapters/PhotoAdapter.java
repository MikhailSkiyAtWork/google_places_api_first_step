package com.example.admin.googleplaces.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.admin.googleplaces.R;
import com.example.admin.googleplaces.models.Photo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Mikhail Valuyskiy on 17.07.2015.
 */
public class PhotoAdapter extends BaseAdapter {

    private Context context_;
    private List<Bitmap> photos_ = new ArrayList<>();

    private static class ViewHolder {
        ImageView image_;
    }

    public PhotoAdapter(Context context, List<Bitmap> photos) {
        this.context_ = context;
        this.photos_ = photos;
    }

    @Override
    public int getCount() {
        return this.photos_.size();
    }

    @Override
    public Bitmap getItem(int arg0) {
        return photos_.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context_).inflate(R.layout.grid_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.image_ = (ImageView) convertView.findViewById(R.id.imagepart);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.image_.setImageBitmap(photos_.get(position));

        return convertView;
    }

}
