package com.amrdeveloper.emojify;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.title_text_view) TextView titleTextView;
    @BindView(R.id.image_view) ImageView resultImageView;
    @BindView(R.id.emojify_button) Button takePhotoButton;
    @BindView(R.id.clear_button) FloatingActionButton clearFloatButton;
    @BindView(R.id.save_button) FloatingActionButton saveFloatButton;
    @BindView(R.id.share_button) FloatingActionButton shareFloatButton;

    private String mTempPhotoPath;
    private Bitmap mResultsBitmap;

    private static final int CAMERA_REQUEST_CODE = 1996;
    private static final int REQUEST_STORAGE_PERMISSION = 1997;
    private static final int REQUEST_CAMERA_PERMISSION = 1998;

    private static final String FILE_PROVIDER_AUTHORITIES = "com.amrdeveloper.emojify.provider";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    /*
     * Show Camera when clicked
     */
    @OnClick(R.id.emojify_button)
    public void takePhotoFromCamera() {
        //Request Storage Permission
        if (ContextCompat
                .checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Array of permissions that i want to request it
            String[] permissions =  new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

            // The user has not yet granted permission, or has revoked it, and we must request it
            ActivityCompat.requestPermissions(this,permissions,REQUEST_STORAGE_PERMISSION);
        } else {
            // if user have permission just open camera
            launchDeviceCamera();
        }
    }

    /**
     * @param requestCode : the int that passed in intent to launch camera
     * @param resultCode  : int to tell us if user click ok or cancel
     * @param data        : the image if user click ok
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // If the image capture activity was called and was successful
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            onImageTakenSuccess();
        } else {
            // Delete the temporary image file
            // Otherwise, delete the temporary image file
            BitmapUtils.deleteImageFile(this, mTempPhotoPath);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_STORAGE_PERMISSION:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // If you get permission, request the camera permission
                    prepareInvokeCamera();
                } else {
                    // If you do not get permission, show a Toast
                    Toast.makeText(this, R.string.permission_denied, Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case REQUEST_CAMERA_PERMISSION:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // If you get permission, launch the camera
                    launchDeviceCamera();
                } else {
                    // If you do not get permission, show a Toast
                    Toast.makeText(this, R.string.permission_denied, Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }


    private void launchDeviceCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            try {
                //  Create Image file
                File photoFile = BitmapUtils.createTempImageFile(this);

                mTempPhotoPath = photoFile.getAbsolutePath();

                // Get Uri from Image File
                Uri imageUri = FileProvider.getUriForFile(this,FILE_PROVIDER_AUTHORITIES,photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);

                takePictureIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void prepareInvokeCamera(){
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            // Array of permissions that i want to request it
            String[] permissions =  new String[]{Manifest.permission.CAMERA};

            // The user has not yet granted permission, or has revoked it, and we must request it
            ActivityCompat.requestPermissions(this,permissions,REQUEST_CAMERA_PERMISSION);
        }else{
            launchDeviceCamera();
        }
    }

    private void onImageTakenSuccess() {
        titleTextView.setVisibility(View.GONE);
        takePhotoButton.setVisibility(View.GONE);

        clearFloatButton.show();
        saveFloatButton.show();
        shareFloatButton.show();

        // Resample the saved image to fit the ImageView
        mResultsBitmap = BitmapUtils.resamplePic(this, mTempPhotoPath);

        // Detect the faces and overlay the appropriate emoji
        mResultsBitmap = EmojiClassifier.detectFacesandOverlayEmoji(this, mResultsBitmap);

        // Set the new bitmap to the ImageView
        resultImageView.setImageBitmap(mResultsBitmap);
    }

    @OnClick(R.id.save_button)
    public void saveImageAction() {
        // Delete the temporary image file
        BitmapUtils.deleteImageFile(this, mTempPhotoPath);

        // Save the image
        BitmapUtils.saveImage(this, mResultsBitmap);
    }

    @OnClick(R.id.share_button)
    public void shareImageAction() {
        // Delete the temporary image file
        BitmapUtils.deleteImageFile(this, mTempPhotoPath);

        // Save the image
        BitmapUtils.saveImage(this, mResultsBitmap);

        // Share the image
        BitmapUtils.shareImage(this, mTempPhotoPath);
    }

    @OnClick(R.id.clear_button)
    public void clearImageAction() {
        // Clear the image and toggle the view visibility
        resultImageView.setImageResource(0);

        // Hide Options
        clearFloatButton.hide();
        saveFloatButton.hide();
        shareFloatButton.hide();

        // Show App Title
        titleTextView.setVisibility(View.VISIBLE);

        // Show Take photo button to retake photo if user want that
        takePhotoButton.setVisibility(View.VISIBLE);

        // Delete the temporary image file
        BitmapUtils.deleteImageFile(this, mTempPhotoPath);
    }
}
