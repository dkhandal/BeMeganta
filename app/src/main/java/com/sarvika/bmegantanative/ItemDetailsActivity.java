package com.sarvika.bmegantanative;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sarvika.bmegantanative.app.AppController;
import com.sarvika.bmegantanative.com.sarvika.notification.NotificationCountSetClass;
import com.sarvika.bmegantanative.constant.IConstants;
import com.sarvika.bmegantanative.model.AttributeDataValue;
import com.sarvika.bmegantanative.model.Attributes;
import com.sarvika.bmegantanative.model.Item;
import com.sarvika.bmegantanative.model.OfferPrices;
import com.sarvika.bmegantanative.util.HttpsTrustManager;
import com.sarvika.bmegantanative.util.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import uk.co.senab.photoview.PhotoViewAttacher;


public class ItemDetailsActivity extends AppCompatActivity  { //implements AdapterView.OnItemSelectedListener
    int imagePosition;
    String stringImageUri;
    String stringImageUriSizeChart;
    Item item = null;
    int minteger = 1;
    String itemUrl;
    Float priceValue;

    private ImageView mImageView;
    private TextView textViewAddToCart;
    private TextView textViewBuyNow;
    private TextView textViewProdName;
    private TextView textViewProdPrice;
    private TextView textViewItemCode;
    private TextView textDescLong;
//    private Spinner spinnerSize;
//    private Spinner spinnerColor;

    private TextView txtLink;

    private static final String TAG = ItemDetailsActivity.class.getSimpleName();
    private static final String INCREMENT = "Increment";
    private static final String DECREMENT = "Decrement";
    String match_text = "";
    public int spinnerCounter = 0;
    //boolean isFirstColor = true;
    //boolean isFirstSize = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        mImageView = (ImageView)findViewById(R.id.imageItemDetail);
        textViewAddToCart = (TextView)findViewById(R.id.text_action_bottom1);
        textViewBuyNow = (TextView)findViewById(R.id.text_action_bottom2);
        textViewProdName = (TextView)findViewById(R.id.prod_name);
        textViewProdPrice = (TextView)findViewById(R.id.prod_price);
        textViewItemCode = (TextView) findViewById(R.id.prod_code);
        textDescLong = (TextView) findViewById(R.id.desc_long);
        LinearLayout linearLayoutAction1 = (LinearLayout)findViewById(R.id.layout_action1);
        LinearLayout linearLayoutAction2 = (LinearLayout)findViewById(R.id.layout_action2);
        LinearLayout linearLayoutAction3 = (LinearLayout)findViewById(R.id.layout_action3);
//        spinnerSize = (Spinner) findViewById(R.id.spinnerSize);
//        spinnerColor = (Spinner)findViewById(R.id.spinnerColor);


        txtLink = (TextView) findViewById(R.id.txtLink);

        // Spinner click listener
//        spinnerSize.setOnItemSelectedListener(this);
//        spinnerColor.setOnItemSelectedListener(this);

        //Getting image uri from previous screen
        if (getIntent().getExtras() != null) {
            itemUrl = getIntent().getStringExtra(MainActivity.ITEM_URL);
//            stringImageUri = getIntent().getStringExtra(MainActivity.STRING_IMAGE_URI);
//            imagePosition = getIntent().getIntExtra(MainActivity.STRING_IMAGE_POSITION,0);
//            textViewProdName.setText(getIntent().getStringExtra(MainActivity.PROD_NAME));
//            textViewProdPrice.setText(getIntent().getStringExtra(MainActivity.PROD_PRICE));
            item = (Item)getIntent().getSerializableExtra(MainActivity.ITEM_OBJECT);
            if(itemUrl != null) {
                showItemDetails(itemUrl);
            }
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
                    MainActivity.cartList.add(item);

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

        Log.d(TAG, "onCreat called");

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

        if(!Utilities.isNetworkAvailable(ItemDetailsActivity.this)) {
            Utilities.showNoNetworkSnackBar(getWindow().getDecorView().getRootView());
            return;
        }

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
                                stringImageUriSizeChart = IConstants.storeBase +"store" + response.getString("sizechart");
                                textViewProdName.setText(response.getString("title"));
                                priceValue = Float.valueOf(response.getString("price"));
                                textViewProdPrice.setText("" +response.getString("price"));
                                textViewItemCode.setText(""+response.getString("item_code"));
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                    textDescLong.setText(Html.fromHtml(response.getString("desc_long").toString(),Html.FROM_HTML_MODE_LEGACY));
                                } else {
                                    textDescLong.setText(Html.fromHtml(response.getString("desc_long").toString()));
                                }
                                final List <Attributes> attributeList = new ArrayList<Attributes>();


