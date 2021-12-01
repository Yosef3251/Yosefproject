package com.yosef.yosefproject1011;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddQuestion extends AppCompatActivity {
    private EditText etQuestion, etNumber, etPoints, etOption1, etOption2, etOption3 ,etOption4;
    private ImageView ivAdd;
    private FirebaseServices fbs;
    private static final String TAG ="AddQuestion";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
    }
    public void AddQuestion(View view) {
        String Question, Number, Option1, Option2, Option3, Option4;
        int Points;
        Question = etQuestion.getText().toString();
        Number = etNumber.getText().toString();
        Points = Integer.parseInt(etPoints.getText().toString());
        Option1 = etOption1.getText().toString();
        Option2 = etOption2.getText().toString();
        Option3 = etOption3.getText().toString();
        Option4 = etOption4.getText().toString();

        Question q = new Question(Question, Number, Points, Option1, Option2, Option3, Option4);
        fbs.getFire().collection("Questions")
                .add("Questions")
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

        ivAdd.setDrawingCacheEnabled(true);
        ivAdd.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) ivAdd.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        StorageReference storageRef = fbs.getStorage().getReference();
        UploadTask uploadTask = storageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
            }
        });
    }

    public void AddPhoto(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),40);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 40) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                        ivAdd.setBackground(null);
                        ivAdd.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED)  {
                Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
