package com.example.accidentsystem;

import androidx.appcompat.app.AppCompatActivity;


import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateNewReport extends AppCompatActivity {

    ImageView photoV;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private StorageReference mStorageRef;
    private List<String> cars = new ArrayList<String>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference storage;
    private String carForThisAccount = "";
    boolean access = false;
    private String car = "";
    private boolean key ;
    private QueryDocumentSnapshot documentTemp;
    private String accidentNumber = generateReportNumber();
    //public static ArrayList<Bitmap> imageFromCamera ;
    private ImageView viewPicture;
    private int count = 0;

    private ArrayList<String> images = new ArrayList<String>();
 //   private ProgressDialog progress;

    private Uri uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_report);

        storage = FirebaseStorage.getInstance().getReference();



        key = false;
        mStorageRef = FirebaseStorage.getInstance().getReference();
        viewPicture = (ImageView) findViewById(R.id.viewImage);
   //     progress = new ProgressDialog(this);



// take a picture code (( Access to camera ))

        ImageButton takePictuer = (ImageButton)findViewById(R.id.takePicture);

        takePictuer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent();
                takePictureIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

                if(takePictureIntent.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE);
                }

            }
        });



/*
        File localFile = null;
        try {
            localFile = File.createTempFile("images", "jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        riversRef.getFile(localFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        // Successfully downloaded data to local file
                        // ...
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle failed download
                // ...
            }
        });

*/





//to add car in accident report searching by car
        Button cAddCar = (Button) findViewById(R.id.addCar);

        cAddCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText getCar = (EditText)findViewById(R.id.car);

                car = getCar.getText().toString();


         if (!(car.equals(""))){
                cars.add(car.toUpperCase());




                db.collection("Customer")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
boolean access2 = true;
                                    for (QueryDocumentSnapshot document : task.getResult()) {


                                        List<String> searchByCar = (List<String>) document.get("cars");

                                        for (String c : searchByCar){

                                            if(c.equalsIgnoreCase(car)){

                                                access2 = false;
                                                access = false;
//to store car for this account has in accident to print it

                                                if (document.getId().equals(MainActivity.customer.getId())){
                                                    access = true;
                                                    carForThisAccount = car;
                                                }

                                                documentTemp = document;




                                                // Toast.makeText(CreateNewReport.this, documentTemp.getId(), Toast.LENGTH_SHORT).show();

                                                final DocumentReference sfDocRef = db.collection("Customer").
                                                        document(documentTemp.getId());

                                                // final DocumentReference sfDocRefAcc = db.collection("Accident").document(accidentNumber);

                                                db.runTransaction(new Transaction.Function<Void>() {
                                                    @Override
                                                    public Void apply(Transaction transaction) throws FirebaseFirestoreException {
                                                        // DocumentSnapshot snapshot = transaction.get(sfDocRef);

                                                        // Note: this could be done without a transaction
                                                        //       by updating the population using FieldValue.increment()
                                                        //


                                                        if (access){
                                                            sfDocRef.update("state", false);
                                                        }
                                                        else{
                                                            sfDocRef.update("state", true);
                                                            sfDocRef.update("currentAccident", accidentNumber);
                                                        }


                                                        sfDocRef.update("accidents", FieldValue.arrayUnion(accidentNumber));


                                                        // Success
                                                        return null;
                                                    }
                                                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(CreateNewReport.this, "Car added", Toast.LENGTH_SHORT).show();

                                                    }
                                                })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Toast.makeText(CreateNewReport.this, "Un success", Toast.LENGTH_SHORT).show();

                                                            }
                                                        });




                                            }
                                        }

                                    }
                                    if (access2){
                 Toast.makeText(CreateNewReport.this,"Not found "+ car +" in system",Toast.LENGTH_SHORT).show();

                                    }
                                } else {
                                    Toast.makeText(CreateNewReport.this,"Error : "+task.getException(),Toast.LENGTH_SHORT).show();

                                }
                            }
                        });




if(key) {


}
                getCar.setText("");
            }
         else {

             Toast.makeText(CreateNewReport.this,"Car field is empty",Toast.LENGTH_SHORT).show();

         }
            }
        });




