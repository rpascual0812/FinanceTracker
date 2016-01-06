package com.projects.trofunlait.financetracker;

import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

/**
 * Created by kith on 12/5/15.
 */
public class new_transaction extends Activity {
    DBTools dbTools = new DBTools(this);
    private String array_spinner[];
    @Override
    public void onCreate(Bundle savedInstanceState) {
        final EditText amount;
        final Spinner category;
        final DatePicker datePicker;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_transaction);

        final String trans_type = getIntent().getExtras().getString("type");

        List<String> array_spinner = dbTools.getCategories(trans_type);

        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.select_dialog_item, array_spinner);

        category = (Spinner) findViewById(R.id.spinner_categories);
        category.setAdapter(adapter);

        amount = (EditText) findViewById(R.id.txt_amount);
        datePicker = (DatePicker) findViewById(R.id.datePicker);

        final Button button = (Button) findViewById(R.id.btn_savetransaction);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                HashMap<String, String> queryValuesMap =  new  HashMap<String, String>();
                queryValuesMap.put("transactiontype", trans_type);
                queryValuesMap.put("amount", amount.getText().toString());
                queryValuesMap.put("category", category.getSelectedItem().toString());

                int y = datePicker.getYear();
                int m = datePicker.getMonth() + 1;
                String m2 = String.format("%2s", Integer.toString(m)).replace(' ', '0');

                int d = datePicker.getDayOfMonth();
                String d2 = String.format("%2s", Integer.toString(d)).replace(' ', '0');

                queryValuesMap.put("datecreated", Integer.toString(y) + "-" + m2 + "-" + d2);

                dbTools.insertTransactions(queryValuesMap);

                EditText amount = (EditText)findViewById(R.id.txt_amount);
                Toast toast;
                String cat = category.getSelectedItem().toString();

                toast = Toast.makeText(getApplicationContext(), "New "+trans_type+" amounting to " + amount.getText() + " under the category " + cat + " has been saved. ", Toast.LENGTH_SHORT);
                toast.show();

                Intent main = new Intent(getApplication(), MainActivity.class);
                startActivity(main);
            }
        });

        final Button button_cancel = (Button) findViewById(R.id.btn_cancel);
        button_cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new_transaction.this.finish();
            }
        });
    }
}
