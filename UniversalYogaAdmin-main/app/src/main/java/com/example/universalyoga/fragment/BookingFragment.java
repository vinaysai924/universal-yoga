package com.example.universalyoga.fragment;

import static com.example.universalyoga.fragment.HomeFragment.SHARED_PREFS;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.universalyoga.R;
import com.example.universalyoga.activity.BookingAdapter;
import com.example.universalyoga.databinding.FragmentBookingBinding;
import com.example.universalyoga.databinding.FragmentHomeBinding;
import com.example.universalyoga.db.DBHandler;
import com.example.universalyoga.model.AddClassDataModel;
import com.example.universalyoga.model.AllCourseNameModel;
import com.example.universalyoga.model.BookingDataModel;
import com.example.universalyoga.model.BookingModel;
import com.example.universalyoga.model.UserDataModel;
import com.example.universalyoga.utils.ApiClient;
import com.example.universalyoga.utils.ApiInterface;
import com.example.universalyoga.utils.Constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingFragment extends Fragment {
    FragmentBookingBinding binding;
    DBHandler dbHandler;
    ApiInterface apiInterface;
    List<BookingDataModel> bookingDataModel;
    BookingAdapter bookingAdapter;
    SharedPreferences sharedpreferences;

    ArrayList<UserDataModel> userDataModels;
    ArrayList<String> listitem;
    public static final String MyPREFERENCES = "MyPrefs" ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = binding.inflate(inflater, container, false);
        View view = binding.getRoot();
        init();
        return view;
    }

    public void init(){
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        bookingRequest();
        listitem = new ArrayList();
        dbHandler = new DBHandler(getContext());
        Cursor cursor = dbHandler.readyDate();
        while (cursor.moveToNext()) {
            listitem.add(cursor.getString(1));
//            binding.dateTv.setText(String.valueOf(listitem.get(0)));
        }

        userDataModels = new ArrayList();
        dbHandler = new DBHandler(getContext());
        userDataModels = dbHandler.readData();
        for (int i =1; i < userDataModels.size(); i++){

            for (int d = 1; d < userDataModels.get(i).getWeek().length; d++) {
            }
            String days = Arrays.toString(userDataModels.get(i).getWeek());
            days =  days.substring( 1, days.length() - 1 );
            days = days.replace("null", "");
            days = days.replace(",", "");

        }
    }

    public void bookingRequest(){
        String userId;
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        userId = sharedpreferences.getString("USERID", null);
        Call<BookingModel> call = apiInterface.getBooking();
        call.enqueue(new Callback<BookingModel>() {
            @Override
            public void onResponse(Call<BookingModel> call, Response<BookingModel> response) {
                if (response.isSuccessful()){
                    BookingModel bookingModel;
                    bookingModel = response.body();
                    bookingDataModel = bookingModel.getData();
                    bookingAdapter = new BookingAdapter(bookingDataModel, getContext());
                    binding.rvLayout.setAdapter(bookingAdapter);

                }else {
                    Toast.makeText(getActivity(), "Try again" , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BookingModel> call, Throwable t) {
            }
        });
    }


}
