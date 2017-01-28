package edu.smartbox.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

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
import edu.smartbox.adapters.AssignmentAdapter;
import edu.smartbox.dto.AssignmentDto;
import edu.smartbox.global.AppController;
import edu.smartbox.ui.SnackBar;
import edu.smartbox.util.ClassNameFormat;

/**
 * Created by Ankit on 28/01/17.
 */
public class Activity_photo_gallery extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private View parentView;
    @Bind(R.id.RecyclerView)
    RecyclerView noticeRecyclerView;
    private List<AssignmentDto> listassignment;
    private AssignmentAdapter adapter;
    private ProgressDialog pDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_event);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Photo Gallery");
        listassignment = new ArrayList<>();
        adapter = new AssignmentAdapter(Activity_photo_gallery.this, listassignment);
        noticeRecyclerView.setAdapter(adapter);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(Activity_photo_gallery.this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        noticeRecyclerView.setLayoutManager(layoutManager);
        fetch_notice();
    }

    private void fetch_notice() {
        pDialog = new ProgressDialog(Activity_photo_gallery.this);
        pDialog.setMessage("Fetching Pics...");
        pDialog.show();
        String tag_json_arry = "json_array_req";
        String url = "http://www.smartboxapp.esy.es/api/view_photo_gallery.php";
        final StringRequest getreq = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.dismiss();
                        try {
                            JSONArray jsonarr = new JSONArray(response);
                            for (int i = 0; i < jsonarr.length(); i++) {
                                AssignmentDto nd = new AssignmentDto();
                                JSONObject obj = jsonarr.getJSONObject(i);
                                nd.setName(obj.getString("category"));
                                nd.setUrl(obj.getString("url"));
                                listassignment.add(nd);
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
                SnackBar.show(Activity_photo_gallery.this, "Try Again");
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

