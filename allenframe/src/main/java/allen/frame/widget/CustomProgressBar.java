package allen.frame.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import androidx.core.content.ContextCompat;

import allen.frame.R;

public class CustomProgressBar extends ProgressBar {
	private Context mContext;
	private Paint mPaint;
	private String mText;

	// IconTextProgressBar的文字大小(sp)
	private static final float TEXT_SIZE_SP = 15f;

	public CustomProgressBar(Context context) {
		super(context, null, android.R.attr.progressBarStyleHorizontal);
		mContext = context;
		init();
	}

	public CustomProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init();
	}


	public void setText(String text) {
		mText = text;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		drawIconAndText(canvas);
	}

	private void init() {
		setIndeterminate(false);
		setIndeterminateDrawable(
				ContextCompat.getDrawable(mContext, android.R.drawable.progress_indeterminate_horizontal));
		setProgressDrawable(ContextCompat.getDrawable(mContext, R.drawable.progress_bar_bg));
		setMax(100);

		mPaint = new Paint();
		mPaint.setDither(true);
		mPaint.setAntiAlias(true);
		mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		mPaint.setTextAlign(Paint.Align.LEFT);
		mPaint.setTextSize(sp2px(mContext, TEXT_SIZE_SP));
		mPaint.setTypeface(Typeface.MONOSPACE);

		// mPorterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
	}

	private void drawIconAndText(Canvas canvas) {
		mPaint.setColor(ContextCompat.getColor(mContext, R.color.white));
		String text = mText;
		Rect textRect = new Rect();
		mPaint.getTextBounds(text, 0, text.length(), textRect);

		// 仅绘制文字
		float textX = (getWidth() / 2) - textRect.centerX();
		float textY = (getHeight() / 2) - textRect.centerY();
		canvas.drawText(text, dip2px(mContext, 10), textY, mPaint);
	}

	/**
	 * 说明：根据手机的分辨率将dp转成为px
	 */
	public int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 说明：根据手机的分辨率将sp转成为px
	 */
	public int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

}