                                JSONArray attributesArray= response.getJSONArray("attributes");
                                for(int i=0;i<attributesArray.length();i++){
                                    Attributes attributes = new Attributes();
                                    JSONObject objectAtt= attributesArray.getJSONObject(i);
                                    attributes.setAttributee_id(objectAtt.getString("attributee_id"));
                                    attributes.setAttribute_name(objectAtt.getString("attribute_name"));
                                    attributes.setAttribute_type(objectAtt.getString("attribute_type"));
                                    attributes.setAttribute_dataname(objectAtt.getString("attribute_dataname"));
                                    attributes.setAttribute_screenname(objectAtt.getString("attribute_screenname"));
                                    attributes.setAttribute_dropname(objectAtt.getString("attribute_dropname"));



                                    ArrayList<AttributeDataValue> attributeDataValue2 = new ArrayList<AttributeDataValue>();
                                    JSONArray attributesDataValue= objectAtt.getJSONArray("data_value");
                                        for(int j=0;j<attributesDataValue.length();j++) {

                                            JSONObject objectDataValue= attributesDataValue.getJSONObject(j);
                                            AttributeDataValue attributeDataValueObj = new AttributeDataValue();
                                            attributeDataValueObj.setOptionid(objectDataValue.getString("optionid"));
                                            attributeDataValueObj.setDdtext(objectDataValue.getString("ddtext"));
                                            attributeDataValueObj.setCode(objectDataValue.getString("code"));

                                            attributeDataValue2.add(attributeDataValueObj);
                                            attributes.setData_value(attributeDataValue2);
                                        }
                                    attributeList.add(attributes);
                                }

                                final List <OfferPrices> offerPricesList = new ArrayList<OfferPrices>();
                                JSONArray offerPriceArray = response.getJSONArray("offerprice");
                                for(int i=0;i<offerPriceArray.length();i++){
                                    OfferPrices offerPrices = new OfferPrices();
                                    JSONObject objectofferPrice = offerPriceArray.getJSONObject(i);
                                    offerPrices.setItemcode(objectofferPrice.getString("itemcode"));
                                    offerPrices.setItemprice(objectofferPrice.getString("itemprice"));
                                    offerPrices.setCurrencyid(objectofferPrice.getString("currencyid"));
                                    offerPricesList.add(offerPrices);
                                }

