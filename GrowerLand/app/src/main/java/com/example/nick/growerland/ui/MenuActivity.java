package com.example.nick.growerland.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nick.growerland.NetworkManager;
import com.example.nick.growerland.R;
import com.example.nick.growerland.async.OnResultCallback;
import com.example.nick.growerland.async.OwnAsyncTask;
import com.example.nick.growerland.async.task.JsonReader;
import com.example.nick.growerland.constant.ListConstants;
import com.example.nick.growerland.lovelydialog.AlertCustomDialog;

import static com.example.nick.growerland.constant.ListConstants.CONFIG;
import static com.example.nick.growerland.constant.ListConstants.URL_JSON_SETTINGS;

public class MenuActivity extends AppCompatActivity {

    public void onAddClicked(final View view) {

        final AlertCustomDialog dialog = new AlertCustomDialog(this);
        dialog.setView(R.layout.custom)
                .setTopColorRes(R.color.brand_color)
                .setCancelable(false)
                .setIcon(R.drawable.leaf)
                .show();

        final View viewDialog = dialog.getAddedView();
        final TextView cancel = (TextView) viewDialog.findViewById(R.id.ld_btn_negative);
        final TextView ok = (TextView) viewDialog.findViewById(R.id.ld_btn_confirm);
        final EditText name = (EditText) viewDialog.findViewById(R.id.name);

        ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                final String nameStr = name.getText().toString();
                if(!nameStr.isEmpty()) {
                    final Intent intent = new Intent(MenuActivity.this, AddGardenActivity.class);
                    intent.putExtra(ListConstants.Extras.EXTRA_GARDEN_NAME_CREATE, nameStr);
                    startActivity(intent);
                    dialog.dismiss();
                } else {
                    Toast.makeText(MenuActivity.this, "Please input your garden name", Toast.LENGTH_LONG).show();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                dialog.dismiss();
            }
        });
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        takeJson();

    }

    public void takeJson() {
        OwnAsyncTask sync = new OwnAsyncTask();

        try {
            if (NetworkManager.isConnected(this)) {
                sync.execute(new JsonReader(), URL_JSON_SETTINGS + CONFIG, new OnResultCallback<String, Void>() {

                    @Override
                    public void onSuccess(final String pS) {
                        Log.d("thecriserJSON", pS);
                    }

                    @Override
                    public void onError(final Exception pE) {
                        Toast.makeText(MenuActivity.this, "cannot parse", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onProgressChanged(final Void pVoid) {

                    }
                });
            } else {
                Toast.makeText(this, "No internet", Toast.LENGTH_SHORT).show();
            }
        } catch (final Exception e) {
            Toast.makeText(this, "No internet", Toast.LENGTH_SHORT).show();
        }
    }
}