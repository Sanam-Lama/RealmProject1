package com.example.realmproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import io.realm.RealmResults;

public class TestActivity extends AppCompatActivity {

    RecyclerView recycler_view;
    RecyclerViewAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Integer size = getIntent().getIntExtra("size", 0);
        Integer index = getIntent().getIntExtra("startingIndex", 0);
//        Log.e("size", size.toString());

        recycler_view = (RecyclerView)findViewById(R.id.recyclerView);
        layoutManager = new GridLayoutManager(getApplicationContext(), size);  // spanCount refers to the number of columns
        recycler_view.setLayoutManager(layoutManager);
        recycler_view.setHasFixedSize(true);

        adapter = new RecyclerViewAdapter(size, index);
        recycler_view.setAdapter(adapter);
    }
}
