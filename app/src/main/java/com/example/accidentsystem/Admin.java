package com.example.accidentsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Admin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Button Admin = (Button)findViewById(R.id.ABlogin);

        Admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText id = (EditText)findViewById(R.id.AInputID);
                EditText password = (EditText)findViewById(R.id.aInputPass);

                String sId = id.getText().toString();
                String sPass = password.getText().toString();
if (!sId.matches("") || !sPass.matches("")){
                if((sId.equalsIgnoreCase("admin"))&& (sPass.equalsIgnoreCase("admin"))){
                    Intent intent = new Intent(Admin.this,AdminPage.class);
                    startActivity(intent);

                }
else {

                    Toast.makeText(Admin.this,"ID or password wrong",Toast.LENGTH_SHORT).show();

                }
}
else {
    Toast.makeText(Admin.this,"ID or password is empty",Toast.LENGTH_SHORT).show();

}
            }
        });
    }
}
