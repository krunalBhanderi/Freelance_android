package com.example.freelancer13;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;


public class home extends AppCompatActivity {

    TextView textDisplay;
    public static String TAG = "Document";
    FirebaseFirestore db;
    Intent i;
    public Fragment selected=null;
    Button bu1;
        //BottomNavigationView nav=findViewById(R.id.bottom_navigation);
   // nav.OnNavigationItemSelectedListener


    private static final int RC_SIGN_IN = 234;
   // private static final String TAG = "simplifiedcoding";
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

    String userskill[]={"web development","java"};
    public static int p=0;
    public static int u=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        db = FirebaseFirestore.getInstance();
        BottomNavigationView nav=findViewById(R.id.bottom_navigation);
        nav.setOnNavigationItemSelectedListener(navlistner);



        listView=findViewById(R.id.list);
        db=FirebaseFirestore.getInstance();

        db.collection("project")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override

                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                      //      Toast.makeText(relatedproject.this, "data is get", Toast.LENGTH_SHORT).show();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                    //            Toast.makeText(relatedproject.this, "data is get", Toast.LENGTH_SHORT).show();
                                data.append(document.get("title")).append("#");
                                id.append(document.get("id")).append("#");
                                skill.append(document.get("skills")).append("#");
                                type.append(document.get("type")).append("#");
                            }
                        } else {
                  //          Toast.makeText(relatedproject.this, "data not get", Toast.LENGTH_SHORT).show();
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

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new home_fragment()).commit();
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navlistner=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch(menuItem.getItemId())
            {
                case R.id.home:
                   selected=new home_fragment();
                    break;

                case R.id.search:
                    selected=new search_fragment();
                    break;

                case R.id.profile:
                    selected=new profile_fragment();                    break;


            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selected).commit();
            return true;

        }
    };


}