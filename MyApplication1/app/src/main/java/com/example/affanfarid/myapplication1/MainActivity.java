package com.example.affanfarid.myapplication1;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
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
    private TextView mTextMessage;

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
                    return true;
                case R.id.navigation_gallery:
                    mTextMessage.setText(R.string.title_gallery);
                    return true;
            }
            return false;
        }

    };
    //test1

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //navigation.setTranslucentNavigationEnabled(true);

    }


    public void cameraButton(View v){
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
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
    }

    public void onButtonTap(){

        Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


        //File pictureDirectory = Enviornment.getExternalSt




        startActivity(intent);

//        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//        startActivity(intent);

//        Toast myToast = Toast.makeText(getApplicationContext(),"Text", Toast.LENGTH_LONG);
//        if (myToast == null || myToast.getView().getWindowVisibility() != View.VISIBLE) {
//            myToast.setDuration(2);
//            myToast.show();
//        }
//        //else myToast.
//
//        //myToast.show();
    }

//    static final int REQUEST_IMAGE_CAPTURE = 1;
//
//    public void dispatchTakePictureIntent() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//        }
//    }






}