                                match_text=textViewItemCode.getText().toString()+".";
                                final List<Spinner> spinners = new ArrayList<Spinner>();
                                if(attributeList.size() > 0) {
//                                    spinnerColor.setVisibility(View.VISIBLE);
//                                    spinnerSize.setVisibility(View.VISIBLE);

                                    txtLink.setVisibility(View.VISIBLE);

                                    LinearLayout linearLayoutSpinner = findViewById(R.id.layoutSpinner);
                                    final List<List<AttributeDataValue>> attributeDataListComplete = new ArrayList<List<AttributeDataValue>>();
                                    List<String> sizeList = new ArrayList<String>();
                                    for (int k = 0; k < attributeList.size() ; k++) {
                                         final List<AttributeDataValue> attributeDataList = new ArrayList<AttributeDataValue>();
                                         final Spinner spinner = new Spinner(ItemDetailsActivity.this);
                                        spinners.add(spinner);

                                        spinner.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//                                        Random r = new Random();
//                                        int randomNumber = r.nextInt(100);
                                        spinner.setId(1000 * (k + 1));

                                        final Attributes attributesObj = attributeList.get(k);

                                            AttributeDataValue attributeDataValue1 = new AttributeDataValue();
                                            attributeDataValue1.setDdtext(attributesObj.getAttribute_dropname());
                                            attributeDataList.add(attributeDataValue1);

                                            for (int l = 0; l < attributesObj.getData_value().size(); l++) {

                                                attributeDataList.add(attributesObj.getData_value().get(l));

                                            }
                                            ArrayAdapter colorAdapter = new ArrayAdapter(ItemDetailsActivity.this, android.R.layout.simple_spinner_item, attributeDataList);
                                            colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                            spinner.setAdapter(colorAdapter);
                                            spinner.setBackgroundResource(android.R.drawable.btn_dropdown);

                                        attributeDataListComplete.add(attributeDataList);

                                        linearLayoutSpinner.addView(spinner);

                                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            @Override
                                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                                if(!attributesObj.getAttribute_name().equalsIgnoreCase(attributeDataList.get(position).getDdtext())){
                                                for(int ij = 0;ij<spinners.size();ij++)
                                                {

                                                    String spinnervalue = spinners.get(ij).getSelectedItem().toString();
                                                    Toast.makeText(ItemDetailsActivity.this, "aa namee" + attributeList.get(ij).getAttribute_name()+" total spinner val "+spinnervalue, Toast.LENGTH_LONG).show();
                                                    if(!attributeList.get(ij).getAttribute_name().equalsIgnoreCase(spinnervalue)) {
                                                        match_text = match_text + "-" + spinnervalue;
                                                        Toast.makeText(ItemDetailsActivity.this, " " + match_text, Toast.LENGTH_LONG).show();
                                                    }

                                                }
                                                   //Toast.makeText(ItemDetailsActivity.this, "Selected Item: " + parent.getId() + " " + attributeDataList.get(position).getDdtext(), Toast.LENGTH_SHORT).show();
                                            }
                                            }

                                            @Override
                                            public void onNothingSelected(AdapterView<?> parent) {
                                            }
                                        });
                                    }

                                    if(attributeList.size() > 2){
                                        linearLayoutSpinner.setOrientation(LinearLayout.VERTICAL);
                                    }else{
                                        linearLayoutSpinner.setOrientation(LinearLayout.HORIZONTAL);
                                    }


                                }else{
//                                    spinnerColor.setVisibility(View.GONE);
//                                    spinnerSize.setVisibility(View.GONE);

                                    txtLink.setVisibility(View.INVISIBLE);
                                }





                            } catch (JSONException e) {
                                e.printStackTrace();
                                Utilities.hideProgressDialog();
                            }
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

//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        spinnerCounter = spinnerCounter + 1;
//        String item = parent.getItemAtPosition(position).toString();
//
//        switch(parent.getId()) {
//            case R.id.spinnerColor:
//                // Showing selected spinner item
//                if(spinnerCounter > 4) {
//                    Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
//                }
//                break;
//             case R.id.spinnerSize:
//                 // Showing selected spinner item
//                 if(spinnerCounter > 4) {
//                     Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
//                 }
//                 break;
//        }
//
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//
//    }


    public void onClickSizeChart(View v)
    {

        final Dialog nagDialog = new Dialog(ItemDetailsActivity.this,android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        nagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        nagDialog.setCancelable(true);
        nagDialog.setContentView(R.layout.preview_image);
        Button btnClose = (Button)nagDialog.findViewById(R.id.btnIvClose);
        ImageView ivPreview = (ImageView)nagDialog.findViewById(R.id.iv_preview_image);

        Glide.with(ItemDetailsActivity.this).load(stringImageUriSizeChart).apply(RequestOptions.placeholderOf(R.drawable.no_image)).into(ivPreview);

        PhotoViewAttacher pAttacher;
        pAttacher = new PhotoViewAttacher(ivPreview);
        pAttacher.update();

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                nagDialog.dismiss();
            }
        });
        nagDialog.show();

    }


}
