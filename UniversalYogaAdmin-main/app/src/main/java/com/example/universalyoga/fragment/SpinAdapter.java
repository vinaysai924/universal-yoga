package com.example.universalyoga.fragment;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.universalyoga.model.AllCourseNameDataModel;

import java.util.List;

public class SpinAdapter extends ArrayAdapter<AllCourseNameDataModel> {
    private Context context;
    private List<AllCourseNameDataModel> values;

    public SpinAdapter(Context context, int simple_spinner_item, List<AllCourseNameDataModel> allCourseNameDataModels) {
        super(context, simple_spinner_item,allCourseNameDataModels);
        this.context = context;
        this.values = allCourseNameDataModels;
        notifyDataSetChanged();
    }


    @Override
    public int getCount(){
        return values.size();
    }

    @Override
    public AllCourseNameDataModel getItem(int position){
        return values.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
        label.setText(values.get(position).getCourseName());

        // And finally return your dynamic (or custom) view for each spinner item
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setTextColor(Color.WHITE);
        label.setText(values.get(position).getCourseName());

        return label;
    }
}
