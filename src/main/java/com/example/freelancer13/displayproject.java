package com.example.freelancer13;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class displayproject extends AppCompatActivity implements View.OnClickListener {



    public static  StringBuilder data=new StringBuilder();
    public static StringBuilder data1=new StringBuilder();

    public static String user="krunalbhanderi137@gmail.com";
    TextView title1,skills1,type1,time1,budget1,description1;
    Button apply1;
    public static  String apply;
    public  static  String id1="";
    Intent i;
    FirebaseFirestore db;
    public static final String TAG ="Freelancer";
    EditText bid1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.displayproject);

        title1=findViewById(R.id.title);
        skills1=findViewById(R.id.skills);
        type1=findViewById(R.id.type);
        time1=findViewById(R.id.time);
        budget1=findViewById(R.id.budget);
        description1=findViewById(R.id.description);
        apply1=findViewById(R.id.apply);
        apply1.setOnClickListener(this);
        db=FirebaseFirestore.getInstance();
        i=getIntent();
        String id=i.getStringExtra("id");

        bid1=findViewById(R.id.bid);
        String field="id";

        db.collection("project")
                .whereEqualTo(field, id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                data=new StringBuilder();
                                data1=new StringBuilder();
                                data.append(document.get("title")).append("#");
                                data.append(document.get("type")).append("#");
                                data.append(document.get("skills")).append("#");
                                data.append(document.get("time")).append("#");
                                data.append(document.get("budget")).append("#");
                                data.append(document.get("description"));
                                data1.append(document.get("id"));

                                data1.append(document.get("apply"));
                            pass(data.toString());

                            pass1(data1.toString());
                               Toast.makeText(displayproject.this, data1.toString(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                      }

                });


    }

    private void pass1(String toString1) {
        String s[]=toString1.split("#");
        id1=s[0];
        apply=s[1];

    }

    private void pass(String toString) {

        String s[]=toString.split("#");
        s[0]="   " +s[0];
        title1.setText(s[0]);
        s[1]="type"+"\n"+s[1];
        type1.setText(s[1]);
        String skill[]=s[2].split(",");
        String g="";
        for(String i:skill) {
            g=g+"\n" + "-" +i;
        }
        g="skills"+g;
        skills1.setText(g);
        s[3]="Time"+"\n"+s[3];
        time1.setText(s[3]);
        s[4]="Budget"+"\n"+s[4];
        budget1.setText(s[4]);

        s[5]="                                          Description"+"\n" + "      -"+ s[5];
        description1.setText(s[5]);

    }


    @Override
    public void onClick(View v) {
        String app="";
        String bid2=bid1.getText().toString();
        app=apply + " : " + bid2 +"#"+user;

        DocumentReference documentReference = db.collection("project").document(id1);

        documentReference.update("apply", app)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(displayproject.this,"Document Updated",Toast.LENGTH_LONG).show();
                      //  startActivity(new Intent(displayproject.this,home.class));

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(displayproject.this,e.getMessage(),Toast.LENGTH_LONG).show();
                        Log.d("Androidview", e.getMessage());
                    }
                });
    }
}
