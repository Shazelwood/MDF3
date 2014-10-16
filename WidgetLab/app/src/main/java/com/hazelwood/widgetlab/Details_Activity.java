package com.hazelwood.widgetlab;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Hazelwood on 10/14/14.
 */
public class Details_Activity extends Activity implements Details_Fragment.DetailsFragmentListener {
    public static final String TAG = "DETAIL_ACTIVITY_TAG";

    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String selectedState = prefs.getString("PREF_LOCATION_LIST","Not Assigned");
        Network network = new Network();
        Weather oldWeather = new Weather();
        fragmentManager =getFragmentManager();


        if (selectedState.equals("1")){
            if (isConnected()){
                network.execute("http://api.wunderground.com/api/c51861cc030c674b/forecast10day/q/VA/Hampton.json");
            } else {

            }
        }
        else if (selectedState.equals("2")){
            if (isConnected()){
                network.execute("http://api.wunderground.com/api/c51861cc030c674b/forecast10day/q/MD/Baltimore.json");
            } else {

            }
        }
        else if (selectedState.equals("3")){
            if (isConnected()){
                network.execute("http://api.wunderground.com/api/c51861cc030c674b/forecast10day/q/DC/Washington.json");
            } else {

            }
        }
    }

    @Override
    public void populateDetails(Weather weather) {

    }

    private class Network extends AsyncTask<String, Void, Weather> {
        ProgressDialog progressDialog;
        ArrayList<Weather> weatherArrayList;
        Weather weather;
        String todayCondition;
        int todayTemperature, todayHigh, todayLow;
        int tomorrowTemperature, yesterdayTemeperature;

        @Override
        protected void onPreExecute() {
            weather = new Weather();
            weatherArrayList = new ArrayList<Weather>();
            progressDialog = new ProgressDialog(Details_Activity.this);
            progressDialog.setProgressStyle((ProgressDialog.STYLE_HORIZONTAL));
            progressDialog.setMessage("Loading...");
            progressDialog.setIndeterminate(true);
            progressDialog.setProgressNumberFormat("Copyright (c) 2014 The New York Times Company. All Rights Reserved.");
            progressDialog.setProgressPercentFormat(null);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Weather doInBackground(String... strings) {
            String jsonString = "";
            try{
                //URL connection check
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream is = connection.getInputStream();
                jsonString = IOUtils.toString(is);
                is.close();
                connection.disconnect();
            }catch (MalformedURLException e){
                e.printStackTrace();

            }catch (IOException e) {
                e.printStackTrace();
            }

            try{
                //JSON
                JSONObject outerObject = new JSONObject(jsonString);
                JSONObject items = outerObject.getJSONObject("forecast");
                JSONObject simpleforecast = items.getJSONObject("simpleforecast");
                JSONArray forecastDay = simpleforecast.getJSONArray("forecastday");

                JSONObject today = forecastDay.getJSONObject(0);

                if (today.has("conditions")){
                    todayCondition = today.getString("conditions");
                    Log.d(TAG, todayCondition);
                }
                if (today.has("high")){
                    JSONObject high = today.getJSONObject("high");
                    if (high.has("fahrenheit")){
                        todayHigh = high.getInt("fahrenheit");
                    }
                }
                if (today.has("low")){
                    JSONObject low = today.getJSONObject("low");
                    if (low.has("fahrenheit")){
                        todayLow = low.getInt("fahrenheit");
                    }
                }

                todayTemperature = (todayHigh + todayLow)/2;

                JSONObject tomorrow = forecastDay.getJSONObject(1);
                int toHigh = 100;
                int toLow= 100;
                if (tomorrow.has("high")){
                    JSONObject high = tomorrow.getJSONObject("high");
                    if (high.has("fahrenheit")){
                        toHigh = high.getInt("fahrenheit");
                    }
                }
                if (tomorrow.has("low")){
                    JSONObject high = tomorrow.getJSONObject("low");
                    if (high.has("fahrenheit")){
                        toLow = high.getInt("fahrenheit");
                    }
                }

                tomorrowTemperature = (toHigh + toLow)/2;

                JSONObject yesterday = forecastDay.getJSONObject(2);
                int yesHigh = 100;
                int yesLow = 100;
                if (yesterday.has("high")){
                    JSONObject high = yesterday.getJSONObject("high");
                    if (high.has("fahrenheit")){
                        yesHigh = high.getInt("fahrenheit");
                    }

                }
                if (yesterday.has("low")){
                    JSONObject high = yesterday.getJSONObject("low");
                    if (high.has("fahrenheit")){
                        yesLow = high.getInt("fahrenheit");
                    }
                }

                yesterdayTemeperature = (yesHigh + yesLow)/2;
                DateFormat outFormat = new SimpleDateFormat("HH:mm:ss");
                outFormat.setTimeZone(TimeZone.getTimeZone("EST"));
                long time = System.currentTimeMillis();
                Date d = new Date(time);
                String result = outFormat.format(d);

                weather = new Weather(todayCondition, result, todayTemperature, yesterdayTemeperature, tomorrowTemperature, todayHigh, todayLow);

//                weatherArrayList.add(new Weather(todayCondition, result, todayTemperature, yesterdayTemeperature, tomorrowTemperature));


            }catch (JSONException e){
                e.printStackTrace();

            }
            return weather;
        }

        @Override
        protected void onPostExecute(Weather s) {

            fragmentManager.beginTransaction().replace(R.id.detail_container, Details_Fragment.newInstance(s), Details_Fragment.TAG).commit();
            super.onPostExecute(s);
            progressDialog.dismiss();



        }
    }

    private boolean isConnected(){
        ConnectivityManager mgr = (ConnectivityManager)getSystemService((Context.CONNECTIVITY_SERVICE));

        if (mgr != null){
            NetworkInfo info = mgr.getActiveNetworkInfo();

            if (info != null && info.isConnected()){
                return true;
            }
        }
        return false;
    }

}
