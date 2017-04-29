package com.example.nick.growerland.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.nick.growerland.R;
import com.example.nick.growerland.constant.ListConstants;

public class AddGardenActivity extends AppCompatActivity {

    TextView title;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_garden);

        title = (TextView) findViewById(R.id.title);

        String gardenName;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                gardenName= null;
            } else {
                gardenName= extras.getString(ListConstants.Extras.EXTRA_GARDEN_NAME_CREATE);
            }
        } else {
            gardenName= (String) savedInstanceState.getSerializable(ListConstants.Extras.EXTRA_GARDEN_NAME_CREATE);
        }

       title.setText(gardenName);
    }
}
