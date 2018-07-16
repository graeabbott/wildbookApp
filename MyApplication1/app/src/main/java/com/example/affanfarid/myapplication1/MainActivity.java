package com.example.affanfarid.myapplication1;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
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
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

//TODO:
//Make all the methods private and use the Onclick method
// Fragments
// Rotate bitmap thumbnail
//
//

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int thumbnailHeight = 100;
    public static final int CAMERA_PERMISSION_REQUEST_CODE = 8675309;
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 10;
    static final int locationRefreshRate = 30 * 1000; //in milliseconds
    static final int minDistance = 5; //minimum distance change for location to update, in meters
    private TextView mTextMessage;



    public ImageView mImageView;

    private Button locationButton;
    private TextView locationText;
    private LocationManager locationManager;
    private LocationListener locationListener;

    private int fileNumCounter = 0;




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
                    onTakePhotoClick_Menu();
                    return true;
                case R.id.navigation_gallery:
                    mTextMessage.setText(R.string.title_gallery);
                    goToAlbum_Menu();
                    return true;
            }
            return false;
        }

    };


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == CAMERA_PERMISSION_REQUEST_CODE){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                cameraStart();
            }
            else{
                Toast.makeText(this,"cant take photo without permission", Toast.LENGTH_LONG).show();
            }
        }

        if(requestCode==LOCATION_PERMISSION_REQUEST_CODE){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                onLocationClick();
            }
            else{
                Toast.makeText(this,"cant get location without permission", Toast.LENGTH_LONG).show();
            }

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        mImageView = (ImageView) findViewById(R.id.thumbnail);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        getLocation();
        //navigation.setTranslucentNavigationEnabled(true);

    }

    public void goToAlbum_Menu(){
        Intent startAlbumActivity = new Intent(this, albumActivity.class);
        startActivity(startAlbumActivity);

    }


    public void getLocation() {
        locationButton = (Button) findViewById(R.id.locationButton);
        locationText = (TextView) findViewById(R.id.locationText);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                locationText.setText("\n" + location.getLongitude() + " " + location.getLatitude() );
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


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET

                }, LOCATION_PERMISSION_REQUEST_CODE);
                return;
            }

        }else{

            requestLocation();

        }

        requestLocation();



    }

    private void onLocationClick(){

        locationButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                requestLocation();

            }


        });


    }


    private void requestLocation(){
        locationManager.requestLocationUpdates("gps", locationRefreshRate, minDistance, locationListener);
    }

    public void onTakePhotoClick_Menu(){
        if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){

            cameraStart();
            //onButtonTap();
        }
        else{
            String[] permissionRequest = {Manifest.permission.CAMERA};
            requestPermissions(permissionRequest, CAMERA_PERMISSION_REQUEST_CODE);
        }
    }



    public void onTakePhotoClick_Button(View v){
        if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){

            cameraStart();
            //onButtonTap();
        }
        else{
            String[] permissionRequest = {Manifest.permission.CAMERA};
            requestPermissions(permissionRequest, CAMERA_PERMISSION_REQUEST_CODE);
        }
    }




    public void cameraStart(){

        //takePicture();

        dispatchTakePictureIntent();

        //Intent cameraIntent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

//        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
//            File photoFile = null;
//            try {
//                photoFile = createImageFile();
//                System.out.println("works");
//            } catch (IOException ex) {
//                // Error occurred while creating the File
//                Toast.makeText(this, "Error occurred while creating the File", Toast.LENGTH_LONG).show();
//                System.out.println("doesnt work");
//            }
//
//            Uri photoURI = FileProvider.getUriForFile(this,
//                    "com.example.android.fileprovider",
//                    photoFile);
//            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//            startActivityForResult(cameraIntent, REQUEST_TAKE_PHOTO);
//        }

//
//        WRITE IMAGE SAVING CODE HERE
//
//        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
//
//        String imageName = getPictureName();
//        File imageFile = new File(pictureDirectory, imageName);
//
//        Uri pictureUri = Uri.fromFile(imageFile);
//
//        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);


//        storageDir = new File(
//                Environment.getExternalStoragePublicDirectory(
//                        Environment.DIRECTORY_PICTURES
//                ),
//                getAlbumName()
//        );





        //startActivity(cameraIntent);


    }


    static final int REQUEST_TAKE_PHOTO = 1;

    private void dispatchTakePictureIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        // Ensure that there's a camera activity to handle the intent

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go

            File photoFile = null;

            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                //System.out.println("caught IO Exception");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {

                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
               //takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                //takePictureIntent.putExtra("IMAGEURI", photoURI.toString());



                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);

            }
        }
    }

//    private void dispatchTakePictureIntent() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//        }
//    }


    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //System.out.println("marker2.5");

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            //System.out.println("marker3");


            if (data != null){

                //System.out.println("marker4");


                Bundle extras = data.getExtras();

                //System.out.println("marker5");

                //Uri uri = Uri.parse(extras.getString("IMAGEURI"));
                //Bitmap myImg = BitmapFactory.decodeFile(uri.getPath());
                //mImageView.setImageBitmap(myImg);




                Bitmap imageBitmap = (Bitmap) extras.get("data");
                imageBitmap = ThumbnailUtils.extractThumbnail(imageBitmap, thumbnailHeight, thumbnailHeight);
//
//                //ExifInterface ei = new ExifInterface(imageBitmap.getImageUri());


                //System.out.println("marker6");


                mImageView.setImageBitmap(imageBitmap);
                //mImageView.setImageBitmap(myImg);
            }


        }
    }

    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".png",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public String createUniqueName(){
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "WLDBK_" + timeStamp + ".png";
        return imageFileName;
    }


    public static final String PATH_TO_IMAGE_FILE = Environment.getExternalStorageDirectory().getAbsolutePath()
            + File.separator + "WILDBOOK" + File.separator;


    public static File generateImageFile(String fileName) {
        File wildbookDir = new File(PATH_TO_IMAGE_FILE);
        if(!wildbookDir.exists()) {
            wildbookDir.mkdir();
        }
        return new File(PATH_TO_IMAGE_FILE + fileName);
    }

    public void takePicture() {


        String imageFileName = createUniqueName();

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(generateImageFile(imageFileName)));

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }



}

//public class GenericFileProvider extends FileProvider {}




