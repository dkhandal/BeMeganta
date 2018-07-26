package com.sarvika.bmegantanative;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.ColorRes;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.sarvika.bmegantanative.adapter.CustomListAdapter;
import com.sarvika.bmegantanative.adapter.ExpandableListAdapter;
import com.sarvika.bmegantanative.adapter.ItemAdapter;
import com.sarvika.bmegantanative.adapter.SlidingImage_Adapter;
import com.sarvika.bmegantanative.app.AppController;
import com.sarvika.bmegantanative.com.sarvika.notification.NotificationCountSetClass;
import com.sarvika.bmegantanative.constant.IConstants;
import com.sarvika.bmegantanative.model.CategoryBean;
import com.sarvika.bmegantanative.model.CustomObject;
import com.sarvika.bmegantanative.model.ImageModel;
import com.sarvika.bmegantanative.model.Item;
import com.sarvika.bmegantanative.model.ItemBean;
import com.sarvika.bmegantanative.model.Labels;
import com.sarvika.bmegantanative.model.MenuOptions;
import com.sarvika.bmegantanative.model.Movie;
import com.sarvika.bmegantanative.model.Price;
import com.sarvika.bmegantanative.model.Prop;
import com.sarvika.bmegantanative.model.Value;
import com.sarvika.bmegantanative.util.ClickableViewPager;
import com.sarvika.bmegantanative.util.HttpsTrustManager;
import com.sarvika.bmegantanative.util.Utilities;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
//https://www.raywenderlich.com/127544/android-gridview-getting-started
    private NavigationView navigationView;

    // Log tag
    private static final String TAG = MainActivity.class.getSimpleName();
    public static ArrayList<Item> wishList = new ArrayList<Item>();

    // Movies json url
    // Device - 192.168.1.13
    // Emulator - 10.0.2.2
    // Live API - https://api.androidhive.info/json/movies.json
    // http://localhost:3000/movies

//    private static final String testList = "https://192.168.0.100/preview/store.html?tpt=json_en&vid=20180531204&cid=53494";
//    public static final String storeBase = "https://192.168.0.100/preview/";
//    public static final String suffixJsonType = "&tpt=json_en";
//    public static final String urlCategory = storeBase + "mystore2";
//    private static final String urlList = "https://api.myjson.com/bins/xnsje"; // "http://10.0.2.2:3000/movies"
    private static final String urlMenu = "https://api.myjson.com/bins/ji6xq"; //http://10.0.2.2:3000/menu";
//    private static final String urlCategory = "https://api.myjson.com/bins/7fz82"; //"http://10.0.2.2:3000/category";
    private static final String urlimageSliderList = "https://api.myjson.com/bins/wpxvg"; //http://10.0.2.2:3000/slidingimage";
    private static final String urlCategoryNew = "https://shopstar2-test.halo.com/preview/store.html";//?vid=20171112571&cid=117488";
    private ProgressDialog pDialog;
    private List<Movie> movieList = new ArrayList<Movie>();
//    private ListView listView;
    private CustomListAdapter adapter;
    private ImageView imgTwitter;
    private ImageView imgInstagram;
    private View footer;

    // view pager
//    private static ViewPager mPager;
    private ClickableViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private ArrayList<ImageModel> imageModelArrayList;

//    private int[] myImageList = new int[]{R.drawable.tmobile1, R.drawable.tmobile2,
//            R.drawable.tmobile3,R.drawable.tmobile4
//            ,R.drawable.tmobile5,R.drawable.tmobile6};

    private List<MenuOptions> menuOptionsList = new ArrayList<MenuOptions>();
    private List<ItemBean> itemBeanList = new ArrayList<ItemBean>();;

    //Expandable list
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<CustomObject> listDataHeader;
    HashMap<String, List<CustomObject>> listDataChild;

    String vid = "20170504134";//"20170504134";//"20180531204"; "20171004499"

    GridView gridView;
    Item[] items;
    ItemAdapter itemAdapter;

    public static final String STRING_IMAGE_URI = "ImageUri";
    public static final String STRING_IMAGE_POSITION = "ImagePosition";
    public static final String PROD_NAME = "ProdName";
    public static final String PROD_PRICE = "ProdPrice";
    public static final String ITEM_OBJECT = "ItemObject";
    public static int notificationCountCart = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorAccent));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.logo);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



