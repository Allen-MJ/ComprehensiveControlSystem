package allen.frame.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

/** An image view which always remains square with respect to its width. */
@SuppressLint("Instantiatable")
class SquaredImageView extends AppCompatImageView {
  public SquaredImageView(Context context) {
    this(context,null);
  }

  @SuppressLint("Instantiatable")
public SquaredImageView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
  }
}
