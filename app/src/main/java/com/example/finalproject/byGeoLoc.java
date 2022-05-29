package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class byGeoLoc extends AppCompatActivity {

    private RequestQueue queue;
    String lat;
    String lon;
    String key = "5b1b578abf8b4ecc9b2f6e86e3b0ee17";
    String apiUrl = "https://api.openweathermap.org/data/2.5/weather?lat="+ lat + "&lon=" + lon + "&appid=" + key + "&units=metric";
    String weatherDescripion;
    double temperature;
    double windspeed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Instantiating volley queue
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_by_geo_loc);
        queue = Volley.newRequestQueue(this);

        // Receiving bundle and checking if it has values
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            lat = bundle.getString("LAT:");
            lon = bundle.getString("LON:");
            apiUrl = "https://api.openweathermap.org/data/2.5/weather?lat="+ lat + "&lon=" + lon + "&appid=" + key + "&units=metric";
        }

        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiUrl,
                response -> {
                    //Toast.makeText(this,response,Toast.LENGTH_LONG).show();
                    parseJsonAndUpdateUI(response);  	//<= Sub function which parses the json object
                },
                volleyError -> {
                    Toast.makeText(this,"Error",Toast.LENGTH_LONG).show();
                });

        // Sending request by adding it to queue
        queue.add(stringRequest);

    }

    public void parseJsonAndUpdateUI(String response){
        try {
            JSONObject weather = new JSONObject(response);
            weatherDescripion = weather.getJSONArray("weather").getJSONObject(0).getString("description");
            temperature = weather.getJSONObject("main").getDouble("temp");
            windspeed = weather.getJSONObject("wind").getDouble("speed");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        TextView weather = findViewById(R.id.weather);
        weather.setText(weatherDescripion);
        TextView temp = findViewById(R.id.temp);
        temp.setText(Double.toString(temperature) + " Celsius");
        TextView wind = findViewById(R.id.wind);
        wind.setText(Double.toString(windspeed) + "m/s");

    }
}