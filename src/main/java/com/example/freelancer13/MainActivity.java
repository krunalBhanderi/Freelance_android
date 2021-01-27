package com.example.freelancer13;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {




    private static final int RC_SIGN_IN = 234;
    private static final String TAG = "coding";
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    Button signup;
    public  static Integer j=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            startActivity(new Intent(this,welcome.class));

    //   startActivity(new Intent(this,home1.class));
//         startActivity(new Intent(this,viewprojectowner.class));
//        startActivity(new Intent(this,signup.class));
   //     startActivity(new Intent(this,relatedproject.class));
        //startActivity(new Intent(this,displayproject.class));
       // startActivity(new Intent(this,search.class));
     //   startActivity(new Intent(this,viewproject.class));
        //startActivity(new Intent(this,postproject.class));
        //startActivity(new Intent(this,projectgiver.class));
//        startActivity(new Intent(this,home.class));
        db=FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();

      GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signup=findViewById(R.id.signup);
       signup.setOnClickListener(this);
        SignInButton signin =findViewById(R.id.sign_in_button);
        signin.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser()!=null)
        {
           // finish();
//            startActivity(new Intent(this, ProfileActivity.class));

        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.sign_in_button:
                signin();
                break;
            case R.id.signup:
                startActivity(new Intent(this,medium.class));
        }




            //textName.setText(user.getDisplayName());
            //textEmail.setText(user.getEmail());

            //Toast.makeText(this, user.getEmail(), Toast.LENGTH_SHORT).show();
           // startActivity(new Intent(this, home.class));
            //  startActivity(new Intent(this,ProfileActivity.class));
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==RC_SIGN_IN)
        {
            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                GoogleSignInAccount account = task.getResult(ApiException.class);

                //authenticating with firebase 
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }

   private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        //getting the auth credential
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

        //Now using firebase we are signing in the user here
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this, "User Signed In", Toast.LENGTH_SHORT).show();
                            pass();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }


                    }
                });
    }

    private void pass() {
        startActivity(new Intent(this,home.class));
    }


    private void signin() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();

        //starting the activity for result
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


}
