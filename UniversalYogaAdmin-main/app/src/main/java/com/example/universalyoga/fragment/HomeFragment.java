package com.example.universalyoga.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.universalyoga.R;
import com.example.universalyoga.databinding.FragmentHomeBinding;
import com.example.universalyoga.db.DBHandler;
import com.example.universalyoga.model.AddClassDataModel;
import com.example.universalyoga.model.AddClassModel;
import com.example.universalyoga.model.AllCourseNameDataModel;
import com.example.universalyoga.model.AllCourseNameModel;
import com.example.universalyoga.utils.ApiClient;
import com.example.universalyoga.utils.ApiInterface;
import com.example.universalyoga.utils.Constant;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    FragmentHomeBinding binding;
    DBHandler dbHandler;
    String typeClass,time,courseName,courseNameStr;
    List<AllCourseNameDataModel> allCourseNameDataModels;
    View view;
    public static final String MyPREFERENCES = "MyPrefs" ;

    public static final String YOGA_CLASS_ID = "yogaClass";
    public static final String SHARED_PREFS = "shared_prefs";
    SharedPreferences sharedPreferences;
    ApiInterface apiInterface;
    private SpinAdapter adapter;
    String days;
    String dayWeekSelectedStr, priceStr;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = binding.inflate(inflater, container, false);
        view = binding.getRoot();
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        getCourseNameRequest();
        timeCourse();
        click();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    public void click(){
        dbHandler = new DBHandler(getContext());
        binding.submitClickTv.setOnClickListener(view -> {
            checkedButtonClicked(view);
            validation();
        });

        binding.confirmTv.setOnClickListener(view -> {
            typeClass = binding.typeClassSpinner.getSelectedItem().toString();
//            dbHandler.insetTypeClassData(typeClass,priceStr, day);
            addClassRequest();

        });

        binding.editTv.setOnClickListener(view -> {
            inputEnable();
        });
    }

    public void inputEnable(){
        binding.submitClickTv.setVisibility(View.VISIBLE);
        binding.editTv.setVisibility(View.GONE);
        binding.confirmTv.setVisibility(View.GONE);
        binding.typeClassSpinner.setEnabled(true);
        binding.mondayCheckBox.setEnabled(true);
        binding.tuesdayCheckBox.setEnabled(true);
        binding.wednesdayCheckBox.setEnabled(true);
        binding.thursdayCheckBox.setEnabled(true);
        binding.fridayCheckBox.setEnabled(true);
        binding.saturdayCheckBox.setEnabled(true);
        binding.sundayCheckBox.setEnabled(true);
        binding.capacityEt.setEnabled(true);
        binding.durationEt.setEnabled(true);
        binding.priceClassEt.setEnabled(true);
        binding.discripitionEt.setEnabled(true);
    }

    public void validation(){
        String capacityStr = binding.capacityEt.getText().toString().trim();
        String durationStr = binding.durationEt.getText().toString().trim();
        String discripitionStr = binding.discripitionEt.getText().toString().trim();

        priceStr = binding.priceClassEt.getText().toString().trim();

        if (!binding.mondayCheckBox.isChecked() && !binding.tuesdayCheckBox.isChecked() && !binding.wednesdayCheckBox.isChecked() && !binding.thursdayCheckBox.isChecked()&&
                !binding.fridayCheckBox.isChecked() && !binding.saturdayCheckBox.isChecked() && !binding.sundayCheckBox.isChecked()){
            Toast.makeText(getContext(), "Please Select Day", Toast.LENGTH_SHORT).show();
        }

        else if (capacityStr.isEmpty()){
            Toast.makeText(getContext(), "Please Enter Capacity", Toast.LENGTH_SHORT).show();
        }
        else if (durationStr.isEmpty()){
            Toast.makeText(getContext(), "Please Enter Duration", Toast.LENGTH_SHORT).show();
        }
        else if (priceStr.isEmpty()){
            Toast.makeText(getContext(), "Please Enter Price", Toast.LENGTH_SHORT).show();
        }
        else if (discripitionStr.isEmpty()){
            Toast.makeText(getContext(), "Please Enter Description", Toast.LENGTH_SHORT).show();
        }
        else {
            inputDisable();
        }
    }

    public void inputDisable(){
        binding.submitClickTv.setVisibility(View.GONE);
        binding.editTv.setVisibility(View.VISIBLE);
        binding.confirmTv.setVisibility(View.VISIBLE);
        binding.typeClassSpinner.setEnabled(false);
        binding.mondayCheckBox.setEnabled(false);
        binding.tuesdayCheckBox.setEnabled(false);
        binding.wednesdayCheckBox.setEnabled(false);
        binding.thursdayCheckBox.setEnabled(false);
        binding.fridayCheckBox.setEnabled(false);
        binding.saturdayCheckBox.setEnabled(false);
        binding.sundayCheckBox.setEnabled(false);
        binding.capacityEt.setEnabled(false);
        binding.durationEt.setEnabled(false);
        binding.priceClassEt.setEnabled(false);
        binding.discripitionEt.setEnabled(false);
    }

    public void checkedButtonClicked(View view){
        if (binding.mondayCheckBox.isChecked()){
            days = "Monday";
      }if (binding.tuesdayCheckBox.isChecked()){
            days = "Tuesday";
      }if (binding.wednesdayCheckBox.isChecked()){
            days = "Wednesday";
      }if (binding.thursdayCheckBox.isChecked()){
            days = "Thursday";
      }if (binding.fridayCheckBox.isChecked()){
            days = "Friday";
      }if (binding.saturdayCheckBox.isChecked()){
            days = "Saturday";
      }if (binding.sundayCheckBox.isChecked()){
            days = "Sunday";
      }
    }


    public void timeCourse(){
        String[] timeCourse = {"6:00" ,"7:00", "8:00","9:00", "10:00" ,"11:00","12:00" ,"13:00","14:00" ,"15:00","16:00", "17:00","18:00" ,"19:00","2:00" ,"21:00"};
            binding.timeCourseSpinner.setOnItemSelectedListener(this);
            ArrayAdapter aa = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,timeCourse);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            binding.timeCourseSpinner.setAdapter(aa);
    }

    public void typeClass() {
        // Extract Course_Name values from the response data and create an array
        List<String> courseNames = new ArrayList<>();
        for (AllCourseNameDataModel data : allCourseNameDataModels) {
            courseNames.add(data.getCourseName());
        }

        // Create an ArrayAdapter using the courseNames array
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, courseNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the adapter for the spinner
        binding.typeClassSpinner.setAdapter(adapter);

        // Set the item selection listener
        binding.typeClassSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                AllCourseNameDataModel selectedData = allCourseNameDataModels.get(i);
                courseName = selectedData.getCourseName();
                Constant.COURSE_ID = selectedData.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Handle nothing selected event if needed
            }
        });
    }








    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        ((TextView) view).setTextColor(Color.BLACK); //Change selected text color
        Spinner spin = (Spinner)adapterView;
        Spinner spin2 = (Spinner)adapterView;

        if (spin.getId() == R.id.timeCourseSpinner){
            time = String.valueOf(adapterView.getItemAtPosition(i));
        }
        dayWeekSelectedStr = String.valueOf(i +1);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void addClassRequest(){
        String capacityStr, durationStr,priceStr,descriptionStr;
        capacityStr = binding.capacityEt.getText().toString().trim();
        durationStr = binding.durationEt.getText().toString().trim();
        priceStr = binding.priceClassEt.getText().toString().trim();
        descriptionStr = binding.discripitionEt.getText().toString().trim();

        AddClassModel addClassModel = new AddClassModel();
        AddClassDataModel addClassDataModel = new AddClassDataModel();
        addClassDataModel.setTypesOfClass(courseName);
        addClassDataModel.setCapacity(capacityStr);
        addClassDataModel.setTiming(time);
        addClassDataModel.setDay(days);
        addClassDataModel.setDescription(descriptionStr);
        addClassDataModel.setPrice(priceStr);
        addClassDataModel.setDuration(durationStr);
        addClassModel.setData(addClassDataModel);

        Call<AddClassModel> call = apiInterface.addClass(addClassDataModel);
        call.enqueue(new Callback<AddClassModel>() {
            @Override
            public void onResponse(Call<AddClassModel> call, Response<AddClassModel> response) {
                if (response.isSuccessful()){
//                    addClassDataModel1 = response.body();
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    String yogaClassId = addClassDataModel1.getId();
//                    editor.putString(YOGA_CLASS_ID, yogaClassId);
//                    Log.e("onResponsexxxId",addClassDataModel1  + "cdc");
//                    sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
//                    editor.apply();
                    ArrayList<AddClassModel> dataList = new ArrayList<>();
                    AddClassModel addClassModel1 = response.body();
                    dataList.add(addClassModel1);
                    AddClassDataModel addClassDataModel1 = addClassModel1.getData();
                    Constant.BOOKING_ID = addClassDataModel1.getId();
                    Constant.COURSE_NAME = courseName;
                    sharedPreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editors = sharedPreferences.edit();
                    editors.putString("BOOKING_ID", addClassDataModel1.getId());
                    editors.putString("day", addClassDataModel1.getDay());
                    editors.putString("Timing", addClassDataModel1.getTiming());
                    editors.putString("Capacity", addClassDataModel1.getCapacity());
                    editors.putString("Duration", addClassDataModel1.getDuration());
                    editors.putString("price", addClassDataModel1.getPrice());
                    editors.putString("Types_of_Class", addClassDataModel1.getTypesOfClass());
                    editors.putString("Description", addClassDataModel1.getDescription());
                    editors.commit();

                    AddClassFragment addClassFragment = new AddClassFragment();
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, addClassFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }else {
                    Toast.makeText(getContext(), response.message().toString() , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddClassModel> call, Throwable t) {
            }
        });
    }

    public void getCourseNameRequest(){
        Call<AllCourseNameModel> call = apiInterface.getCourseName();
        call.enqueue(new Callback<AllCourseNameModel>() {
            @Override
            public void onResponse(Call<AllCourseNameModel> call, Response<AllCourseNameModel> response) {
                if (response.isSuccessful()){
                    AllCourseNameModel allCourseNameModel;
                    allCourseNameModel = response.body();
                    allCourseNameDataModels = allCourseNameModel.getData();
                    typeClass();
                }else {
                    Toast.makeText(getContext(), "Try again" , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AllCourseNameModel> call, Throwable t) {
            }
        });
    }
}
