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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.smartbox.R;
import edu.smartbox.global.AppConfig;
import edu.smartbox.global.AppController;
import edu.smartbox.ui.SnackBar;
import edu.smartbox.util.NetworkCheck;

/**
 * Created by Ankit on 06/01/17.
 */
public class ComplainActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.edtSub)
    EditText edtSub;
    @Bind(R.id.btnSub)
    Button btnSub;
    private ProgressDialog mProgress;
    SharedPreferences pref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain);
        ButterKnife.bind(this);
        pref = getApplication().getSharedPreferences("Options", MODE_PRIVATE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.complain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mProgress = new ProgressDialog(this);
        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(NetworkCheck.isNetworkAvailable(ComplainActivity.this)){
                String complaint = edtSub.getText().toString().trim();
                String uid = pref.getString("libid","");
                submit(complaint, uid);
            }
                else
                    SnackBar.show(ComplainActivity.this,"No Internet Connection");
            }
        });
    }

    private void submit(final String complaint, final String uid) {
        mProgress.setMessage("Submiting Complaint");
        mProgress.show();
        String tag_json_arry = "json_array_req";
        String url = "http://www.smartboxapp.esy.es/api/complaint.php";
        StringRequest getreq = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonobj = new JSONObject(response);
                            boolean exist = jsonobj.getBoolean("valid");
                            if (exist) {
                                mProgress.dismiss();
                                Toast.makeText(ComplainActivity.this, "Complaint Submitted Successfully", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(ComplainActivity.this, DashboardActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                            } else {
                                mProgress.dismiss();
                                SnackBar.show(ComplainActivity.this, "Unable To Submit");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgress.dismiss();
                SnackBar.show(ComplainActivity.this, "Try Again");
                // VolleyLog.d(TAG, "Error: " + error.getMessage());

            }

        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("complain", complaint);
                params.put("id", uid);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(getreq, tag_json_arry);

    }


}
