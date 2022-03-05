package com.yosef.yosefproject1011.SubjectPack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.yosef.yosefproject1011.FirebaseServices;
import com.yosef.yosefproject1011.MyCallBack;
import com.yosef.yosefproject1011.QuestionPack.Question;
import com.yosef.yosefproject1011.R;

import java.util.ArrayList;

public class AllSubjectsActivity extends AppCompatActivity {

    private RecyclerView rvAllSubjects;
    AdapterSubject adapter;
    FirebaseServices fbs;
    ArrayList<Subject> sub;
    MyCallBack myCallBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_subjects);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide(); //Hide Action Bar

        fbs = FirebaseServices.getInstance();
        sub = new ArrayList<Subject>();
        readData();
        myCallBack = new MyCallBack() {
            @Override
            public void onCallBackSub(ArrayList<Subject> subjectlist) { }
            @Override
            public void onCallback(ArrayList<Question> questionlist) {
                RecyclerView recyclerView = findViewById(R.id.rvSubjectAllSubjects);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter = new AdapterSubject(getApplicationContext(), sub);
                recyclerView.setAdapter(adapter);
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
}




