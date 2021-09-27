package allen.frame.tools;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Point;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import allen.frame.R;

/**
 * Created by Administrator on 2017-03-22.
 */

public class ResRenameDialogUtil {
	private static Dialog dialog;

	// Dialog提示框消失方法
	public static void dialogDismiss() {
		if (isDialogShowing()) {
			dialog.dismiss();
			dialog = null;
		}
	}

	// Dialog提示框是否正在运行
	public static boolean isDialogShowing() {
		return dialog != null && dialog.isShowing();
	}

	// 创建Dialog提示框
	private static void createDialog(Activity activity) {
		dialog = new Dialog(activity, R.style.dialog_custom);
		dialog.setContentView(R.layout.res_rename_dialog);
		// 点击弹窗外区域，弹窗不消失
		dialog.setCanceledOnTouchOutside(true);
		Window dialogWindow = dialog.getWindow(); // 实例化Window
		WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 实例化Window操作者
		lp.x = 0; // 新位置X坐标
		lp.y = 0; // 新位置Y坐标
		dialogWindow.setAttributes(lp);// 放置属性
		Display display = activity.getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size); // size.x就是宽度，size.y就是高度
		dialog.getWindow().setLayout(size.x - 80, size.y / 2);// 设置宽高
		dialog.show();

	}

	/**
	 * 显示提示框
	 *
	 * @param activity
	 *            当前Activity
	 */
	public static void showSecurityCodeInputDialog(final Activity activity, String title, OnClickListener l) {
		if (dialog == null) {
			createDialog(activity);
		} else {
			dialog = null;
			createDialog(activity);
		}

		Window dialogWindow = dialog.getWindow();
		Button btnCancle = (Button) dialogWindow.findViewById(R.id.btn_cancle);
		Button btnSubmit = (Button) dialogWindow.findViewById(R.id.btn_commit);
		TextView tvTitle=(TextView) dialogWindow.findViewById(R.id.title);
		tvTitle.setText(title);
		btnCancle.setOnClickListener(l);
		btnSubmit.setOnClickListener(l);
		dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
					dialogDismiss();
					return true;
				}
				return false;
			}
		});
		dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				dialogDismiss();
			}
		});

	}

	public static View getView(int viewId) {
		return dialog.getWindow().findViewById(viewId);
	}
}
