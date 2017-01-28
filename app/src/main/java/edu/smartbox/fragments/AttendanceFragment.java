package edu.smartbox.fragments;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.smartbox.R;
import edu.smartbox.activities.TeacherMarkAttendanceActivity;
import edu.smartbox.ui.DatePickerq;
import edu.smartbox.ui.SnackBar;

/**
 * Created by Ankit on 04/01/17.
 */
public class AttendanceFragment extends android.support.v4.app.Fragment {

    @Bind(R.id.btnSubmit)
    Button submit;
    @Bind(R.id.edtDate)
    TextView edtDate;
    @Bind(R.id.btnSubmit_update)
    Button update;
    @Bind(R.id.edtDate_update)
    TextView txtDateupdate;
    private View parentView;
    int year_x, month_x, day_x;
    String date;

    public AttendanceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parentView = inflater.inflate(R.layout.fragment_attendance_select_date, container, false);
        populate();
        return parentView;
    }

    private void populate() {
        ButterKnife.bind(this, parentView);
        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH) + 1;
        day_x = cal.get(Calendar.DAY_OF_MONTH);
        date = day_x + "-" + month_x + "-" + year_x;

        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerq dialog = new DatePickerq(view);
                FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
                dialog.show(ft, "date picker");

            }
        });

        txtDateupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerq dialog = new DatePickerq(view);
                FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
                dialog.show(ft, "date picker");

            }
        });


        edtDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {
                    DatePickerq dialog = new DatePickerq(view);
                    FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
                    dialog.show(ft, "date picker");
                }
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtDateupdate.getText().toString().length()==10){
                    Intent i = new Intent(getActivity(), TeacherMarkAttendanceActivity.class);
                    i.putExtra("date", edtDate.getText().toString().trim());
                    startActivity(i);

                }else {
                    SnackBar.show(getActivity(), "Enter The Date");
                }

            }
        });


            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (edtDate.getText().toString().length() == 10) {

                        Intent i = new Intent(getActivity(), TeacherMarkAttendanceActivity.class);
                        i.putExtra("date", edtDate.getText().toString().trim());
                        startActivity(i);
                    } else {
                        SnackBar.show(getActivity(), "Enter The Date");
                    }

                }
            });
        }


}


