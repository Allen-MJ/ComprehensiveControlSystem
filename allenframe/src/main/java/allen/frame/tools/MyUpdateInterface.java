package allen.frame.tools;

import android.content.Context;
import android.os.Handler;

import java.io.File;

import allen.frame.tools.AppDialog;
import allen.frame.tools.DownLoadHelper;
import allen.frame.tools.FileIntent;
import allen.frame.tools.Logger;
import allen.frame.tools.UpdateInterface;

public class MyUpdateInterface extends UpdateInterface {

	/*private Handler mHandler;*/
	private Context context;

	/*public MyUpdateInterface() {
	}

	public MyUpdateInterface(Handler handler) {
		mHandler = handler;
	}*/
	public MyUpdateInterface(Context context) {
		this.context = context;
	}

	@Override
	public void openFile(String path, String msg) {
		// TODO Auto-generated method stub
		super.openFile(path, msg);
		Logger.e("debug",path);
		FileIntent.openFile(context, new File(path));
	}

	/*@Override
	public void downLoad(AppDialog dialog, String name, String url) {
		// TODO Auto-generated method stub
		DownLoadHelper helper = DownLoadHelper.getInstall();
		int i = helper.downLoadFile(dialog, "download", name, url, 30000);
		if (i == 0) {
			mHandler.sendEmptyMessage(10);
			// dialog.dismissDownDialog();
		} else if (i == 1) {
			mHandler.sendEmptyMessage(11);
			// dialog.dismissDownDialog();
		} else {
			mHandler.sendEmptyMessage(12);
		}
	}*/

}
