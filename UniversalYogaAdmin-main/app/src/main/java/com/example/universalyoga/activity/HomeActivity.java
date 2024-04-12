package com.example.universalyoga.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.universalyoga.R;
import com.example.universalyoga.databinding.ActivityHomeBinding;
import com.example.universalyoga.fragment.AddClassFragment;
import com.example.universalyoga.fragment.AddCourseNameFragment;
import com.example.universalyoga.fragment.BookingFragment;
import com.example.universalyoga.fragment.CourseFragment;
import com.example.universalyoga.fragment.HomeFragment;
import com.example.universalyoga.fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    ActivityHomeBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        init();
    }

    public void init(){
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
        Fragment selectedFragment = null;
        int itemId = item.getItemId();
        if (itemId == R.id.home) {
            selectedFragment = new HomeFragment();
        } else if (itemId == R.id.booking) {
            selectedFragment = new BookingFragment();
        } else if (itemId == R.id.addCourse) {
            selectedFragment = new AddCourseNameFragment();
        }
//        else if (itemId == R.id.profile) {
//            selectedFragment = new ProfileFragment();
//        }
//        else if (itemId == R.id.course) {
//            selectedFragment = new CourseFragment();
//        }

        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
        }
        return true;
    };
}
