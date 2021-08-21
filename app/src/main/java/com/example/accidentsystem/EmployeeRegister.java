package com.example.accidentsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class EmployeeRegister extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static QueryDocumentSnapshot employee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_register);


        Button login = (Button)findViewById(R.id.EMPLogin);

    }

    public void bAdmin(View view) {

        Intent intent = new Intent(EmployeeRegister.this,Admin.class);
        startActivity(intent);
    }

    public void bLoginEmp(View view) {

        EditText idEmp = (EditText) findViewById(R.id.EMPeditTextID);
        EditText passwordEmp = (EditText) findViewById(R.id.EMPeditTextPass);

        final String sIdEmp = idEmp.getText().toString();
        final String sPassEmp = passwordEmp.getText().toString();



        if (!(sIdEmp.matches("") || sPassEmp.matches(""))) {



            db.collection("Employee")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                boolean access = false;
                                for (QueryDocumentSnapshot document : task.getResult()) {


                                    if(document.getId().matches(sIdEmp)){
                                        access = true;

                                        if ((document.get("password")).toString().matches(sPassEmp)) {
                                            employee = document;
                                            Intent intent = new Intent(EmployeeRegister.this, EmpHomePage.class);
                                            startActivity(intent);
                                            Toast.makeText(EmployeeRegister.this,sIdEmp,Toast.LENGTH_SHORT).show();


                                        }
                                        else {
                                            Toast.makeText(EmployeeRegister.this,"ID or Password is wrong",Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                    if (!access){

                                        Toast.makeText(EmployeeRegister.this,sIdEmp+" not registered",Toast.LENGTH_SHORT).show();

                                    }
                                }
                            } else {
                                Toast.makeText(EmployeeRegister.this,"Error : "+task.getException(),Toast.LENGTH_SHORT).show();

                            }
                        }
                    });






        }
        else {
            Toast.makeText(EmployeeRegister.this,"ID or Password is empty",Toast.LENGTH_SHORT).show();
        }

    }
}