//        Item item1 = new Item("One","Desc1", "https://pbs.twimg.com/media/DgYiv_2VMAEz3Vz.jpg");
//        Item item2 = new Item("Two","Desc2", "https://pbs.twimg.com/media/DfrNJxAU8AAZOr6.jpg");
//        Item item3 = new Item("Three","Desc3", "https://pbs.twimg.com/media/Dfn3clMUYAA1qM0.jpg");
//        Item item4 = new Item("One","Desc2", "https://pbs.twimg.com/media/DgnzVirWkAE7Yw_.jpg");
//        Item item5 = new Item("One","Desc2", "https://pbs.twimg.com/media/Dfhl981XkAIc7_u.jpg");
//        Item item6 = new Item("One","Desc2", "https://pbs.twimg.com/media/Dfggd_9UYAAOnqe.jpg");
//        Item item7 = new Item("One","Desc2", "https://pbs.twimg.com/media/Dfggd_9UYAAOnqe.jpg");
//        Item item8 = new Item("One","Desc2", "https://pbs.twimg.com/media/DgYiv_2VMAEz3Vz.jpg");
//        Item item9 = new Item("One","Desc2", "https://pbs.twimg.com/media/DfrNJxAU8AAZOr6.jpg");
//        Item item10 = new Item("One","Desc2", "https://pbs.twimg.com/media/Dfggd_9UYAAOnqe.jpg");
//        Item[] items = new Item[]{ item1,item2,item3,item4,item5,item6,item7,item8,item9,item10 };

        gridView = (GridView)findViewById(R.id.gridview);
        //ItemAdapter itemAdapter = new ItemAdapter(this, items);
        //gridView.setAdapter(itemAdapter);

        // List view code
//        View listViewLayout = findViewById(R.id.listViewLayout);
//
//        listView = listViewLayout.findViewById(R.id.list);

//        footer = getLayoutInflater().inflate(R.layout.home_footer, null);
//        footer.setVisibility(View.INVISIBLE);
//        listView.addFooterView(footer);

//        adapter = new CustomListAdapter(this, movieList);
//        listView.setAdapter(adapter);

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Movie movieItem = (Movie) parent.getAdapter().getItem(position);
//                Toast.makeText(getApplicationContext(),movieItem.getTitle() + " " + movieItem.getThumbnailUrl(),Toast.LENGTH_LONG).show();
//            }
//        });


        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        prepareListData1();

        // preparing list data
//        prepareListData1();
//        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
//        // setting list adapter
//        expListView.setAdapter(listAdapter);



        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
//                 Toast.makeText(getApplicationContext(),
//                 "Group Clicked " + listDataHeader.get(groupPosition),
//                 Toast.LENGTH_SHORT).show();
//                TextView textView = (TextView)findViewById(R.id.lblListHeader);
//                 if(listDataHeader.get(groupPosition).toString() == textView.getText().toString()){
//                     textView.setTextColor(getResources().getColor(R.color.colorAccent));
//                 }else{
//                     textView.setTextColor(getResources().getColor(R.color.colorWhite));
//                 }

//                Toast.makeText(
//                        getApplicationContext(),
//                        "Category Id: " + listDataHeader.get(groupPosition).getCid() + " URL: " + listDataHeader.get(groupPosition).getURL(), Toast.LENGTH_SHORT).show();

                String catUrl = listDataHeader.get(groupPosition).getURL();
                if(!TextUtils.isEmpty(catUrl)){
                    loadItems(catUrl);
                    // if required to close drawer
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            DrawerLayout drawer = findViewById(R.id.drawer_layout);
                            if (drawer.isDrawerOpen(GravityCompat.START)) {
                                drawer.closeDrawer(GravityCompat.START);
                            }
                        }
                    }, 2000);

                }


                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
//                Toast.makeText(getApplicationContext(),
//                        listDataHeader.get(groupPosition) + " Expanded",
//                        Toast.LENGTH_SHORT).show();
            }

        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
//                Toast.makeText(getApplicationContext(),
//                        listDataHeader.get(groupPosition) + " Collapsed",
//                        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
//                Toast.makeText(
//                        getApplicationContext(),
//                        listDataHeader.get(groupPosition)
//                                + " : "
//                                + listDataChild.get(
//                                listDataHeader.get(groupPosition)).get(
//                                childPosition), Toast.LENGTH_SHORT)
//                        .show();

//                Toast.makeText(
//                        getApplicationContext(),
//                        "Category Id: " + listDataHeader.get(groupPosition).getCid()
//                                + " : "
//                                + "ItemId: " + listDataChild.get(listDataHeader.get(groupPosition).getName()).get(childPosition).getCid()
//                                + " URL: " + listDataChild.get(listDataHeader.get(groupPosition).getName()).get(childPosition).getURL(), Toast.LENGTH_SHORT).show();

                String subcatUrl = listDataChild.get(listDataHeader.get(groupPosition).getName()).get(childPosition).getURL();
                if(!TextUtils.isEmpty(subcatUrl)){
                    loadItems(subcatUrl);
                    // if required to close drawer
//                    new Handler().postDelayed(new Runnable() {
//                        public void run() {
                            DrawerLayout drawer = findViewById(R.id.drawer_layout);
                            if (drawer.isDrawerOpen(GravityCompat.START)) {
                                drawer.closeDrawer(GravityCompat.START);
                            }
//                        }
//                    }, 2000);

                }


