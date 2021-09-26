package com.coffee_secrets;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.textclassifier.ConversationActions;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

public class NameActivity extends AppCompatActivity {

    private ImageView mItam;


    private RecyclerView mRecyclerview;


    private static final int PICK_IMAGE_REQUEST = 1;
    private Button mButtonChoose;
    private EditText mEditTxtFileName;
    private Button mButtonUpload;
    private TextView mShowUpload;
    private ProgressBar mProgressBar;
    private ImageView mImageView01;

    private Uri mImageUri;

    private StorageReference mstorageRef;
    private DatabaseReference mdatabaseRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.name);


        mItam = findViewById(R.id.itam);
       // mRecyclerview = findViewById(R.id.recyclerview);





        mButtonChoose = findViewById(R.id.button_choose);
        mEditTxtFileName = findViewById(R.id.edit_txt_file_name);
        mButtonUpload = findViewById(R.id.button_upload);
        mShowUpload = findViewById(R.id.show_upload);
        mProgressBar = findViewById(R.id.ProgressBar);
        mImageView01 = findViewById(R.id.imageView01);



        mstorageRef = FirebaseStorage.getInstance().getReference("uploads");

        try {

            mdatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        } catch (Exception e) {
            System.out.println("Oops!");
            System.out.println(e);
        }



        mButtonChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openchooser();




            }
        });


        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                 uploadFile();




            }
        });


        mShowUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });


        mItam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(NameActivity.this,details_Activity.class);
                startActivity(i);

            }
        });

    }




    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));



    }






    private void uploadFile() {

        try {



            if (mImageView01 != null){
                StorageReference fileReference = mstorageRef.child(System.currentTimeMillis()
                        + "." + getFileExtension(mImageUri));

                fileReference.putFile(mImageUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                                upload upload = new upload(mEditTxtFileName.getText().toString().trim(),
                                        taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());


                                String uploadId = mdatabaseRef.push().getKey();
                                 mdatabaseRef.child(uploadId).setValue(upload);

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                             @Override
                             public void onFailure(@NonNull Exception e) {

                                 Toast.makeText(NameActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                             }
                         })


                         .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                             @Override
                             public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                              // double progress = (100.0 * taskSnapshot.get)

                             }
                         });



              }else {
                  Toast.makeText(this, "no file", Toast.LENGTH_SHORT).show();



            }


        } catch (Exception e) {
            System.out.println("Oops!");
        }






    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        try {

            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                    && data != null && data.getData() != null) {

                mImageUri = data.getData();

                //Picasso.with(this).load(mImageUri).into(mImageView01);

                Picasso.get().load(mImageUri).into(mImageView01);
                // mImageView01.setImageURI(mImageUri);


            }


        } catch (Exception e) {
            System.out.println("Oops!");
        }


    }

    private void openchooser(){

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);





    }



}