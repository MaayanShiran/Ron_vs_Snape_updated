package com.example.ron_vs_mcgonagall;

import java.util.ArrayList;
import java.util.Collections;

public class TopTen_Arr {

    private static TopTen_Arr topTen_arr;
    private static ArrayList<TopTenDetails> topTens;
    private static int currentTopTenNum;

    public TopTen_Arr() {
        currentTopTenNum = 0;
        topTens = new ArrayList<TopTenDetails>();
        for (int i = 1; i <= 10; i++) {
            topTens.add(new TopTenDetails().setSerialNoImg(i));
        }

    }

    public static TopTen_Arr set() {
        if (topTen_arr == null) {
            topTen_arr = new TopTen_Arr();
        }
        return topTen_arr;
    }

    public static ArrayList<TopTenDetails> getTopTen() {
        return topTens;
    }

    public TopTen_Arr setSongs(ArrayList<TopTenDetails> topTenArr) {
        this.topTens = topTenArr;
        return this;
    }

    @Override
    public String toString() {
        return "TopTenArr{" +

                "TopTen=" + topTens +
                '}';
    }

    public void addRecord(String name, int score, double lat, double lon) {
        TopTenDetails tTD = new TopTenDetails(name, score, lat, lon);

        if (topTens.size() < 8) {//was currentTopTenNum
            topTens.get(topTens.size()).setName(name);
            topTens.get(topTens.size()).setLocation(lat, lon);
            topTens.get(topTens.size()).setScore(score);
            topTens.get(topTens.size()).setSerialNoImg(topTens.size());

            currentTopTenNum++;

        } else {//there are aleady 10 Top-Ten, if the current's score is bigger than the last's
            if (score > topTens.get(9).getScore()) {
                topTens.get(9).setName(name);
                topTens.get(9).setLocation(lat, lon);
                topTens.get(9).setScore(score);
            }

        }
        Collections.sort(topTens, new CompTTD());
        sortArrTopTen();
    }

    public void sortArrTopTen() {

        for (int i = 0; i < topTens.size(); i++) {
            topTens.get(i).setSerialNoImg(i + 1);

        }

    }

    public static void setTopTens(ArrayList<TopTenDetails> arr) {
        topTens = arr;
    }
}
