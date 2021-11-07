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
import cn.lyj.thepublic.entry.VoteOption;


public class OptionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<VoteOption> list;
//    private int count;
    private String type;

    public OptionAdapter(String type) {
        this.type = type;
    }

    public void setList(List<VoteOption> list) {
        this.list = list;
//        this.count = (count == 0 ? 100 : count);
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

        public void bind(final VoteOption entry, final int position) {
            if (entry != null) {
                sort.setText((char) (position + 65) + "、");
//                bar.setMax(count);
//                itemcount.setText(entry.getDaCount()+"");
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    bar.setProgress(entry.getDaCount(),true);
//                }else{
//                    bar.setProgress(entry.getDaCount());
//                }
                bar.setVisibility(View.GONE);
                itemcount.setVisibility(View.GONE);
                 if (type.equals("2")) {
                    title.setText(entry.getValue());
                    title.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0,
                            entry.isChecked() ? R.mipmap.ic_vote_single_check : R.mipmap.ic_vote_single_uncheck, 0);
                } else if (type.equals("3")) {
                    title.setText(entry.getValue());
                    title.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0,
                            entry.isChecked() ? R.mipmap.ic_vote_multiple_check : R.mipmap.ic_vote_multiple_uncheck, 0);
                }
                title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        view.setEnabled(false);
                        if (listener != null) {
                            listener.itemChoiceClick(position, !entry.isChecked());
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
