package com.example.runningapp.models;

import android.graphics.Bitmap;

public class DeliveredModel {
    private String Category, Lostdate,Lostlocation,Lostby,Founddate,Foundlocation,Foundby, attributes,Delivered_date,Type;
    private int Award,Price;
    private Bitmap image;

    public DeliveredModel(String Category, String Lostdate, String Lostlocation, String Lostby,int Award,
                          String Founddate, String Foundlocation, String Foundby,int Price,String Delivered_date,String Type,String attributes, Bitmap image) {


        this.Category = Category;

        this.Lostdate = Lostdate;
        this.Lostlocation = Lostlocation;
        this.Lostby=Lostby;
        this.Award = Award;

        this.Founddate = Founddate;
        this.Foundlocation = Foundlocation;
        this.Foundby=Foundby;
        this.Price = Price;

        this.Delivered_date=Delivered_date;
        this.Type=Type;

        this.attributes = attributes;
        this.image = image;
    }

    public String getCategory() {
        return Category;
    }

    public String getLostdate() {
        return Lostdate;
    }

    public String getLostlocation() {
        return Lostlocation;
    }
    public String getLostby() {
        return Lostby;
    }


    public int getAward() {
        return Award;
    }


    public String getFounddate() {
        return Founddate;
    }

    public String getFoundlocation() {
        return Foundlocation;
    }
    public String getFoundby() {
        return Foundby;
    }


    public int getPrice() {
        return Price;
    }

    public String getDelivered_date() {
        return Delivered_date;
    }

    public String getType() {
        return Type;
    }



    public String getAttributes() {
        return attributes;
    }

    public Bitmap getImage() {
        return image;
    }

}


