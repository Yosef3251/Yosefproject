package com.yosef.yosefproject1011;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class AllQuestionsActivity extends AppCompatActivity {

    private RecyclerView rvQuestions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_questions);
        // data to populate the RecyclerView with
        ArrayList<String> QuestionNum = new ArrayList<>();
        QuestionNum.add("Question 1");
        QuestionNum.add("Question 2");
        QuestionNum.add("Question 3");
        QuestionNum.add("Question 4");
        QuestionNum.add("Question 5");

        // set up the RecyclerView
        rvQuestions = findViewById(R.id.rvQuestionsAllQuestions);
        rvQuestions.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewAdapterQuestion adapter= new RecyclerViewAdapterQuestion(this, QuestionNum);
        rvQuestions.setAdapter(adapter);
    }

    public static class FirebaseServices {
            private static FirebaseServices instance;
            private FirebaseAuth auth;
            private FirebaseFirestore fire;
            private FirebaseStorage storage;

            public FirebaseAuth getAuth() {
                return auth;
            }

            public FirebaseFirestore getFire() {
                return fire;
            }

            public FirebaseStorage getStorage() {
                return storage;
            }

            public FirebaseServices()
            {
                auth = FirebaseAuth.getInstance();
                fire = FirebaseFirestore.getInstance();
                storage = FirebaseStorage.getInstance();
            }

            public static FirebaseServices getInstance()
            {
                if (instance == null)
                    instance = new FirebaseServices();
                return instance;
            }
    }
}