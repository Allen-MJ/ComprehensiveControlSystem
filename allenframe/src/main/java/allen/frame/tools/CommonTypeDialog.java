package allen.frame.tools;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import java.util.List;

import allen.frame.R;
import allen.frame.adapter.CommonAdapter;
import allen.frame.adapter.CoreTypeAdapter;
import allen.frame.entry.CoreType;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CommonTypeDialog<T> {
	private Context context;
	private List<T> list;
	private AlertDialog alertDialog;

	public CommonTypeDialog(Context context, List<T> list) {
		this.context = context;
		this.list = list;
	}
	public CommonTypeDialog(Context context) {
		this.context = context;
	}

	public AlertDialog showDialog(String sname, CommonAdapter<T> adapter, CommonAdapter.OnItemClickListener onItemClickListener) {
		View v = LayoutInflater.from(context).inflate(
				R.layout.alen_common_type_layout, null);
		RecyclerView lv = v.findViewById(R.id.type_lv);
		lv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager
				.VERTICAL, false));
		lv.setAdapter(adapter);
		adapter.setDatas(list);
		alertDialog = new AlertDialog.Builder(context)
				.setTitle(sname).setView(v)
				.setPositiveButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						arg0.dismiss();
					}
				}).show();
		adapter.setOnItemClickListener(onItemClickListener);
		return alertDialog;
	}

	public AlertDialog showMimuDialog(String sname, CommonAdapter<T> adapter, CommonAdapter.OnItemClickListener onItemClickListener,DialogInterface.OnClickListener onClickListener) {
		View v = LayoutInflater.from(context).inflate(
				R.layout.alen_common_type_layout, null);
		RecyclerView lv = v.findViewById(R.id.type_lv);
		lv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager
				.VERTICAL, false));
		lv.setAdapter(adapter);
		adapter.setDatas(list);
		alertDialog = new AlertDialog.Builder(context)
				.setTitle(sname).setView(v)
				.setPositiveButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						arg0.dismiss();
					}
				}).setNegativeButton("确定",onClickListener).show();
		adapter.setOnItemClickListener(onItemClickListener);
		return alertDialog;
	}

	RecyclerView next,last;
	private LinearLayoutCompat lastLayout;
	private String[] names;
	public AlertDialog showLevelDialog(String sname, List<CoreType> coreTypes, final OnItemClickListener onItemClickListener){
		names = new String[3];
		View v = LayoutInflater.from(context).inflate(
				R.layout.alen_common_two_type_layout, null);
		RecyclerView lv = v.findViewById(R.id.type_lv);
		next = v.findViewById(R.id.type_next_lv);
		last = v.findViewById(R.id.type_last_lv);
		lastLayout = v.findViewById(R.id.type_last_layout);
		lv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager
				.VERTICAL, false));
		CoreTypeAdapter adapter = new CoreTypeAdapter(context,coreTypes);
		lv.setAdapter(adapter);
		adapter.setData(coreTypes);
		alertDialog = new AlertDialog.Builder(context)
				.setTitle(sname).setView(v)
				.setPositiveButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						arg0.dismiss();
					}
				}).show();
		/*WindowManager.LayoutParams lp = alertDialog.getWindow().getAttributes();
		lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
		alertDialog.getWindow().setAttributes(lp);*/
		adapter.setOnItemClickListener(new CoreTypeAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(View v, CoreType entry, int position) {
				names[0] = entry.getLabel();
				names[1] = "";
				names[2] = "";
				lastLayout.setVisibility(View.GONE);
				WindowManager.LayoutParams lp = alertDialog.getWindow().getAttributes();
				lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
				alertDialog.getWindow().setAttributes(lp);
				if(entry.getChildren()==null||entry.getChildren().size()==0){
					if(onItemClickListener!=null){
						onItemClickListener.itemClick(entry.getLabel(),entry.getValue());
					}
				}else{
					next(entry.getChildren(),onItemClickListener);
				}
			}
		});
		return alertDialog;
	}

	private void next(List<CoreType> coreTypes, final OnItemClickListener onItemClickListener){
		next.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager
				.VERTICAL, false));
		CoreTypeAdapter adapter = new CoreTypeAdapter(context,coreTypes);
		next.setAdapter(adapter);
		adapter.setData(coreTypes);
		adapter.setOnItemClickListener(new CoreTypeAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(View v, CoreType entry, int position) {
				String name = entry.getLabel();
				names[1] = name;
				names[2] = "";
				if(entry.getChildren()==null||entry.getChildren().size()==0){
					lastLayout.setVisibility(View.GONE);
					if(onItemClickListener!=null){
						onItemClickListener.itemClick(names[0]+names[1],entry.getValue());
					}
				}else if(name.equals("市辖区")||name.equals("县")||name.equals("市辖县")){
					lastLayout.setVisibility(View.VISIBLE);
					last(entry.getChildren(),onItemClickListener);
				}else{
					lastLayout.setVisibility(View.GONE);
					if(onItemClickListener!=null){
						onItemClickListener.itemClick(names[0]+names[1],entry.getValue());
					}
				}
			}
		});
	}

	private void last(List<CoreType> coreTypes, final OnItemClickListener onItemClickListener){
		WindowManager.LayoutParams lp = alertDialog.getWindow().getAttributes();
		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		alertDialog.getWindow().setAttributes(lp);
		last.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager
				.VERTICAL, false));
		CoreTypeAdapter adapter = new CoreTypeAdapter(context,coreTypes);
		last.setAdapter(adapter);
		adapter.setData(coreTypes);
		adapter.setOnItemClickListener(new CoreTypeAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(View v, CoreType entry, int position) {
				names[2] = entry.getLabel();
				if(onItemClickListener!=null){
					onItemClickListener.itemClick(names[0]+names[2],entry.getValue());
				}
			}
		});
	}

	public interface OnItemClickListener{
		void itemClick(String name,String code);
	}

	public void  dismiss(){
		if (alertDialog!=null&&alertDialog.isShowing()){
			alertDialog.dismiss();
		}
	}
}