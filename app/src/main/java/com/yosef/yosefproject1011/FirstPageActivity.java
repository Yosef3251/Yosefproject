package com.yosef.yosefproject1011;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.yosef.yosefproject1011.QuestionPack.AddQuestionActivity;
import com.yosef.yosefproject1011.QuestionPack.AllQuestionsActivity;
import com.yosef.yosefproject1011.SubjectPack.AddSubjectActivity;
import com.yosef.yosefproject1011.SubjectPack.AllSubjectsActivity;

public class FirstPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide(); //Hide Action Bar
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_first_page);
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