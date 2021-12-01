package cn.lyj.core.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import allen.frame.tools.DateUtils;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import cn.lyj.core.R;
import cn.lyj.core.entry.Log;


public class LogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Log> list;

    public void setShow(boolean show) {
        isShow = show;
    }

    private boolean isShow = false;

    public LogAdapter() {
    }

    public void setList(List<Log> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(isShow){
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.core_show_log_item, parent, false);
            v.setLayoutParams(new ViewGroup
                    .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            return new ShowHolder(v);
        }else{
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.core_log_item, parent, false);
            v.setLayoutParams(new ViewGroup
                    .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            return new ObjectHolder(v);
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(isShow){
            ShowHolder objectHolder = (ShowHolder) holder;
            objectHolder.bind(list.get(position));
        }else{
            ObjectHolder objectHolder = (ObjectHolder) holder;
            objectHolder.bind(list.get(position));
        }
    }

    public class ObjectHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView name, delete, date, content;
        private View view;
        public ObjectHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_name);
            date = itemView.findViewById(R.id.item_date);
            content = itemView.findViewById(R.id.item_content);
            delete = itemView.findViewById(R.id.item_delete);
            view = itemView.findViewById(R.id.item_layout);
        }
        public void bind(final Log entry) {
            if (entry != null) {
                name.setText(entry.getCreateBy());
                date.setText(DateUtils.getTimeFormatText(entry.getCreateTime()));
                content.setText(entry.getDescription());
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.setEnabled(false);
                        if(listener!=null){
                            listener.onItemClick(v,entry,getAdapterPosition());
                        }
                        v.setEnabled(true);
                    }
                });
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.setEnabled(false);
                        if(listener!=null){
                            listener.onItemDelete(v,entry,getAdapterPosition());
                        }
                        v.setEnabled(true);
                    }
                });
            }
        }
    }
    public class ShowHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView date, content;
        private View view;
        public ShowHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.item_date);
            content = itemView.findViewById(R.id.item_content);
            view = itemView.findViewById(R.id.item_layout);
        }
        public void bind(final Log entry) {
            if (entry != null) {
                date.setText(DateUtils.getTimeFormatText(entry.getCreateTime()));
                content.setText(entry.getDescription());
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.setEnabled(false);
                        if(listener!=null){
                            listener.onItemClick(v,entry,getAdapterPosition());
                        }
                        v.setEnabled(true);
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
        void onItemClick(View v, Log entry, int position);
        void onItemDelete(View v, Log entry, int position);
    }
}
