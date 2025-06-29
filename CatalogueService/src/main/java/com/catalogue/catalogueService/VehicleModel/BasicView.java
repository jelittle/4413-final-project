package com.catalogue.catalogueService.VehicleModel;


import org.springframework.data.mongodb.core.mapping.Field;

/**
 * This class should contain the common elements of both New and Used Vehicles, stored in the BasicView Object
 */
public class BasicView {
    private String make;
    private String model;
    private String bodyType;
    private int modelYear;
    private int price;
    private boolean isHotDeal;
    private int postTime;           // Posting time, minutes since Jan 1,1970


    public BasicView(String make, String model, String bodyType, int modelYear, int price, boolean isHotDeal, int postTime) {
        this.make = make;
        this.model = model;
        this.bodyType = bodyType;
        this.modelYear = modelYear;
        this.price = price;
        this.isHotDeal = isHotDeal;
        this.postTime = postTime;
    }

    // Getter Methods Required By Jackson

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getBodyType() {
        return bodyType;
    }

    public int getModelYear() {
        return modelYear;
    }

    public int getPrice() {
        return price;
    }

    public boolean isHotDeal() {
        return isHotDeal;
    }

    public int getPostTime() {
        return postTime;
    }
}
