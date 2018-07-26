package com.sarvika.bmegantanative.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sarvika.bmegantanative.R;
import com.sarvika.bmegantanative.app.AppController;
import com.sarvika.bmegantanative.constant.IConstants;


import org.json.JSONException;
import org.json.JSONObject;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by sujaypidara on 2/1/16.
 */
public class Utilities {

    // Regarding dialog box
    private static ProgressDialog pDialog = null;
    private static int snackBarMaxLine = 2;

    // Regarding get user service
    private static HashMap<String, String> customHeaders;

    // this method will return boolean value for network activity status
    // see http://stackoverflow.com/questions/5474089/how-to-check-currently-internet-connection-is-available-or-not-in-android

    /**
     * To check internet available or not in mobile device
     * @param activityContext Passed activity
     * @return true or false
     */
    public static boolean isNetworkAvailable(Context activityContext) {
        Context context = activityContext;
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            //Toast.makeText(context, "No network found", Toast.LENGTH_LONG).show();
			   Log.d(IConstants.TAG, "No network found");
        } else {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
		      Log.d(IConstants.TAG, "Network Info: "+connectivity.getActiveNetworkInfo());
            if (info != null && info.isConnected()) {
                return true;
            }
        }
        return false;
    }

    /**
     *  To show snackbar specific on network unavailable
     * @param viewOnSnackBar passed view
     */
    public static void showNoNetworkSnackBar(View viewOnSnackBar){

        String snackBarMsg = AppController.getInstance().getApplicationContext().getResources().getString(R.string.error_no_network); //viewOnSnackBar.getResources().getString(R.string.error_no_network)
        String snackBarActionName = AppController.getInstance().getApplicationContext().getResources().getString(R.string.snack_bar_action_close); //viewOnSnackBar.getResources().getString(R.string.snack_bar_action_close)
        Snackbar snackbar = Snackbar
                .make(viewOnSnackBar, snackBarMsg, Snackbar.LENGTH_LONG)
                .setAction(snackBarActionName, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });

        // Changing message text color
        snackbar.setActionTextColor(Color.RED);

        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);

        snackbar.show();
    }

    /**
     * To show snackbar commonly in app wide
     * @param viewOnSnackBar passed view
     * @param snackBarMsg to show msg
     * @param snackBarActionName action name or ""
     * @param timeDelay Snackbar.LENGTH_SHORT | Snackbar.LENGTH_LONG | Snackbar.LENGTH_INDEFINITE
     */
    public static void showCommonkSnackBar(View viewOnSnackBar, String snackBarMsg, String snackBarActionName, int timeDelay){

        Snackbar snackbar = Snackbar
                .make(viewOnSnackBar, snackBarMsg, timeDelay)
                .setAction(snackBarActionName, null);

        if(TextUtils.isEmpty(snackBarActionName)){
            snackbar.setAction(snackBarActionName, null);
        }else{
            snackbar.setAction(snackBarActionName, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // nothing to do here - just will hide only
                }
            });
        }

        // Changing message text color
        snackbar.setActionTextColor(Color.RED);

        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        textView.setMaxLines(snackBarMaxLine);

        snackbar.show();
    }

    /**
     * This will hide keyboard from any passed activity
     * @param activity passed activity
     */
    public static void hideKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static void showProgressDialog(Context context, String msg, boolean isCancelable, int progressDialogStyle) {
//        if (!pDialog.isShowing())
        if(pDialog == null){
            pDialog = new ProgressDialog(context, progressDialogStyle); //ProgressDialog.STYLE_SPINNER
        }
        if (pDialog !=null && !pDialog.isShowing()) {
            pDialog.setMessage(msg);
            pDialog.setCancelable(isCancelable);
            pDialog.show();
        }
    }

    public static void hideProgressDialog() {
        if (pDialog !=null && pDialog.isShowing()) {
            pDialog.hide();
//            pDialog.dismiss();
            pDialog = null;
        }
    }

    public static boolean isEmailValid(String email) {
          //TODO: Replace this with your own logic
//        final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
//        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
//        Matcher matcher = pattern.matcher(email);
//        return matcher.matches();

        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        if(password.trim().length() < 6){
            return false;
        }else{
            return true;
        }
    }




    public static String getSubString(String mainString, String startString, String lastString) {
        String endString = "";
        int startIndex = mainString.indexOf(startString) + startString.length();
        int endIndex = mainString.indexOf(lastString);
//        Log.d(IConstants.TAG, "" + mainString.substring(startIndex, endIndex));
//        endString = mainString.substring(startIndex, endIndex);
        Log.d(IConstants.TAG, "" + TextUtils.substring(mainString, startIndex, endIndex));
        endString = TextUtils.substring(mainString, startIndex, endIndex);
        return endString;
    }


    // shifted to UserInformation.java file
