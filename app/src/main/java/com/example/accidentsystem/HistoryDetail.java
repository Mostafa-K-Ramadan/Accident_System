package com.example.accidentsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Base64;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class HistoryDetail extends AppCompatActivity {

    private ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();
    private ImageView view;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail);

        Bundle extras = getIntent().getExtras();
        String detail = extras.getString("Details");
        ArrayList<String> imageString = extras.getStringArrayList("image");


        view = (ImageView) findViewById(R.id.showToHistory);
/*
      String s = memory.getString("123321","Empty");

        byte [] imageAsByte = Base64.decode(s.getBytes(),Base64.DEFAULT);
        view.setImageBitmap(BitmapFactory.decodeByteArray(imageAsByte,0,imageAsByte.length));

*/

        ImageButton forward = (ImageButton) findViewById(R.id.hForward);
        ImageButton back = (ImageButton) findViewById(R.id.hBack);

        for (String encode : imageString) {

            byte[] imageAsByte = Base64.decode(encode.getBytes(), Base64.DEFAULT);
            bitmaps.add(BitmapFactory.decodeByteArray(imageAsByte, 0, imageAsByte.length));

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
        TextView details = (TextView) findViewById(R.id.detailDiscription);
        details.setMovementMethod(new ScrollingMovementMethod());
        details.setText(detail);
    }
}
