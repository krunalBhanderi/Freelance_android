package com.example.freelancer13;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link home_fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link home_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class home_fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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

    private OnFragmentInteractionListener mListener;

    public home_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment home_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static home_fragment newInstance(String param1, String param2) {
        home_fragment fragment = new home_fragment();
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
        View v=inflater.inflate(R.layout.fragment_home_fragment, container, false);
      //  startActivity(new Intent(getActivity(),home.class));
        Toast.makeText(getActivity(), "home", Toast.LENGTH_SHORT).show();
        db = FirebaseFirestore.getInstance();



        db = FirebaseFirestore.getInstance();



        listView=v.findViewById(R.id.list);
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


                        private void pass1(String toString, String toString1,String skill, String type) {
                            final String s[]=toString.split("#");
                            final String r[]=toString1.split("#");
                            final String skill1[]=skill.split("#");
                            final String type1[]=type.split("#");


                            arrayList=new ArrayList<String>();

                            adapter=new ArrayAdapter<String>(listView.getContext(),android.R.layout.simple_spinner_dropdown_item,arrayList);
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
