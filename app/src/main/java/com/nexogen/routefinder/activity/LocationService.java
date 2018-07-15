package com.nexogen.routefinder.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

public class LocationService extends Service {

	private LocationManager locationManager;
	private Boolean locationChanged;
	SharedPreferences sh;

	private Handler handler = new Handler();
	public static Location curLocation;
	public static boolean isService = true;
	String ip = "";
	String[] zone;
	String pc = "";
	String url = "";
	String lid="";


	public static String phoneid = "", place = "", address = "", lati = "", logi = "", imei = "";



	LocationListener locationListener = new LocationListener() {

		public void onLocationChanged(Location location) {
			if (curLocation == null) {
				curLocation = location;
				locationChanged = true;
			} else if (curLocation.getLatitude() == location.getLatitude() && curLocation.getLongitude() == location.getLongitude()) {
				locationChanged = false;
				return;
			} else
				locationChanged = true;
			curLocation = location;

			if (locationChanged)
				locationManager.removeUpdates(locationListener);
		}

		public void onProviderDisabled(String provider) {
		}

		public void onProviderEnabled(String provider) {
		}


		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			if (status == 0)// UnAvailable
			{
			} else if (status == 1)// Trying to Connect
			{
			} else if (status == 2) {// Available
			}
		}
	};

	@Override
	public void onCreate() {
		super.onCreate();
		sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		String ip = sh.getString("ip", "");
		url="http://"+ip+":5000/curloc/";
		lid=sh.getString("lid","");



		curLocation = getBestLocation();

		if (curLocation == null) {
			System.out.println("starting problem.........3...");
			Toast.makeText(this, "GPS problem..........", Toast.LENGTH_SHORT).show();
		} else {
			 Log.d("ssssssssssss", String.valueOf("latitude2.........."+curLocation.getLatitude()));
		}
		isService = true;
	}

	final String TAG = "LocationService";

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}

	@Override

	public void onLowMemory() {
		super.onLowMemory();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		Toast.makeText(this, "Start services", Toast.LENGTH_SHORT).show();

		String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

		if (!provider.contains("gps")) { //if gps is disabled
			final Intent poke = new Intent();
			poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
			poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
			poke.setData(Uri.parse("3"));
			sendBroadcast(poke);
		}
		handler.postDelayed(GpsFinder, 100);

	}


	@Override
	public void onDestroy() {

		String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

		if (provider.contains("gps")) { //if gps is enabled
			final Intent poke = new Intent();
			poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
			poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
			poke.setData(Uri.parse("3"));
			sendBroadcast(poke);
		}

		handler.removeCallbacks(GpsFinder);
		handler = null;
		Toast.makeText(this, "Service Stopped..!!", Toast.LENGTH_SHORT).show();
		isService = false;
	}


	public Runnable GpsFinder = new Runnable() {


		public void run() {

			String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

			if (!provider.contains("gps")) { //if gps is disabled
				final Intent poke = new Intent();
				poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
				poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
				poke.setData(Uri.parse("3"));
				sendBroadcast(poke);
			}


			Location tempLoc = getBestLocation();

			if (tempLoc != null) {

				curLocation = tempLoc;


				lati = String.valueOf(curLocation.getLatitude());
				logi = String.valueOf(curLocation.getLongitude());


				Toast.makeText(getApplicationContext(),phoneid+"\nlat.. and longi.."+ lati+"..."+logi, Toast.LENGTH_SHORT).show();
				insertloc(lid, lati, logi);

				String loc = "";

				Geocoder geoCoder = new Geocoder(getBaseContext(), Locale.getDefault());
				try {
					List<Address> addresses = geoCoder.getFromLocation(curLocation.getLatitude(), curLocation.getLongitude(), 1);
					if (addresses.size() > 0) {
						for (int index = 0; index < addresses.get(0).getMaxAddressLineIndex(); index++)
							address += addresses.get(0).getAddressLine(index) + " ";
						//Log.d("get loc...", address);

						place = addresses.get(0).getLocality().toString();

					} else {

					}
				} catch (IOException e) {
					e.printStackTrace();
				}

				if (!place.equalsIgnoreCase("") && place != null) {

				}




			}
			handler.postDelayed(GpsFinder, 30000);// register again to start after 20 seconds...
		}

		private void insertloc(String lid, String lati, String logi) {


			try {
				List<NameValuePair> ad = new ArrayList<NameValuePair>();
				ad.add(new BasicNameValuePair("uid", lid));
				ad.add(new BasicNameValuePair("lati", lati));
				ad.add(new BasicNameValuePair("longi", logi));

				JSONParser json = new JSONParser();
				JSONObject job = json.makeHttpRequest(url, "GET", ad);

				Toast.makeText(getApplicationContext(),"Loc Update..", Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
//				Log.v("pearl",e.getMessage());
				Toast.makeText(getApplicationContext(),"Error in Loc Update.."+e.toString(), Toast.LENGTH_SHORT).show();

			}


		}

	};

	private Location getBestLocation() {
		Location gpslocation = null;
		Location networkLocation = null;
		if (locationManager == null) {
			locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
		}
		try {
			if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
				locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locationListener);// here you can set the 2nd argument time interval also that after how much time it will get the gps location
				if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
					// TODO: Consider calling

					return null;
				}
				gpslocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

	              
	            }
	            if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
	                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,5000, 0, locationListener);
	                networkLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER); 
	            }
	        } catch (IllegalArgumentException e) {
	            Log.e("error", e.toString());
	        }
	        if(gpslocation==null && networkLocation==null)
	            return null;

	        if(gpslocation!=null && networkLocation!=null){
	            if(gpslocation.getTime() < networkLocation.getTime()){
	                gpslocation = null;
	                return networkLocation;
	            }else{
	                networkLocation = null;
	                return gpslocation;
	            }
	        }
	        if (gpslocation == null) {
	            return networkLocation;
	        }
	        if (networkLocation == null) {
	            return gpslocation;
	        }
	        return null;
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
