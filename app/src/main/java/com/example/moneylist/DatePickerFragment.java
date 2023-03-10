package com.example.moneylist;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatePickerFragment extends DialogFragment {
    private static final String ARG_DATE = "date";
    private DatePicker mdatePicker;
    public static final String EXTRA_DATE = "com.example.moneylist.date";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Date date=(Date)getArguments().getSerializable(ARG_DATE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        View v= LayoutInflater.from(getActivity()).inflate(R.layout.dialog_date,null);

        mdatePicker = (DatePicker) v.findViewById(R.id.dialog_date_picker);
        mdatePicker.init(year,month,day,null);


        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("记账日期")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int year = mdatePicker.getYear();
                        int month = mdatePicker.getMonth();
                        int day = mdatePicker.getDayOfMonth();
                        //变成Date形式
                        Date date = new GregorianCalendar(year ,month ,day).getTime();
                        sendResult(Activity.RESULT_OK,date);
                    }
                })
                .create();
    }
    public static DatePickerFragment newInstance(Date date){
        Bundle args=new Bundle();
        args.putSerializable(ARG_DATE,date);
        DatePickerFragment fragment=new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void sendResult(int resultCode, Date date){
        if(getTargetFragment() == null){
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE,date);
        getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode,intent);
    }
}
