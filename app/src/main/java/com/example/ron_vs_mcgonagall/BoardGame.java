package com.example.ron_vs_mcgonagall;

public class BoardGame {

    private GameTool matrix[][];
    private int cols;
    private int rows;
    private int playerCurrentRow;
    private int playerCurrentCol;
    private int playerLastRow;
    private int playerLastCol;
    private GameTool gameTools[];
    private int numberGameTools;


    public BoardGame(int rows, int cols, int numberGameTools) {
        this.cols = cols;
        this.rows = rows;
        matrix = new GameTool[this.rows][this.cols];
        playerCurrentRow = rows-1;
        playerCurrentCol = cols/2;
        matrix[playerCurrentRow][playerCurrentCol] = new Player(playerCurrentRow, playerCurrentCol, 0,0);
        this.numberGameTools = numberGameTools;
        gameTools = new GameTool[numberGameTools];

       generateRandomGameTool();
    }

    public GameTool[] getGameTools() {
        return gameTools;
    }

    public  void  resetGameTools(){
        for(int i=0; i<numberGameTools; i++){
            gameTools[i] = null;
        }
    }

    public void resetGameToolsLocation(){
        for(int i=0; i<numberGameTools; i++){
            gameTools[i].setCurrentCol((int)(Math.random() * (cols - 0)) + 0);
            gameTools[i].setCurrentRow(0);
            gameTools[i].setLastCol(0);
            gameTools[i].setLastRow(0);
        }
    }

    public void generateRandomGameTool(){
    //first, we generate the type of gameTool we want to create (OBS --> 2 or COIN --> 3)
        //now we have only obstacles
        for(int i=0;i<numberGameTools; i++){
         gameTools[i] = new Obstacle(0,(int)(Math.random() * (cols - 0)) + 0, 0,(int)(Math.random() * (cols - 0)) + 0);
            System.out.println("the current col is:");
            System.out.println(gameTools[i].getCurrentCol());
            System.out.println("the current row is:");
            System.out.println(gameTools[i].getCurrentRow());

        }
    }


    public int getPlayerLastCol(){
        return playerLastCol;
    }

    public int getNumberGameTools(){
        return numberGameTools;
    }

    public void setPlayerCurrentLocation(int move){

        playerLastCol = playerCurrentCol;
        playerLastRow = playerCurrentRow;
        removePlayerFromLastCol(playerLastRow, playerLastCol);

        playerCurrentCol +=move;
        matrix[playerCurrentRow][playerCurrentCol] = new Player(playerCurrentRow, playerCurrentCol, playerLastRow, playerLastCol);
    }

    public void removePlayerFromLastCol(int lastRow, int lastCol){
        matrix[lastRow][lastCol] = null;
    }

    public GameTool getGameToolType(int rows, int cols){
        return matrix[rows][cols];
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getPlayerCurrentRow() {
        return playerCurrentRow;
    }

    public int getPlayerCurrentCol() {
        return playerCurrentCol;
    }
}