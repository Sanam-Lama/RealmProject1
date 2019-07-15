package com.example.realmproject;

/* This class is where you initialize Realm */

import android.app.Application;
import android.util.Log;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // initializing realm
        Realm.init(getApplicationContext());

        // setting configuration for realm
        RealmConfiguration config = new RealmConfiguration.Builder()
                                    .deleteRealmIfMigrationNeeded()
                                    .build();

        Realm.setDefaultConfiguration(config);

        Log.e("asdasd",config.getRealmFileName().toString());
    }
}


/* This section needs to go in MainActivity. In this case the images has already been stored to realm database
 * that is why we do not have to write everytime.Once the data is stored in the realm database, we just call
 * read method to retrieve the data. */
//        writeImages(R.drawable.ic_ac_unit_black_24dp);
//        writeImages(R.drawable.ic_access_alarm_black_24dp);
//        writeImages(R.drawable.ic_accessibility_black_24dp);
//        writeImages(R.drawable.ic_accessible_black_24dp);
//        writeImages(R.drawable.ic_account_balance_black_24dp);
//        writeImages(R.drawable.ic_account_balance_wallet_black_24dp);
//        writeImages(R.drawable.ic_account_box_black_24dp);
//        writeImages(R.drawable.ic_account_circle_black_24dp);
//        writeImages(R.drawable.ic_adb_black_24dp);
//        writeImages(R.drawable.ic_add_a_photo_black_24dp);
//        writeImages(R.drawable.ic_add_alarm_black_24dp);
//        writeImages(R.drawable.ic_add_alert_black_24dp);
//        writeImages(R.drawable.ic_add_box_black_24dp);
//        writeImages(R.drawable.ic_add_circle_black_24dp);
//        writeImages(R.drawable.ic_add_location_black_24dp);
//        writeImages(R.drawable.ic_add_shopping_cart_black_24dp);
//        writeImages(R.drawable.ic_add_to_photos_black_24dp);
//        writeImages(R.drawable.ic_add_to_queue_black_24dp);
//        writeImages(R.drawable.ic_adjust_black_24dp);
//        writeImages(R.drawable.ic_airline_seat_flat_angled_black_24dp);
//        writeImages(R.drawable.ic_airline_seat_flat_black_24dp);
//        writeImages(R.drawable.ic_airline_seat_legroom_extra_black_24dp);
//        writeImages(R.drawable.ic_airline_seat_legroom_reduced_black_24dp);
//        writeImages(R.drawable.ic_airline_seat_recline_extra_black_24dp);
//        writeImages(R.drawable.ic_airplanemode_active_black_24dp);
//        writeImages(R.drawable.ic_airplanemode_inactive_black_24dp);
//        writeImages(R.drawable.ic_airplay_black_24dp);
//        writeImages(R.drawable.ic_airport_shuttle_black_24dp);
//        writeImages(R.drawable.ic_alarm_off_black_24dp);
//        writeImages(R.drawable.ic_alarm_on_black_24dp);
//        writeImages(R.drawable.ic_album_black_24dp);
//        writeImages(R.drawable.ic_announcement_black_24dp);
//        writeImages(R.drawable.ic_apps_black_24dp);
//        writeImages(R.drawable.ic_archive_black_24dp);
//        writeImages(R.drawable.ic_arrow_back_black);
//        writeImages(R.drawable.ic_arrow_drop_down_black_24dp);
//        writeImages(R.drawable.ic_arrow_drop_up_black_24dp);
//        writeImages(R.drawable.ic_art_track_black_24dp);
//        writeImages(R.drawable.ic_aspect_ratio_black_24dp);
//        writeImages(R.drawable.ic_assessment_black_24dp);
//        writeImages(R.drawable.ic_assignment_black_24dp);
//        writeImages(R.drawable.ic_assignment_ind_black_24dp);
//        writeImages(R.drawable.ic_assignment_late_black_24dp);
//        writeImages(R.drawable.ic_assignment_return_black_24dp);
//        writeImages(R.drawable.ic_assignment_returned_black_24dp);
//        writeImages(R.drawable.ic_assignment_turned_in_black_24dp);
//        writeImages(R.drawable.ic_assistant_black_24dp);
//        writeImages(R.drawable.ic_assistant_photo_black_24dp);
//        writeImages(R.drawable.ic_attach_file_black_24dp);
//        writeImages(R.drawable.ic_attach_money_black_24dp);
//        writeImages(R.drawable.ic_attachment_black_24dp);
//        writeImages(R.drawable.ic_audiotrack_black_24dp);
//        writeImages(R.drawable.ic_autorenew_black_24dp);
//        writeImages(R.drawable.ic_av_timer_black_24dp);
//        writeImages(R.drawable.ic_backspace_black_24dp);
//        writeImages(R.drawable.ic_backup_black_24dp);
//        writeImages(R.drawable.ic_battery_20_black_24dp);
//        writeImages(R.drawable.ic_battery_50_black_24dp);
//        writeImages(R.drawable.ic_battery_80_black_24dp);
//        writeImages(R.drawable.ic_battery_alert_black_24dp);
//        writeImages(R.drawable.ic_battery_charging_full_black_24dp);
//        writeImages(R.drawable.ic_battery_full_black_24dp);
//        writeImages(R.drawable.ic_beach_access_black_24dp);
//        writeImages(R.drawable.ic_beenhere_black_24dp);
//        writeImages(R.drawable.ic_block_black_24dp);
//        writeImages(R.drawable.ic_bluetooth_audio_black_24dp);
//        writeImages(R.drawable.ic_blur_circular_black_24dp);
//        writeImages(R.drawable.ic_blur_linear_black_24dp);
//        writeImages(R.drawable.ic_book_black_24dp);
//        writeImages(R.drawable.ic_bookmark_black_24dp);


