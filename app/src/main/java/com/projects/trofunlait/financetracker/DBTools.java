package com.projects.trofunlait.financetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kith on 11/30/15.
 */
public class DBTools extends SQLiteOpenHelper {

    public DBTools(Context applicationContext){
        super(applicationContext, "trofunlait_financetracker.db", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table transactions (id integer primary key autoincrement, transactiontype text, amount float, category integer)";
        db.execSQL(query);

        String sample = "insert into transactions (transactiontype, amount, category) values ('income', 100, 'Food')";
        db.execSQL(sample);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "drop table if exists transactions";
        db.execSQL(query);
        onCreate(db);
    }

    public void insertTransactions(HashMap<String, String> queryValues){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("transactiontype", queryValues.get("transcationtype"));
        values.put("amount", queryValues.get("amount"));
        values.put("category", queryValues.get("category"));

        db.insert("transactions", null, values);

        db.close();
    }

    public int updateTransactions(HashMap<String, String> queryValues){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("transactiontype", queryValues.get("transcationtype"));
        values.put("amount", queryValues.get("amount"));
        values.put("category", queryValues.get("category"));

        return db.update(
                "transactions",
                values,
                "id" + " = ?",
                new String[] {queryValues.get("id")}
        );
    }

    public void deleteTransactions(String id){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "delete from transactions where id ='" + id + "'";

        db.execSQL(query);
    }

    public ArrayList<HashMap<String, String>> getAllTransactions(){
        ArrayList<HashMap<String, String>> transactionsList = new ArrayList<HashMap<String, String>>();

        String query = "Select * from transactions order by id";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do {
                HashMap<String, String> map = new HashMap<String, String>();

                map.put("id", cursor.getString(0));
                map.put("trasactiontype", cursor.getString(1));
                map.put("amount", cursor.getString(2));
                map.put("category", cursor.getString(3));

                transactionsList.add(map);
            } while (cursor.moveToNext());
        }

        return transactionsList;
    }

    public HashMap<String, String> getTransaction(String id){
        HashMap<String, String> map = new HashMap<String, String>();

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "Select * from transactions where id = '" + id + "'";

        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do {
                map.put("id", cursor.getString(0));
                map.put("trasactiontype", cursor.getString(1));
                map.put("amount", cursor.getString(2));
                map.put("category", cursor.getString(3));

            } while (cursor.moveToNext());
        }

        return map;
    }

    public HashMap<String, String> getSpending(String transactiontype){
        HashMap<String, String> map = new HashMap<String, String>();

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "Select sum(amount) from transactions where transactiontype = '"+ transactiontype +"'";

        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do {
                map.put(transactiontype, cursor.getString(0));

            } while (cursor.moveToNext());
        }

        return map;
    }

}