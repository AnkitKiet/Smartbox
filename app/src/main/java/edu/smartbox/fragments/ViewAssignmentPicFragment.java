package edu.smartbox.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.smartbox.R;
import edu.smartbox.adapters.AssignmentAdapter;
import edu.smartbox.adapters.ViewNoticeAdapter;
import edu.smartbox.dto.AssignmentDto;
import edu.smartbox.dto.NoticeDto;
import edu.smartbox.global.AppController;
import edu.smartbox.ui.SnackBar;
import edu.smartbox.util.ClassNameFormat;

/**
 * Created by Ankit on 28/01/17.
 */
public class ViewAssignmentPicFragment extends Fragment {

    private View parentView;
    @Bind(R.id.RecyclerView)
    RecyclerView noticeRecyclerView;
    private List<AssignmentDto> listassignment;
    private AssignmentAdapter adapter;
    private ProgressDialog pDialog;
    SharedPreferences pref;

    public ViewAssignmentPicFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parentView = inflater.inflate(R.layout.fragment_assignment_pic, container, false);
        ButterKnife.bind(this, parentView);
        populate();
        return parentView;
    }

    private void populate() {
        listassignment = new ArrayList<>();
        fetch_notice();
        adapter = new AssignmentAdapter(getActivity(), listassignment);
        noticeRecyclerView.setAdapter(adapter);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        noticeRecyclerView.setLayoutManager(layoutManager);
    }

    private void fetch_notice() {
        pref = this.getActivity().getSharedPreferences("Options", Context.MODE_PRIVATE);
        final String sclass = pref.getString("sclass", "");
        final String ssec = pref.getString("ssec", "");
        ClassNameFormat obj = new ClassNameFormat();
        final String clas = obj.classnamechange(sclass, ssec);
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Fetching Assignments");
        pDialog.show();
        String tag_json_arry = "json_array_req";
        String url = "http://www.smartboxapp.esy.es/api/view_assignment_pic.php";
        final StringRequest getreq = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.dismiss();
                        try {
                            JSONArray jsonarr = new JSONArray(response);
                            for (int i = 0; i < jsonarr.length(); i++) {
                                AssignmentDto nd = new AssignmentDto();
                                JSONArray obj = jsonarr.getJSONArray(i);
                                nd.setName(obj.getString(0));
                                nd.setUrl(obj.getString(1));
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
                SnackBar.show(getActivity(), "Try Again");
                // VolleyLog.d(TAG, "Error: " + error.getMessage());

            }

        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("class", clas);

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(getreq, tag_json_arry);
    }


}
