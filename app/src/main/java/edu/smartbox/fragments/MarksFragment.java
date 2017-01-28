package edu.smartbox.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.smartbox.R;
import edu.smartbox.activities.ViewMarksActivity;
import edu.smartbox.global.AppController;
import edu.smartbox.ui.SnackBar;
import edu.smartbox.util.NetworkCheck;

/**
 * Created by Ankit on 04/01/17.
 */
public class MarksFragment extends android.support.v4.app.Fragment {

    @Bind(R.id.spinExam)
    Spinner spinExam;
    @Bind(R.id.spinSession)
    Spinner spinSession;
    @Bind(R.id.btnFind)
    Button btnMarks;
    private View parentView;
    String exam = "Select";
    String session = null;
    SharedPreferences pref;
    Boolean sclick = false;
    Boolean eclick = false;
    private ProgressDialog mProgress;

    public MarksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parentView = inflater.inflate(R.layout.fragment_marks, container, false);
        ButterKnife.bind(this, parentView);
        populate();
        return parentView;
    }

    private void populate() {
        mProgress = new ProgressDialog(getActivity());
        pref = this.getActivity().getSharedPreferences("Options", Context.MODE_PRIVATE);
        final String sclass = pref.getString("sclass", "");
        final String libid = pref.getString("libid", "");

        List<String> categories = new ArrayList<String>();
        categories.add("Select Exam Here");
        categories.add("Half Yearly Exams");
        categories.add("Annual Exams");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinExam.setAdapter(dataAdapter);
        spinExam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                exam = parent.getItemAtPosition(position).toString();
                if (exam.equals("Half Yearly Exams")) {
                    exam = "Half";
                    sclick = true;
                } else if (exam.equals("Annual Exams")) {
                    exam = "Annual";
                    sclick = true;
                } else {
                    if (exam.equals("Select Exam Here")) {
                        sclick = false;
                        // Toast.makeText(getActivity(), "You Haven't Chosen Exam", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        List<String> categoriessessn = new ArrayList<String>();
        categoriessessn.add("Select Session Here");
        categoriessessn.add("2015-2016");

        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categoriessessn);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinSession.setAdapter(dataAdapter1);
        spinSession.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                session = parent.getItemAtPosition(position).toString();
                if (session.equals("2015-2016")) {
                    session = "2016";
                    eclick = true;
                } else {
                    if (session.equals("Select Session Here")) {
                        // Toast.makeText(getActivity(), "You Haven't Chosen Session", Toast.LENGTH_SHORT).show();
                        eclick = false;
                    }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        btnMarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NetworkCheck.isNetworkAvailable(getActivity())) {
                    // request_marks(sclass,libid,exam,session);
                    if (sclick == true && eclick == true) {

                        Intent i = new Intent(getActivity(), ViewMarksActivity.class);
                        i.putExtra("session", session);
                        i.putExtra("exam", exam);
                        startActivity(i);
                    } else
                        SnackBar.show(getActivity(), "Select Category First");
                } else
                    SnackBar.show(getActivity(), "No Internet Connection");
            }

        });

    }

/*
    private void request_marks(final String sclass, final String libid, final String exam, final String session) {
        Toast.makeText(getActivity(), sclass + " " + libid + exam + " " + session, Toast.LENGTH_SHORT).show();
        String tag_json_arry = "json_array_req";

        String url = "http://www.smartboxapp.esy.es/api/view_marks.php";
        mProgress.setMessage("Please wait");
        mProgress.show();
        final StringRequest getreq = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        for (int i = 0; i <= response.length(); i++) {


                        }

                        try {
                            JSONObject jsonobj = new JSONObject(response);
                            *//* String first = jsonobj.getString("one");
                            String sec = jsonobj.getString("two");
                            String third = jsonobj.getString("three");
                            String four = jsonobj.getString("four");
                            String five = jsonobj.getString("five");
                            String six = jsonobj.getString("six");
                            String seven = jsonobj.getString("seven");
                            String eight = jsonobj.getString("eight");
                           *//*
                            mProgress.dismiss();

                            Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            mProgress.dismiss();
                            Toast.makeText(getActivity(), "Try Again", Toast.LENGTH_SHORT).show();
                        }

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // VolleyLog.d(TAG, "Error: " + error.getMessage());
                SnackBar.show(getActivity(), "Sorry! Try Again");
                mProgress.hide();
            }

        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("class", sclass);
                params.put("library_id", libid);
                params.put("exam", exam);
                params.put("session", session);


                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(getreq, tag_json_arry);


    }*/

}

