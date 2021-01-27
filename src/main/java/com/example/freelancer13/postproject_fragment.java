package com.example.freelancer13;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link postproject_fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link postproject_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class postproject_fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;



    String TAG="data";
    String ty;
    EditText title1,description1,budget1,time1,skills1;
    Button submit1;
    Spinner spinner;
    FirebaseFirestore db;
    String type[]={"Web Development","Data Analyze","Software Development"};
    Random ran=new Random();
    String random1;
    String owner="krunalbhanderi137@gmail.com";



    public postproject_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment postproject_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static postproject_fragment newInstance(String param1, String param2) {
        postproject_fragment fragment = new postproject_fragment();
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

        View v=inflater.inflate(R.layout.fragment_postproject_fragment, container, false);


        Toast.makeText(getActivity(), "post project", Toast.LENGTH_SHORT).show();

        title1=v.findViewById(R.id.title);
        description1=v.findViewById(R.id.description);
        budget1=v.findViewById(R.id.budget);
        time1=v.findViewById(R.id.time);
        submit1=v.findViewById(R.id.submit);
        spinner=(Spinner)v.findViewById(R.id.spinner);

        int random=ran.nextInt(10000);
        random1=String.valueOf(random);
        spinner.setPrompt("Select project type");
        ArrayAdapter<String> ad=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,type);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(ad);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                ty=type[position];
                Toast.makeText(getContext(), ty, Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), type[position], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        submit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db=FirebaseFirestore.getInstance();

                String title2=title1.getText().toString();
                String description2=description1.getText().toString();
                String time2=time1.getText().toString();
                String budget2=budget1.getText().toString();

                Map<String, Object> project = new HashMap<>();
                project.put("id",random1);
                project.put("title",title2);
                project.put("type",ty);
                project.put("description",description2);
                project.put("time", time2);
                project.put("budget",budget2);
                project.put("owner",owner);


                random1=owner+random1;




                db.collection("project").document(random1).set(project)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully written!");
                                Toast.makeText(getActivity(), "data inserted successfully", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error writing document", e);
                                Toast.makeText(getActivity(), "Data not inserted", Toast.LENGTH_SHORT).show();
                            }
                        });






            }
        });



        return v;
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
