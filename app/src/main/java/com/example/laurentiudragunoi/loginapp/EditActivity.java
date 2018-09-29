package com.example.laurentiudragunoi.loginapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.laurentiudragunoi.loginapp.EmployeeAdapter.EMPLOYEE;
import static com.example.laurentiudragunoi.loginapp.MainActivity.USER_NAME;
import static java.lang.Double.valueOf;

public class EditActivity extends AppCompatActivity {

    private static final String TAG = EditActivity.class.getSimpleName();
  //  private static final String HIDE_STATE = "hideDeleteOptionMenu";
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
    private Employee currentEmployee;
    EmployeeAdapter adapter;
    private String userName;
   // private String mState;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if(intent != null && intent.hasExtra(EMPLOYEE)) {
            currentEmployee = intent.getParcelableExtra(EMPLOYEE);
            employeeName.setText(currentEmployee.getName());
            employeeBankAcount.setText(currentEmployee.getBankAccount());
            double salaryAmount = currentEmployee.getAmount();
            employeeSalary.setText(String.valueOf(salaryAmount));
            userName = currentEmployee.getUserName();

        }else if(intent != null && intent.hasExtra(USER_NAME)){
            userName = intent.getStringExtra(USER_NAME);
        }
        if(currentEmployee == null){
            setTitle(getString(R.string.add_title));

        }else {  setTitle(getString(R.string.edit_title));
        }
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mEmployeeDatabaseReference = mFirebaseDatabase.getReference().child("employee");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.edit_menu, menu);
        MenuItem deleteItem = menu.findItem(R.id.action_delete);
        if(currentEmployee != null) {
            deleteItem.setVisible(true);
        }
        return true;
    }

    @Override
    public boolean  onOptionsItemSelected(MenuItem menuItem){
        int id = menuItem.getItemId();
        if(id == R.id.action_save){
            if(currentEmployee != null){
                updateEmployeeInDb();
                finish();
            }else {
                saveEmployeeInDb();
                finish();
            }

            }else if(id == R.id.action_delete){
            //delete item
            deleteEmployee(currentEmployee);
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void deleteEmployee(Employee employee) {
        Query employeeQuery = mEmployeeDatabaseReference.orderByChild("name").equalTo(employee.getName());
       employeeQuery.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               for (DataSnapshot employeeSnapshot: dataSnapshot.getChildren()){
                   employeeSnapshot.getRef().removeValue();

               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {
               Log.e(TAG, "onCancelled", databaseError.toException());
           }
       });

    }

    private void saveEmployeeInDb() {

        employeeNameString = this.employeeName.getText().toString();
        employeeAccountString = this.employeeBankAcount.getText().toString();
        employeeSalaryAmount = valueOf(this.employeeSalary.getText().toString());

        Employee employeeEntry = new Employee(userName,employeeNameString,employeeAccountString,employeeSalaryAmount);
        mEmployeeDatabaseReference.push().setValue(employeeEntry);
        }

    private void updateEmployeeInDb() {
        String name = currentEmployee.getName();
        Query employeeQuery = mEmployeeDatabaseReference.orderByChild("name").equalTo(currentEmployee.getName());

        employeeNameString = this.employeeName.getText().toString();
        employeeAccountString = this.employeeBankAcount.getText().toString();
        employeeSalaryAmount = valueOf(this.employeeSalary.getText().toString());

        String key = mEmployeeDatabaseReference.getKey();
         currentEmployee.setName(employeeNameString);
        currentEmployee.setBankAccount(employeeAccountString);
        currentEmployee.setAmount(employeeSalaryAmount);

        employeeQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot employeeSnapshot: dataSnapshot.getChildren()){
                    employeeSnapshot.getRef().setValue(currentEmployee);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });

    }

}
