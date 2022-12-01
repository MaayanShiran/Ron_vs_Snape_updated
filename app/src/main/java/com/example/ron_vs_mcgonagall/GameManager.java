package com.example.ron_vs_mcgonagall;

public class GameManager {

    private final int NUM_GT = 2;
    private BoardGame board;
    private final int COLS = 3;
    private final int ROWS = 5;
    private int wrong;

    public GameManager() {
        board = new BoardGame(ROWS, COLS, NUM_GT);
        wrong = 0;
    }

    public int getNUM_GT() {
        return NUM_GT;
    }

    public BoardGame getBoard() {
        return board;
    }

    public int getCOLS() {
        return COLS;
    }

    public boolean move(String moveTo) {

        if(moveTo.equals("moveRight")){
            if(board.getPlayerCurrentCol()!= board.getCols()-1){
                board.setPlayerCurrentLocation(1);

                return true;
            }
        }

        if(moveTo.equals("moveLeft")){
            if(board.getPlayerCurrentCol() != 0){
                board.setPlayerCurrentLocation(-1);
                return true;
            }
        }
        return false;

    }

    public void increasWrong(){
        wrong++;
    }

    public int getWrong(){
        return wrong;
    }

}
