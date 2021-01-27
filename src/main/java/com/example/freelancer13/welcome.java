package com.example.freelancer13;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class welcome extends AppCompatActivity {


    MainActivity main;
    Button user1,freelancer1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);

        user1=findViewById(R.id.user);
        freelancer1=findViewById(R.id.freelancer);


        user1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(welcome.this,userlogin.class));
            }
        });

        freelancer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(welcome.this,freelancerlogin.class));
            }
        });

    }
}
