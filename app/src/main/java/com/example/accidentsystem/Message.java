package com.example.accidentsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Message extends AppCompatActivity {

    private Button sendButton;
    private String meanCar;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private QueryDocumentSnapshot accidentRef1;
    private boolean key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        key = false ;
        sendButton = (Button) findViewById(R.id.MessSendButton);

        if ((boolean)MainActivity.customer.get("state")) {
            sendButton.setVisibility(View.VISIBLE);
            TextView viewReportNumber = (TextView) findViewById(R.id.showReportNumber);



            viewReportNumber.setText("Report number : "+MainActivity.customer.get("currentAccident"));
        }
        else{
            sendButton.setVisibility(View.GONE);}
// get accident
     //       Toast.makeText(Message.this, key+"", Toast.LENGTH_SHORT).show();

         /*   db.collection("Accident")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {

                                for (QueryDocumentSnapshot document : task.getResult()) {



                                    if (document.getId().matches(MainActivity.customer.get("currentAccident").toString())) {

                                        key = true;
                                      //  accidentRef = document;
                                        break;
                                    }
                                }
                            }
                            else {
                                Toast.makeText(Message.this,"Error : "+task.getException(),Toast.LENGTH_SHORT).show();

                            }}});*/


        sendButton.setOnClickListener(new View.OnClickListener() {// 514GOF : 20
    @Override
    public void onClick(View v) {

// final DocumentSnapshot accidentRef = document;


        db.collection("Accident")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            boolean access = false;
                            String test = "";

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                test += document.getId() + " ";
                                if (document.getId().matches((MainActivity.customer.get("currentAccident")).toString())) {
                                    access = true;

                                    key = true;
                                    accidentRef1 = document;
                                        /*Intent intent = new Intent(Message.this, HomePage.class);
                                        startActivity(intent);*/
                                    //Toast.makeText(Message.this,document.getId(),Toast.LENGTH_SHORT).show();






//--------------------------------------------------------------------------------------------------


                                    // print id of accident
                                    //  Toast.makeText(Message.this, accidentRef1.getId(), Toast.LENGTH_SHORT).show();


                                    EditText textArea = (EditText) findViewById(R.id.messageField);
                                    final String description = textArea.getText().toString();

                                    // find the meaning car


                                    List<String> carsCustomer = (List<String>) MainActivity.customer.get("cars");
                                    List<String> carsAccident = (List<String>) accidentRef1.get("cars");

                                    for (String carsCust : carsCustomer) {
                                        for (String carsAcc : carsAccident) {

                                            if (carsCust.equals(carsAcc))
                                                meanCar = carsCust;

                                        }


                                    }


                                    db.runTransaction(new Transaction.Function<Void>() {
                                        @Override
                                        public Void apply(Transaction transaction) throws FirebaseFirestoreException {
                                            // DocumentSnapshot snapshot = transaction.get(accidentRef);
                                            final DocumentReference sfDocRefAcc = db.collection("Accident").document(accidentRef1.getId());


                                            final DocumentReference sfDocRefCust = db.collection("Customer").document(MainActivity.customer.getId());


                                            // Note: this could be done without a transaction
                                            //       by updating the population using FieldValue.increment(

                                            sfDocRefAcc.update("descriptions", FieldValue.arrayUnion(meanCar + " : \n" + description));
                                            sfDocRefCust.update("state", false);
                                            sfDocRefCust.update("currentAccident", "");

                                            // make the accident evaluate is true
boolean test = false;


//--------------------------------------------------------------------------------------------------Convert
                                sfDocRefAcc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();

  //   Toast.makeText(Message.this, "Hello", Toast.LENGTH_SHORT).show();

              ArrayList<String> cars = (ArrayList<String>) document.get("cars");
              ArrayList<String> desc = (ArrayList<String>) document.get("descriptions");


              if (cars.size() == desc.size()){

                  sfDocRefAcc.update("state", true);

              }


                                                        if (document.exists()) {
// Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                                        } else {
 // Log.d(TAG, "No such document");
                                                        }
                                                    } else {
    // Log.d(TAG, "get failed with ", task.getException());
                                                    }
                                                }
                                            });


                                            /*
                                            db.collection("Accident")
                                                    .get()
                                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                            if (task.isSuccessful()) {

                              for (QueryDocumentSnapshot document : task.getResult()) {




                                                                }
                                                            } else {
             Toast.makeText(Message.this,"Error : "+task.getException(),Toast.LENGTH_SHORT).show();

                                                            }
                                                        }
                                                    });
*/


                                            Intent intent = new Intent(Message.this,HomePage.class);
                                            startActivity(intent);
                                            //  Toast.makeText(Message.this, MainActivity.customer.get("state").toString(), Toast.LENGTH_SHORT).show();

                                            //sendButton.setVisibility(View.GONE);




               /*     if ((boolean)MainActivity.customer.get("state")) {
                        sendButton.setVisibility(View.VISIBLE);
                        TextView viewReportNumber = (TextView) findViewById(R.id.showReportNumber);



                        viewReportNumber.setText("Report number : "+MainActivity.customer.get("currentAccident"));
                    }
                    else{
                        sendButton.setVisibility(View.GONE);}
*/



                                            // Success
                                            return null;
                                        }
                                    }).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
//                    Toast.makeText(Message.this, "Success", Toast.LENGTH_SHORT).show();

                                        }
                                    })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    //                          Toast.makeText(Message.this, "Un success", Toast.LENGTH_SHORT).show();

                                                }
                                            });


//--------------------------------------------------------------------------------------------------







                                }
                                if (!access) {

                                    // Toast.makeText(Message.this,(MainActivity.customer.get("currentAccident")).toString()+" Not found "+test,Toast.LENGTH_SHORT).show();

                                }
                            }
                        } else {
                            Toast.makeText(Message.this, "Error : " + task.getException(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });



        if (key) {



        }


    }
});





    }


}
