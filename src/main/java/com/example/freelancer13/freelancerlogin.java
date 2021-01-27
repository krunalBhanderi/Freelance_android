package com.example.freelancer13;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class freelancerlogin extends AppCompatActivity implements View.OnClickListener{

    private static final int RC_SIGN_IN = 234;
    private static final String TAG = "coding";
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    Button signup,login1;
    public  static Integer j=1;

    StringBuilder name3=new StringBuilder();
    StringBuilder pass3=new StringBuilder();
    EditText name1,password1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freelancerlogin);

        db=FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        login1=findViewById(R.id.login);
        signup=findViewById(R.id.signup);
        signup.setOnClickListener(this);
        SignInButton signin =findViewById(R.id.sign_in_button);
        signin.setOnClickListener(this);
        login1.setOnClickListener(this);

        name1=findViewById(R.id.name);
        password1=findViewById(R.id.password);

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
                break;

            case R.id.login:

                name3=new StringBuilder();
                pass3=new StringBuilder();
                String login=name1.getText().toString();
                final String pass=password1.getText().toString();
                db.collection("user")
                        .whereEqualTo("password",pass)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                            @Override

                            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                if (task.isSuccessful()) {
                                 //   Toast.makeText(freelancerlogin.this, "data is get", Toast.LENGTH_SHORT).show();
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                //         startActivity(new Intent(freelancerlogin.this,home.class));
                                 //       Toast.makeText(freelancerlogin.this, "data is get", Toast.LENGTH_SHORT).show();
                                        name3.append(document.get("gmail"));
                                        pass3.append(document.get("password"));
                                        pass(name3.toString(),pass3.toString());

                                    }
                                } else {
                                    Toast.makeText(freelancerlogin.this, "Invalid Id & Password", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "Invalid Id & password ", task.getException());
                                }
                                 }

                            private void pass(String toString, String toString1) {
                                String name4=name1.getText().toString();
                                String pass4=password1.getText().toString();

                                if((name4.equals(toString)) && (pass4.equals(toString1)))
                                {
                                    Toast.makeText(freelancerlogin.this, "Welcome " + name4, Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(freelancerlogin.this,home.class));
                                }
                                else
                                {
                                    Toast.makeText(freelancerlogin.this, "invalid user id & password", Toast.LENGTH_SHORT).show();
                                }
                            }


                        });

                break;
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
                            Toast.makeText(freelancerlogin.this, "User Signed In", Toast.LENGTH_SHORT).show();
                            pass();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(freelancerlogin.this, "Authentication failed.",
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
