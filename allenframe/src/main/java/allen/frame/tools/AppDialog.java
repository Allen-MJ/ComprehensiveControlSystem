package allen.frame.tools;

import java.util.HashMap;

import allen.frame.AllenManager;
import allen.frame.R;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Html;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

public class AppDialog {
	private ProgressDialog pdialog;
	private Context context;
	private ProgressDialog progressDialog;
	public AppDialog(Context context){
		this.context = context;
	}
	public void judgeUpdate(){
		pdialog = new ProgressDialog(context, R.style.Base_V21_Theme_AppCompat_Light_Dialog);
		pdialog.setMessage("正在检测最新版本...");
		pdialog.setCancelable(false);
		pdialog.show();
		update();
	}
	
	public void showDialog(String mes){
		pdialog = new ProgressDialog(context, R.style.Base_V21_Theme_AppCompat_Light_Dialog);
		pdialog.setMessage(mes);
		pdialog.setCancelable(false);
		pdialog.show();
		appMethod();
	}
	
	private void downLoadNewApp(String name,String url){
		
		progressDialog = new ProgressDialog(context, R.style.Base_V21_Theme_AppCompat_Light_Dialog);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.setProgressNumberFormat(String.format("%.2fM/%.2fM", 0f,100f));
		progressDialog.setMessage(name);
		progressDialog.setCancelable(false);
		progressDialog.show();
		download();
	}
	
	private void downLoadFile(String name,String url){
		
		progressDialog = new ProgressDialog(context, R.style.Base_V21_Theme_AppCompat_Light_Dialog);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.setProgressNumberFormat(String.format("%.2fM/%.2fM", 0f,100f));
		progressDialog.setMessage(Html.fromHtml("正在下载<font color=\"red\">"+name+"</font>..."));
		progressDialog.setCancelable(false);
		progressDialog.show();
		download(name,url);
	}
	
	public void setProgress(int value,int max){
		if(progressDialog!=null){
			Message msg = new Message();
			msg.what = 1;
			msg.obj = new int[]{value,max};
			handler.sendMessage(msg);
		}
	}
	String name="下载中...";
	public void showNewVersion(final HashMap<String, String> info){
		if(info!=null){
			info.containsKey("loadName");
			name = info.get("loadName");
		}
		AlertDialog dialog = new AlertDialog.Builder(context, R.style.Theme_AppCompat_Light_Dialog_Alert)
		.setTitle("版本信息").setMessage(info.get("info")).setCancelable(false)
		.setPositiveButton("更新", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				downLoadNewApp(name,info.get("url"));
			}
		}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				AllenManager.getInstance().exitApp();
			}
		}).create();
		dialog.show();
	}
	/**
	 * 带进度下载提示确认dialog
	 * @param info
	 */
	public void showOpenFileDialog(final HashMap<String, String> info){
		if(info!=null){
			info.containsKey("loadName");
			name = info.get("loadName");
		}
		AlertDialog dialog = new AlertDialog.Builder(context, R.style.Theme_AppCompat_Light_Dialog_Alert)
		.setTitle(info.get("title")).setMessage(info.get("info")).setCancelable(false)
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				downLoadNewApp(name,info.get("url"));
			}
		}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		}).create();
		dialog.show();
	}
	/**
	 * 带进度下载地址的
	 * @param info
	 */
	public void OpenFileDialog(final HashMap<String, String> info){
		if(info!=null){
			name = info.get("name");
		}else{
			return;
		}
		AlertDialog dialog = new AlertDialog
				.Builder(context, R.style.Theme_AppCompat_Light_Dialog_Alert)
		.setTitle(info.get("title")).setMessage(Html.fromHtml("是否下载<font color=\"red\">"+name+"</font>文件?")).setCancelable(false)
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				downLoadFile(name,info.get("url"));
			}
		}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		}).create();
		dialog.show();
	}
	
	private void update(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				if(updateInterface!=null){
					updateInterface.judgeUpdateMethod(AppDialog.this);
				}
			}
		}).start();
	}
	private void appMethod(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				if(updateInterface!=null){
					updateInterface.appMethod(AppDialog.this);
				}
			}
		}).start();
	}
	
	private void download(final String name,final String url){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				if(updateInterface!=null){
					updateInterface.downLoad(AppDialog.this,name,url);
				}
			}
		}).start();
	}
	private void download(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				if(updateInterface!=null){
					updateInterface.downLoad(AppDialog.this);
				}
			}
		}).start();
	}
	
	public void dismissProgressDialog(){
		if(pdialog!=null){
			pdialog.dismiss();
		}
	}
	
	public void dismissDownDialog(){
		if(progressDialog!=null){
			progressDialog.dismiss();
		}
	}
	
	public void OpenFileNewDialog(final HashMap<String, String> info){
		if(info==null){
			return;
		}else{
			name = info.get("name");
			AlertDialog dialog = new AlertDialog
					.Builder(context, R.style.Theme_AppCompat_Light_Dialog_Alert)
			.setTitle(info.get("title")).setMessage(Html.fromHtml("是否下载<font color=\"red\">"+name+"</font>文件?")).setCancelable(false)
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					newdownLoadFile(info.get("dir"),name,info.get("url"));
				}
			}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			}).create();
			dialog.show();
		}
	}
	private void newdownLoadFile(final String dir, final String name,final String url){
		progressDialog = new ProgressDialog(context, R.style.Base_V21_Theme_AppCompat_Light_Dialog);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.setProgressNumberFormat(String.format("%.2fM/%.2fM", 0f,100f));
		progressDialog.setMessage(Html.fromHtml("正在下载<font color=\"red\">"+name+"</font>..."));
		progressDialog.setCancelable(false);
		progressDialog.setButton(DialogInterface.BUTTON_POSITIVE, "暂停下载", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				DownLoadManager.getInstance().pause(dir, name, url);
			}
		});
		progressDialog.show();
		DownLoadManager.getInstance().download(dir, name, url, listener);
