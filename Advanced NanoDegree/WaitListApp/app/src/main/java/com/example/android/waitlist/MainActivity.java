package com.example.android.waitlist;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.waitlist.data.DataBaseHelper;
import com.example.android.waitlist.data.TestUtils;
import com.example.android.waitlist.data.WaitlistContract;


public class MainActivity extends AppCompatActivity {

    private GuestListAdapter mAdapter;
    private SQLiteDatabase sqLiteDatabase;

    private EditText mNewGuestNameEditText;
    private EditText mNewGuestSizeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView waitlistRecyclerView;

        // Set local attributes to corresponding views
        waitlistRecyclerView = (RecyclerView) this.findViewById(R.id.all_guests_list_view);

        mNewGuestNameEditText = (EditText) findViewById(R.id.person_name_edit_text);
        mNewGuestSizeEditText = (EditText) findViewById(R.id.party_count_edit_text);

        // Set layout for the RecyclerView, because it's a list we are using the linear layout
        waitlistRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Database Helper Object
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
        sqLiteDatabase = dataBaseHelper.getWritableDatabase();

        //Insert Dummy data
        TestUtils.insertFakeData(sqLiteDatabase);

        //Get ALl Guests in Cursor
        Cursor cursor = getAllGuests();

        // Create an adapter for that cursor to display the data
        mAdapter = new GuestListAdapter(this ,cursor);

        // Link the adapter to the RecyclerView
        waitlistRecyclerView.setAdapter(mAdapter);

        //Implements TouchHelper
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                //get id
                long id = (long)viewHolder.itemView.getTag();
                //Remove This Person with id that saved in tag
                removeGuest(id);
                //Swap Cursor to refresh the list
                mAdapter.swapCursor(getAllGuests());
            }
        }).attachToRecyclerView(waitlistRecyclerView);
    }


    /**
     * This method is called when user clicks on the Add to waitlist button
     *
     * @param view The calling view (button)
     */
    public void addToWaitlist(View view) {
        String personName = mNewGuestNameEditText.getText().toString().trim();
        String partySize = mNewGuestSizeEditText.getText().toString().trim();

        //Assert name and size not empty
        if(personName.equals("") || partySize.equals("")){
            return;
        }

        //Party Size Integer default value
        int iPartySize = 1;
        try{
            iPartySize = Integer.parseInt(partySize);
        }catch(Exception e){}

        //Insert
        addNewGuest(personName,iPartySize);
        //Swap Cursor To Update UI
        mAdapter.swapCursor(getAllGuests());
        //Make UI Nice
        mNewGuestSizeEditText.clearFocus();
        mNewGuestNameEditText.getText().clear();
        mNewGuestSizeEditText.getText().clear();
    }

    private Cursor getAllGuests() {
        return sqLiteDatabase.query(
                WaitlistContract.WaitlistEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                WaitlistContract.WaitlistEntry.COLUMN_TIMESTAMP
        );
    }

    //Make ContentValues And Insert To DataBase
    private long addNewGuest(String name , int partySize){
        //Add To DataBase Use ContentValues
        ContentValues values = new ContentValues();
        values.put(WaitlistContract.WaitlistEntry.COLUMN_GUEST_NAME,partySize);
        values.put(WaitlistContract.WaitlistEntry.COLUMN_PARTY_SIZE,partySize);

        //Add To DataBase
        return sqLiteDatabase.insert(
                WaitlistContract.WaitlistEntry.TABLE_NAME,
                null
                ,values
        );
    }

    //Remove Guest From DataBase
    private boolean removeGuest(long guestId){
        //Delete Guest From DataBase
        int state = sqLiteDatabase.delete(WaitlistContract.WaitlistEntry.TABLE_NAME,
                WaitlistContract.WaitlistEntry._ID + " = " + guestId,
                null
        );
        return (state != -1);
    }

}
