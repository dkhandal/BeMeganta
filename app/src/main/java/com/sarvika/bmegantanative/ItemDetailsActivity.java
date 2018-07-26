package com.sarvika.bmegantanative;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sarvika.bmegantanative.com.sarvika.notification.NotificationCountSetClass;
import com.sarvika.bmegantanative.model.Item;


public class ItemDetailsActivity extends AppCompatActivity {
    int imagePosition;
    String stringImageUri;
    Item item = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        ImageView mImageView = (ImageView)findViewById(R.id.imageItemDetail);
        TextView textViewAddToCart = (TextView)findViewById(R.id.text_action_bottom1);
        TextView textViewBuyNow = (TextView)findViewById(R.id.text_action_bottom2);
        TextView textViewProdName = (TextView)findViewById(R.id.prod_name);
        TextView textViewProdPrice = (TextView)findViewById(R.id.prod_price);
        LinearLayout linearLayoutAction1 = (LinearLayout)findViewById(R.id.layout_action1);
        LinearLayout linearLayoutAction2 = (LinearLayout)findViewById(R.id.layout_action2);
        LinearLayout linearLayoutAction3 = (LinearLayout)findViewById(R.id.layout_action3);

        //Getting image uri from previous screen
        if (getIntent() != null) {
            stringImageUri = getIntent().getStringExtra(MainActivity.STRING_IMAGE_URI);
            imagePosition = getIntent().getIntExtra(MainActivity.STRING_IMAGE_POSITION,0);
            textViewProdName.setText(getIntent().getStringExtra(MainActivity.PROD_NAME));
            textViewProdPrice.setText(getIntent().getStringExtra(MainActivity.PROD_PRICE));
            item = (Item)getIntent().getSerializableExtra("sampleObject");
        }
//        Uri uri = Uri.parse(stringImageUri);
//        mImageView.setImageURI(uri);

        Glide.with(this).load(stringImageUri).into(mImageView);

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    Intent intent = new Intent(ItemDetailsActivity.this, ViewPagerActivity.class);
//                    intent.putExtra("position", imagePosition);
//                    startActivity(intent);

            }
        });

        textViewAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ImageUrlUtils imageUrlUtils = new ImageUrlUtils();
//                imageUrlUtils.addCartListImageUri(stringImageUri);
                if(item != null){
                    MainActivity.wishList.add(item);
                }
                Toast.makeText(ItemDetailsActivity.this,"Item added to cart.", Toast.LENGTH_SHORT).show();
                MainActivity.notificationCountCart++;
                NotificationCountSetClass.setNotifyCount(MainActivity.notificationCountCart);
                finish();
            }
        });

        textViewBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ImageUrlUtils imageUrlUtils = new ImageUrlUtils();
//                imageUrlUtils.addCartListImageUri(stringImageUri);
//                MainActivity.notificationCountCart++;
//                NotificationCountSetClass.setNotifyCount(MainActivity.notificationCountCart);
//                startActivity(new Intent(ItemDetailsActivity.this, CartListActivity.class));

            }
        });

        linearLayoutAction1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shareBody = stringImageUri;
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "\n\n");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent,  getResources().getString(R.string.action_settings)));
            }
        });

        //startActivity(new Intent(MainActivity.this, WishlistActivity.class));

        linearLayoutAction3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.wishList.size() > 0) {
                    startActivity(new Intent(ItemDetailsActivity.this, WishlistActivity.class));
                }else{
                    Toast.makeText(getApplicationContext(),"No item in wishlist",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
