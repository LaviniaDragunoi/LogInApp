package com.example.laurentiudragunoi.loginapp;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import static java.lang.String.valueOf;

/**
 * Created by Lavinia Dragunoi on 9/14/2017.
 */

public class EmployeeAdapter extends ArrayAdapter<Employee>{
    public EmployeeAdapter(Context context, int resource, List<Employee> objects) {
        super(context, resource, objects);
    }
    public View getView(int position, View convertView, ViewGroup parent){

        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.list_item, parent, false);

        }

        TextView nameView = (TextView) convertView.findViewById(R.id.name);
        TextView bankAccountView = (TextView) convertView.findViewById(R.id.bankAccount);
        TextView amountView = (TextView) convertView.findViewById(R.id.amount);

        Employee employeeDetails = getItem(position);

       if (employeeDetails != null){
        nameView.setText(employeeDetails.getName());
        bankAccountView.setText(employeeDetails.getBankAccount());
        amountView.setText(valueOf(employeeDetails.getAmount()));
       }
        return convertView;
    }
}
