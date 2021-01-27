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
 * {@link profile_fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link profile_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class profile_fragment extends Fragment {
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
    TextView name1,gmail1,education1,skills1;
    Button apply1;
    public static  String apply;
    public  static  String id1="";
    Intent i;
    FirebaseFirestore db;
    public static final String TAG ="Freelancer";
    EditText bid1;
    String id="krunalbhanderi1379@gmail.com";
    Button logout1;

    public profile_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment profile_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static profile_fragment newInstance(String param1, String param2) {
        profile_fragment fragment = new profile_fragment();
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
       View v=inflater.inflate(R.layout.fragment_profile_fragment, container, false);


        name1=v.findViewById(R.id.name);
        gmail1=v.findViewById(R.id.gmail);
        education1=v.findViewById(R.id.education);
        skills1=v.findViewById(R.id.skills);
        logout1=v.findViewById(R.id.logout);
        db=FirebaseFirestore.getInstance();



        logout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),welcome.class));
            }
        });
        bid1=v.findViewById(R.id.bid);

        String field="gmail";
        db.collection("user")
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
                                data.append(document.get("name")).append("#");
                                data.append(document.get("gmail")).append("#");
                                data.append(document.get("education")).append("#");

                                data.append(document.get("skills")).append("#");


                                pass(data.toString());

                                Toast.makeText(getActivity(), data1.toString(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }

                });



        return v;
    }

    private void pass(String toString) {

        String s[]=toString.split("#");
        s[0]="name:   " +s[0];
        gmail1.setText(s[0]);
        s[1]="Gmail: "+"\n"+s[1];
        name1.setText(s[1]);
        String g="Skills: "+ s[3];

        skills1.setText(g);
        s[2]="Education:  "+"\n"+s[2];
        education1.setText(s[2]);


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
