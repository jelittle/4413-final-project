package com.user.userService.models;

@Entity
public class Vehicle {
    @Id
    private Long purchaseId;            // PK
    String vehicleId;

    public Vehicle() {

    }

    public Vehicle(Long purchaseId, String vehicleId) {
        this.purchaseId = purchaseId;
        this.vehicleId = vehicleId;
    }

    public Long getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Long purchaseId) {
        this.purchaseId = purchaseId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }
}
