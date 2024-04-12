package com.example.universalyoga.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllCourseNameDataModel {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("Course_Name")
    @Expose
    private String courseName;
    @SerializedName("deleteFlag")
    @Expose
    private Boolean deleteFlag;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
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
        return "AllCourseNameDataModel{" +
                "id='" + id + '\'' +
                ", courseName='" + courseName + '\'' +
                ", deleteFlag=" + deleteFlag +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", v=" + v +
                '}';
    }
}
