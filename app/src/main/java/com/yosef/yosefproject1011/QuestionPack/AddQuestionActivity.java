package com.yosef.yosefproject1011.QuestionPack;

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
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.yosef.yosefproject1011.FirebaseServices;
import com.yosef.yosefproject1011.R;

import java.io.IOException;
import java.util.UUID;

public class AddQuestionActivity extends AppCompatActivity {
    private EditText etQuestion, etNumber, etPoints, etOption1, etOption2, etOption3, etOption4;
    private ImageView ivAdd;
    private FirebaseServices fbs;
    private Uri filePath;
    private static final String TAG = "AddQuestionActivity";
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide(); //Hide Action Bar
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_add_question);

        etQuestion = findViewById(R.id.etQuestionQuestion);
        etNumber = findViewById(R.id.etNumberQuestion);
        etPoints = findViewById(R.id.etPointsQuestion);
        etOption1 = findViewById(R.id.etOption1Question);
        etOption2 = findViewById(R.id.etOption2Question);
        etOption3 = findViewById(R.id.etOption3Question);
        etOption4 = findViewById(R.id.etOption4Question);
        ivAdd = findViewById(R.id.ivAddImgQuestion);
        fbs = FirebaseServices.getInstance();
        storageReference = fbs.getStorage().getReference();
    }

    public void AddQuestion(View view) {
        // check if any field is empty
        String Question, Number, Option1, Option2, Option3, Option4, Photo;
        int Points;
        Question = etQuestion.getText().toString();
        Number = etNumber.getText().toString();
        Points = Integer.parseInt(etPoints.getText().toString());
        Option1 = etOption1.getText().toString();
        Option2 = etOption2.getText().toString();
        Option3 = etOption3.getText().toString();
        Option4 = etOption4.getText().toString();
        if (ivAdd.getDrawable() == null)
            Photo = "no_image";
        else Photo = storageReference.toString();

        if (Question.trim().isEmpty() || Number.trim().isEmpty() || Option1.trim().isEmpty() ||
                Option2.trim().isEmpty() || Option3.trim().isEmpty() || Photo.trim().isEmpty()) {
            Toast.makeText(this, R.string.err_firebase_general, Toast.LENGTH_SHORT).show();
            return;
        }
        Question q = new Question(Question, Number, Points, Option1, Option2, Option3, Option4, Photo);
        fbs.getFire().collection("Questions")
                .add(q)
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
                        ivAdd.setBackground(null);
                        ivAdd.setImageBitmap(bitmap);
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
                                            .makeText(AddQuestionActivity.this,
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
                                    .makeText(AddQuestionActivity.this,
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