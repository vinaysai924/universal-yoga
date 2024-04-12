package com.example.universalyoga.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.universalyoga.R;
import com.example.universalyoga.databinding.FragmentAddCourseNameBinding;
import com.example.universalyoga.model.AddClassDataModel;
import com.example.universalyoga.model.CourseNameAddDataModel;
import com.example.universalyoga.utils.ApiClient;
import com.example.universalyoga.utils.ApiInterface;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCourseNameFragment extends Fragment {
    View view;
    ApiInterface apiInterface;
    String courseStr;
    FragmentAddCourseNameBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = binding.inflate(inflater, container, false);
        view = binding.getRoot();
        init();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    public void init(){
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        binding.submitClickTv.setOnClickListener(view -> {
            courseStr = binding.courseEt.getText().toString();
            if (courseStr.isEmpty()){
                Toast.makeText(getContext(), "Please Add Course Name", Toast.LENGTH_SHORT).show();
            }else {
                addCourseNameRequest();
            }
        });
    }

    public void addCourseNameRequest(){
        CourseNameAddDataModel courseNameAddDataModel = new CourseNameAddDataModel();
        courseNameAddDataModel.setCourseName(courseStr);

        Call<CourseNameAddDataModel> call = apiInterface.addCourseName(courseNameAddDataModel);
        call.enqueue(new Callback<CourseNameAddDataModel>() {
            @Override
            public void onResponse(Call<CourseNameAddDataModel> call, Response<CourseNameAddDataModel> response) {
                if (response.isSuccessful()){
                    HomeFragment homeFragment = new HomeFragment();
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, homeFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    Toast.makeText(getActivity(), "Course Added Successfully", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "Try again" , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CourseNameAddDataModel> call, Throwable t) {
            }
        });
    }
}
