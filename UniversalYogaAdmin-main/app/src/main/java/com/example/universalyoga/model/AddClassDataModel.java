package com.example.universalyoga.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddClassDataModel {
//    public AddClassDataModel(String day, String timing, String capacity, String duration, String price, String typesOfClass, String description) {
//        this.day = day;
//        this.timing = timing;
//        this.capacity = capacity;
//        this.duration = duration;
//        this.price = price;
//        this.typesOfClass = typesOfClass;
//        this.description = description;
//    }

    @SerializedName("Day")
    @Expose
    private String day;
    @SerializedName("Timing")
    @Expose
    private String timing;
    @SerializedName("Capacity")
    @Expose
    private String capacity;
    @SerializedName("Duration")
    @Expose
    private String duration;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("Types_of_Class")
    @Expose
    private String typesOfClass;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("deleteFlag")
    @Expose
    private Boolean deleteFlag;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTypesOfClass() {
        return typesOfClass;
    }

    public void setTypesOfClass(String typesOfClass) {
        this.typesOfClass = typesOfClass;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    @Override
    public String toString() {
        return "AddClassDataModel{" +
                "day='" + day + '\'' +
                ", timing='" + timing + '\'' +
                ", capacity='" + capacity + '\'' +
                ", duration='" + duration + '\'' +
                ", price='" + price + '\'' +
                ", typesOfClass='" + typesOfClass + '\'' +
                ", description='" + description + '\'' +
                ", deleteFlag=" + deleteFlag +
                ", id='" + id + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", v=" + v +
                '}';
    }
}

