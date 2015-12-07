package com.projects.trofunlait.financetracker;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.HashMap;

/**
 * Created by wennie on 12/5/15.
 */
public class new_transaction extends Activity {
    DBTools dbTools = new DBTools(this);
    private String array_spinner[];
    @Override
    public void onCreate(Bundle savedInstanceState) {
        final EditText amount;
        final Spinner category;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_transaction);
        array_spinner=new String[8];
        array_spinner[0]="Eating Out";
        array_spinner[1]="Shopping";
        array_spinner[2]="Clothes";
        array_spinner[3]="Entertainment";
        array_spinner[4]="Fuel";
        array_spinner[5]="General";
        array_spinner[6]="Gifts";
        array_spinner[7]="Holidays";

        amount = (EditText) findViewById(R.id.txt_amount);
        category = (Spinner) findViewById(R.id.spinner_categories);
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, array_spinner);

        category.setAdapter(adapter);



        final Button button_income = (Button) findViewById(R.id.btn_savetransaction);
        button_income.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                HashMap<String, String> queryValuesMap =  new  HashMap<String, String>();
                queryValuesMap.put("transactiontype", "income");
                queryValuesMap.put("amount", amount.getText().toString());
                queryValuesMap.put("category", category.getSelectedItem().toString());

                dbTools.insertTransactions(queryValuesMap);

                EditText amount = (EditText)findViewById(R.id.txt_amount);
                Toast toast;
                String cat = category.getSelectedItem().toString();
                toast = Toast.makeText(getApplicationContext(), "New income amounting to " + amount.getText() + " under the category " + cat + " has been saved. ", Toast.LENGTH_SHORT);
                toast.show();

                amount.setText("");
                //category.setSelection(0);


            }
        });
    }


}
