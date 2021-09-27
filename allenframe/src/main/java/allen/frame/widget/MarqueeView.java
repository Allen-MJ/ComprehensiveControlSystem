package allen.frame.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;

public class MarqueeView extends AppCompatTextView {
  
    public MarqueeView(Context context, AttributeSet attrs) {  
        super(context, attrs);  
    }  
  
    protected void onFocusChanged(boolean focused, int direction,  
            Rect previouslyFocusedRect) {  
        if (focused)  
            super.onFocusChanged(focused, direction, previouslyFocusedRect);  
    }  
  
    @Override  
    public void onWindowFocusChanged(boolean focused) {  
        if (focused)  
            super.onWindowFocusChanged(focused);  
    }  
  
    @Override  
    public boolean isFocused() {  
        return true;//һֱ����true����װ����ؼ�һֱ��ȡ�Ž���  
    }  
}