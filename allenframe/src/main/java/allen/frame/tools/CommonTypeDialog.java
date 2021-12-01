package allen.frame.tools;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

import java.util.List;

import allen.frame.R;
import allen.frame.adapter.CommonAdapter;
import allen.frame.adapter.CoreTypeAdapter;
import allen.frame.entry.CoreType;
import androidx.appcompat.app.AlertDialog;
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
	public CommonTypeDialog(Context context, int layoutId, List<CoreType> list) {
		this.context = context;
		this.coreTypes = list;
	}
	public CommonTypeDialog(Context context) {
		this.context = context;
	}

	public void setCoreTypes(List<CoreType> coreTypes){
		this.coreTypes = coreTypes;
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

	RecyclerView next;
	private List<CoreType> coreTypes;
	public AlertDialog showLevelDialog(String sname, CoreTypeAdapter adapter, CoreTypeAdapter.OnItemClickListener onItemClickListener){
		View v = LayoutInflater.from(context).inflate(
				R.layout.alen_common_two_type_layout, null);
		RecyclerView lv = v.findViewById(R.id.type_lv);
		next = v.findViewById(R.id.type_next_lv);
		lv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager
				.VERTICAL, false));
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
		adapter.setOnItemClickListener(onItemClickListener);
		return alertDialog;
	}

	public AlertDialog setNextData(CoreTypeAdapter adapter, CoreTypeAdapter.OnItemClickListener onItemClickListener){
		next.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager
				.VERTICAL, false));
		next.setAdapter(adapter);
		adapter.setOnItemClickListener(onItemClickListener);
		return alertDialog;
	}

	/*public AlertDialog showTwoDialog(String sname){
		View v = LayoutInflater.from(context).inflate(
				R.layout.alen_common_two_type_layout, null);
		RecyclerView lv = v.findViewById(R.id.type_lv);
		RecyclerView next = v.findViewById(R.id.type_next_lv);
		lv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager
				.VERTICAL, false));
		next.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager
				.VERTICAL, false));
		CoreTypeAdapter adapter = new CoreTypeAdapter(context)
		lv.setAdapter(adapter);
		next.setAdapter(childadapter);
		adapter.setData(coreTypes);
		alertDialog = new AlertDialog.Builder(context)
				.setTitle(sname).setView(v)
				.setPositiveButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						arg0.dismiss();
					}
				}).show();
		adapter.setOnItemClickListener(onItemClickListener);
		childadapter.setOnItemClickListener(onChildItemClickListener);
		return alertDialog;
	}*/

	public void  dismiss(){
		if (alertDialog!=null&&alertDialog.isShowing()){
			alertDialog.dismiss();
		}
	}
}