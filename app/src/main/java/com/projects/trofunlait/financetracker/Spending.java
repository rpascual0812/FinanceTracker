package com.projects.trofunlait.financetracker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;

public class Spending extends AppCompatActivity {
    DBHandler dbHandler;

    private int _id;
    private String _amount;

    TextView income;
    TextView expense;
    TextView balance;

    EditText test_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        spendingtracker = new DBHandler(this);
//
//        Cursor income = spendingtracker.countIncome();
//        //Cursor expense = spendingtracker.countExpense();
//
//        String total_spending = income.getString(income.getColumnIndex(DBHandler.TRANSACTIONS_TOTAL_INCOME));
//
//        TextView income_textview = (TextView)findViewById(R.id.income_textview);
//        income_textview.setText(total_spending);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.spending_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Snackbar.make(view, "Not yet working Jhester ^_^", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        income = (TextView) findViewById(R.id.income_textview);
        expense = (TextView) findViewById(R.id.expense_textview);
        balance = (TextView) findViewById(R.id.balance_textview);

        dbHandler = new DBHandler(this, null, null, 1);
        printDatabase();
    }

    public void printDatabase(){
        String dbString = dbHandler.databasetostring();
        income.setText(dbString);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        //Log.e("n", item.toString());
        Intent spendings = new Intent(getApplicationContext(), Spending.class);
        Intent transactions = new Intent(getApplicationContext(), Transactions.class);
        Intent categories = new Intent(getApplicationContext(), Categories.class);
        Intent accounts = new Intent(getApplicationContext(), Accounts.class);

        if(id == R.id.menu_transactions){
            startActivity(transactions);
        }
        else if(id == R.id.menu_categories){
            startActivity(categories);
        }
        else if(id == R.id.menu_accounts){
            startActivity(accounts);
        }
        else {
            startActivity(spendings);
        }

//        TextView title = (TextView)findViewById(R.id.page_title);
//        title.setText(item.toString());

//        Toast toast = Toast.makeText(this, item.toString(), Toast.LENGTH_LONG);
//        toast.show();

        return super.onOptionsItemSelected(item);
    }

    public String get_amount(){
        return _amount;
    }
}
