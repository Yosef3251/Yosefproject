package com.yosef.yosefproject1011.QuestionPack;

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
import com.yosef.yosefproject1011.Random.FirebaseServices;
import com.yosef.yosefproject1011.Random.MyCallBack;
import com.yosef.yosefproject1011.R;
import com.yosef.yosefproject1011.SubjectPack.Subject;

import java.util.ArrayList;

public class AllQuestionsActivity extends AppCompatActivity {

    private RecyclerView rvAllQuestions;
    AdapterQuestion adapter;
    FirebaseServices fbs;
    ArrayList<Question> que;
    MyCallBack myCallBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_questions);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide(); //Hide Action Bar

        fbs = FirebaseServices.getInstance();
        que = new ArrayList<Question>();
        readData();
        myCallBack = new MyCallBack() {
            @Override
            public void onCallback(ArrayList<Question> questionlist) {
                RecyclerView recyclerView = findViewById(R.id.rvQuestionAllQuestions);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter = new AdapterQuestion(getApplicationContext(), que);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCallBackSub(ArrayList<Subject> subjectlist) { }
        };
    }

    private void readData() {
        try {
            fbs.getFire().collection("Questions")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    que.add(document.toObject(Question.class));
                                }
                                myCallBack.onCallback(que);
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
