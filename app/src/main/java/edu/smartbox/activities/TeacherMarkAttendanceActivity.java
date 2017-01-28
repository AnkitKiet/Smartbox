package edu.smartbox.activities;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.smartbox.R;
import edu.smartbox.adapters.AttendanceAdapter;
import edu.smartbox.dto.AttendanceDto;
import edu.smartbox.global.AppController;
import edu.smartbox.ui.SnackBar;
import edu.smartbox.util.ClassNameFormat;
import edu.smartbox.util.NetworkCheck;

/**
 * Created by Ankit on 20/01/17.
 */
public class TeacherMarkAttendanceActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.submit)
    Button submit;
    @Bind(R.id.attendanceRecyclerView)
    RecyclerView attendanceRecyclerView;
    private AttendanceAdapter adapter;
    private List<AttendanceDto> listattendance;
    private ProgressDialog pDialog;
    SharedPreferences pref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_attendance);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Mark Attendance");
        pref = getApplication().getSharedPreferences("Options", MODE_PRIVATE);
        final String tclass = pref.getString("tclass", "");
        final String tsec = pref.getString("tsec", "");
        Bundle extras = getIntent().getExtras();
        final String date = extras.getString("date");

        listattendance = new ArrayList<>();
        fetch_students(tclass, tsec);
        adapter = new AttendanceAdapter(TeacherMarkAttendanceActivity.this, listattendance);
        attendanceRecyclerView.setAdapter(adapter);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(TeacherMarkAttendanceActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        attendanceRecyclerView.setLayoutManager(layoutManager);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Integer> stListabsent = ((AttendanceAdapter) adapter)
                        .getStudentAbsent();

                Integer[] stArrayabsent = new Integer[stListabsent.size()];
                stListabsent.toArray(stArrayabsent);
                //Assume every class has 30 students
                List<Integer> stListpresent = new ArrayList<Integer>();
                for (int i = 1; i <= 30; i++) {
                    stListpresent.add(i);
                }
                for (int i = 0; i < stArrayabsent.length; i++) {
                    if (stListpresent.contains(stArrayabsent[i])) {
                        stListpresent.remove(stArrayabsent[i]);
                    }

                }
                Toast.makeText(TeacherMarkAttendanceActivity.this, "Absent: " + stListabsent, Toast.LENGTH_SHORT).show();
                Integer[] stArraypresent = new Integer[stListpresent.size()];
                stListpresent.toArray(stArraypresent);
                String present = Arrays.toString(stArraypresent);
                if(NetworkCheck.isNetworkAvailable(TeacherMarkAttendanceActivity.this)) {
                    insertAttendance(present, tclass, tsec, date);
                }
                else
                    SnackBar.show(TeacherMarkAttendanceActivity.this,"No Internet Connection");
            }
        });

    }

    private void insertAttendance(final String stListpresent, final String class_no, final String section, final String date) {

        ClassNameFormat obj = new ClassNameFormat();
        final String class_name = obj.classnamechange(class_no, section);
        final String present = stListpresent.substring(1, stListpresent.length() - 1);
        pDialog = new ProgressDialog(TeacherMarkAttendanceActivity.this);
        pDialog.setMessage("Inserting Attendance");
        pDialog.show();
        String tag_json_arry = "json_array_req";
        String url = "http://www.smartboxapp.esy.es/api/mark_attendance_submit.php";
        StringRequest getreq = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.dismiss();
                        try {
                            JSONObject jsonobj = new JSONObject(response);
                            Boolean result = jsonobj.getBoolean("updated");
                            if (result) {
                                Toast.makeText(TeacherMarkAttendanceActivity.this, "Successfully Marked", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(TeacherMarkAttendanceActivity.this, "Sorry! Try Again", Toast.LENGTH_SHORT).show();
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
                SnackBar.show(TeacherMarkAttendanceActivity.this, "Try Again");
                // VolleyLog.d(TAG, "Error: " + error.getMessage());

            }

        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("present_students", present.trim());
                params.put("date", date);
                params.put("class", class_name);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(getreq, tag_json_arry);
    }


    private void fetch_students(final String class_no, final String section) {

        pDialog = new ProgressDialog(TeacherMarkAttendanceActivity.this);
        pDialog.setMessage("Fetching Students");
        pDialog.show();
        String tag_json_arry = "json_array_req";
        String url = "http://www.smartboxapp.esy.es/api/mark_attendance_view_stud.php";
        StringRequest getreq = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.dismiss();
                        try {
                            JSONArray jsonarr = new JSONArray(response);
                            for (int i = 0; i < response.length(); i++) {
                                JSONArray jsona = jsonarr.getJSONArray(i);
                                AttendanceDto obj = new AttendanceDto();
                                obj.setRoll(jsona.getString(0));
                                obj.setName(jsona.getString(1));
                                listattendance.add(obj);
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
                SnackBar.show(TeacherMarkAttendanceActivity.this, "Try Again");
                // VolleyLog.d(TAG, "Error: " + error.getMessage());

            }

        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("class", class_no);
                params.put("section", section);
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
        ArrayList<AttendanceDto> newList = new ArrayList<>();
        for (AttendanceDto attendanceDto : listattendance) {
            String sub = attendanceDto.getName().toLowerCase();
            if (sub.contains(newText))
                newList.add(attendanceDto);
        }
        adapter.setFilter(newList);

        return true;
    }

}

