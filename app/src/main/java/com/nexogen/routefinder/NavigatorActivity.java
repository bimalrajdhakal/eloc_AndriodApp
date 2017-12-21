package com.nexogen.routefinder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.nexogen.routefinder.ChekNetwork.CheckNetworkConnection;
import com.nexogen.routefinder.databases.AppDatabase;
import com.nexogen.routefinder.databases.NavigatorTable;
import com.nexogen.routefinder.intefaces.TagName;
import com.nexogen.routefinder.utils.Global;
import com.nexogen.routefinder.utils.UtilClass;

import java.util.List;

import static com.nexogen.routefinder.intefaces.TagName.fromAndTo;

/**
 * Created by nexogen on 11/12/17.
 */

public class NavigatorActivity extends AppCompatActivity implements View.OnClickListener {
    double[] dataSource = {0, 0};
    double[] dataFrom = {0, 0};
    private ImageButton btnCancel, btnCurLocation, btnFindRoute;
    private EditText edt_to, edt_from;
    private String source, destination;
    private UtilClass utilClass;
    private AppDatabase appDatabase;


    private List<NavigatorTable> navigatorTables;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigator_layout);
        utilClass = new UtilClass();

        init();
        // room persistence database third party library
        dataBaseStore();


    }

    private void dataBaseStore() {

        appDatabase = AppDatabase.getDatabase(this);

        navigatorTables = appDatabase.navigatorDao().getAllUser();

        Log.w("check", navigatorTables.toString());


    }

    private void init() {
        btnCancel = findViewById(R.id.btn_cancel);
        btnCurLocation = findViewById(R.id.btn_crrent_location);
        btnFindRoute = findViewById(R.id.find_route_btn);

        btnCancel.setOnClickListener(this);
        btnCurLocation.setOnClickListener(this);
        btnFindRoute.setOnClickListener(this);

        edt_to = findViewById(R.id.edt_to);
        edt_from = findViewById(R.id.edt_from);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                finish();

                break;
            case R.id.btn_crrent_location:

                try {
                    edt_from.setText(LocationAddress.address.getPremises() + " " + LocationAddress.address.getSubLocality() + " " + LocationAddress.address.getLocality());
                } catch (Exception e) {
                    e.printStackTrace();
                }


                break;

            case R.id.find_route_btn:
                source = edt_from.getText().toString().trim();
                destination = edt_to.getText().toString().trim();


                try {
                    dataSource = utilClass.getAddressName(source, NavigatorActivity.this);
                    dataFrom = utilClass.getAddressName(destination, NavigatorActivity.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                if (CheckNetworkConnection.isConnectionAvailable(NavigatorActivity.this)) {

                    if (source.isEmpty() || destination.isEmpty()) {
                        Toast.makeText(this, fromAndTo, Toast.LENGTH_SHORT).show();
                    } else {
                        appDatabase.navigatorDao().addUser(new NavigatorTable(navigatorTables.size() + 1, source + " to", destination, Global.sampleDateFormat()));

                        utilClass.openGoogleMap(NavigatorActivity.this, dataSource[0], dataSource[1]
                                , dataFrom[0], dataFrom[1]);

                        edt_from.setText("");
                        edt_to.setText("");
                    }

                } else {
                    Toast.makeText(NavigatorActivity.this, TagName.notConnected, Toast.LENGTH_SHORT).show();
                }


                break;
        }
    }
}
