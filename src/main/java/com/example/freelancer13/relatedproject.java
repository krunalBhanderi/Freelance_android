package com.example.freelancer13;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class relatedproject extends AppCompatActivity {


    private static final int RC_SIGN_IN = 234;
    private static final String TAG = "simplifiedcoding";
    public static String user="krunalbhanderi137@gmail.com";
    public static String field="skills";
    public static StringBuilder data=new StringBuilder();
    public static StringBuilder id=new StringBuilder();
    public static StringBuilder skill=new StringBuilder();
    public static StringBuilder type=new StringBuilder();
    public static int pos;
    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;
    ListView listView;
    Intent i;
    String userskill[]={"web development","java"};
    public static int p=0;
    public static int u=0;


    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relatedproject);


        listView=findViewById(R.id.list);
        db=FirebaseFirestore.getInstance();

        db.collection("project")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override

                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(relatedproject.this, "data is get", Toast.LENGTH_SHORT).show();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                Toast.makeText(relatedproject.this, "data is get", Toast.LENGTH_SHORT).show();
                                data.append(document.get("title")).append("#");
                                id.append(document.get("id")).append("#");
                                skill.append(document.get("skills")).append("#");
                                type.append(document.get("type")).append("#");
                            }
                        } else {
                            Toast.makeText(relatedproject.this, "data not get", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                        pass1(data.toString(),id.toString(),skill.toString(),type.toString());
                    }



                });



    }

    private void pass1(String toString, String toString1,String skill, String type) {
        final String s[]=toString.split("#");
        final String r[]=toString1.split("#");
        final String skill1[]=skill.split("#");
        final String type1[]=type.split("#");


        arrayList=new ArrayList<String>();
        listView=findViewById(R.id.list);
        adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,arrayList);
        listView.setAdapter(adapter);


        int y=0;
        int t=0;
        for(String i:userskill)
        {
            for (String j:skill1) {

                if(j.equals(i)) {
                    y=1;
                      }
            }
            for (String l:type1)
            {
                if(l.equals(i))
                {
                    y=1;
                }
            }

            if(y==1)
            {
                arrayList.add(s[t]);
                adapter.notifyDataSetChanged();

            }
            t+=1;

        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pos=position;
                String p=r[pos];
                Toast.makeText(relatedproject.this, p, Toast.LENGTH_SHORT).show();
                i=new Intent(getApplicationContext(),displayproject.class);
                i.putExtra("id",p);
                startActivity(i);
            }
        });
    }

}

