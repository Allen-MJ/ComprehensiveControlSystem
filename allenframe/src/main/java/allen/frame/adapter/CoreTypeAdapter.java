package allen.frame.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import allen.frame.R;
import allen.frame.entry.CoreType;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CoreTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<CoreType> list;
    private Context context;

    public CoreTypeAdapter(Context context) {
        this.context = context;
    }
    public CoreTypeAdapter(Context context,List<CoreType> list) {
        this.context = context;
        this.list = list;
    }

    public void setData(List<CoreType> list){
        this.list = list;
        notifyDataSetChanged();
    }

    private void setSingle(int index){
        for(int i=0;i<list.size();i++){
            list.get(i).setCheck(i==index);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.alen_type_item, parent, false);
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

        private TextView name;
        public ObjectHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_tv);
        }
        public void bind(final CoreType entry) {
            if (entry != null) {
                name.setText(entry.getLabel());
                name.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,entry.isCheck()?R.drawable.allen_arrow_right:0,0);
                name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.setEnabled(false);
                        setSingle(getAdapterPosition());
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
        void onItemClick(View v, CoreType entry, int position);
    }
}
