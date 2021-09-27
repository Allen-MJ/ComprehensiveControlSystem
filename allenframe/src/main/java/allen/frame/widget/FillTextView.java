package allen.frame.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import allen.frame.R;

public class FillTextView extends View implements MyInputConnection.InputListener, View.OnKeyListener {


    //编辑字段标记
    private String EDIT_TAG = "<fill>";

    //编辑字段替换
    private String EDIT_REPLACEMENT = "【        】";

    //可编辑空白
    private String BLANKS = "    ";

    //可编辑开始符
    private String mEditStartTag = "(";

    //可编辑结束符
    private String mEditEndTag = ")";

    //文本
    private StringBuffer mText = new StringBuffer("");

    //存放文字段的列表，根据<fill>分割为多个字段
    private ArrayList<AText> mTextList = new ArrayList<>();

    //正在输入的字段
    private AText mEditingText = null;

    //当前正在编辑的文本行数
    private int mEditTextRow = 1;

    //光标[0]：x坐标,[1]：文字的基准线
    private float[] mCursor = {-1f, -1f};

    //光标所在文字索引
    private int mCursorIndex = 0;

    //光标闪烁标志
    private boolean mHideCursor = true;

    //控件宽度
    private int mWidth = 0;

    //文字画笔
    private Paint mNormalPaint = new Paint();

    //普通文字颜色
    private int mNormalColor = Color.BLACK;

    //文字画笔
    private Paint mFillPaint = new Paint();

    //填写文字颜色
    private int mFillColor = Color.BLACK;

    //光标画笔
    private Paint mCursorPain = new Paint();

    //光标宽度1dp
    private float mCursorWidth = 1f;

    //一个汉字的宽度
    private float mOneWordWidth = 0f;

    //一行最大的文字数
    private int mMaxSizeOneLine = 0;

    //字体大小
    private float mTextSize = sp2px(16f);

    //当前绘制到第几行
    private int mCurDrawRow = 1;

    //获取文字的起始位置
    private int mStartIndex = 0;

    //获取文字的结束位置
    private int mEndIndex = 0;

    //存放每行的文字，用于计算文字长度
    private StringBuffer mOneRowText = new StringBuffer();

    //一行字包含的字段：普通字段，可编辑字段
    private List<AText> mOneRowTexts = new ArrayList<>();

    //默认行距2dp，也是最小行距（用户设置的行距在此基础上叠加，即：2 + cst）
    private float mRowSpace = dp2px(2f);

    //是否显示下划线
    private boolean mUnderlineVisible = false;

    //下划线画笔
    private Paint mUnderlinePain = new Paint();

    public FillTextView(Context context) {
        super(context);
        init();
    }

