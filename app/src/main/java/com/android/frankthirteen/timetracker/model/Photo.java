package com.android.frankthirteen.timetracker.model;

import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by Frank on 4/24/16.
 */
public class Photo {
    private String mPhotoName;
    private String mPhotoPath;

    public String getmPhotoName() {
        return mPhotoName;
    }

    public Photo(UUID filename) {
        mPhotoName = "JPG_" + filename.toString();
    }

    public Photo(UUID fileName,String photoPath){
        mPhotoName = fileName.toString();
        mPhotoPath = photoPath;
    }

    public File createPhotoFile(Photo mPhotoName) throws IOException {
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(mPhotoName.getmPhotoName(), ".jpg", storageDir);
        //The API guide said here should be "file" + image.getAbsolutePath(); indeed not.
        mPhotoPath = image.getAbsolutePath();
        return image;
    }

    public String getmPhotoPath() {
        return mPhotoPath;
    }
}
