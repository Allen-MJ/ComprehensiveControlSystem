package allen.frame.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;

import allen.frame.R;

public class QrCodePop {
	private View v;
    private PopupWindow pop;
    private Context context;
    private ImageView iv;
	public QrCodePop(Context context) {
		this.context = context;
		v = LayoutInflater.from(context).inflate(R.layout.alen_qrcode_layout, null, false);
		iv = (ImageView) v.findViewById(R.id.show);
		iv.setOnClickListener(l);
		v.findViewById(R.id.lay).setOnClickListener(l);
		pop = new PopupWindow(v);
		pop.setFocusable(true);
        pop.setOutsideTouchable(true);
	}
	public void show(View vd, Bitmap bit){
		iv.setImageBitmap(bit);
		if(pop!=null&&!pop.isShowing()){
			pop.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
			pop.setHeight(WindowManager.LayoutParams.MATCH_PARENT);
			pop.setTouchable(true);  
			pop.setFocusable(true);
			pop.setOutsideTouchable(true);
	        ColorDrawable dw = new ColorDrawable(Color.WHITE);
	        pop.setBackgroundDrawable(dw);
	        pop.showAtLocation(vd, Gravity.CENTER, 0, 0);
		}
	}
	private OnClickListener l = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if(v.getId()== R.id.show||v.getId()== R.id.lay){
				dismiss();
			}
		}
	};
	public void dismiss(){
		if(pop!=null&&pop.isShowing()){
			pop.dismiss();
		}
	}
}
