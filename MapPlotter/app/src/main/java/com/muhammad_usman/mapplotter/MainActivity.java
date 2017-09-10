package com.muhammad_usman.mapplotter;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.icu.util.Calendar;
import android.net.ParseException;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONObject;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    String body;
    Long milli;
    TextView current_date;
    ImageView back, forward;
    LocalDate localDate;
    ArrayList<sms_data> data;
    MapView mapView;
    GoogleMap googleMap;
    LatLngBounds.Builder builder = new LatLngBounds.Builder();
    ProgressDialog pDialog;
    AlertDialogManager alert = new AlertDialogManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        current_date = (TextView) findViewById(R.id.current_duration);
        back = (ImageView) findViewById(R.id.imageView9);
        forward = (ImageView) findViewById(R.id.imageView10);
        mapView = (MapView) findViewById(R.id.mapView);
        runtime_permission();
        pDialog = ProgressDialog.show(MainActivity.this, "Please Wait...", "Loading...", true);


        mapView.onCreate(savedInstanceState);

        mapView.onResume(); // needed to get the map to display immediately



        localDate = new LocalDate();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
        DateTime day = localDate.toDateTimeAtStartOfDay().plusHours(5);


        String date = new LocalDate(day).toString(formatter);
//        java.util.Calendar calendar =  java.util.Calendar.getInstance();
//        TimeZone timezone = TimeZone.getTimeZone("GMT+05:00");
//        calendar.setTimeZone(timezone);
//        LocalDate d = formatter.parseLocalDate(date);
//        calendar.set(d.getYear(),d.getMonthOfYear()-1,d.getDayOfMonth(),11,59,0);
//
//        long day1 = calendar.getTimeInMillis();
        DateTime day1 = localDate.plusDays(1).toDateTimeAtStartOfDay().plusHours(5);

        current_date.setText(date);
        Load_data(day.getMillis(),day1.getMillis());

    }


    public void post(View v) {


        localDate = localDate.plusDays(1);
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
        DateTime day = localDate.toDateTimeAtStartOfDay().plusHours(5);


        String date = new LocalDate(day).toString(formatter);
//        java.util.Calendar calendar =  java.util.Calendar.getInstance();
//        TimeZone timezone = TimeZone.getTimeZone("GMT+05:00");
//        calendar.setTimeZone(timezone);
//        LocalDate d = formatter.parseLocalDate(date);
//        calendar.set(d.getYear(),d.getMonthOfYear()-1,d.getDayOfMonth(),11,59,0);
//
//        long day1 = calendar.getTimeInMillis();
        DateTime day1 = localDate.plusDays(1).toDateTimeAtStartOfDay().plusHours(5);

        current_date.setText(date);
        Load_data(day.getMillis(),day1.getMillis());

    }

    public void pre(View v) {

        localDate = localDate.minusDays(1);
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
        DateTime day = localDate.toDateTimeAtStartOfDay().plusHours(5);

        String date = new LocalDate(day).toString(formatter);
//        java.util.Calendar calendar =  java.util.Calendar.getInstance();
//        TimeZone timezone = TimeZone.getTimeZone("GMT+05:00");
//        calendar.setTimeZone(timezone);
//        LocalDate d = formatter.parseLocalDate(date);
//        calendar.set(d.getYear(),d.getMonthOfYear()-1,d.getDayOfMonth(),11,59,0);
//
//        long day1 = calendar.getTimeInMillis();
        DateTime day1 = localDate.plusDays(1).toDateTimeAtStartOfDay().plusHours(5);

        current_date.setText(date);
        Load_data(day.getMillis(),day1.getMillis());

    }


    public void Setting(View v) {


        Intent intent = new Intent(this, Setting.class);
        startActivity(intent);
        finish();

    }

    public void Load_data(Long a, Long b) {
        InternetConnectionStatus   Ics = new InternetConnectionStatus(getApplicationContext());
        Boolean isInternetAvailable=false;
        // Check if Internet is available
        isInternetAvailable = Ics.isConnectingToInternet();
        if (!isInternetAvailable) {
            // Internet Connection is not available
            alert.showAlertDialog(this, "Internet Connection Error",
                    "Please connect to  Internet", false);
            return;
        }
        try {
            if (!Ics.isConnected()) {
                alert.showAlertDialog(this, "Internet Connection Error",
                        "Please connect to working Internet connection", false);
                return;
            }

        }catch (Exception e) {
        }
        data=new ArrayList<>();
        data.clear();
        Uri mSmsinboxQueryUri = Uri.parse("content://sms/inbox");
        String[] projection = {"address", "body", "date"};
        String phoneNumber = Preferences_Manager.getInstance().Data_String("phone", getApplicationContext());

        Cursor cursor1 = getContentResolver().query(mSmsinboxQueryUri,
                projection,
                "address = ?",
                new String[]{phoneNumber},
                null);


        if (cursor1 != null ) {
            while (cursor1.moveToNext()) {
                body = cursor1.getString(cursor1.getColumnIndex("body"));
                milli = (cursor1.getLong(cursor1.getColumnIndexOrThrow("date"))+18000000)/1000L;
                Toast.makeText(this, body, Toast.LENGTH_LONG).show();

                if (milli >= (a / 1000L) && milli < (b / 1000L)) {
                    String[] str_array = body.split(",");
                    double lat = Double.parseDouble(str_array[0]);
                    double lng = Double.parseDouble(str_array[1]);
                    data.add(new sms_data(lat, lng));
                    // Toast.makeText(this, str_array[0]+")"+str_array[1], Toast.LENGTH_LONG).show();
                }

            }
            if(data.size()>0) {

                load_map(data);
                pDialog.hide();


            }
            else {
                pDialog.hide();

                alert.showAlertDialog(this, "No Data Available!!",
                        ",There no data for this date!! ", false);
            }
            }


        else {
            pDialog.hide();
            android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(this).create();

            // Setting Dialog Title
            alertDialog.setTitle("No data available for this number!!");

            // Setting Dialog Message
            alertDialog.setMessage("Please try again later.");


            alertDialog.setIcon( R.drawable.im );


            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    moveTaskToBack(true);
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(1);
                }
            });

            // Showing Alert Message
            alertDialog.show();


        }
    }











    public void load_map(final ArrayList<sms_data> list) {
        try {
            MapsInitializer.initialize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                googleMap.setTrafficEnabled(true);
                googleMap.setIndoorEnabled(true);
                googleMap.setBuildingsEnabled(true);

                googleMap.getUiSettings().setZoomControlsEnabled(true);


                pDialog.hide();
                for (int i = 0; i < list.size(); i++) {
                    double lat, lng;
                    sms_data temp = list.get(i);
                    lat = roundoff(temp.lat);
                    lng = roundoff(temp.lng);


                    MarkerOptions markerOptions = new MarkerOptions();

                    LatLng latLng = new LatLng(lat, lng);
                    markerOptions.position(latLng);
                    //  markerOptions.title();
                    if (i == 0) {
                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                        markerOptions.title("Today Start");

                    } else if (i == list.size() - 1) {
                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                        markerOptions.title("Last Location");
                    } else {
                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                    }
                    googleMap.addMarker(markerOptions);
                    builder.include(latLng);

                }

                LatLngBounds bounds = builder.build();
                googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 20));
            }


        });
    }



    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
    private void runtime_permission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> permissionsNeeded = new ArrayList<String>();

            final List<String> permissionsList = new ArrayList<String>();
            if (!addPermission(permissionsList, android.Manifest.permission.ACCESS_FINE_LOCATION))
                permissionsNeeded.add("GPS");
            if (!addPermission(permissionsList, android.Manifest.permission.READ_CONTACTS))
                permissionsNeeded.add("Read Contacts");


            if (permissionsList.size() > 0) {
                if (permissionsNeeded.size() > 0) {
                    // Need Rationale
                    String message = "You need to grant access to " + permissionsNeeded.get(0);
                    for (int i = 1; i < permissionsNeeded.size(); i++)
                        message = message + ", " + permissionsNeeded.get(i);
                    showMessageOKCancel(message, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                                        REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                            }
                        }
                    });
                    return;
                }
                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                        REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                return;
            }

        }
    }

    private boolean addPermission(List<String> permissionsList, String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(permission);
                if (!shouldShowRequestPermissionRationale(permission))
                    return false;
            }
        }
        return true;

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
    public  double roundoff(double a)
    {
        double t = a;
        DecimalFormat df = new DecimalFormat("#.##");
        t = Double.valueOf(df.format(t));
        return  t;
    }


}