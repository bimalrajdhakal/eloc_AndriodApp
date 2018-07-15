package com.nexogen.routefinder.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.nexogen.routefinder.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserLogin extends AppCompatActivity {
    EditText userEmail,userPassword;
    AppCompatButton login_btn,register_btn,skip_btn;
    String user_email,user_password;
    String ur;
    SharedPreferences sh;
    JSONParser jparser =new JSONParser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        userEmail=(EditText)findViewById(R.id.user_name);
        userPassword=(EditText)findViewById(R.id.user_pass);
        login_btn=(AppCompatButton)findViewById(R.id.btn_login);
        register_btn=(AppCompatButton)findViewById(R.id.btn_newregister);
        // temporary button
        skip_btn=(AppCompatButton)findViewById(R.id.btn_skip);
        skip_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainintent =new Intent(getApplicationContext(),MainActivity.class);
                startActivity(mainintent);
            }
        });

        

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_email=userEmail.getText().toString();
                user_password=userPassword.getText().toString();

                int flg=0;
                if(user_email.equalsIgnoreCase("")){
                    userEmail.setError("You can't leave this empty !");
                    flg++;
                }
                if(!user_email.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")){
                    userEmail.setError("Please enter valid email");
                    flg++;
                }
                if(user_password.equalsIgnoreCase("")){
                    userPassword.setError("You can't leave this empty !");
                    flg++;
                }

                if(user_password.length()<=4){
                    userPassword.setError("Password criteria not matched !");
                    flg++;
                }

                if(flg==0)
                {
                    try{
                        // code to user login
                        sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        String ip =sh.getString("ip","");
                        ur="http://"+ip+":5000/eloc/api/login";
                        //Toast.makeText(getApplicationContext(),ur,Toast.LENGTH_LONG).show();
                        List<NameValuePair> logindetails=new ArrayList<NameValuePair>();
                        logindetails.add(new BasicNameValuePair("useremail",user_email));
                        logindetails.add(new BasicNameValuePair("password",user_password));

                        JSONObject job =jparser.makeHttpRequest(ur,"GET",logindetails);
                        String response =job.getString("status");
                        if(response.equalsIgnoreCase("Success")){
                            // main intent
                            SharedPreferences.Editor editor=sh.edit();
                            editor.putString("useremail",user_email);
                            editor.commit();
                            Intent mainintent =new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(mainintent);
                        }

                        if(response.equalsIgnoreCase("Failed")){
                            // error message if the user name or password goes wrong
                            Toast.makeText(getApplicationContext(),"Invalid Username or Password !",Toast.LENGTH_LONG).show();
                        }

                        if (response.equalsIgnoreCase("NF")){
                            Toast.makeText(getApplicationContext(),"Sorry! You are not registered with us.",Toast.LENGTH_LONG).show();
                        }

                    }
                    catch (Exception e){
                        Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                    }

                }
            }
        });
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regIntent=new Intent(getApplicationContext(),UserRegister.class);
                startActivity(regIntent);
            }
        });
    }




}
