package com.user.userService.services.kafka;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;

public class UsagePayload {

    private String customerUsername;
    private LocalDate fullDate;
    private Year year;
    private Month month;
    private Integer day;
    private DayOfWeek dayOfWeek;
    private String action;

    /**
     * DON'T USE THIS ONE, USE THE ONE WITHOUT DATE
     */
    public UsagePayload(String customerUsername, LocalDate fullDate, Year year, Month month, Integer day, DayOfWeek dayOfWeek, String action) {
        this.customerUsername = customerUsername;
        this.fullDate = fullDate;
        this.year = year;
        this.month = month;
        this.day = day;
        this.dayOfWeek = dayOfWeek;
        this.action = action;
    }

    public UsagePayload() {
    }

    /**
     * Use this constructor to build UsagePayload OBJ
     *
     * @param customerUsername - String
     * @param fullDate - Current Date
     * @param action -  Action Performed
     */
    public UsagePayload(String customerUsername, LocalDate fullDate, String action) {
        this.customerUsername = customerUsername;
        this.fullDate = fullDate;
        this.year = Year.of(fullDate.getYear());
        this.month = fullDate.getMonth();
        this.day = fullDate.getDayOfYear();
        this.dayOfWeek = fullDate.getDayOfWeek();
        this.action = action;
    }


    public String getCustomerUsername() {
        return customerUsername;
    }

    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    }

    public LocalDate getFullDate() {
        return fullDate;
    }

    public void setFullDate(LocalDate fullDate) {
        this.fullDate = fullDate;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}