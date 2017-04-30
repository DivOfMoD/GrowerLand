package com.example.nick.growerland.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.nick.growerland.PlanetAdapter;
import com.example.nick.growerland.R;
import com.example.nick.growerland.constant.ListConstants;
import com.example.nick.growerland.plantmanager.PlantJsonProvider;
import com.example.nick.growerland.plantmanager.core.PlantParent;

import java.util.ArrayList;

public class AddGardenActivity extends AppCompatActivity {

    TextView title;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_garden);
        mRecyclerView = (RecyclerView) findViewById(R.id.item_recycler_veg);
        final RecyclerView.Adapter adapter;
        final RecyclerView.LayoutManager layoutManager;

        final ArrayList<String> planetList = new ArrayList();

        ArrayList<PlantParent> arrayList = new PlantJsonProvider().getJsonCollection(this, "http://growerlandnasa.appspot.com/config").getPlantTypes().get(0).getPlantParents();

        for (int i = 0; i < arrayList.size(); i++) {
            for (int j = 0; j < arrayList.get(i).getSorts().size(); j++) {
                String name = arrayList.get(i).getName() + " | " + arrayList.get(i).getSorts().get(j).getName();
                planetList.add(name);
            }
        }

        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        adapter = new PlanetAdapter(planetList, getApplicationContext());
        mRecyclerView.setAdapter(adapter);

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
