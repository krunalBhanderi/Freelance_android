package com.example.freelancer13;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class search1 extends Fragment {


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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.search1,container,false);



        search1=v.findViewById(R.id.search);
        db=FirebaseFirestore.getInstance();
        listView=v.findViewById(R.id.list);

        //search1.setOnClickListener(this);


        autocomplete1=v.findViewById(R.id.autocomplete);
        db.collection("project")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override

                    public void onComplete(@NonNull Task<QuerySnapshot> task) {


                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "data is get", Toast.LENGTH_SHORT).show();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());


                                data.append(document.get("title")).append("#");
                            }
                        } else {
                            Toast.makeText(getActivity(), "data not get", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }


                        pass(data.toString());

                    }

                });







        return inflater.inflate(R.layout.search,container,false);
        }



    private void pass(String toString) {
        toString=toString +"#"+"web development"+"#"+"app development"+"#"+"data analyze" + "#" +"java" + "#" +"python";
        Toast.makeText(getActivity(), toString, Toast.LENGTH_SHORT).show();
        String f[]=toString.split("#");

        Toast.makeText(getActivity(), f[0], Toast.LENGTH_SHORT).show();
        ArrayAdapter<String> ad=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,f);
        autocomplete1.setAdapter(ad);
        autocomplete1.setThreshold(1);



    }





    private void pass1(String toString, String toString1) {
        final String s[]=toString.split("#");
        final String r[]=toString1.split("#");

        arrayList=new ArrayList<String>();

        adapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item,arrayList);
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
                Toast.makeText(getActivity(), p, Toast.LENGTH_SHORT).show();
                i=new Intent(getActivity(),displayproject.class);
                i.putExtra("id",p);
                startActivity(i);
            }
        });
    }
    public void he(View v)
    {
        Toast.makeText(getActivity(), "hello", Toast.LENGTH_SHORT).show();
    }
}
