package allen.frame.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import allen.frame.R;
import androidx.appcompat.widget.LinearLayoutCompat;

/**
 * Created by nereo on 15/11/10.
 */
public class ScaleLinearLayout extends LinearLayoutCompat {
    private float scale = 1f;
    public ScaleLinearLayout(Context context) {
        this(context,null,0);
    }

    public ScaleLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ScaleLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.ScaleLinearLayout, defStyle, 0);
        scale = t.getFloat(R.styleable.ScaleLinearLayout_scale,1f);
        t.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), (int) (getMeasuredWidth()*scale));
    }
}
