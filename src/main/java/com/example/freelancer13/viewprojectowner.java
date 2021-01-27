package com.example.freelancer13;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class viewprojectowner extends AppCompatActivity {

    TextView textDisplay;
    public static String TAG = "Document";
    FirebaseFirestore db;
    Intent i;
    public Fragment selected=null;
    Button post,logout;
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


    public static int p=0;
    public static int u=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewprojectowner);

        db = FirebaseFirestore.getInstance();



        listView=findViewById(R.id.list);

        String owner="krunalbhanderi137@gmail.com";

        post=findViewById(R.id.post);
        logout=findViewById(R.id.logout);
        db= FirebaseFirestore.getInstance();

        logout=findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(viewprojectowner.this,MainActivity.class));

            }
        });
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(viewprojectowner.this,postproject.class));
            }
        });
        db.collection("project")
                .whereEqualTo("owner", owner)
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
                                data.append(document.get("title")).append("#");
                                id.append(document.get("id")).append("#");
                            }
                        } else {
                            //  Toast.makeText(getActivity(), "data not get", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Error getting documents: ", task.getException());


                        }
                        pass(data.toString(),id.toString());
                    }

                    private void pass(String toString, String toString1) {


                        String s[]=toString.split("#");
                        final String r[]=toString1.split("#");
                        //s= new String[]{"java", "android"};
                        //Toast.makeText(getActivity(), s.toString(), Toast.LENGTH_SHORT).show();
                        //Toast.makeText(getActivity(), "pass", Toast.LENGTH_SHORT).show();
                        arrayList=new ArrayList<String>();


                        adapter=new ArrayAdapter<String>(listView.getContext(),android.R.layout.simple_spinner_dropdown_item,arrayList);
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

                                Toast.makeText(viewprojectowner.this, p, Toast.LENGTH_SHORT).show();
                               Intent i=new Intent(viewprojectowner.this,viewbid.class);
                                i.putExtra("key",p);
                                startActivity(i);
                            }
                        });

                    }


                });







    }
}

