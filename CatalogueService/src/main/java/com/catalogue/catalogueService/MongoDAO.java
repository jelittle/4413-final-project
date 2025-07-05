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

    final static int QUERY_LIMIT = 10; // The MAX number of vehicles returned by a query, can be set to less by request



    public List<Vehicle> simpleQuery(String vehicleId, ExtCommController.requestType vehicleType) {

        Query query = new Query();                                            // Building query
        if(vehicleId != null) {                                               // Pagination
            query.addCriteria(Criteria.where("_id").gt(vehicleId));      // Start page after vehicleId if provided
        }
        setVehicleType(query,vehicleType);
        query.limit(QUERY_LIMIT);                                             // Limit to QUERY_LIMIT Vehicles

        return queryTemplate.find(query, Vehicle.class);      // Run query and return output
    }


    public List<Vehicle> orderedFilteredQuery(int sortInt,
                                              ExtCommController.requestType vehicleType,
                                              Sort.Direction sortDirection,
                                              String sortString,
                                              VehicleFilter filterOptions) {

        Query query = new Query();
        query.limit(QUERY_LIMIT);                                               // Limit to QUERY_LIMIT Vehicles
        applyFilter(query, filterOptions);
        setVehicleType(query,vehicleType);
        query.with(Sort.by(sortDirection, sortString));                         // Setting sorting

        if(sortInt == -1){
            return queryTemplate.find(query, Vehicle.class);
        }

        switch (sortDirection) {
            case ASC -> query.addCriteria(Criteria.where(sortString).gt(sortInt));
            case DESC -> query.addCriteria(Criteria.where(sortString).lt(sortInt));
            case null, default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order must be ASC or DESC");
        }

        return queryTemplate.find(query, Vehicle.class);
    }


    public List<Vehicle> orderedFilteredQuery(String sortField,
                                              ExtCommController.requestType vehicleType,
                                              Sort.Direction sortDirection,
                                              String sortString,
                                              VehicleFilter filterOptions) {
        Query query = new Query();
        applyFilter(query, filterOptions);

        query.limit(QUERY_LIMIT);                                               // Limit to QUERY_LIMIT Vehicles
        query.with(Sort.by(sortDirection, sortString));                         // Setting sorting
        setVehicleType(query,vehicleType);
        if(sortField == null){
            return queryTemplate.find(query, Vehicle.class);
        }

        switch (sortDirection){
            case ASC -> query.addCriteria(Criteria.where(sortString).gt(sortField));
            case DESC -> query.addCriteria(Criteria.where(sortString).lt(sortField));
            case null, default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order must be ASC or DESC");
        }

        return queryTemplate.find(query, Vehicle.class);
    }

    private void applyFilter(Query query, VehicleFilter filterOptions) {
        if(filterOptions == null){return;}
        List<Criteria> filterList = filterOptions.getFilterList();
        if(!filterList.isEmpty()){
            query.addCriteria(new Criteria().andOperator(filterList.toArray(new Criteria[0])));
        }
    }

    private void setVehicleType(Query query, ExtCommController.requestType vehicleType) {
        switch (vehicleType){
            case USED -> query.addCriteria(Criteria.where("isNew").is(false));
            case NEW -> query.addCriteria(Criteria.where("isNew").is(true));
        }
    }
}
