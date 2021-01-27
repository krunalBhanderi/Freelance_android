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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
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
 * {@link viewproject_fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link viewproject_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class viewproject_fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;



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
    
    
    
    public viewproject_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment viewproject_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static viewproject_fragment newInstance(String param1, String param2) {
        viewproject_fragment fragment = new viewproject_fragment();
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
       View v= inflater.inflate(R.layout.fragment_viewproject_fragment, container, false);


        listView=v.findViewById(R.id.list);


        db= FirebaseFirestore.getInstance();

        db.collection("project")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override

                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        data=new StringBuilder();
                        id=new StringBuilder();
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "data is get", Toast.LENGTH_SHORT).show();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                Toast.makeText(getActivity(), "data is get", Toast.LENGTH_SHORT).show();
                                data.append(document.get("title")).append("#");
                                id.append(document.get("id")).append("#");
                            }
                        } else {
                            Toast.makeText(getActivity(), "data not get", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                        pass(data.toString(),id.toString());
                    }



                });

















        // Inflate the layout for this fragment
        return v;
    }
    private void pass(String toString,String id) {
        String s[]=toString.split("#");
        final String r[]=id.split("#");
        //s= new String[]{"java", "android"};
        Toast.makeText(getActivity(), s.toString(), Toast.LENGTH_SHORT).show();
        Toast.makeText(getActivity(), "pass", Toast.LENGTH_SHORT).show();
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
              //  Toast.makeText(getActivity(),p, Toast.LENGTH_SHORT).show();
             //   i=new Intent(getActivity(),displayproject.class);
              //  i.putExtra("id",p);
              //  startActivity(i);


                displayproduct_fragment fragment=new displayproduct_fragment();



                Bundle bundle = new Bundle();
                bundle.putString("key",p); // Put anything what you want

                viewbid_fragment fragment2 = new viewbid_fragment();
                fragment2.setArguments(bundle);

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment2)
                        .commit();


            }
        });
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
