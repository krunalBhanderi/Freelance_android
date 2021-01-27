package com.example.freelancer13;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class search extends AppCompatActivity implements View.OnClickListener {


    public static StringBuilder data=new StringBuilder();
    public static StringBuilder data1=new StringBuilder();
    public static  StringBuilder id=new StringBuilder();

    private static final int RC_SIGN_IN = 234;
    private static final String TAG = "simplifiedcoding";
    FirebaseFirestore db;
    Button search1;
    EditText et1;
    AutoCompleteTextView autocomplete1;

    public static int pos;
    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;
    ListView listView;
    Intent i;

     public  static String field="title";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);


        search1=findViewById(R.id.search);
        db=FirebaseFirestore.getInstance();
        listView=findViewById(R.id.list);

        search1.setOnClickListener(this);


        autocomplete1=findViewById(R.id.autocomplete);
        db.collection("project")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override

                    public void onComplete(@NonNull Task<QuerySnapshot> task) {


                        if (task.isSuccessful()) {
                            Toast.makeText(search.this, "data is get", Toast.LENGTH_SHORT).show();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());


                              data.append(document.get("title")).append("#");
                            }
                        } else {
                            Toast.makeText(search.this, "data not get", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }


                        pass(data.toString());

                    }

                     });




    }

    private void pass(String toString) {
        toString=toString +"#"+"web development"+"#"+"app development"+"#"+"data analyze" + "#" +"java" + "#" +"python";
        Toast.makeText(this, toString, Toast.LENGTH_SHORT).show();
        String f[]=toString.split("#");

        Toast.makeText(this, f[0], Toast.LENGTH_SHORT).show();
        ArrayAdapter<String> ad=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,f);
        autocomplete1.setAdapter(ad);
        autocomplete1.setThreshold(1);



    }


    @Override
    public void onClick(View v) {
        String auto=autocomplete1.getText().toString();
        Toast.makeText(this, auto, Toast.LENGTH_SHORT).show();


        if(auto.equals("web development") || auto.equals("app development") || auto.equals("data analyze"))
        {
            field="type";
        }
        if (auto.equals("java") || auto.equals("python"))
        {
            field="skills";
        }

        db.collection("project")
                .whereEqualTo(field, auto)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Toast.makeText(search.this, "data is get", Toast.LENGTH_SHORT).show();
                                data1.append(document.get("title")).append("#");
                                id.append(document.get("id")).append("#");
                             //   Toast.makeText(search.this, , Toast.LENGTH_SHORT).show();
                                Toast.makeText(search.this, "hello", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                        pass1(data1.toString(),id.toString());


                    }

                });

    }

    private void pass1(String toString, String toString1) {
        final String s[]=toString.split("#");
        final String r[]=toString1.split("#");

        arrayList=new ArrayList<String>();
        listView=findViewById(R.id.list);
        adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,arrayList);
        listView.setAdapter(adapter);


        for(String i:s)
        {
            arrayList.add(i);
            adapter.notifyDataSetChanged();
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pos=position;
                String p=r[pos];
                Toast.makeText(search.this, p, Toast.LENGTH_SHORT).show();
                i=new Intent(getApplicationContext(),displayproject.class);
                i.putExtra("id",p);
                startActivity(i);
            }
        });
    }

}
