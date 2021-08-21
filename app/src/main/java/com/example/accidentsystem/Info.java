package com.example.accidentsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Transaction;

import java.util.List;

public class Info extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
         DocumentReference sfDocRef = db.collection("Customer").document(MainActivity.customer.getId());

         TextView showId = (TextView) findViewById(R.id.showIdInInfo);
         showId.setText("ID : "+sfDocRef.getId());

// to get the data for current account
        sfDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        TextView areaInfo = (TextView) findViewById(R.id.textInfo);
         //String textInfo = document.get("name").toString();


                        areaInfo.setText(areaInfo.getText()+"Name : "+(document.get("name").toString()+"\n\n"));

                       // textInfo += "\n";
                        areaInfo.setText(areaInfo.getText()+"Cars : \n\n");

                        List<String> cars = (List<String>) document.get("cars");
         for (String car : cars){
             areaInfo.setText(areaInfo.getText()+"- "+car+"\n\n");

         }


                    } else {


                    }
                } else {


                }
            }
        });


    }
}
