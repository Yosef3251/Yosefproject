package com.yosef.loginsignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etUsername = findViewById(R.id.etUsernameSignup);
        etPassword = findViewById(R.id.etPasswordSignup);
        auth = FirebaseAuth.getInstance();
    }
    public boolean Validation(String username){
        String password = etPassword.getText().toString();
        String[] email = username.split("@");
        String part1 = email[0];
        String part2 = email[1];
        int n = 0;

        if (email.length != 2)
            return false;

        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            Toast.makeText(SignupActivity.this, "Username or password is empty!", Toast.LENGTH_SHORT).show();
            return false;
        }
        for (int j = 0; j < part1.length(); j++) {
            if (part1.charAt(j) == ' ') {
                Toast.makeText(SignupActivity.this, "Invalid Email!", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        if (!(part1.charAt(0) >= 'A' && part1.charAt(0) <= 'Z' ||
                part1.charAt(0) >= 'a' && part1.charAt(0) <= 'z' ||
                part1.charAt(0) >= '_')) {
            Toast.makeText(SignupActivity.this, "Invalid Email!", Toast.LENGTH_LONG).show();
            return false;
        }
        for (int j = 1; j < part1.length(); j++) {
            if (!(part1.charAt(0) >= 'A' && part1.charAt(0) <= 'Z' ||
                    part1.charAt(0) >= 'a' && part1.charAt(0) <= 'z' ||
                    part1.charAt(0) >= '0' && part1.charAt(0) <= '9' ||
                    part1.charAt(0) >= '_' || part1.charAt(0) <= '.')) {
                Toast.makeText(SignupActivity.this, "Invalid Email!", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        if (part1.length() >= 70 || part1.length() < 3) {
            Toast.makeText(SignupActivity.this, "Invalid Email!", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!(part2.charAt(0) >= 'A' && part2.charAt(0) <= 'Z' ||
                part2.charAt(0) >= 'a' && part2.charAt(0) <= 'z' ||
                part2.charAt(0) >= '_')) {
            Toast.makeText(SignupActivity.this, "Invalid Email!", Toast.LENGTH_LONG).show();
            return false;
        }
        if ((part2.charAt(0) == '.') || (part2.charAt(part2.length() - 1) == '.')) {
            Toast.makeText(SignupActivity.this, "Invalid Email!", Toast.LENGTH_LONG).show();
            return false;
        }
        for (int j = 1; j < part2.length() - 1; j++) {
            if (part2.charAt(j) == '.')
                n++;
        }
        if (n == 0)
            return false;

        return true;
    }
    public void signup(View view) {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        if (Validation(username))
        {}

        if (username.trim().isEmpty() || password.trim().isEmpty())
        {
            Toast.makeText(SignupActivity.this, "Username or password is empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // TODO: commands if successful
                        } else {

                            // TODO: commands if failed
                            Toast.makeText(SignupActivity.this, "Username or password is empty!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });
    }

}