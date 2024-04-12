package com.example.universalyoga.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.universalyoga.R;
import com.example.universalyoga.databinding.FragmentAddClassBinding;
import com.example.universalyoga.db.DBHandler;
import com.example.universalyoga.model.AddClassDataModel;
import com.example.universalyoga.model.AddClassModel;
import com.example.universalyoga.model.AddYogaCourseDataModel;
import com.example.universalyoga.model.UserDataModel;
import com.example.universalyoga.utils.ApiClient;
import com.example.universalyoga.utils.ApiInterface;
import com.example.universalyoga.utils.Constant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddClassFragment extends Fragment {
    FragmentAddClassBinding binding;
    DBHandler dbHandler;
    RequestBody requestBody;
    String dateStr,teacherStr;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;

    String bookingId,userId,Timing,Duration,Capacity,Types_of_Class,Description,price,Day;
    ApiInterface apiInterface;
    ArrayList<UserDataModel> list;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = binding.inflate(inflater, container, false);
        View view = binding.getRoot();
        init();
        calender();
        return view;
    }

    public void init(){
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        bookingId = sharedpreferences.getString("BOOKING_ID", null);
        Log.e( "initbookingId", bookingId + "dc");
        userId = sharedpreferences.getString("USERID", null);
        Day = sharedpreferences.getString("day", null);
        Log.e( "initngId", dateStr + "dc");

        Timing = sharedpreferences.getString("Timing", null);
        Capacity = sharedpreferences.getString("Capacity", null);
        Duration = sharedpreferences.getString("Duration", null);
        price = sharedpreferences.getString("price", null);
        Types_of_Class = sharedpreferences.getString("Types_of_Class", null);
        Description = sharedpreferences.getString("Description", null);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        list = new ArrayList();
        dbHandler = new DBHandler(getContext());
        list = dbHandler.readData();

//        for (int i =1; i < list.size(); i++){
//            binding.courseTv.setText(list.get(i).getTypeOfClass());
//        }
        binding.courseTv.setText(Constant.COURSE_NAME);
        binding.submitClickTv.setOnClickListener(view -> {
            dateStr =  binding.dateTv.getText().toString().trim();
            teacherStr =  binding.teacherEt.getText().toString().trim();

            if (dateStr.isEmpty()){
                Toast.makeText(getContext(), "Please select a Date", Toast.LENGTH_SHORT).show();
            }else if (teacherStr.isEmpty()){
                Toast.makeText(getContext(), "Please enter a Teacher Name", Toast.LENGTH_SHORT).show();
            }else {
                addClassRequest(dateStr);
            }
        });
    }

    public void calender(){
        binding.calenderIv.setOnClickListener(view -> {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getContext(),
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            String selectedDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                            binding.dateTv.setText(selectedDate);
                        }
                    },
                    year, month, day);
            datePickerDialog.show();
        });
    }

    public void addClassRequest(String dateStr){
        String commentStr;

        AddYogaCourseDataModel addYogaCourseDataModel= new AddYogaCourseDataModel();
        commentStr = binding.typeClassTvEt.getText().toString().trim();
        list = new ArrayList();
        dbHandler = new DBHandler(getContext());
        list = dbHandler.readData();
        for (int i =1; i < list.size(); i++){
            binding.courseTv.setText(list.get(i).getTypeOfClass());
            addYogaCourseDataModel.setCourseName(list.get(i).getTypeOfClass());

        }
        addYogaCourseDataModel.setComments(commentStr);
        addYogaCourseDataModel.setDate(dateStr);
        addYogaCourseDataModel.setTeacher(teacherStr);
        Map<String, String> putDataParams = new HashMap<>();
        putDataParams.put("param1", Constant.BOOKING_ID);
        putDataParams.put("param2", Constant.COURSE_ID);
        Log.e("addClassRequesxxx", bookingId + "cd");

        JSONObject jsonObject = new JSONObject();
        try {
            // Add key-value pairs to the JSON object
            jsonObject.put("Day", Day);
            jsonObject.put("Timing", Timing);
            jsonObject.put("Capacity", Capacity);
            jsonObject.put("Timing", Timing);
            jsonObject.put("Duration", Duration);
            jsonObject.put("price", price);
            jsonObject.put("Timing", Timing);
            jsonObject.put("Types_of_Class", Types_of_Class);
            jsonObject.put("Description", Description);
            jsonObject.put("Date", dateStr);
            jsonObject.put("Teacher", teacherStr);
            jsonObject.put("Comments", commentStr);

        }catch (JSONException e) {
            e.printStackTrace();
        }
        requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        Log.e("addClassRequesxx", requestBody + "xcxc");
        Call<AddYogaCourseDataModel> call = apiInterface.editClassesDataByAdmin(bookingId, requestBody);
        call.enqueue(new Callback<AddYogaCourseDataModel>() {
            @Override
            public void onResponse(Call<AddYogaCourseDataModel> call, Response<AddYogaCourseDataModel> response) {
                if (response.isSuccessful()){

                    dbHandler.insetDate(dateStr);
                    HomeFragment homeFragment = new HomeFragment();
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, homeFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    Toast.makeText(getActivity(), "Class Added Successfully", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(getContext(), "Try again" , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddYogaCourseDataModel> call, Throwable t) {
            }
        });
    }

}
