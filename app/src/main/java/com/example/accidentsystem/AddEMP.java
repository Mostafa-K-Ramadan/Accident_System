package com.example.accidentsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddEMP extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_emp);

        Button bAddEmp = (Button)findViewById(R.id.AddB);

        bAddEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText password = (EditText) findViewById(R.id.AddInpuPass);
                final EditText id = (EditText) findViewById(R.id.AddInputID);


                String sId = id.getText().toString();
                String sPass = password.getText().toString();

                if (!(sId.matches("") || sPass.matches(""))){
                Map<String, Object> employee = new HashMap<>();
                employee.put("password", sPass);


                // Add a new document with a generated ID
                db.collection("Employee").document(sId)
                        .set(employee)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(AddEMP.this, "Account successfully registered", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(AddEMP.this, AdminPage.class);
                                startActivity(intent);
                                password.setText("");
                                id.setText("");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddEMP.this, "Account unsuccessfully register", Toast.LENGTH_SHORT).show();


                            }
                        });
                }
                else {
                    Toast.makeText(AddEMP.this,"ID or Password is empty",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}


