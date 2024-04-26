package com.dhian.itube.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dhian.itube.ListItem;
import com.dhian.itube.PlayListAdapter;
import com.dhian.itube.PlaylistController;
import com.dhian.itube.R;
import com.dhian.itube.RecyclerTouchListener;
import com.dhian.itube.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MyPlayListActivity extends AppCompatActivity {
    private List<ListItem> listItemList;
    private RecyclerView recyclerView;
    private PlayListAdapter playListAdapter;
    private PlaylistController playlistController;
    private ActivityMainBinding binding;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userId = getIntent().getStringExtra("userId");

        playlistController = new PlaylistController(MyPlayListActivity.this);

        recyclerView = findViewById(R.id.recyclerViewTasks);


        listItemList = new ArrayList<>();
        playListAdapter = new PlayListAdapter(listItemList, getApplicationContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(playListAdapter);

        refreshList();


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                ListItem data = listItemList.get(position);
                Intent intent = new Intent(MyPlayListActivity.this, PlayerActivity.class);
                intent.putExtra("url", data.getUrl());
                startActivity(intent);
            }


        }));

    }


    @Override
    protected void onResume() {
        super.onResume();
        refreshList();
    }

    public void refreshList() {
        if (playListAdapter == null) return;
        listItemList = playlistController.getPlayList(userId);
        playListAdapter.setTaskList(listItemList);
        playListAdapter.notifyDataSetChanged();
    }
}