package com.android.frankthirteen.timetracker.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.view.Display;
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


}
