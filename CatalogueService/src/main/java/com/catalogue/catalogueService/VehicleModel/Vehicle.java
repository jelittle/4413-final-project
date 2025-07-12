package com.catalogue.catalogueService.VehicleModel;



import com.catalogue.catalogueService.ExtCommController;
import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.Map;

/**
 * This is the completed JSON file for catalogue in java class form
 */
public class Vehicle {

    @Id
    private String id;

    private ExtCommController.requestBrand make;
    private String model;
    private ExtCommController.requestBodyType bodyType;
    private Integer modelYear;
    private Integer price;
    private Double rating;
    private Integer numRatings;
    private Integer quantity;
    private Boolean isHotDeal;
    private Boolean isNew;
    private String description;
    private String thumbnail;
    private List<String> images;
    private Integer mileage;
    private ExtCommController.requestHistory history;
    private Map<String, Object> customizations;

    public Vehicle(ExtCommController.requestBrand make, String model, ExtCommController.requestBodyType bodyType,
                   Integer modelYear, Integer price, Double rating, Integer numRatings, Integer quantity, Boolean isHotDeal,
                   Boolean isNew, String description, String thumbnail, List<String> images, Integer mileage,
                   ExtCommController.requestHistory history, Map<String, Object> customizations) {
        this.make = make;
        this.model = model;
        this.bodyType = bodyType;
        this.modelYear = modelYear;
        this.price = price;
        this.rating = rating;
        this.numRatings = numRatings;
        this.quantity = quantity;
        this.isHotDeal = isHotDeal;
        this.isNew = isNew;
        this.description = description;
        this.thumbnail = thumbnail;
        this.images = images;
        this.mileage = mileage;
        this.history = history;
        this.customizations = customizations;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ExtCommController.requestBrand getMake() {
        return make;
    }

    public void setMake(ExtCommController.requestBrand make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public ExtCommController.requestBodyType getBodyType() {
        return bodyType;
    }

    public void setBodyType(ExtCommController.requestBodyType bodyType) {
        this.bodyType = bodyType;
    }

    public Integer getModelYear() {
        return modelYear;
    }

    public void setModelYear(Integer modelYear) {
        this.modelYear = modelYear;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Boolean getHotDeal() {
        return isHotDeal;
    }

    public void setHotDeal(Boolean hotDeal) {
        isHotDeal = hotDeal;
    }

    public Boolean getNew() {
        return isNew;
    }

    public void setNew(Boolean aNew) {
        isNew = aNew;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public Map<String, Object> getCustomizations() {
        return customizations;
    }

    public void setCustomizations(Map<String, Object> customizations) {
        this.customizations = customizations;
    }

    public ExtCommController.requestHistory getHistory() {
        return history;
    }

    public void setHistory(ExtCommController.requestHistory history) {
        this.history = history;
    }

    public Integer getNumRatings() {
        return numRatings;
    }

    public void setNumRatings(Integer numRatings) {
        this.numRatings = numRatings;
    }

}
