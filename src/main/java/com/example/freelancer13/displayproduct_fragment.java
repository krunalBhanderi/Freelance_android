package com.example.freelancer13;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link displayproduct_fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link displayproduct_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class displayproduct_fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


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



    public displayproduct_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment displayproduct_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static displayproduct_fragment newInstance(String param1, String param2) {
        displayproduct_fragment fragment = new displayproduct_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_displayproduct_fragment, container, false);


        Bundle bundle = this.getArguments();


        // handle your code here.
//        Toast.makeText(getActivity(), "hdcjskdvb", Toast.LENGTH_SHORT).show();
        String id=bundle.getString("key","not found").toString();
      //  Toast.makeText(getActivity(), id, Toast.LENGTH_SHORT).show();



        title1=v.findViewById(R.id.title);
        skills1=v.findViewById(R.id.skills);
        type1=v.findViewById(R.id.type);
        time1=v.findViewById(R.id.time);
        budget1=v.findViewById(R.id.budget);
        description1=v.findViewById(R.id.description);
        apply1=v.findViewById(R.id.apply);

        db=FirebaseFirestore.getInstance();


        apply1.setOnClickListener(new View.OnClickListener() {
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
                                Toast.makeText(getActivity(),"Bid apply successfully",Toast.LENGTH_LONG).show();
                                  startActivity(new Intent(getActivity(),home1.class));

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
                                Log.d("Androidview", e.getMessage());
                            }
                        });

            }
        });

        bid1=v.findViewById(R.id.bid);

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
        //                        Toast.makeText(getActivity(), data1.toString(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }

                });



        return v;
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


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
