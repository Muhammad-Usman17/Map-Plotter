package com.muhammad_usman.mapplotter;

import android.content.Intent;
import android.database.Cursor;
import android.net.ParseException;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {
 String body;
    TextView current_date;
    ImageView back,forward;
    LocalDate localDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        current_date=(TextView)findViewById(R.id.current_duration);
        back=(ImageView)findViewById(R.id.imageView9);
        forward=(ImageView)findViewById(R.id.imageView10);

        localDate=new LocalDate();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
        DateTime day = localDate.toDateTimeAtStartOfDay();
        String desde = new LocalDate(day).toString(formatter);
       // Load_data();
        current_date.setText(desde);


        Uri mSmsinboxQueryUri = Uri.parse("content://sms/inbox");
        String[] projection = {"address", "body"};
        String phoneNumber = Preferences_Manager.getInstance().Data_String("phone",getApplicationContext());

        Cursor cursor1 = getContentResolver().query(mSmsinboxQueryUri,
                projection,
                "address = ?",
                new String[] {phoneNumber},
                null);


                    if (cursor1 != null ) {
                        while (cursor1.moveToNext())
                        {
                            body= cursor1.getString( cursor1.getColumnIndex("body") );
                            Toast.makeText(this,body,Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                        Toast.makeText(this,"empty cusor",Toast.LENGTH_LONG).show();

    }

    public void post(View v)
    {


            localDate=localDate.plusDays(1);
            DateTimeFormatter formatterFecha = DateTimeFormat.forPattern("dd/MM/yyyy");
            DateTime primerDiaDelMes = localDate.toDateTimeAtStartOfDay();
            String date = new LocalDate(primerDiaDelMes).toString(formatterFecha);
            current_date.setText(date);
           // Load_data();


    }

    public void pre(View v)
    {

            localDate=localDate.minusDays(1);
            DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
            DateTime day = localDate.toDateTimeAtStartOfDay();
            String date = new LocalDate(day).toString(formatter);
            current_date.setText(date);
            //Load_data();

        }


    public void Setting(View v)
    {


        Intent intent=new Intent(this,Setting.class);
        startActivity(intent);

    }

}
