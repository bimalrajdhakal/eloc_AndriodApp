package com.nexogen.routefinder.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.WindowManager;
import android.widget.ImageView;

import com.nexogen.routefinder.R;

import static com.nexogen.routefinder.utils.Global.placesImages;

public class CustomMarkerImage {
    public static ImageView imgloc;



    @SuppressLint({"InflateParams", "WrongConstant"})
    private static Bitmap createClusterBitmap(Context context, int positions) {
        View cluster = LayoutInflater.from(context).inflate(R.layout.marker_image, null);
        imgloc = (ImageView) cluster.findViewById(R.id.markerImageView);
        imgloc.setImageResource(placesImages[positions]);
        cluster.measure(MeasureSpec.makeMeasureSpec(0, 0), MeasureSpec.makeMeasureSpec(0, 0));
        cluster.layout(0, 0, cluster.getMeasuredWidth(), cluster.getMeasuredHeight());
        Bitmap clusterBitmap = Bitmap.createBitmap(cluster.getMeasuredWidth(), cluster.getMeasuredHeight(), Config.ARGB_8888);
        cluster.draw(new Canvas(clusterBitmap));
        cluster.invalidate();
        return clusterBitmap;
    }

    public static Bitmap getMarkerIcon(Context context, int positions) {
        int width = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
        return Bitmap.createScaledBitmap(createClusterBitmap(context,positions), width / 12, width / 8, false);
    }
}
