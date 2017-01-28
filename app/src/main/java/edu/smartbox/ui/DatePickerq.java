package edu.smartbox.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

import edu.smartbox.R;

/**
 * Created by Ankit on 21/01/17.
 */
public class DatePickerq extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    TextView edtDate;

    String stringOfDate;
    public DatePickerq(){

    }

    @SuppressLint("ValidFragment")
    public DatePickerq(View view){
        edtDate=(TextView) view;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        //Use the current date as the default date in the date picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dpd=new DatePickerDialog(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_DARK, this, year, month, day);
        DatePicker dp = dpd.getDatePicker();
        //Set the DatePicker minimum date selection to current date
        c.add(Calendar.DAY_OF_MONTH,-6);

        dp.setMinDate(c.getTimeInMillis());//get the current day
        //dp.setMinDate(System.currentTimeMillis() - 1000);// Alternate way to get the current day
        c.add(Calendar.DAY_OF_MONTH,6);

        //Add 6 days with current date

        //Set the maximum date to select from DatePickerDialog
        dp.setMaxDate(c.getTimeInMillis());
        //Now DatePickerDialog have 7 days range to get selection any one from those dates
        return dpd; //Return the DatePickerDialog in app window


    }

    @Override
    public void onDateSet(android.widget.DatePicker datePicker, int i, int i1, int i2) {

          stringOfDate = i2 + "-" + i1+1 + "-" + i;
            edtDate.setText(stringOfDate);
    }


}