//		download(name,url);
	}
	public void setNewProgress(long value,long max){
		if(progressDialog!=null){
			Message msg = new Message();
			msg.what = 2;
			msg.obj = new long[]{value,max};
			handler.sendMessage(msg);
		}
	}
	private DowloadListener listener = new DowloadListener() {
		@Override
		public void historySucess(String path, String msg) {
			// TODO Auto-generated method stub
			super.historySucess(path, msg);
			Logger.e("debug", "download->historySucess");
			Looper.prepare();
			dismissDownDialog();
			if(updateInterface!=null){
				updateInterface.openFile(path, msg);
			}
			Looper.loop();
		}
		@Override
		public void pause(String path, long finish, long total, String msg) {
			// TODO Auto-generated method stub
			super.pause(path, finish, total, msg);
			Logger.e("debug", "download->pause");
			Looper.prepare();
			dismissDownDialog();
			Looper.loop();
		}
		@Override
		public void progress(String path, long current, long total, String msg) {
			// TODO Auto-generated method stub
			super.progress(path, current, total, msg);
			Logger.e("debug", "download->progress");
			setNewProgress(current, total);
		}
		@Override
		public void sucess(String path, long total, String msg) {
			// TODO Auto-generated method stub
			super.sucess(path, total, msg);
			Logger.e("debug", "download->sucess");
			Looper.prepare();
			dismissDownDialog();
			if(updateInterface!=null){
				updateInterface.openFile(path, msg);
			}
			Looper.loop();
		}
		@Override
		public void fail(String msg) {
			// TODO Auto-generated method stub
			super.fail(msg);
			Looper.prepare();
			Logger.e("debug", "download->fail");
			dismissDownDialog();
			if(updateInterface!=null){
				updateInterface.downLoadFail(msg);
			}
			showFailDialog(msg);
			Looper.loop();
		}
	};
	
	private void showFailDialog(String msg){
		AlertDialog dialog = new AlertDialog
				.Builder(context, R.style.Theme_AppCompat_Light_Dialog_Alert)
		.setTitle("提示").setMessage(msg).setCancelable(false)
		.setPositiveButton("知道了", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		}).create();
		dialog.show();
	}
	
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				Log.e("debug", "dismiss");
				break;
			case 1:
				int[] numbers = (int[]) msg.obj;
				progressDialog.setProgress(numbers[0]);
				progressDialog.setMax(numbers[1]);
				progressDialog.setProgressNumberFormat(String.format("%.2fM/%.2fM", 
						numbers[0]/1024/1024f,numbers[1]/1024/1024f));
				break;
			case 2:
				Logger.e("debug", "setProgress");
				long[] numberls = (long[]) msg.obj;
				progressDialog.setProgress((int) numberls[0]);
				progressDialog.setMax((int) numberls[1]);
				progressDialog.setProgressNumberFormat(String.format("%.2fM/%.2fM", 
						numberls[0]/1024/1024f,numberls[1]/1024/1024f));
				break;
			default:
				break;
			}
		};
	};
	private UpdateInterface updateInterface;
	public void setUpdateInterface(UpdateInterface updateInterface){
		this.updateInterface = updateInterface;
	}
}
