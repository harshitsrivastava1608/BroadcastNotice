package com.example.project4.NewsFeed;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.project4.R;
import com.squareup.picasso.Picasso;

public class NoticeInFull extends AppCompatActivity {
private ImageView imageView;
private String image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_in_full);
        imageView=findViewById(R.id.fullNotice);
        image=getIntent().getStringExtra("image");
        try {
            Picasso.get().load(image).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}