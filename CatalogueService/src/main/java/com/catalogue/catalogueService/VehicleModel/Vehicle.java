package com.catalogue.catalogueService.VehicleModel;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * This is the completed JSON file for catalogue in java class form
 */
public class Vehicle {

    @Id
    private String id;

    @Field("basicView")
    private BasicView basicView;

    @Field("detailedView")
    private DetailedView detailedView;

    public Vehicle(BasicView basicView, DetailedView detailedView) {
        this.basicView = basicView;
        this.detailedView = detailedView;
    }


    // Getter Methods Required By Jackson
    public String getId() {
        return id;
    }

    public BasicView getBasicView() {
        return basicView;
    }

    public DetailedView getDetailedView() {
        return detailedView;
    }
}
