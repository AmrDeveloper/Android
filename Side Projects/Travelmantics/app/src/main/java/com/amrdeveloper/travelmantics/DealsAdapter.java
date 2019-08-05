package com.amrdeveloper.travelmantics;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DealsAdapter extends RecyclerView.Adapter<DealsAdapter.DealViewHolder> {

    private List<TravelDeal> travelDealList;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildListener;

    public DealsAdapter() {
        travelDealList = FirebaseUtil.getTravelDeals();
        mFirebaseDatabase = FirebaseUtil.getFirebaseDatabase();
        mDatabaseReference = FirebaseUtil.getDatabaseReference();
        mChildListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                TravelDeal travelDeal = dataSnapshot.getValue(TravelDeal.class);
                Log.v("Deal", travelDeal.getTitle());
                travelDeal.setId(dataSnapshot.getKey());
                travelDealList.add(travelDeal);
                notifyItemInserted(travelDealList.size() - 1);
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
        mDatabaseReference.addChildEventListener(mChildListener);
    }

    @NonNull
    @Override
    public DealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutID = R.layout.deal_list_item;
        View view = LayoutInflater.from(context).inflate(layoutID,parent,false);
        return new DealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DealViewHolder holder, int position) {
        TravelDeal deal = travelDealList.get(position);
        holder.bindTravel(deal);
    }

    @Override
    public int getItemCount() {
        return travelDealList.size();
    }

    class DealViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private TextView dealTitle;
        private TextView dealDescription;
        private TextView dealPrice;
        private ImageView dealImage;

        private DealViewHolder(@NonNull View itemView) {
            super(itemView);
            this.dealTitle = itemView.findViewById(R.id.tvTitle);
            this.dealDescription = itemView.findViewById(R.id.tvDescription);
            this.dealPrice = itemView.findViewById(R.id.tvPrice);
            this.dealImage = itemView.findViewById(R.id.imageDeal);
            itemView.setOnClickListener(this);
        }

        private void bindTravel(TravelDeal deal) {
            dealTitle.setText(deal.getTitle());
            dealDescription.setText(deal.getDescription());
            dealPrice.setText(deal.getPrice());
            showDealImage(deal.getImageUrl());
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            TravelDeal clickedDeal = travelDealList.get(position);
            Intent intent = new Intent(view.getContext(),AdminActivity.class);
            intent.putExtra("deal",clickedDeal);
            view.getContext().startActivity(intent);
        }

        private void showDealImage(String imageUrl){
            if(imageUrl != null && !imageUrl.isEmpty()){
                Picasso.get().load(imageUrl)
                        .resize(160 ,160)
                        .centerCrop()
                        .into(dealImage);
            }
        }
    }
}
