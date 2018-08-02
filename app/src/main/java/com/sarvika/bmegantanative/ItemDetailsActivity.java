package com.sarvika.bmegantanative;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.sarvika.bmegantanative.app.AppController;
import com.sarvika.bmegantanative.com.sarvika.notification.NotificationCountSetClass;
import com.sarvika.bmegantanative.constant.IConstants;
import com.sarvika.bmegantanative.model.CategoryBean;
import com.sarvika.bmegantanative.model.Item;
import com.sarvika.bmegantanative.model.Prop;
import com.sarvika.bmegantanative.util.HttpsTrustManager;
import com.sarvika.bmegantanative.util.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;


public class ItemDetailsActivity extends AppCompatActivity {
    int imagePosition;
    String stringImageUri;
    Item item = null;
    int minteger = 1;
    String itemUrl;
    Float priceValue;

    ImageView mImageView;
    TextView textViewAddToCart;
    TextView textViewBuyNow;
    TextView textViewProdName;
    TextView textViewProdPrice;
    TextView textDescLong;

    private static final String TAG = ItemDetailsActivity.class.getSimpleName();
    private static final String INCREMENT = "Increment";
    private static final String DECREMENT = "Decrement";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        mImageView = (ImageView)findViewById(R.id.imageItemDetail);
        textViewAddToCart = (TextView)findViewById(R.id.text_action_bottom1);
        textViewBuyNow = (TextView)findViewById(R.id.text_action_bottom2);
        textViewProdName = (TextView)findViewById(R.id.prod_name);
        textViewProdPrice = (TextView)findViewById(R.id.prod_price);
        textDescLong = (TextView) findViewById(R.id.desc_long);
        LinearLayout linearLayoutAction1 = (LinearLayout)findViewById(R.id.layout_action1);
        LinearLayout linearLayoutAction2 = (LinearLayout)findViewById(R.id.layout_action2);
        LinearLayout linearLayoutAction3 = (LinearLayout)findViewById(R.id.layout_action3);

        //Getting image uri from previous screen
        if (getIntent() != null) {
            itemUrl = getIntent().getStringExtra(MainActivity.ITEM_URL);
//            stringImageUri = getIntent().getStringExtra(MainActivity.STRING_IMAGE_URI);
//            imagePosition = getIntent().getIntExtra(MainActivity.STRING_IMAGE_POSITION,0);
//            textViewProdName.setText(getIntent().getStringExtra(MainActivity.PROD_NAME));
//            textViewProdPrice.setText(getIntent().getStringExtra(MainActivity.PROD_PRICE));
            item = (Item)getIntent().getSerializableExtra(MainActivity.ITEM_OBJECT);
            showItemDetails(itemUrl);
        }
//        Uri uri = Uri.parse(stringImageUri);
//        mImageView.setImageURI(uri);

//        Glide.with(this).load(stringImageUri).into(mImageView);

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

                    // save the task list to preference
//                    SharedPreferences prefs = getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = prefs.edit();
//                    try {
//                        editor.putString(WISH_LIST, ObjectSerializer.serialize(MainActivity.wishList));
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    editor.commit();

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

    public void increaseInteger(View view) {
        minteger = minteger + 1;
        display(minteger,INCREMENT);
    }

    public void decreaseInteger(View view){
        minteger = minteger - 1;
        if(minteger < 1){
            minteger = 1;
        }
        display(minteger,DECREMENT);
    }

    public void display(int number, String incrementType){
        TextView displayInteger = (TextView) findViewById(
                R.id.integer_number);
        displayInteger.setText("" + number);

        if(incrementType == INCREMENT){
            textViewProdPrice.setText("" + (priceValue * number));
        }else if(incrementType == DECREMENT){
            Float price = Float.valueOf(textViewProdPrice.getText().toString());
            if((price - priceValue) == 0){
                textViewProdPrice.setText("" + priceValue);
            }else {
                textViewProdPrice.setText("" + (price - priceValue));
            }
        }
    }



    private void showItemDetails(String url){
        HttpsTrustManager.allowAllSSL();
        // Creating volley request obj
        String urlCat = IConstants.storeBase + url + "?" + IConstants.suffixJsonType;

        Utilities.showProgressDialog(ItemDetailsActivity.this,"Loading",false, ProgressDialog.STYLE_SPINNER);

        JsonObjectRequest jsonObjReqItemDetail = new JsonObjectRequest(Request.Method.GET,
                urlCat, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        Utilities.hideProgressDialog();
                        if(!TextUtils.isEmpty(response.toString())){
                            try {
                                stringImageUri = response.getString("image");
                                Glide.with(ItemDetailsActivity.this).load(IConstants.storeBase +"store" + stringImageUri).into(mImageView);
                                textViewProdName.setText(response.getString("desc_short"));
                                priceValue = Float.valueOf(response.getString("price"));
                                textViewProdPrice.setText("" +response.getString("price"));
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                    textDescLong.setText(Html.fromHtml(response.getString("desc_long"),Html.FROM_HTML_MODE_LEGACY));
                                } else {
                                    textDescLong.setText(Html.fromHtml(response.getString("desc_long")));
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Utilities.hideProgressDialog();
                            }
                            //            stringImageUri = getIntent().getStringExtra(MainActivity.STRING_IMAGE_URI);
//            imagePosition = getIntent().getIntExtra(MainActivity.STRING_IMAGE_POSITION,0);
//            textViewProdName.setText(getIntent().getStringExtra(MainActivity.PROD_NAME));
//            textViewProdPrice.setText(getIntent().getStringExtra(MainActivity.PROD_PRICE));

                        }// TextUtil
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),""+ error.getMessage() , Toast.LENGTH_LONG).show();
                // hide the progress dialog
                Utilities.hideProgressDialog();
            }
        });


        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReqItemDetail);
    }
}
