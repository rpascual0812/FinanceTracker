package com.projects.trofunlait.financetracker;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import java.util.List;

/**
 * Created by kith on 12/5/15.
 */
public class Categories extends Activity {
    DBTools dbTools = new DBTools(this);
    private String array_spinner[];
    @Override
    public void onCreate(Bundle savedInstanceState) {
        final EditText amount;
        final Spinner category;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories_main);

        final String trans_type = getIntent().getExtras().getString("type");

        set_list(trans_type);

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
            public void onNothingSelected(AdapterView<?> arg0) { }

        });
    }

    public void set_list(String trans_type){
        final ListView category_listview;

        List<String> array_listview = dbTools.getCategories(trans_type);

        ArrayAdapter adapter2 = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, array_listview);

        category_listview = (ListView) findViewById(R.id.listview_categories);
        category_listview.setAdapter(adapter2);
    }
}
