package com.projects.trofunlait.financetracker;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.content.Intent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
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
        contentview("spending");

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                contentview("transaction_new");
////                Snackbar.make(view, "Not yet working Jhester ^_^", Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
//            }
//        });

//        income = (TextView) findViewById(R.id.income_textview);
//        expense = (TextView) findViewById(R.id.expense_textview);
//        balance = (TextView) findViewById(R.id.balance_textview);
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
//        Intent spendings = new Intent(getApplicationContext(), Spending.class);
//        Intent transactions = new Intent(getApplicationContext(), Transactions.class);
//        Intent categories = new Intent(getApplicationContext(), Categories.class);
//        Intent accounts = new Intent(getApplicationContext(), Accounts.class);

        if(id == R.id.menu_transactions){
            //contentview("transactions");
            setContentView(R.layout.transactions_main);

            final ListView category_listview;

            List<String> array_list = dbTools.getTransactions();

            ArrayAdapter adapter2 = new ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, array_list);

            category_listview = (ListView) findViewById(R.id.listview_transactions);
            category_listview.setAdapter(adapter2);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
        }
        else if(id == R.id.menu_categories){
            setContentView(R.layout.categories_main);

            final Spinner category;

            set_list("income");

            String[] array_spinner = new String[] {
                    "Income",
                    "Expense"
            };

            ArrayAdapter adapter = new ArrayAdapter(this,
                    android.R.layout.select_dialog_item, array_spinner);

            category = (Spinner) findViewById(R.id.spinner_categories);
            category.setAdapter(adapter);

            final Spinner spinner_type = (Spinner) findViewById(R.id.spinner_categories);
            spinner_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {
                    String text = spinner_type.getSelectedItem().toString();
                    set_list(text.toLowerCase());
                }

                public void onNothingSelected(AdapterView<?> arg0) {
                }

            });

            final Button button_addcategory = (Button) findViewById(R.id.btn_addcategory);
            button_addcategory.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, new_categories.class));

                }
            });

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

        } else if(id == R.id.menu_accounts){
            contentview("accounts");
            //startActivity(accounts);
        }
        else {
            contentview("spending");


            //startActivity(spendings);
        }

//        TextView title = (TextView)findViewById(R.id.page_title);
//        title.setText(item.toString());

//        Toast toast = Toast.makeText(this, item.toString(), Toast.LENGTH_LONG);
//        toast.show();

        return super.onOptionsItemSelected(item);
    }

    private void set_list(String trans_type){
        final ListView category_listview;

        List<String> array_listview = dbTools.getCategories(trans_type);

        ArrayAdapter adapter2 = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, array_listview);

        category_listview = (ListView) findViewById(R.id.listview_categories);
        category_listview.setAdapter(adapter2);
    }

    public String get_amount(){
        return amount;
    }

    public void contentview(String page){
        if(page == "transactions"){
            setContentView(R.layout.transactions_main);

            final ListView category_listview;

            List<String> array_listview = dbTools.getTransactions();

            ArrayAdapter adapter2 = new ArrayAdapter(this,
                    android.R.layout.simple_list_item_2, array_listview);

            category_listview = (ListView) findViewById(R.id.listview_transactions);
            category_listview.setAdapter(adapter2);
        }
        else if(page == "transaction_new"){
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

            int inc = Integer.parseInt(income_db.get("income"));
            int exp = Integer.parseInt(expense_db.get("expense"));
            float bal = inc - exp;

            String str_bal = Float.toString(bal);
            balance.setText(str_bal);

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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

}
