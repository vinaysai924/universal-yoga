package com.example.universalyoga.activity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.universalyoga.R;
import com.example.universalyoga.model.BookingDataModel;

import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.MyViewHolder> {
    List<BookingDataModel> bookingDataModelList;
    Context context;

    public BookingAdapter(List<BookingDataModel> bookingDataModel, Context context) {
        this.bookingDataModelList = bookingDataModel;
        this.context = context;
        notifyDataSetChanged();
        Log.e("BookingAdaptexx", bookingDataModel + "cd");
    }

    @NonNull
    @Override
    public BookingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingAdapter.MyViewHolder holder, int position) {
        BookingDataModel bookingDataModel = bookingDataModelList.get(position);
        if (bookingDataModelList.isEmpty()){
            holder.nodatatxt.setVisibility(View.VISIBLE);
        }
        if (bookingDataModel.getUserName() != null){
            holder.nameTv.setText(bookingDataModel.getUserName());
        }
        if (bookingDataModel.getDate() != null){
            holder.dateTv.setText(bookingDataModel.getDate());

        }if (bookingDataModel.getPrice() != null){
            holder.priceClassTv.setText(bookingDataModel.getDay());

        }if (bookingDataModel.getTypesOfClass() != null){
            holder.typeClassTv.setText(bookingDataModel.getTypesOfClass());

        }if (bookingDataModel.getTypesOfClass() != null){
            holder.dayTv.setText(bookingDataModel.getDay());
        }
    }

    @Override
    public int getItemCount() {
        return bookingDataModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTv, dateTv, dayTv, typeClassTv, priceClassTv,nodatatxt;
        public MyViewHolder(View view) {
            super(view);
            nameTv = view.findViewById(R.id.userNameTv);
            nodatatxt = view.findViewById(R.id.nodatatxt);
            dateTv = view.findViewById(R.id.userDateTv);
            dayTv = view.findViewById(R.id.userDayTv);
            typeClassTv = view.findViewById(R.id.userTypeClassTv);
            priceClassTv = view.findViewById(R.id.userPriceClassTv);
        }
    }
}
