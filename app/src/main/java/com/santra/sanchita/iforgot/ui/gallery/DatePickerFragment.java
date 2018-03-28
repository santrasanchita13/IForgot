package com.santra.sanchita.iforgot.ui.gallery;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import com.santra.sanchita.iforgot.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by sanchita on 26/3/18.
 */

public class DatePickerFragment extends DialogFragment {
    DatePickerDialog.OnDateSetListener ondateSet;

    public DatePickerFragment() {
    }

    public void setCallBack(DatePickerDialog.OnDateSetListener ondate) {
        startDate1 = startDate;
        endDate1 = endDate;
        ondateSet = ondate;
    }

    private int year, month, day;
    private String startDate, endDate;
    private static String startDate1, endDate1;

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        year = args.getInt("year");
        month = args.getInt("month");
        day = args.getInt("day");
        startDate = args.getString("fromDate"); //maxDate Date after this cannot be selected
        endDate = args.getString("toDate"); //minDate Dates before this cannot be selected
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.CustomDialog, ondateSet, year, month, day);
        datePickerDialog.setTitle(this.getTag());
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        try {
            if (startDate != null) {
                Date d = f.parse(startDate);
                long milliseconds = d.getTime();
                datePickerDialog.getDatePicker().setMaxDate(milliseconds - 1000L);
            }
            if (endDate != null) {
                Date d = f.parse(endDate);
                long milliseconds = d.getTime();
                datePickerDialog.getDatePicker().setMinDate(milliseconds + 1000L);
            }
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return datePickerDialog;
    }
}
