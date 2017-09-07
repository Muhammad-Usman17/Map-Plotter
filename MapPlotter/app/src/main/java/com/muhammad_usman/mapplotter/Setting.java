package com.muhammad_usman.mapplotter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Setting extends AppCompatActivity {
EditText Phone_number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Phone_number =(EditText)findViewById(R.id.editText2);
        Phone_number.setText(Preferences_Manager.getInstance().Data_String("phone",getApplicationContext()));
    }

    public void save(View v)
    {
        Preferences_Manager.getInstance().Add_String("phone",Phone_number.getText().toString(),getApplicationContext());
        Toast.makeText(getApplicationContext(),"Successfully Updated",Toast.LENGTH_SHORT).show();
        Intent intent =new Intent (this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
