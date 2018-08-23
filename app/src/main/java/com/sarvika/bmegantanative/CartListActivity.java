package com.sarvika.bmegantanative;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sarvika.bmegantanative.model.Item;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.sarvika.bmegantanative.MainActivity.ITEM_OBJECT;
import static com.sarvika.bmegantanative.MainActivity.PROD_NAME;
import static com.sarvika.bmegantanative.MainActivity.PROD_PRICE;
import static com.sarvika.bmegantanative.MainActivity.STRING_IMAGE_POSITION;
import static com.sarvika.bmegantanative.MainActivity.STRING_IMAGE_URI;


public class CartListActivity extends AppCompatActivity {

    private static final String TAG = CartListActivity.class.getSimpleName();
    private static Context mContext;
    private static TextView textViewPaymentPrice;
    static Float totalPrice = 0.0f;
    static boolean isRemoved = false;
    static Float removedPrice = 0.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);
        mContext = CartListActivity.this;

        textViewPaymentPrice = (TextView)findViewById(R.id.text_action_bottom1);

        ArrayList<Item> cartList = MainActivity.cartList;

        totalPrice = 0.0f;

        for(Item item:cartList){
            if(!TextUtils.isEmpty(item.getItemPrice())) { // if item description is not empty
                String[] itemsPrice = item.getItemPrice().split(" ");

                totalPrice = totalPrice + Float.valueOf((itemsPrice[1]));
            }

        }

        textViewPaymentPrice.setText("$ " + String.format("%.02f", totalPrice));

        //Show cart layout based on items
        setCartLayout();

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager recylerViewLayoutManager = new LinearLayoutManager(mContext);

        recyclerView.setLayoutManager(recylerViewLayoutManager);
        recyclerView.setAdapter(new CartListActivity.SimpleStringRecyclerViewAdapter(recyclerView, cartList));
    }

    public class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<CartListActivity.SimpleStringRecyclerViewAdapter.ViewHolder> {

        private ArrayList<Item> mCartlist;
        private RecyclerView mRecyclerView;

        public  class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final ImageView mImageView;
            public final LinearLayout mLayoutItem, mLayoutRemove , mLayoutEdit;
            public final TextView mItemName;
            public final TextView mItemDesc;
            public final TextView mItemPrice;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = (ImageView) view.findViewById(R.id.image_cartlist);
                mLayoutItem = (LinearLayout) view.findViewById(R.id.layout_item_desc);
                mLayoutRemove = (LinearLayout) view.findViewById(R.id.layout_action1);
                mLayoutEdit = (LinearLayout) view.findViewById(R.id.layout_action2);
                mItemName = (TextView)mLayoutItem.findViewById(R.id.item_name_cart);
                mItemDesc = (TextView)mLayoutItem.findViewById(R.id.item_desc_cart);
                mItemPrice = (TextView)mLayoutItem.findViewById(R.id.item_price_cart);
            }
        }

        public SimpleStringRecyclerViewAdapter(RecyclerView recyclerView, ArrayList<Item> cartList) {
            mCartlist = cartList;
            mRecyclerView = recyclerView;
        }

        @Override
        public CartListActivity.SimpleStringRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cartlist_item, parent, false);
            return new CartListActivity.SimpleStringRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onViewRecycled(CartListActivity.SimpleStringRecyclerViewAdapter.ViewHolder holder) {

        }

        @Override
        public void onBindViewHolder(final CartListActivity.SimpleStringRecyclerViewAdapter.ViewHolder holder, final int position) {
//            final Uri uri = Uri.parse(mCartlistImageUri.get(position));
//            holder.mImageView.setImageURI(uri);

            final Item item = mCartlist.get(position);

            Glide.with(mContext).load(item.getImageUrl()).apply(RequestOptions.placeholderOf(R.drawable.loading_spinner)).into(holder.mImageView);
            holder.mItemName.setText(item.getItemName());
            holder.mItemDesc.setText(item.getShortDescription());
            holder.mItemPrice.setText(item.getItemPrice());
            Log.d(TAG,item.getItemPrice());

//            if(!TextUtils.isEmpty(item.getItemDescription())) { // if item description is not empty
//                String[] itemsPrice = item.getItemDescription().split(" ");
//
//                if(!isRemoved) {
//                    totalPrice = totalPrice + Float.valueOf((itemsPrice[1]));
//                }else{
//                    totalPrice = totalPrice - removedPrice;
//                    //isRemoved = false;
//                }
//            }
//
//            textViewPaymentPrice.setText("$ " + String.format("%.02f", totalPrice));

            holder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ItemDetailsActivity.class);
                    intent.putExtra(MainActivity.ITEM_URL, item.getItemUrl());
                    intent.putExtra(STRING_IMAGE_URI, item.getImageUrl());
                    intent.putExtra(STRING_IMAGE_POSITION, position);
                    //String price = item.getCurrencySign() + " " + itemBeanList.get(position).getPrice().getValue().getInteger() + "." + itemBeanList.get(position).getPrice().getValue().getDecimal();
                    intent.putExtra(PROD_NAME,item.getItemName());
                    intent.putExtra(PROD_PRICE,item.getItemPrice());
                    intent.putExtra(ITEM_OBJECT,item);
                    mContext.startActivity(intent);
                }
            });

           //Set click action
            holder.mLayoutRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    isRemoved = true;
                    final Item item = mCartlist.get(position);
                    if(!TextUtils.isEmpty(item.getItemPrice())) { // if item description is not empty
                        String[] itemsPrice = item.getItemPrice().split(" ");
                        removedPrice = Float.valueOf((itemsPrice[1]));
                        totalPrice = totalPrice - removedPrice;
                        textViewPaymentPrice.setText("$ " + String.format("%.02f", totalPrice));
                    }


                    mCartlist.remove(position);
//                    notifyDataSetChanged();
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position,mCartlist.size());
                    //Decrease notification count
                    MainActivity.notificationCountCart--;

                    if(mCartlist.size() == 0){
                        setCartLayout();
                    }


                }
            });

            //Set click action
            holder.mLayoutEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
        }

        @Override
        public int getItemCount() {
            return mCartlist.size();
        }
    }

    public void setCartLayout(){
        LinearLayout layoutCartItems = (LinearLayout) findViewById(R.id.layout_items);
        LinearLayout layoutCartPayments = (LinearLayout) findViewById(R.id.layout_payment);
        LinearLayout layoutCartNoItems = (LinearLayout) findViewById(R.id.layout_cart_empty);

        if(MainActivity.notificationCountCart >0){
            layoutCartNoItems.setVisibility(View.GONE);
            layoutCartItems.setVisibility(View.VISIBLE);
            layoutCartPayments.setVisibility(View.VISIBLE);
        }else {
            layoutCartNoItems.setVisibility(View.VISIBLE);
            layoutCartItems.setVisibility(View.GONE);
            layoutCartPayments.setVisibility(View.GONE);

            Button bStartShopping = (Button) findViewById(R.id.bAddNew);
            bStartShopping.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
    }
}
