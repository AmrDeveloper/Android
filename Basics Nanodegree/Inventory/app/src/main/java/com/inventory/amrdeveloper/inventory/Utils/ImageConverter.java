package com.inventory.amrdeveloper.inventory.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by AmrDeveloper on 2/13/2018.
 */

public class ImageConverter {

    /**
     *
     * @param bitmap as input
     * @return bitmap as byte array
     */
    public static byte[] getBytesFromBitmap(Bitmap bitmap){
        if(bitmap == null){
            return null;
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    /**
     *
     * @param image byte array as input
     * @return return byte array in bitmap
     */
    public static Bitmap getBitmapFromBytes(byte[] image){
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}
