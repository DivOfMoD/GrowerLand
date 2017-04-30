package com.example.nick.growerland.ui;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nick.growerland.NotifyUser;
import com.example.nick.growerland.PlanetAdapter;
import com.example.nick.growerland.R;
import com.example.nick.growerland.async.OnResultCallback;
import com.example.nick.growerland.async.OwnAsyncTask;
import com.example.nick.growerland.async.task.LoadVeg;
import com.example.nick.growerland.constant.ListConstants;
import com.example.nick.growerland.lovelydialog.AlertCustomDialog;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class AddGardenActivity extends AppCompatActivity {

    TextView title;
    RecyclerView mRecyclerView;
    private MapView mapView;
    private GoogleMap googleMap;

    public void onPushClicked(final View view) {
        NotifyUser.notifyUser(this, MenuActivity.class, "GrowerLand", "Poor your vegetables right now on Baranovichi garden...", R.drawable.leafgreen);
    }

    public void onLocationClicked(final View view) {
//        final AlertCustomDialog dialog = new AlertCustomDialog(this);
//        dialog.setView(R.layout.custom_map)
//                .setTopColorRes(R.color.brand_color)
//                .setCancelable(false)
//                .setIcon(R.drawable.leaf)
//                .show();
//
//        final View viewDialog = dialog.getAddedView();
//        final MapView mapView = (MapView) viewDialog.findViewById(R.id.map);
//        final TextView ok = (TextView) viewDialog.findViewById(R.id.ld_btn_confirm);
//        final TextView cancel = (TextView) viewDialog.findViewById(R.id.ld_btn_negative);
//
//        if (googleMap == null) {
//            mapView.onResume();
//            mapView.getMapAsync(new OnMapReadyCallback() {
//
//                @Override
//                public void onMapReady(final GoogleMap pGoogleMap) {
//                    googleMap = pGoogleMap;
//                    googleMap.getUiSettings().setAllGesturesEnabled(true);
//                    if (ActivityCompat.checkSelfPermission(AddGardenActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                            && ActivityCompat.checkSelfPermission(AddGardenActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                        return;
//                    }
//                    googleMap.setMyLocationEnabled(true);
//                    final LatLng currentLatLng = new LatLng(53.66, 23.83);
//
//                    if (currentLatLng != null) {
//                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 14));
//                    }
//                }
//            });
//        }
//
//        ok.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(final View v) {
//                dialog.dismiss();
//            }
//        });
//
//        cancel.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(final View v) {
//                dialog.dismiss();
//            }
//        });
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_garden);

//        mapView = (MapView) findViewById(R.id.map);
//        mapView.onCreate(savedInstanceState);

        mRecyclerView = (RecyclerView) findViewById(R.id.item_recycler_veg);
        final RecyclerView.Adapter[] adapter = new RecyclerView.Adapter[1];
        final RecyclerView.LayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        final OwnAsyncTask sync = new OwnAsyncTask();

        try {
            sync.execute(new LoadVeg(), this, new OnResultCallback<ArrayList<String>, Void>() {

                @Override
                public void onSuccess(final ArrayList<String> pStrings) {
                    findViewById(R.id.progress).setVisibility(View.GONE);
                    adapter[0] = new PlanetAdapter(pStrings, getApplicationContext());
                    mRecyclerView.setAdapter(adapter[0]);
                }

                @Override
                public void onError(final Exception pE) {

                }

                @Override
                public void onProgressChanged(final Void pVoid) {

                }
            });
        } catch (final Exception e) {
            Toast.makeText(this, "No internet", Toast.LENGTH_SHORT).show();
        }

        title = (TextView) findViewById(R.id.title);

        final String gardenName;
        if (savedInstanceState == null) {
            final Bundle extras = getIntent().getExtras();
            if (extras == null) {
                gardenName = null;
            } else {
                gardenName = extras.getString(ListConstants.Extras.EXTRA_GARDEN_NAME_CREATE);
            }
        } else {
            gardenName = (String) savedInstanceState.getSerializable(ListConstants.Extras.EXTRA_GARDEN_NAME_CREATE);
        }

        title.setText(gardenName);
    }
}
