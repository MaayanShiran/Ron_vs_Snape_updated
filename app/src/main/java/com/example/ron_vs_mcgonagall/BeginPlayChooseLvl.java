package com.example.ron_vs_mcgonagall;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.appgozar.fadeoutparticle.FadeOutParticleFrameLayout;

public class BeginPlayChooseLvl extends AppCompatActivity {

    public static final String KEY_MODE = "";//which mode was selected:sensor or button
    private Button modeFast;
    private Button modeSlow;
    private Button returnBack;
    private FadeOutParticleFrameLayout fade;
    private FadeOutParticleFrameLayout fade1;
    private FadeOutParticleFrameLayout fade2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyUtiles.hideSystemUI(this);
        setContentView(R.layout.activity_menu_chose_play);

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

        Intent previousIntent = getIntent();
        String mode = previousIntent.getExtras().getString(KEY_MODE);

        modeFast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fade1.startAnimation();
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    public void run() {

                        openPlayGame(mode, "fast");

                        fade1.reset();
                    }
                }, 1750);   //1.7 seconds

            }
        });

        modeSlow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fade.startAnimation();
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    public void run() {

                        openPlayGame(mode, "slow");

                        fade.reset();
                    }
                }, 1750);   //1.7 seconds
            }
        });

        returnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fade2.startAnimation();
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    public void run() {

                        finish();
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);//check

                        fade2.reset();
                    }
                }, 1750);   //1.7 seconds

            }
        });

    }

    private void openPlayGame(String mode, String lvl) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.KEY_MODE, "" + mode);
        intent.putExtra(MainActivity.KEY_LEVEL, lvl);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        finish();
    }

    private void findViews() {
        modeFast = findViewById(R.id.game_BTN_fastMode);
        modeSlow = findViewById(R.id.game_BTN_slowMode);
        returnBack = findViewById(R.id.game_BTN_goBack);
        fade = findViewById(R.id.particles);
        fade1 = findViewById(R.id.particles2);
        fade2 = findViewById(R.id.particles3);

    }

}