//                TextView textView = (TextView)findViewById(R.id.lblListItem);
//                if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).toString() == textView.getText().toString()){
//                    textView.setTextColor(getResources().getColor(R.color.colorAccent));
//                }else{
//                    textView.setTextColor(getResources().getColor(R.color.colorWhite));
//                }

                return false;
            }
        });



        // fill list view through volley
//        if(!Utilities.isNetworkAvailable(MainActivity.this)) {
//            Utilities.showNoNetworkSnackBar(getWindow().getDecorView().getRootView());
//
//        }else{
//            pDialog = new ProgressDialog(this);
//            // Showing progress dialog before making http request
//            pDialog.setMessage("Loading...");
//            pDialog.show();
//
//            fillListView();
//
//        }

//        this.runOnUiThread(new Runnable() {
//            public void run() {
                // make menu's
//                makeMenuOption();
//            }
//        });

//        if(!Utilities.isNetworkAvailable(MainActivity.this)) {
//            Utilities.showNoNetworkSnackBar(getWindow().getDecorView().getRootView());
//            defaultMenus();
//        }else {
//            makeMenuOption();
//        }

        //defaultMenus();


        // Twitter and instagram click event listner

//        imgTwitter = (ImageView)findViewById(R.id.imgTwitter);
//        imgInstagram = (ImageView)findViewById(R.id.imgInstgram);

//        imgTwitter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Snackbar.make(v, "Twitter page will redirect", Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
//                imgTwitter.setImageResource(R.drawable.ic_twitter_pink);
//                imgInstagram.setImageResource(R.drawable.ic_instagram_white);
//                Intent viewIntent =
//                        new Intent("android.intent.action.VIEW",
//                                Uri.parse("https://twitter.com/TMobile"));
//                startActivity(viewIntent);
//            }
//        });


//        imgInstagram.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Snackbar.make(v, "Instagram page will redirect", Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
//                imgInstagram.setImageResource(R.drawable.ic_instagram_pink);
//                imgTwitter.setImageResource(R.drawable.ic_twitter_white);
//                Intent viewIntent =
//                        new Intent("android.intent.action.VIEW",
//                                Uri.parse("https://www.instagram.com/tmobile/"));
//                startActivity(viewIntent);
//            }
//        });

        //view pager
        //imageModelArrayList = new ArrayList<>();
        //fillSliderImages();
//        imageModelArrayList = populateList();
//
//        init();




//        LayoutInflater factory = LayoutInflater.from(this);
//
////text_entry is an Layout XML file containing two text field to display in alert dialog
//        final View textEntryView = factory.inflate(R.layout.layout_vid, null);
//
//        final EditText input1 = (EditText) textEntryView.findViewById(R.id.editTextVid);
//        final EditText input2 = (EditText) textEntryView.findViewById(R.id.editTextUserName);
//        final EditText input3 = (EditText) textEntryView.findViewById(R.id.editTextPassword);
//
//
////        input1.setText("DefaultValue", TextView.BufferType.EDITABLE);
////        input2.setText("DefaultValue", TextView.BufferType.EDITABLE);
////        input3.setText("DefaultValue", TextView.BufferType.EDITABLE);
//
//        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
//        alert.setIcon(R.drawable.logo).setTitle("Enter the Text:").setView(textEntryView).setPositiveButton("Ok",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog,
//                                        int whichButton) {
//
//                        Log.i("AlertDialog","TextEntry 1 Entered "+input1.getText().toString());
//                        Log.i("AlertDialog","TextEntry 2 Entered "+input2.getText().toString());
//                        Log.i("AlertDialog","TextEntry 3 Entered "+input3.getText().toString());
//                        /* User clicked OK so do some stuff */
//                    }
//                }).setNegativeButton("Cancel",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog,
//                                        int whichButton) {
//                        /*
//                         * User clicked cancel so do some stuff
//                         */
//                    }
//                });
//        alert.show();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                if(items != null) {

                    Item item = items[position];

                    Intent intent = new Intent(MainActivity.this, ItemDetailsActivity.class);
                    intent.putExtra(STRING_IMAGE_URI, items[position].getImageUrl());
                    intent.putExtra(STRING_IMAGE_POSITION, position);
                    String price = itemBeanList.get(position).getCurrencySign() + " " + itemBeanList.get(position).getPrice().getValue().getInteger() + "." + itemBeanList.get(position).getPrice().getValue().getDecimal();
                    intent.putExtra(PROD_NAME,item.getItemName());
                    intent.putExtra(PROD_PRICE,price);
                    intent.putExtra(ITEM_OBJECT,item);

                    startActivity(intent);


//                    item.toggleFavorite();

                    // This tells the GridView to redraw itself
                    // in turn calling your BooksAdapter's getView method again for each cell
