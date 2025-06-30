package com.catalogue.catalogueService;

import com.catalogue.catalogueService.VehicleModel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


import static com.catalogue.catalogueService.ExtCommController.requestSortParam.MILEAGE;
import static com.catalogue.catalogueService.ExtCommController.requestSortParam.PRICE;
import static com.catalogue.catalogueService.ExtCommController.requestType.NEW;
import static com.catalogue.catalogueService.ExtCommController.requestType.USED;


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


    final static String NEW_COLLECTION_NAME = "newVehicle";
    final static String USED_COLLECTION_NAME = "usedVehicle";


    // ENUMS storing valid parameters for filtering/sorting
    // Need to be filled and implemented in getOrderedList()

    public enum requestType      {NEW, USED}
    public enum requestBrand     {TOYOTA}
    public enum requestBodyType  {SEDAN, SUV}
    public enum requestHistory   {CLEAN, DAMAGED, TOTALED}
    public enum requestSortParam {VEHICLE_ID, PRICE, MILEAGE}

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

        String collectionName = selectCollection(vehicleType);
        return mongoDAO.simpleQuery(vehicleId, collectionName);
    }

    /**
     *
     * Catalogue request for a list of detailed vehicles,
     * used by detailed view and comparing different vehicles.
     *
     * @param vehicleIdList - List of vehicleId's being queried
     * @return - List of detailed vehicle views
     */
    @GetMapping("/vehicle/list/detailed")
    public List<Vehicle> getListOfVehiclesDetailed(@RequestBody List<String> vehicleIdList,
                                                      @RequestParam(value = "type", defaultValue = "NEW") requestType vehicleType) {

        String collectionName = selectCollection(vehicleType);
        return mongoDAO.detailedQuery(vehicleIdList,collectionName);
    }

    /**
     * Most complex query, implements Pagination, Sorting, Filtering
     * @param sortParam - The parameter that the sorting is performed over, can be one of [VEHICLE_ID/PRICE/MILEAGE]
     *                  DEFAULT is VEHICLE_ID, MILEAGE and vehicleType=NEW is an invalid combination.
     * @param sortDirection - The order of sorting, one of [ASC/DESC]
     * @param sortField - The previous last value of the sortParam (ie. if sorting on price ascending,
     *                  the max price of the prev page)
     * @param vehicleType - Query for new or used vehicle, value take on one of [NEW/USED]
     * @return - Ordered page of filtered sorted vehicles
     */
    @GetMapping("/vehicle/list/ordered/{param}/{mode}")
    public List<Vehicle> getOrderedList(@PathVariable("param") requestSortParam sortParam,
                                        @PathVariable("mode") Sort.Direction sortDirection,
                                        @RequestParam(value = "sortField", defaultValue = "-1") String sortField,
                                        @RequestParam(value = "type", defaultValue = "NEW") requestType vehicleType)  {


        if(sortParam == MILEAGE && vehicleType == NEW){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mileage Not Valid Sort Param For New Vehicles");
        }

        String collectionName = NEW_COLLECTION_NAME;                          // Default to new vehicle collection
        if(vehicleType == USED){collectionName = USED_COLLECTION_NAME;}       // If used vehicle requested, search used

        String sortString;
        if     (sortParam == PRICE)  { sortString = "basicView.price";}      // Sort by Price
        else if(sortParam == MILEAGE){ sortString = "basicView.mileage";}    // Sort by Mileage
        else                         { sortString = "basicView._id";}        // Default to id


        if(sortParam == PRICE || sortParam == MILEAGE){
            int sortInt = Integer.parseInt(sortField);
            return mongoDAO.orderedFilteredQuery(sortInt, collectionName, sortDirection, sortString);
        }

        return mongoDAO.orderedFilteredQuery(sortField, collectionName, sortDirection, sortString);
    }


    /**
     * Utility method, takes a requestType, returns collection to search in DB
     * @param vehicleType - requestType enum, one of [NEW/USED]
     * @return - NEW_COLLECTION_NAME or USED_COLLECTION_NAME
     */
    private String selectCollection(requestType vehicleType) {
        return (vehicleType == NEW) ? NEW_COLLECTION_NAME : USED_COLLECTION_NAME;
    }


    @PostMapping("/newVehicle")
    public String postNewVehicle(@RequestBody NewVehicle vehicle){

        vehicleRepo.save(vehicle);

        return "Success";
    }

    @PostMapping("/newVehicle/format")
    public String postNewVehicle(){
        return
            "{" +
                "\"basicView\" : {"+
                    "\"make\": \"FILL\","+
                    "\"model\": \"FILL\","+
                    "\"bodyType\": \"FILL\","+
                    "\"modelYear\": INT,"+
                    "\"price\": INT,"+
                    "\"isHotDeal\": BOOL,"+
                    "\"postTime\": INT"+
                "},"+
                "\"detailedView\" :{"+
                    "\"someExtras\": \"FILL\""+
                "}"+
            "}";
    }

}