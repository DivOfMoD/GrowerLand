package com.example.nick.growerland.ui;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nick.growerland.CardCursorAdapter;
import com.example.nick.growerland.CardsCursorLoader;
import com.example.nick.growerland.R;
import com.example.nick.growerland.async.OnResultCallback;
import com.example.nick.growerland.async.OwnAsyncTask;
import com.example.nick.growerland.async.task.JsonReader;
import com.example.nick.growerland.constant.ListConstants;
import com.example.nick.growerland.database.DatabaseHelper;
import com.example.nick.growerland.database.models.GardenModel;
import com.example.nick.growerland.lovelydialog.AlertCustomDialog;

import static com.example.nick.growerland.constant.ListConstants.CONFIG;
import static com.example.nick.growerland.constant.ListConstants.URL_JSON_SETTINGS;

public class MenuActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LOADER_ID = 1;
    RecyclerView recyclerItems;
    CardCursorAdapter adapter;

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
                if (!nameStr.isEmpty()) {

                    final ContentValues cvNewTicket = new ContentValues();
                    cvNewTicket.put(GardenModel.NAME, nameStr);
                    // TODO: 30.04.2017
                    cvNewTicket.put(GardenModel.PLACE, "GRODNO");
                    cvNewTicket.put(GardenModel.ID, (Integer) null);

                    final DatabaseHelper db = DatabaseHelper.Impl.newInstance(MenuActivity.this);

                    db.insert(GardenModel.class, cvNewTicket, new OnResultCallback<Long, Void>() {

                        @Override
                        public void onSuccess(final Long pLong) {
                            Log.d("thecriser", "suc");
                        }

                        @Override
                        public void onError(final Exception pE) {
                            Log.d("thecriser", "err");
                        }

                        @Override
                        public void onProgressChanged(final Void pVoid) {

                        }
                    });

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
    protected void onResume() {
        super.onResume();
        final LoaderManager supportLoaderManager = getSupportLoaderManager();
        if (supportLoaderManager.getLoader(LOADER_ID) != null) {
            supportLoaderManager.getLoader(LOADER_ID).forceLoad();
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(final int id, final Bundle args) {
        return new CardsCursorLoader(this, "", GardenModel.class, getApplication());
    }

    @Override
    public void onLoadFinished(final Loader<Cursor> loader, final Cursor data) {
        adapter = new CardCursorAdapter(data, this, R.layout.item_garden);
        recyclerItems.swapAdapter(adapter, true);

    }

    @Override
    public void onLoaderReset(final Loader<Cursor> loader) {
        recyclerItems.swapAdapter(null, true);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        recyclerItems = (RecyclerView) findViewById(R.id.recycler);
        getSupportLoaderManager().restartLoader(LOADER_ID, null, this);

        recyclerItems.setHasFixedSize(true);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerItems.setLayoutManager(layoutManager);

        takeJson();
    }

    public void takeJson() {
        final OwnAsyncTask sync = new OwnAsyncTask();

        try {
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
        } catch (final Exception e) {
            Toast.makeText(this, "No internet", Toast.LENGTH_SHORT).show();
        }
    }
}