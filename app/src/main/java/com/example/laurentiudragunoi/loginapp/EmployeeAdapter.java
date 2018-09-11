package com.example.laurentiudragunoi.loginapp;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static java.lang.String.valueOf;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder>{

    private static final String EMPLOYEE = "employee";
    private List<Employee> employeeList;
    private Context context;

    public EmployeeAdapter(Context context, List<Employee> employeeList){
        this.context = context;
        this.employeeList = employeeList;

    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item, viewGroup, false);
        return new EmployeeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final EmployeeViewHolder employeeViewHolder, int position) {
        final Employee employee = employeeList.get(position);

        TextView employeeNameTextView = employeeViewHolder.employeeNameTV;
        String employeeNameString = employee.getName();
        employeeNameTextView.setText(employeeNameString);

        TextView employeeAcountTextView = employeeViewHolder.employeeBankAccountTV;
        String bankAccountString = employee.getBankAccount();
        employeeAcountTextView.setText(bankAccountString);

        TextView employeeSalaryTextView = employeeViewHolder.employeeAmountDueTV;
        String amountString = valueOf(employee.getAmount());
        employeeSalaryTextView.setText(amountString);

        employeeViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditActivity.class);
                intent.putExtra(EMPLOYEE, employee);
                employeeViewHolder.itemView.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (employeeList == null) return 0;
        return employeeList.size();
    }

    public class EmployeeViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.name)
        TextView employeeNameTV;
        @BindView(R.id.bank_account)
        TextView employeeBankAccountTV;
        @BindView(R.id.amount)
        TextView employeeAmountDueTV;

        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
