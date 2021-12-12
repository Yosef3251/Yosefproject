package com.yosef.yosefproject1011;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class AllSubjectsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_subjects);
    }

    public static class FirebaseServices {
        private static AllQuestionsActivity.FirebaseServices instance;
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

        public FirebaseServices() {
            auth = FirebaseAuth.getInstance();
            fire = FirebaseFirestore.getInstance();
            storage = FirebaseStorage.getInstance();
        }

        public static AllQuestionsActivity.FirebaseServices getInstance() {
            if (instance == null)
                instance = new AllQuestionsActivity.FirebaseServices();
            return instance;
        }
    }
}