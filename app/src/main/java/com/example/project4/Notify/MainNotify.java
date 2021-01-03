package com.example.project4.Notify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project4.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class MainNotify extends AppCompatActivity {
public static final String ChID="sc";
public static final String chName="sc";
public static final String chDes="we get notif";
EditText email,pass;
ProgressBar pb;
private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_main_notify);
        mAuth=FirebaseAuth.getInstance();
        email=findViewById(R.id.email);
        pass=findViewById(R.id.pass);
        pb=findViewById(R.id.pb);
        pb.setVisibility(View.INVISIBLE);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel(ChID,chName,NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(chDes);
            NotificationManager manager= getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
      /*  btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayNotification();
            }
        });*/
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
createUser();
            }


        });

    }
    private void createUser() {
        String e=email.getText().toString().trim();
        String p=pass.getText().toString().trim();
        if(e.isEmpty()){
            email.setError("empty email field");
            email.requestFocus();
            return;
        }
        if(p.isEmpty()){
            pass.setError("empty password field");
            pass.requestFocus();
            return;
        }
        if(p.length()<6){
            pass.setError("password length too short");
            pass.requestFocus();
            return;
        }
        pb.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(e,p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startProfileActivity();
                }else{
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        userLogin(e,p);
                    }
                    else{
                        pb.setVisibility(View.INVISIBLE);
                        Toast.makeText(MainNotify.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            }


        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser()!=null){
          startProfileActivity();
        }

    }
    private void startProfileActivity(){
        Intent intent=new Intent(this,ProfileActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
    private void userLogin(String em,String ps) {
        mAuth.signInWithEmailAndPassword(em,ps).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startProfileActivity();
                }
                else{
                    pb.setVisibility(View.INVISIBLE);
                    Toast.makeText(MainNotify.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
   /* private void displayNotification(){
        NotificationCompat.Builder mbuilder=new NotificationCompat.Builder(this,
                ChID)
                .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                .setContentTitle("its working..")
                .setContentText("content text")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(1,mbuilder.build());
    }*/
}