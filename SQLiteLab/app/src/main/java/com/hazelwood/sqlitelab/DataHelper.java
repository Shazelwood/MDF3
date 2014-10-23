package com.hazelwood.sqlitelab;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Hazelwood on 10/21/14.
 */
public class DataHelper extends SQLiteOpenHelper {
    public static final String DATABASE_FILE = "data.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE = "employees";
    public static final String COLUMN_ID = "_id";
    public static final String FIRST_NAME = "firstname";
    public static final String LAST_NAME = "lastname";
    public static final String DATE = "date";
    public static final String PAY_RATE = "pay";
    public static final String IMAGE_PATH = "image";

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
    + TABLE + "("+ COLUMN_ID + "INTEGER PRIMARY KEY AUTOINCREMENT,"
    + FIRST_NAME +" TEXT,"
    + LAST_NAME+ " TEXT,"
    + DATE + " DATETIME,"
    + PAY_RATE + " REAL,"
    + IMAGE_PATH + " TEXT)";


    public DataHelper(Context context) {
        super(context, DATABASE_FILE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }

    public void save(String fname, String lname/*, int id*/, String date, float pay, String path){
        SQLiteDatabase dataBase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
//        contentValues.put(COLUMN_ID, id);
        contentValues.put(FIRST_NAME, fname);
        contentValues.put(LAST_NAME, lname);
        contentValues.put(DATE, date);
        contentValues.put(PAY_RATE, pay);
        contentValues.put(IMAGE_PATH, path);

        dataBase.insert(TABLE, null, contentValues);
    }

    public void loadTable(){
        SQLiteDatabase dataBase = getReadableDatabase();

        Cursor cursor = dataBase.query(TABLE, null, null, null, null, null, null);

    }

    public void loadSection(){

    }



}
