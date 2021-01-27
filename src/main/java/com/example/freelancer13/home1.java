package com.example.freelancer13;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class home1 extends AppCompatActivity implements View.OnClickListener {



    Button post,logout;

    Fragment selected=new viewprojectowner_fragment();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home1);


        post=findViewById(R.id.post);
        logout=findViewById(R.id.logout);

        Toast.makeText(this, "hoem 1", Toast.LENGTH_SHORT).show();
        post.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.post:
                selected=new postproject_fragment();
                break;


            case R.id.logout:
                finish();
                break;


        }

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selected).commit();


    }
    }


