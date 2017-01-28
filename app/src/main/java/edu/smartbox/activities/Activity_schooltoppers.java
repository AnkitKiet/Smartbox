package edu.smartbox.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import butterknife.Bind;
import butterknife.ButterKnife;
import edu.smartbox.R;
import edu.smartbox.adapters.School_topperAdapter;
import edu.smartbox.dto.ToppersDto;
import edu.smartbox.global.AppController;
import edu.smartbox.ui.SnackBar;

/**
 * Created by Ankit on 23/01/17.
 */
public class Activity_schooltoppers extends AppCompatActivity {

    @Bind(R.id.toppersRecyclerView)
    RecyclerView toppersRecyclerView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private List<ToppersDto> listtoppers;
    private School_topperAdapter adapter;
    private ProgressDialog pDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_toppers);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("School Toppers");
        listtoppers = new ArrayList<>();
        fetch_data();
        adapter = new School_topperAdapter(Activity_schooltoppers.this, listtoppers);
        toppersRecyclerView.setAdapter(adapter);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(Activity_schooltoppers.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        toppersRecyclerView.setLayoutManager(layoutManager);


    }

    private void fetch_data() {

        pDialog = new ProgressDialog(Activity_schooltoppers.this);
        pDialog.setMessage("Fetching Students");
        pDialog.show();
        String tag_json_arry = "json_array_req";
        String url = "http://www.smartboxapp.esy.es/api/school_toppers.php";
        StringRequest getreq = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.dismiss();
                        try {
                            JSONArray jsonarr = new JSONArray(response);
                            for (int i=0;i<jsonarr.length();i++){
                                JSONObject jsonobj=jsonarr.getJSONObject(i);
                                ToppersDto obj = new ToppersDto();
                                obj.setTopper(jsonobj.getString("name"));
                                obj.setBatch(jsonobj.getString("batch"));
                                obj.setPic(jsonobj.getString("pic"));
                                listtoppers.add(obj);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                SnackBar.show(Activity_schooltoppers.this, "Try Again");
                // VolleyLog.d(TAG, "Error: " + error.getMessage());

            }

        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(getreq, tag_json_arry);
    }
}
