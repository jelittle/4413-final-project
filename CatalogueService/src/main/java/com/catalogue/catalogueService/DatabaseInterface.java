package com.catalogue.catalogueService;


import com.catalogue.catalogueService.VehicleModel.NewVehicle;
import com.catalogue.catalogueService.VehicleModel.Vehicle;
import org.json.JSONArray;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 * The interface used for internal communication with mongoDB
 */

public interface DatabaseInterface extends MongoRepository<NewVehicle,String> {
    
}


