package com.example.project4.NewsFeed;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.project4.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class addNews extends AppCompatActivity {
private ImageView addNewsPic;
private EditText addNewsTitle,addNewsDesc;
private Spinner addCateg;
private Button btnAdd;
private ProgressDialog pd;
private StorageReference storageReference;
private DatabaseReference reference,dbRef;
private Bitmap bitmap;
    private String category;
    private Uri filePath;
    private String title,description,downloadUrl="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news);
        addNewsPic=findViewById(R.id.addNewsPic);
        addNewsTitle=findViewById(R.id.addNewsTitle);
        addNewsDesc=findViewById(R.id.addNewsDesc);
        addCateg=findViewById(R.id.addCateg);
        btnAdd=findViewById(R.id.btnAdd);

        pd=new ProgressDialog(this);
        addNewsPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        String[] items=new String[]{"Select Category","Dept1","Dept2","Dept3","Dept4","Others"};
        addCateg.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,items));
        reference= FirebaseDatabase.getInstance().getReference();
        storageReference= FirebaseStorage.getInstance().getReference();
        addCateg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category=addCateg.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });
    }
    private void chooseImage(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Image"),1);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data.getData()!=null){
            filePath=data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                addNewsPic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
  private void checkValidation() {
        title=addNewsTitle.getText().toString();
        description=addNewsDesc.getText().toString();

        if(title.isEmpty()){
            addNewsTitle.setError("Empty");
            addNewsTitle.requestFocus();
        }
        else if(description.isEmpty()){
            addNewsDesc.setError("Empty");
            addNewsDesc.requestFocus();
        }

        else if(category.equals("Select Category")){
            Toast.makeText(this,"Provide Category",Toast.LENGTH_SHORT).show();
        }
        else if(bitmap==null){
          insertData();
        }
        else{
            uploadImage();
        }
    }

    private void uploadImage() {
        pd.setMessage("Uploading...");
        pd.show();
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);
        byte[] finalImage=byteArrayOutputStream.toByteArray();
        final StorageReference filePath;
        filePath=storageReference.child("News").child(title+category);//finalImage+"jpg");
        final UploadTask uploadTask=filePath.putBytes(finalImage);
        uploadTask.addOnCompleteListener(addNews.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    downloadUrl=String.valueOf(uri);
                                    insertData();
                                }
                            });
                        }
                    });
                }
                else{
                    pd.dismiss();
                    Toast.makeText(addNews.this,"Something Went Wrong",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void insertData() {
        String uniqueKey=reference.child(category).push().getKey();
        NewsData data=new NewsData(title,description,category,downloadUrl,uniqueKey);
        reference.child("News").child(category).child(uniqueKey).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pd.dismiss();
                Toast.makeText(addNews.this,"Pdf Uploaded Successfully",Toast.LENGTH_SHORT).show();
                notification();
                //pdfTitle.setText("");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(addNews.this,"Failed To Upload Pdf",Toast.LENGTH_SHORT).show();
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