package com.example.runningapp.models;

import android.graphics.Bitmap;

public class studentAuctionmodel {

    private int itemId;
    private String category;
    private String foundDate;
    private String foundLocation;
    private String foundBy;
    private int estimatedPrice;
    private String startDate;
    private String endDate;
    private String status;
    private int maximumBid;
    private String maximumBidder;
    private String attributes;
    private Bitmap image;

    // Constructor
    public studentAuctionmodel(int itemId, String category, String foundDate, String foundLocation,
                               String foundBy, int estimatedPrice, String startDate, String endDate,
                               String status, int maximumBid, String maximumBidder, String attributes, Bitmap image) {
        this.itemId = itemId;
        this.category = category;
        this.foundDate = foundDate;
        this.foundLocation = foundLocation;
        this.foundBy = foundBy;
        this.estimatedPrice = estimatedPrice;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.maximumBid = maximumBid;
        this.maximumBidder = maximumBidder;
        this.attributes = attributes;
        this.image = image;
    }

    // Getters and Setters
    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFoundDate() {
        return foundDate;
    }

    public void setFoundDate(String foundDate) {
        this.foundDate = foundDate;
    }

    public String getFoundLocation() {
        return foundLocation;
    }

    public void setFoundLocation(String foundLocation) {
        this.foundLocation = foundLocation;
    }

    public String getFoundBy() {
        return foundBy;
    }

    public void setFoundBy(String foundBy) {
        this.foundBy = foundBy;
    }

    public int getEstimatedPrice() {
        return estimatedPrice;
    }

    public void setEstimatedPrice(int estimatedPrice) {
        this.estimatedPrice = estimatedPrice;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getMaximumBid() {
        return maximumBid;
    }

    public void setMaximumBid(int maximumBid) {
        this.maximumBid = maximumBid;
    }

    public String getMaximumBidder() {
        return maximumBidder;
    }

    public void setMaximumBidder(String maximumBidder) {
        this.maximumBidder = maximumBidder;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
