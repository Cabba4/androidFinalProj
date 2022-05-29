package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements LocationListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    double latitude;
    double longitude;

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {

        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putDouble("lat",latitude);
        savedInstanceState.putDouble("lon",longitude);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        if(savedInstanceState!=null)
        {
            latitude = savedInstanceState.getDouble("lat");
            longitude = savedInstanceState.getDouble("lon");
            TextView locationView = (TextView) findViewById(R.id.editTextTextPersonName3);
            locationView.setText("Lat: " + df.format(latitude) + " Long: " + df.format(longitude) );
        }
        else
        {
            Toast.makeText(this,"Error", Toast.LENGTH_LONG);
        }
    }

    private static final DecimalFormat df = new DecimalFormat("0.00");

    public void getGeoLoc(View view) {

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED )
        {
            ActivityCompat.requestPermissions(this,new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},0);
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,0, this);

        if( latitude!=0.0 && longitude!=0.0 )
        {
            Bundle b = new Bundle();
            b.putString("LAT:", String.valueOf(latitude));
            b.putString("LON:",String.valueOf(longitude));
            Intent intent = new Intent(this,byGeoLoc.class);
            intent.putExtras(b);
            startActivity(intent);
        }

    }

    public void getCityName(View view) {

        Intent intent = new Intent(this,byLocation.class);
        TextView textview = findViewById(R.id.editTextTextPersonName2);
        String intentValue = textview.getText().toString();
        intent.putExtra("CITY_NAME:",intentValue);

        startActivity(intent);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

        latitude = location.getLatitude();
        longitude = location.getLongitude();
        TextView locationView = (TextView) findViewById(R.id.editTextTextPersonName3);
        locationView.setText("Lat: " + df.format(latitude) + " Long: " + df.format(longitude) );

    }

    public void goToSo (View view) {
        goToUrl ( "https://github.com/Cabba4/androidFinalProj");
    }

    private void goToUrl(String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

    @Override
    protected void onStart(){
        super.onStart();
    }
    @Override
    protected void onPause(){
        super.onPause();
    }
    @Override
    protected void onResume(){
        super.onResume();
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

}