package com.example.accidentsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Transaction;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class history extends AppCompatActivity implements HistoryAdapter.OnAccidentListener {

    private ArrayList<Accident> accidentList;
    private RecyclerView listView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private HistoryAdapter hAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        accidentList = new ArrayList<Accident>();
        hAdapter = new HistoryAdapter(accidentList,this);

        listView = (RecyclerView) findViewById(R.id.listView);
        listView.setHasFixedSize(true);
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(hAdapter);

ArrayList<String> accidents = (ArrayList<String>) MainActivity.customer.get("accidents");

//move on all accident

   for (String accident : accidents){

       final DocumentReference sfDocRefAcc = db.collection("Accident").document(accident);

       sfDocRefAcc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
           @Override
           public void onComplete(@NonNull Task<DocumentSnapshot> task) {

               if (task.isSuccessful()) {
                   DocumentSnapshot document = task.getResult();

   // check the evaluate state

   if ((boolean) document.get("evaluated")){

Accident acc = document.toObject(Accident.class);
accidentList.add(acc);

hAdapter.notifyDataSetChanged();

   }



               }
           }
       });


   }

/* List<String> searchByCar = (List<String>) doc.get("cars");
//check for all cars in one accident
    for (String carAccident : searchByCar){

//check for all cars in customer
        for (String carCustomer : cars){

        if(carCustomer.equalsIgnoreCase(carAccident)) {




        }

        }
    }*/



  //      listView.







    }

    @Override
    public void onAccidentClick(int position) {
        Accident a = accidentList.get(position);
        Intent intent = new Intent (history.this,HistoryDetail.class);


        String s = "";

        s += " Report Number : "+a.getId()+"\n\n";
        s += " Date : "+a.getDate()+"\n\n";
        s += " ____________________________________________\n";
        ArrayList<String> evaluates = a.getEvaluate();
        s+= " Evaluated : \n\n";
        for (String ev : evaluates) {
            s += " "+ev + "\n";
        }
        s += " ____________________________________________\n";
        s+= " Descriptions : \n\n";
        ArrayList<String> desc = a.getDescriptions();
            for (String ds : desc) {
                s += " "+ds + "\n\n";
            }

            ArrayList<String> imageForThisAccident = a.getImages();


       intent.putExtra("image",imageForThisAccident);
       intent.putExtra("Details",s);
      // Log.d("mmbbzz",a.getDate()+"");
       startActivity(intent);
    }
}
