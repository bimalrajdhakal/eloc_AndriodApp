package com.nexogen.routefinder.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by bimal on 7/4/18.
 */

public class JsonAct {
    String ipval = "";
    String path = "";
    SharedPreferences sh;

    public JsonAct(Context context)
    {
        sh = PreferenceManager.getDefaultSharedPreferences(context);
        ipval = sh.getString("ip", "192.168.43.86");
        path = "http://"+ipval+":5000/";
    }

    public String setJsonVal(String upath)
    {
        HttpURLConnection c = null;
        try {
            URL u=new URL(path+upath);
            Log.v("xxxxxxxxxxxx", "URL = "+u.toString());
            c=(HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setRequestProperty("Content-length","0");
            c.setUseCaches(false);
            c.setAllowUserInteraction(false);
            c.connect();
            int status=c.getResponseCode();

            switch (status)
            {
                case 200:
                case 201:
                    BufferedReader br=new BufferedReader(new InputStreamReader(c.getInputStream()));
                    StringBuilder sb=new StringBuilder();
                    String line = "";
                    while ((line=br.readLine())!=null)
                    {
                        sb.append(line+"\n");
                    }
                    br.close();
                    return sb.toString();
                default:
                    return "";
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
