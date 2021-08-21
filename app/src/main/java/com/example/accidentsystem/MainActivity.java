package com.example.accidentsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<Customer> customers = new ArrayList<>();
    public static Customer current = null;
    public static QueryDocumentSnapshot customer;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String sId;
    private String sPass;
    private EditText password;
    private EditText id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void cLogin(View view) {

    /*    Switch s = (Switch) findViewById(R.id.switch1);

        if (s.isChecked()){
            Intent intent = new Intent(this, EmployeeRegister.class);
            startActivity(intent);
        }
        else {*/

            id = (EditText) findViewById(R.id.inputID);
            password = (EditText) findViewById(R.id.inpuPass);

            sId = id.getText().toString();
            sPass = password.getText().toString();

      if (!(sId.matches("") || sPass.matches(""))) {
       //       int iID = Integer.valueOf(sId);
     //         int iPass = Integer.valueOf(sPass);


          db.collection("Customer")
                  .get()
                  .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                      @Override
                      public void onComplete(@NonNull Task<QuerySnapshot> task) {
                          if (task.isSuccessful()) {
                              boolean access = false;
                              for (QueryDocumentSnapshot document : task.getResult()) {

                                  if(document.getId().matches(sId)){
                                      access = true;

                                           if ((document.get("password")).toString().matches(sPass)) {

                                               customer = document;
                                               id.setText("");
                                               password.setText("");
                                               Intent intent = new Intent(MainActivity.this, HomePage.class);
                                               startActivity(intent);
                                               Toast.makeText(MainActivity.this,sId,Toast.LENGTH_SHORT).show();


                                           }
                                           else {
                                               Toast.makeText(MainActivity.this,"ID or Password is wrong",Toast.LENGTH_SHORT).show();
                                           }

                                  }
                                  if (!access){

                                      Toast.makeText(MainActivity.this,sId+" not registered",Toast.LENGTH_SHORT).show();

                                  }
                              }
                          } else {
                              Toast.makeText(MainActivity.this,"Error : "+task.getException(),Toast.LENGTH_SHORT).show();

                          }
                      }
                  });






          }
          else {
              Toast.makeText(this,"ID or Password is empty",Toast.LENGTH_SHORT).show();
          }

      //  }


    }


    public void cRegister(View view) {
        Intent intent = new Intent(MainActivity.this,register.class);
        startActivity(intent);
    }



}
