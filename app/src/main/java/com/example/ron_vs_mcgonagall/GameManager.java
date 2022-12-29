package com.example.ron_vs_mcgonagall;

public class GameManager {

    private BoardGame board;
    private final int COLS = 5;
    private final int ROWS = 7;
    private int wrong;
    private final int NUM_GT = ROWS - 1;
    private int score;
    private static GameManager manager;
    private TopTen_Arr topTen;

    public GameManager() {
        board = new BoardGame(ROWS, COLS, NUM_GT);
        wrong = 0;
        score = 0;
        topTen = TopTen_Arr.set();

    }

    public static GameManager set() {
        if (manager == null) {
            manager = new GameManager();
        }
        return manager;
    }

    public TopTen_Arr getTopTen() {
        return topTen;
    }

    public void addRecord(String name, double lon, double lat, int score) {

        topTen.addRecord(name, score, lat, lon);


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

    public void gameLogic() {

    }
}