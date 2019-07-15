package com.example.realmproject;

/* This project allows you to use realm where you can write (store) data to the realm database and retrieve them
 * anytime. Here I have used recyclerview adapter sending the gridviewlayout of varying size to display according
 * to the button user clicks.
 *
 * ConstraintLayout is required to set the view of the images to fit perfectly in the device according to the size
 * of the device. */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    Realm realm;
    ImageView image_view;
    Button button1, button2, button3;

    Image image;
    Integer startIndex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image_view = (ImageView) findViewById(R.id.iv);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TestActivity.class);
                intent.putExtra("size", 3);
                intent.putExtra("startingIndex", startIndex);
                startIndex = startIndex + 9;
                startActivity(intent);

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TestActivity.class);
                intent.putExtra("size", 4);
                intent.putExtra("startingIndex", startIndex);
                startIndex = startIndex + 16;
                startActivity(intent);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TestActivity.class);
                intent.putExtra("size", 7);
                intent.putExtra("startingIndex", startIndex);
                startIndex = startIndex + 49;
                startActivity(intent);
            }
        });


//        readImages();


//        Log.e("icon", Integer.toString(R.drawable.ic_ac_unit_black_24dp));

    }

    // method to write images to realm database
    public void writeImages(final Integer id) {
        Realm realm = null;
        try {

            // writing data to Realm
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {

                    Image image = new Image();
                    image.setImage(id);


                    realm.copyToRealm(image);
                }
            });
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
    }
}

