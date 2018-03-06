package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by AmrDeveloper on 11/19/2017.
 */

public class QuakeReportAdapter extends ArrayAdapter<EarthQuake>{
    private static final String LOCATION_SEPARATOR = " of ";


    //Constructor
    public QuakeReportAdapter(@NonNull Context context, ArrayList<EarthQuake> objects) {
        super(context,0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //View Object
        View view = convertView;
        //Check if this view not equal null
        if(view == null){
            //Inflater This Layout
            view = LayoutInflater.from(getContext()).inflate(R.layout.quake_report_card,parent,false);
        }

        //Report Object
        EarthQuake currentEarthquake = getItem(position);

        //Set Quake Report Mag
        TextView magnitudeView = (TextView)view.findViewById(R.id.magnitude);
        //Format magnitude
        String magnitude= formatMagnitude(currentEarthquake.getMagnitude());
        //Set Magnitude to textView After Formatted
        magnitudeView.setText(magnitude);
        //Set Circle Color

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();
        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMagnitude());
        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        //Get Location of current Earth Quake
        String originalLocation = currentEarthquake.getQuakePlace();
        //String to save Primary Location
        String primaryLocation;
        //String to save Offset Location
        String locationOffset;
        //Split The Full Location to 2 String
        if (originalLocation.contains(LOCATION_SEPARATOR))
        {
            //Split String to 2 string using word "of"
            String[] parts = originalLocation.split(LOCATION_SEPARATOR);
            //String First is offset plus of
            locationOffset = parts[0] + LOCATION_SEPARATOR;
            //String second is primary Location
            primaryLocation = parts[1];
        }
        else
        {
            //If no Of in String Take String from resource
            locationOffset = getContext().getString(R.string.near_the);
            //And primary is the original
            primaryLocation = originalLocation;
        }

        //Set Location Offset
        TextView offsetLoc = (TextView)view.findViewById(R.id.location_offset);
        //Set Offset Location
        offsetLoc.setText(locationOffset);

        //Set Primary Locaiton
        TextView primaryLoc = (TextView)view.findViewById(R.id.primary_location);
        //Set Primary Location
        primaryLoc.setText(primaryLocation);

        //Set Quake Report time
        TextView time = (TextView)view.findViewById(R.id.time);
        //Create Data TO Set Time
        Date dataObject = new Date(currentEarthquake.getQuakeTime());
        //Format Time
        String timeToDisplay = formatTime(dataObject);
        //Set Time on time TextView
        time.setText(timeToDisplay);

        //Set Quake Report Date
        TextView date = (TextView)view.findViewById(R.id.date);
        //Format Date
        String dataToDisplay = formatDate(dataObject);
        //Set Date on Date TextView
        date.setText(dataToDisplay);

        //Return this View
        return view;
    }

    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    /**
     * Return the formatted magnitude string showing 1 decimal place (i.e. "3.2")
     * from a decimal magnitude value.
     */
    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }

    /**
     * Return The formatted place
     */
    @NonNull
    private String[] formatPlace(String fullPlace){
        //Split String
        //index zero is Location
        //index one is primary Location
        return fullPlace.split("of");
    }

    //Set Color For different magnitude
    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }

}
