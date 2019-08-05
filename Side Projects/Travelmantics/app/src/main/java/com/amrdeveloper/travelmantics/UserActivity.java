package com.amrdeveloper.travelmantics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Toast.makeText(this, "Debug mode", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_activity_menu, menu);
        /*
        MenuItem insertMenu = menu.findItem(R.id.insert_menu);

        if (FirebaseUtil.isAdminUser()) {
            insertMenu.setVisible(true);
        }else{
            insertMenu.setVisible(false);
        }
        */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.insert_menu:
                Intent intent = new Intent(this,AdminActivity.class);
                startActivity(intent);
                return true;
            case R.id.logout_menu:
                AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener(task -> {
                            Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                            FirebaseUtil.attachAuthListsner();
                        });
                FirebaseUtil.dettachAuthListsner();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        FirebaseUtil.dettachAuthListsner();
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUtil.openDatabaseRefernce("traveldeals",this);
        RecyclerView dealsRecyclerView = findViewById(R.id.dealsRecyclerView);
        final DealsAdapter dealsAdapter = new DealsAdapter();

        dealsRecyclerView.setAdapter(dealsAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        dealsRecyclerView.setLayoutManager(layoutManager);
        FirebaseUtil.attachAuthListsner();
    }


    public void showMenu() {
        /*
         * Tell os that menu is change so redraw it
         */
        invalidateOptionsMenu();
    }
}

