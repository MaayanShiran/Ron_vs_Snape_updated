package com.example.ron_vs_mcgonagall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import com.bumptech.glide.Glide;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_MODE = "KEY_MODE";//which mode was selected: sensor or button
    public static final String KEY_LEVEL = "KEY_LEVEL";//which level was selected: fast or slow
    public static int DELAY = 0;

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Sensor mGyroscope;

    private String mode;
    private String level;
    private ExtendedFloatingActionButton moveLeft;
    private ExtendedFloatingActionButton moveRight;
    private ShapeableImageView[] playerLocation;
    private ShapeableImageView[] gameToolsLocation;
    private ShapeableImageView[] hearts;
    private Timer timer = new Timer();
    private AppCompatImageView back_IMG_background;
    private TextView scoreLable;
    private int currentSpeed = 1;
    private int lastSpeed = 0;

    GameManager gameManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyUtiles.hideSystemUI(this);
        setContentView(R.layout.activity_main);

        gameManager = new GameManager();

        findViews();

        Intent previousIntent = getIntent();
        mode = previousIntent.getStringExtra(KEY_MODE);
        level = previousIntent.getStringExtra(KEY_LEVEL);

        Glide
                .with(MainActivity.this)
                .load("https://images7.alphacoders.com/119/thumb-1920-1192080.jpg")
                .into(back_IMG_background);

        initViews();

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mode.equals("sensor")) {
            mSensorManager.unregisterListener(accelerometerEventListener);
            mSensorManager.unregisterListener(gyroscopeEventListener);
        }

        stopTimer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mode.equals("sensor")) {
            mSensorManager.registerListener(accelerometerEventListener, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            mSensorManager.registerListener(gyroscopeEventListener, mGyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        }

        startTimer();
    }

    private void stopTimer() {
        handler.removeCallbacks(runnable);
    }

    private void findViews() {

        back_IMG_background = findViewById(R.id.back_IMG_background);

        scoreLable = findViewById(R.id.LBL_score);

        moveLeft = findViewById(R.id.game_BTN_moveLeft);
        moveRight = findViewById(R.id.game_BTN_moveRight);

        playerLocation = new ShapeableImageView[]{

                findViewById(R.id.game_IMG_player0), findViewById(R.id.game_IMG_player1), findViewById(R.id.game_IMG_player2),
                findViewById(R.id.game_IMG_player3), findViewById(R.id.game_IMG_player4),

        };

        hearts = new ShapeableImageView[]{
                findViewById(R.id.game_IMG_heart3), findViewById(R.id.game_IMG_heart2), findViewById(R.id.game_IMG_heart1),
        };

        gameToolsLocation = new ShapeableImageView[]{
                findViewById(R.id.game_IMG_lane1_1), findViewById(R.id.game_IMG_lane2_1), findViewById(R.id.game_IMG_lane3_1), findViewById(R.id.game_IMG_lane4_1), findViewById(R.id.game_IMG_lane5_1),
                findViewById(R.id.game_IMG_lane1_2), findViewById(R.id.game_IMG_lane2_2), findViewById(R.id.game_IMG_lane3_2), findViewById(R.id.game_IMG_lane4_2), findViewById(R.id.game_IMG_lane5_2),
                findViewById(R.id.game_IMG_lane1_3), findViewById(R.id.game_IMG_lane2_3), findViewById(R.id.game_IMG_lane3_3), findViewById(R.id.game_IMG_lane4_3), findViewById(R.id.game_IMG_lane5_3),
                findViewById(R.id.game_IMG_lane1_4), findViewById(R.id.game_IMG_lane2_4), findViewById(R.id.game_IMG_lane3_4), findViewById(R.id.game_IMG_lane4_4), findViewById(R.id.game_IMG_lane5_4),
                findViewById(R.id.game_IMG_lane1_5), findViewById(R.id.game_IMG_lane2_5), findViewById(R.id.game_IMG_lane3_5), findViewById(R.id.game_IMG_lane4_5), findViewById(R.id.game_IMG_lane5_5),
                findViewById(R.id.game_IMG_lane1_6), findViewById(R.id.game_IMG_lane2_6), findViewById(R.id.game_IMG_lane3_6), findViewById(R.id.game_IMG_lane4_6), findViewById(R.id.game_IMG_lane5_6),
                findViewById(R.id.game_IMG_lane1_7), findViewById(R.id.game_IMG_lane2_7), findViewById(R.id.game_IMG_lane3_7), findViewById(R.id.game_IMG_lane4_7), findViewById(R.id.game_IMG_lane5_7),
                findViewById(R.id.game_IMG_player0), findViewById(R.id.game_IMG_player1), findViewById(R.id.game_IMG_player2), findViewById(R.id.game_IMG_player3), findViewById(R.id.game_IMG_player4),
        };

    }

    @SuppressLint("ServiceCast")
    private void initViews() {
        int playersCurrentRow = gameManager.getBoard().getPlayerCurrentRow();
        int playersCurrentCol = gameManager.getBoard().getPlayerCurrentCol();
        int boardCols = gameManager.getBoard().getCols();
        int boardRows = gameManager.getBoard().getRows();

        if (mode.equals("sensor")) {
            moveLeft.setVisibility(View.INVISIBLE);
            moveRight.setVisibility(View.INVISIBLE);

            mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            mGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);//was fastest

        }

        if (level.equals("fast")) {
            DELAY = 400;//was 600
        } else if (level.equals("slow")) {
            DELAY = 1200;
        }

        for (int i = 0; i < boardCols * (boardRows); i++) {
            gameToolsLocation[i].setVisibility(View.INVISIBLE);
        }

        Player player = (Player) gameManager.getBoard().getGameToolType(playersCurrentRow, playersCurrentCol);

        for (int i = 0; i < boardCols; i++) {
            if (i != playersCurrentCol)
                playerLocation[i].setVisibility(View.INVISIBLE);
        }

        playerLocation[playersCurrentCol].setImageResource(player.getImgRcs());

        for (int i = 0; i < gameManager.getNUM_GT(); i++) {
            if (gameManager.getBoard().getGameTools()[i] != null) {
                int currentGTRow = gameManager.getBoard().getGameTools()[i].currentRow;
                int currentGTCol = gameManager.getBoard().getGameTools()[i].currentCol;
                gameToolsLocation[currentGTRow * gameManager.getCOLS() + currentGTCol].setImageResource(gameManager.getBoard().getGameTools()[i].getImgRcs());
                gameToolsLocation[currentGTRow * gameManager.getCOLS() + currentGTCol].setVisibility(View.VISIBLE);
            }

        }


        moveRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean answer = moveAfterClicked("moveRight");
                if (answer == true)//if there was any change in players position at all
                    updateGameToolTypeView(gameManager.getBoard().getGameToolType(gameManager.getBoard().getPlayerCurrentRow(), gameManager.getBoard().getPlayerCurrentCol()));
            }
        });

        moveLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean answer = moveAfterClicked("moveLeft");
                if (answer == true)//if there was any change in players position at all
                    updateGameToolTypeView(gameManager.getBoard().getGameToolType(gameManager.getBoard().getPlayerCurrentRow(), gameManager.getBoard().getPlayerCurrentCol()));
            }
        });

    }

    long timeStamp = 0;
    long timeStamp2 = 0;

    private SensorEventListener gyroscopeEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

            float a = sensorEvent.values[1];
            //tilt backwards -> more and more negative
            //tilt forwards -> more and more positive
            if (System.currentTimeMillis() - timeStamp2 > 400) {//to make the sensor mode less sensitive
                timeStamp2 = System.currentTimeMillis();

                currentSpeed = (int) a;


                if (currentSpeed <= -50) {
                    DELAY = 1200;
                } else if (currentSpeed > -50 && currentSpeed <= -35) {
                    DELAY = 600;
                } else if (currentSpeed > -35 && currentSpeed <= -20) {
                    DELAY = 300;
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    private SensorEventListener accelerometerEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float a = sensorEvent.values[0];
            if (System.currentTimeMillis() - timeStamp > 400) {//to make the sensor mode less sensitive
                timeStamp = System.currentTimeMillis();
                int move = (int) a * (-1);
                boolean answer = moveBySensor(move);
                if (answer == true)//if there was any change in players position at all
                    updateGameToolTypeView(gameManager.getBoard().getGameToolType(gameManager.getBoard().getPlayerCurrentRow(), gameManager.getBoard().getPlayerCurrentCol()));

            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
        }
    };

    private void updateGameToolTypeView(GameTool type) {
        if (type != null) {

        }
        if (type.id == 1) {//player
            playerLocation[type.currentCol].setVisibility(View.VISIBLE);
            playerLocation[type.currentCol].setImageResource(type.getImgRcs());
            int lastCol = gameManager.getBoard().getPlayerLastCol();
            playerLocation[lastCol].setVisibility(View.INVISIBLE);
        }

        if (type.id == 2) {//obstacle

            gameToolsLocation[type.currentRow * gameManager.getCOLS() + type.currentCol].setVisibility(View.VISIBLE);
            gameToolsLocation[type.currentRow * gameManager.getCOLS() + type.currentCol].setImageResource(type.getImgRcs());

            removeAllLastGameToolView();
        }

    }

    private void showNewGameToolViewGenerated(GameTool type) {
        int CurrRow = type.currentRow;
        int CurrCol = type.currentCol;
        gameToolsLocation[CurrRow * gameManager.getCOLS() + CurrCol].setImageResource(type.getImgRcs());
        gameToolsLocation[CurrRow * gameManager.getCOLS() + CurrCol].setVisibility(View.VISIBLE);

    }

    private void removeAllCurrentGameToolView() {

        for (int i = 0; i < gameManager.getBoard().getNumberGameTools(); i++) {
            int currentCol = gameManager.getBoard().getGameTools()[i].getCurrentCol();
            int currentRow = gameManager.getBoard().getGameTools()[i].getCurrentRow();
            gameToolsLocation[currentRow * gameManager.getCOLS() + currentCol].setVisibility(View.INVISIBLE);
        }
    }

    private void removeCurrentGameToolView(GameTool type) {
        int currentCol = type.getCurrentCol();
        int currentRow = type.getCurrentRow();
        gameToolsLocation[currentRow * gameManager.getCOLS() + currentCol].setVisibility(View.INVISIBLE);

    }

    private void removeAllLastGameToolView() {

        for (int i = 0; i < gameManager.getBoard().getNumberGameTools(); i++) {
            int lastCol = gameManager.getBoard().getGameTools()[i].getLastCol();
            int lastRow = gameManager.getBoard().getGameTools()[i].getLastRow();
            gameToolsLocation[lastRow * gameManager.getCOLS() + lastCol].setVisibility(View.INVISIBLE);
        }

    }

    private void removeLastGameToolView(GameTool type) {
        int lastCol = type.getLastCol();
        int lastRow = type.getLastRow();
        gameToolsLocation[lastRow * gameManager.getCOLS() + lastCol].setVisibility(View.INVISIBLE);

    }

    private boolean moveAfterClicked(String moveTo) {
        return gameManager.move(moveTo);
    }

    private boolean moveBySensor(int position) {
        return gameManager.moveBySensor(position);
    }

    private void vibrate() {
        Vibrator v = (Vibrator) getSystemService(this.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(500);
        }

    }

    final Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        public void run() {
            handler.postDelayed(this, DELAY);
            GameTool[] gameTool = gameManager.getBoard().getGameTools();
            for (int i = 0; i < gameManager.getBoard().getCurrentNumberGT(); i++) {

                if (gameTool[i].getCurrentRow() < gameManager.getBoard().getRows() - 1) {

                    gameTool[i].getRowDown();
                    showNewGameToolViewGenerated(gameTool[i]);
                    removeLastGameToolView(gameTool[i]);

                } else if (gameTool[i].getCurrentRow() == gameManager.getBoard().getRows() - 1) {
                    if (gameTool[i].getCurrentCol() == gameManager.getBoard().getPlayerCurrentCol()) {
                        if (gameTool[i].id == 2) {//obs
                            vibrate();
                            toastMsg();
                            removeHeart();
                            MyMediaPlayer.getMediaPlayerInstance().playAudioFile(MainActivity.this, R.raw.where_did_you_come_from);
                        } else if (gameTool[i].id == 3) {//coin
                            //add 30 points - bonus
                            gameManager.updateScore(gameTool[i].pointsAdd());
                            updateScoreView();
                            MyMediaPlayer.getMediaPlayerInstance().playAudioFile(MainActivity.this, R.raw.wicked);

                        }

                    }
                    gameManager.updateScore(10);
                    updateScoreView();
                    removeCurrentGameToolView(gameTool[i]);
                    gameManager.getBoard().generateRandomGameTool(i);
                    showNewGameToolViewGenerated(gameTool[i]);
                }

            }
            if (gameManager.getBoard().getCurrentNumberGT() != gameManager.getBoard().getNumberGameTools()) {
                gameManager.getBoard().generateRandomGameTool(gameManager.getBoard().getCurrentNumberGT());
                showNewGameToolViewGenerated(gameTool[gameManager.getBoard().getCurrentNumberGT() - 1]);
            }

        }
    };

    private void startTimer() {
        handler.postDelayed(runnable, DELAY);
    }

    private void updateScoreView() {
        scoreLable.setText("" + gameManager.getScore());
    }

    private void removeHeart() {

        if (gameManager.getWrong() < 3)
            gameManager.increasWrong();

        if (gameManager.getWrong() == 3) {
            //insert record to top ten

            Intent intent = new Intent(this, InsertDetails.class);
            intent.putExtra(InsertDetails.KEY_SCORE, "" + gameManager.getScore());
            startActivity(intent);
            finish();

            finish();
        }


        for (int i = 0; i < gameManager.getWrong(); i++) {
            hearts[i].setVisibility(View.INVISIBLE);
        }

    }

    private void toastMsg() {
        Toast toast = Toast.makeText(MainActivity.this, "OUCH", Toast.LENGTH_SHORT);
        View toastView = toast.getView();
        toast.show();
    }

}