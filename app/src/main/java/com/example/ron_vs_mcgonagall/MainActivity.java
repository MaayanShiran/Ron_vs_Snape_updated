package com.example.ron_vs_mcgonagall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;

import java.time.Instant;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ExtendedFloatingActionButton moveLeft;
    private ExtendedFloatingActionButton moveRight;
    private ShapeableImageView[] playerLocation;
    private ShapeableImageView[] gameToolsLocation;
    private ShapeableImageView[] hearts;
    private Timer timer = new Timer();
    private AppCompatImageView back_IMG_background;

    GameManager gameManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameManager = new GameManager();

        findViews();

        Glide
                .with(MainActivity.this)
                .load("https://images7.alphacoders.com/119/thumb-1920-1192080.jpg")
                .into(back_IMG_background);

        initViews();


    }
    @Override
    protected void onPause() {
        super.onPause();
        stopTimer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startTimer();
    }

    private void stopTimer() {
        timer.cancel();
    }

    private void findViews() {

        back_IMG_background = findViewById(R.id.back_IMG_background);

        moveLeft = findViewById(R.id.game_BTN_moveLeft);
        moveRight = findViewById(R.id.game_BTN_moveRight);

        playerLocation = new ShapeableImageView[]{

                findViewById(R.id.game_IMG_playerLeft),
                findViewById(R.id.game_IMG_playerCenter),
                findViewById(R.id.game_IMG_playerRight),

        };

        hearts = new ShapeableImageView[]{
                findViewById(R.id.game_IMG_heart3),
                findViewById(R.id.game_IMG_heart2),
                findViewById(R.id.game_IMG_heart1),
        };

        gameToolsLocation = new ShapeableImageView[]{
                findViewById(R.id.game_IMG_lane1_1),
                findViewById(R.id.game_IMG_lane2_1),
                findViewById(R.id.game_IMG_lane3_1),
                findViewById(R.id.game_IMG_lane1_2),
                findViewById(R.id.game_IMG_lane2_2),
                findViewById(R.id.game_IMG_lane3_2),
                findViewById(R.id.game_IMG_lane1_3),
                findViewById(R.id.game_IMG_lane2_3),
                findViewById(R.id.game_IMG_lane3_3),
                findViewById(R.id.game_IMG_lane1_4),
                findViewById(R.id.game_IMG_lane2_4),
                findViewById(R.id.game_IMG_lane3_4),
                findViewById(R.id.game_IMG_playerLeft),//new
                findViewById(R.id.game_IMG_playerCenter),
                findViewById(R.id.game_IMG_playerRight),
        };

    }

    private void initViews() {
        int playersCurrentRow = gameManager.getBoard().getPlayerCurrentRow();
        int playersCurrentCol = gameManager.getBoard().getPlayerCurrentCol();
        int boardCols = gameManager.getBoard().getCols();
        int boardRows = gameManager.getBoard().getRows();

        for (int i = 0; i < boardCols * (boardRows - 1); i++) {
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

    private void showNewGameToolViewGenerated() {

        for(int i=0; i<gameManager.getBoard().getNumberGameTools(); i++){
            int CurrRow = gameManager.getBoard().getGameTools()[i].currentRow;
            int CurrCol = gameManager.getBoard().getGameTools()[i].currentCol;
            gameToolsLocation[CurrRow * gameManager.getCOLS() + CurrCol].setImageResource(gameManager.getBoard().getGameTools()[i].getImgRcs());
            gameToolsLocation[CurrRow * gameManager.getCOLS() + CurrCol].setVisibility(View.VISIBLE);

}

    }
    private void removeAllCurrentGameToolView(){

        for(int i=0; i<gameManager.getBoard().getNumberGameTools();i++){
            int currentCol = gameManager.getBoard().getGameTools()[i].getCurrentCol();
            int currentRow = gameManager.getBoard().getGameTools()[i].getCurrentRow();
            gameToolsLocation[currentRow * gameManager.getCOLS() + currentCol].setVisibility(View.INVISIBLE);
        }
    }


    private void removeAllLastGameToolView(){

        for(int i=0; i<gameManager.getBoard().getNumberGameTools();i++){
            int lastCol = gameManager.getBoard().getGameTools()[i].getLastCol();
            int lastRow = gameManager.getBoard().getGameTools()[i].getLastRow();
            gameToolsLocation[lastRow * gameManager.getCOLS() + lastCol].setVisibility(View.INVISIBLE);
        }

    }

    private boolean moveAfterClicked(String moveTo) {
        return gameManager.move(moveTo);
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

    private void startTimer() {
        timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @SuppressLint("ResourceType")
                    public void run() {

                        for(int i=0; i<gameManager.getBoard().getNumberGameTools();i++) {
                            GameTool[] gameTool = gameManager.getBoard().getGameTools();


                            if(gameTool[i].getCurrentRow() < gameManager.getBoard().getRows()-2){
                                gameTool[i].getRowDown();
                                updateGameToolTypeView(gameTool[i]);

                            }
                            else {
                                for(int j=0; j<gameManager.getBoard().getNumberGameTools(); j++){
                                    if(gameTool[j].getCurrentCol() == gameManager.getBoard().getPlayerCurrentCol()){
                                        vibrate();
                                        toastMsg();
                                        removeHeart();
                                        break;
                                }

                                }
                                removeAllCurrentGameToolView();
                                gameManager.getBoard().resetGameTools();
                                gameManager.getBoard().generateRandomGameTool();
                                showNewGameToolViewGenerated();
                                break;
                            }
                        }
                    }
                });
            }
        }, 1000, 600);
    }

    private void removeHeart() {

        if(gameManager.getWrong() < 3)
            gameManager.increasWrong();

        for (int i = 0; i < gameManager.getWrong(); i++) {
            hearts[i].setVisibility(View.INVISIBLE);
        }

    }

    private void toastMsg() {
        Toast toast =

                Toast.makeText(MainActivity.this, "OUCH", Toast.LENGTH_SHORT);
        View toastView = toast.getView();
        toast.show();
    }

}
