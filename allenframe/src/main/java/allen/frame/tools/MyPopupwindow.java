package allen.frame.tools;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.PopupWindow;

import allen.frame.R;


public class MyPopupwindow extends PopupWindow {
	private Context mContext;
	private LayoutInflater mInflater;
	private View mContentView;
	private int[] mViewID;
	private OnClickListener mListener;
	private int popupWidth;
    private int popupHeight;

	/**
	 * 
	 * @param context
	 * @param layoutID	popupwindow布局文件
	 * @param viewID	需要监听的控件id
	 * @param listener
	 */
	public MyPopupwindow(Context context, int layoutID, int[] viewID, OnClickListener listener) {
		this.mContext = context;
		mViewID = viewID;
		mListener = listener;
		// 设置popupwindow宽度
		this.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
		// 设置popupwindow高度
		this.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
		// 设置popupwindow布局资源
		mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mContentView = mInflater.inflate(layoutID, null);
		this.setContentView(mContentView);
		// 设置点击外部区域，popupwindow是否消失，需两个同时设置
		this.setOutsideTouchable(true);
		this.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#a7ed9b")));

		// 设置popupwindow进入，退出时动画
		this.setAnimationStyle(R.style.popup_window_anim);
		
		//获取自身的长宽高
		mContentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        popupHeight = mContentView.getMeasuredHeight();
        popupWidth = mContentView.getMeasuredWidth();
		
		// 设置焦点，点击退出时，关闭popupwindow
		this.setFocusable(true);
		setOutsideTouchable(true);
		// 更新
		this.update();

		/**
		 * 事件处理
		 */
		event();

	}
	
	public void setBackground(int color){
		setBackgroundDrawable(new ColorDrawable(color));;
	}

	/**
	 * 根据viewID获取view
	 * @param id view的id
	 * @return
	 */
	public View getView(int id){
		return mContentView.findViewById(id);
	}
	
	/**
     * 设置显示在v上方(以v的左边距为开始位置)
     * @param v
     */
    public void showUp(View v) {
        //获取需要在其上方显示的控件的位置信息
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        //在控件上方显示
        showAtLocation(v, Gravity.NO_GRAVITY, (location[0]) - popupWidth / 2, location[1] - popupHeight);
    }

    /**
     * 设置显示在v上方（以v的中心位置为开始位置）
     * @param v
     */
    public void showUp2(View v) {
        //获取需要在其上方显示的控件的位置信息
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        //在控件上方显示
        showAtLocation(v, Gravity.NO_GRAVITY, (location[0] + v.getWidth() / 2) - popupWidth / 2, location[1] - popupHeight);
    }
	
	private void event() {
		for (int viewid : mViewID) {
			mContentView.findViewById(viewid).setOnClickListener(mListener);
		}

	}

}
