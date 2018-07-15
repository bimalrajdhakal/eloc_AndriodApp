package com.nexogen.routefinder.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nexogen.routefinder.R;

public class CustomToast_nearby {
    Context context;

    public CustomToast_nearby(Context context) {
        this.context = context;
    }

    @SuppressLint({"InflateParams"})
    public static void Show(Context context, String message, boolean flag) {
        View customToastroot = LayoutInflater.from(context).inflate(R.layout.custom_toast_nearby, null);
        ImageView imageView = (ImageView) customToastroot.findViewById(R.id.toastImageView);
        ((TextView) customToastroot.findViewById(R.id.toastTextView)).setText(message);
        if (flag) {
            imageView.setImageResource(R.drawable.success);
        } else {
            imageView.setImageResource(R.drawable.fail);
        }
        Toast customtoast = new Toast(context);
        customtoast.setView(customToastroot);
        customtoast.setDuration(Toast.LENGTH_LONG);
        customtoast.show();
    }
}
