package com.example.freelancer13;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class signup extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {



    private static final int RC_SIGN_IN = 234;

    //Tag for the logs optional
    private static final String TAG = "simplifiedcoding";

    //creating a GoogleSignInClient object
    GoogleSignInClient mGoogleSignInClient;
    FirebaseFirestore db;
    //And also a Firebase Auth object
    FirebaseAuth mAuth;
    TextView gmail1,name1;
    EditText password;
    ImageView image1;
    String skill[]={"web development","app development","data analyze","java","python"};
    MultiAutoCompleteTextView skills1;
    String education1[]={"MCA","BCA","Bsc IT","Msc IT","BE (Computer)"};
    Spinner education2;
    Button signup1;
    public static String edu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);



        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();

        password=findViewById(R.id.password);
        education2=findViewById(R.id.education);
        skills1=findViewById(R.id.skills);
        gmail1=findViewById(R.id.gmail);
        name1=findViewById(R.id.name);
        image1=findViewById(R.id.image);
        signup1=findViewById(R.id.signup);



        name1.setText(user.getDisplayName());
        gmail1.setText(user.getEmail());
        //Then we need a GoogleSignInOptions object
        //And we need to build it as below

        FirebaseFirestore db;


        signup1.setOnClickListener(this);
        ArrayAdapter<String> ad=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,skill);
        skills1.setAdapter(ad);
        skills1.setThreshold(1);
        skills1.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());


        ArrayAdapter<String> ad1=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,education1);
        education2.setAdapter(ad1);
        education2.setOnItemSelectedListener(this);




    }





    @Override
    public void onClick(View v) {

        db=FirebaseFirestore.getInstance();

        String gmail3=gmail1.getText().toString();
        String name3=name1.getText().toString();
        String skills3=skills1.getText().toString();
        String education3=edu;
        String pass=password.getText().toString();

        Map<String, Object> user = new HashMap<>();
        user.put("gmail",gmail3);
        user.put("name",name3);
        user.put("skills",skills3);
        user.put("education",education3);
        user.put("password",pass);


        db.collection("user").document(gmail3).set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");

                        Toast.makeText(signup.this, "sign up successfully!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(signup.this,home.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                        Toast.makeText(signup.this, "Data not inserted", Toast.LENGTH_SHORT).show();
                    }
                });




    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        edu=education1[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