    public FillTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        getAttrs(attrs);
        init();
    }

    public FillTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttrs(attrs);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public FillTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        getAttrs(attrs);
        init();
    }

    private void getAttrs(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.filled_text);
        mTextSize = ta.getDimension(R.styleable.filled_text_fillTextSize, mTextSize);
        mText = mText.append(ta.getText(R.styleable.filled_text_filledText) != null ? ta.getText(R.styleable.filled_text_filledText) : "");
        mNormalColor = ta.getColor(R.styleable.filled_text_normalColor, Color.BLACK);
        mFillColor = ta.getColor(R.styleable.filled_text_fillColor, Color.BLACK);
        mRowSpace += ta.getDimension(R.styleable.filled_text_rowSpace, 0f);
        ta.recycle();
    }

    private void init() {
        setFocusable(true);
        initCursorPaint();
        initTextPaint();
        initFillPaint();
        splitTexts();
        initHandler();
        setOnKeyListener(this);
    }

    /**
     * 初始化光标画笔
     */
    private void initCursorPaint() {
        mCursorWidth = dp2px(mCursorWidth);
        mCursorPain.setStrokeWidth(mCursorWidth);
        mCursorPain.setColor(mFillColor);
        mCursorPain.setAntiAlias(true);
    }

    /**
     * 初始化文字画笔
     */
    private void initTextPaint() {
//        mTextSize = sp2px(mTextSize).toFloat()
//        mRowSpace = dp2px(mRowSpace).toFloat()

        mNormalPaint.setColor(mNormalColor);
        mNormalPaint.setTextSize(mTextSize);
        mNormalPaint.setAntiAlias(true);

        mOneWordWidth = measureTextLength("测");
    }

    private void initFillPaint() {
        mFillPaint.setColor(mFillColor);
        mFillPaint.setTextSize(mTextSize);
        mFillPaint.setAntiAlias(true);
    }

    /**
     * 计算文字长度：px
     */
    private float measureTextLength(String text) {
        return mNormalPaint.measureText(text);
    }

    private int dp2px(float dp) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5);
    }

    private int sp2px(float sp) {
        float density = getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * density + 0.5);
    }

    /**
     * 拆分文字，普通文字和可编辑文字
     */
    private void splitTexts() {
        mTextList.clear();
        String[] texts = mText.toString().split(EDIT_TAG);
        for (int i = 0; i < texts.length - 1; i++) {
            String text = texts[i];
            if (i > 0) {
                text = mEditEndTag + text;
            }
            text += mEditStartTag;
            mTextList.add(new AText(text));
            mTextList.add(new AText(BLANKS, true));
        }
        mTextList.add(new AText(mEditEndTag + texts[texts.length - 1]));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = widthSize;
        int height = heightSize;

        StringBuffer realText = new StringBuffer();
        for (AText atext : mTextList
        ) {
            realText.append(atext.text);
        }
        switch (widthMode) {
            case MeasureSpec.EXACTLY:
                width = widthSize;
                //用户指定宽高
                mWidth = width;
                mMaxSizeOneLine = (int) (width / mOneWordWidth);
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                //绘制宽高为文字最大长度，如果长度超过，则使用父布局可用的最大长度
                width = (mText.toString().isEmpty()) ? 0 : (int) Math.min(widthSize, measureTextLength(realText.toString()));

                //设配最大宽高
                mWidth = widthSize;
                mMaxSizeOneLine = (int) (widthSize / mOneWordWidth);
                break;
        }

        switch (heightMode) {
            case MeasureSpec.EXACTLY:
                height = heightSize;
                break;
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                height = realText.toString().isEmpty() ? 0
                        : //其中mRowSpace + mNormalPaint.fontMetrics.descent是最后一行距离底部的间距
                        (int) (getRowHeight() * (mCurDrawRow - 1) + mRowSpace + mNormalPaint.getFontMetrics().descent);
                break;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    public void draw(Canvas canvas) {
        clear();
        canvas.save();
        mStartIndex = 0;
        mEndIndex = mMaxSizeOneLine;
        for (int i = 0; i < mTextList.size(); i++) {
            AText aText = mTextList.get(i);
            String text = aText.text;
            while (true) {
                if (mEndIndex > text.length()) {
                    mEndIndex = text.length();
                }
                addEditStartPos(aText); //记录编辑初始位置

                String cs = (String) text.subSequence(mStartIndex, mEndIndex);
                mOneRowTexts.add(new AText(cs, aText.isFill));
                mOneRowText.append(cs);

                float textWidth = measureTextLength(mOneRowText.toString());
                if (textWidth <= mWidth) {
                    float left = mWidth - textWidth;
                    float textCount = left / mOneWordWidth;
                    if (mEndIndex < text.length()) {
                        mStartIndex = mEndIndex;
                        mEndIndex += textCount;
                        if (mStartIndex == mEndIndex) {
                            float one = measureTextLength(text.substring(mEndIndex, mEndIndex + 1));
                            if (one + textWidth < mWidth) { //可以放多一个字
                                mEndIndex++;
                            } else {
                                //绘制文字
                                addEditEndPos(aText);
                                drawOneRow(canvas);
                                addEditStartPosFromZero(aText, mStartIndex); //编辑的段落可能进入下一行
                            }
                        }
                    } else { //进入下一段文字
                        addEditEndPos(aText); //记录编辑结束位置
                        if (i < mTextList.size() - 1) {
                            mStartIndex = 0;
                            mEndIndex = (int) textCount;
                            if (mStartIndex == mEndIndex) {
                                float one = measureTextLength(mTextList.get(i + 1).text.substring(0, 1));
                                if (one + textWidth < mWidth) { //可以放多一个字
                                    mEndIndex = 1; //只读下一段文字第一个字
                                } else {
                                    //绘制文字
                                    drawOneRow(canvas);
                                }
                            }
                        } else {
                            //绘制文字
                            drawOneRow(canvas);
                        }
                        break;
                    }
                } else {
                    //绘制文字
                    drawOneRow(canvas);
                }
            }
        }
        if (isFocused()) {
            drawCursor(canvas);
        }
        super.draw(canvas);
        canvas.restore();
    }

    /**
     * 添加编辑字段起始位置
     */
    private void addEditStartPos(AText aText) {
        if (aText.isFill && mStartIndex == 0) {
            aText.posInfo.clear();
            float width = measureTextLength(mOneRowText.toString());
            Rect rect = new Rect((int) width, (int) (getRowHeight() * (mCurDrawRow - 1) + mRowSpace/*加上行距*/), 0, 0);
            EditPosInfo info = new EditPosInfo(mStartIndex, rect);
            aText.posInfo.put(mCurDrawRow, info);
        }
    }

    /**
     * 添加编辑字段结束位置
     */
    private void addEditEndPos(AText aText) {
        if (aText.isFill) {
            int width = (int) measureTextLength(mOneRowText.toString());
            if (aText.posInfo.get(mCurDrawRow) != null) {
                if (aText.posInfo.get(mCurDrawRow).rect != null) {
                    aText.posInfo.get(mCurDrawRow).rect.right = width;
                    aText.posInfo.get(mCurDrawRow).rect.bottom = (int) (getRowHeight() * mCurDrawRow);
                }
            }
        }
    }

    /**
     * 添加编辑字段起始位置（换行的情况）
     */
    private void addEditStartPosFromZero(AText aText, int index) {
        if (aText.isFill) {
            Rect rect = new Rect(0, (int) (getRowHeight() * (mCurDrawRow - 1) + mRowSpace/*加上行距*/), 0, 0);
            EditPosInfo info = new EditPosInfo(index, rect);
            aText.posInfo.put(mCurDrawRow, info);
        }
    }

    /**
     * 获取一行高度
     */
    private float getRowHeight() {
        return mTextSize + mRowSpace;
    }

    private Handler mHandler = null;

    /**
     * 光标闪烁定时
     */
    private void initHandler() {
        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                mHideCursor = !mHideCursor;
                mHandler.sendEmptyMessageDelayed(1, 500);
                invalidate();
                return true;
            }
        });
        if (mHandler != null) {
            mHandler.sendEmptyMessageDelayed(1, 500);
        }
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, @Nullable Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        if (gainFocus) {
            if (mHandler != null) {
                mHandler.removeMessages(1);
                mHandler.sendEmptyMessageDelayed(1, 500);
            }
        } else {//失去焦点时，停止刷新光标
            if (mHandler != null) {
                mHandler.removeMessages(1);
            }
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (!hasWindowFocus) {
            hideInput();
        }
    }

    /**
     * 隐藏输入法
     */
    private void hideInput() {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindowToken(), 0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        setFocusableInTouchMode(true);
        requestFocus();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (touchCollision(event)) {
                    setFocusableInTouchMode(true);
                    setFocusable(true);
                    requestFocus();
                    try {
                        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(this, InputMethodManager.RESULT_SHOWN);
                        imm.restartInput(this);
                    } catch (Exception ignore) {

                    }
                    return true;
                }
                break;
        }

        return super.onTouchEvent(event);
    }

    /**
     * 检测点击碰撞
     */
    private boolean touchCollision(MotionEvent event) {
        for (AText aText : mTextList) {
            if (aText.isFill) {
                for (Integer row : aText.posInfo.keySet()
                ) {
                    EditPosInfo posInfo = aText.posInfo.get(row);
                    if (event.getX() > posInfo.rect.left && event.getX() < posInfo.rect.right &&
                            event.getY() > posInfo.rect.top && event.getY() < posInfo.rect.bottom) {
                        mEditTextRow = row;
                        if (aText.text == BLANKS) {
                            int firstRow = aText.getStartPos();
                            if (firstRow >= 0) { //可能存在换行
                                mEditTextRow = firstRow;
                            }
                        }
                        mEditingText = aText;
                        if (aText.posInfo.get(mEditTextRow) != null) {
                            calculateCursorPos(event, aText.posInfo.get(mEditTextRow), aText.text);
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 计算光标位置
     */
    private void calculateCursorPos(MotionEvent event, EditPosInfo posInfo, String text) {
        float eX = event.getX();
        float innerWidth = eX - posInfo.rect.left;
        int nWord = (int) (innerWidth / mOneWordWidth);
        int wordsWidth = 0;
        if (nWord <= 0) nWord = 1;
        if (text == BLANKS) {
            mCursor[0] = posInfo.rect.left;
            mCursor[1] = posInfo.rect.bottom;
            mCursorIndex = 0;
        } else {
            //循环计算，直到最后一个真正超过显示范围的文字（因为汉字和英文数字占位不一样，这里以汉字作为初始占位）
            do {
                wordsWidth = (int) measureTextLength(text.substring(posInfo.index, posInfo.index + nWord));
                nWord++;
            } while (wordsWidth < innerWidth && posInfo.index + nWord <= text.length());
            mCursorIndex = posInfo.index + nWord - 1;
            float leftWidth = wordsWidth - innerWidth; //计算点击位置是否超过所点击文字的一半
            if (leftWidth > measureTextLength(text.substring(mCursorIndex - 1, mCursorIndex)) / 2) {
                mCursorIndex--;
            }

            mCursor[0] = posInfo.rect.left + measureTextLength(text.substring(posInfo.index, mCursorIndex));
            mCursor[1] = posInfo.rect.bottom;
        }
        invalidate();
    }

    /**
     * 清除过期状态
     */
    private void clear() {
        mCurDrawRow = 1;
        mStartIndex = 0;
        mEndIndex = 0;
        mOneRowText.delete(0, mOneRowText.length());
        mOneRowTexts.clear();
        if (mEditingText != null) {
            if (mEditingText.posInfo != null) {
                mEditingText.posInfo.clear();
            }
        }
    }

    /**
     * 绘制一行文字
     */
    private void drawOneRow(Canvas canvas) {
        //drawText中的y坐标为文字基线
//        canvas.drawText(mOneRowText.toString(), 0f, getRowHeight()*mCurDrawRow, mNormalPaint)
        Paint.FontMetrics fm = mNormalPaint.getFontMetrics();//文字基准线问题
        float x = 0f;
        for (AText aText : mOneRowTexts) {
            canvas.drawText(aText.text, x, getRowHeight() * mCurDrawRow,
                    (aText.isFill) ? mFillPaint : mNormalPaint);

            float lineStartX = x;
            x += measureTextLength(aText.text);

            if (aText.isFill && mUnderlineVisible) {
                canvas.drawLine(lineStartX, getRowHeight() * mCurDrawRow + fm.descent,
                        x, (getRowHeight() * mCurDrawRow + fm.descent), mUnderlinePain);
            }
        }

        mCurDrawRow++;
        mEndIndex += mMaxSizeOneLine;
        mOneRowText.delete(0, mOneRowText.length());
        mOneRowTexts.clear();
        requestLayout();
    }

    /**
     * 绘制光标
     */
    private void drawCursor(Canvas canvas) {
        if (mHideCursor) {
            mCursorPain.setAlpha(0);
        } else {
            mCursorPain.setAlpha(255);
        }

        if (mCursor[0] >= 0 && mCursor[1] >= 0) {
            if ((mEditingText != null && mEditingText.text == BLANKS) && //光标可能需要换到上一行
                    (mCursor[0] == 0f || (mCursor[0] == mCursorWidth && mEditingText != null && mEditingText.posInfo.size() > 1))) {
                if (mEditingText != null && mEditingText.posInfo.size() > 1) {
                    mEditTextRow = mEditingText.getStartPos(); //得到可编辑字段最上面一行的起始位置
                    EditPosInfo posInfo = mEditingText.posInfo.get(mEditTextRow);
                    if (posInfo != null) {
                        mCursor[0] = posInfo.rect.left;
                        mCursor[1] = posInfo.rect.bottom;
                    }

                    if (mCursor[0] <= 0) mCursor[0] = (int) mCursorWidth;//矫正光标X轴坐标
                }
            }

            Paint.FontMetrics fm = mNormalPaint.getFontMetrics(); //文字基准线问题
            canvas.drawLine(mCursor[0], mCursor[1] + fm.ascent,
                    mCursor[0], (mCursor[1] + fm.descent), mCursorPain);
        }
    }

    @Override
    public void onTextInput(String text) {
        if (mEditingText != null) {
            StringBuffer filledText = new StringBuffer(mEditingText.text.replace(BLANKS,""));
            if (filledText.toString().isEmpty()) {
                filledText.append(text);
                mCursorIndex = text.length();
            } else {
                filledText.insert(mCursorIndex, text);
                mCursorIndex += text.length();
            }
                mEditingText.text = filledText.toString();
            if (mCursor[0] + measureTextLength(text.toString()) > mWidth) {//计算实际可以放多少字
                int restCount = (int) ((mWidth - mCursor[0]) / mOneWordWidth);
                int realWidth = (int) (mCursor[0] + measureTextLength(text.substring(0, restCount)));

                //循环计算，直到最后一个真正超过显示范围的文字（因为汉字和英文数字占位不一样，这里以汉字作为初始占位）
                while (realWidth <= mWidth && restCount < text.length()) {
                    restCount++;
                    realWidth = (int) (mCursor[0] + measureTextLength(text.substring(0, restCount)));
                }
                mEditTextRow += ((mCursor[0] + measureTextLength(text.toString())) / mWidth);
                if (mEditTextRow < 1) mEditTextRow = 1;
                int realCount = Math.max(restCount - 1, 0);
                mCursor[0] = measureTextLength(text.substring(realCount, text.length()));
                mCursor[1] = getRowHeight() * (mEditTextRow);
            } else {
                mCursor[0] += measureTextLength(text.toString());
            }
            invalidate();
        }
    }

    @Override
    public void onDeleteWord() {
        if (mEditingText != null) {
            StringBuffer text = new StringBuffer(mEditingText.text);
            if (!text.toString().isEmpty() &&
                    text.toString() != BLANKS &&
                    mCursorIndex >= 1) {
                int cursorPos = (int) (mCursor[0] - measureTextLength(text.substring(mCursorIndex - 1, mCursorIndex)));
                if (cursorPos > 0 ||
                        (cursorPos == 0 && mEditingText.posInfo.size() == 1)) {//光标仍然在同一行
                    mCursor[0] = cursorPos;
                } else { //光标回到上一行
                    mEditTextRow--;
                    EditPosInfo posInfo = mEditingText.posInfo.get(mEditTextRow);
                    mCursor[0] = posInfo.rect.left + measureTextLength(text.substring(posInfo.index, mCursorIndex - 1));
                    mCursor[1] = getRowHeight() * (mEditTextRow);
                }
                mEditingText.text = text.replace(mCursorIndex - 1, mCursorIndex, "").toString();
                mCursorIndex--;

                if (mEditingText.text.length() == 0) {
                    if (text.toString() != BLANKS) {
                        mEditingText.text = BLANKS;
                        mCursorIndex = 1;
                        int firstRow = mEditingText.getStartPos();
                        if (firstRow > 0) {//可能存在换行
                            mEditTextRow = firstRow;
                        }
                        mCursor[0] = mEditingText.posInfo.get(mEditTextRow).rect.left;
                        mCursor[1] = getRowHeight() * (mEditTextRow);
                    }
                }

                invalidate();
            }
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DEL &&
                event.getAction() == KeyEvent.ACTION_DOWN) {
            onDeleteWord();
            return true;
        }
        return false;
    }

    @Override
    public boolean onCheckIsTextEditor() {
        return true;
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        outAttrs.inputType = InputType.TYPE_CLASS_TEXT;
        outAttrs.imeOptions = EditorInfo.IME_ACTION_DONE;
        return new MyInputConnection(this, false, this);
    }

    /**
     * 设置文本
     */
    public void setText(String text) {
        mText.delete(0,mText.length());
        mText.append(text);
        splitTexts();
        invalidate();
    }

    /**
     * 设置字体大小，单位sp
     */
    public void setTextSize(float sp) {
        mTextSize = sp2px(sp);
        initTextPaint();
        initFillPaint();
        invalidate();
    }

    /**
     * 设置行距，单位dp
     */
    public void setRowSpace(float dp) {
        mRowSpace = dp2px(2 + dp);
        invalidate();
    }

    /**
     * 设置可编辑标记的开始和结束符
     */
    public void setEditTag(String startTag, String endTag) {
        mEditStartTag = startTag;
        mEditEndTag = endTag;
        invalidate();
    }

    /**
     * 设置是否显示可编辑字段下划线
     */
    public void displayUnderline(boolean visible) {
        mUnderlineVisible = visible;
    }

    /**
     * 设置下划线颜色
     */
    public void setUnderlineColor(int color) {
        mUnderlinePain.setColor(color);
        invalidate();
    }

    /**
     * 获取填写的文本内容
     */
    public List<String> getFillTexts() {
        List<String> list = new ArrayList<>();
        for (AText value : mTextList) {
            if (value.isFill) {
                list.add(value.text);
            }
        }
        return list;
    }
    /**
     * 获取文本内容
     */
    public String getText(){
        return mText.toString();
    }
}

class AText {

    //段落文字
    String text;

    //是否为填写字段
    boolean isFill = false;

    //文本位置信息<行，文本框>
//        MutableMap<int, EditPosInfo> posInfo = new MutableMap<>;
    Map<Integer, EditPosInfo> posInfo = new HashMap<>();

    public AText(String text, boolean isFill) {
        this.text = text;
        this.isFill = isFill;
    }

    public AText(String text) {
        this.text = text;
    }

    int getStartPos() {
        if (posInfo.isEmpty()) return -1;
        int firstRow = Integer.MAX_VALUE;
        for (Integer row : posInfo.keySet()) {
            if (firstRow > row) {
                firstRow = row;
            }
        }
        return firstRow;
    }

}


class EditPosInfo {
    int index;
    Rect rect;

    public EditPosInfo(int index, Rect rect) {
        this.index = index;
        this.rect = rect;
    }
}
