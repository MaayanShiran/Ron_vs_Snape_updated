package com.example.ron_vs_mcgonagall;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TopTen_Panel extends AppCompatActivity {

    private Fragment_List fragment_list;
    private Fragment_Map fragment_map;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyUtiles.hideSystemUI(this);
        setContentView(R.layout.top_ten_panel);

        fragment_list = new Fragment_List();
        fragment_map = new Fragment_Map();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.panel_LAY_map, fragment_map)
                .commit();

        fragment_list.setCallBack_topTenProtocol(callBack_topTenProtocol);

        getSupportFragmentManager().beginTransaction().add(R.id.panel_LAY_list, fragment_list).commit();

    }

    protected void onPause() {
        super.onPause();
        MyMediaPlayer.getMediaPlayerInstance().pausePlayer();
    }

    protected void onResume() {

        super.onResume();
        MyMediaPlayer.getMediaPlayerInstance().resumePlayer(R.raw.prologue, this);
    }

    private void showTopTenLocation(TopTenDetails topTenDetails) {
        double lat = Double.parseDouble(topTenDetails.getLatitude());
        double lon = Double.parseDouble(topTenDetails.getLongitude());
        fragment_map.zoomOnMap(lat, lon, topTenDetails.getName());
    }

    private void showFakeCoordinates1() {
        double lat = 666;
        double lon = 555;
        fragment_map.zoomOnMap(lat, lon, "fakeName");
    }

    CallBack_TopTenProtocol callBack_topTenProtocol = new CallBack_TopTenProtocol() {
        @Override
        public void topTenDetails(TopTenDetails details) {
            showTopTenLocation(details);
        }

        @Override
        public void showFakeCoordinates() {
            showFakeCoordinates1();
        }
    };

}
