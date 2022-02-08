package com.yosef.yosefproject1011;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class AddSubjectActivity extends AppCompatActivity {
    private EditText etSubject, etInfo;
    private ImageView ivPicLeft, ivPicRight;
    private Button btnAddSubject;
    private FirebaseServices fbs;
    private Uri filePath;
    private static final String TAG = "AddSubject";
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);

        etSubject = findViewById(R.id.etSubjectSubject);
        etInfo = findViewById(R.id.etInfoSubject);
        ivPicLeft = findViewById(R.id.ivPicLeftSubject);
        ivPicRight = findViewById(R.id.ivPicRightSubject);
        btnAddSubject = findViewById(R.id.btnAddSubjectSubject);

        fbs = FirebaseServices.getInstance();
        storageReference = fbs.getStorage().getReference();
    }

        public void AddQuestion(View view) {
            // check if any field is empty
            String Subject, Info, Photo;
            Subject = etSubject.getText().toString();
            Info = etInfo.getText().toString();

            if (ivPicLeft.getDrawable() == null)
                Photo = "no_image";
            else Photo = storageReference.getDownloadUrl().toString();

            if (ivPicRight.getDrawable() == null)
                Photo = "no_image";
            else Photo = storageReference.getDownloadUrl().toString();

            if (Subject.trim().isEmpty() || Info.trim().isEmpty()) {
                Toast.makeText(this, R.string.err_firebase_general, Toast.LENGTH_SHORT).show();
                return;
            }

            Subject s = new Subject(Subject, Info, Photo);
            fbs.getFire().collection("Subjects")
                    .add(s)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error adding document", e);
                        }
                    });
        }
        public void AddPhoto(View view) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 40);
        }

        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 40) {
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        try {
                            filePath = data.getData();
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                            ivPicLeft.setBackground(null);
                            ivPicLeft.setImageBitmap(bitmap);

                            uploadImage();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
                }
            }
        }

        private void uploadImage() {
            if (filePath != null) {

                // Code for showing progressDialog while uploading
                ProgressDialog progressDialog
                        = new ProgressDialog(this);
                progressDialog.setTitle("Uploading...");
                progressDialog.show();

                // Defining the child of storageReference
                StorageReference ref
                        = storageReference
                        .child(
                                "images/"
                                        + UUID.randomUUID().toString());

                // adding listeners on upload
                // or failure of image
                ref.putFile(filePath)
                        .addOnSuccessListener(
                                new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                    @Override
                                    public void onSuccess(
                                            UploadTask.TaskSnapshot taskSnapshot) {

                                        // Image uploaded successfully
                                        // Dismiss dialog
                                        progressDialog.dismiss();
                                        Toast
                                                .makeText(AddSubjectActivity.this,
                                                        "Image Uploaded!!",
                                                        Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                })

                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                // Error, Image not uploaded
                                progressDialog.dismiss();
                                Toast
                                        .makeText(AddSubjectActivity.this,
                                                "Failed " + e.getMessage(),
                                                Toast.LENGTH_SHORT)
                                        .show();
                            }
                        })
                        .addOnProgressListener(
                                new OnProgressListener<UploadTask.TaskSnapshot>() {

                                    // Progress Listener for loading
                                    // percentage on the dialog box
                                    @Override
                                    public void onProgress(
                                            UploadTask.TaskSnapshot taskSnapshot) {
                                        double progress
                                                = (100.0
                                                * taskSnapshot.getBytesTransferred()
                                                / taskSnapshot.getTotalByteCount());
                                        progressDialog.setMessage(
                                                "Uploaded "
                                                        + (int) progress + "%");
                                    }
                                });

            }
        }
    }
