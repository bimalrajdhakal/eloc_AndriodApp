package com.nexogen.routefinder.utils;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.nexogen.routefinder.R;
import com.nexogen.routefinder.adapter.DrawerItemCustomAdapter;
import com.nexogen.routefinder.model.ObjectDrawerItem;

public class NavigationDrawerHelper {

    DrawerLayout mDrawerLayout;
    ListView mDrawerListView;
    private ActionBarDrawerToggle mDrawerToggle;

    private int[] navigatioIcon = { R.drawable.address, //1
                                    R.drawable.bookmarks, //2
                                    R.drawable.locationhistory, //3
                                    R.drawable.navigator, //4
                                    R.drawable.current_locations1, //5
                                    R.drawable.navigator, //6
                                    R.drawable.nearby, //7
                                    R.drawable.address, //8
                                    R.drawable.locationhistory, //9
                                    R.drawable.bookmarks, //10
                                    R.drawable.eloc, //11
                                    R.drawable.qr, //12
                                    R.drawable.search_eloc, //13
                                    R.drawable.settings     //14
                                };


    public void init(Activity activity, ListView.OnItemClickListener listener) {

        // We define our constant variables
        mDrawerLayout = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        mDrawerListView = (ListView) activity.findViewById(R.id.left_drawer);


        String[] title = activity.getResources().getStringArray(R.array.title);

        // List the Drawer Items
        ObjectDrawerItem[] drawerItem = new ObjectDrawerItem[14];

        for (int count = 0; count < title.length; count++) {
            drawerItem[count] = new ObjectDrawerItem(navigatioIcon[count], title[count]);


        }


        // Declare a new instance of our Custom drawer Adapter
        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(activity, R.layout.listview_drawer_item_row, drawerItem);

        // Set the Adapter and the Listener on the ListView
        mDrawerListView.setAdapter(adapter);

        mDrawerListView.setOnItemClickListener(listener);

        // Set shadow and the default item selected in the ListView to be the first one
        mDrawerLayout.setDrawerShadow(R.drawable.cancel2, GravityCompat.START);
        mDrawerListView.setItemChecked(0, true);

        // Call the next method
        setupActionBar(activity);
    }

    // (opening/closing) of the drawer when the User taps the Menu Icon.
    private void setupActionBar(final Activity theActivity) {
        final Activity activity = theActivity;

        mDrawerToggle = new ActionBarDrawerToggle(
                theActivity,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.share_selector,  /* nav drawer image to replace 'Up' caret */
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close)

        {
            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onDrawerOpened(View drawerView) {
                activity.invalidateOptionsMenu();
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                activity.invalidateOptionsMenu();
                super.onDrawerClosed(drawerView);
            }
        };

    }

    // To highlight at selection, the list item selected and close the Drawer
    // Will be called in the OnItemClick() method of the hosting Activity
    public void handleSelect(int option) {
        mDrawerListView.setItemChecked(option, true);
        mDrawerLayout.closeDrawer(mDrawerListView);

    }

    public void handleSelectOpen(int option) {
        mDrawerListView.setItemChecked(option, true);
        mDrawerLayout.openDrawer(mDrawerListView);

    }


    // It checks if the Drawer is Open and it loops through the Menu option and disables all of them.
    // If the Drawer is closed, it enables all of them
    public void handleOnPrepareOptionMenu(Menu menu) {
        boolean itemVisible = !mDrawerLayout.isDrawerOpen(mDrawerListView);
        for (int index = 0; index < menu.size(); index++) {
            MenuItem item = menu.getItem(index);
            item.setEnabled(itemVisible);
        }

    }

    // To delegate the Open/Close when the User taps the menu icon
    public void handleOnOptionsItemSelected(MenuItem menuItem) {
        mDrawerToggle.onOptionsItemSelected(menuItem);

    }

    // Tells the Drawer Toggle to check everything is checked and layed out the way it should be after configuration change
    public void syncState() {
        mDrawerToggle.syncState();
    }

    public void setSelection(int option) {
        mDrawerListView.setItemChecked(option, true);
    }

}