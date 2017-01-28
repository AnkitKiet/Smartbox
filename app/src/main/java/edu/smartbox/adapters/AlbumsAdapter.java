package edu.smartbox.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import edu.smartbox.R;
import edu.smartbox.activities.ActivityCafet;
import edu.smartbox.activities.Activity_Upcoming_Events;
import edu.smartbox.activities.Activity_photo_gallery;
import edu.smartbox.activities.Activity_schooltoppers;
import edu.smartbox.activities.SubDashboardActivity;
import edu.smartbox.dto.Dashboard_grid;
import edu.smartbox.util.NetworkCheck;

/**
 * Created by Ankit on 07/01/17.
 */
public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.MyViewHolder> {

    private Context mContext;
    private List<Dashboard_grid> albumList;
    private SharedPreferences pref;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }


    public AlbumsAdapter(Context mContext, List<Dashboard_grid> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_cards, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Dashboard_grid album = albumList.get(position);
        holder.title.setText(album.getName());

        Picasso.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow, position);
            }
        });
    }

    private void showPopupMenu(View view, int position) {
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_grid_dash, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(position));
        popup.show();
    }

    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        int position;

        public MyMenuItemClickListener(int position) {
            this.position = position;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    if (position == 0) {
                        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mContext);
                        LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View bottomSheetView = li.inflate(R.layout.bottomsheet_bustime, null);
                        bottomSheetDialog.setContentView(bottomSheetView);
                        bottomSheetDialog.show();
                        LinearLayout l1 = (LinearLayout) bottomSheetDialog.findViewById(R.id.l1);
                        LinearLayout l2 = (LinearLayout) bottomSheetDialog.findViewById(R.id.l2);
                        Button ApplyButton = (Button) bottomSheetDialog.findViewById(R.id.btnApply);
                        TextView txtPtime = (TextView) bottomSheetDialog.findViewById(R.id.txtPtime);
                        TextView txtDtime = (TextView) bottomSheetDialog.findViewById(R.id.txtDtime);
                        pref = mContext.getSharedPreferences("Options", mContext.MODE_PRIVATE);
                        String Ptime = pref.getString("ptime", "");
                        String Dtime = pref.getString("dtime", "");
                        String bus = pref.getString("bus", "");
                        if (bus.equalsIgnoreCase("yes")) {
                            txtDtime.setText(Dtime);
                            txtPtime.setText(Ptime);
                        } else {
                            l1.setVisibility(View.GONE);
                            l2.setVisibility(View.GONE);
                            ApplyButton.setVisibility(View.VISIBLE);
                        }
                        ApplyButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(NetworkCheck.isNetworkAvailable(mContext))
                                Toast.makeText(mContext, "You Have Requested For Bus Service", Toast.LENGTH_SHORT).show();
                            }
                        });

                        return true;
                    }
                    if (position == 1) {
                        Intent i = new Intent(mContext, SubDashboardActivity.class);
                        i.putExtra("content", "one");
                        mContext.startActivity(i);
                    }
                    if (position == 2) {
                        Intent i = new Intent(mContext, SubDashboardActivity.class);
                        i.putExtra("content", "two");
                        mContext.startActivity(i);
                    }
                    if (position == 3) {
                        Intent i = new Intent(mContext, ActivityCafet.class);
                        mContext.startActivity(i);
                    }
                    if (position == 4) {

                        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mContext);
                        LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View bottomSheetView = li.inflate(R.layout.bottomsheet_viewassignment, null);
                        bottomSheetDialog.setContentView(bottomSheetView);
                        bottomSheetDialog.show();

                        LinearLayout imageButton = (LinearLayout) bottomSheetDialog.findViewById(R.id.image);
                        LinearLayout textButton = (LinearLayout) bottomSheetDialog.findViewById(R.id.text);


                        imageButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (NetworkCheck.isNetworkAvailable(mContext))
                                {
                                    Intent intent = new Intent(mContext, SubDashboardActivity.class);
                                intent.putExtra("content", "three");
                                mContext.startActivity(intent);
                            }
                            }
                        });

                        textButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(NetworkCheck.isNetworkAvailable(mContext)) {
                                    Intent i = new Intent(mContext, SubDashboardActivity.class);
                                    i.putExtra("content", "four");
                                    mContext.startActivity(i);
                                }
                            }
                        });
                    }
                    if(position == 5){
                        Intent i = new Intent(mContext, Activity_schooltoppers.class);
                        mContext.startActivity(i);
                    }
                    if(position == 6){
                        Intent i = new Intent(mContext, Activity_photo_gallery.class);
                        mContext.startActivity(i);
                    }
                    if(position == 7){
                        Intent i = new Intent(mContext, Activity_Upcoming_Events.class);
                        mContext.startActivity(i);
                    }

                    return true;

                default:
                    Toast.makeText(mContext, "Something's Wrong", Toast.LENGTH_SHORT).show();
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }
}

