package com.example.ron_vs_mcgonagall;

import java.util.Comparator;

public class CompTTD implements Comparator<TopTenDetails> {
    @Override
    public int compare(TopTenDetails t1, TopTenDetails t2) {
        if(t1.getScore() > t2.getScore())
            return -1;
        if(t1.getScore() < t2.getScore())
            return 1;

        return 0;
    }
}