//to send the report to server
        Button cSend = (Button) findViewById(R.id.send);

            cSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (count >= 1) {

                        final Map<String, Object> accident = new HashMap<>();

                    EditText desc = (EditText) findViewById(R.id.descriptionArea);

                    List<String> descriptions = new ArrayList<String>();
                    List<String> evaluate = new ArrayList<String>();
                    descriptions.add(carForThisAccount + " : \n" + desc.getText().toString());

                    accident.put("evaluated", false);
                    accident.put("state", false);
                    accident.put("descriptions", descriptions);
                    accident.put("evaluate", evaluate);
                    accident.put("cars", cars);
                    accident.put("id", accidentNumber);
                    accident.put("date", new Date());
                    accident.put("images", images);

                    db.collection("Accident").document(accidentNumber)
                            .set(accident)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(CreateNewReport.this, "sent successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(CreateNewReport.this, HomePage.class);
                                    startActivity(intent);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(CreateNewReport.this, "Send unsuccessfully", Toast.LENGTH_SHORT).show();


                                }
                            });

                }
                  else {
                    count++;
                    Toast.makeText(CreateNewReport.this, "Press send again to confirm report", Toast.LENGTH_LONG).show();
                }
                }
            });












        /*takePictuer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(takePictureIntent.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE);
                }

                Uri file = getImageUri(CreateNewReport.this,images);
              //  StorageReference riversRef = mStorageRef.child("images/rivers.jpg");

                mStorageRef.putFile(file)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // Get a URL to the uploaded content
                                Uri downloadUrl = taskSnapshot.getUploadSessionUri();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle unsuccessful uploads
                                // ...
                            }
                        });



            }
        });*/



        Button undo = (Button) findViewById(R.id.undo);

        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (images.size() != 0){

                    Toast.makeText(CreateNewReport.this,"Image removed successfully",Toast.LENGTH_SHORT).show();

                    images.remove(images.size()-1);
                    viewPicture.setImageResource(R.drawable.car_collision);
                }
            }
        });

    }


// get the bitmap
 /*   @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            images = imageBitmap;
           // photoV.setImageBitmap(imageBitmap);
        }}

*/

/*
*
*       ImageButton takePictuer = (ImageButton)findViewById(R.id.takePicture);

        takePictuer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent();
                takePictureIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

                if(takePictureIntent.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE);
                }

            }
        });
        *
        * */

@Override
protected void onActivityResult(int requestCode,int resultCode,Intent data){

    super.onActivityResult(requestCode,resultCode,data);

    if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK /*&& data != null && data.getData() != null*/){
     /*   progress.setMessage("Uploading image ...");
        progress.show();*/

       /* Bundle extras = data.getExtras();
        Bitmap bmp = (Bitmap) extras.get("data");
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte [] byteArray = stream.toByteArray();
*/

        Bundle extras = data.getExtras();
        Bitmap bitmap = (Bitmap) extras.get("data");

        viewPicture.setImageBitmap(bitmap);

        if (bitmap != null) {

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
            byte [] b = stream.toByteArray();

            String encode = Base64.encodeToString(b,Base64.DEFAULT);

            images.add(encode);
            Toast.makeText(CreateNewReport.this,"Image uploaded",Toast.LENGTH_SHORT).show();

        }
      /*  uri = getImageUri(this,imageBitmap);*/

        StorageReference filePath = storage.child("Photo").child("1233321");

      /*  filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
       //    progress.dismiss();

           Toast.makeText(CreateNewReport.this,"Uploading finished",Toast.LENGTH_SHORT).show();

            }
        });*/
    }
else {           Toast.makeText(CreateNewReport.this,"Error",Toast.LENGTH_SHORT).show();
    }
}

/*
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }*/

    public String generateReportNumber(){
        int fDigit = (int)(Math.random() * 10) + 0;
        int sDigit = (int)(Math.random() * 10) + 0;
        int tDigit = (int)(Math.random() * 10) + 0;
        int fhDigit = (int)(Math.random() * 10) + 0;
        int fChar = (int)(Math.random() * 26) + 65;
        int sChar = (int)(Math.random() * 26) + 65;
        int tChar = (int)(Math.random() * 26) + 65;

        String reportNumber = ""+fDigit+sDigit+tDigit+fhDigit+(char)fChar+(char)sChar+(char)tChar;
        return reportNumber;

    }

}
