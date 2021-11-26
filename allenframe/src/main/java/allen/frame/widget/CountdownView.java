package allen.frame.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;

import java.lang.ref.WeakReference;

import allen.frame.tools.TimeMeter;
import allen.frame.tools.countdowntime.CountdownTime;
import allen.frame.tools.countdowntime.CountdownTimeQueueManager;

public class CountdownView extends AppCompatTextView implements CountdownTime.OnCountdownTimeListener {
	/**
	 * 当前控件绑定的倒计时实践对象id，由于重用，RecyclerView滚动的时候， 会复用view，导致里面显示的时间其实是不一样的
	 */
	private String nowId;
	private TimeMeter timeMeter = TimeMeter.getInstance();
	private CountdownTimeQueueManager manager;
	private CountdownTime countdownTime;
	private float TEXT_SIZE = 63;
	private int TEXT_COLOR = 0xFFF18D00;
	private Paint textPaint;

	public CountdownView(Context context) {
		super(context);
		init();
	}

	public CountdownView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public CountdownView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		textPaint = getPaint();
//		textPaint.setTextSize(TEXT_SIZE);
//		textPaint.setColor(TEXT_COLOR);
		textPaint.setTextAlign(Paint.Align.CENTER);
		textPaint.setTextAlign(Paint.Align.LEFT);
		textPaint.setStrokeWidth(1);
		manager = CountdownTimeQueueManager.getInstance();
	}

	private void drawText(Canvas canvas) {
		String testString;
		if (countdownTime == null) {
			testString = "00:00";
		} else {
			testString = "剩余  "+countdownTime.getTimeText();
		}
		Rect bounds = new Rect();
		textPaint.getTextBounds(testString, 0, testString.length(), bounds);
		Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
		int baseline = (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
		canvas.drawText(testString, 0, baseline, textPaint);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		drawText(canvas);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
	}

	private int measureWidth(int origin) {
		int result = (int) textPaint.measureText("00:00");
		int specMode = MeasureSpec.getMode(origin);
		int specSize = MeasureSpec.getSize(origin);
		if (specMode == MeasureSpec.EXACTLY) {
			result = specSize;
		} else {
			if (specMode == MeasureSpec.AT_MOST) {
				result = Math.min(result, specSize);
			}
		}
		return result;
	}

	private int measureHeight(int origin) {
		int result = (int) textPaint.measureText("00");
		int specMode = MeasureSpec.getMode(origin);
		int specSize = MeasureSpec.getSize(origin);
		if (specMode == MeasureSpec.EXACTLY) {
			result = specSize;
		} else {
			if (specMode == MeasureSpec.AT_MOST) {
				result = Math.min(result, specSize);
			}
		}
		return result;
	}

	/**
	 * 多了一个id参数，可以是订单id、流水id之类，可以保证唯一性即可
	 */
	public void setCountdownTime(int time, String id) {
		nowId = id;
		if (time <= 0) {
			if (countdownTime != null)
				countdownTime.setSeconds(0);
		} else {
			WeakReference<CountdownView> weakReference = new WeakReference<>(this);
			countdownTime = manager.addTime(time, id, weakReference.get());
		}
		postInvalidate();
	}

	@Override
	public void onCountdownTimeDraw(CountdownTime time) {
		if (TextUtils.equals(nowId, time.getId())) {
			countdownTime = time;
			postInvalidate();
		}
	}

}
