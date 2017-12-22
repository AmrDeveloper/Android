package com.bitprice.amrdeveloper.bitprice;

/**
 * Created by AmrDeveloper on 12/23/2017.
 */

public class CoinInformation {

    //BitCoin price in United States Dollar
    private String priceUSD;
    //BitCoin price in British Pound Sterling
    private String priceGBP;
    //BitCoin price in Euro
    private String priceEUR;

    //Constructor
    public CoinInformation(String priceUSD, String priceGBP, String priceEUR) {
        this.priceUSD = priceUSD;
        this.priceGBP = priceGBP;
        this.priceEUR = priceEUR;
    }

    public String getPriceUSD() {
        return priceUSD;
    }

    public void setPriceUSD(String priceUSD) {
        this.priceUSD = priceUSD;
    }

    public String getPriceGBP() {
        return priceGBP;
    }

    public void setPriceGBP(String priceGBP) {
        this.priceGBP = priceGBP;
    }

    public String getPriceEUR() {
        return priceEUR;
    }

    public void setPriceEUR(String priceEUR) {
        this.priceEUR = priceEUR;
    }
}
