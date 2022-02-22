package cn.lyj.core.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import cn.lyj.core.R;
import cn.lyj.core.entry.KaoheEntry;

public class KaoheRelateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<KaoheEntry.Relation> list;
    public void setList(List<KaoheEntry.Relation> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.core_kaohe_appraise_relate_item, parent, false);
        v.setLayoutParams(new ViewGroup
                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        return new ObjectHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ObjectHolder objectHolder = (ObjectHolder) holder;
        objectHolder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    public class ObjectHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView title,date,finish;
        private View view;
        public ObjectHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_title);
            date = itemView.findViewById(R.id.item_date);
            finish = itemView.findViewById(R.id.item_finish);
            view = itemView.findViewById(R.id.item_layout);
        }
        public void bind(final KaoheEntry.Relation entry) {
            if (entry != null) {
                title.setText(entry.getGridName());
                date.setText(entry.getFinishTime());
                if(entry.isFinish()){
                    finish.setVisibility(View.GONE);
                }else{
                    finish.setVisibility(View.VISIBLE);
                }
                finish.setOnClickListener(new View.OnClickListener() {
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

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public interface OnItemClickListener{
        void onItemClick(View v, KaoheEntry.Relation entry, int position);
    }
}
