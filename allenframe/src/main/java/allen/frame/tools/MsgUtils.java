package allen.frame.tools;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import allen.frame.AllenManager;
import allen.frame.R;

public class MsgUtils {
	/**
	 * Toast提示
	 * 
	 * @param text
	 */
	public static void showLongToast(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_LONG).show();
	}

	public static void showShortToast(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	/**
	 * Snackbar 提示
	 * 
	 * @param view
	 * @param text
	 * @param btn
	 */
	public static void showSnackbar(View view, String text, String btn) {
		final Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG);
		View bg = snackbar.getView();
		if (bg != null) {
			bg.setBackgroundColor(view.getContext().getResources().getColor(R.color.text_blue_color));
		}
		snackbar.show();
		if (StringUtils.notEmpty(btn)) {
			snackbar.setAction(btn, new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					snackbar.dismiss();
				}
			});
		}
	}

	public static void showSnackbar(View view, String text, String btn, View.OnClickListener click) {
		final Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG);
		View bg = snackbar.getView();
		if (bg != null) {
			bg.setBackgroundColor(view.getContext().getResources().getColor(R.color.text_blue_color));
		}
		snackbar.show();
		if (StringUtils.notEmpty(btn)) {
			snackbar.setAction(btn, click);
		}
	}

	public static void showSnackbar(View view, String text, String btn, int color, View.OnClickListener click) {
		final Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG);
		View bg = snackbar.getView();
		if (bg != null) {
			bg.setBackgroundColor(view.getContext().getResources().getColor(R.color.text_blue_color));
		}
		if (StringUtils.notEmpty(btn)) {
			snackbar.setAction(btn, click);
		}
		snackbar.setActionTextColor(color);
		snackbar.show();
	}

	/**
	 * Alert提示
	 * 
	 * @param context
	 * @param text
	 */
	public static void showMessage(Context context, String text) {
		AlertDialog dialog = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT).setTitle("温馨提示")
				.setMessage(text).setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).show();
		dialog.getButton(DialogInterface.BUTTON_POSITIVE)
				.setTextColor(context.getResources().getColor(R.color.text_blue_color));

	}

	/**
	 * Material风格AlertDialog
	 * 
	 * @param context
	 * @param text
	 */
	public static void showMDMessage(Context context, String text) {
		showMDMessage(context, "温馨提示", text);
		/*android.support.v7.app.AlertDialog dialog = new android.support.v7.app.AlertDialog.Builder(context,
				R.style.Theme_AppCompat_Light_Dialog_Alert).setTitle().setMessage(text)
						.setPositiveButton("确定", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						}).show();
		dialog.getButton(DialogInterface.BUTTON_POSITIVE)
				.setTextColor(context.getResources().getColor(R.color.text_blue_color));*/
	}
	
	public static void showMDMessage(Context context, String title, String text) {
		AlertDialog dialog = new AlertDialog.Builder(context,
				R.style.Theme_AppCompat_Light_Dialog_Alert).setTitle(title).setMessage(text)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).show();
		dialog.getButton(DialogInterface.BUTTON_POSITIVE)
		.setTextColor(context.getResources().getColor(R.color.text_blue_color));
	}

	public static void showMDMessage(Context context, String text, DialogInterface.OnClickListener onclick) {
		AlertDialog dialog = new AlertDialog.Builder(context,
				R.style.Theme_AppCompat_Light_Dialog_Alert).setTitle("温馨提示").setMessage(text)
						.setPositiveButton("确定", onclick).show();
		dialog.getButton(DialogInterface.BUTTON_POSITIVE)
				.setTextColor(context.getResources().getColor(R.color.text_blue_color));
	}

	public static void showMDMessage(Context context, String text, DialogInterface.OnClickListener onclick,
			boolean isCancle) {
		 AlertDialog dialog = new  AlertDialog.Builder(context,
				R.style.Theme_AppCompat_Light_Dialog_Alert).setTitle("温馨提示").setMessage(text)
						.setPositiveButton("确定", onclick).setCancelable(isCancle).show();
		dialog.getButton(DialogInterface.BUTTON_POSITIVE)
				.setTextColor(context.getResources().getColor(R.color.text_blue_color));
	}

	public static void showMDMessage(Context context, String msg, String pos,
			DialogInterface.OnClickListener onPosClick, String neg, DialogInterface.OnClickListener onNegClick) {
		showMDMessage(context, "温馨提示", msg, pos,onPosClick, neg, onNegClick);
	}

	public static void showMDMessage(Context context, String title, String msg, String pos,
			DialogInterface.OnClickListener onPosClick, String neg, DialogInterface.OnClickListener onNegClick) {
		 AlertDialog dialog = new AlertDialog.Builder(context,
				R.style.Theme_AppCompat_Light_Dialog_Alert).setTitle(title).setMessage(msg)
						.setNegativeButton(neg, onNegClick).setPositiveButton(pos, onPosClick).show();
		dialog.getButton(DialogInterface.BUTTON_POSITIVE)
				.setTextColor(context.getResources().getColor(R.color.text_blue_color));
		dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
	}

	public static void showNotOutMDMessage(Context context, String msg, String pos,
			DialogInterface.OnClickListener onPosClick, String neg, DialogInterface.OnClickListener onNegClick) {
		AlertDialog dialog = new AlertDialog.Builder(context,
				R.style.Theme_AppCompat_Light_Dialog_Alert).setTitle("温馨提示").setCancelable(false).setMessage(msg)
						.setNegativeButton(neg, onNegClick).setPositiveButton(pos, onPosClick).show();
		dialog.getButton(DialogInterface.BUTTON_POSITIVE)
				.setTextColor(context.getResources().getColor(R.color.text_blue_color));
		dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
	}

	public static void exitMDMessage(Context context, String text) {
		AlertDialog dialog = new AlertDialog.Builder(context,
				R.style.Theme_AppCompat_Light_Dialog_Alert).setTitle("温馨提示").setMessage(text)
						.setNegativeButton("取消", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						}).setPositiveButton("确定", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
								AllenManager.getInstance().exitApp();
							}
						}).show();
		dialog.getButton(DialogInterface.BUTTON_POSITIVE)
				.setTextColor(context.getResources().getColor(R.color.text_blue_color));
		dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
	}

	public static void exitNotOutMDMessage(Context context, String text) {
		AlertDialog dialog = new AlertDialog.Builder(context,
				R.style.Theme_AppCompat_Light_Dialog_Alert).setTitle("温馨提示").setMessage(text).setCancelable(false)
						.setPositiveButton("确定", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
								AllenManager.getInstance().exitApp();
							}
						}).show();
		dialog.getButton(DialogInterface.BUTTON_POSITIVE)
				.setTextColor(context.getResources().getColor(R.color.text_blue_color));
		dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
	}

	public static void showSystemMDMessage(Context context, String title, String msg, String pos,
										   DialogInterface.OnClickListener onPosClick, String neg, DialogInterface.OnClickListener onNegClick) {
		AlertDialog.Builder  builder = new AlertDialog.Builder(context,
				R.style.Theme_AppCompat_Light_Dialog_Alert).setTitle(title).setMessage(msg)
				.setNegativeButton(neg, onNegClick).setPositiveButton(pos, onPosClick);
		AlertDialog dialog = builder.create();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			dialog.getWindow().setType((WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY));
		}else {
			dialog.getWindow().setType((WindowManager.LayoutParams.TYPE_SYSTEM_ALERT));
		}
		dialog.show();
	}

}
