package com.projects.trofunlait.financetracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

/**
 * Created by kith on 1/6/16.
 */
public class new_categories extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.new_categories);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        width = (int) (width * 0.8);
        height = (int) (height * 0.6);

        getWindow().setLayout(width, height);

        final Spinner trans_type;
        String[] array_spinner = new String[] {
                "Income",
                "Expense"
        };

        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.select_dialog_item, array_spinner);

        trans_type = (Spinner) findViewById(R.id.spType);

        trans_type.setAdapter(adapter);

        final Button button_addcategory = (Button) findViewById(R.id.btnCancel);
        button_addcategory.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new_categories.this.finish();
            }
        });

    }
}
