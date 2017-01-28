package edu.smartbox.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.smartbox.R;
import edu.smartbox.dto.MarksDto;

/**
 * Created by Ankit on 11/01/17.
 */
public class ViewMarksAdapter extends RecyclerView.Adapter<ViewMarksAdapter.MarksViewHolder> {

    private Context context;
    private List<MarksDto> data;

    public ViewMarksAdapter(Context context, List<MarksDto> data) {

        this.context = context;
        this.data = data;
    }


    @Override
    public MarksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.row_marks, null);
        MarksViewHolder holder = new MarksViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MarksViewHolder holder, int position) {
        MarksDto current = data.get(position);
        holder.txtMark.setText(current.getMarks());
        holder.txtSub.setText(current.getSubject());
        if (Integer.valueOf(current.getMarks()) > 70) {
            holder.progress.setProgressDrawable(context.getResources().getDrawable(R.drawable.progresshorizontalgreen));
        }else
            holder.progress.setProgressDrawable(context.getResources().getDrawable(R.drawable.progresshorizontal));

        holder.progress.setProgress(Integer.valueOf(current.getMarks()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MarksViewHolder extends RecyclerView.ViewHolder {

        TextView txtSub;
        TextView txtMark;
        ProgressBar progress;

        public MarksViewHolder(View view) {
            super(view);
            txtMark = (TextView) view.findViewById(R.id.txtMarks);
            txtSub = (TextView) view.findViewById(R.id.txtSubject);
            progress = (ProgressBar) view.findViewById(R.id.progressBar1);
        }

    }

    public void setFilter(ArrayList<MarksDto> newList){
        data=new ArrayList<>();
        data.addAll(newList);
        notifyDataSetChanged();
    }

}
