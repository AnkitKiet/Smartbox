package edu.smartbox.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import edu.smartbox.R;
import edu.smartbox.dto.AssignmentDto;

/**
 * Created by Ankit on 28/01/17.
 */
public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.AssignmentViewHolder> {

    private Context context;
    private List<AssignmentDto> data;

    public AssignmentAdapter(Context context, List<AssignmentDto> data) {

        this.context = context;
        this.data = data;
    }


    @Override
    public AssignmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.row_assignment, null);
        AssignmentViewHolder holder = new AssignmentViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(AssignmentViewHolder holder, int position) {


        AssignmentDto current = data.get(position);
        holder.detail.setText(current.getName());
        Picasso.with(context).load(current.getUrl()).into(holder.pic);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class AssignmentViewHolder extends RecyclerView.ViewHolder {

        TextView detail;
        ImageView pic;

        public AssignmentViewHolder(View view) {
            super(view);

            DisplayMetrics metrics = new DisplayMetrics();
            ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(metrics.widthPixels, LinearLayout.LayoutParams.MATCH_PARENT);

            LinearLayout icon1 = (LinearLayout) view.findViewById(R.id.card);
            icon1.setLayoutParams(params);

            detail = (TextView) view.findViewById(R.id.txtTitle);
            pic = (ImageView) view.findViewById(R.id.image);
        }

    }

}
