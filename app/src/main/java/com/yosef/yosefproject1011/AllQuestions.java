package com.yosef.yosefproject1011;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class AllQuestions extends AppCompatActivity {

    private RecyclerView rvAllRest;
    RecyclerViewAdapter adapter;

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
        RecyclerView recyclerView = findViewById(R.id.rvQuestionsAllQuestions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(this, QuestionNum);
        recyclerView.setAdapter(adapter);
    }
}