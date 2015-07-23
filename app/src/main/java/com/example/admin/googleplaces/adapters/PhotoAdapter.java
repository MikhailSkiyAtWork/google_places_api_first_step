package com.example.admin.googleplaces.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.admin.googleplaces.R;
import com.example.admin.googleplaces.activities.FullscreenActivity;
import com.example.admin.googleplaces.activities.GalleryActivity;
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
    private String placeId_;

    private static class ViewHolder {
        ImageView image_;
    }

    public PhotoAdapter(Context context, List<Bitmap> photos,String placeId) {
        this.context_ = context;
        this.photos_ = photos;
        this.placeId_ = placeId;
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

    class OnImageClickListener implements View.OnClickListener {

        int _postion;

        // constructor
        public OnImageClickListener(int position) {
            this._postion = position;
        }

        @Override
        public void onClick(View v) {
            // on selecting grid view image
            // launch full screen activity
            Intent i = new Intent(context_, FullscreenActivity.class);
            i.putExtra("position", _postion);
            i.putExtra(context_.getResources().getString(R.string.place_id_key), placeId_);
            context_.startActivity(i);
        }
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
        viewHolder.image_.setOnClickListener(new OnImageClickListener(position));

        return convertView;
    }
}
