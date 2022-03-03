package com.yosef.yosefproject1011.QuestionPack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yosef.yosefproject1011.QuestionPack.Question;
import com.yosef.yosefproject1011.R;

public class QuestionDetailsActivty extends AppCompatActivity {
    
    private TextView tvQuestion, tvNumberOfQuestion, tvOption1, tvOption2, tvOption3, tvOption4, tvPhone;
    private ImageView ivPhoto;
    /*
        private String address;
    private RestCat category;
    private String photo;
    private String phone;
    * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_details_activty);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide(); //Hide Action Bar

        connectComponents();
        Intent i = this.getIntent();
        Question que = (Question) i.getSerializableExtra("que");

        tvQuestion.setText(que.getQuestion());
        tvNumberOfQuestion.setText(que.getNumberOfQuestion());
        tvOption1.setText(que.getOption1());
        tvOption2.setText(que.getOption2());
        tvOption3.setText(que.getOption3());
        tvOption4.setText(que.getOption4());

        Picasso.get().load(que.getPhoto()).into(ivPhoto);
    }

    private void connectComponents() {
        tvQuestion = findViewById(R.id.tvQuestionRow);
        tvNumberOfQuestion = findViewById(R.id.tvNumberOfQuestionRestDetails);
        tvOption1 = findViewById(R.id.tvOption1RestDetails);
        tvOption2 = findViewById(R.id.tvCategoryRestDetails);
        tvOption3 = findViewById(R.id.tvCategoryRestDetails);
        tvOption4 = findViewById(R.id.tvCategoryRestDetails);
        tvPhone = findViewById(R.id.tvPhoneRestDetails);
        ivPhoto = findViewById(R.id.ivPhotoRestDetails);
    }
}