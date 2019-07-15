package com.example.realmproject;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import io.realm.Realm;
import io.realm.RealmResults;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ImageViewHolder> {

    RealmResults<Image> results;
    Realm realm;
    Integer size, startIndex;

    private int height = 1550;
    private int width = 1080;

    // constructor that has size and startIndex as the parameter
    public RecyclerViewAdapter(Integer size, Integer startIndex) {
        this.size = size;
        this.startIndex = startIndex;

        // calling realm database
        realm = Realm.getDefaultInstance();

        // reading the data that is being stored in realm database
        readImages();
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.images_layout, parent, false);
        ImageViewHolder imageViewHolder = new ImageViewHolder(view);
        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(ImageViewHolder viewHolder, int position) {

        // this will allow the images to recycle to beginning index and prevent app from crashing when it reaches the last image
        Image image = results.get((position + startIndex) % results.size());

        viewHolder.image_view.setMinimumWidth(width/size);
        viewHolder.image_view.setMinimumHeight(height/size);
        viewHolder.image_view.setImageResource(image.getImage());
    }

    @Override
    public int getItemCount() {

        return size * size;
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{

        ImageView image_view;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            image_view = (ImageView)itemView.findViewById(R.id.iv);
        }
    }

    // method to read the images stored in realm database
    public void readImages() {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                results = realm.where(Image.class).findAll();

//                Log.e("results size", Integer.toString(results.size()));

//                for (Image image : results) {
//                    image_view.setImageResource(image.getImage());
//                }
            }
        });
    }
}
