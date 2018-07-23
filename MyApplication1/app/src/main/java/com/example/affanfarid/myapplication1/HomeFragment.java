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



    public ImageView testImage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //testImage = (ImageView) getActivity().findViewById(R.id.thumbnail);
        //retrieveFromStorage();

        View myFragmentView = inflater.inflate(R.layout.home_fragment, container, false);

        ImageView mImageView = (ImageView) myFragmentView.findViewById(R.id.thumbnail);

        File sdCard = Environment.getExternalStorageDirectory();
        //Internal Storage/Android/data/com.example.affanfarid.myapplication1/files/Pictures/JPEG_20180723.png
        File directory = new File (sdCard.getAbsolutePath() + "/Android/data/com.example.affanfarid.myapplication1/files/Pictures/");
        File file = new File(directory, "JPEG_20180718_101356_760658815"); //or any other format supported

        System.out.println("DIRECTORY NAME IS: "+directory.listFiles()[0]);



        System.out.println("IMAGE VIEW: " + mImageView);
        mImageView.setImageBitmap(BitmapFactory.decodeFile(directory+"JPEG_20180718_101356_760658815"));


        //mImageView.setImageBitmap(BitmapFactory.decodeFile());








        return myFragmentView;
    }

//    public void retrieveFromStorage(){
//        File sdCard = Environment.getExternalStorageDirectory();
//        //Internal Storage/Android/data/com.example.affanfarid.myapplication1/files/Pictures/JPEG_20180723.png
//        File directory = new File (sdCard.getAbsolutePath() + "/Android/data/com.example.affanfarid.myapplication1/files/Pictures/");
//        File file = new File(directory, "JPEG_20180718_101356_760658815"); //or any other format supported
//
//        System.out.println("DIRECTORY NAME IS: "+directory.listFiles()[0]);
//
//
//
//        ImageView mImageView;
//        //mImageView = (ImageView) getView().findViewById(R.id.thumbnail);
//
//        System.out.println("IMAGE VIEW: " + mImageView);
////        mImageView.setImageBitmap(BitmapFactory.decodeFile(directory+"JPEG_20180718_101356_760658815"));
//
//
//        //mImageView.setImageBitmap(BitmapFactory.decodeFile());
////
////        try
////        {
////            FileInputStream streamIn = new FileInputStream(file);
////            Bitmap testBitmap = BitmapFactory.decodeStream(streamIn); //This gets the image
////            testImage.setImageBitmap(testBitmap);
////
////            try {
////                streamIn.close();
////            }
////            catch (IOException e) {
////                // Do something here
////            }
////
////        }
////        catch (FileNotFoundException ex)
////        {
////            // insert code to run when exception occurs
////        }
//
//
//
//    }


}