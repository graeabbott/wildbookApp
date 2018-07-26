package com.example.affanfarid.myapplication1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class HomeFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View myFragmentView = inflater.inflate(R.layout.home_fragment, container, false);

        int num = 7;

        retrieveFile(myFragmentView, num);



        return myFragmentView;
    }

    public void retrieveFile(View fragView, int num){

        ImageView mImageView = (ImageView) fragView.findViewById(R.id.thumbnail);

        File sdCard = Environment.getExternalStorageDirectory();
        File directory = new File (sdCard.getAbsolutePath() + "/Android/data/com.example.affanfarid.myapplication1/files/Pictures/");
        File[] fileArray = directory.listFiles();

        if( num < 0 || num > fileArray.length || fileArray.length <= 0){
            System.out.println("INVALID INDEX");
            return;
        }
        else{
            File file = directory.listFiles()[num];
            if(file.exists()){

                displayImage(mImageView,file);
            }
        }

    }

    public void displayImage(ImageView imgView, File file){

        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

        System.out.println("HEIGHT IS: "+ bitmap.getHeight());
        System.out.println("WIDTH IS: "+ bitmap.getWidth());

        System.out.println("ORIENTATION IS:" + getCameraPhotoOrientation(file));



        switch (getCameraPhotoOrientation(file)){
            case 0:
                bitmap = Bitmap.createBitmap(bitmap, bitmap.getWidth()/2 - bitmap.getHeight()/2, 0, bitmap.getHeight(), bitmap.getHeight());
                break;
            case 90:
                bitmap = rotateImage(bitmap, 90);
                //bitmap = Bitmap.createBitmap(bitmap, bitmap.getWidth()/2 - bitmap.getHeight()/2, 0, bitmap.getHeight(), bitmap.getHeight());
                bitmap = Bitmap.createBitmap(bitmap, 0, bitmap.getHeight()/2 - bitmap.getWidth()/2, bitmap.getWidth(), bitmap.getWidth());
                break;
            case 180:
                bitmap = rotateImage(bitmap, 180);
                bitmap = Bitmap.createBitmap(bitmap, bitmap.getWidth()/2 - bitmap.getHeight()/2, 0, bitmap.getHeight(), bitmap.getHeight());
                break;

            case 270:
                bitmap = rotateImage(bitmap, 270);
                //bitmap = Bitmap.createBitmap(bitmap, bitmap.getWidth()/2 - bitmap.getHeight()/2, 0, bitmap.getHeight(), bitmap.getHeight());
                bitmap = Bitmap.createBitmap(bitmap, 0, bitmap.getHeight()/2 - bitmap.getWidth()/2, bitmap.getWidth(), bitmap.getWidth());
                break;
            default:
                break;
        }



        if(bitmap.getWidth() > bitmap.getHeight()){

            System.out.println("TEST1");

            //bitmap = rotateImage(bitmap, 90);

            bitmap = Bitmap.createBitmap(bitmap, bitmap.getWidth()/2 - bitmap.getHeight()/2, 0, bitmap.getHeight(), bitmap.getHeight());


            //bitmap = Bitmap.createBitmap(bitmap, 0, bitmap.getHeight()/2 - bitmap.getWidth()/2, bitmap.getWidth(), bitmap.getWidth());

        } else if(bitmap.getHeight() != bitmap.getWidth()){


            //bitmap = Bitmap.createBitmap(bitmap, bitmap.getWidth()/2 - bitmap.getHeight()/2, 0, bitmap.getHeight(), bitmap.getHeight());
            bitmap = Bitmap.createBitmap(bitmap, 0, bitmap.getHeight()/2 - bitmap.getWidth()/2, bitmap.getWidth(), bitmap.getWidth());

            //bitmap = rotateImage(bitmap, 90);
            System.out.println("TEST2");
        }



        imgView.setImageBitmap(bitmap);

    }



    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }


    public int getCameraPhotoOrientation( File imageFile){
        int rotate = 0;
        try {
            //context.getContentResolver().notifyChange(imageUri, null);
            //File imageFile = new File(imagePath);

            ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }

//            Log.i("RotateImage", "Exif orientation: " + orientation);
//            Log.i("RotateImage", "Rotate value: " + rotate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rotate;
    }





}