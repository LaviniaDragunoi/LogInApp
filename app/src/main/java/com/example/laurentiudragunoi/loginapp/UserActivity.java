package com.example.laurentiudragunoi.loginapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

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
    public List<Employee> employeeList = new ArrayList<>();


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

        Intent intent = getIntent();

            employeeList = intent.getParcelableArrayListExtra("employeeList");

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        listRV.setLayoutManager(layoutManager);

            adapter = new EmployeeAdapter(this, employeeList);
            listRV.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.user_menu, menu);
        return true;
    }

}
