package com.nexogen.routefinder.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.nexogen.routefinder.Modules.AppLocationService;
import com.nexogen.routefinder.R;
import com.nexogen.routefinder.fragments.AddressFragment;
import com.nexogen.routefinder.fragments.LocationHistoryListFragment;
import com.nexogen.routefinder.fragments.MapLoadingFragment;
import com.nexogen.routefinder.fragments.NearByFragment;
import com.nexogen.routefinder.fragments.SettingsFragment;
import com.nexogen.routefinder.intefaces.FragmentMessangers;
import com.nexogen.routefinder.intefaces.TagName;
import com.nexogen.routefinder.model.LatLongModel;
import com.nexogen.routefinder.utils.AlertDialogHelper;
import com.nexogen.routefinder.utils.NavigationDrawerHelper;

import java.util.ArrayList;

import static com.nexogen.routefinder.intefaces.TagName.Cancel;
import static com.nexogen.routefinder.intefaces.TagName.GPSENABLED;
import static com.nexogen.routefinder.intefaces.TagName.GPS_SETTINGS;
import static com.nexogen.routefinder.intefaces.TagName.HomeFragment;

public class MainActivity extends AppCompatActivity implements FragmentMessangers, ListView.OnItemClickListener, AlertDialogHelper.AlertDialogListener {
    public static NavigationDrawerHelper mNavigationDrawerHelper;
    public static ArrayList<LatLongModel> latLongModel;
    public static TextView toolbar_title;
    public FragmentTransaction fragmentTransaction;
    private android.support.v4.app.FragmentManager fragmentManager;
    private AppLocationService appLocationService;
    private Location locations;
    private AlertDialogHelper alertDialogHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        alertDialogHelper = new AlertDialogHelper(this);

        latLongModel = new ArrayList<>();

        mNavigationDrawerHelper = new NavigationDrawerHelper();
        mNavigationDrawerHelper.init(this, this);

        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText(getResources().getString(R.string.currentLocation));


        ImageButton imageButton = (ImageButton) findViewById(R.id.drwaer_btn);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNavigationDrawerHelper.handleSelectOpen(1);
            }
        });
        changeFragment(new MapLoadingFragment(), null, HomeFragment, true);


        appLocationService = new AppLocationService(this);
        locations = appLocationService.getLocation(LocationManager.GPS_PROVIDER);
        if (locations == null) {
            locations = appLocationService.getLocation(LocationManager.NETWORK_PROVIDER);
        }
        if (locations != null) {
            latLongModel.add(new LatLongModel(1, (float) locations.getLatitude(), (float) locations.getLongitude()));
        } else {
            alertDialogHelper.showAlertDialog(GPS_SETTINGS, GPSENABLED, TagName.Settings, Cancel, 0);


        }

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        mNavigationDrawerHelper.handleSelect(position);
        switch (position) {
            case 4:
                clearStack();
                changeFragment(new MapLoadingFragment(), null, HomeFragment, true);
                break;
            case 5:
                clearStack();
                startActivity(new Intent(getApplicationContext(), NavigatorActivity.class));
                break;
            case 6:
                clearStack();
                changeFragment(new NearByFragment(), null, "NearByFragment", true);
                break;
            case 7:
                clearStack();
                changeFragment(new AddressFragment(), null, "AddressFragment", true);
                break;
            case 8:
                clearStack();
                changeFragment(new LocationHistoryListFragment(), null, "LocationHistoryListFragment", true);
                break;
            case 9:
                clearStack();
                startActivity(new Intent(MainActivity.this, BookMarkActivity.class));
                break;
            case 10:
                 clearStack();
                 startActivity(new Intent(getApplicationContext(),GetEloc.class));
                 break;
            case 11:
                clearStack();
                startActivity(new Intent(getApplicationContext(),showQRCocde.class));
                break;

            case 12:
                clearStack();
                startActivity(new Intent(getApplicationContext(),SearchEloc.class));
                break;

            case 13:
                clearStack();
                changeFragment(new SettingsFragment(), null, "SettingsFragment", true);
                break;

        }

    }

    private void clearStack() {
        if (fragmentManager.getBackStackEntryCount() > 1) {
            fragmentManager.popBackStack();
        }
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mNavigationDrawerHelper.syncState();
    }

    // if the Drawer is opened or not
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mNavigationDrawerHelper.handleOnPrepareOptionMenu(menu);
        return super.onPrepareOptionsMenu(menu);
    }

    // If any configuration change, the NavigationDrawerHelper
    // will be laying up the ActionBAr
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        mNavigationDrawerHelper.syncState();
        super.onConfigurationChanged(newConfig);
    }


    @Override
    public void changeFragment(Fragment fragment, Bundle data, String fragmentTag, boolean addToBackStack) {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment, fragmentTag);
        if (data != null)
            fragment.setArguments(data);
        fragmentTransaction.addToBackStack(fragmentTag);
        fragmentTransaction.commit();

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (fragmentManager.getBackStackEntryCount() == 0) {
            finish();
        } else {

        }


    }


    @Override
    public void onPositiveClick(int from) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
    }

    @Override
    public void onNegativeClick(int from) {

    }

    @Override
    public void onNeutralClick(int from) {

    }
}

