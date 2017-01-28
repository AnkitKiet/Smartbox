package edu.smartbox.fragments;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import edu.smartbox.adapters.AlbumsAdapter;
import edu.smartbox.dto.Dashboard_grid;
import edu.smartbox.dto.NoticeDto;
import edu.smartbox.global.AppController;
import edu.smartbox.ui.SnackBar;

/**
 * Created by Ankit on 04/01/17.
 */
public class HomeFragment extends android.support.v4.app.Fragment {

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    private AlbumsAdapter adapter;
    private List<Dashboard_grid> albumList;
    private View parentView;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parentView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, parentView);
        pref = getActivity().getSharedPreferences("Options", getContext().MODE_PRIVATE);
        String id=pref.getString("libid","");
        populate();
        get_bus_time(id);
        return parentView;
    }

    private void get_bus_time(final String id) {

        String tag_json_arry = "json_array_req";
        String url = "http://www.smartboxapp.esy.es/api/bus_time.php";
        final StringRequest getreq = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonobj = new JSONObject(response);
                            String route = jsonobj.getString("route");
                            String pick_time = jsonobj.getString("pick_time");
                            String drop_time = jsonobj.getString("drop_time");
                            editor = pref.edit();
                            editor.putString("ptime", pick_time);
                            editor.putString("dtime", drop_time);
                            editor.commit();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
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
                params.put("id",id);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(getreq, tag_json_arry);
    }


    private void populate() {

        albumList = new ArrayList<>();
        adapter = new AlbumsAdapter(getContext(), albumList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareAlbums();


    }

    private void prepareAlbums() {
        int[] covers = new int[]{
                R.drawable.album1,
                R.drawable.album2,
                R.drawable.album3,
                R.drawable.album4,
                R.drawable.album5,
                R.drawable.album6,
                R.drawable.album7,
                R.drawable.album8,
             };

        Dashboard_grid a = new Dashboard_grid("Bus Timing", covers[0]);
        albumList.add(a);

        a = new Dashboard_grid("Notice Board", covers[1]);
        albumList.add(a);

        a = new Dashboard_grid("Time Table", covers[2]);
        albumList.add(a);

        a = new Dashboard_grid("Cafet Menu", covers[3]);
        albumList.add(a);

        a = new Dashboard_grid("View Assignment", covers[4]);
        albumList.add(a);

        a = new Dashboard_grid("School Toppers", covers[5]);
        albumList.add(a);

        a = new Dashboard_grid("Photo Gallery", covers[6]);
        albumList.add(a);

        a = new Dashboard_grid("School Events", covers[7]);
        albumList.add(a);

    /*    a = new Dashboard_grid("Event Winners", covers[8]);
        albumList.add(a);

        a = new Dashboard_grid("Fee Details", covers[9]);
        albumList.add(a);
*/
        adapter.notifyDataSetChanged();
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}

