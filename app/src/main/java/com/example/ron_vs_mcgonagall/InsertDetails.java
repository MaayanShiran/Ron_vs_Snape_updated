package com.example.ron_vs_mcgonagall;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class InsertDetails extends AppCompatActivity {
    public static final String KEY_SCORE = "KEY_SCORE";
    public static final String TEXT = "text";
    private static final String SP_KEY_TOPTEN = "SP_KEY_PLAYLIST";

    private EditText setName;
    private String score;
    private String name;
    private Button submit;
    private float lat;
    private float lon;

    GameManager gameManager;

    LocationManager locationManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_details);

        gameManager = GameManager.set();

        findViews();

        Intent previousIntent = getIntent();
        score = previousIntent.getStringExtra(KEY_SCORE);

        initViews();

    }

    private void initViews() {

        submit.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
                name = setName.getText().toString();
                ActivityCompat.requestPermissions(InsertDetails.this, new String[]
                        {Manifest.permission.ACCESS_FINE_LOCATION}, 0);

                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                lat = (float) location.getLatitude();
                lon = (float) location.getLongitude();

                MySharedPreferences.init(getApplicationContext());

                setListFromJson();
                gameManager.addRecord(name, lon, lat, Integer.parseInt(score));//add a new record

                storeListToJson();

                finish();
            }
        });
    }

    private void findViews() {
        setName = findViewById(R.id.game_LBL_setName);
        submit = findViewById(R.id.game_BTN_submitName);
    }

    public void setListFromJson() {
        ArrayList<TopTenDetails> topten;
        String serializedObject = MySharedPreferences.getInstance().getString(SP_KEY_TOPTEN, null);
        if (serializedObject != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<TopTenDetails>>() {
            }.getType();
            topten = gson.fromJson(serializedObject, type);
            TopTen_Arr.setTopTens(topten);
        }
    }

    public void storeListToJson() {
        Gson gson = new Gson();
        String topten = gson.toJson(gameManager.getTopTen().getTopTen());
        MySharedPreferences.getInstance().putString(SP_KEY_TOPTEN, topten);//put jason string in SP

    }

}
