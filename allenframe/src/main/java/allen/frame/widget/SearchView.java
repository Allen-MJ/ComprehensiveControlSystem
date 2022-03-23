package allen.frame.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Editable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import allen.frame.R;
import allen.frame.tools.MaterialUtil;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;

public class SearchView extends LinearLayoutCompat {
    private AppCompatEditText input;
    private AppCompatImageView bt;
    private float textSize = 10;
    private int padding = 10;
    private int textColor = Color.BLACK;
    private String hint;
    private int resId = 0;
    private int bgId = 0;
    public SearchView(@NonNull Context context) {
        this(context,null,0);
    }

    public SearchView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SearchView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(final Context context, AttributeSet attrs, int defStyleAttr){
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SearchView, defStyleAttr, 0);
        textSize = a.getDimensionPixelSize(R.styleable.SearchView_inputTextSize, MaterialUtil.dip2px(context,16));
        textColor = a.getColor(R.styleable.SearchView_inputTextColor,Color.BLACK);
        resId = a.getResourceId(R.styleable.SearchView_searchIcon,R.mipmap.ic_logo_search);
        bgId = a.getResourceId(R.styleable.SearchView_searchBackground,0);
        hint = a.getString(R.styleable.SearchView_inputHint);
        padding = a.getDimensionPixelOffset(R.styleable.SearchView_inputPadding,MaterialUtil.dip2px(context,8));
        setOrientation(HORIZONTAL);
        if(bgId!=0){
            setBackgroundResource(bgId);
        }
        setGravity(Gravity.CENTER);
        input = new AppCompatEditText(context);
        input.setBackgroundColor(Color.TRANSPARENT);
        input.setTextColor(textColor);
        input.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
        input.setSingleLine(true);
        input.setPaddingRelative(padding,padding,padding,padding);
        input.setHint(hint);
        bt = new AppCompatImageView(context);
        bt.setScaleType(ImageView.ScaleType.FIT_CENTER);
        bt.setImageResource(resId);
        LayoutParams inputParams = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT,1.0f);
        LayoutParams btParams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        btParams.setMarginEnd(padding);
        addView(input,inputParams);
        addView(bt,btParams);
        bt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setEnabled(false);
                if(onClearnListenner!=null){
                    onClearnListenner.onSerchEvent(input.getText().toString().trim());
                }
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        InputMethodManager manager = ((InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE));
                        if (((Activity) context).getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
                            if (((Activity) context).getCurrentFocus() != null)
                                manager.hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        }
                    }
                },200);
                view.setEnabled(true);
            }
        });
    }

    public Editable getText(){
        return input.getText();
    }

    public AppCompatEditText getEditText(){
        return input;
    }

    public void setText(String text){
        input.setText(text);
    }

    private onSerchListenner onClearnListenner = null;
    public void setOnSerchListenner(onSerchListenner onClearnListenner) {
        this.onClearnListenner = onClearnListenner;
    }

    public interface onSerchListenner{
        public void onSerchEvent(String key);
    }
}
