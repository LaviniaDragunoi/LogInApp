package com.example.laurentiudragunoi.loginapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static java.lang.Double.valueOf;

public class EditActivity extends AppCompatActivity {

    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mEmployeeDatabaseReference;
    @BindView(R.id.employee_name)
    EditText employeeName;
    @BindView(R.id.employee_bank_account)
    EditText employeeBankAcount;
    @BindView(R.id.employee_salary)
    EditText employeeSalary;

    String employeeNameString;
    String employeeAccountString;
    double employeeSalaryAmount;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ButterKnife.bind(this);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mEmployeeDatabaseReference = mFirebaseDatabase.getReference().child("employee");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.edit_menu, menu);
        return true;
    }

    @Override
    public boolean  onOptionsItemSelected(MenuItem menuItem){
        int id = menuItem.getItemId();
        if(id == R.id.action_save){
                saveEmployeeInDb();
                finish();
            }else if(id == R.id.action_delete){
            //delete item
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void saveEmployeeInDb() {
        employeeNameString = employeeName.getText().toString();
        employeeAccountString = employeeBankAcount.getText().toString();
        employeeSalaryAmount = valueOf(employeeSalary.getText().toString());
        Employee employeeEntry = new Employee(employeeNameString, employeeAccountString, employeeSalaryAmount);
        mEmployeeDatabaseReference.push().setValue(employeeEntry);

    }
}
