package com.example.affanfarid.myapplication1;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import android.content.Intent;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_PIC_REQUEST = 1337;
    public static final int CAMERA_PERMISSION_REQUEST_CODE = 8675309;
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 10;
    private TextView mTextMessage;


    private Button locationButton;
    private TextView locationText;
    private LocationManager locationManager;
    private LocationListener locationListener;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_camera:
                    mTextMessage.setText(R.string.title_camera);
                    onTakePhotoClick1();
                    return true;
                case R.id.navigation_gallery:
                    mTextMessage.setText(R.string.title_gallery);
                    return true;
            }
            return false;
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        getLocation();
        //navigation.setTranslucentNavigationEnabled(true);

    }

    public void getLocation() {
        locationButton = (Button) findViewById(R.id.locationButton);
        locationText = (TextView) findViewById(R.id.locationText);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                locationText.append("\n" + location.getLatitude() + " " + location.getLatitude() );
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };

        int locationRefreshRate = 5000; //in milliseconds
        int minDistance = 5; //minimum distance change for location to update, in meters

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET

                }, LOCATION_PERMISSION_REQUEST_CODE);
                return;
            }

        }else{
            //configureButton();
            locationManager.requestLocationUpdates("gps", locationRefreshRate, minDistance, locationListener);
        }

        locationManager.requestLocationUpdates("gps", locationRefreshRate, minDistance, locationListener);



    }

    private void configureButton(){
        final int locationRefreshRate = 5000; //in milliseconds
        final int minDistance = 5; //minimum distance change for location to update, in meters

        locationButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                locationManager.requestLocationUpdates("gps", locationRefreshRate, minDistance, locationListener);
            }


        });

        //locationManager.requestLocationUpdates("gps", locationRefreshRate, minDistance, locationListener);
    }



    public void onTakePhotoClick1(){
        if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){

            onButtonTap();
        }
        else{
            String[] permissionRequest = {Manifest.permission.CAMERA};
            requestPermissions(permissionRequest, CAMERA_PERMISSION_REQUEST_CODE);
        }
    }



    public void onTakePhotoClick(View v){
        if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){

            onButtonTap();
        }
        else{
            String[] permissionRequest = {Manifest.permission.CAMERA};
            requestPermissions(permissionRequest, CAMERA_PERMISSION_REQUEST_CODE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == CAMERA_PERMISSION_REQUEST_CODE){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                onButtonTap();
            }
            else{
                Toast.makeText(this,"cant take photo without permission", Toast.LENGTH_LONG).show();
            }
        }

        if(requestCode==LOCATION_PERMISSION_REQUEST_CODE){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                configureButton();
            }
            else{
                Toast.makeText(this,"cant get location without permission", Toast.LENGTH_LONG).show();
            }

        }

    }

    public void onButtonTap(){

        Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);



//        File pictureDirectory = Enviornment.getExternalStoragePublicDirectory(Enviornment.DIRECTORY_PICTURES);
//
//        String imageName = getPictureName();
//        File imageFile = new File(pictureDirectory, imageName);
//
//        Uri pictureUri = Uri.fromFile(imageFile);
//
//        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);


        startActivity(intent);


    }










}
