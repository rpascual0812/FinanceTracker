package com.projects.trofunlait.financetracker;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.content.Intent;

import java.util.HashMap;

public class Categories extends AppCompatActivity {
    DBTools dbTools = new DBTools(this);

    ListActivity listActivity = new ListActivity();

    private int id;
    private String amount;
    private String transactiontype;
    private String category;

    TextView income;
    TextView expense;
    TextView balance;

    EditText test_text;

    private SimpleCursorAdapter dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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

        if(id == R.id.menu_transactions){
            contentview("transactions");
            //startActivity(transactions);
        }
        else if(id == R.id.menu_categories){
            //contentview("categories");
            Intent intent = new Intent(getApplication(), Categories.class);
            intent.putExtra("type", "income");
            startActivity(intent);
        } else if(id == R.id.menu_accounts){
            contentview("accounts");
            //startActivity(accounts);
        }
        else {
            contentview("spending");
        }

        return super.onOptionsItemSelected(item);
    }

    public void contentview(String page){
        if(page == "transactions"){
            setContentView(R.layout.transactions_main);

            Cursor cursor = (Cursor) dbTools.getAllCursorTransactions();

            String[] columns = new String[] {
                    dbTools._transactiontype,
                    dbTools._amount,
                    dbTools._category
            };

            dataAdapter = new SimpleCursorAdapter(
                    this, R.layout.transactions_list,
                    cursor,
                    columns,
                    null,
                    0);

            ListView listView = (ListView) findViewById(R.id.listView1);

            listView.setAdapter(dataAdapter);

        }
        else if(page == "categories"){
            setContentView(R.layout.new_transaction);
        }
        else if(page == "accounts"){
            setContentView(R.layout.accounts_main);
        }
        else {
            setContentView(R.layout.spending_main);

            HashMap<String, String> income_db = dbTools.getSpending("income");
            HashMap<String, String> expense_db = dbTools.getSpending("expense");

            income = (TextView) findViewById(R.id.income_textview);
            expense = (TextView) findViewById(R.id.expense_textview);
            balance = (TextView) findViewById(R.id.balance_textview);

            income.setText(income_db.get("income"));
            expense.setText(expense_db.get("expense"));

            balance.setText("0");

            final Button button_income = (Button) findViewById(R.id.btn_income);
            button_income.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent new_transaction = new Intent(getApplication(), new_transaction.class);
                    new_transaction.putExtra("type", "income");
                    startActivity(new_transaction);
                }
            });

            final Button button_expense = (Button) findViewById(R.id.btn_expense);
            button_expense.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent new_transaction = new Intent(getApplication(), new_transaction.class);
                    new_transaction.putExtra("type", "expense");
                    startActivity(new_transaction);
                }
            });

            TextView tv =(TextView)findViewById(R.id.dbcheck);

            tv.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    Intent dbmanager = new Intent(getApplication(),AndroidDatabaseManager.class);
                    startActivity(dbmanager);
                }
            });
        }


    }

}
