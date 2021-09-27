package allen.frame.adapter;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewDivider extends RecyclerView.ItemDecoration {
	private Drawable mDivider;
	private Paint mPaint;
	private int height;

	public RecyclerViewDivider(int color, int height) {
		mPaint = new Paint();
		mPaint.setColor(color);
		mPaint.setAntiAlias(true);
	}
	public RecyclerViewDivider(Drawable divider) {
		mDivider = divider;
	}

	@Override
	public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
		super.onDraw(c, parent, state);
		int childCount = parent.getChildCount();
		Rect rect = new Rect();
		rect.left = parent.getPaddingLeft()+15;
		rect.right = parent.getWidth() - parent.getPaddingRight()-15;
		for (int i = 0; i < childCount; i++) {
			View childView = parent.getChildAt(i);
			rect.top = childView.getBottom();
			if(mDivider!=null){
				rect.bottom = rect.top + mDivider.getIntrinsicHeight();
				// 直接用canvas进行绘制
				mDivider.setBounds(rect);
				mDivider.draw(c);
			}
			if (mPaint != null) {
				rect.bottom = rect.top + height;
				c.drawRect(rect, mPaint);
			}
		}
	}

	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
		super.getItemOffsets(outRect, view, parent, state);
		if (mDivider == null) {
			outRect.bottom += height;
		} else {
			outRect.bottom += mDivider.getIntrinsicHeight();
		}
	}

}
