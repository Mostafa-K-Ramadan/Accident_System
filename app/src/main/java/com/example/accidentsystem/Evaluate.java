package com.example.accidentsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Evaluate extends AppCompatActivity {


    String carSelected = "";
    ArrayList<String> evaluate = new ArrayList<String>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<String> cars;
    private String id;
    private int percentCount;
    private  ArrayAdapter<String> arrayAdapter;
    private ImageView view;
    private int count = 0;
    private ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();
    private ArrayList<String> imageString;
    private Spinner spinner;
    private int countSend = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);

        percentCount = 100;

         view = (ImageView) findViewById(R.id.showToEvaluate);
/*
      String s = memory.getString("123321","Empty");

        byte [] imageAsByte = Base64.decode(s.getBytes(),Base64.DEFAULT);
        view.setImageBitmap(BitmapFactory.decodeByteArray(imageAsByte,0,imageAsByte.length));

*/
        imageString = (ArrayList<String>) EmpHomePage.currentReport.get("images");

        ImageButton forward = (ImageButton) findViewById(R.id.forward);
        ImageButton back = (ImageButton) findViewById(R.id.back);

        for(String encode : imageString){

            byte [] imageAsByte = Base64.decode(encode.getBytes(),Base64.DEFAULT);
            bitmaps.add(BitmapFactory.decodeByteArray(imageAsByte,0,imageAsByte.length));

        }

        if (bitmaps != null && !(imageString.size() == 0)) {
            view.setImageBitmap(bitmaps.get(count));

            forward.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (count < bitmaps.size() - 1) {
                        view.setImageBitmap(bitmaps.get(++count));
                    } else {
                        count = 0;
                        view.setImageBitmap(bitmaps.get(count));
                    }

                }
            });

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (count == 0) {
                        count = bitmaps.size() - 1;
                        view.setImageBitmap(bitmaps.get(count));
                    } else {
                        count = count - 1;
                        view.setImageBitmap(bitmaps.get(count));
                    }
                }
            });
        }
        TextView descriptions = (TextView) findViewById(R.id.descriptionToEvaluate);
        descriptions.setMovementMethod(new ScrollingMovementMethod());
        spinner = (Spinner) findViewById(R.id.spinnerCar);


       // ArrayList<String> carFromDB = (ArrayList<String>) EmpHomePage.currentReport.get("cars");
        cars = (ArrayList<String>) EmpHomePage.currentReport.get("cars");
        id = EmpHomePage.currentReport.get("id").toString();

   /*     cars.add("");
        for (String c : carFromDB){
            cars.add(c);
        }*/
 /*  if (cars != null){
       if (!(cars.get(0).matches(""))){
           cars.add(cars.get(0));
           cars.set(0,"");
       }
   }
*/

        ArrayList<String> fDescription = (ArrayList<String>) EmpHomePage.currentReport.get("descriptions");



        String desc = "";

        for (String s : fDescription){
//areaInfo.setText(areaInfo.getText()+"- "+car+"\n\n");
            desc += s+"\n\n";

        }
        descriptions.setText(desc);


        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, cars);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                carSelected = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + carSelected, Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });


    }

    public void bSubmit(View view) {

        EditText percent = (EditText) findViewById(R.id.percentText);
        int per = Integer.parseInt(percent.getText().toString());
        if (per <= percentCount) {
            evaluate.add(carSelected + " : " + per + " %");
            arrayAdapter.remove(carSelected);

            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(arrayAdapter);

            //cars.remove(0);
            percentCount -= per;
        }
        else {
            Toast.makeText(Evaluate.this, "Percentage most be less than or equal to "+percentCount, Toast.LENGTH_LONG).show();
        }

    }

    public void bSend(View view) {

if (countSend >= 1){
        final DocumentReference DocAcc = db.collection("Accident").document(EmpHomePage.currentReport.getId());
        DocAcc.update("evaluate", evaluate);
        DocAcc.update("evaluated",(boolean)true);
        Toast.makeText(Evaluate.this,"Report successfully evaluated",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Evaluate.this, EmpHomePage.class);
        startActivity(intent);
    }
                  else {
    countSend++;
        Toast.makeText(Evaluate.this, "Press send again to confirm evaluate", Toast.LENGTH_LONG).show();
    }
    }

    public void regecjtEvaluate(View view) {

        if (countSend >= 1){
        for(String c : cars){

            evaluate.add(" "+c+" : Not evaluated yet");

        }
        final DocumentReference DocAcc = db.collection("Accident").document(EmpHomePage.currentReport.getId());
        DocAcc.update("id", id + "\n Rejected\n please contact with 0557797373 for more details");
        DocAcc.update("evaluate", evaluate);
        DocAcc.update("evaluated",(boolean)true);
            Toast.makeText(Evaluate.this,"Report successfully rejected",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Evaluate.this, EmpHomePage.class);
        startActivity(intent);

    }
                  else {
        countSend++;
        Toast.makeText(Evaluate.this, "Press send again to confirm reject", Toast.LENGTH_LONG).show();
    }
    }
}
