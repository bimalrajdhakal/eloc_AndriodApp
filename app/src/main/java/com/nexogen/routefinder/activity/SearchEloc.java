package com.nexogen.routefinder.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nexogen.routefinder.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.nexogen.routefinder.activity.MainActivity.toolbar_title;

public class SearchEloc extends AppCompatActivity{

    AppCompatButton eloc_continue;
    EditText elocid;
    public static String eloc_id,eloc_info;
    SharedPreferences sh;
    String ur;
    String hno,street,landmark,area,village_town,pincode,lat,lng,district,state,resident_type;
    JSONParser jparser =new JSONParser();
    public static  Double lati,longi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_eloc);
        eloc_continue=(AppCompatButton)findViewById(R.id.btn_eloc_continue);
        elocid=(EditText)findViewById(R.id.id_eloc);


        eloc_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eloc_id=elocid.getText().toString();
                int flg=0;
                if(eloc_id.equalsIgnoreCase("")){
                    elocid.setError("You can't leave this empty!!");
                    flg++;
                }
                if(eloc_id.length()<6){
                    elocid.setError("eLoc should be 6 character long.");
                    flg++;
                }

                if(flg==0)
                    try{
                        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        String ip = sh.getString("ip", "");
                        ur = "http://" + ip + ":5000/eloc/api/searchEloc";
                        List<NameValuePair> details = new ArrayList<NameValuePair>();
                        details.add(new BasicNameValuePair("eloc_id",eloc_id));
                        JSONObject job = jparser.makeHttpRequest(ur, "GET", details);
                        String response = job.getString("status");
                        if(response.equalsIgnoreCase("Success")){
                            JSONArray jsonArray=job.getJSONArray("elocresult");
                            JSONObject jobj=jsonArray.getJSONObject(0);
                            eloc_id=jobj.getString("eloc_id");
                            hno=jobj.getString("hno");
                            street=jobj.getString("street");
                            landmark=jobj.getString("landmark");
                            area=jobj.getString("area");
                            village_town=jobj.getString("village_town");
                            pincode=jobj.getString("pincode");
                            district=jobj.getString("district");
                            state=jobj.getString("state");
                            lat=jobj.getString("lat");
                            lng=jobj.getString("lng");
                            resident_type=jobj.getString("residency_type");
                            eloc_info =("eloc_id:"+eloc_id+","+"Resident Type:"+resident_type+","+"House/Floor/Door No:"+hno+","+"Street/Road/Lane:"+street+","+
                                    "Landmark:"+landmark+","+"Area/Locality:"+area+","+"Village/Town/City:"+village_town+","+"PIN_Code:"+pincode+","+"District:"+district+","+
                                    "State:"+state);

                            //1L6WMS
                            lati=Double.parseDouble(lat);
                            longi=Double.parseDouble(lng);

                            ProgressDialog progressDialog=new ProgressDialog(SearchEloc.this);
                            progressDialog.setMessage("Loading...");
                            progressDialog.setTitle("eLoc Search Progress");
                            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            progressDialog.show();
                            // redirect to elocsearch map activity to display marker on map
                            Intent elocmapint=new Intent(getApplicationContext(),ElocSearchActivity.class);
                            startActivity(elocmapint);

                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Something went wrong !!!",Toast.LENGTH_LONG).show();
                        }
                    }catch(Exception e){
                        Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                    }

            }
        });

    }

}
