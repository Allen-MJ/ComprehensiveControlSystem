package allen.frame.widget;

import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.BaseInputConnection;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.view.KeyEvent.KEYCODE_DEL;

public class MyInputConnection extends BaseInputConnection {
    private InputListener mListener;

    public MyInputConnection(View targetView, boolean fullEditor, FillTextView fillTextView) {
        super(targetView, fullEditor);
        mListener=fillTextView;
    }


    @Override
    public boolean commitText(CharSequence text, int newCursorPosition) {
        if (!isEmoji(text)) { //过滤emoji表情
            mListener.onTextInput(text.toString());
        }
        return super.commitText(text, newCursorPosition);
    }

    private boolean isEmoji(CharSequence text) {
        //过滤Emoji表情
        Pattern p = Pattern.compile("[^\\u0000-\\uFFFF]");
        //过滤Emoji表情和颜文字
        //Pattern p = Pattern.compile("[\\ud83c\\udc00-\\ud83c\\udfff]|[\\ud83d\\udc00-\\ud83d\\udfff]|[\\u2600-\\u27ff]|[\\ud83e\\udd00-\\ud83e\\uddff]|[\\u2300-\\u23ff]|[\\u2500-\\u25ff]|[\\u2100-\\u21ff]|[\\u0000-\\u00ff]|[\\u2b00-\\u2bff]|[\\u2d06]|[\\u3030]")
        Matcher m = p.matcher(text);
        return m.find();
    }

    @Override
    public boolean deleteSurroundingText(int beforeLength, int afterLength) {
        //软键盘的删除键 DEL 无法直接监听，自己发送del事件
        if (beforeLength == 1 && afterLength == 0) {

            boolean b = super.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KEYCODE_DEL)) && super.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KEYCODE_DEL));
            return b;
        } else {
            return super.deleteSurroundingText(beforeLength, afterLength);
        }
    }

    interface InputListener {
        void onTextInput(String text);

        void onDeleteWord();
    }
}
