package com.example.accidentsystem;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class HomePage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


// button to move to activity history
        ImageButton cMess = (ImageButton)findViewById(R.id.smsButton);

        cMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this,Message.class);
                startActivity(intent);
            }
        });





// button to move to activity history
    ImageButton cHistory = (ImageButton)findViewById(R.id.histButton);

        cHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this,history.class);
                startActivity(intent);
            }
        });
    // to Button to move to activity create new report
    ImageButton cAdd = (ImageButton)findViewById(R.id.addButton);

        cAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                FirebaseFirestore db = FirebaseFirestore.getInstance();
                final DocumentReference sfDocRef = db.collection("Customer").document(MainActivity.customer.getId().toString());

                db.runTransaction(new Transaction.Function<Void>() {
                    @Override
                    public Void apply(Transaction transaction) throws FirebaseFirestoreException {
                       // DocumentSnapshot snapshot = transaction.get(sfDocRef);

                        // Note: this could be done without a transaction
                        //       by updating the population using FieldValue.increment()
                       // double newPopulation = snapshot.getDouble("population") + 1;
                       //  qwes
                        //Map<String, Object> addReport = new HashMap<>();
                        //addReport.put("report_number","15951");
                        //transaction.set(sfDocRef,addReport);

                        // Success
                        return null;
                    }
                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(HomePage.this,"Success",Toast.LENGTH_SHORT).show();

                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(HomePage.this,"Unsuccess",Toast.LENGTH_SHORT).show();

                            }
                        });









                Intent intent = new Intent(HomePage.this,CreateNewReport.class);
                startActivity(intent);
            }
        });
        // to Button to move to activity information
        ImageButton cInfo = (ImageButton)findViewById(R.id.infoButton);

        cInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this,Info.class);
                startActivity(intent);
            }
        });



        ImageButton bLogOut = (ImageButton) findViewById(R.id.logOut);
        bLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, FirstPage.class);
                startActivity(intent);
            }
        });

    }

}
