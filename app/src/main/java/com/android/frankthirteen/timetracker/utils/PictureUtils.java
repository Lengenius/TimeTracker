package com.android.frankthirteen.timetracker.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Frank on 4/25/16.
 */
public class PictureUtils {

    public static BitmapDrawable getScaledPic(Activity a, String photoPath){
/**
 * The width variable need to wait for activity attached.
 */
        Display display = a.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int targetWidth =  size.x;
        int targetHeight = size.y;

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = Math.max(photoH/targetHeight,photoW/targetWidth);

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap scaledPic = BitmapFactory.decodeFile(photoPath,bmOptions);

        return new BitmapDrawable(a.getResources(), scaledPic);
    }

    public static Bitmap getThumbnail(View view, String photoPath){
        int viewWidth = view.getLayoutParams().width;
        int viewHeight = view.getLayoutParams().height;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoPath, options);
        int width = options.outWidth;
        int height = options.outHeight;

        int scaleFactor = Math.max(width/viewWidth,height/viewHeight);

        options.inJustDecodeBounds = false;
        options.inSampleSize = scaleFactor;

        return BitmapFactory.decodeFile(photoPath,options);

//        return new BitmapDrawable(view.getContext().getResources(),thumbnail);
    }

//    private int calculateInSampleSize(
//            BitmapFactory.Options options, int reqWidth, int reqHeight) {
//        // Raw height and width of image
//        final int height = options.outHeight;
//        final int width = options.outWidth;
//        int inSampleSize = 1;
//
//        if (height > reqHeight || width > reqWidth) {
//
//            final int halfHeight = height / 2;
//            final int halfWidth = width / 2;
//
//            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
//            // height and width larger than the requested height and width.
//            while ((halfHeight / inSampleSize) > reqHeight
//                    && (halfWidth / inSampleSize) > reqWidth) {
//                inSampleSize *= 2;
//            }
//        }
//
//        return inSampleSize;
//    }
}
