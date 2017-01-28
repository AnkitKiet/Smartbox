package edu.smartbox.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.smartbox.R;
import edu.smartbox.adapters.ViewMarksAdapter;
import edu.smartbox.dto.MarksDto;
import edu.smartbox.global.AppConfig;
import edu.smartbox.global.AppController;
import edu.smartbox.ui.SnackBar;

/**
 * Created by Ankit on 11/01/17.
 */
public class ViewMarksActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.RecyclerView)
    RecyclerView marksRecyclerView;
    private List<MarksDto> listmarks;
    private ViewMarksAdapter adapter;
    private ProgressDialog pDialog;
    SharedPreferences pref;
    @Bind(R.id.progressBartotal)
    ProgressBar pt;
    @Bind(R.id.percent)
    TextView percent;
    @Bind(R.id.status)
    TextView status;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_marks);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("View Marks");
        pref = getApplication().getSharedPreferences("Options", MODE_PRIVATE);
        final String sclass = pref.getString("sclass", "");
        final String libid = pref.getString("libid", "");

        listmarks = new ArrayList<>();
        Bundle extras = getIntent().getExtras();
        String exam = extras.getString("exam");
        String session = extras.getString("session");
        //function call to get value
        fetch_marks(sclass, libid, exam, session);

        adapter = new ViewMarksAdapter(ViewMarksActivity.this, listmarks);
        marksRecyclerView.setAdapter(adapter);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(ViewMarksActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        marksRecyclerView.setLayoutManager(layoutManager);
    }

    private void fetch_marks(final String sclass, final String libid, final String exam, final String session) {
        pDialog = new ProgressDialog(ViewMarksActivity.this);
        pDialog.setMessage("Fetching Marks");
        pDialog.show();
        String tag_json_arry = "json_array_req";
        String url = "http://www.smartboxapp.esy.es/api/view_marks.php";
        StringRequest getreq = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.dismiss();
                        try {
                            int sum = 0, n = 0;
                            JSONObject jsonobj = new JSONObject(response);
                            String respos = response.substring(1);
                            String[] arraysubject = respos.split(",");
                            String subject[] = new String[8];
                            for (int i = 0; i < arraysubject.length; i++) {
                                MarksDto obj = new MarksDto();
                                String sub = arraysubject[i];
                                sub = sub.substring(1);
                                int index = sub.indexOf('"');
                                sub = sub.substring(0, index);
                                if (sub.equals("")) {
                                    continue;
                                } else {
                                    subject[i] = sub;
                                    sum = sum + Integer.valueOf(jsonobj.getString(subject[i]));
                                    n++;
                                    // Toast.makeText(ViewMarksActivity.this, subject[i], Toast.LENGTH_SHORT).show();
                                    obj.setMarks(jsonobj.getString(subject[i]));
                                    obj.setSubject(subject[i]);
                                    listmarks.add(obj);
                                }
                            }
                            int percen = sum / n;
                            pt.setProgress(percen);
                            percent.setText(String.valueOf(percen) + "%");
                            if (percen > 40)
                                status.setText("Pass");
                            else
                                status.setText("Fail");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                SnackBar.show(ViewMarksActivity.this, "Try Again");
                // VolleyLog.d(TAG, "Error: " + error.getMessage());

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        newText = newText.toLowerCase();
        ArrayList<MarksDto> newList = new ArrayList<>();
        for (MarksDto marksDto : listmarks) {
            String sub = marksDto.getSubject().toLowerCase();
            if (sub.contains(newText))
                newList.add(marksDto);
        }
        adapter.setFilter(newList);

        return true;
    }
}
