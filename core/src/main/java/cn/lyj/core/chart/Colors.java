package cn.lyj.core.chart;

import android.graphics.Color;

public class Colors {
    private static int[] colors = new int[]{
            Color.parseColor("#0067c6"), Color.parseColor("#01b4e2"), Color.parseColor("#4a8ace"),
            Color.parseColor("#3f59c0"), Color.parseColor("#6f8cd4"), Color.parseColor("#5b48bc"),
            Color.parseColor("#8369c6"), Color.parseColor("#8241b9"), Color.parseColor("#9943a2"),
            Color.parseColor("#a24379"), Color.parseColor("#cd3c0d"), Color.parseColor("#fe7d27"),
            Color.parseColor("#f6a31d"), Color.parseColor("#ffd925"), Color.parseColor("#ddcc13"),
            Color.parseColor("#b4c647"), Color.parseColor("#86a844"), Color.parseColor("#218b7e"),
            Color.parseColor("#40beac"), Color.parseColor("#436b99")
    };

    public static int getColor(int index){
        return colors[index%colors.length];
    }
}
