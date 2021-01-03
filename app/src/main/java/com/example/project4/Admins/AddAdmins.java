package com.example.project4.Admins;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
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
import java.util.HashMap;

public class AddAdmins extends AppCompatActivity {
private ImageView addProfilePic;
private EditText addName,addEmail,addPhone;
private Spinner addCateg;
private Button btnAdd;
private ProgressDialog pd;
private StorageReference storageReference;
private DatabaseReference reference,dbRef;
private Bitmap bitmap;
    private String category;
    private Uri filePath;
    private String name,email,phone,downloadUrl="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_admins);
        addProfilePic=findViewById(R.id.addProfilePic);
        addName=findViewById(R.id.addName);
        addEmail=findViewById(R.id.addEmail);
        addPhone=findViewById(R.id.addPhone);
        addCateg=findViewById(R.id.addCateg);
        btnAdd=findViewById(R.id.btnAdd);

        pd=new ProgressDialog(this);
        addProfilePic.setOnClickListener(new View.OnClickListener() {
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

    private void checkValidation() {
        name=addName.getText().toString();
        email=addEmail.getText().toString();
        phone=addPhone.getText().toString();
        if(name.isEmpty()){
            addName.setError("Empty");
            addName.requestFocus();
        }
        else if(email.isEmpty()){
            addEmail.setError("Empty");
            addEmail.requestFocus();
        }
        else if(phone.isEmpty()){
            addPhone.setError("Empty");
            addPhone.requestFocus();
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
        filePath=storageReference.child("Admins").child(finalImage+"jpg");
        final UploadTask uploadTask=filePath.putBytes(finalImage);
        uploadTask.addOnCompleteListener(AddAdmins.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                    Toast.makeText(AddAdmins.this,"Something Went Wrong",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void insertData() {
        String uniqueKey=reference.child(category).push().getKey();
        AdminData data=new AdminData(name,email,phone,downloadUrl,uniqueKey);
        reference.child("Admins").child(category).child(uniqueKey).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pd.dismiss();
                Toast.makeText(AddAdmins.this,"Pdf Uploaded Successfully",Toast.LENGTH_SHORT).show();
                //pdfTitle.setText("");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(AddAdmins.this,"Failed To Upload Pdf",Toast.LENGTH_SHORT).show();
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
                addProfilePic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}