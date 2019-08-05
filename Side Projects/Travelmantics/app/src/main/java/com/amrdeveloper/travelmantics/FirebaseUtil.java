package com.amrdeveloper.travelmantics;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FirebaseUtil {

    private static FirebaseUtil mFirebaseUtil;

    private static List<TravelDeal> mTravelDeals;

    private static UserActivity mCallerActivity;

    //Realtime Database
    private static FirebaseDatabase mFirebaseDatabase;
    private static DatabaseReference mDatabaseReference;

    //Auth
    private static FirebaseAuth mFirebaseAuth;
    private static FirebaseAuth.AuthStateListener mAuthListener;

    //Storage
    private static FirebaseStorage mFirebaseStorage;
    private static StorageReference mStorageRefernce;

    private static final int RC_SIGN_IN = 123;
    private static boolean isAdmin;

    public static void openDatabaseRefernce(String reference, final UserActivity callerActivity) {
        if (mFirebaseUtil == null) {
            mFirebaseUtil = new FirebaseUtil();
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mFirebaseAuth = FirebaseAuth.getInstance();
            mCallerActivity = callerActivity;
            mAuthListener = firebaseAuth -> {
                if (firebaseAuth.getCurrentUser() == null) {
                    signIn();
                } else {
                    String userID = firebaseAuth.getUid();
                    isAdminUser(userID);
                }
                Toast.makeText(callerActivity, "Welcome back!", Toast.LENGTH_SHORT).show();
            };
            connectStrage();
        }
        mTravelDeals = new ArrayList<>();
        mDatabaseReference = mFirebaseDatabase.getReference().child(reference);
    }

    public static void connectStrage(){
        mFirebaseStorage = FirebaseStorage.getInstance();
        mStorageRefernce = mFirebaseStorage.getReference().child("deals_pictures");
    }

    public static void signIn() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        mCallerActivity.startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTheme(R.style.AppTheme)
                        .build(),
                RC_SIGN_IN);
    }

    private static void isAdminUser(String userID) {
        isAdmin = false;
        DatabaseReference adminsRef = mFirebaseDatabase.getReference().child("administrators").child(userID);
        ChildEventListener adminsEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                isAdmin = true;
                Log.d("Admin", "You are in administrators");
                mCallerActivity.showMenu();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        adminsRef.addChildEventListener(adminsEventListener);
    }

    public static FirebaseDatabase getFirebaseDatabase() {
        return mFirebaseDatabase;
    }

    public static DatabaseReference getDatabaseReference() {
        return mDatabaseReference;
    }

    public static FirebaseAuth getFirebaseAuth() {
        return mFirebaseAuth;
    }

    public static void attachAuthListsner() {
        mFirebaseAuth.addAuthStateListener(mAuthListener);
    }

    public static void dettachAuthListsner() {
        mFirebaseAuth.removeAuthStateListener(mAuthListener);
    }

    public static StorageReference getStorageRefernce() {
        return mStorageRefernce;
    }

    public static  FirebaseStorage getFirebaseStorage(){
        return mFirebaseStorage;
    }

    public static List<TravelDeal> getTravelDeals() {
        return mTravelDeals;
    }

    public static boolean isAdminUser() {
        return isAdmin;
    }
}
