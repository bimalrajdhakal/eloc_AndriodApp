package com.nexogen.routefinder.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.android.Intents;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.nexogen.routefinder.Manifest;
import com.nexogen.routefinder.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class showQRCocde extends AppCompatActivity {
    AppCompatButton btn_scan,btn_save_qr,btn_pickgallery_qr;
    TextView tv_eloc_id,tv_hno,tv_street,tv_landmark,tv_area,tv_village_town,tv_pincode,tv_district,tv_state,tv_resident_type;
    ImageView image;
    public String qrcodedata;
    public String text2Qr,qrdata;
    String ur;
    String user_email;
    SharedPreferences sh;
    String eloc_id,hno,street,landmark,area,village_town,pincode,lat,lng,district,state,resident_type;
    JSONParser jparser =new JSONParser();
    public static String map_eloc_id,map_hno,map_street,map_landmark,map_area,map_village_town,
            map_pincode,map_district,map_state,map_resident_type;
    public static Double map_lat,map_lng;

    private static final int SELECT_PHOTO = 100;
    //for easy manipulation of the result
    public String barcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_qrcocde);
        // to populate user's eloc data
        tv_resident_type=(TextView)findViewById(R.id.tv_resident_type);
        tv_eloc_id=(TextView)findViewById(R.id.tv_eloc_id);
        tv_hno=(TextView)findViewById(R.id.tv_hno);
        tv_street=(TextView)findViewById(R.id.tv_street);
        tv_landmark=(TextView)findViewById(R.id.tv_landmark);
        tv_area=(TextView)findViewById(R.id.tv_area);
        tv_village_town=(TextView)findViewById(R.id.tv_village_town);
        tv_pincode=(TextView)findViewById(R.id.tv_pincode);
        tv_district=(TextView)findViewById(R.id.tv_district);
        tv_state=(TextView)findViewById(R.id.tv_state);

        // to display qr image
        image = (ImageView) findViewById(R.id.qr_image);
        try{
            sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String ip = sh.getString("ip", "");
            ur = "http://" + ip + ":5000/eloc/api/generateQR";
            user_email=sh.getString("useremail","");
            List<NameValuePair> details = new ArrayList<NameValuePair>();
            details.add(new BasicNameValuePair("useremail",user_email));
            JSONObject job = jparser.makeHttpRequest(ur, "GET", details);
            String response = job.getString("status");
            if (response.equalsIgnoreCase("Success")){
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
                tv_resident_type.setText("Resident Type: "+resident_type);
                tv_eloc_id.setText("eLoc ID: "+eloc_id);
                tv_hno.setText("House/Floor/Door No: "+hno);
                tv_street.setText("Street/Road/Lane: "+street);
                tv_landmark.setText("Landmark: "+landmark);
                tv_area.setText("Area/Locality: "+area);
                tv_village_town.setText("Village/Town/City: "+village_town);
                tv_pincode.setText("PIN Code: "+pincode);
                tv_district.setText("District: "+district);
                tv_state.setText("State: "+state);

            }else{
                Toast.makeText(getApplicationContext(),"Something went wrong !!!",Toast.LENGTH_LONG).show();

            }


        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
        }

        JSONObject json =new JSONObject();
        try{
            json.put("eloc_id",eloc_id);
            json.put("resident",resident_type);
            json.put("hno",hno);
            json.put("street",street);
            json.put("landmark",landmark);
            json.put("area",area);
            json.put("village_town",village_town);
            json.put("pincode",pincode);
            json.put("district",district);
            json.put("state",state);
            json.put("lat",lat);
            json.put("lng",lng);

        }catch (JSONException jsonexception){

            Toast.makeText(getApplicationContext(),jsonexception.toString(),Toast.LENGTH_LONG).show();

        }
        JSONArray jArr = new JSONArray();
        jArr.put(json);
        JSONObject elocObj = new JSONObject();
        try {
            elocObj.put("elocinfo", jArr);
        }catch (JSONException je){
            Toast.makeText(getApplicationContext(),je.toString(),Toast.LENGTH_LONG).show();
        }


        text2Qr=elocObj.toString();


           // creating QR code
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try{
            BitMatrix bitMatrix = multiFormatWriter.encode(text2Qr, BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            image.setImageBitmap(bitmap);
        }
        catch (WriterException e){
            e.printStackTrace();
        }

        btn_scan=(AppCompatButton)findViewById(R.id.btn_scan_qr);
        btn_save_qr=(AppCompatButton)findViewById(R.id.btn_save_qr);
        btn_pickgallery_qr=(AppCompatButton)findViewById(R.id.btn_pick_qr);

        // save qr code image button
        btn_save_qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                image.buildDrawingCache();

                Bitmap bmp = image.getDrawingCache();
                File storageLoc = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES); //context.getExternalFilesDir(null);

                File file = new File(storageLoc, "eloc_"+eloc_id + ".jpg");
                try{
                    FileOutputStream fos = new FileOutputStream(file);
                    bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.close();
                    Toast.makeText(getApplicationContext(), "QR Code Image Saved !!!", Toast.LENGTH_LONG).show();

                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }

            }

        }); // save button method ends here

           // qr image pick from gallery start here
        btn_pickgallery_qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPic = new Intent(Intent.ACTION_PICK);
                photoPic.setType("image/*");
                startActivityForResult(photoPic, SELECT_PHOTO);
            }
        });
           // qr image pick from gallery ends  here



        final Activity activity = this;

        // button scan click here
        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });
    }

   // @Override
    // scanning image which is pick from gallery

