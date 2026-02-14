package com.example.runningapp.models;

import android.graphics.Bitmap;

public class ItemModel {
    private String category, Date, Location,Person, attributes,Urgent,Nominate;
    private Integer Money,Item_id;
    private Bitmap image;

    public ItemModel(int Item_id,String category, String Date, String Location, String Person,String Urgent,Integer Money,String Nominate, String attributes, Bitmap image) {
       this.Item_id=Item_id;
        this.category = category;
        this.Date = Date;
        this.Urgent=Urgent;
        this.Nominate=Nominate;
        this.Location = Location;
        this.Person=Person;
        this.Money = Money;
        this.attributes = attributes;
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return Date;
    }

    public String getLocation() {
        return Location;
    }
    public String getperson() {
        return Person;
    }
    public String getUrgent() {
        return Urgent;
    }

    public String getNominate() {
        return Nominate;
    }


    public Integer getmoney() {
        return Money;
    }

    public String getAttributes() {
        return attributes;
    }

    public Bitmap getImage() {
        return image;
    }
    public Integer getItem_id() {
        return Item_id;
    }
}
