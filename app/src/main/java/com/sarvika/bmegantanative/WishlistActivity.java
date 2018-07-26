package com.sarvika.bmegantanative;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sarvika.bmegantanative.model.Item;

import java.util.ArrayList;

import static com.sarvika.bmegantanative.MainActivity.ITEM_OBJECT;
import static com.sarvika.bmegantanative.MainActivity.PROD_NAME;
import static com.sarvika.bmegantanative.MainActivity.PROD_PRICE;
import static com.sarvika.bmegantanative.MainActivity.STRING_IMAGE_POSITION;
import static com.sarvika.bmegantanative.MainActivity.STRING_IMAGE_URI;

public class WishlistActivity extends AppCompatActivity {
    private static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recylerview_list);
        mContext = WishlistActivity.this;

//        ImageUrlUtils imageUrlUtils = new ImageUrlUtils();
        ArrayList<Item> wishList = MainActivity.wishList;
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager recylerViewLayoutManager = new LinearLayoutManager(mContext);

        recyclerView.setLayoutManager(recylerViewLayoutManager);
        recyclerView.setAdapter(new SimpleStringRecyclerViewAdapter(recyclerView, wishList));
    }

    public static class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<WishlistActivity.SimpleStringRecyclerViewAdapter.ViewHolder> {

        private ArrayList<Item> mWishlist;
        private RecyclerView mRecyclerView;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final ImageView mImageView;
            public final LinearLayout mLayoutItem;
            public final ImageView mImageViewWishlist;
            public final TextView mItemName;
            public final TextView mItemDesc;
            public final TextView mItemPrice;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = (ImageView) view.findViewById(R.id.image_wishlist);
                mLayoutItem = (LinearLayout) view.findViewById(R.id.layout_item_desc);
                mImageViewWishlist = (ImageView) view.findViewById(R.id.ic_wishlist);
                mItemName = (TextView)mLayoutItem.findViewById(R.id.item_name);
                mItemDesc = (TextView)mLayoutItem.findViewById(R.id.item_desc);
                mItemPrice = (TextView)mLayoutItem.findViewById(R.id.item_price);
            }
        }

        public SimpleStringRecyclerViewAdapter(RecyclerView recyclerView, ArrayList<Item> wishList) {
            mWishlist = wishList;
            mRecyclerView = recyclerView;
        }

        @Override
        public WishlistActivity.SimpleStringRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_wishlist_item, parent, false);
            return new WishlistActivity.SimpleStringRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onViewRecycled(ViewHolder holder) {

        }

        @Override
        public void onBindViewHolder(final WishlistActivity.SimpleStringRecyclerViewAdapter.ViewHolder holder, final int position) {
//            final Uri uri = Uri.parse(mWishlistImageUri.get(position));
//            holder.mImageView.setImageURI(uri);
            final Item item = mWishlist.get(position);

            Glide.with(mContext).load(item.getImageUrl()).apply(RequestOptions.placeholderOf(R.drawable.loading_spinner)).into(holder.mImageView);
            holder.mItemName.setText(item.getItemName());
            holder.mItemDesc.setText("desc pending");
            holder.mItemPrice.setText(item.getItemDescription());
            holder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(mContext, ItemDetailsActivity.class);
                    intent.putExtra(STRING_IMAGE_URI, item.getImageUrl());
                    intent.putExtra(STRING_IMAGE_POSITION, position);
                    //String price = item.getCurrencySign() + " " + itemBeanList.get(position).getPrice().getValue().getInteger() + "." + itemBeanList.get(position).getPrice().getValue().getDecimal();
                    intent.putExtra(PROD_NAME,item.getItemName());
                    intent.putExtra(PROD_PRICE,item.getItemDescription());
                    intent.putExtra(ITEM_OBJECT,item);
                    mContext.startActivity(intent);
                }
            });

            //Set click action for wishlist
            holder.mImageViewWishlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    ImageUrlUtils imageUrlUtils = new ImageUrlUtils();
//                    imageUrlUtils.removeWishlistImageUri(position);

                    mWishlist.remove(position);
                    notifyDataSetChanged();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mWishlist.size();
        }
    }
}
