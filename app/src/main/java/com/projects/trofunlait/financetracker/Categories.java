package com.projects.trofunlait.financetracker;

import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

/**
 * Created by kith on 12/27/15.
 */
public class Categories extends Activity {
    DBTools dbTools = new DBTools(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        final EditText amount;
        final Spinner category;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories_main);
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

            //startActivity(transactions);
        }
        else if(id == R.id.menu_categories){
            //contentview("categories");
            Intent intent = new Intent(getApplication(), Categories.class);
            intent.putExtra("type", "income");
            startActivity(intent);
        } else if(id == R.id.menu_accounts){

            //startActivity(accounts);
        }
        else {



            //startActivity(spendings);
        }

//        TextView title = (TextView)findViewById(R.id.page_title);
//        title.setText(item.toString());

//        Toast toast = Toast.makeText(this, item.toString(), Toast.LENGTH_LONG);
//        toast.show();

        return super.onOptionsItemSelected(item);
    }
}
