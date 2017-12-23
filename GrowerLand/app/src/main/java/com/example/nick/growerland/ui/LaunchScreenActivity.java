package com.example.nick.growerland.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nick.growerland.R;

public class LaunchScreenActivity extends AppCompatActivity {

    public void onAddClicked(final View view) {
//        startActivity(new Intent(this, AddGardenActivity.class));

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom);
        dialog.setTitle("Title...");

//        TextView text = (TextView) dialog.findViewById(R.id.text);

        dialog.show();
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

    }
}
