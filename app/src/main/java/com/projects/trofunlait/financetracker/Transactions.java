package com.projects.trofunlait.financetracker;

/**
 * Created by kith on 12/16/15.
 */
public class Transactions {

    //private variables
    int _id;
    String _transactiontype;
    String _amount;
    String _category;

    // Empty constructor
    public Transactions(){

    }
    // constructor
    public Transactions(int id, String transactiontype, String amount, String category){
        this._id = id;
        this._transactiontype = transactiontype;
        this._amount = amount;
        this._category = category;
    }

    // constructor
    public Transactions(String transactiontype, String amount, String category){
        this._transactiontype = transactiontype;
        this._amount = amount;
        this._category = category;
    }

    // getting ID
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }
}
