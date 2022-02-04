package com.example.sharedpreferencedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    EditText editTextTextEmailAddress, editTextPhone, editTextTextPersonName;
    Button button;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTextEmailAddress = findViewById(R.id.editTextTextEmailAddress);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextTextPersonName = findViewById(R.id.editTextTextPersonName);
        button = findViewById(R.id.button);

        sharedpreferences =
                getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String et_name = editTextTextPersonName.getText().toString();
                String et_email = editTextTextEmailAddress.getText().toString();
                String et_phone = editTextPhone.getText().toString();
                editTextTextEmailAddress.setError(null);
                editTextTextPersonName.setError(null);
                editTextPhone.setError(null);
                if(et_name.isEmpty()) {
                    editTextTextPersonName.setError("Please enter name");
                } else if(et_email.isEmpty()) {
                    editTextTextEmailAddress.setError("Please enter email");
                } else if(et_phone.isEmpty()) {
                    editTextPhone.setError("Please enter name");
                } else {
                    contact = new Contact(et_name, et_email, et_phone);
                    try {
                        String contact_string = ObjectSerializer.serialize(contact);
                        editor.putString("contact", contact_string);
                        editor.commit();

                        Contact contact = (Contact) ObjectSerializer.deserialize(sharedpreferences.getString("contact", null));
                        Log.d ( "serial" , contact.toString ());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}