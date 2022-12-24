package com.example.ron_vs_mcgonagall;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.appgozar.fadeoutparticle.FadeOutParticleFrameLayout;

public class Menu extends AppCompatActivity {

    private Button modeSensor;
    private Button modeButton;
    private Button topTenTable;
    private FadeOutParticleFrameLayout fade;
    private FadeOutParticleFrameLayout fade1;
    private FadeOutParticleFrameLayout fade2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyUtiles.hideSystemUI(this);
        setContentView(R.layout.activity_menu);

        doInBackground();

        findViews();

        initViews();
    }


    protected void onPause() {
        super.onPause();
        MyMediaPlayer.getMediaPlayerInstance().pausePlayer();
    }

    protected void onResume() {

        super.onResume();
        MyMediaPlayer.getMediaPlayerInstance().resumePlayer(R.raw.prologue, this);
    }

    private void initViews() {

        modeSensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fade1.startAnimation();
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    public void run() {

                        openStartGameChooseLvlPage("sensor");
                        fade1.reset();
                    }
                }, 1750);   //1.7 seconds

            }
        });

        modeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fade.startAnimation();
                Handler handler2 = new Handler();
                handler2.postDelayed(new Runnable() {
                    public void run() {

                        openStartGameChooseLvlPage("button");
                        fade.reset();
                    }
                }, 1750);   //1.7 seconds

            }
        });

        topTenTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fade2.startAnimation();
                Handler handler3 = new Handler();
                handler3.postDelayed(new Runnable() {
                    public void run() {

                        openTopTenViewPage();
                        fade2.reset();

                    }
                }, 1750);   //1.7 seconds


            }
        });

    }

    private void findViews() {
        fade = findViewById(R.id.particles);
        fade1 = findViewById(R.id.particles2);
        fade2 = findViewById(R.id.particles3);
        modeSensor = findViewById(R.id.game_BTN_sensorMode);
        modeButton = findViewById(R.id.game_BTN_buttonMode);
        topTenTable = findViewById(R.id.game_BTN_topTen);

    }

    private void openStartGameChooseLvlPage(String status) {
        Intent intent = new Intent(this, BeginPlayChooseLvl.class);
        intent.putExtra(BeginPlayChooseLvl.KEY_MODE, status);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }

    private void doInBackground(Void... params) {

        MyMediaPlayer.getMediaPlayerInstance().playAudioFile(this, R.raw.prologue);
        MyMediaPlayer.getMediaPlayerInstance().setLoop(true);

    }

    private void openTopTenViewPage() {
        Intent intent = new Intent(this, TopTen_Panel.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

}
