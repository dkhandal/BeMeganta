package com.sarvika.bmegantanative.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.bumptech.glide.Glide;
import com.sarvika.bmegantanative.R;
import com.sarvika.bmegantanative.app.AppController;
import com.sarvika.bmegantanative.model.Movie;

public class CustomListAdapter extends BaseAdapter {
    private  Context mContext;
//    private Activity activity;
    private LayoutInflater inflater;
    private List<Movie> movieItems;
//    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CustomListAdapter(Context context, List<Movie> movieItems) {
//        this.activity = activity;
        mContext = context;
        this.movieItems = movieItems;
    }

    @Override
    public int getCount() {
        return movieItems.size();
    }

    @Override
    public Object getItem(int location) {
        return movieItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);

/*        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail);*/
        ImageView thumbNail = convertView.findViewById(R.id.thumbnail);
        TextView titleOverImg = convertView.findViewById((R.id.titleoverimg));


        // getting movie data for the row
        Movie m = movieItems.get(position);

        // title
        if(!m.getTitle().isEmpty()) {
            titleOverImg.setText(m.getTitle());
        }else{
            titleOverImg.setVisibility(View.INVISIBLE);
        }

        // thumbnail image
//        thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);
        Glide.with(mContext).load(m.getThumbnailUrl()).into(thumbNail);

        return convertView;
    }

}
