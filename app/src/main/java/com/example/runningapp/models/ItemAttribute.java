package com.example.runningapp.models;

import org.json.JSONException;
import org.json.JSONObject;

public class ItemAttribute {
    private String attributeName;
    private String attributeValue;

    public ItemAttribute(JSONObject json) throws JSONException {
        this.attributeName = json.getString("AttributeName");
        this.attributeValue = json.getString("AttributeValue");
    }

    public String getAttributeName() { return attributeName; }
    public String getAttributeValue() { return attributeValue; }
}
