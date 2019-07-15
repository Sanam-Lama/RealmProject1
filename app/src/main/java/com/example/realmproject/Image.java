package com.example.realmproject;

/* Defining model class Image by extending RealmObject*/

import android.util.Log;

import java.nio.ByteBuffer;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Image extends RealmObject {

    // having primaryKey will force the app to have unique names to each object otherwise the app will crash
    @PrimaryKey
    private int id;

    public int getImage() {
        return id;
    }

    public void setImage(int id) {
        this.id = id;
    }

}
