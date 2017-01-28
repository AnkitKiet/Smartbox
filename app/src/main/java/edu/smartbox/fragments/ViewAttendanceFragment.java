package edu.smartbox.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.smartbox.R;
import edu.smartbox.global.AppController;
import edu.smartbox.util.AttendanceCriteriaMsg;
import edu.smartbox.util.ClassNameFormat;

/**
 * Created by Ankit on 23/01/17.
 */
public class ViewAttendanceFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.txtPercent)
    TextView txtpercent;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipe;
    @Bind(R.id.progressAttendance)
    CircularProgressBar circularProgressBar;
    private View parentView;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    public ViewAttendanceFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parentView = inflater.inflate(R.layout.fragment_viewattendance, container, false);
        ButterKnife.bind(this, parentView);
        pref = this.getActivity().getSharedPreferences("Options", Context.MODE_PRIVATE);
        fetch_attendance();
        swipe.setOnRefreshListener(this);
        swipe.post(new Runnable() {
            @Override
            public void run() {
                swipe.setRefreshing(true);
                fetch_attendance();
                populate();
            }
        });

        populate();

        return parentView;
    }

    private void fetch_attendance() {
        final String roll = pref.getString("roll", "");
        final String sclass = pref.getString("sclass", "");
        final String ssec = pref.getString("ssec", "");
        ClassNameFormat cnf = new ClassNameFormat();
        final String clas = cnf.classnamechange(sclass, ssec);


        String tag_json_arry = "json_array_req";
        String url = "http://www.smartboxapp.esy.es/api/view_attendance.php";
        final StringRequest getreq = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonobj = new JSONObject(response);
                            String percent = jsonobj.getString("percent");
                            editor = pref.edit();
                            editor.putString("attendance", percent);
                            editor.commit();
                            swipe.setRefreshing(false);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // VolleyLog.d(TAG, "Error: " + error.getMessage());

            }

        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("roll", roll);
                params.put("class", clas);

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(getreq, tag_json_arry);

    }

    private void populate() {
        String percent = pref.getString("attendance", "0");
        if (Float.valueOf(((percent))) > 75)
            circularProgressBar.setColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        else {
            circularProgressBar.setColor(ContextCompat.getColor(getActivity(), R.color.warning_red));
            send_mail();
        }
        txtpercent.setText(percent + "%");
        circularProgressBar.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorSecText));
        circularProgressBar.setProgressBarWidth(getResources().getDimension(R.dimen.progressBarWidth));
        circularProgressBar.setBackgroundProgressBarWidth(getResources().getDimension(R.dimen.backgroundProgressBarWidth));
        int animationDuration = 2500; // 2500ms = 2,5s
        circularProgressBar.setProgressWithAnimation(Float.valueOf((percent)), animationDuration); // Default duration = 1500ms


    }

    private void send_mail() {
        final String email = pref.getString("pemail", "");
        AttendanceCriteriaMsg obj=new AttendanceCriteriaMsg();
        obj.send_sms(email);
    }

    @Override
    public void onRefresh() {
        fetch_attendance();
        populate();
    }
}


