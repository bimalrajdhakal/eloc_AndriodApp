package com.nexogen.routefinder.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class UserRegister extends AppCompatActivity {
    EditText fullName,userEmail,userMobNo,userPassword,confrm_password;
    AppCompatButton register_btn,login_btn;

    String userFullName,user_email,user_mobno,user_password,conf_password;
    String ur;
    SharedPreferences sh;
    JSONParser jparser =new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        fullName=(EditText)findViewById(R.id.full_name);
        userEmail=(EditText)findViewById(R.id.user_email);
        userMobNo=(EditText)findViewById(R.id.user_mobno);
        userPassword=(EditText)findViewById(R.id.user_password);
        confrm_password=(EditText)findViewById(R.id.confirm_password);
        register_btn=(AppCompatButton)findViewById(R.id.btn_register);
        login_btn=(AppCompatButton)findViewById(R.id.btn_alreadyregister);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userFullName=fullName.getText().toString();
                user_email=userEmail.getText().toString();
                user_mobno=userMobNo.getText().toString();
                user_password=userPassword.getText().toString();
                conf_password=confrm_password.getText().toString();

                int flg=0;
                if(userFullName.equalsIgnoreCase("")){
                    fullName.setError("You can't leave this empty !");
                    flg++;
                }
                if(!Pattern.matches("^[\\p{L} .'-]+$",fullName.getText())){
                    fullName.setError("Please enter valid name");
                    flg++;
                }
                if(user_email.equalsIgnoreCase("")){
                    userEmail.setError("You can't leave this empty !");
                    flg++;
                }
                if(!user_email.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")){
                    userEmail.setError("Please enter valid email");
                    flg++;
                }
                if(user_mobno.equalsIgnoreCase("")){
                    userMobNo.setError("You cant't leave this empty !");
                    flg++;
                }
                if(user_mobno.length()!=10){
                    userMobNo.setError("Please enter valid Mobile No");
                    flg++;
                }

                if(user_password.equalsIgnoreCase("")){
                    userPassword.setError("You can't leave this empty !");
                    flg++;
                }
                if(conf_password.equalsIgnoreCase("")){
                    confrm_password.setError("You can't leave this empty !");
                    flg++;
                }
                if(!conf_password.equals(user_password)){
                    confrm_password.setError("Password did not match !");
                    flg++;
                }

                if(user_password.length()<=4){
                    userPassword.setError("Password criteria not matched");
                    flg++;
                }

                if(flg==0){
                    try{
                        // to store user registration details
                        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        String ip =sh.getString("ip","");
                        ur="http://"+ip+":5000/eloc/api/signup";
                        List<NameValuePair> signupdetails=new ArrayList<NameValuePair>();
                        signupdetails.add(new BasicNameValuePair("fullname",userFullName));
                        signupdetails.add(new BasicNameValuePair("email",user_email));
                        signupdetails.add(new BasicNameValuePair("mobno",user_mobno));
                        signupdetails.add(new BasicNameValuePair("password",user_password));

                        JSONObject job =jparser.makeHttpRequest(ur,"POST",signupdetails);
                        String response =job.getString("status");
                        if(response.equalsIgnoreCase("Success")){
                            // error message if the user name or password goes wrong
                            AlertDialog alertDialog = new AlertDialog.Builder(UserRegister.this).create();
                            alertDialog.setTitle("eLoc Information !!!");
                            alertDialog.setMessage("Registration Successfully");
                            alertDialog.setButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent registerintent =new Intent(getApplicationContext(),UserRegister.class);
                                    startActivity(registerintent);
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

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginactivity=new Intent(getApplicationContext(),UserLogin.class);
                startActivity(loginactivity);
            }
        });

    }
}
