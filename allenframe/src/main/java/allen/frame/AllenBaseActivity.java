package allen.frame;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import allen.frame.tools.StatusBarUtil;
import allen.frame.tools.StringUtils;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class AllenBaseActivity extends AppCompatActivity {
	
	public ActivityHelper actHelper;
	private ProgressDialog dialog;
	public Context context = this;
	private AppCompatTextView titleat;
	private Unbinder unbinder;
	public SharedPreferences shared;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		AllenManager.init(getApplication());
//		Aria.init(getApplicationContext());
		actHelper = new ActivityHelper(this);
		AllenManager.getInstance().addActivity(this);
		super.onCreate(savedInstanceState);
		if(isStatusBarColorWhite()){
			if(Build.VERSION.SDK_INT >= 21){
				getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
				StatusBarUtil.setStatusBarColor(this,R.color.white);
			}else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
				getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
				StatusBarUtil.setStatusBarColor(this,R.color.white);
			}
		}
		setContentView(getLayoutResID());
		shared = actHelper.getSharedPreferences();
		unbinder = ButterKnife.bind(this);
		initBar();
		initUI(savedInstanceState);
		addEvent();
	}

	protected abstract boolean isStatusBarColorWhite();

	protected abstract int getLayoutResID();
	
	protected abstract void initBar();

	protected abstract void initUI(@Nullable Bundle savedInstanceState);

	protected abstract void addEvent();

	@Deprecated
	protected void setToolbarTitle(Toolbar bar,CharSequence title){
		setToolbarTitle(bar,title, true,null);
	}

	protected void setToolbarTitle(Toolbar bar,CharSequence title, boolean displayHomeAsUpEnabled){
		setToolbarTitle(bar,title, displayHomeAsUpEnabled,null);
	}

	protected void setToolbarTitle(Toolbar bar, CharSequence title, boolean displayHomeAsUpEnabled, View.OnClickListener onClickListener){
		titleat = findViewById(R.id.title);
		if(onClickListener!=null){
			titleat.setOnClickListener(onClickListener);
		}
		if(StringUtils.notEmpty(title.toString())){
			bar.setTitle("");
			titleat.setText(title);
		}
		setSupportActionBar(bar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(displayHomeAsUpEnabled);
	}

	@Override
	protected void onDestroy() {
		AllenManager.getInstance().closeActivity(this);
		if(dialog!=null&&dialog.isShowing()){
			dialog.dismiss();
			dialog = null;
		}
		super.onDestroy();
		if(unbinder!=Unbinder.EMPTY){
			unbinder.unbind();
		}
	}
	public void showProgressDialog(String msg){
		if(dialog==null?true:!dialog.isShowing()){
			dialog = new ProgressDialog(this, R.style.Allen_Dialog_Theme);
			dialog.setMessage(StringUtils.notEmpty(msg)?msg:"正在处理,请稍等...");
			dialog.setCancelable(false);
			dialog.show();
			dialog.setIndeterminateDrawable(context.getResources().getDrawable(R.drawable.dialog_bar));
		}else{
			dialog.setMessage(StringUtils.notEmpty(msg)?msg:"正在处理,请稍等...");
		}
	}
	@SuppressWarnings("deprecation")
	public void showOldProgressDialog(String msg){
		if(dialog==null||!dialog.isShowing()){
			dialog = new ProgressDialog(this, AlertDialog.THEME_HOLO_LIGHT);
			dialog.setMessage(StringUtils.notEmpty(msg)?msg:"正在处理,请稍等...");
			dialog.setCancelable(false);
			dialog.show();
			dialog.setIndeterminateDrawable(context.getResources().getDrawable(R.drawable.dialog_bar));
		}else{
			dialog.setMessage(StringUtils.notEmpty(msg)?msg:"正在处理,请稍等...");
		}
	}
	public void dismissProgressDialog(){
		if(dialog!=null&&dialog.isShowing()){
			dialog.dismiss();
		}
	}

	public void exitApp(){
		AllenManager.getInstance().exitApp();
	}

}
