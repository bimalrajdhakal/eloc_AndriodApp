package com.nexogen.routefinder.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.nexogen.routefinder.R;

public class IPConnection extends AppCompatActivity {
    EditText ip_text;
    AppCompatButton ipconnect;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipconnection);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ip_text=(EditText)findViewById(R.id.ip_address);
        ipconnect=(AppCompatButton)findViewById(R.id.btn_ipconnect);
        ip_text.setText(sh.getString("ip", "192.168.43.86"));

        ipconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ip_add=ip_text.getText().toString();

                if (!ip_add.equalsIgnoreCase("")){
                    // connection to server

                    SharedPreferences.Editor er=sh.edit();
                    er.putString("ip",ip_add);
                    er.commit();

                    // Intent for redirect to login page
                    Intent loginactivity =new Intent(getApplicationContext(),UserLogin.class);
                    startActivity(loginactivity);
                }
                else{
                    // else condition
                    // message if not connected to server
                    Toast.makeText(getApplicationContext(),"Not Connected to Server",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
