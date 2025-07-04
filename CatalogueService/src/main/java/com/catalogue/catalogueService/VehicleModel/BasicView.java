package com.catalogue.catalogueService.VehicleModel;


import org.springframework.data.mongodb.core.mapping.Field;

/**
 * This class should contain the common elements of both New and Used Vehicles, stored in the BasicView Object
 */
public class BasicView {
    private String make;
    private String model;
    private String bodyType;
    private Integer modelYear;
    private Integer price;
    private Boolean isHotDeal;


    public BasicView(String make, String model, String bodyType, int modelYear, int price, boolean isHotDeal) {
        this.make = make;
        this.model = model;
        this.bodyType = bodyType;
        this.modelYear = modelYear;
        this.price = price;
        this.isHotDeal = isHotDeal;
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

}
