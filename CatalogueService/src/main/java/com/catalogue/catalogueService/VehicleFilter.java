package com.catalogue.catalogueService;

import com.fasterxml.jackson.annotation.JsonAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.ArrayList;
import java.util.List;

import static com.catalogue.catalogueService.ExtCommController.requestBrand;
import static com.catalogue.catalogueService.ExtCommController.requestBodyType;
import static com.catalogue.catalogueService.ExtCommController.requestHistory;

@Document(value = "vehicleFilter")
public class VehicleFilter {


    @JsonAlias("make")
    private List<requestBrand> makeList;

    @JsonAlias("bodyType")
    private List<requestBodyType> bodyTypeList;

    @JsonAlias("history")
    private List<requestHistory> historyList;

    @JsonAlias("hotDeal")
    private Boolean isHotDeal;

    public List<Criteria> getFilterList() {
        List<Criteria> output = new ArrayList<>();

        if(makeList != null && !makeList.isEmpty()){
            output.add(Criteria.where("basicView.make").in(makeList));
        }

        if(bodyTypeList != null && !bodyTypeList.isEmpty()){
            output.add(Criteria.where("basicView.bodyType").in(bodyTypeList));
        }

        if(historyList != null && !historyList.isEmpty()){
            output.add(Criteria.where("basicView.vehicleHistory").in(historyList));
        }

        if (isHotDeal != null) {
            output.add(Criteria.where("basicView.isHotDeal").is(isHotDeal));
        }

        return output;
    }

    public List<requestBrand> getMakeList() {
        return makeList;
    }

    public void setMakeList(List<requestBrand> brand) {
        this.makeList = brand;
    }

    public List<requestBodyType> getBodyTypeList() {
        return bodyTypeList;
    }

    public void setBodyTypeList(List<requestBodyType> bodyType) {
        this.bodyTypeList = bodyType;
    }

    public List<requestHistory> getHistoryList() {
        return historyList;
    }

    public void setHistoryList(List<requestHistory> history) {
        this.historyList = history;
    }

    public Boolean getHotDeal() {
        return isHotDeal;
    }

    public void setHotDeal(Boolean hotDeal) {
        isHotDeal = hotDeal;
    }
}
