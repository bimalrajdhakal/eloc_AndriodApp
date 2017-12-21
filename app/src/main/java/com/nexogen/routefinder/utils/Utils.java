package com.nexogen.routefinder.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Bashir on 01-Aug-16.
 */

public class Utils {
    public static List<String> monthList = Arrays.asList("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");

    public static void callNumber(Context context, String number) {
        try {
            if (isValidPhoneNumber(number)) {
//                Intent intent = new Intent(Intent.ACTION_DIAL);
//                intent.setData(Uri.parse("tel:+91" + number));
//                context.startActivity(intent);
                context.startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + number)));
            } else {
                Toast.makeText(context, "Invalid Number", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getAddress(Context context, double lat, double lng) throws IOException {
        Geocoder gcd = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = gcd.getFromLocation(lat, lng, 1);
        if (addresses.size() > 0) {
            String address = addresses.get(0).getSubLocality();
            if (address == null || address.isEmpty()) {
                address = addresses.get(0).getLocality();
            }
            return address;
        } else {
            return "searching";
        }
    }

    public static void launchGoogleMaps(Context context, String address) {
        try {
            Intent searchAddress = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + address));
            context.startActivity(searchAddress);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void launchGoogleNavigation(Context context, double mLat, double mLong) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + mLat + "," + mLong));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static String readFileFromAssets(Context context, String fileName) throws IOException {
        StringBuilder buf = new StringBuilder();
        InputStream json = context.getAssets().open(fileName);
        BufferedReader in =
                new BufferedReader(new InputStreamReader(json, "UTF-8"));
        String str;

        while ((str = in.readLine()) != null) {
            buf.append(str);
        }
        in.close();
        return buf.toString();
    }

    public static boolean isValidText(String text) {
        if (text != null && !TextUtils.isEmpty(text.trim())) {
            return true;
        }
        return false;
    }

    public static boolean isValidPhoneNumber(String number) {
        if (!isValidText(number) || number.length() != 10) {
            return false;
        }
        return (android.util.Patterns.PHONE.matcher(number).matches());
    }


    public static String getTrimmedDistance(String distance) {
        if (isValidText(distance)) {
            if (distance.indexOf(".") != -1) {
                distance = distance.substring(0, distance.lastIndexOf(".") + 3);
            }
            return distance + " KM";
        } else {
            return "";
        }
    }

    public static void rateApplication(Context context) {

        String appPackageName = context.getPackageName();
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    public static void androidShare(Context context) {

        String appPackageName = context.getPackageName();
        try {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            String data = "https://play.google.com/store/apps/details?id=" + appPackageName;
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Hey there I'm using DalmiaMedicare\n");
            sendIntent.putExtra(Intent.EXTRA_TEXT, data);
            sendIntent.setType("text/plain");
            context.startActivity(sendIntent);
        } catch (Exception e) {
        }
    }

    public static void shareLab(Context context, String labId) {

//        String url = "http://52.66.104.87/lab/" + labId;
        String url = "https://www.dalmiamedicare.com/lab/" + labId;
        try {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Explore this Lab\n");
            sendIntent.putExtra(Intent.EXTRA_TEXT, url);
            sendIntent.setType("text/plain");
            context.startActivity(sendIntent);
        } catch (Exception e) {
        }
    }

    public static void TrackEvent(Tracker tracker, String category, String action, String label) {
        tracker.send(new HitBuilders.EventBuilder().setCategory(category).setAction(action).setLabel(label).build());
    }

    public static void TrackEvent(Tracker tracker, Map<String, String> events) {
        tracker.send(events);
    }

    public static void TrackTiming(Tracker tracker, String category, String var, long time, String label) {
        tracker.send(new HitBuilders.TimingBuilder()
                .setCategory(category)
                .setVariable(var)
                .setValue(time / 10)
                .build());
    }

    public static String getFormattedDate(int date, int month, int year) {
        String dateString = "";
        try {
            dateString = dateString + date + "-" + monthList.get(month) + "-" + year;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateString;
    }

    public static boolean isValidCityName(String name) {
        Pattern pattern_ = Pattern.compile("^[a-zA-Z][a-zA-Z\\s-]+[a-zA-Z]$");
        Matcher matcher = pattern_.matcher(name);
        return matcher.matches();
    }

    public static boolean isValidName(String name) {
        Pattern pattern_ = Pattern.compile("^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$");
        Matcher matcher = pattern_.matcher(name);
//        return matcher.matches();
        return isValidText(name);
    }


    public static boolean isValidBloodGroup(String name) {
        Pattern pattern_ = Pattern.compile("^(A|B|AB|O)[+-]$");
        Matcher matcher = pattern_.matcher(name);
        return matcher.matches();
    }

    public static boolean isValidEmail(String email) {
        return (isValidText(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }


    public static void openSettings(Activity context) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        context.startActivity(intent);
    }

    public static String getFormattedDate(long timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return sdf.format(new Date(timeStamp));
    }

    public static long getMillisecondsFromDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long timeInMilliseconds = 0;
        try {
            Date mDate = sdf.parse(date);
            timeInMilliseconds = mDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeInMilliseconds;
    }

    public static int getDeviceWidth(Activity activity) {

        int screenWidth;
        if (Build.VERSION.SDK_INT >= 17) {
            Point size = new Point();
            try {
                activity.getWindowManager().getDefaultDisplay().getRealSize(size);
                screenWidth = size.x;
            } catch (NoSuchMethodError e) {
                screenWidth = activity.getWindowManager().getDefaultDisplay().getWidth();
            }

        } else {
            DisplayMetrics metrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
            screenWidth = metrics.widthPixels;
        }
        return screenWidth;
    }


    public static float dp2px(Resources resources, float dp) {
        final float scale = resources.getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

    public static float sp2px(Resources resources, float sp) {
        final float scale = resources.getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

    public static boolean isValidPassword(final String password) {
        Pattern pattern;
        Matcher matcher;
//        final String PASSWORD_PATTERN = "^[^<>'\\\\\"/;`:|~(){}[/] %]*$";

        final String PASSWORD_PATTERN = "[a-zA-Z0-9!@#$&*_\\-=+?]+$";

//        final String PASSWORD_PATTERN1 =
//                "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }


    public static String getPasswordMessage(String password) {
        if (!isValidText(password))
            return "Enter valid password";

        if (password.length() > 20)
            return "Password exceeds 20 characters";

        if (password.length() < 6)
            return "Password less than 6 characters";

        return null;
    }

   /* public static boolean isValidName(String name){
        String regex = "^\\p{L}+[\\p{L}\\p{Z}\\p{P}]{0,}";
        Pattern pattern_ = Pattern.compile(regex);
        Matcher matcher = pattern_.matcher(name);
        return matcher.matches();
    }*/

    public static boolean isNumberOnly(String text) {
        final String PATTERN = "[0-9]*$";
        Pattern pattern_ = Pattern.compile(PATTERN);
        Matcher matcher = pattern_.matcher(text);
        return matcher.matches();
    }

    public static String extractOTP(String message) {
        String PATTERN = "(|^)\\d{4}";
        Pattern p = Pattern.compile(PATTERN);
        Matcher m = p.matcher(message);
        String otp = null;
        if (m.find()) {
            otp = m.group(0);
        }
        return otp;
    }

    public static long dateToTimestamp(String date) {
        long timeStamp = 0;
        if (date != null && !date.isEmpty()) {
            String[] splitDob = date.split("-");
            if (splitDob.length == 3) {
                int day = Integer.valueOf(splitDob[0]);
                int month = Utils.monthList.indexOf(splitDob[1]);
                int year = Integer.valueOf(splitDob[2]);
                Calendar mCalendar = Calendar.getInstance();
                mCalendar.set(Calendar.DAY_OF_MONTH, day);
                mCalendar.set(Calendar.MONTH, month);
                mCalendar.set(Calendar.YEAR, year);
                timeStamp = mCalendar.getTimeInMillis();
            }
        }
        return timeStamp;
    }

    public static String getReadableDate(String date) {
        String readableDate = null;
        if (date != null && !date.isEmpty()) {
            String[] actualDob =/*splitDob[0]*/date.split("-");
            if (actualDob.length == 3) {
                int day = Integer.valueOf(actualDob[0]);
                int month = Integer.valueOf(actualDob[1]) - 1;
                int year = Integer.valueOf(actualDob[2]);
                readableDate = Utils.getFormattedDate(day, month, year);
            }
        }
        return readableDate;
    }
}
