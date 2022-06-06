package com.yosef.yosefproject1011;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.yosef.yosefproject1011.QuestionPack.AddQuestionActivity;
import com.yosef.yosefproject1011.QuestionPack.AllQuestionsActivity;
import com.yosef.yosefproject1011.QuestionPack.Question;
import com.yosef.yosefproject1011.Random.FirebaseServices;
import com.yosef.yosefproject1011.Random.MyCallBack;
import com.yosef.yosefproject1011.SubjectPack.AdapterSubject;
import com.yosef.yosefproject1011.SubjectPack.AddSubjectActivity;
import com.yosef.yosefproject1011.SubjectPack.Subject;

import java.util.ArrayList;

public class FirstPageActivity extends AppCompatActivity {

    private AdapterSubject adapter;
    private FirebaseServices fbs;
    private ArrayList<Subject> sub;
    private MyCallBack myCallBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide(); //Hide Action Bar
        fbs = FirebaseServices.getInstance();
        sub = new ArrayList<Subject>();
        readData();
        myCallBack = new MyCallBack() {
            @Override
            public void onCallBackSub(ArrayList<Subject> subjectslist) {
                RecyclerView recyclerView = findViewById(R.id.rvAllSubjectsFirstPageActivity);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter = new AdapterSubject(getApplicationContext(), sub);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCallback(ArrayList<Question> questionlist) {
            }
        };
    }

    private void readData() {
        try {
            fbs.getFire().collection("Subjects")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    sub.add(document.toObject(Subject.class));
                                }
                                myCallBack.onCallBackSub(sub);
                            } else {
                                Log.e("AllQuestionActivity: readData()", "Error getting documents.", task.getException());
                            }
                        }
                    });
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), "error reading!" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void Question(View view) {
        Intent i = new Intent(this, AddQuestionActivity.class);
        startActivity(i);
    }

    public void AllQuestions(View view) {
        Intent i = new Intent(this, AllQuestionsActivity.class);
        startActivity(i);
    }

    public void AddSubject(View view) {
        Intent i = new Intent(this, AddSubjectActivity.class);
        startActivity(i);
    }

}