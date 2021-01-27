package com.example.freelancer13;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link search_fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link search_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class search_fragment extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;



    public static StringBuilder data=new StringBuilder();
    public static StringBuilder data1=new StringBuilder();
    public static  StringBuilder id=new StringBuilder();

    private static final int RC_SIGN_IN = 234;
    private static final String TAG = "simplifiedcoding";
    FirebaseFirestore db;
    Button search1;
    EditText et1;
    AutoCompleteTextView autocomplete1;
    LinearLayout li;

    public static int pos;
    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;
    ListView listView;
    Intent i;

    public  static String field="title";





    public search_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment search_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static search_fragment newInstance(String param1, String param2) {
        search_fragment fragment = new search_fragment();
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
        View v=inflater.inflate(R.layout.fragment_search_fragment, container, false);

        Toast.makeText(getActivity(), "search_fragment", Toast.LENGTH_SHORT).show();
            search1=v.findViewById(R.id.search);
                db= FirebaseFirestore.getInstance();
        listView=v.findViewById(R.id.list);

        //search1.setOnClickListener(this);
        search1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String auto=autocomplete1.getText().toString();
                Toast.makeText(getActivity(), auto, Toast.LENGTH_SHORT).show();


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
                                data1=new StringBuilder();
                                id=new StringBuilder();
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {

                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        Toast.makeText(getActivity(), "data is get", Toast.LENGTH_SHORT).show();
                                        data1.append(document.get("title")).append("#");
                                        id.append(document.get("id")).append("#");
                                        //   Toast.makeText(search.this, , Toast.LENGTH_SHORT).show();
                                        Toast.makeText(getActivity(), "hello", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                                pass1(data1.toString(),id.toString());


                            }

                        });


            }
        });

        li=v.findViewById(R.id.ba);


        autocomplete1=v.findViewById(R.id.autocomplete);
        db.collection("project")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override

                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        data=new StringBuilder();

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());


                                data.append(document.get("title")).append("#");
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }


                        pass(data.toString());





                    }





                });











        // Inflate the layout for this fragment
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


    private void pass(String toString1) {
        toString1=toString1 +"#"+"web development"+"#"+"app development"+"#"+"data analyze" + "#" +"java" + "#" +"python";
        Toast.makeText(getActivity(), toString1, Toast.LENGTH_SHORT).show();
        String f[]=toString1.split("#");

        Toast.makeText(getActivity(), f[0], Toast.LENGTH_SHORT).show();
        ArrayAdapter<String> ad=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,f);
        autocomplete1.setAdapter(ad);
        autocomplete1.setThreshold(1);



    }

    private void pass1(String toString, String toString1) {
        final String s[]=toString.split("#");
        final String r[]=toString1.split("#");

        arrayList=new ArrayList<String>();

        adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,arrayList);
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
                Toast.makeText(getActivity(),p, Toast.LENGTH_SHORT).show();


                Bundle bundle = new Bundle();
                bundle.putString("key",p); // Put anything what you want

                displayproduct_fragment fragment1 = new displayproduct_fragment();
                fragment1.setArguments(bundle);

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment1)
                        .commit();

            }
        });
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
