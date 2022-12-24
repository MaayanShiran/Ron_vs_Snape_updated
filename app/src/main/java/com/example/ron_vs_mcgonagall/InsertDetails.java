package com.example.ron_vs_mcgonagall;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class InsertDetails extends AppCompatActivity {
    public static final String KEY_SCORE = "KEY_SCORE";
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";


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

                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                int numItems = sharedPreferences.getInt("numberItems", gameManager.getCurrentTopTenNum());

                gameManager.setCurrentTopTenNum(numItems);
                gameManager.addRecord(name, lon, lat, Integer.parseInt(score));//add a new record


                numItems++;
                editor.putInt("numberItems", numItems);

                for (int i = 0; i < 10; i++) {

                    editor.putString("name" + i, gameManager.getTopTen().get(i).getName());
                    editor.putInt("score" + i, gameManager.getTopTen().get(i).getScore());
                    editor.putString("lat" + i, gameManager.getTopTen().get(i).getLatitude().toString());
                    editor.putString("lon" + i, gameManager.getTopTen().get(i).getLongitude().toString());

                }

                editor.commit();

                finish();
            }
        });
    }

    private void findViews() {
        setName = findViewById(R.id.game_LBL_setName);
        submit = findViewById(R.id.game_BTN_submitName);
    }


}
