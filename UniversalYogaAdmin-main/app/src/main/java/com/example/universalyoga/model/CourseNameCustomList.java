package com.example.universalyoga.model;

public class CourseNameCustomList {
    String id;
    String courseName;
    String deleteFlag;
    String createdAt;
    String updatedAt;

    public CourseNameCustomList(String id, String courseName, String deleteFlag, String createdAt, String updatedAt) {
        this.id = id;
        this.courseName = courseName;
        this.deleteFlag = deleteFlag;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

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

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
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
}
