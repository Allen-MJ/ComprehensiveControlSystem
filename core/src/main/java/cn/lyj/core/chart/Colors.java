package cn.lyj.core.chart;

public class Colors {
    private static Colors color = null;
    private int len = 0;
    private Colors(){
        len = colors.length;
    }
    public static Colors init(){
        if(color==null){
            color = new Colors();
        }
        return color;
    }

    private int[] colors = new int[]{
        android.graphics.Color.parseColor("#0067c6"), android.graphics.Color.parseColor("#01b4e2"), android.graphics.Color.parseColor("#4a8ace"), android.graphics.Color.parseColor("#3f59c0"),
                android.graphics.Color.parseColor("#6f8cd4"), android.graphics.Color.parseColor("#5b48bc"), android.graphics.Color.parseColor("#8369c6"), android.graphics.Color.parseColor("#8241b9"),
                android.graphics.Color.parseColor("#9943a2"), android.graphics.Color.parseColor("#a24379"), android.graphics.Color.parseColor("#cd3c0d"), android.graphics.Color.parseColor("#fe7d27"),
                android.graphics.Color.parseColor("#f6a31d"), android.graphics.Color.parseColor("#ffd925"), android.graphics.Color.parseColor("#ddcc13"), android.graphics.Color.parseColor("#b4c647"),
                android.graphics.Color.parseColor("#86a844"), android.graphics.Color.parseColor("#218b7e"), android.graphics.Color.parseColor("#40beac"), android.graphics.Color.parseColor("#436b99")
    };

    public int getColor(int index){
        return colors[index&len];
    }
}
