package com.example.ron_vs_mcgonagall;

public class GameTool {

    protected int id;
    protected boolean crash; //true -> remove heart, false -> add ten points
    protected int currentRow;
    protected int currentCol;
    protected int lastRow;
    protected int lastCol;
    protected int imgRcs;

    public GameTool() {

    }

    public GameTool(int id, boolean crash, int currentRow, int currentCol, int lastRow, int lastCol, int imgRcs) {
        this.id = id;
        this.crash = crash;
        this.currentRow = currentRow;
        this.currentCol = currentCol;
        this.lastRow = lastRow;
        this.lastCol = lastCol;
        this.imgRcs = imgRcs;
    }


    public GameTool(int id, int currentRow, int currentCol, int lastRow, int lastCol, int imgRcs) {
        this.id = id;
        this.currentRow = currentRow;
        this.currentCol = currentCol;
        this.lastRow = lastRow;
        this.lastCol = lastCol;
        this.imgRcs = imgRcs;
    }

    public void getRowDown() {

        setLastCol(currentCol);
        setLastRow(currentRow);
        currentRow += 1;
    }

    public int getImgRcs() {
        return imgRcs;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public int getLastCol() {
        return lastCol;
    }

    public void setLastCol(int lastCol) {
        this.lastCol = lastCol;
    }

    public int getLastRow() {
        return lastRow;
    }

    public void setLastRow(int lastRow) {
        this.lastRow = lastRow;
    }

    public int getCurrentCol() {
        return currentCol;
    }

    public void setCurrentCol(int currentCol) {
        this.currentCol = currentCol;
    }

    public int pointsAdd() {
        return 0;
    }
}
