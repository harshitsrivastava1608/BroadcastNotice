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
import android.widget.ProgressBar;
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
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class UpdateToAdminActivity extends AppCompatActivity {
private ImageView updateProfilePic;
private EditText updateName,updateEmail,updatePhone;
private Spinner updateCateg;
private Button btnUpdate,btnDelete;
private String name,email,phone,image;
private Bitmap bitmap;
private Uri filePath;String updateCategory;
private ProgressDialog pd;private String downloadUrl;String category,uniqueKey;
private StorageReference storageReference;
private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_to_admin);
        name=getIntent().getStringExtra("name");
        email=getIntent().getStringExtra("email");
        phone=getIntent().getStringExtra("phone");
        image=getIntent().getStringExtra("image");
         uniqueKey=getIntent().getStringExtra("key");
       category=getIntent().getStringExtra("category");
  //      String[] items=new String[]{"Select Category","Dept1","Dept2","Dept3","Dept4","Others"};
//        updateCateg.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,items));

        reference= FirebaseDatabase.getInstance().getReference();
        storageReference= FirebaseStorage.getInstance().getReference();
        pd=new ProgressDialog(this);
        /*updateCateg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                  @Override
                                                  public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                      updateCategory = updateCateg.getSelectedItem().toString();
                                                  }

                                                  @Override
                                                  public void onNothingSelected(AdapterView<?> parent) {

                                                  }
                                              });*/
            updateProfilePic=findViewById(R.id.updateProfilePic);
        updateName=findViewById(R.id.updateName);
        updateEmail=findViewById(R.id.updateEmail);
        updatePhone=findViewById(R.id.updatePhone);
        updateCateg=findViewById(R.id.updateCateg);
        btnUpdate=findViewById(R.id.btnUpdate);
        updateProfilePic=findViewById(R.id.updateProfilePic);
        btnDelete=findViewById(R.id.btnDelete);
        try {
            Picasso.get().load(image).into(updateProfilePic);
        } catch (Exception e) {
            e.printStackTrace();
        }
        updateName.setText(name);
        updateEmail.setText(email);
        updatePhone.setText(phone);
        updateProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
            }
        });
    }

    private void deleteData() {
        reference.child("Admins").child(category).child(uniqueKey).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(UpdateToAdminActivity.this,"Admin Deleted Sucessfully!!",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(UpdateToAdminActivity.this,UpdateAdmin.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateToAdminActivity.this,"Something Went Wrong",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkValidation() {
        name=updateName.getText().toString();
        email=updateEmail.getText().toString();
        phone=updatePhone.getText().toString();
        if(name.isEmpty()){
            updateName.setError("Empty");
            updateName.requestFocus();
        }
        else if(email.isEmpty()){
            updateEmail.setError("Empty");
            updateEmail.requestFocus();
        }
        else if(phone.isEmpty()){
            updatePhone.setError("Empty");
            updatePhone.requestFocus();
        }
      /* else if(updateCategory.equals("Select Category")){
            Toast.makeText(this,"Provide Category",Toast.LENGTH_SHORT).show();
        }*/
        else if(bitmap==null){
            updateData(image);
        }
        else{
            uploadImage();
        }
    }

    private void updateData(String s) {
        HashMap hp=new HashMap();
        hp.put("name",name);
        hp.put("email",email);
        hp.put("phone",phone);
        hp.put("image",s);

        reference.child("Admins").child(category).child(uniqueKey).updateChildren(hp).addOnSuccessListener(
                new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(UpdateToAdminActivity.this,"Admin Updated Sucessfully!!",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(UpdateToAdminActivity.this,UpdateAdmin.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    }
                }
        ).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
             Toast.makeText(UpdateToAdminActivity.this,"Something Went Wrong",Toast.LENGTH_SHORT).show();
            }
        });
    }
private void uploadImage(){
    pd.setMessage("Uploading...");
    pd.show();
    ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);
    byte[] finalImage=byteArrayOutputStream.toByteArray();
    final StorageReference filePath;
    filePath=storageReference.child("Admins").child(finalImage+"jpg");
    final UploadTask uploadTask=filePath.putBytes(finalImage);
    uploadTask.addOnCompleteListener(UpdateToAdminActivity.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                                updateData(downloadUrl);
                            }
                        });
                    }
                });
            }
            else{
               // pd.dismiss();
                Toast.makeText(UpdateToAdminActivity.this,"Something Went Wrong",Toast.LENGTH_SHORT).show();
            }
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
                updateProfilePic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}