package cn.lyj.thepublic.adapter;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.lyj.thepublic.R;
import cn.lyj.thepublic.entry.VoteEntity;


public class OptionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> list;
    private int count;
    private String type;
    private int status;
    private Map<String, Boolean> map;

    public OptionAdapter(String type, int status) {
        this.type = type;
        this.status = status;
    }

    public void setList(VoteEntity.ItemListBean list, int count) {
        this.list = list.getValueList();
        this.count = (count == 0 ? 100 : count);
        map = new HashMap<>();
        for (String s : this.list
        ) {
            map.put(s, false);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_vote_option, parent, false);
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
        objectHolder.bind(list.get(position), position);
    }

    public class ObjectHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView sort, title, itemcount;
        private ProgressBar bar;

        public ObjectHolder(@NonNull View itemView) {
            super(itemView);
            sort = itemView.findViewById(R.id.item_sort);
            title = itemView.findViewById(R.id.item_title);
            itemcount = itemView.findViewById(R.id.item_count);
            bar = itemView.findViewById(R.id.item_percent);
        }

        public void bind(final String entry, final int position) {
            if (entry != null) {
                sort.setText((char) (position + 65) + "、");
                title.setText(entry);
                bar.setMax(count);
//                itemcount.setText(entry.getDaCount()+"");
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    bar.setProgress(entry.getDaCount(),true);
//                }else{
//                    bar.setProgress(entry.getDaCount());
//                }
                if (status == 0) {
                    bar.setVisibility(View.GONE);
                    itemcount.setVisibility(View.GONE);
                    if (type .equals("1")) {
                        title.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0,
                                map.get(entry) ? R.mipmap.ic_vote_single_check : R.mipmap.ic_vote_single_uncheck, 0);
                    } else {
                        title.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0,
                                map.get(entry) ? R.mipmap.ic_vote_multiple_check : R.mipmap.ic_vote_multiple_uncheck, 0);
                    }
                } else {
                    title.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
                    bar.setVisibility(View.VISIBLE);
                    itemcount.setVisibility(View.VISIBLE);
                }
                title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        view.setEnabled(false);
                        if (status == 0) {
                            if (listener != null) {
                                listener.itemChoiceClick(position, !map.get(entry));
                            }
                        }
                        view.setEnabled(true);
                    }
                });
            }
        }
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void itemChoiceClick(int position, boolean isCheck);
    }
}
