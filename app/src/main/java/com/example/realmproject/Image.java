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
    private byte[] data;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
