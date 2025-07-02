package com.catalogue.catalogueService;

import com.catalogue.catalogueService.VehicleModel.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;

@Repository
public class MongoDAO {
    // MAKE COMPOUND INDEX ON (_id, price, mileage)

    @Autowired
    private MongoTemplate queryTemplate;

    final static int QUERY_LIMIT = 3; // The MAX number of vehicles returned by a query, can be set to less by request



    public List<Vehicle> simpleQuery(String vehicleId, String collectionName) {

        Query query = new Query();                                            // Building query
        if(vehicleId != null) {                                               // Pagination
            query.addCriteria(Criteria.where("_id").gt(vehicleId));      // Start page after vehicleId if provided
        }
        query.fields().exclude("detailedView");                          // Exclude detailedView (Will be null)
        query.limit(QUERY_LIMIT);                                             // Limit to QUERY_LIMIT Vehicles

        return queryTemplate.find(query, Vehicle.class, collectionName);      // Run query and return output
    }


    public List<Vehicle> detailedQuery(List<String> vehicleIdList, String collectionName) {

        Query query = new Query();                                            // Building query
        query.addCriteria(Criteria.where("_id").in(vehicleIdList));      // Search by ID
        query.limit(QUERY_LIMIT);                                             // Limit to QUERY_LIMIT Vehicles

        return queryTemplate.find(query, Vehicle.class, collectionName);      // Run query and return output
    }

    public List<Vehicle> orderedFilteredQuery(int sortInt, String collectionName,
                                                     Sort.Direction sortDirection,
                                                     String sortString) {

        Query query = new Query();
        query.limit(QUERY_LIMIT);                                               // Limit to QUERY_LIMIT Vehicles
        query.with(Sort.by(sortDirection, sortString));                         // Setting sorting

        if(sortInt == -1){
            return queryTemplate.find(query, Vehicle.class, collectionName);
        }

        switch (sortDirection) {
            case ASC -> query.addCriteria(Criteria.where(sortString).gt(sortInt));
            case DESC -> query.addCriteria(Criteria.where(sortString).lt(sortInt));
            case null, default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order must be ASC or DESC");
        }

        return queryTemplate.find(query, Vehicle.class, collectionName);
    }


    public List<Vehicle> orderedFilteredQuery(String sortField, String collectionName, Sort.Direction sortDirection, String sortString) {
        Query query = new Query();
        switch (sortDirection){
            case ASC -> query.addCriteria(Criteria.where(sortString).gt(sortField));
            case DESC -> query.addCriteria(Criteria.where(sortString).lt(sortField));
            case null, default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order must be ASC or DESC");
        }
        query.limit(QUERY_LIMIT);                                               // Limit to QUERY_LIMIT Vehicles
        query.with(Sort.by(sortDirection, sortString));                         // Setting sorting
        return queryTemplate.find(query, Vehicle.class, collectionName);
    }

}
