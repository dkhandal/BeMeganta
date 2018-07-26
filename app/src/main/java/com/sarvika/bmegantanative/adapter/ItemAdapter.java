package com.sarvika.bmegantanative.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sarvika.bmegantanative.MainActivity;
import com.sarvika.bmegantanative.R;
import com.sarvika.bmegantanative.model.Item;

public class ItemAdapter extends BaseAdapter {
    private final Context mContext;
    private final Item[] items;
    private final boolean isFavorite = false;

    // 1
    public ItemAdapter(Context context, Item[] items) {
        this.mContext = context;
        this.items = items;
    }

    // 2
    @Override
    public int getCount() {
        return items.length;
    }

    // 3
    @Override
    public long getItemId(int position) {
        return 0;
    }

    // 4
    @Override
    public Object getItem(int position) {
        return null;
    }

    // 5
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 1
        final Item item = items[position];

        // 2
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.linearlayout_items, null);
        }

        // 3
        final ImageView imageView = (ImageView)convertView.findViewById(R.id.imageview_cover_art);
        final TextView nameTextView = (TextView)convertView.findViewById(R.id.textview_book_name);
        final TextView authorTextView = (TextView)convertView.findViewById(R.id.textview_book_author);
        final ImageView imageViewFavorite = (ImageView)convertView.findViewById(R.id.imageview_favorite);

        // 4
        Glide.with(mContext).load(item.getImageUrl()).apply(RequestOptions.placeholderOf(R.drawable.loading_spinner)).into(imageView);
        nameTextView.setText(item.getItemName());
        authorTextView.setText(item.getItemDescription());

        imageViewFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!MainActivity.wishList.contains(item)) {
                    MainActivity.wishList.add(item);
                    imageViewFavorite.setImageResource(R.drawable.ic_favorite_black_18dp);
                }else{
                    if(MainActivity.wishList.size() > 0){
                        MainActivity.wishList.remove(item);
                        imageViewFavorite.setImageResource(R.drawable.ic_favorite_border_black_18dp);
                    }
                }

//                if(isFavorite){
//
//                    imageViewFavorite.setImageResource(R.drawable.ic_favorite_black_18dp );
//                }else {
//                    if(MainActivity.wishList.size() > 0) {
//                        MainActivity.wishList.remove(item);
//                        imageViewFavorite.setImageResource(R.drawable.ic_favorite_border_black_18dp);
//                    }
//                }



            }
        });


        imageViewFavorite.setImageResource(item.getIsFavorite() ? R.drawable.ic_favorite_black_18dp : R.drawable.ic_favorite_border_black_18dp);
        return convertView;
    }

}