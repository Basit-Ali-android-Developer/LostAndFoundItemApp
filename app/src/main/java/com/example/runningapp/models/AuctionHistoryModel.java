package com.example.runningapp.models;

import android.graphics.Bitmap;

public class AuctionHistoryModel {

    private int itemId;
    private String category;
    private String foundDate;
    private String foundLocation;
    private String foundBy;
    private int estimatedPrice;
    private String startDate;
    private String endDate;

    private int FinalBidPrice;
    private String SoldTo;
    private String SoldDate;
    private String attributes;
    private Bitmap image;

    // Constructor
    public AuctionHistoryModel(int itemId, String category, String foundDate, String foundLocation,
                               String foundBy, int estimatedPrice, String startDate, String endDate,
                               int FinalBidPrice,String SoldTo,String SoldDate,String attributes, Bitmap image) {
        this.itemId = itemId;
        this.category = category;
        this.foundDate = foundDate;
        this.foundLocation = foundLocation;
        this.foundBy = foundBy;
        this.estimatedPrice = estimatedPrice;
        this.startDate = startDate;
        this.endDate = endDate;
        this.FinalBidPrice = FinalBidPrice;
        this.SoldTo = SoldTo;
        this.SoldDate = SoldDate;
        this.attributes = attributes;
        this.image = image;
    }

    // Getters and Setters
    public int getItemId() {
        return itemId;
    }



    public String getCategory() {
        return category;
    }



    public String getFoundDate() {
        return foundDate;
    }



    public String getFoundLocation() {
        return foundLocation;
    }



    public String getFoundBy() {
        return foundBy;
    }


    public int getEstimatedPrice() {
        return estimatedPrice;
    }



    public String getStartDate() {
        return startDate;
    }



    public String getEndDate() {
        return endDate;
    }

    public int getFinalBidPrice() {
        return FinalBidPrice;
    }

    public String getSoldTo() {
        return SoldTo;
    }

    public String getSoldDate() {
        return SoldDate;
    }






    public String getAttributes() {
        return attributes;
    }



    public Bitmap getImage() {
        return image;
    }


}
