package com.example.ron_vs_mcgonagall;

import java.util.ArrayList;
import java.util.Collections;

public class GameManager {

    private BoardGame board;
    private final int COLS = 5;
    private final int ROWS = 7;
    private int wrong;
    private final int NUM_GT = ROWS - 1;
    private int score;
    private ArrayList<TopTenDetails> topTen;
    private int currentTopTenNum;
    private static GameManager manager;

    public GameManager() {
        board = new BoardGame(ROWS, COLS, NUM_GT);
        wrong = 0;
        score = 0;
        topTen = new ArrayList<TopTenDetails>();
        for (int i = 1; i <= 10; i++) {
            topTen.add(new TopTenDetails().setSerialNoImg(i));
        }

        currentTopTenNum = 0;

    }

    public static GameManager set() {
        if (manager == null) {
            manager = new GameManager();
        }
        return manager;
    }

    public ArrayList<TopTenDetails> getTopTen() {
        return topTen;
    }

    public void addRecord(String name, double lon, double lat, int score) {

        TopTenDetails tTD = new TopTenDetails(name, score, lat, lon);

        if (currentTopTenNum < 8) {
            topTen.get(currentTopTenNum).setName(name);
            topTen.get(currentTopTenNum).setLocation(lat, lon);
            topTen.get(currentTopTenNum).setScore(score);
            topTen.get(currentTopTenNum).setSerialNoImg(currentTopTenNum);

            currentTopTenNum++;

        } else {//there are aleady 10 Top-Ten, if the current's score is bigger than the last's
            if (score > topTen.get(9).getScore()) {
                topTen.get(9).setName(name);
                topTen.get(9).setLocation(lat, lon);
                topTen.get(9).setScore(score);
            }

        }
        Collections.sort(topTen, new CompTTD());
        sortArrTopTen();

    }

    public void sortArrTopTen() {

        for (int i = 0; i < topTen.size(); i++) {

            topTen.get(i).setSerialNoImg(i + 1);

        }

    }

    public int getCurrentTopTenNum() {
        return currentTopTenNum;
    }

    public int getNUM_GT() {
        return NUM_GT;
    }

    public void updateScore(int addMe) {
        score += addMe;
    }

    public int getScore() {
        return score;
    }

    public BoardGame getBoard() {
        return board;
    }

    public int getCOLS() {
        return COLS;
    }

    public boolean moveBySensor(int position) {
        if (board.getPlayerCurrentCol() != board.getCols() - 1 && position > 0) {
            board.setPlayerCurrentLocation(1);
            return true;
        }
        if (board.getPlayerCurrentCol() != 0 && position < 0) {
            board.setPlayerCurrentLocation(-1);
            return true;
        }

        return false;
    }

    public boolean move(String moveTo) {

        if (moveTo.equals("moveRight")) {
            if (board.getPlayerCurrentCol() != board.getCols() - 1) {
                board.setPlayerCurrentLocation(1);

                return true;
            }
        }

        if (moveTo.equals("moveLeft")) {
            if (board.getPlayerCurrentCol() != 0) {
                board.setPlayerCurrentLocation(-1);
                return true;
            }
        }
        return false;

    }

    public void increasWrong() {
        wrong++;
    }

    public int getWrong() {
        return wrong;
    }

    public int getROWS() {
        return ROWS;
    }


    public void setCurrentTopTenNum(int numItems) {
        currentTopTenNum = numItems;
    }
}