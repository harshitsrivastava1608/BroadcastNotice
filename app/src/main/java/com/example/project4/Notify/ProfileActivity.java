package com.example.project4.Notify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.project4.Admins.AddAdmins;
import com.example.project4.Admins.UpdateAdmin;
import com.example.project4.NewsFeed.UpdateNews;
import com.example.project4.NewsFeed.addNews;
import com.example.project4.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

public class ProfileActivity extends AppCompatActivity {
FirebaseAuth mAuth;
public static final String NODE_USERS="users";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mAuth=FirebaseAuth.getInstance();
        FirebaseMessaging.getInstance().subscribeToTopic("scode");
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful()){
                            String token=task.getResult().getToken();
                            saveToken(token);
                            //  btn.setText(token);
                        }else{
                            //  btn.setText("Not Generated");
                        }
                    }
                });
       /* findViewById(R.id.admin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Welcome Admin!",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
        findViewById(R.id.enthusiast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Welcome Enthusiast!",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });*/

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser()==null){
            Intent intent=new Intent(this,MainNotify.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

    }

    private void saveToken(String token) {
        String email=mAuth.getCurrentUser().getEmail();
        User user=new User(email,token);
        DatabaseReference dbuser= FirebaseDatabase.getInstance().getReference(NODE_USERS);
        dbuser.child(mAuth.getCurrentUser().getUid()).setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Toast.makeText(ProfileActivity.this,"Token Saved",Toast.LENGTH_SHORT).show();
                    }
                });

    }
    public void addN(View view) {
        startActivity(new Intent(getApplicationContext(), addNews.class));
    }
    public void viewN(View view) {
        startActivity(new Intent(getApplicationContext(), UpdateNews.class));
    }

    public void viewA(View view) {
        startActivity(new Intent(getApplicationContext(), UpdateAdmin.class));
    }

    public void addA(View view) {
        startActivity(new Intent(getApplicationContext(), AddAdmins.class));
    }
}