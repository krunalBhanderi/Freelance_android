package com.example.freelancer13;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Random;

public class viewbid extends AppCompatActivity {

    String ty;
    EditText title1,description1,budget1,time1,skills1;
    Button submit1;
    Spinner spinner;
    FirebaseFirestore db;
    String type[]={"Web Development","Data Analyze","Software Development"};
    Random ran=new Random();
    String random1;
    String owner="krunalbhanderi137@gmail.com";

    private static final String TAG = "simplifiedcoding";
    //FirebaseFirestore db;
    TextView tv1;
    public static  StringBuilder data=new StringBuilder();
    public static StringBuilder id=new StringBuilder();

    Intent i;
    public static int pos;
    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewbid);


        listView=findViewById(R.id.list);

        i=getIntent();
        String a=i.getStringExtra("key");



        db= FirebaseFirestore.getInstance();

        db.collection("project")
                .whereEqualTo("id", a)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override

                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        data=new StringBuilder();
                        id=new StringBuilder();
                        if (task.isSuccessful()) {
                            //       Toast.makeText(getActivity(), "data is get", Toast.LENGTH_SHORT).show();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                //                      Toast.makeText(getActivity(), "data is get", Toast.LENGTH_SHORT).show();
                                data.append(document.get("apply"));
                                //           id.append(document.get("id")).append("#");
                            }
                        } else {
                            //  Toast.makeText(getActivity(), "data not get", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Error getting documents: ", task.getException());


                        }
                        pass(data.toString());
                    }

                    private void pass(String toString) {
                        if (toString.length() > 5) {
                            // Toast.makeText(viewbid.this, toString, Toast.LENGTH_SHORT).show();
                            String s[] = toString.split("#");

                            arrayList = new ArrayList<String>();


                            adapter = new ArrayAdapter<String>(listView.getContext(), android.R.layout.simple_spinner_dropdown_item, arrayList);
                            listView.setAdapter(adapter);


                            for (String i : s) {
                                arrayList.add(i);
                                adapter.notifyDataSetChanged();
                            }


                        } else {
                            Toast.makeText(viewbid.this, "no bids found", Toast.LENGTH_SHORT).show();
                        }
                    }

                });


    }}