//    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent){
//        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
//        switch (requestCode) {
//            case SELECT_PHOTO:
//                if (resultCode == RESULT_OK) {
//                    //doing some uri parsing
//                    Uri selectedImage = imageReturnedIntent.getData();
//                    InputStream imageStream = null;
//                    try {
//                        //getting the image
//                        imageStream = getContentResolver().openInputStream(selectedImage);
//                    } catch (FileNotFoundException e) {
//                        Toast.makeText(getApplicationContext(), "File not found", Toast.LENGTH_SHORT).show();
//                        e.printStackTrace();
//                    }
//                    //decoding bitmap
//                    Bitmap bMap = BitmapFactory.decodeStream(imageStream);
//                    //Intents.Scan.setImageURI(selectedImage);// To display selected image in image view
//                    int[] intArray = new int[bMap.getWidth() * bMap.getHeight()];
//                    // copy pixel data from the Bitmap into the 'intArray' array
//                    bMap.getPixels(intArray, 0, bMap.getWidth(), 0, 0, bMap.getWidth(),
//                            bMap.getHeight());
//
//                    LuminanceSource source = new RGBLuminanceSource(bMap.getWidth(),
//                            bMap.getHeight(), intArray);
//                    BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
//
//                    MultiFormatReader reader = new MultiFormatReader();// use this otherwise
//                    // ChecksumException
//
//                    try {
//                        Hashtable<DecodeHintType, Object> decodeHints = new Hashtable<DecodeHintType, Object>();
//                        decodeHints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
//                        decodeHints.put(DecodeHintType.PURE_BARCODE, Boolean.TRUE);
//
//                        Result result = reader.decode(bitmap, decodeHints);
//                        //*I have created a global string variable by the name of barcode to easily manipulate data across the application*//
//                        barcode =  result.getText().toString();
//
//                        //do something with the results for demo i created a popup dialog
//                        if(barcode!=null){
//                            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                            builder.setTitle("Scan Result");
//                            builder.setIcon(R.mipmap.ic_launcher);
//                            builder.setMessage("" + barcode);
//                            AlertDialog alert1 = builder.create();
//                            alert1.setButton(DialogInterface.BUTTON_POSITIVE, "Done", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    Intent i = new Intent (getBaseContext(),MainActivity.class);
//                                    startActivity(i);
//                                }
//                            });
//
//                            alert1.setCanceledOnTouchOutside(false);
//
//                            alert1.show();
//                        }
//                        else
//                        {
//                            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                            builder.setTitle("Scan Result");
//                            builder.setIcon(R.mipmap.ic_launcher);
//                            builder.setMessage("Nothing found try a different image or try again");
//                            AlertDialog alert1 = builder.create();
//                            alert1.setButton(DialogInterface.BUTTON_POSITIVE, "Done", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    Intent i = new Intent (getBaseContext(),MainActivity.class);
//                                    startActivity(i);
//                                }
//                            });
//
//                            alert1.setCanceledOnTouchOutside(false);
//
//                            alert1.show();
//
//                        }
//                        //the end of do something with the button statement.
//
//                    } catch (NotFoundException e) {
//                        Toast.makeText(getApplicationContext(), "Nothing Found", Toast.LENGTH_SHORT).show();
//                        e.printStackTrace();
//                    } catch (Exception e) {
//                        Toast.makeText(getApplicationContext(), "Wrong Barcode/QR format", Toast.LENGTH_SHORT).show();
//                        e.printStackTrace();
//                    }
//
//                }
//
//        }
//
//    }


    @Override
    // reading QR code image data after scan and redirect to map
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents()==null){
                Toast.makeText(this, "I'm sorry you have cancelled the scanning", Toast.LENGTH_LONG).show();
            }
            else {
                qrcodedata=result.getContents().toString();// getting qr code data
                try {
                    // converting qr code json data into java string
                    JSONObject jobj=new JSONObject(qrcodedata);
                    JSONArray jsonArray=jobj.getJSONArray("elocinfo");
                    JSONObject jsonObject=jsonArray.getJSONObject(0);
                    //jsonObject.getJSONObject("elocinfo");
                    map_eloc_id=jsonObject.getString("eloc_id");
                    map_hno=jsonObject.getString("hno");
                    map_street=jsonObject.getString("street");
                    map_landmark=jsonObject.getString("landmark");
                    map_area=jsonObject.getString("area");
                    map_village_town=jsonObject.getString("village_town");
                    map_pincode=jsonObject.getString("pincode");
                    map_district=jsonObject.getString("district");
                    map_state=jsonObject.getString("state");
                    map_lat=Double.parseDouble(jsonObject.getString("lat"));
                    map_lng=Double.parseDouble(jsonObject.getString("lng"));
                    map_resident_type=jsonObject.getString("resident");

                    // map activity to redirect after getting qr data and render into map
                    Intent scanbyint=new Intent(getApplicationContext(),ElocByScan.class);
                    startActivity(scanbyint);


                }catch (Exception e){
                    Toast.makeText(this, e.toString(),Toast.LENGTH_LONG).show();

                }

            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }// onActivityResult method ends here

}

