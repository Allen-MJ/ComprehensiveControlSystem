package cn.lyj.core.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import cn.lyj.core.R;
import cn.lyj.core.entry.Word;

public class WordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Word> list;
    private boolean isSend = false;

    public WordAdapter(boolean isSend) {
        this.isSend = isSend;
    }

    public void setList(List<Word> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.core_word_item, parent, false);
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

        private AppCompatTextView title,number,jjcd,status,delete;
        private View view;
        public ObjectHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_title);
            number = itemView.findViewById(R.id.item_number);
            jjcd = itemView.findViewById(R.id.item_jjcd);
            status = itemView.findViewById(R.id.item_state);
            delete = itemView.findViewById(R.id.item_delete);
            view = itemView.findViewById(R.id.item_layout);
            delete.setVisibility(isSend?View.VISIBLE:View.GONE);
        }
        public void bind(final Word entry) {
            if (entry != null) {
                title.setText(entry.getTitle());
                number.setText(entry.getMissiveNo());
                jjcd.setText(entry.getEmergencyDegreeName());
                status.setText(entry.getState());
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
                            listener.onDelete(v,entry,getAdapterPosition());
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
        void onItemClick(View v, Word entry, int position);
        void onDelete(View v, Word entry, int position);
    }
}
