package com.example.realmproject;

/* This project allows you to use realm where you can write (store) data to the realm database and retrieve them
 * anytime. Here I have used recyclerview adapter sending the gridviewlayout of varying size to display according
 * to the button user clicks.
 *
 * ConstraintLayout is required to set the view of the images to fit perfectly in the device according to the size
 * of the device.
 *
 * The drawables are first added to an arraylist using prepareList() method and are converted to bitmap to byte array
 * which is done in writeImages() method. Once the contents are stored in realm database, readImages() is called to
 * read each item in the database.
 *
 * The Canvas class holds the "draw" calls. To draw something, you need 4 basic components: A Bitmap to hold the pixels,
 * a Canvas to host the draw calls (writing into the bitmap), a drawing primitive (e.g. Rect, Path, text, Bitmap), and a
 * paint (to describe the colors and styles for the drawing).
 *
 *
 *    /*
 * for loop {
 *
 *   1. create bitmap from the svg file :  https://stackoverflow.com/questions/33696488/getting-bitmap-from-vector-drawable/35347960
 *   2. use the above bitmap as parameter and convert to byte array: https://stackoverflow.com/questions/4989182/converting-java-bitmap-to-byte-array
 *   3. create unique ID for each bye array
 *   4. push into realm database with that ID
 * }
 *
 * */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {


    Realm realm;
    ImageView image_view;
    Button button1, button2, button3;
    FrameLayout frameLayout;
    FragmentManager fragmentManager;
    FirstFragment fragment;
    FragmentTransaction fragmentTransaction;
//    Fragment f;

    Image image;
    Integer startIndex = 0;

    // create an arraylist for all the drawables in drawable folder
    ArrayList<Integer> drawables = new ArrayList<>();

    private static Bitmap bitmap;

    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image_view = (ImageView) findViewById(R.id.iv);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);

        // This needs to be called if we uninstall the application and need to write all the contents to database
//        prepareList();
//        writeImages();

        frameLayout = (FrameLayout)findViewById(R.id.frameLayout);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                button1.setVisibility(View.INVISIBLE);
                button2.setVisibility(View.INVISIBLE);
                button3.setVisibility(View.INVISIBLE);

                fragment = FirstFragment.newInstance(3, startIndex);
