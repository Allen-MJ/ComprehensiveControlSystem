package allen.frame.adapter;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class CommonAdapter<T> extends RecyclerView.Adapter<ViewHolder> {

	private Context mContext;
	private List<T> mDatas;
	private int mLayoutId;// itemLayoutId
	private OnItemClickListener mItemClickListener;

	public CommonAdapter(Context context, List<T> datas, int layoutId) {
		this.mContext = context;
		this.mDatas = datas;
		this.mLayoutId = layoutId;
	}

	public CommonAdapter(Context context, int layoutId) {
		this.mContext = context;
		this.mLayoutId = layoutId;
	}
	
	public  CommonAdapter(Context context) {
		this.mContext = context;
	}

	@Override
	public int getItemCount() {
		return mDatas==null?0:mDatas.size();
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		convert(holder, mDatas.get(position), position);
	}

	public abstract void convert(ViewHolder holder, T entity, int position);

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {
		ViewHolder holder = ViewHolder.createViewHolder(mContext, viewGroup, mLayoutId);
		setListener(viewGroup, holder, type);
		return holder;
	}
	

	private void setListener(ViewGroup parnet, final ViewHolder holder, int type) {
		if (mItemClickListener != null) {
			holder.getConvertView().setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					int position = holder.getAdapterPosition();
					mItemClickListener.onItemClick(v, holder, position);
				}
			});
			holder.getConvertView().setOnLongClickListener(new OnLongClickListener() {

				@Override
				public boolean onLongClick(View v) {
					int position = holder.getAdapterPosition();
					return mItemClickListener.onItemLongClick(v, holder, position);
				}
			});

		}
	}

	public void addData(T t) {
		mDatas.add(t);
		notifyDataSetChanged();
	}

	public void addData(T t, int position) {
		mDatas.add(t);
		notifyItemInserted(position);
	}

	public void deleteItem(int position) {
		mDatas.remove(position);
		notifyItemRemoved(position);
	}

	public void setDatas(List<T> data) {
		// if(mDatas!=null){
		// mDatas.clear();
		// }
		mDatas = data;
		notifyDataSetChanged();
	}

	public List<T> getDatas() {
		return mDatas;
	}

	public T getItem(int position) {
		return mDatas.get(position);
	}

	public interface OnItemClickListener {
		void onItemClick(View view, ViewHolder holder, int position);

		boolean onItemLongClick(View view, ViewHolder holder, int position);
	}

	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		this.mItemClickListener = onItemClickListener;
	}
}
