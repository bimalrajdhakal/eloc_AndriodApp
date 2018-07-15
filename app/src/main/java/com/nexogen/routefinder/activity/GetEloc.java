package com.nexogen.routefinder.activity;


import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationListener;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.nexogen.routefinder.Modules.AppLocationService;
import com.nexogen.routefinder.R;
import com.nexogen.routefinder.model.LatLongModel;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class GetEloc extends AppCompatActivity {
    EditText houseno,street,landMark,areaLocality,villgeTown,pin;
    RadioButton resident,non_resident;
    AppCompatButton btn_getEloc;
    String resident_type,houseNo,streetRoad,land_mark,area_locality,village_town,pincode;

    String ur;
    String lati,longi;
    String generated_by;
    SharedPreferences sh;
    JSONParser jparser =new JSONParser();

    //GPSTracker gpsTracker=new GPSTracker(GetEloc.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_eloc);


        // creating variables
        resident=(RadioButton)findViewById(R.id.resident);
        non_resident=(RadioButton)findViewById(R.id.non_resident);
        houseno=(EditText)findViewById(R.id.hno);
        street=(EditText)findViewById(R.id.street_road);
        landMark=(EditText)findViewById(R.id.landmark);
        areaLocality=(EditText)findViewById(R.id.area_locality);
        villgeTown=(EditText)findViewById(R.id.village_town);
        pin=(EditText)findViewById(R.id.pincode);
        btn_getEloc=(AppCompatButton)findViewById(R.id.btn_geteloc);


        btn_getEloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                houseNo=houseno.getText().toString();
                streetRoad=street.getText().toString();
                land_mark=landMark.getText().toString();
                area_locality=areaLocality.getText().toString();
                village_town=villgeTown.getText().toString();
                pincode=pin.getText().toString();

                if(resident.isChecked()) {
                    resident_type = "Residential";
                }else if(non_resident.isChecked()){
                    resident_type="Non-Residential";
                }


                // validation start

                int flg=0;

                if(houseNo.equalsIgnoreCase("")){
                    houseno.setError("You can't leave this empty !");
                    flg++;
                }

                if(streetRoad.equalsIgnoreCase("")){
                    street.setError("You can't leave this empty !");
                    flg++;
                }

                if(!Pattern.matches("^[\\p{L} .'-]+$",street.getText())){
                    street.setError("Please enter valid Street Name");
                    flg++;
                }


                if(land_mark.equalsIgnoreCase("")){
                    landMark.setError("You can't leave this empty !");
                    flg++;
                }

                if(!Pattern.matches("^[\\p{L} .'-]+$",landMark.getText())){
                    landMark.setError("Please enter valid Landmark Name");
                    flg++;
                }


                if(area_locality.equalsIgnoreCase("")){
                    areaLocality.setError("You can't leave this empty !");
                    flg++;
                }

                if(!Pattern.matches("^[\\p{L} .'-]+$",areaLocality.getText())){
                    areaLocality.setError("Please enter valid Area/Locality Name");
                    flg++;
                }

                if(village_town.equalsIgnoreCase("")){
                    villgeTown.setError("You can't leave this empty !");
                    flg++;
                }

                if(!Pattern.matches("^[\\p{L} .'-]+$",villgeTown.getText())){
                    villgeTown.setError("Please enter valid Village/Town/City");
                    flg++;
                }

                if(pincode.equalsIgnoreCase("")){
                    pin.setError("You can't leave this empty !");
                    flg++;
                }

                if(pincode.length()!=6){
                    pin.setError("Please enter 6 digit PIN Code");
                }

                // validation ends here


                if(flg==0) {

                    GPSTracker gt =new GPSTracker(GetEloc.this);

                    Double latitude=gt.getLatitude();
                    Double longitude=gt.getLongitude();
                    lati=Double.toString(latitude);
                    longi=Double.toString(longitude);

                    try {
                        // to store user's eloc data
                        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        String ip = sh.getString("ip", "");
                        ur = "http://" + ip + ":5000/eloc/api/geteloc";
                        generated_by=sh.getString("useremail","");

                        List<NameValuePair> elocdetails = new ArrayList<NameValuePair>();
                        elocdetails.add(new BasicNameValuePair("resident",resident_type));
                        elocdetails.add(new BasicNameValuePair("hnobldg", houseNo));
                        elocdetails.add(new BasicNameValuePair("street", streetRoad));
                        elocdetails.add(new BasicNameValuePair("landmark", land_mark));
                        elocdetails.add(new BasicNameValuePair("area", area_locality));
                        elocdetails.add(new BasicNameValuePair("villagetown", village_town));
                        elocdetails.add(new BasicNameValuePair("pincode", pincode));
                        elocdetails.add(new BasicNameValuePair("lat", lati));
                        elocdetails.add(new BasicNameValuePair("lng", longi));
                        elocdetails.add(new BasicNameValuePair("useremail", generated_by));

                        JSONObject job = jparser.makeHttpRequest(ur, "POST", elocdetails);
                        String response = job.getString("status");
                        if (response.equalsIgnoreCase("Success")) {
                            String eloc_id=job.getString("eloc_id");
                            AlertDialog alertDialog = new AlertDialog.Builder(GetEloc.this).create();
                            alertDialog.setTitle("eLoc Information !!!");
                            alertDialog.setMessage("Your eLoc Id is: "+eloc_id);
                            alertDialog.setButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent elocintent=new Intent(getApplicationContext(),GetEloc.class);
                                    startActivity(elocintent);
                                }
                            });
                            alertDialog.show();
                        }

                    }
                    catch (Exception e){
                        Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();

                    }

                }

            }
        });

    }
}
