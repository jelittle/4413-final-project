package com.catalogue.catalogueService.VehicleModel;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Document(value = "newVehicle")
public class UsedVehicle {
    @Id
    private String id;

    // Sub Objects
    @Field("basicView")
    private BasicView basicView;

    @Field("detailedView")
    private DetailedView detailedView;


    public UsedVehicle(String id, BasicView basicView, DetailedView detailedView) {
        this.id = id;
        this.basicView = basicView;
        this.detailedView = detailedView;
    }


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

class BasicUsedView extends BasicView {
    private String vehicleHistory;
    private int mileage;

    public BasicUsedView(String make, String model, String bodyType, int modelYear,
                         int price, boolean isHotDeal, int postTime, String vehicleHistory, int mileage) {
        super(make, model, bodyType, modelYear, price, isHotDeal, postTime);
        this.vehicleHistory = vehicleHistory;
        this.mileage = mileage;
    }

    public String getVehicleHistory() {
        return vehicleHistory;
    }

    public int getMileage() {
        return mileage;
    }
}

class DetailedUsedView extends DetailedView {

    public DetailedUsedView(String description) {
        super(description);
    }
}
