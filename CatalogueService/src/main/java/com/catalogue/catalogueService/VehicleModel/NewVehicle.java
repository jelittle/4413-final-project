package com.catalogue.catalogueService.VehicleModel;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(value = "newVehicle")
public class NewVehicle{
    @Id
    private String id;

    // Sub Objects
    @Field("basicView")
    private BasicNewView basicView;

    @Field("detailedView")
    private DetailedNewView detailedView;

//    @Field("customization")
//    private Customization[] customization;


    public NewVehicle(String id, BasicNewView basicView, DetailedNewView detailedView) {
        this.id = id;
        this.basicView = basicView;
        this.detailedView = detailedView;
//        this.customization = customization;
    }


    public String getId() {
        return id;
    }

    public BasicNewView getBasicView() {
        return basicView;
    }

    public DetailedNewView getDetailedView() {
        return detailedView;
    }

//    public Customization[] getCustomization() {
//        return customization;
//    }
}

class BasicNewView extends BasicView {

    public BasicNewView(String make, String model, String bodyType, Integer modelYear, Integer price, Boolean isHotDeal) {
        super(make, model, bodyType, modelYear, price, isHotDeal);
    }
}

class DetailedNewView extends DetailedView {

    public DetailedNewView(String description) {
        super(description);
    }
}

//class Customization {
//    private String optionId;
//    private String optionName;
//    private String category; // Performance, cosmetic, etc.
//    private int price;
//
//    public String getOptionId() {
//        return optionId;
//    }
//
//    public String getOptionName() {
//        return optionName;
//    }
//
//    public String getCategory() {
//        return category;
//    }
//
//    public int getPrice() {
//        return price;
//    }
//}