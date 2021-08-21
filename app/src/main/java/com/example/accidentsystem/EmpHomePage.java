package com.example.accidentsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class EmpHomePage extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static QueryDocumentSnapshot currentReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_home_page);

        final TextView viewReport = (TextView) findViewById(R.id.areaOfReportNumber);
        viewReport.setMovementMethod(new ScrollingMovementMethod());

        final ArrayList<String> reports = new ArrayList<String>();


// print all accident has not evaluated

        db.collection("Accident")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            boolean access = false;
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                if (((boolean)document.get("state")) && (!((boolean)document.get("evaluated")))){

                                    reports.add(document.getId());
                                }

                            }
                            String a = "";
                            for (String s : reports) {
                                a += s+"\n";
                            }
                            viewReport.setText(a);

                        } else {
                            Toast.makeText(EmpHomePage.this,"Error : "+task.getException(),Toast.LENGTH_SHORT).show();

                        }
                    }
                });



        Button Enter = (Button)findViewById(R.id.enter);

        Enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                EditText reportNum = (EditText) findViewById(R.id.eReportNumber);
                final String number = reportNum.getText().toString().toUpperCase().trim();
                if (!(number.matches(""))){


                    db.collection("Accident")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                         boolean access = true;

                                      if(document.getId().matches(number)){

                                          access = false;
                                          currentReport = document;

                                          Intent intent = new Intent(EmpHomePage.this,Evaluate.class);
                                          startActivity(intent);

                                      }
                                      if (access){
                                          Toast.makeText(EmpHomePage.this,"Report number is wrong",Toast.LENGTH_SHORT).show();

                                      }

                                        }
                                    } else {
                                        Toast.makeText(EmpHomePage.this,"Error : "+task.getException(),Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });








            }
            else{
                Toast.makeText(EmpHomePage.this,"Enter report number",Toast.LENGTH_SHORT).show();
                }
            }

        });

        ImageButton bLogOut = (ImageButton) findViewById(R.id.empLogOut);
        bLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmpHomePage.this, FirstPage.class);
                startActivity(intent);
            }
        });
    }
}
/*   DocumentReference sfDocRefAcc = db.collection("Accident").document(""+number);
                sfDocRefAcc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {

                            DocumentSnapshot document = task.getResult();
                            currentReport = document;

                            Intent intent = new Intent(EmpHomePage.this,Evaluate.class);
                            startActivity(intent);

                            if (document.exists()) {

                                Toast.makeText(EmpHomePage.this,currentReport.getId(),Toast.LENGTH_SHORT).show();

// Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            } else {
                                // Log.d(TAG, "No such document");
                            }
                        } else {
                            // Log.d(TAG, "get failed with ", task.getException());
                            Toast.makeText(EmpHomePage.this,"Report number is wrong",Toast.LENGTH_SHORT).show();

                        }
                    }
                });*/