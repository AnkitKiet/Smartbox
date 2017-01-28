package edu.smartbox.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import edu.smartbox.R;
import edu.smartbox.dto.AttendanceDto;

/**
 * Created by Ankit on 20/01/17.
 */
public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.AttendanceViewHolder> {

    private Context context;
    private List<AttendanceDto> data;
    private List<Integer> stListabsent=null;


    public AttendanceAdapter(Context context, List<AttendanceDto> data) {

        this.context = context;
        this.data = data;
    }


    @Override
    public AttendanceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.attendance_row, null);
        AttendanceViewHolder holder = new AttendanceViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(AttendanceViewHolder holder, int position) {
        final AttendanceDto current = data.get(position);
        holder.txtName.setText("Student: " + current.getName());
        holder.txtRoll.setText("Roll No: " + current.getRoll());
        holder.chkAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox cb = (CheckBox) view;

                if (!cb.isChecked()) {
                   if (!stListabsent.contains(Integer.valueOf(current.getRoll()))) {
                        stListabsent.add(Integer.valueOf(current.getRoll()));
                    }

                }

                if (cb.isChecked()) {
                    if (stListabsent.contains(Integer.valueOf(current.getRoll()))) {
                        stListabsent.remove(Integer.valueOf(current.getRoll()));
                    }
                }
            }
        });
    }







    @Override
    public int getItemCount() {
        return data.size();
    }

    class AttendanceViewHolder extends RecyclerView.ViewHolder {

        CheckBox chkAttendance;
        TextView txtName;
        TextView txtRoll;

        public AttendanceViewHolder(View view) {
            super(view);
            txtName = (TextView) view.findViewById(R.id.txtName);
            txtRoll = (TextView) view.findViewById(R.id.txtRoll);
            chkAttendance = (CheckBox) view.findViewById(R.id.chkAttendance);
            stListabsent = new ArrayList<>();

        }

    }

    public void setFilter(ArrayList<AttendanceDto> newList){
        data=new ArrayList<>();
        data.addAll(newList);
        notifyDataSetChanged();
    }


    public List<Integer> getStudentAbsent() {
        return stListabsent;

    }
}