//                    itemAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        imgTwitter.setImageResource(R.drawable.ic_twitter_white);
//        imgInstagram.setImageResource(R.drawable.ic_instagram_white);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

//        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close){
//
//            @Override
//            public void onDrawerOpened(View drawerView) {
//                super.onDrawerOpened(drawerView);
//            }
//            @Override
//            public void onDrawerClosed(View drawerView) {
//                super.onDrawerClosed(drawerView);
//            }
//        }; // Drawer Toggle Object Made
//        drawer.setDrawerListener(mDrawerToggle);
//        mDrawerToggle.syncState();
//
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
//                    drawer.closeDrawer(Gravity.RIGHT);
//                } else {
//                    drawer.openDrawer(Gravity.RIGHT);
//                }
//            }
//        });




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        // Get the notifications MenuItem and
        // its LayerDrawable (layer-list)
        MenuItem item = menu.findItem(R.id.cart);
        NotificationCountSetClass.setAddToCart(MainActivity.this, item,notificationCountCart);
        // force the ActionBar to relayout its MenuItems.
        // onCreateOptionsMenu(Menu) will be called again.
        invalidateOptionsMenu();
        return super.onPrepareOptionsMenu(menu);

//        menu.clear();
//        for (int i = 0; i < menuOptionsList.size(); i++) {
//            MenuOptions menuOptions = new MenuOptions();
//            menuOptions = menuOptionsList.get(i);
//
//            if(menuOptions.getIsShowWithIcon() == true ) {
//                if(menuOptions.getIsShow() == true) {
//                    menu.add(0, menuOptions.getItemId(), Menu.NONE, menuOptions.getText()).setIcon(menuOptions.getThumbnailUrl()).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
//                }
//
//            }else {
//                if(menuOptions.getIsShow() == true) {
//                    menu.add(0, menuOptions.getItemId(), Menu.NONE, menuOptions.getText()).setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
//                }
//            }
//
//        }
//        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
        String msg = "";

        switch(id){
            case R.id.login: //0:
                msg = "Login";
                Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(i);
                break;
            case R.id.search: //1:
                msg = "Search";
                break;
            case R.id.cart: //2:
                msg = "Cart";
                break;
            case R.id.settings: //3:
                msg = "Settings";
                break;
            case R.id.edit: //4:
                msg = "Edit";
                break;
            case R.id.logout:  //5:
                msg = "Logout";
                break;
        }
        Toast.makeText(this,msg+" Checked",Toast.LENGTH_LONG).show();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        resetAllMenuItemsTextColor(navigationView);
        setTextColorForMenuItem(item, R.color.colorWhite);

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_apparel) {
            // Handle the camera action
            setTextColorForMenuItem(item, R.color.colorAccent);

            boolean b=!navigationView.getMenu().findItem(R.id.nav_one).isVisible();
            //setting submenus visible state
            navigationView.getMenu().findItem(R.id.nav_one).setVisible(b);
            navigationView.getMenu().findItem(R.id.nav_two).setVisible(b);
            navigationView.getMenu().findItem(R.id.nav_three).setVisible(b);
            navigationView.getMenu().findItem(R.id.nav_four).setVisible(b);
            return true;
        } else if (id == R.id.nav_accessories) {
            setTextColorForMenuItem(item, R.color.colorAccent);

            boolean b=!navigationView.getMenu().findItem(R.id.nav_five).isVisible();
            //setting submenus visible state
            navigationView.getMenu().findItem(R.id.nav_five).setVisible(b);
            navigationView.getMenu().findItem(R.id.nav_six).setVisible(b);
            navigationView.getMenu().findItem(R.id.nav_seven).setVisible(b);
            navigationView.getMenu().findItem(R.id.nav_eight).setVisible(b);
            return true;
        } else if (id == R.id.nav_new) {
            setTextColorForMenuItem(item, R.color.colorAccent);
        } else if (id == R.id.nav_sale) {
            setTextColorForMenuItem(item, R.color.colorAccent);
        } else if (id == R.id.nav_certificate) {
            setTextColorForMenuItem(item, R.color.colorAccent);
        }

        // sub menu's
        else if(id == R.id.nav_one){
            Toast.makeText(this,"nav One"+" Checked",Toast.LENGTH_LONG).show();
            setTextColorForMenuItem(item, R.color.colorAccent);
        }else if(id == R.id.nav_two){
            Toast.makeText(this,"nav Two"+" Checked",Toast.LENGTH_LONG).show();
            setTextColorForMenuItem(item, R.color.colorAccent);
        }else if(id == R.id.nav_three){
            Toast.makeText(this,"nav Three"+" Checked",Toast.LENGTH_LONG).show();
            setTextColorForMenuItem(item, R.color.colorAccent);
        }else if(id == R.id.nav_four){
            Toast.makeText(this,"nav Four"+" Checked",Toast.LENGTH_LONG).show();
            setTextColorForMenuItem(item, R.color.colorAccent);
        }else if(id == R.id.nav_five){
            Toast.makeText(this,"nav Five"+" Checked",Toast.LENGTH_LONG).show();
            setTextColorForMenuItem(item, R.color.colorAccent);
        }else if(id == R.id.nav_six){
            Toast.makeText(this,"nav six"+" Checked",Toast.LENGTH_LONG).show();
            setTextColorForMenuItem(item, R.color.colorAccent);
        }else if(id == R.id.nav_seven){
            Toast.makeText(this,"nav Seven"+" Checked",Toast.LENGTH_LONG).show();
            setTextColorForMenuItem(item, R.color.colorAccent);
        }else if(id == R.id.nav_eight){
            Toast.makeText(this,"nav Eight"+" Checked",Toast.LENGTH_LONG).show();
            setTextColorForMenuItem(item, R.color.colorAccent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Set text color for menu item:
    private void setTextColorForMenuItem(MenuItem menuItem, @ColorRes int color) {
        SpannableString spanString = new SpannableString(menuItem.getTitle().toString());
        spanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, color)), 0, spanString.length(), 0);
        menuItem.setTitle(spanString);
    }

    //Reset all menu items text color:
    private void resetAllMenuItemsTextColor(NavigationView navigationView) {
        for (int i = 0; i < navigationView.getMenu().size(); i++)
            setTextColorForMenuItem(navigationView.getMenu().getItem(i), R.color.colorWhite);
    }



    //fill the list view
