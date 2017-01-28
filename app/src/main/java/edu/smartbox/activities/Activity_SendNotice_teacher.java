package edu.smartbox.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.smartbox.R;
import edu.smartbox.global.AppController;
import edu.smartbox.ui.SnackBar;
import edu.smartbox.util.NetworkCheck;

/**
 * Created by Ankit on 26/01/17.
 */
public class Activity_SendNotice_teacher extends AppCompatActivity {
    private ProgressDialog pDialog;
    @Bind(R.id.btnSub)
    Button submit;
    @Bind(R.id.edtSub)
    EditText edtSub;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    SharedPreferences pref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notice);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Send Notice");
        pref = getApplication().getSharedPreferences("Options", MODE_PRIVATE);
        if (NetworkCheck.isNetworkAvailable(Activity_SendNotice_teacher.this)) {
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (edtSub.getText().length() != 0) {

                        String sclass = pref.getString("tclass", "");
                        String ssec = pref.getString("tsec", "");

                        send_notice(sclass, ssec);
                    } else {
                        SnackBar.show(Activity_SendNotice_teacher.this, "Enter Something...");
                    }

                }
            });

        } else {
            SnackBar.show(Activity_SendNotice_teacher.this, "No Network Connection");
        }

    }

    private void send_notice(final String sclass, final String ssec) {
        final String notice = edtSub.getText().toString().trim();
        pDialog = new ProgressDialog(Activity_SendNotice_teacher.this);
        pDialog.setMessage("Fetching Marks");
        pDialog.show();
        String tag_json_arry = "json_array_req";
        String url = "http://www.smartboxapp.esy.es/api/send_notice_teacher.php";
        StringRequest getreq = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.dismiss();
                        Intent i = new Intent(Activity_SendNotice_teacher.this, Activity_TeachersDashboard.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                SnackBar.show(Activity_SendNotice_teacher.this, "Try Again");
                // VolleyLog.d(TAG, "Error: " + error.getMessage());

            }

        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("class", sclass);
                params.put("sec", ssec);
                params.put("notice", notice);

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(getreq, tag_json_arry);
    }

}

