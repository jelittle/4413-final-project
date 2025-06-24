package com.catalogue.catalogueService;

import com.catalogue.catalogueService.VehicleModel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static com.catalogue.catalogueService.requestType.MIXED;


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
    private MongoTemplate queryTemplate;

    final static int QUERY_LIMIT = 7; // The MAX number of vehicles returned by a query, can be set to less by request





    @GetMapping("/vehicle/list")
    public List<NewVehicle> getListOfVehicles(@RequestBody List<String> vehicleIdList,
                                           @RequestParam(value = "type", required = false) requestType status) { // new/used/mixed

        Query query = new Query();                                            // Building query
        query.addCriteria(Criteria.where("_id").in(vehicleIdList));      // Search by ID
        query.fields().exclude("detailedView");                          // Exclude detailedView (Will be null)
        query.limit(QUERY_LIMIT);                                             // Limit to QUERY_LIMIT Vehicles


        return processQuery(query, status); // Run query and return output

    }

    @GetMapping("/vehicle/list/detailed")
    public List<NewVehicle> getListOfVehiclesDetailed(@RequestBody List<String> vehicleIdList) {

        System.out.println(vehicleIdList.toString());

        Query query = new Query();                                            // Building query
        query.addCriteria(Criteria.where("_id").in(vehicleIdList));      // Search by ID
        query.limit(QUERY_LIMIT);                                             // Limit to QUERY_LIMIT Vehicles

        return queryTemplate.find(query, NewVehicle.class, "newVehicle"); // Run query and return output
    }

    @GetMapping("/vehicle/list/ordered/{param}/{mode}")
    public List<NewVehicle> getOrderedList(@PathVariable("param") String sortParam,               // price/mileage
                                        @PathVariable("mode") Sort.Direction sortDirection) {     // ASC/DESC


        Query query = new Query();                                              // Building query
        query.limit(QUERY_LIMIT);                                               // Limit to QUERY_LIMIT Vehicles
        query.with(Sort.by(sortDirection, "basicView." + sortParam)); // Setting sorting to



        return queryTemplate.find(query, NewVehicle.class, "newVehicle"); // Run query and return output
    }

    private boolean isDefault(String input) {
        return input.compareTo("-1") == 0;
    }

    private List<NewVehicle> processQuery(Query query, requestType status) {
//        List<Vehicle> newList = new LinkedList<>();
//        List<Vehicle> usedList = new LinkedList<>();
//
//        switch (status) {
//            case NEW:
//                newList = queryTemplate.find(query, Vehicle.class, "newVehicle");
//                break;
//            case USED:
//                usedList = queryTemplate.find(query, Vehicle.class, "usedVehicle");
//                break;
//            case MIXED:
//            case null, default:
//                newList = queryTemplate.find(query, Vehicle.class, "newVehicle");
//                usedList = queryTemplate.find(query, Vehicle.class, "usedVehicle");
//                break;
//        }
//
//        newList.addAll(usedList);
//        Collections.shuffle(newList);
//        System.out.println(newList);
        return queryTemplate.find(query, NewVehicle.class, "newVehicle");
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

enum requestType{
    NEW,
    USED,
    MIXED
}
