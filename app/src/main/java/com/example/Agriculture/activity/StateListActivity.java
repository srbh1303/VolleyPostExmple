package com.example.Agriculture.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.Agriculture.R;
import com.example.Agriculture.adapters.UsersRecyclerAdapter;
import com.example.Agriculture.model.State;
import com.example.Agriculture.sql.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;


public class StateListActivity extends AppCompatActivity {

    private AppCompatActivity activity = StateListActivity.this;
    private RecyclerView recyclerViewUsers;
    private List<State> listStates;
    private UsersRecyclerAdapter usersRecyclerAdapter;
    private DatabaseHelper databaseHelper;
    private ImageView imgBack;
    private TextView txtHead;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_list);

        initViews();
        initObjects();
    }

    /**
     * This method is to initialize views
     */
    private void initViews() {
        recyclerViewUsers = (RecyclerView) findViewById(R.id.recyclerViewUsers);
        imgBack = findViewById(R.id.backBtn);
        txtHead = findViewById(R.id.title_head);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        txtHead.setText("State list");
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        listStates = new ArrayList<>();
        usersRecyclerAdapter = new UsersRecyclerAdapter(null, true, listStates);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewUsers.setLayoutManager(mLayoutManager);
        recyclerViewUsers.setItemAnimator(new DefaultItemAnimator());
        recyclerViewUsers.setHasFixedSize(true);
        recyclerViewUsers.setAdapter(usersRecyclerAdapter);
        databaseHelper = new DatabaseHelper(activity);

        getStateDataFromSQLite();
    }

    private void getStateDataFromSQLite() {
        listStates.clear();
        listStates.addAll(databaseHelper.getAllState());
    }
}
