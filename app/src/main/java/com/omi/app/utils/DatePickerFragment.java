package com.omi.app.utils;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    private EditText mEtFolowUpDate;
    private TextView mTvCurrentDate;
    private int checkedValue;

    //Default Constructor.
    public DatePickerFragment() {
    }

    @SuppressLint("ValidFragment")
    public DatePickerFragment(EditText etfollowupdate) {
        mEtFolowUpDate = etfollowupdate;
        checkedValue = 0;
    }

    @SuppressLint("ValidFragment")
    public DatePickerFragment(TextView mTvCurrentDate) {
        this.mTvCurrentDate = mTvCurrentDate;
        checkedValue = 1;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        Calendar c_min = Calendar.getInstance();
        c_min.set(year, month - 3, day);

        Log.d("current date is", year + "/" + month + 1 + "/" + day);
        String date = year + "-" + month + "-" + day;
        DatePickerDialog mDatePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
        mDatePickerDialog.getDatePicker().setMinDate(c_min.getTimeInMillis() - 1000);
        return mDatePickerDialog;
    }

    //Setting current date as default for date picker
    public void onDateSet(DatePicker view, int year, int month, int day) {
        String date = year + "-" + (month + 1) + "-" + day;
        if (checkedValue == 0) {
            mEtFolowUpDate.setText(date);
        } else if (checkedValue == 1) {
            mTvCurrentDate.setText(date);
        }
    }
}
