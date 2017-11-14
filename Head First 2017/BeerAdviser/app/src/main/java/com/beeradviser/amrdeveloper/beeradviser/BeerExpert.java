package com.beeradviser.amrdeveloper.beeradviser;

import java.util.ArrayList;
import java.util.List;

public class BeerExpert {

    List<String> getBrands(String color){
        //Dynamic Array of beer brands
        List<String> brands = new ArrayList<>();
        //if this color is amber
        if(color.equals("amber"))
        {
            brands.add("Jack Amber");
            brands.add("Rad Moose");
        }
        //if this color is not amber
        else
        {
            brands.add("Jail Pale Ale");
            brands.add("Gout Stout");
        }
        //return array of brands
        return brands;
    }

}
