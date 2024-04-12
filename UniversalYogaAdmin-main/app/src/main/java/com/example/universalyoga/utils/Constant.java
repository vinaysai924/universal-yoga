package com.example.universalyoga.utils;

public class Constant {
    public static String BOOKING_ID = "bookingID";
    public static String COURSE_ID = "courseID";
    public static String COURSE_NAME = "courseName";
    public static String USER_ID = "userId";

    public static void setUserId(String userId) {
        USER_ID = userId;
    }

    public static String getUserId() {
        return USER_ID;
    }

    public static void setCourseId(String courseID) {
        COURSE_ID = courseID;
    }

    public static String getCourseId() {
        return COURSE_ID;
    }
}
