package com.muhammad_usman.mapplotter;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
 String body;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Uri mSmsinboxQueryUri = Uri.parse("content://sms/inbox");
        String[] projection = {"address", "body"};
        String phoneNumber = "+923345409670";

        Cursor cursor1 = getContentResolver().query(mSmsinboxQueryUri,
                projection,
                "address = ?",
                new String[] {phoneNumber},
                "date DESC LIMIT 1");

        if (cursor1 != null && cursor1.moveToFirst()) {
            body = cursor1.getString(cursor1.getColumnIndex("body"));

        }
        Toast.makeText(this,body,Toast.LENGTH_LONG).show();
    }
}
