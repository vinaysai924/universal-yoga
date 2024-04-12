package com.example.universalyoga.utils;

import com.example.universalyoga.model.AddClassDataModel;
import com.example.universalyoga.model.AddClassModel;
import com.example.universalyoga.model.AddYogaCourseDataModel;
import com.example.universalyoga.model.AllCourseNameModel;
import com.example.universalyoga.model.BookingModel;
import com.example.universalyoga.model.CourseNameAddDataModel;
import com.example.universalyoga.model.DeleteClassDataModel;
import com.example.universalyoga.model.LoginData;
import com.example.universalyoga.model.LoginModel;
import com.example.universalyoga.model.SignupDataModel;
import com.example.universalyoga.model.UpdateClassDataModel;
import com.example.universalyoga.model.YogaClassDetailsDataModel;

import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @POST("createAdmin")
    Call<SignupDataModel> signUpAdmin(@Body SignupDataModel signupDataModel);

    @POST("adminLogin")
    Call<LoginModel> loginAdmin(@Body RequestBody loginData);

    @POST("addClassesByAdmin")
    Call<AddClassModel> addClass(@Body AddClassDataModel addClassDataModel);

    @GET("getClassesById/")
    Call<YogaClassDetailsDataModel> getClassDetails(@Body YogaClassDetailsDataModel addClassDataModel);

    @PUT("updateClassesById/")
    Call<UpdateClassDataModel> updateClass(@Body UpdateClassDataModel updateClassDataModel);

    @DELETE("deleteClassDataById/")
    Call<DeleteClassDataModel> deleteClass(@Body DeleteClassDataModel deleteClassDataModel);

    @PUT("editClassesDataByAdmin/{id1}")
    Call<AddYogaCourseDataModel> editClassesDataByAdmin(@Path("id1") String id1,
                                               @Body RequestBody requestBody);

    @POST("addYogaCourses")
    Call<CourseNameAddDataModel> addCourseName(@Body CourseNameAddDataModel courseNameAddDataModel);

    @GET("getCourseData/All")
    Call<AllCourseNameModel> getCourseName();

    @GET("getBookingListData/All")
    Call<BookingModel> getBooking();
}
