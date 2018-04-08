package com.example.android.waitlist;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.waitlist.data.WaitlistContract;


public class GuestListAdapter extends RecyclerView.Adapter<GuestListAdapter.GuestViewHolder> {

    private Context mContext;
    private Cursor mCursor;

    /**
     * Constructor using the context and the db cursor
     *
     * @param context the calling context/activity
     */
    public GuestListAdapter(Context context, Cursor mCursor) {
        this.mContext = context;
        this.mCursor = mCursor;
    }

    @Override
    public GuestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get the RecyclerView item layout
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.guest_list_item, parent, false);
        return new GuestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GuestViewHolder holder, int position) {
        //if Cant move to this position return
         if(!mCursor.moveToPosition(position)){
             return;
         }

         //Get index
         int nameColumnIndex = mCursor.getColumnIndex(WaitlistContract.WaitlistEntry.COLUMN_GUEST_NAME);
         int sizeColumnIndex = mCursor.getColumnIndex(WaitlistContract.WaitlistEntry.COLUMN_PARTY_SIZE);
         int idColumnIndex = mCursor.getColumnIndex(WaitlistContract.WaitlistEntry._ID);

         //Get Value
         String name = mCursor.getString(nameColumnIndex);
         String partySize = mCursor.getString(sizeColumnIndex);
         long id = mCursor.getLong(idColumnIndex);

         //Update UI
        holder.nameTextView.setText(name);
        holder.partySizeTextView.setText(partySize);
        //Put Id on RecyclerView Tag
        holder.itemView.setTag(id);
    }


    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor){
        //Check if current Cursor Is Null
        if(mCursor != null){
            mCursor.close();
        }
        //Update Cursor
        mCursor = newCursor;
        //If new Cursor Not Equal Null Notify
        if(mCursor != null){
            this.notifyDataSetChanged();
        }
    }

    /**
     * Inner class to hold the views needed to display a single item in the recycler-view
     */
    class GuestViewHolder extends RecyclerView.ViewHolder {

        // Will display the guest name
        TextView nameTextView;
        // Will display the party size number
        TextView partySizeTextView;

        /**
         * Constructor for our ViewHolder. Within this constructor, we get a reference to our
         * TextViews
         *
         * @param itemView The View that you inflated in
         *                 {@link GuestListAdapter#onCreateViewHolder(ViewGroup, int)}
         */
        public GuestViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.name_text_view);
            partySizeTextView = (TextView) itemView.findViewById(R.id.party_size_text_view);
        }

    }
}
