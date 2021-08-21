package com.example.accidentsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EmployeeChoose extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_choose);

        Button admin = (Button) findViewById(R.id.goAdmin);
admin.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(EmployeeChoose.this,Admin.class);
        startActivity(intent);
    }
});
        Button emp = (Button) findViewById(R.id.goEmp);
        emp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmployeeChoose.this,EmployeeRegister.class);
                startActivity(intent);
            }
        });
    }
}
