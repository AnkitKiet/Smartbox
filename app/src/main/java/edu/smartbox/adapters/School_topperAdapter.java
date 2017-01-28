package edu.smartbox.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import edu.smartbox.R;
import edu.smartbox.dto.ToppersDto;

/**
 * Created by Ankit on 23/01/17.
 */
public class School_topperAdapter extends RecyclerView.Adapter<School_topperAdapter.TopperViewHolder> {

    private Context context;
    private List<ToppersDto> data;


    public School_topperAdapter(Context context, List<ToppersDto> data) {

        this.context = context;
        this.data = data;
    }


    @Override
    public TopperViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.school_toppers_card, null);
        TopperViewHolder holder = new TopperViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(TopperViewHolder holder, int position) {
        final ToppersDto current = data.get(position);
        holder.txtName.setText("Student : " + current.getTopper());
        holder.txtRoll.setText("Batch : " + current.getBatch());
        Picasso.with(context).load(current.getPic()).into(holder.pic);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class TopperViewHolder extends RecyclerView.ViewHolder {

        ImageView pic;
        TextView txtName;
        TextView txtRoll;

        public TopperViewHolder(View view) {
            super(view);
            txtName = (TextView) view.findViewById(R.id.txtName);
            txtRoll = (TextView) view.findViewById(R.id.txtBatch);
            pic = (ImageView) view.findViewById(R.id.user_pic);

        }

    }

    public void setFilter(ArrayList<ToppersDto> newList) {
        data = new ArrayList<>();
        data.addAll(newList);
        notifyDataSetChanged();
    }


}


