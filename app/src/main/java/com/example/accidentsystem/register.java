package com.example.accidentsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class register extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String sId;
    private String sConfPassword;
    private String sPass;
    private EditText password;
    private EditText confPassword;
    private EditText id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        Button cRegister = (Button)findViewById(R.id.bRegister);

        cRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password = (EditText)findViewById(R.id.rInputPass);
                confPassword = (EditText)findViewById(R.id.rInputConPass);
                id = (EditText)findViewById(R.id.rInputID);

               sId = id.getText().toString();
               sConfPassword = confPassword.getText().toString();
               sPass = password.getText().toString();




if (!(sId.matches("") || sPass.matches(""))){


    db.collection("absher")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {

                        boolean access = true;
                        for (QueryDocumentSnapshot document : task.getResult()) {


                            if(document.getId().matches(sId)){
access = false;
                                List<String> cars = (List<String>) document.get("cars");
                                String name = document.get("name").toString();

                                final Map<String, Object> customer = new HashMap<>();
                                customer.put("name",name);
                                customer.put("cars",cars);
                                customer.put("password",sPass);
                                customer.put("state",false);
                                List<String> accidents = new ArrayList<>();
                                customer.put("currentAccident","");
                                customer.put("accidents",accidents);


                                if (sConfPassword.matches(sPass)){
                                    // Add a new document with a generated ID
                                    db.collection("Customer").document(sId)
                                            .set(customer)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {

                                                    id.setText("");
                                                    confPassword.setText("");
                                                    password.setText("");

                                                    Toast.makeText(register.this,"Account successfully registered",Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(register.this,MainActivity.class);
                                                    startActivity(intent);
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(register.this,"Account unsuccessfully register",Toast.LENGTH_SHORT).show();


                                                }
                                            });
                                }
                                else {

                                    password.setText("");
                                    confPassword.setText("");
                                    Toast.makeText(register.this,"Password isn't match with confirm password",Toast.LENGTH_SHORT).show();

                                }






                                break;

                            }
                        }
                        if (access)
                        {
                            Toast.makeText(register.this,"You don't have account into ABSHER",Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(register.this,"Error : "+task.getException(),Toast.LENGTH_SHORT).show();

                    }
                }
            });






}
else {
    Toast.makeText(register.this,"ID or Password is empty",Toast.LENGTH_SHORT).show();
}










            }
        });


    }
}













/*
                if (!(sId.matches("") && sPass.matches(""))) {
                    MainActivity.customers.add(new Customer(Integer.valueOf(sId), Integer.valueOf(sPass)));
                    startActivity(new Intent(register.this,MainActivity.class));
                }
                else {
                    Toast.makeText(register.this,"ID or Password is empty",Toast.LENGTH_SHORT).show();
                }*/
