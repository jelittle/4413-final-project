package com.catalogue.catalogueService;

import com.catalogue.catalogueService.VehicleModel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


import static com.catalogue.catalogueService.ExtCommController.requestSortParam.MILEAGE;
import static com.catalogue.catalogueService.ExtCommController.requestType.NEW;


/**
 * The External Communication Controller
 * Entry point to the Catalogue Database
 * Default parameters are expected to disable expected functionality
 */
@RestController
public class ExtCommController {

    @Autowired
    DatabaseInterface vehicleRepo;

    @Autowired
    MongoDAO mongoDAO;


    final static String NEW_COLLECTION_NAME = "vehicle";
    final static String USED_COLLECTION_NAME = "vehicle";


    public enum requestBrand     {Tesla, BYD, BMW, Mercedes, Volkswagen, Hyundai, KIA, Ford, Nissan, Rivian, Toyota}
    public enum requestBodyType  {Sedan, SUV, Hatchback, Coupe}
    public enum requestHistory   {New, Clean, Damaged, Totaled}

    public enum requestType      {NEW, USED, MIXED}
    public enum requestSortParam {ID, PRICE, MILEAGE}

    /**
     *
     * Catalogue request with only new/used/mixed vehicles param, supports pagination through vehicleId param
     * Defaults to mixed/page 0 if neither is provided
     *
     * @param vehicleId - the vehicleId of the last vehicle in the previous page
     * @param vehicleType - Should take on a value of [NEW/USED], returns which vehicle category to search
     * @return a JSON list of at most QUERY_LIMIT items, that contains a page of vehicles
     */
    @GetMapping("/vehicle/list")
    public List<Vehicle> getListOfVehicles(@RequestParam(value = "vehicleId", required = false) String vehicleId,
                                           @RequestParam(value = "type", defaultValue = "NEW") requestType vehicleType) {

        return mongoDAO.simpleQuery(vehicleId, vehicleType);
    }

    /**
     * Most complex query, implements Pagination, Sorting, Filtering
     * @param sortParam - The parameter that the sorting is performed over, can be one of [VEHICLE_ID/PRICE/MILEAGE]
     *                  DEFAULT is VEHICLE_ID, MILEAGE and vehicleType=NEW is an invalid combination.
     * @param sortDirection - The order of sorting, one of [ASC/DESC]
     * @param vehicleType - Query for new or used vehicle, value take on one of [NEW/USED]
     * @return - Ordered page of filtered sorted vehicles
     */
    @GetMapping("/vehicle/list/ordered/{param}/{mode}")
    public List<Vehicle> getOrderedList(@PathVariable("param") requestSortParam sortParam,
                                        @PathVariable("mode") Sort.Direction sortDirection,
                                        @RequestParam(value = "vehicleId", required = false) String vehicleId,
                                        @RequestParam(value = "price", defaultValue = "-1") int price,
                                        @RequestParam(value = "mileage", defaultValue = "-1") int mileage,
                                        @RequestParam(value = "type", defaultValue = "NEW") requestType vehicleType,
                                        @RequestBody(required = false) VehicleFilter filterOptions)  {

        if(sortParam == MILEAGE && vehicleType == NEW){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mileage Not Valid Sort Param For New Vehicles");
        }

        String collectionName = selectCollection(vehicleType);

        switch (sortParam){
            case PRICE -> {
                return mongoDAO.orderedFilteredQuery(price, vehicleType, sortDirection, "basicView.price", filterOptions);
            }
            case MILEAGE -> {
                return mongoDAO.orderedFilteredQuery(mileage, vehicleType, sortDirection, "basicView.mileage", filterOptions);
            }
        }
        return mongoDAO.orderedFilteredQuery(vehicleId, vehicleType, sortDirection, "basicView._id", filterOptions); // DEFAULT to vehicleId
    }


    /**
     * Utility method, takes a requestType, returns collection to search in DB
     * @param vehicleType - requestType enum, one of [NEW/USED]
     * @return - NEW_COLLECTION_NAME or USED_COLLECTION_NAME
     */
    private String selectCollection(requestType vehicleType) {
        return (vehicleType == NEW) ? NEW_COLLECTION_NAME : USED_COLLECTION_NAME;
    }


    @PostMapping("/vehicle")
    public String postNewVehicle(@RequestBody Vehicle vehicle){

        vehicleRepo.save(vehicle);

        return "Success";
    }

    @GetMapping("/vehicle/format")
    @ResponseBody
    public ResponseEntity<String> getVehicleFormat(){

        String str = '{' +
                "\"make\" : \"ENUM\"," +
                "\"model\" : \"STRING\"," +
                "\"bodyType\" : \"ENUM\"," +
                "\"modelYear\" : \"INTEGER\"," +
                "\"price\" : \"INTEGER\"," +
                "\"rating\" : \"FLOAT\"," +
                "\"quantity\" : \"INTEGER\"," +
                "\"isHotDeal\" : \"BOOLEAN\"," +
                "\"isNew\" : \"BOOLEAN\"," +
                "\"description\" : \"STRING\"," +
                "\"thumbnail\" : \"URL\"," +
                "\"images\" : \"[URL]\"," +
                "\"mileage\" : \"INTEGER\"," +
                "\"history\" : \"ENUM\"," +
                "\"customizations\" : \"JSON_OBJ\"" +
                '}';

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(str);
    }

}