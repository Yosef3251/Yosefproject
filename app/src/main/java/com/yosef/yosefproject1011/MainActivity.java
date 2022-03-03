package com.yosef.yosefproject1011;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.yosef.yosefproject1011.QuestionPack.AllQuestionsActivity;

public class MainActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private Button btnLogin;
    private TextView tvForgetpass, tvCreata;
    private FirebaseServices fbs;
    private Utilities utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide(); //Hide Action Bar
        setContentView(R.layout.activity_main);
        connectComponents();
    }
    private void connectComponents() {
        etUsername = findViewById(R.id.etUsernameMain);
        etPassword = findViewById(R.id.etPasswordMain);
        fbs = FirebaseServices.getInstance();
        utils = Utilities.getInstance();
    }
    public void Login(View view) {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        utils = Utilities.getInstance();

        fbs.getAuth().signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Success login", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), FirstPageActivity.class);
                            startActivity(i);
                            // TODO: commands if successfull

                        } else {
                            Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            return;
                            // TODO: commands if failed
                        }
                    }
                });
    }
    //New page when signup
    public void SignUp(View view) {
        Intent i = new Intent(this, SignupActivity.class);
        startActivity(i);
    }
    //New page when u press forgot password
    public void Forget(View view) {
        Intent i = new Intent(this, SendSMSActivity.class);
        startActivity(i);
    }
}
