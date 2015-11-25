package com.projects.trofunlait.financetracker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Not yet working Jhester ^_^", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

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
        Intent spendings = new Intent(getApplicationContext(), MainActivity.class);
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
}
