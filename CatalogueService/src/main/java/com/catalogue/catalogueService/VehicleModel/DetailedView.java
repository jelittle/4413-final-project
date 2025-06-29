package com.catalogue.catalogueService.VehicleModel;

import org.springframework.data.mongodb.core.mapping.Field;

/**
 * This class should contain the common elements of both New and Used Vehicles, stored in the DetailedView Object
 */
public class DetailedView {
    private String description;

    public DetailedView(String description) {
        this.description = description;
    }

    // Getter Methods Required By Jackson

    public String getDescription() {
        return description;
    }
}
