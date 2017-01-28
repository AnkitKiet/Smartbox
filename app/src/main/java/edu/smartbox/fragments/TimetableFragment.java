package edu.smartbox.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.smartbox.R;
import edu.smartbox.activities.DashboardActivity;
import edu.smartbox.global.AppConfig;
import edu.smartbox.global.AppController;
import edu.smartbox.holders.IconTreeItemHolder;
import edu.smartbox.holders.SelectableHeaderHolder;
import edu.smartbox.holders.SelectableItemHolder;
import edu.smartbox.ui.SnackBar;

/**
 * Created by Ankit on 08/01/17.
 */
public class TimetableFragment extends Fragment {
    private AndroidTreeView tView;
    @Bind(R.id.edtDay)
    Spinner edtDay;
    @Bind(R.id.btnFind)
    Button btnFind;
    SharedPreferences pref;
    private ProgressDialog mProgress;
    TreeNode folder1;
    String dayspin;


    public TimetableFragment() {

    }

    @Nullable

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_timetable, null, false);
        final ViewGroup containerView = (ViewGroup) rootView.findViewById(R.id.container);
        mProgress = new ProgressDialog(getActivity());
        ButterKnife.bind(this, rootView);
        pref = this.getActivity().getSharedPreferences("Options", Context.MODE_PRIVATE);

        final String sclass = pref.getString("sclass", "");
        final String ssec = pref.getString("ssec", "");

        List<String> categories = new ArrayList<String>();
        categories.add("Select Day Here");
        categories.add("monday");
        categories.add("tuesday");
        categories.add("wednesday");
        categories.add("thursday");
        categories.add("friday");
        categories.add("saturday");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edtDay.setAdapter(dataAdapter);
        edtDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dayspin = parent.getItemAtPosition(position).toString();
                    if(dayspin.equals("Select Day Here")){
                        //Toast.makeText(getActivity(), "You Haven't Chosen Day", Toast.LENGTH_SHORT).show();
                        btnFind.setVisibility(view.GONE);
                    }
                else {
                        btnFind.setVisibility(view.VISIBLE);

                    }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String day = dayspin;
                 TreeNode root = TreeNode.root();
                folder1 = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_check_circle, day)).setViewHolder(new SelectableHeaderHolder(getActivity()));
                request_timetable(sclass, ssec, day);
                containerView.setVisibility(View.VISIBLE);

                //TreeNode folder2 = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_check_circle, "Folder 2")).setViewHolder(new SelectableHeaderHolder(getActivity()));
                //TreeNode folder3 = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_check_circle, "Folder 3")).setViewHolder(new SelectableHeaderHolder(getActivity()));

                //fillFolder(folder2);
                //fillFolder(folder3);
                addchild(root, containerView);

            }
        });
        containerView.setVisibility(View.GONE);


        if (savedInstanceState != null) {
            String state = savedInstanceState.getString("tState");
            if (!TextUtils.isEmpty(state)) {
                tView.restoreState(state);
            }
        }
        return rootView;
    }

    private void addchild(TreeNode root, ViewGroup containerView) {

        root.addChildren(folder1);
        tView = new AndroidTreeView(getActivity(), root);
        tView.setDefaultAnimation(true);
        containerView.addView(tView.getView());
        tView.expandAll();

    }


    private void request_timetable(final String sclass, final String ssec, final String day) {

        String tag_json_arry = "json_array_req";

        String url = "http://www.smartboxapp.esy.es/api/view_timetable.php";
        mProgress.setMessage("Please wait");
        mProgress.show();
        final StringRequest getreq = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonobj = new JSONObject(response);
                            String first = jsonobj.getString("one");
                            String sec = jsonobj.getString("two");
                            String third = jsonobj.getString("three");
                            String four = jsonobj.getString("four");
                            String five = jsonobj.getString("five");
                            String six = jsonobj.getString("six");
                            String seven = jsonobj.getString("seven");
                            String eight = jsonobj.getString("eight");
                            fillFolder(folder1, first, sec, third, four, five, six, seven, eight);
                            mProgress.dismiss();

                            // Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
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
                params.put("sec", ssec);
                params.put("day", day);


                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(getreq, tag_json_arry);


    }


    private void fillFolder(TreeNode folder, String first, String sec, String third, String four, String five, String six, String seven, String eight) {
        TreeNode file1 = new TreeNode(first).setViewHolder(new SelectableItemHolder(getActivity()));
        TreeNode file2 = new TreeNode(sec).setViewHolder(new SelectableItemHolder(getActivity()));
        TreeNode file3 = new TreeNode(third).setViewHolder(new SelectableItemHolder(getActivity()));
        TreeNode file4 = new TreeNode(four).setViewHolder(new SelectableItemHolder(getActivity()));
        TreeNode file5 = new TreeNode(five).setViewHolder(new SelectableItemHolder(getActivity()));
        TreeNode file6 = new TreeNode(six).setViewHolder(new SelectableItemHolder(getActivity()));
        TreeNode file7 = new TreeNode(seven).setViewHolder(new SelectableItemHolder(getActivity()));
        TreeNode file8 = new TreeNode(eight).setViewHolder(new SelectableItemHolder(getActivity()));

        folder.addChildren(file1, file2, file3, file4, file5, file6, file7, file8);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("tState", tView.getSaveState());
    }
}
