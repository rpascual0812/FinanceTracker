package com.projects.trofunlait.financetracker;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Reports extends AppCompatActivity {
    DBTools dbTools = new DBTools(this);

    TextView income;
    TextView savings;
    TextView expense;
    TextView balance;

    //Transactions
    List<String> array_list;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //main layout
        setContentView(R.layout.reports_main);

        HashMap<String, String> income_db = dbTools.getSpending("Income");
        HashMap<String, String> expense_db = dbTools.getSpending("Expense");
        HashMap<String, String> savings_db = dbTools.getSpending("Savings");

        PieChart pieChart = (PieChart) findViewById(R.id.pieChart);
        float[] datas = new float[3];
        datas[0] = Integer.parseInt(income_db.get("Income"));
        datas[1] = Integer.parseInt(savings_db.get("Savings"));
        datas[2] = Integer.parseInt(expense_db.get("Expense"));
        pieChart.setData(datas);

        String[] labels = new String[3];
        labels[0] = "INCOME";
        labels[1] = "SAVINGS";
        labels[2] = "EXPENSES";
        pieChart.setLabels(labels);
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
            setContentView(R.layout.transactions_main);

            ListView category_listview;

            array_list = dbTools.getTransactions();

            List<String> new_transactions = new ArrayList<String>();

            for(int i=0;i<array_list.size();i++){
                String[] a = array_list.get(i).split("~");

                new_transactions.add(a[1].toString());
            }

            ArrayAdapter adapter2 = new ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, new_transactions);

            category_listview = (ListView) findViewById(R.id.listview_transactions);
            category_listview.setAdapter(adapter2);

            category_listview.setOnItemClickListener(onTransactionClickListener);

        }
        else if(id == R.id.menu_categories){
            setContentView(R.layout.categories_main);

            final Spinner category;

            set_list("Income");

            String[] array_spinner = new String[] {
                    "Income",
                    "Expense",
                    "Savings"
            };

            ArrayAdapter adapter = new ArrayAdapter(this,
                    android.R.layout.select_dialog_item, array_spinner);

            category = (Spinner) findViewById(R.id.spinner_categories);
            category.setAdapter(adapter);

            final Spinner spinner_type = (Spinner) findViewById(R.id.spinner_categories);
            spinner_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {
                    String text = spinner_type.getSelectedItem().toString();
                    set_list(text);
                }

                public void onNothingSelected(AdapterView<?> arg0) {
                }

            });

            final Button button_addcategory = (Button) findViewById(R.id.btn_addcategory);
            button_addcategory.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    startActivity(new Intent(Reports.this, new_categories.class));

                }
            });

        }
        else {
            contentview("spending");
        }

        return super.onOptionsItemSelected(item);
    }

    private AdapterView.OnItemClickListener onTransactionClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
            // TODO Auto-generated method stub
            //do your job here, position is the item position in ListView

            String[] a = array_list.get((int)id).split("~");

            Intent edit_transaction = new Intent(getApplication(), edit_transaction.class);
            edit_transaction.putExtra("id", a[0].toString());
            startActivity(edit_transaction);
        }
    };

    private void set_list(String trans_type){
        final ListView category_listview;

        List<String> array_listview = dbTools.getCategories(trans_type);

        ArrayAdapter adapter2 = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, array_listview);

        category_listview = (ListView) findViewById(R.id.listview_categories);
        category_listview.setAdapter(adapter2);
    }

    public void contentview(String page){
        if(page == "transaction_new"){
            setContentView(R.layout.new_transaction);
        }
        else if(page == "reports"){
            setContentView(R.layout.reports_main);
        }
        else {
            setContentView(R.layout.spending_main);

            HashMap<String, String> income_db = dbTools.getSpending("Income");
            HashMap<String, String> expense_db = dbTools.getSpending("Expense");
            HashMap<String, String> savings_db = dbTools.getSpending("Savings");

            income = (TextView) findViewById(R.id.income_textview);
            savings = (TextView) findViewById(R.id.savings_textview);
            expense = (TextView) findViewById(R.id.expense_textview);
            balance = (TextView) findViewById(R.id.balance_textview);

            income.setText(income_db.get("Income"));
            savings.setText(savings_db.get("Savings"));
            expense.setText(expense_db.get("Expense"));

            int inc = Integer.parseInt(income_db.get("Income"));
            int sav = Integer.parseInt(savings_db.get("Savings"));
            int exp = Integer.parseInt(expense_db.get("Expense"));
            float bal = (inc + sav) - exp;

            String str_bal = Float.toString(bal);
            balance.setText(str_bal);

            //final Button button_income = (Button) findViewById(R.id.btn_income);
            final TextView button_income = (TextView) findViewById(R.id.btn_income);
            button_income.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent new_transaction = new Intent(getApplication(), new_transaction.class);
                    new_transaction.putExtra("type", "Income");
                    startActivity(new_transaction);
                }
            });

            //final Button button_expense = (Button) findViewById(R.id.btn_expense);
            final TextView button_expense = (TextView) findViewById(R.id.btn_expense);
            button_expense.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent new_transaction = new Intent(getApplication(), new_transaction.class);
                    new_transaction.putExtra("type", "Expense");
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
