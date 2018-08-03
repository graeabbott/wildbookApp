package com.example.affanfarid.myapplication1;

import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;


public class AlbumFragment extends Fragment {




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v  = inflater.inflate(R.layout.album_fragment, container, false);


//        for (int i = 0; i< 4; i++){
//            retrieveFile(v,i);
//
//        }

        GridView gridView = (GridView) v.findViewById(R.id.galleryGridView);
        gridView.setAdapter(new ImageAdapter(getActivity()));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id){
                galleryOnClick(position);
            }

        });




        return v;
        //return inflater.inflate(R.layout.album_fragment, container, false);
    }

    public void galleryOnClick(int position){
        Toast.makeText(getActivity(), " " + position, Toast.LENGTH_SHORT).show();
        //NEW Activity/Fragment popup goes here

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

                //displayImage(mImageView,file);

            }
        }

    }

    File sdCard = Environment.getExternalStorageDirectory();
    File directory = new File (sdCard.getAbsolutePath() + "/Android/data/com.example.affanfarid.myapplication1/files/Pictures/");
    File[] fileArray = directory.listFiles();

    public File retrieveFile1( int num){

        //ImageView mImageView = (ImageView) fragView.findViewById(R.id.thumbnail);



        if( num < 0 || num > fileArray.length || fileArray.length <= 0){
            System.out.println("INVALID INDEX");
            return null;
        }
        else{
            File file = directory.listFiles()[num];
            if(file.exists()){

                return file;
                //displayImage(mImageView,file);

            }
        }

        return null;
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

    public Bitmap displayImage1(File file){

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



        return bitmap;
        //imgView.setImageBitmap(bitmap);

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



    //@Override
    public class ImageAdapter extends BaseAdapter{
        private Context mContext;

        public ImageAdapter(Context c){
            mContext = c;
        }

        public int getCount(){
            return mThumbsIds.length;
        }

        public Object getItem(int position){
            return null;
        }

        public long getItemId(int position){
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent){

//            Bitmap[] mThumbsIds2 = new Bitmap[20];
//
//
//            for (int i =0; i<fileArray.length-1; i++){
//                mThumbsIds2[i]= displayImage1(retrieveFile1(i));
//            }

//            for (int i =0; i<2; i++){
//                mThumbsIds2[i]= displayImage1(retrieveFile1(i));
//            }



            ImageView imageView = new ImageView(mContext);
            //DECREASE IMAGEVIEW SIZE?????
            //imageView.setImageResource(mThumbsIds[position]);
            imageView.setImageBitmap(mThumbsIds[position]);
            return imageView;

        }

        //private Bitmap[] mThumbsIds2 = new Bitmap[3] ;

        private Bitmap[] mThumbsIds = {
                displayImage1(retrieveFile1(5)),
         //       displayImage1(retrieveFile1(1))
        };

        private Integer[] mThumbsIds1 = {
                R.drawable.ic_notifications_black_24dp,
                R.drawable.ic_location_black_24dp
        };

    }


}