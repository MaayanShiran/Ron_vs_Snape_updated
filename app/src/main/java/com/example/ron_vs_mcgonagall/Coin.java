package com.example.ron_vs_mcgonagall;

public class Coin extends GameTool{

        public Coin(int currentRow, int currentCol, int lastRow, int lastCol){
            super(3, true, currentRow, currentCol, lastRow, lastCol, R.drawable.pngegg__5_);
        }

        public int pointsAdd(){
            return 30;
        }

}
