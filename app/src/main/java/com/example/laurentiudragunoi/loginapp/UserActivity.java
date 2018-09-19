package com.example.laurentiudragunoi.loginapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserActivity extends AppCompatActivity {
    @BindView(R.id.fab_new_entry_added)
    FloatingActionButton addEmployee;
    @BindView(R.id.list)
    RecyclerView listRV;
    EmployeeAdapter adapter;
    @BindView(R.id.empty_message)
    LinearLayout emptyMessage;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    public List<Employee> employeeList;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mEmployeeDatabaseReference;
    private ChildEventListener mChildEventListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);
        addEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, EditActivity.class);
                startActivity(intent);
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        listRV.setLayoutManager(layoutManager);
        showLoading();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mEmployeeDatabaseReference = mFirebaseDatabase.getReference().child("employee");

        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                getEmployeeList(dataSnapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                getEmployeeList(dataSnapshot);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                getEmployeeList(dataSnapshot);

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //empty
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Error message:", databaseError.getMessage());
            }
        };
       mEmployeeDatabaseReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               getEmployeeList(dataSnapshot);
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });

    }

    private void getEmployeeList(DataSnapshot dataSnapshot) {

        employeeList = new ArrayList<>();
        for(DataSnapshot singleDataSnapshot :  dataSnapshot.getChildren()){
            Employee employee = singleDataSnapshot.getValue(Employee.class);
            employeeList.add(employee);
            listRV.setAdapter(new EmployeeAdapter(this, employeeList));

        }
        showLoaded();
        if(employeeList.isEmpty()){
            showEmpty();
        }else emptyMessage.setVisibility(View.GONE);
    }

    private void showEmpty() {
        if(employeeList.isEmpty()){
        progressBar.setVisibility(View.GONE);
        emptyMessage.setVisibility(View.VISIBLE);
        }else {
            emptyMessage.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.user_menu, menu);
        return true;
    }

    private void showLoading(){
        progressBar.setVisibility(View.VISIBLE);
    }

    private void showLoaded(){
        progressBar.setVisibility(View.GONE);
        listRV.setVisibility(View.VISIBLE);
    }
}
