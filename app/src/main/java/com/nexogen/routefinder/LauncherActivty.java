package com.nexogen.routefinder;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

/**
 * Created by nexogen on 7/12/17.
 */

public class LauncherActivty extends AppCompatActivity implements View.OnClickListener {

    private AppCompatButton btnShare, btnMore, btnFeedback, btnLike, btnContinue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_launcher);


        init();

        tedPermision();

    }

    private void init() {

        btnShare = findViewById(R.id.btn_share);
        btnMore = findViewById(R.id.btn_more);
        btnFeedback = findViewById(R.id.btn_feedback);
        btnLike = findViewById(R.id.btn_like);
        btnContinue = findViewById(R.id.btn_continue);

        btnShare.setOnClickListener(this);
        btnMore.setOnClickListener(this);
        btnLike.setOnClickListener(this);
        btnFeedback.setOnClickListener(this);
        btnContinue.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_continue:
                startActivity(new Intent(LauncherActivty.this, MainActivity.class));
                break;
            case R.id.btn_more:
                break;
            case R.id.btn_feedback:
                break;
            case R.id.btn_like:
                break;
            case R.id.btn_share:
                break;
        }
    }


    private void tedPermision() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(LauncherActivty.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }


        };
        TedPermission.with(LauncherActivty.this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                .check();

    }
}
