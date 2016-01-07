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
    String _id = "id";
    String _transactiontype = "transactiontype";
    String _amount = "amount";
    String _category = "category";
    String _datecreated = "datecreated";

    String categories_tbl = "categories";

    private ArrayList<String> categories_results = new ArrayList<String>();

    public DBTools(Context applicationContext){
        super(applicationContext, "trofunlait_financetracker.db", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table "+transactions_tbl+" (id integer primary key autoincrement, transactiontype text, amount float, category integer, datecreated date)";
        db.execSQL(query);

        String sample = "insert into "+transactions_tbl+" (transactiontype, amount, category, datecreated) values ('Income', 100, 'Food', date('now'))";
        db.execSQL(sample);

        String categories = "create table "+categories_tbl+" (id integer primary key autoincrement, transactiontype text, category text)";
        db.execSQL(categories);

        String sample_categories = "insert into "+categories_tbl+" (transactiontype, category) values ('Income', 'Salary'), ('Income', 'Part-time Job'), ('Expense', 'Food'), ('Expense', 'Transportation');";
        db.execSQL(sample_categories);
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
        values.put(_datecreated, queryValues.get(_datecreated));

        db.insert(transactions_tbl, null, values);

        db.close();
    }

    public int updateTransaction(HashMap<String, String> queryValues){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        //values.put(_id, queryValues.get(_id));
        values.put(_amount, queryValues.get(_amount));
        values.put(_category, queryValues.get(_category));
        values.put(_datecreated, queryValues.get(_datecreated));

        return db.update(
                "transactions",
                values,
                "id" + " = ?",
                new String[] {queryValues.get("id")}
        );
    }

    public void insertCategory(HashMap<String, String> queryValues){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(_transactiontype, queryValues.get(_transactiontype));
        values.put(_category, queryValues.get(_category));

        db.insert(categories_tbl, null, values);

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

    public List<String> getTransactions(){
        List<String> categories = new ArrayList<String>();

        // Select All Query
        String query = "SELECT id || '~' || category ||  ' - ' || amount as category FROM transactions order by datecreated desc;";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                categories.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return categories;
    }

    public List<String> getEditTransaction(String id){
        List<String> transactions = new ArrayList<String>();

        // Select All Query
        String query = "SELECT id || '~' || category ||  '~' || amount || '~' || datecreated || '~' || transactiontype FROM transactions where id = "+ id +" order by datecreated desc;";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                transactions.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return transactions;
    }

    public List<String> getAllCategories(){
        List<String> categories = new ArrayList<String>();

        // Select All Query
        String query = "SELECT id, category FROM categories;";

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

        String query = "Select coalesce(sum(amount),0) from transactions where transactiontype = '"+ transactiontype +"'";

        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            //Log.e(">>>>>>>>>>>", cursor.getString(0));
            if(transactiontype == "Income")
                map.put("Income", cursor.getString(0));
            else if(transactiontype == "Savings")
                map.put("Savings", cursor.getString(0));
            else
                map.put("Expense", cursor.getString(0));
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