//    public static void logOut(){
//        AppController.getInstance().removeAllCookie();
//    }


    public static String parseJsonDate(String dateStringFromJSON, String dateFormat) {
//        String dateStringFromJSON = "/Date(126521670000-0700)/"; //Date(1446182396357+0000)
        Log.d(IConstants.TAG, "parseJsonDate Received=" + dateStringFromJSON );

        // Remove prefix and suffix extra string information
        String dateString = dateStringFromJSON.replace("/Date(", "").replace(")/", "");

        // Split date and timezone parts
        String[] dateParts = dateString.split("[+-]");

        // The date must be in milliseconds since January 1, 1970 00:00:00 UTC
        // We want to be sure that it is a valid date and time, aka the use of Calendar
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(dateParts[0]));

        // If you want to play with time zone:
        calendar.setTimeZone(TimeZone.getTimeZone(dateParts[1]));

        // Read back and look at it, it must be the same
        long timeinmilliseconds = calendar.getTimeInMillis();

        // Convert it to a Date() object now:
        Date date = calendar.getTime();

        // Convert it to a formatted string as you wish:
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat); //"yyyy.MM.dd HH:mm:ss" //"MM-dd-yyyy"

        // Show it in the Log window:
        Log.d(IConstants.TAG, "parseJsonDate Received date and time=" + formatter.format(date) );

        return formatter.format(date);


//        or in case with timezone required

//        String value = dateStringFromJSON.replaceFirst("\\D+([^\\)]+).+", "$1");
//
//        //Timezone could be either positive or negative
//        String[] timeComponents = value.split("[\\-\\+]");
//        long time = Long.parseLong(timeComponents[0]);
//        int timeZoneOffset = Integer.valueOf(timeComponents[1]) * 36000; // (("0100" / 100) * 3600 * 1000)
//
//        //If Timezone is negative
//        if(value.indexOf("-") > 0){
//            timeZoneOffset *= -1;
//        }
//
//        //Remember that time could be either positive or negative (ie: date before 1/1/1970)
//        time += timeZoneOffset;
////
////        return new Date(time);
//        // Convert it to a Date() object now:
//        Date date1 = new Date(time);
//
//        // Convert it to a formatted string as you wish:
//        SimpleDateFormat formatter1 = new SimpleDateFormat(dateFormat); //"yyyy.MM.dd HH:mm:ss" //"MM-dd-yyyy"
//
//        // Show it in the Log window:
//        Log.d(IConstants.TAG, "parseJsonDate Received date and time=" + formatter1.format(date1) );
//
//        return formatter.format(date);
    }

    // This method will give alert / Toast anywhere in application wide. //Toast.LENGTH_LONG
    public static void showToast(Activity context,CharSequence charSequence, int timeDelay){
//        Toast.makeText(context, charSequence, timeDelay).show();
        Toast toast = Toast.makeText(context, charSequence, timeDelay);
        toast.setGravity(Gravity.CENTER, toast.getXOffset() / 2, toast.getYOffset() / 2);
        toast.show();
    }



}
