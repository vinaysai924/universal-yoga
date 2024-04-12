package com.example.universalyoga.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.universalyoga.model.UserDataModel;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {
    private SQLiteDatabase database;
    private static final String DB_NAME = "UserData";
    private static final String TABLE_NAME = "UserData";
    private static final String TABLE_NAME_TYPE_CLASS = "UserDataTypeClass";
    public static final String DATABASE_NAME = "MyDBName";
    public static final String CONTACTS_TABLE_NAME = "selectDetails";
    private static final String TABLE_NAME_DATE_CLASS = "UserDataDateClass";

    private static final String ID_COL = "id";
    private static final String TYPE_OF_CLASS = "typeOfClass" ;
    private static final String DATE = "date";
    private static final String PRICE = "price";
    private static final String DAY = "day";
    private static final String SCORE_COL = "score";
    private static final String DATE_COL = "date";
    private static final String TARGET_WEIGHT_COL = "targetweight";
    private static final String TARGET_DATE_COL = "targetdate";
    private static  String WEIGHT_IMAGE = "weightImage" ;


    public DBHandler(Context context) {
        super(context,DB_NAME,null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table UserDetails(userID TEXT primary key,name TEXT,password PASSWORD,number NUMBER)");

//        String query = "CREATE TABLE " + TABLE_NAME + " (" + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TYPE_OF_CLASS + " TEXT)";

//        DB.execSQL(query);

        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TYPE_OF_CLASS + " TEXT,"
                + PRICE + " TEXT,"
                + DAY +" TEXT)";

        DB.execSQL(query);


        String queryDate = "CREATE TABLE " + TABLE_NAME_TYPE_CLASS + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DATE +" TEXT)";
        DB.execSQL(queryDate);

    }
    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists UserDetails");
    }
    public Boolean insetUserData(String email,String password){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userID",email);
        contentValues.put("password",password);

        long result= DB.insert("UserDetails",null,contentValues);
        if (result == -1){
            return false;
        }else {
            return true;
        }
    }

    public void insetTypeClassData(String typeofclass, String price, String[] day) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValue = new ContentValues();
        contentValue.put(TYPE_OF_CLASS, typeofclass);
        contentValue.put(PRICE, price);
        contentValue.put(DAY, convertArrayToString(day));
        db.insert(TABLE_NAME, null, contentValue);
        db.close();
    }
    private String convertArrayToString(String[] stringArray) {
        StringBuilder stringBuilder = new StringBuilder();

        for (String s : stringArray) {
            stringBuilder.append(s).append(",");
        }

        // Remove the trailing comma
        if (stringBuilder.length() > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }

        return stringBuilder.toString();
    }

    public void insetDate(String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValue = new ContentValues();
        contentValue.put(DATE, date);
        long result = db.insert(TABLE_NAME_TYPE_CLASS, null, contentValue);

    }

    public Cursor readyDate() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_TYPE_CLASS, null);
        return cursor;
    }

//    public Cursor readyTypeClass() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_TYPE_CLASS, null);
//        return cursor;
//    }

    @SuppressLint("Range")
    public ArrayList<UserDataModel> readData() {
        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();

        // on below line we are creating a cursor with query to read data from database.
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        // on below line we are creating a new array list.
        ArrayList<UserDataModel> courseModalArrayList = new ArrayList<>();

        // moving our cursor to first position.
        if (cursor.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                courseModalArrayList.add(new UserDataModel(cursor.getString(1),
                        new String[]{cursor.getString(3)},
                        cursor.getString(2)));

            } while (cursor.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursor.close();
        return courseModalArrayList;
    }

    public Cursor getDataLogin(){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Userdetails ",null);
        return cursor;
    }

    public void deleteById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID_COL + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }

    public int deleteItem(int id){
        SQLiteDatabase database = getWritableDatabase();
        return database.delete(TABLE_NAME, "id=?", new String[]{String.valueOf(id)});
    }
}