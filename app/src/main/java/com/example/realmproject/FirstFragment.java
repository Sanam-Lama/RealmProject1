package com.example.realmproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FirstFragment extends Fragment {

    Integer size, startIndex;

    RecyclerView recycler_view;
    RecyclerViewAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    public FirstFragment() {
    }

    public static FirstFragment newInstance(int size, int startIndex) {
        FirstFragment fragment = new FirstFragment();
        Bundle bundle = new Bundle(2);
        bundle.putInt("SIZE", size);
        bundle.putInt("START_INDEX", startIndex);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        size = getArguments().getInt("SIZE");
        startIndex = getArguments().getInt("START_INDEX");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_first, container, false);

//        Integer size = getIntent().getIntExtra("size", 0);
//        Integer index = getIntent().getIntExtra("startingIndex", 0);
        Log.e("size", size.toString());

        recycler_view = (RecyclerView)view.findViewById(R.id.recyclerView);
        layoutManager = new GridLayoutManager(getContext(), size);  // spanCount refers to the number of columns
        recycler_view.setLayoutManager(layoutManager);
        recycler_view.setHasFixedSize(true);

        adapter = new RecyclerViewAdapter(size, startIndex);
        recycler_view.setAdapter(adapter);

        return view;
    }
}
