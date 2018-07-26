package com.example.affanfarid.myapplication1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
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

        int num = 1;

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

        if(bitmap.getWidth() > bitmap.getHeight()){ //portrait
            bitmap = rotateImage(bitmap, 90);
        }
        imgView.setImageBitmap(bitmap);

    }



    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

}