package com.example.universalyoga.model;

public class UserDataModel {
    private int id;
    private String typeOfClass;
    private String[] week;
    private String price;
    private String date;
    public UserDataModel(String typeOfClass, String[] week, String price) {
        this.id = id;
        this.typeOfClass = typeOfClass;
        this.week = week;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeOfClass() {
        return typeOfClass;
    }

    public void setTypeOfClass(String typeOfClass) {
        this.typeOfClass = typeOfClass;
    }

    public String[] getWeek() {
        return week;
    }

    public void setWeek(String[] week) {
        this.week = week;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "UserDataModel{" +
                "id=" + id +
                ", typeOfClass='" + typeOfClass + '\'' +
                ", week='" + week + '\'' +
                ", price='" + price + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
