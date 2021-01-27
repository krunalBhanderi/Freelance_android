package com.example.freelancer13;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

public class viewproject extends AppCompatActivity {


    private static final String TAG = "simplifiedcoding";
    FirebaseFirestore db;
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
        setContentView(R.layout.viewproject);


      //  String type1=i.getStringExtra("aa");
       // Toast.makeText(this, type1, Toast.LENGTH_SHORT).show();


        db= FirebaseFirestore.getInstance();

        db.collection("project")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override

                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(viewproject.this, "data is get", Toast.LENGTH_SHORT).show();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                Toast.makeText(viewproject.this, "data is get", Toast.LENGTH_SHORT).show();
                                data.append(document.get("title")).append("#");
                                id.append(document.get("id")).append("#");
                            }
                        } else {
                            Toast.makeText(viewproject.this, "data not get", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                        pass(data.toString(),id.toString());
                    }



                });




   }

    private void pass(String toString,String id) {
        final String s[]=toString.split("#");
        final String r[]=id.split("#");

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
              //  Toast.makeText(viewproject.this, p, Toast.LENGTH_SHORT).show();
                i=new Intent(getApplicationContext(),displayproject.class);
                i.putExtra("id",p);
               startActivity(i);
            }
        });
    }

 }