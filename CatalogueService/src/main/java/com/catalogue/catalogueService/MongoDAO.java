package com.catalogue.catalogueService;

import com.catalogue.catalogueService.VehicleModel.Vehicle;
import com.catalogue.catalogueService.VehicleModel.VehicleUpdate;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;

@Repository
public class MongoDAO {
    // MAKE COMPOUND INDEX ON (_id, price, mileage)

    @Autowired
    private MongoTemplate queryTemplate;

    final static int QUERY_LIMIT = 10; // The MAX number of vehicles returned by a query



    public List<Vehicle> simpleQuery(String vehicleId, ExtCommController.requestType vehicleType) {

        Query query = new Query();                                            // Building query
        if(vehicleId != null) {                                               // Pagination
            query.addCriteria(Criteria.where("_id").gt(vehicleId));      // Start page after vehicleId if provided
        }
        setVehicleType(query,vehicleType);
        query.limit(QUERY_LIMIT);                                             // Limit to QUERY_LIMIT Vehicles

        return queryTemplate.find(query, Vehicle.class);      // Run query and return output
    }

    public List<Vehicle> listQuery(List<String> vehicleList) {

        Query query = new Query();                                            // Building query

        query.addCriteria(Criteria.where("_id").in(vehicleList));

        query.limit(QUERY_LIMIT);                                             // Limit to QUERY_LIMIT Vehicles

        return queryTemplate.find(query, Vehicle.class);      // Run query and return output
    }

    public boolean updateQuantity(List<VehicleUpdate> updateList) {

        for(VehicleUpdate updateItem: updateList) {
            Query query = new Query(Criteria.where("_id").is(updateItem.getVehicleId()));
            Update update = new Update();

            update.set("quantity", updateItem.getQuantity());

            UpdateResult ur = queryTemplate.updateFirst(query, update, Vehicle.class);
            if(!ur.wasAcknowledged()) {return false;}
        }
        return true;
    }

    public boolean updateRating(List<VehicleUpdate> updateList) {
        VehicleUpdate updateItem = updateList.getFirst();

        Query query = new Query(Criteria.where("_id").is(updateItem.getVehicleId()));
        Vehicle vehicle = queryTemplate.findOne(query, Vehicle.class);


        if (vehicle == null) {return false;}            // If vehicle does not exist, return error


        Integer numRatings = vehicle.getNumRatings();
        Double rating = vehicle.getRating();
        Integer newRating = updateItem.getRating();

        rating = ((rating*numRatings)+newRating)/(numRatings+1); // Weighted Avg
        rating = (double) Math.round(rating * 10) / 10;

        Update update = new Update();
        update.set("rating", rating);
        update.set("numRatings", numRatings+1);


        queryTemplate.updateFirst(query, update, Vehicle.class);
        return true;
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



    // =========================== UTILITY METHODS ===========================

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