//    private void fillListView(){
//        // Creating volley request obj
//        JsonArrayRequest movieReq = new JsonArrayRequest(urlList,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        Log.d(TAG, response.toString());
//                        hidePDialog();
//
//                        // Parsing json
//                        for (int i = 0; i < response.length(); i++) {
//                            try {
//
//                                JSONObject obj = response.getJSONObject(i);
//                                Movie movie = new Movie();
//                                movie.setThumbnailUrl(obj.getString("image"));
//
//                                movie.setTitle(obj.getString("title"));
//
//                                // adding movie to movies array
//                                movieList.add(movie);
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//
//                        }
//
//                        // notifying list adapter about data changes
//                        // so that it renders the list view with updated data
//                        adapter.notifyDataSetChanged();
//                        footer.setVisibility(View.VISIBLE);
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.d(TAG, "Error: " + error.getMessage());
//                hidePDialog();
//            }
//            }) {
//
////            @Override
////            public Map<String, String> getHeaders() throws AuthFailureError {
////                HashMap<String, String> headers = new HashMap<String, String>();
////                headers.put("Content-Type", "application/json; charset=utf-8");
////                return headers;
////            }
//
//                @Override
//                public String getBodyContentType() {
//                    return "application/json";
//                } //application/x-www-form-urlencoded
//
//            };
//
//        // Adding request to request queue
//        AppController.getInstance().addToRequestQueue(movieReq);
//    }

    //make toolbar
    private void makeMenuOption(){
        // Creating volley request obj
        JsonArrayRequest menuReq = new JsonArrayRequest(urlMenu,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        //hidePDialog();

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                MenuOptions menuOptions = new MenuOptions();
                                menuOptions.setItemId(obj.getInt("itemId"));
                                menuOptions.setText(obj.getString("text"));
                                menuOptions.setThumbnailUrl(obj.getInt("thumnailUrl"));
                                menuOptions.setIsShowWithIcon(obj.getBoolean("showWithIcon"));
                                menuOptions.setIsShow(obj.getBoolean("isShow"));

                                // adding movie to movies array
                                menuOptionsList.add(menuOptions);



                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        invalidateOptionsMenu();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                //hidePDialog();

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(menuReq);
    }

    // view pager
//    private ArrayList<ImageModel> populateList(){
//
//        ArrayList<ImageModel> list = new ArrayList<>();
//
//        for(int i = 0; i < 6; i++){
//            ImageModel imageModel = new ImageModel();
//            imageModel.setImage_drawable(myImageList[i]);
//            list.add(imageModel);
//        }
//
//        return list;
//    }

    private void init() {

        mPager = (ClickableViewPager) footer.findViewById(R.id.pager);
        mPager.setAdapter(new SlidingImage_Adapter(MainActivity.this,imageModelArrayList));
//https://demonuts.com/image-slider-slideshow/
        CirclePageIndicator indicator = (CirclePageIndicator)
                findViewById(R.id.indicator);

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
        indicator.setRadius(5 * density);

        NUM_PAGES =imageModelArrayList.size();

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

        // on click slider image
        mPager.setOnItemClickListener(new ClickableViewPager.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                int imgModelList = imageModelArrayList.get(position).getId();
                Toast.makeText(getApplicationContext(),imgModelList +"",Toast.LENGTH_LONG).show();
            }
        });

    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

