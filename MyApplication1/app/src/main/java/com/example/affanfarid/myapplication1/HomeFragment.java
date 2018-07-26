package com.example.affanfarid.myapplication1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

        int num = 0;

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


            //File file = new File(directory, "JPEG_20180718_105716_724376777.jpg");



            //File file = new File(directory, directory.listFiles()[num]);
            File file = directory.listFiles()[num];


            System.out.println("DIRECTORY NAME IS: "+directory.listFiles()[num]);



            System.out.println("IMAGE VIEW: " + mImageView);

            System.out.println(file);

            if(file.exists()){

                displayImage(mImageView,file);

                //mImageView.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
            }

        }

    }

    public void displayImage(ImageView imgView, File file){

        imgView.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));

    }


}