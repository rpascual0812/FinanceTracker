package com.projects.trofunlait.financetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by kith on 11/30/15.
 */
public class DBTools extends SQLiteOpenHelper {
    String transactions_tbl = "transactions";
    String _transactiontype = "transactiontype";
    String _amount = "amount";
    String _category = "category";

    String categories_tbl = "categories";

    public DBTools(Context applicationContext){
        super(applicationContext, "trofunlait_financetracker.db", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table "+transactions_tbl+" (id integer primary key autoincrement, transactiontype text, amount float, category integer)";
        db.execSQL(query);

        String sample = "insert into "+transactions_tbl+" (transactiontype, amount, category) values ('income', 100, 'Food')";
        db.execSQL(sample);

        String categories = "create table "+categories_tbl+" (id integer primary key autoincrement, transactiontype text, category text)";
        db.execSQL(categories);

        String sample_income_categories = "insert into "+categories_tbl+" (transactiontype, category) values ('income', 'Salary');";
        db.execSQL(sample_income_categories);

        String sample_expense_categories = "insert into "+categories_tbl+" (transactiontype, category) values ('expense', 'Food');";
        db.execSQL(sample_expense_categories);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "drop table if exists "+transactions_tbl;
        db.execSQL(query);
        onCreate(db);
    }

    public void insertTransactions(HashMap<String, String> queryValues){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(_transactiontype, queryValues.get(_transactiontype));
        values.put(_amount, queryValues.get(_amount));
        values.put(_category, queryValues.get(_category));

        Log.e("n", values.toString());
        db.insert(transactions_tbl, null, values);

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

        String query = "delete from "+transactions_tbl+" where id ='" + id + "'";

        db.execSQL(query);
    }

    public List<String> getCategories(String transactiontype){
        List<String> categories = new ArrayList<String>();

        // Select All Query
        String query = "SELECT id, category FROM categories where transactiontype = '" + transactiontype + "';";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                categories.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return categories;
    }

    public Cursor getAllCursorTransactions() {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT  transactiontype,amount,category FROM " + transactions_tbl;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null) {
            cursor.moveToNext();
        }
        return cursor;
    }

//    public List<Transactions> getAllGridTransactions() {
//        List<Transactions> transList = new ArrayList<Transactions>();
//        // Select All Query
//        String selectQuery = "SELECT  * FROM " + transactions_tbl;
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                Transactions transactions = new Transactions();
//                transactions.setID(Integer.parseInt(cursor.getString(0)));
//
//                String amount = cursor.getString(3) +"\n"+ cursor.getString(2);
//                MainActivity.ArrayofTransactions.add(amount);
//                // Adding contact to list
//                transList.add(transactions);
//            } while (cursor.moveToNext());
//        }
//
//        // return contact list
//        return transList;
//    }

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
        Log.e("n", query);
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
            //Log.e(">>>>>>>>>>>", cursor.getString(0));
            if(transactiontype == "income")
                map.put("income", cursor.getString(0));
            else
                map.put("expense", cursor.getString(0));
        }

        return map;
    }

    public ArrayList<Cursor> getData(String Query){
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "mesage" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);


        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);


            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {


                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;
        } catch(SQLException sqlEx){
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        } catch(Exception ex){

            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+ex.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }


    }

}