//    private void defaultMenus(){
//        MenuOptions menuOptions1 = new MenuOptions();
//        menuOptions1.setItemId(0);
//        menuOptions1.setText("Login");
//        menuOptions1.setThumbnailUrl(0);
//        menuOptions1.setIsShowWithIcon(true);
//        menuOptions1.setIsShow(true);
//
//        MenuOptions menuOptions2 = new MenuOptions();
//        menuOptions2.setItemId(1);
//        menuOptions2.setText("Search");
//        menuOptions2.setThumbnailUrl(1);
//        menuOptions2.setIsShowWithIcon(true);
//        menuOptions2.setIsShow(true);
//
//        MenuOptions menuOptions3 = new MenuOptions();
//        menuOptions3.setItemId(2);
//        menuOptions3.setText("Cart");
//        menuOptions3.setThumbnailUrl(2);
//        menuOptions3.setIsShowWithIcon(true);
//        menuOptions3.setIsShow(true);
//        NotificationCountSetClass.setAddToCart(MainActivity.this, menuOptions3,notificationCountCart);
//
//        MenuOptions menuOptions4 = new MenuOptions();
//        menuOptions4.setItemId(3);
//        menuOptions4.setText("Setting");
//        menuOptions4.setThumbnailUrl(0);
//        menuOptions4.setIsShowWithIcon(false);
//        menuOptions4.setIsShow(true);
//
//        MenuOptions menuOptions5 = new MenuOptions();
//        menuOptions5.setItemId(4);
//        menuOptions5.setText("Edit");
//        menuOptions5.setThumbnailUrl(0);
//        menuOptions5.setIsShowWithIcon(false);
//        menuOptions5.setIsShow(true);
//
//        MenuOptions menuOptions6 = new MenuOptions();
//        menuOptions6.setItemId(5);
//        menuOptions6.setText("Logout");
//        menuOptions6.setThumbnailUrl(0);
//        menuOptions6.setIsShowWithIcon(false);
//        menuOptions6.setIsShow(true);
//
//
//        menuOptionsList.add(menuOptions1);
//        menuOptionsList.add(menuOptions2);
//        menuOptionsList.add(menuOptions3);
//        menuOptionsList.add(menuOptions4);
//        menuOptionsList.add(menuOptions5);
//        menuOptionsList.add(menuOptions6);
//    }



    /*
     * Preparing the list data
     */
