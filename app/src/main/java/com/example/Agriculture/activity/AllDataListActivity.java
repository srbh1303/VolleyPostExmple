package com.example.Agriculture.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.Agriculture.R;
import com.example.Agriculture.adapters.AllDataRecyclerAdapter;
import com.example.Agriculture.adapters.UsersRecyclerAdapter;
import com.example.Agriculture.model.User;
import com.example.Agriculture.sql.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class AllDataListActivity extends AppCompatActivity {

    private AppCompatActivity activity = AllDataListActivity.this;
    private RecyclerView recyclerViewUsers;
    private List<User> listUsers;
    private AllDataRecyclerAdapter usersRecyclerAdapter;
    private DatabaseHelper databaseHelper;
    private ImageView imgBack;
    private TextView txtHead;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alldata_list);

        initViews();
        initObjects();
    }

    /**
     * This method is to initialize views
     */
    private void initViews() {
        recyclerViewUsers = findViewById(R.id.recyclerViewUsers);
        imgBack = findViewById(R.id.backBtn);
        txtHead = findViewById(R.id.title_head);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        txtHead.setText("ALL Details");
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        listUsers = new ArrayList<>();
        usersRecyclerAdapter = new AllDataRecyclerAdapter(listUsers);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewUsers.setLayoutManager(mLayoutManager);
        recyclerViewUsers.setItemAnimator(new DefaultItemAnimator());
        recyclerViewUsers.setHasFixedSize(true);
        recyclerViewUsers.setAdapter(usersRecyclerAdapter);
        databaseHelper = new DatabaseHelper(activity);

        getJoinDataFromSQLite();
    }

    private void getJoinDataFromSQLite() {
        listUsers.clear();
        listUsers.addAll(databaseHelper.getALLCropByState());
    }
}
