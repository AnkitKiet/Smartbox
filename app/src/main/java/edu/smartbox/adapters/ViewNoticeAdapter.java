package edu.smartbox.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import edu.smartbox.R;
import edu.smartbox.dto.MarksDto;
import edu.smartbox.dto.NoticeDto;
import edu.smartbox.fragments.ViewNoticeFragment;

/**
 * Created by Ankit on 17/01/17.
 */
public class ViewNoticeAdapter extends RecyclerView.Adapter<ViewNoticeAdapter.NoticeViewHolder> {

    private Context context;
    private List<NoticeDto> data;

    public ViewNoticeAdapter(Context context, List<NoticeDto> data) {

        this.context = context;
        this.data = data;
    }


    @Override
    public NoticeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.row_notice, null);
        NoticeViewHolder holder = new NoticeViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(NoticeViewHolder holder, int position) {


        NoticeDto current = data.get(position);
        holder.date.setText(current.getDate());
        holder.detail.setText(current.getNotice());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class NoticeViewHolder extends RecyclerView.ViewHolder {

        TextView date;
        TextView detail;

        public NoticeViewHolder(View view) {
            super(view);

            DisplayMetrics metrics = new DisplayMetrics();
            ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(metrics.widthPixels, LinearLayout.LayoutParams.MATCH_PARENT);

            LinearLayout icon1 = (LinearLayout)view.findViewById(R.id.card);
            icon1.setLayoutParams(params);

            date = (TextView) view.findViewById(R.id.date);
            detail = (TextView) view.findViewById(R.id.detail);
        }

    }

}
