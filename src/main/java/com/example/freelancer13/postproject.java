package com.example.freelancer13;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

// import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
// import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import java.util.Random;

public class postproject extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    String TAG="data";
    String skill[]={"java","python"};
    String ty;
    EditText title1,description1,budget1,time1;
    Button submit1;
    Spinner spinner;
    FirebaseFirestore db;
    String type[]={"Web Development","Data Analyze","Software Development"};
    Random ran=new Random();
    String random1;
    public static String owner="krunalbhanderi137@gmail.com";
    MultiAutoCompleteTextView skills1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.postproject);
        Toast.makeText(this, "post project", Toast.LENGTH_SHORT).show();

        skills1=findViewById(R.id.skills);
        title1=findViewById(R.id.title);
        description1=findViewById(R.id.description);
        budget1=findViewById(R.id.budget);
        time1=findViewById(R.id.time);
        submit1=findViewById(R.id.post);
        spinner=(Spinner)findViewById(R.id.type);


        ArrayAdapter<String> ad1=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,skill);
        skills1.setAdapter(ad1);
        skills1.setThreshold(1);
        skills1.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());


        int random=ran.nextInt(10000);
        random1=String.valueOf(random);
       // spinner.setPrompt("Select project type");
        ArrayAdapter<String> ad=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,type);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(ad);

        spinner.setOnItemSelectedListener(this);
        submit1.setOnClickListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        ty=type[position];
        Toast.makeText(this, ty, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, type[position], Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        db=FirebaseFirestore.getInstance();

        String title2=title1.getText().toString();
        String description2=description1.getText().toString();
        String time2=time1.getText().toString();
        String budget2=budget1.getText().toString();


        random1=owner+random1;

        Map<String, Object> project = new HashMap<>();
        project.put("id",random1);
        project.put("title",title2);
        project.put("type",ty);
        project.put("description",description2);
        project.put("time", time2);
        project.put("budget",budget2);
        project.put("owner",owner);





        db.collection("project").document(random1).set(project)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        Toast.makeText(postproject.this, "project post successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(postproject.this,viewprojectowner.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                        Toast.makeText(postproject.this, "Data not inserted", Toast.LENGTH_SHORT).show();
                    }
                });




    }



}