//    private void prepareListData() {
//        listDataHeader = new ArrayList<String>();
//        listDataChild = new HashMap<String, List<String>>();
//
//        // Adding child data
//        listDataHeader.add("Top 250");
//        listDataHeader.add("Now Showing");
//        listDataHeader.add("Coming Soon..");
//
//        // Adding child data
//        List<String> top250 = new ArrayList<String>();
//        top250.add("The Shawshank Redemption");
//        top250.add("The Godfather");
//        top250.add("The Godfather: Part II");
//        top250.add("Pulp Fiction");
//        top250.add("The Good, the Bad and the Ugly");
//        top250.add("The Dark Knight");
//        top250.add("12 Angry Men");
//
//        List<String> nowShowing = new ArrayList<String>();
//        nowShowing.add("The Conjuring");
//        nowShowing.add("Despicable Me 2");
//        nowShowing.add("Turbo");
//        nowShowing.add("Grown Ups 2");
//        nowShowing.add("Red 2");
//        nowShowing.add("The Wolverine");
//
//        List<String> comingSoon = new ArrayList<String>();
//        comingSoon.add("2 Guns");
//        comingSoon.add("The Smurfs 2");
//        comingSoon.add("The Spectacular Now");
//        comingSoon.add("The Canyons");
//        comingSoon.add("Europa Report");
//
//        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
//        listDataChild.put(listDataHeader.get(1), nowShowing);
//        listDataChild.put(listDataHeader.get(2), comingSoon);
//    }


    private void prepareListData1(){
        HttpsTrustManager.allowAllSSL();
        // Creating volley request obj
        String urlCat = IConstants.urlCategory + "?vid=" + vid + "&" + IConstants.suffixJsonType;

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                urlCat, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();
                        if(!TextUtils.isEmpty(response.toString())){
                        try {

                            if (response != null) {

                                final ArrayList<CategoryBean> categoryList= new ArrayList<CategoryBean>();
                                if (response.length() > 0) {

                                    JSONArray jsonArray = response.getJSONArray("childs");

                                    for (int jIndex = 0; jIndex < jsonArray.length(); jIndex++) {

                                        JSONObject innerObject = jsonArray.getJSONObject(jIndex);
        //                                            Log.w("innerObject", innerObject.toString());
                                        Iterator<?> keys = innerObject.keys();
                                        while (keys.hasNext()) {
                                            String key = (String) keys.next();
                                            String value = innerObject.getString(key);
                                            if(value.equalsIgnoreCase("menu1") ){
                                                Log.w("innerObject-----", innerObject.toString());
                                                JSONArray jsonArrayMenu = innerObject.getJSONArray("childs");
                                                for (int kIndex = 0; kIndex < jsonArrayMenu.length(); kIndex++) {

                                                    JSONObject innerObjectChild = jsonArrayMenu.getJSONObject(kIndex);

                                                    CategoryBean categoryBean = new CategoryBean();
                                                    categoryBean.setName(innerObjectChild.getString("name"));
                                                    categoryBean.setVid(innerObjectChild.getString("vid"));
                                                    categoryBean.setCid(innerObjectChild.getString("cid"));
                                                    categoryBean.setPosition(innerObjectChild.getString("position"));
                                                    categoryBean.setDescription(innerObjectChild.getString("description"));
                                                    categoryBean.setURL(innerObjectChild.getString("URL"));

                                                    Prop prop = new Prop();
                                                    JSONArray jsonArrayProperty = innerObjectChild.getJSONArray("properties");
                                                    for (int i = 0; i < jsonArrayProperty.length(); i++) {
                                                        JSONObject row = jsonArrayProperty.getJSONObject(i);

                                                        prop.setPropname(row.getString("propname"));
                                                        prop.setPropvalue(row.getString("propvalue"));

                                                    }
                                                    categoryBean.setProperties(prop);


                                                    // for sub categories
                                                    ArrayList<CategoryBean> categorySubList = new ArrayList<CategoryBean>();
                                                    JSONArray jsonArrayMenuChild = innerObjectChild.getJSONArray("childs");
                                                    for (int lIndex = 0; lIndex < jsonArrayMenuChild.length(); lIndex++) {

                                                        JSONObject innerObjectMenuChild = jsonArrayMenuChild.getJSONObject(lIndex);

                                                        if (jsonArrayMenuChild !=null && jsonArrayMenuChild.length() > 0) {

                                                            CategoryBean subCategoryBean = new CategoryBean();

                                                            subCategoryBean.setName(innerObjectMenuChild.getString("name"));
                                                            subCategoryBean.setVid(innerObjectMenuChild.getString("vid"));
                                                            subCategoryBean.setCid(innerObjectMenuChild.getString("cid"));
                                                            subCategoryBean.setPosition(innerObjectMenuChild.getString("position"));
                                                            subCategoryBean.setDescription(innerObjectMenuChild.getString("description"));
                                                            subCategoryBean.setURL(innerObjectMenuChild.getString("URL"));

                                                            Prop propnew = new Prop();
                                                            JSONArray jsonArrayPropertyNew = innerObjectMenuChild.getJSONArray("properties");
                                                            for (int i = 0; i < jsonArrayPropertyNew.length(); i++) {
                                                                JSONObject row = jsonArrayPropertyNew.getJSONObject(i);

                                                                propnew.setPropname(row.getString("propname"));
                                                                propnew.setPropvalue(row.getString("propvalue"));

                                                            }
                                                            subCategoryBean.setProperties(propnew);
                                                            categorySubList.add(subCategoryBean);


                                                            categoryBean.setChild(categorySubList);

                                                        }


                                                    }
                                                    categoryList.add(categoryBean);
                                                }
                                            }
                                        }
                                    }
                                }

//                         MainActivity.this.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
                                prepareListData(categoryList);
//                            }
//                        });

                            }
                        }catch (JSONException je) {
                            Log.e("Error: ","" + je.getLocalizedMessage());
                        }
                        }// TextUtil
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),""+ error.getMessage() , Toast.LENGTH_LONG).show();
                // hide the progress dialog
                hidePDialog();
            }
        });


        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }


    //fill the slider images
    private void fillSliderImages(){
        // Creating volley request obj
        JsonArrayRequest slidingImgReq = new JsonArrayRequest(urlimageSliderList,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
//                        hidePDialog();

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                ImageModel imageModel = new ImageModel();
                                imageModel.setSlidingImgUrl(obj.getString("image"));
                                imageModel.setId(obj.getInt("id"));

                                // adding movie to movies array
                                imageModelArrayList.add(imageModel);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        // render the images in slider way
                        init();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();
            }
        }) {

//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Content-Type", "application/json; charset=utf-8");
//                return headers;
//            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            } //application/x-www-form-urlencoded

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(slidingImgReq);
    }

    public void showToast(final String msg){
        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(getApplicationContext(),""+ msg , Toast.LENGTH_LONG).show();
            }
        });

    }

    private void prepareListData(ArrayList<CategoryBean> categoryList) {
        listDataHeader = new ArrayList<CustomObject>();
        listDataChild = new HashMap<String, List<CustomObject>>();

        for (int i = 0; i < categoryList.size(); i++) {
            CategoryBean categoryBean = new CategoryBean();
            categoryBean = categoryList.get(i);
            CustomObject customObject = new CustomObject(categoryBean.getName(), categoryBean.getVid(), categoryBean.getCid(), categoryBean.getPosition(), categoryBean.getDescription(), categoryBean.getURL(), i);
            listDataHeader.add(customObject);

            List<CustomObject> childList = new ArrayList<CustomObject>();
            if (categoryBean.getChild() != null && categoryBean.getChild().size() > 0) {
                for (int j = 0; j < categoryBean.getChild().size(); j++) {
                    CategoryBean categoryBeanChild = new CategoryBean();
                    categoryBeanChild = categoryBean.getChild().get(j);
                    CustomObject customObjectChild = new CustomObject(categoryBeanChild.getName(), categoryBeanChild.getVid(), categoryBeanChild.getCid(), categoryBeanChild.getPosition(), categoryBeanChild.getDescription(), categoryBeanChild.getURL(), j);
                    childList.add(customObjectChild);
                }
            }

            listDataChild.put(((CustomObject) listDataHeader.get(i)).getName(), childList);
            Log.i("Info:", "" + listDataChild.size());

        }
        listAdapter = new ExpandableListAdapter(MainActivity.this, listDataHeader, listDataChild);
        // setting list adapter
        expListView.setAdapter(listAdapter);

        if (listDataHeader != null){
            String catUrl = listDataHeader.get(0).getURL();
            if (!TextUtils.isEmpty(catUrl)) {
                loadItems(catUrl);
            }
        }
    }

    private void loadItems(String urlItem){


        itemBeanList.clear();

        String urlItems = IConstants.storeBase + urlItem + "?" + IConstants.suffixJsonType;

        JsonArrayRequest menuReq = new JsonArrayRequest(urlItems,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        //hidePDialog();

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                ItemBean itemBean = new ItemBean();
                                itemBean.setId(obj.getInt("id"));
                                itemBean.setCode(obj.getString("code"));
                                itemBean.setTitle(obj.getString("title"));
                                itemBean.setTeaser(obj.getString("teaser"));
                                itemBean.setTrimmed(obj.getBoolean("trimmed"));
                                itemBean.setUrl(obj.getString("url"));
                                itemBean.setImage(obj.getString("image"));
                                itemBean.setCount(obj.getString("count"));
                                itemBean.setItemStarRating(obj.getString("itemStarRating"));
                                itemBean.setStoreFeatured(obj.getString("store_featured"));
                                itemBean.setCatFeatured(obj.getString("cat_featured"));
                                itemBean.setNewItem(obj.getString("new_item"));
                                itemBean.setOnSale(obj.getString("on_sale"));
                                itemBean.setSelectButton(obj.getBoolean("select_button"));
                                itemBean.setCurrencySign(obj.getString("currency_sign"));

                                Labels labels = new Labels();

                                JSONObject jsonObjectLabel = obj.getJSONObject("labels");
                                labels.setListPrice(jsonObjectLabel.getString("list_price"));
                                labels.setNoPrice(jsonObjectLabel.getString("no_price"));
                                labels.setYouSave(jsonObjectLabel.getString("you_save"));
                                labels.setSavePlus(jsonObjectLabel.getString("save_plus"));
                                labels.setPoints(jsonObjectLabel.getString("points"));
                                labels.setCallprice(jsonObjectLabel.getString("callprice"));
                                labels.setQuickinfo(jsonObjectLabel.getString("quickinfo"));
                                labels.setItemCode(jsonObjectLabel.getString("item_code"));

                                itemBean.setLabels(labels);

                                Price price = new Price();

                                JSONObject jsonObjectPrice = obj.getJSONObject("price");
                                price.setType(jsonObjectPrice.getString("type"));
                                JSONObject jsonObjectValue = jsonObjectPrice.getJSONObject("value");
                                Value value = new Value();
                                value.setInteger(jsonObjectValue.getString("integer"));
                                value.setDecimal(jsonObjectValue.getString("decimal"));
                                price.setValue(value);

                                itemBean.setPrice(price);


                                // adding movie to movies array
                                itemBeanList.add(itemBean);



                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        items = new Item[itemBeanList.size()];
                        for (int i=0; i < itemBeanList.size(); i++){
                            String price = itemBeanList.get(i).getCurrencySign() + " " + itemBeanList.get(i).getPrice().getValue().getInteger() + "." + itemBeanList.get(i).getPrice().getValue().getDecimal();
                            Item item = new Item(itemBeanList.get(i).getTitle(),price,IConstants.storeBase + itemBeanList.get(i).getImage());
                            items[i] = item;
                        }
                        itemAdapter = new ItemAdapter(MainActivity.this, items);
                        gridView.setAdapter(itemAdapter);

                        Log.d(TAG, ""+itemBeanList.size());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                //hidePDialog();

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(menuReq);


    }

}