//                Bundle bundle = new Bundle();
//                bundle.putInt("SIZE",2);
//                fragment.setArguments(bundle);
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.frameLayout, fragment);
                fragmentTransaction.commit();
                startIndex = startIndex + 9;
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                button1.setVisibility(View.INVISIBLE);
                button2.setVisibility(View.INVISIBLE);
                button3.setVisibility(View.INVISIBLE);

                fragment = FirstFragment.newInstance(4, startIndex);
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.frameLayout, fragment);
                fragmentTransaction.commit();
                startIndex += 16;
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                button1.setVisibility(View.INVISIBLE);
                button2.setVisibility(View.INVISIBLE);
                button3.setVisibility(View.INVISIBLE);

                fragment = FirstFragment.newInstance(7, startIndex);
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.frameLayout, fragment);
                fragmentTransaction.commit();
                startIndex += 49;
            }
        });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Log.e("back pressed", "here");
       frameLayout.removeAllViews();
       button1.setVisibility(View.VISIBLE);
       button2.setVisibility(View.VISIBLE);
       button3.setVisibility(View.VISIBLE);

    }

    // method to convert drawables to bitmap
    public static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = AppCompatDrawableManager.get()
                .getDrawable(context, drawableId);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap); //canvas class holds the "draw" calls
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    // method to write images to realm database
    public void writeImages() {
        Realm realm = null;
        try {

            // writing data to Realm
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {


                    for (int i=0; i<drawables.size(); i++) {
                        Image image = new Image(); // creating new image eachtime because realm doesnot take the data if it already exists
                        int id = drawables.get(i); // creates new id eachtime the loop is run
                        image.setId(id);

                        // converting each id to bitmap
                        Bitmap bmp = getBitmapFromVectorDrawable(getApplicationContext(), id);

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);

                        // converting bitmap to bytearray
                        byte[] byteArray = stream.toByteArray();

                        image.setData(byteArray);

                        realm.copyToRealm(image);
                    }
                }
            });
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
    }

    // method to add all the drawables in the arraylist of type Integer
    public void prepareList(){
        drawables.add(R.drawable.ic_ac_unit_black_24dp);
        drawables.add(R.drawable.ic_access_alarm_black_24dp);
        drawables.add(R.drawable.ic_accessibility_black_24dp);
        drawables.add(R.drawable.ic_accessible_black_24dp);
        drawables.add(R.drawable.ic_account_balance_black_24dp);
        drawables.add(R.drawable.ic_account_balance_wallet_black_24dp);
        drawables.add(R.drawable.ic_account_box_black_24dp);
        drawables.add(R.drawable.ic_account_circle_black_24dp);
        drawables.add(R.drawable.ic_adb_black_24dp);
        drawables.add(R.drawable.ic_add_a_photo_black_24dp);
        drawables.add(R.drawable.ic_add_alarm_black_24dp);
        drawables.add(R.drawable.ic_add_alert_black_24dp);
        drawables.add(R.drawable.ic_add_box_black_24dp);
        drawables.add(R.drawable.ic_add_circle_black_24dp);
        drawables.add(R.drawable.ic_add_location_black_24dp);
        drawables.add(R.drawable.ic_add_shopping_cart_black_24dp);
        drawables.add(R.drawable.ic_add_to_photos_black_24dp);
        drawables.add(R.drawable.ic_add_to_queue_black_24dp);
        drawables.add(R.drawable.ic_adjust_black_24dp);
        drawables.add(R.drawable.ic_airline_seat_flat_angled_black_24dp);
        drawables.add(R.drawable.ic_airline_seat_flat_black_24dp);
        drawables.add(R.drawable.ic_airline_seat_legroom_extra_black_24dp);
        drawables.add(R.drawable.ic_airline_seat_legroom_reduced_black_24dp);
        drawables.add(R.drawable.ic_airline_seat_recline_extra_black_24dp);
        drawables.add(R.drawable.ic_airplanemode_active_black_24dp);
        drawables.add(R.drawable.ic_airplanemode_inactive_black_24dp);
        drawables.add(R.drawable.ic_airplay_black_24dp);
        drawables.add(R.drawable.ic_airport_shuttle_black_24dp);
        drawables.add(R.drawable.ic_alarm_off_black_24dp);
        drawables.add(R.drawable.ic_alarm_on_black_24dp);
        drawables.add(R.drawable.ic_album_black_24dp);
        drawables.add(R.drawable.ic_announcement_black_24dp);
        drawables.add(R.drawable.ic_apps_black_24dp);
        drawables.add(R.drawable.ic_archive_black_24dp);
        drawables.add(R.drawable.ic_arrow_back_black);
        drawables.add(R.drawable.ic_arrow_drop_down_black_24dp);
        drawables.add(R.drawable.ic_arrow_drop_up_black_24dp);
        drawables.add(R.drawable.ic_art_track_black_24dp);
        drawables.add(R.drawable.ic_aspect_ratio_black_24dp);
        drawables.add(R.drawable.ic_assessment_black_24dp);
        drawables.add(R.drawable.ic_assignment_black_24dp);
        drawables.add(R.drawable.ic_assignment_ind_black_24dp);
        drawables.add(R.drawable.ic_assignment_late_black_24dp);
        drawables.add(R.drawable.ic_assignment_return_black_24dp);
        drawables.add(R.drawable.ic_assignment_returned_black_24dp);
        drawables.add(R.drawable.ic_assignment_turned_in_black_24dp);
        drawables.add(R.drawable.ic_assistant_black_24dp);
        drawables.add(R.drawable.ic_assistant_photo_black_24dp);
        drawables.add(R.drawable.ic_attach_file_black_24dp);
        drawables.add(R.drawable.ic_attach_money_black_24dp);
        drawables.add(R.drawable.ic_attachment_black_24dp);
        drawables.add(R.drawable.ic_audiotrack_black_24dp);
        drawables.add(R.drawable.ic_autorenew_black_24dp);
        drawables.add(R.drawable.ic_av_timer_black_24dp);
        drawables.add(R.drawable.ic_backspace_black_24dp);
        drawables.add(R.drawable.ic_backup_black_24dp);
        drawables.add(R.drawable.ic_battery_20_black_24dp);
        drawables.add(R.drawable.ic_battery_50_black_24dp);
        drawables.add(R.drawable.ic_battery_80_black_24dp);
        drawables.add(R.drawable.ic_battery_alert_black_24dp);
        drawables.add(R.drawable.ic_battery_charging_full_black_24dp);
        drawables.add(R.drawable.ic_battery_full_black_24dp);
        drawables.add(R.drawable.ic_beach_access_black_24dp);
        drawables.add(R.drawable.ic_beenhere_black_24dp);
        drawables.add(R.drawable.ic_block_black_24dp);
        drawables.add(R.drawable.ic_bluetooth_audio_black_24dp);
        drawables.add(R.drawable.ic_blur_circular_black_24dp);
        drawables.add(R.drawable.ic_blur_linear_black_24dp);
        drawables.add(R.drawable.ic_book_black_24dp);
        drawables.add(R.drawable.ic_bookmark_black_24dp);
        drawables.add(R.drawable.ic_camera_roll_black_24dp);
        drawables.add(R.drawable.ic_cancel_black_24dp);
        drawables.add(R.drawable.ic_cake_black_24dp);
        drawables.add(R.drawable.ic_call_black_24dp);
        drawables.add(R.drawable.ic_call_end_black_24dp);
        drawables.add(R.drawable.ic_call_made_black_24dp);
        drawables.add(R.drawable.ic_call_merge_black_24dp);
        drawables.add(R.drawable.ic_camera_alt_black_24dp);
        drawables.add(R.drawable.ic_camera_black_24dp);
        drawables.add(R.drawable.ic_card_giftcard_black_24dp);
        drawables.add(R.drawable.ic_card_membership_black_24dp);
        drawables.add(R.drawable.ic_card_travel_black_24dp);
        drawables.add(R.drawable.ic_call_missed_black_24dp);
        drawables.add(R.drawable.ic_colorize_black_24dp);
        drawables.add(R.drawable.ic_create_black_24dp);
        drawables.add(R.drawable.ic_delete_black_24dp);
        drawables.add(R.drawable.ic_details_black_24dp);
        drawables.add(R.drawable.ic_devices_other_black_24dp);
        drawables.add(R.drawable.ic_directions_bike_black_24dp);
        drawables.add(R.drawable.ic_done_black_24dp);
        drawables.add(R.drawable.ic_explicit_black_24dp);
        drawables.add(R.drawable.ic_fiber_new_black_24dp);
        drawables.add(R.drawable.ic_filter_black_24dp);
        drawables.add(R.drawable.ic_filter_vintage_black_24dp);
        drawables.add(R.drawable.ic_flare_black_24dp);
        drawables.add(R.drawable.ic_gavel_black_24dp);
        drawables.add(R.drawable.ic_gesture_black_24dp);
        drawables.add(R.drawable.ic_hdr_weak_black_24dp);
        drawables.add(R.drawable.ic_hot_tub_black_24dp);
        drawables.add(R.drawable.ic_hourglass_full_black_24dp);
    }

    // method to read the images stored in realm database
//    public void readImages() {
//        Realm realm = Realm.getDefaultInstance();
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//
//                results = realm.where(Image.class).findAll();
//
//                Log.e("results size", Integer.toString(results.size()));
//
////                for (Image image : results) {
////                    image_view.setImageResource(image.getImage());
////                }
//            }
//        });
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null) {
            realm.close();
        }
    }
}

