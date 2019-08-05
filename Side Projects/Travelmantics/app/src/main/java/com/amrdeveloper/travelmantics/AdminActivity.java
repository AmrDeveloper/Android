package com.amrdeveloper.travelmantics;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class AdminActivity extends AppCompatActivity {

    private EditText mTitle;
    private EditText mDescription;
    private EditText mPrice;
    private ImageView mDealImg;

    private TravelDeal mTravelDeal;

    private DatabaseReference mDatabaseReference;

    private static final int PICTURE_REQUEST = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Toast.makeText(this, "Debug Mode", Toast.LENGTH_SHORT).show();
        initFirebaseDatabase();
        initViews();

        Intent intent = getIntent();
        TravelDeal travelDeal = (TravelDeal) intent.getSerializableExtra("deal");
        if (travelDeal == null) {
            mTravelDeal = new TravelDeal();
        } else {
            mTravelDeal = travelDeal;
            mTitle.setText(mTravelDeal.getTitle());
            mDescription.setText(mTravelDeal.getDescription());
            mPrice.setText(mTravelDeal.getPrice());
            showDealImage(mTravelDeal.getImageUrl());
        }
        Button loadImage = findViewById(R.id.loadImageButton);
        loadImage.setOnClickListener(v -> {
            Intent loadIntent = new Intent(Intent.ACTION_GET_CONTENT);
            loadIntent.setType("image/jpeg");
            loadIntent.putExtra(Intent.EXTRA_LOCAL_ONLY,true);
            startActivityForResult(loadIntent.createChooser(loadIntent,"Insert Picture"),PICTURE_REQUEST);
        });
    }

    private void initViews() {
        mTitle = findViewById(R.id.title);
        mDescription = findViewById(R.id.description);
        mPrice = findViewById(R.id.price);
        mDealImg = findViewById(R.id.dealImage);
    }

    private void initFirebaseDatabase() {
        mDatabaseReference = FirebaseUtil.getDatabaseReference();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu, menu);
        /*
        if (FirebaseUtil.isAdminUser()) {
            menu.findItem(R.id.delete_menu).setVisible(true);
            menu.findItem(R.id.save_menu).setVisible(true);
            enableEditText(true);
        } else {
            menu.findItem(R.id.delete_menu).setVisible(false);
            menu.findItem(R.id.save_menu).setVisible(false);
            enableEditText(false);
        }
        */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_menu:
                saveDeal();
                Toast.makeText(this, "Deal saved", Toast.LENGTH_LONG).show();
                clearData();
                backToListActivity();
                return true;
            case R.id.delete_menu:
                deleteDeal();
                Toast.makeText(this, "Deal removed", Toast.LENGTH_LONG).show();
                clearData();
                backToListActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICTURE_REQUEST && resultCode == RESULT_OK){
            Uri imageUri = data.getData();
            StorageReference reference = FirebaseUtil.getStorageRefernce().child(imageUri.getLastPathSegment());
            UploadTask uploadTask = reference.putFile(imageUri);
            uploadTask.addOnSuccessListener(this,taskSnapshot -> {
                Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                result.addOnSuccessListener(uri -> {
                    String photoStringLink = uri.toString();
                    String pictureName = taskSnapshot.getStorage().getPath();
                    mTravelDeal.setImageUrl(photoStringLink);
                    mTravelDeal.setImageName(pictureName);
                    showDealImage(photoStringLink);
                    Log.d("Delete","Name : " + pictureName);
                });
            });
        }
    }

    private void saveDeal() {
        mTravelDeal.setTitle(mTitle.getText().toString());
        mTravelDeal.setDescription(mDescription.getText().toString());
        mTravelDeal.setPrice(mPrice.getText().toString());

        //If this deal is new push it, if not update it
        if (mTravelDeal.getId() == null) {
            mDatabaseReference.push().setValue(mTravelDeal);
            return;
        }
        mDatabaseReference.child(mTravelDeal.getId()).setValue(mTravelDeal);
    }

    private void deleteDeal() {
        if (mTravelDeal.getId() == null) {
            Toast.makeText(this, "You can't deleted unsaved Deal", Toast.LENGTH_SHORT).show();
            return;
        }
        mDatabaseReference.child(mTravelDeal.getId()).removeValue();
        if(mTravelDeal.getImageName() != null && !mTravelDeal.getImageName().isEmpty()){
            StorageReference pictureReference = FirebaseUtil.getFirebaseStorage().getReferenceFromUrl(mTravelDeal.getImageUrl());
            pictureReference.delete().addOnSuccessListener(aVoid -> {
                Log.d("Delete","Success");
            }).addOnFailureListener(e -> {
                Log.d("Delete","Failure");
            });
        }
    }

    private void backToListActivity() {
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
    }

    private void clearData() {
        mTitle.getText().clear();
        mDescription.getText().clear();
        mPrice.getText().clear();
        mTitle.requestFocus();
    }

    private void enableEditText(boolean enable) {
        mTitle.setEnabled(enable);
        mDescription.setEnabled(enable);
        mPrice.setEnabled(enable);
    }

    private void showDealImage(String imageUrl){
        if(imageUrl != null && !imageUrl.isEmpty()){
            int width = Resources.getSystem().getDisplayMetrics().widthPixels;
            Picasso.get().load(imageUrl)
                    .resize(width,width * 2 / 3)
                    .centerCrop()
                    .into(mDealImg);
        }
    }
}
