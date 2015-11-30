package com.projects.trofunlait.financetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by kith on 11/30/15.
 */
public class DBHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "trofunlait_financetracker.db";
    private static final Integer DATABASE_VERSION = 1;
    public static final String TABLE_TRANSACTIONS = "transactions";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_AMOUNT = "amount";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table " + TABLE_TRANSACTIONS + " ( " +
                COLUMN_ID + " integer primary key autoincrement," +
                COLUMN_AMOUNT + " float" +
                ")";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTIONS);
        onCreate(db);
    }

    public void addTransaction(Spending spending){
        ContentValues values = new ContentValues();
        values.put(COLUMN_AMOUNT, spending.get_amount());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_TRANSACTIONS, null, values);
        db.close();
    }

    public String databasetostring(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "select * from " + TABLE_TRANSACTIONS + " where 1";

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex("amount")) != null){
                dbString += c.getString(c.getColumnIndex("amount"));
                dbString += "\n";
            }
        }
        db.close();
        return dbString;
    }


    //    public DBHandler(Context context) {
//        super(context, DATABASE_NAME , null, 1);
//    }
//
//    public Cursor countIncome() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res =  db.rawQuery( "select sum(amount) as total_income from transactions where transaction_type = 'income'", null );
//        return res;
//    }
//
//    public Cursor countExpense() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res =  db.rawQuery( "select sum(amount) as total_expense from transactions where transaction_type = 'expense'", null );
//        return res;
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        // TODO Auto-generated method stub
//        db.execSQL(
//                "create table transactions " +
//                        "(id integer primary key, user integer, amount numeric, transaction_type text, transaction_date date, category integer, note text);"
//        );
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS transactions");
//        onCreate(db);
//
//        db.execSQL("DROP TABLE IF EXISTS categories");
//        onCreate(db);
//    }

}