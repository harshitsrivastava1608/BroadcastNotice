package com.example.project4.NewsFeed;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project4.Admins.AddAdmins;
import com.example.project4.Admins.AdminAdapter;
import com.example.project4.Admins.AdminData;
import com.example.project4.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UpdateNews extends AppCompatActivity {
FloatingActionButton fab;
private RecyclerView dept1,dept2,dept3,dept4;
private LinearLayout dep1Nodata,dep2Nodata,dep3Nodata,dep4Nodata;
private List<NewsData> list1,list2,list3,list4;
private DatabaseReference reference,dbRef;
private NewsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_news);
        dept1=findViewById(R.id.dept1);
        dept2=findViewById(R.id.dept2);
        dept3=findViewById(R.id.dept3);
        dept4=findViewById(R.id.dept4);
        dep1Nodata=findViewById(R.id.dep1Nodata);
        dep2Nodata=findViewById(R.id.dep2Nodata);
        dep3Nodata=findViewById(R.id.dep3Nodata);
        dep4Nodata=findViewById(R.id.dep4Nodata);dept1=findViewById(R.id.dept1);
        reference= FirebaseDatabase.getInstance().getReference().child("News");
        depart1();
        depart2();
        depart3();
        depart4();
        fab=findViewById(R.id.fab);
       /* if(getIntent().getStringExtra("ae").equals("e"))
            fab.setVisibility(View.GONE);*/

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateNews.this, addNews.class));
            }
        });



    }

    private void depart1() {
        dbRef=reference.child("Dept1");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1=new ArrayList<>();
                if (!snapshot.exists()){
                    dep1Nodata.setVisibility(View.VISIBLE);
                    dept1.setVisibility(View.GONE);

                }
                else{
                    dep1Nodata.setVisibility(View.GONE);
                    dept1.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot1:snapshot.getChildren()){
                        NewsData data=snapshot1.getValue(NewsData.class);
                        list1.add(data);
                        notification();
                    }
                    dept1.setHasFixedSize(true);
                    dept1.setLayoutManager(new LinearLayoutManager(UpdateNews.this));
                    adapter=new NewsAdapter(list1, UpdateNews.this,"Dept1");
                    dept1.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateNews.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void depart2() { dbRef=reference.child("Dept2");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list2=new ArrayList<>();
                if (!snapshot.exists()){
                    dep2Nodata.setVisibility(View.VISIBLE);
                    dept2.setVisibility(View.GONE);
                }
                else{
                    dep2Nodata.setVisibility(View.GONE);
                    dept2.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot1:snapshot.getChildren()){
                        NewsData data=snapshot1.getValue(NewsData.class);
                        list2.add(data);
                        notification();
                    }
                    dept2.setHasFixedSize(true);
                    dept2.setLayoutManager(new LinearLayoutManager(UpdateNews.this));
                    adapter=new NewsAdapter(list2, UpdateNews.this,"Dept2");
                    dept2.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateNews.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void depart3() {
        dbRef=reference.child("Dept3");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list3=new ArrayList<>();
                if (!snapshot.exists()){
                    dep3Nodata.setVisibility(View.VISIBLE);
                    dept3.setVisibility(View.GONE);
                }
                else{
                    dep3Nodata.setVisibility(View.GONE);
                    dept3.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot1:snapshot.getChildren()){
                        NewsData data=snapshot1.getValue(NewsData.class);
                        list3.add(data);
                       notification();
                    }
                    dept3.setHasFixedSize(true);
                    dept3.setLayoutManager(new LinearLayoutManager(UpdateNews.this));
                    adapter=new NewsAdapter(list3, UpdateNews.this,"Dept3");
                    dept3.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateNews.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void depart4() {
        dbRef=reference.child("Dept4");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list4=new ArrayList<>();
                if (!snapshot.exists()){
                    dep4Nodata.setVisibility(View.VISIBLE);
                    dept4.setVisibility(View.GONE);
                }
                else{
                    dep4Nodata.setVisibility(View.GONE);
                    dept4.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot1:snapshot.getChildren()){
                       NewsData data=snapshot1.getValue(NewsData.class);
                        list4.add(data);
                        notification();
                    }
                    dept4.setHasFixedSize(true);
                    dept4.setLayoutManager(new LinearLayoutManager(UpdateNews.this));
                    adapter=new NewsAdapter(list4, UpdateNews.this,"Dept4");
                    dept4.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateNews.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void notification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("n", "n", NotificationManager.IMPORTANCE_DEFAULT);
            //channel.setDescription(CHANNEL_DESC);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "n")
                    .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                    .setContentTitle("Notice Information").setContentText("New Data Added!")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
            notificationManagerCompat.notify(1, mBuilder.build());
        }
    }


}