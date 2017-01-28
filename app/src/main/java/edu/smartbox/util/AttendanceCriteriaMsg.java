package edu.smartbox.util;

import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import edu.smartbox.activities.Activity_TeachersDashboard;
import edu.smartbox.global.AppController;
import edu.smartbox.ui.SnackBar;

/**
 * Created by Ankit on 28/01/17.
 */
public class AttendanceCriteriaMsg {

    public void send_sms(final String email){
        String tag_json_arry = "json_array_req";
        String url = "http://www.smartboxapp.esy.es/mail.php";
        StringRequest getreq = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
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
                params.put("email", email);

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(getreq, tag_json_arry);
    }



}

