package com.yosef.yosefproject1011;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class SignupActivity extends AppCompatActivity {
    private EditText etName, etEmail, etPassword, etPhone;
    private Button btn;
    private TextView tvBacklogin;
    private AllQuestions.FirebaseServices fbs;
    private Utilities utils;
    private static final String TAG ="SignupActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide(); //Hide Action Bar
        setContentView(R.layout.activity_main);
        connectComponents();
    }
    private void connectComponents() {
        setContentView(R.layout.activity_signup);
        etName = findViewById(R.id.etNameSignup);
        etEmail = findViewById(R.id.etEmailSignup);
        etPassword = findViewById(R.id.etPasswordSignup);
        etPhone = findViewById(R.id.etPhoneSignup);
        btn = findViewById(R.id.btnSignup);
        tvBacklogin = findViewById(R.id.tvBackloginSignup);
        fbs = AllQuestions.FirebaseServices.getInstance();
        utils = Utilities.getInstance();
    }
    public void SignUpToApp(View view) {
        String username = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        fbs.getAuth().createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Account created successfully!", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(SignupActivity.this, "Failed to create a new account!", Toast.LENGTH_LONG).show();
                            return;
                            // TODO: what to do if fails
                        }
                    }
                });
    }
                    //when click here back to login page
                    public void Backlogin(View view) {
                        Intent i = new Intent(this, MainActivity.class);
                        startActivity(i);
                    }
        }

