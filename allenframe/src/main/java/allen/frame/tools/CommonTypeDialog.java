package allen.frame.tools;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

import java.util.List;

import allen.frame.R;
import allen.frame.adapter.CommonAdapter;
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
	public void  dismiss(){
		if (alertDialog!=null&&alertDialog.isShowing()){
			alertDialog.dismiss();
		}
	}
}