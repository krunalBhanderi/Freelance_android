package com.example.freelancer13;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class projectgiver extends AppCompatActivity{


    private static final int RC_SIGN_IN = 234;
    private static final String TAG = "simplifiedcoding";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "project giver", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,postproject.class));

    }}

