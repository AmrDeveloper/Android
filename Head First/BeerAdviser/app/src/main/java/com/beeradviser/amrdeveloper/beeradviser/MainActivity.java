package com.beeradviser.amrdeveloper.beeradviser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //Object from BeerExpert Class
    private BeerExpert beerExpert = new BeerExpert();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    //From Head First
    //If you want a method to respond to a button click,
    //it must be public,have a void return type, and take a single View parameter.
    //view The parameter refers to the GUI component , the button in this app
    public void onClickFindBeer(View view){
        //findViewById(); return view Object and we cast it to Button or TextView ,..
        //like Object and String in java
        //R is a special Java class that enables you to retrieve references to resources in your app
        //r class path app/build/generated/source/r/debug
        Spinner colors = (Spinner) findViewById(R.id.colors);
        TextView brands = (TextView) findViewById(R.id.brands);
        //take beer color from spinner and show it on textview
        //get selected item from colors spinner as String
        String beertype = String.valueOf(colors.getSelectedItem());
        //put color in BeerExpert Class
        List<String> brandsName = beerExpert.getBrands(beertype);
        //make one String from all list using StringBuilder not string +=
        StringBuilder beerName = new StringBuilder();
        //for every name in list
        for(String name : brandsName){
            //put new line to show all in textView
            beerName.append(name).append("\n");
        }
        //show it on textview
        brands.setText(beerName);
    }
}
