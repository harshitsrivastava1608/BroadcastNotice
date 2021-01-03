package com.example.project4.Admins;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.project4.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UpdateAdmin extends AppCompatActivity {
FloatingActionButton fab;
private RecyclerView dept1,dept2,dept3,dept4;
private LinearLayout dep1Nodata,dep2Nodata,dep3Nodata,dep4Nodata;
private List<AdminData> list1,list2,list3,list4;
private DatabaseReference reference,dbRef;
private AdminAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_admin);
        dept1=findViewById(R.id.dept1);
        dept2=findViewById(R.id.dept2);
        dept3=findViewById(R.id.dept3);
        dept4=findViewById(R.id.dept4);
        dep1Nodata=findViewById(R.id.dep1Nodata);
        dep2Nodata=findViewById(R.id.dep2Nodata);
        dep3Nodata=findViewById(R.id.dep3Nodata);
        dep4Nodata=findViewById(R.id.dep4Nodata);dept1=findViewById(R.id.dept1);
        reference= FirebaseDatabase.getInstance().getReference().child("Admins");
        depart1();
        depart2();
        depart3();
        depart4();
        fab=findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateAdmin.this,AddAdmins.class));
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
                        AdminData data=snapshot1.getValue(AdminData.class);
                        list1.add(data);
                    }
                    dept1.setHasFixedSize(true);
                    dept1.setLayoutManager(new LinearLayoutManager(UpdateAdmin.this));
                    adapter=new AdminAdapter(list1,UpdateAdmin.this,"Dept1");
                    dept1.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateAdmin.this,error.getMessage(),Toast.LENGTH_SHORT).show();
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
                        AdminData data=snapshot1.getValue(AdminData.class);
                        list2.add(data);
                    }
                    dept2.setHasFixedSize(true);
                    dept2.setLayoutManager(new LinearLayoutManager(UpdateAdmin.this));
                    adapter=new AdminAdapter(list2,UpdateAdmin.this,"Dept2");
                    dept2.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateAdmin.this,error.getMessage(),Toast.LENGTH_SHORT).show();
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
                        AdminData data=snapshot1.getValue(AdminData.class);
                        list3.add(data);
                    }
                    dept3.setHasFixedSize(true);
                    dept3.setLayoutManager(new LinearLayoutManager(UpdateAdmin.this));
                    adapter=new AdminAdapter(list3,UpdateAdmin.this,"Dept3");
                    dept3.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateAdmin.this,error.getMessage(),Toast.LENGTH_SHORT).show();
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
                        AdminData data=snapshot1.getValue(AdminData.class);
                        list4.add(data);
                    }
                    dept4.setHasFixedSize(true);
                    dept4.setLayoutManager(new LinearLayoutManager(UpdateAdmin.this));
                    adapter=new AdminAdapter(list4,UpdateAdmin.this,"Dept4");
                    dept4.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateAdmin.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}