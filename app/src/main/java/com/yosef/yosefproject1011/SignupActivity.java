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
import com.yosef.yosefproject1011.Random.FirebaseServices;
import com.yosef.yosefproject1011.Random.Utilities;

public class SignupActivity extends AppCompatActivity {
    private EditText etName, etEmail, etPassword, etPhone;
    private Button btn;
    private TextView tvBacklogin;
    private FirebaseServices fbs;
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
        fbs = FirebaseServices.getInstance();
        utils = Utilities.getInstance();
    }
    public void SignUpToApp(View view) {
        String username = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        if (username.trim().isEmpty() || password.trim().isEmpty())
        {
            Toast.makeText(this, "Some fields are empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!utils.validateEmail(username) || !utils.validatePassword(password))
        {
            Toast.makeText(this, "Incorrect email or password!", Toast.LENGTH_SHORT).show();
            return;
        }

        fbs.getAuth().createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent i = new Intent(SignupActivity.this, FirstPageActivity.class);
                            startActivity(i);
                        } else {
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

