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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.yosef.yosefproject1011.FirebaseServices;
import com.yosef.yosefproject1011.MyCallBack;
import com.yosef.yosefproject1011.R;

import java.util.ArrayList;
import java.util.List;

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
            public void onCallback(List<Question> questionsList) {
                RecyclerView recyclerView = findViewById(R.id.rvQuestionsAllQuestions);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter = new AdapterQuestion(getApplicationContext(), que);
                recyclerView.setAdapter(adapter);
            }
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
