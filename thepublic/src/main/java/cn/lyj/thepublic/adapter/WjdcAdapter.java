package cn.lyj.thepublic.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import cn.lyj.thepublic.R;
import cn.lyj.thepublic.entry.WjdcEntity;


public class WjdcAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<WjdcEntity> list;

    public WjdcAdapter() {
    }

    public void setList(List<WjdcEntity> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_wjdc, parent, false);
        v.setLayoutParams(new ViewGroup
                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        return new ObjectHolder(v);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ObjectHolder objectHolder = (ObjectHolder) holder;
        objectHolder.bind(list.get(position), getItemCount() - 1 == position);
    }

    public class ObjectHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView title, date, status;
        private AppCompatImageView icon;
        private View view, line;

        public ObjectHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.item_icon);
            title = itemView.findViewById(R.id.item_title);
            date = itemView.findViewById(R.id.item_date);
            status = itemView.findViewById(R.id.item_status);
            view = itemView.findViewById(R.id.item_layout);
            line = itemView.findViewById(R.id.item_line);
        }

        public void bind(final WjdcEntity entry, boolean isLast) {
            if (entry != null) {
                title.setText("[问卷]" + entry.getPollTitle());
//                date.setText(entry.getSumCount() + "人参加");
                date.setVisibility(View.GONE);
//                status.setText("结束时间:"+entry.getPollEndtime());
//                status.setBackgroundResource( R.mipmap.ic_news_vote_status_start);
                icon.setImageResource(R.mipmap.ic_news_vote);
                line.setVisibility(isLast ? View.INVISIBLE : View.VISIBLE);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        view.setEnabled(false);
                        if (listener != null) {
                            listener.itemClick(view, entry);
                        }
                        view.setEnabled(true);
                    }
                });
            }
        }
    }

    private String getStatusName(double status) {
        String name = "";
        if (status < 0) {
            name = "已结束";
        } else {
            name = "进行中";
        }
        return name;
    }

    private int getStatusResId(double status) {
        int resId = 0;
        if (status < 0) {
            resId = R.mipmap.ic_news_vote_status_finish;
        } else {
            resId = R.mipmap.ic_news_vote_status_start;
        }
        return resId;
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void itemClick(View v, WjdcEntity wjdc);
    }
}
