package cn.lyj.work.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.lyj.core.adapter.CoreModelAdapter;
import cn.lyj.core.entry.Model;
import cn.lyj.work.R;


public class ModelParentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Model> list;

    public ModelParentAdapter() {
    }

    public void setList(List<Model> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.work_home_model_item, parent, false);
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
        objectHolder.bind(list.get(position));
    }

    public class ObjectHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView name;
        private RecyclerView rv;
        private GridLayoutManager manager;
        private CoreModelAdapter adapter;
        public ObjectHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_name);
            rv = itemView.findViewById(R.id.item_rv);
            manager = new GridLayoutManager(name.getContext(),3);
            rv.setLayoutManager(manager);
            adapter = new CoreModelAdapter();
            rv.setAdapter(adapter);
        }
        public void bind(final Model entry) {
            if (entry != null) {
                name.setText(entry.getName());
                adapter.setData(entry.getList());
                adapter.setOnItemClickListener(new CoreModelAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, Model entry, int position) {
                        if(listener!=null){
                            listener.onItemClick(v,entry,position);
                        }
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
        void onItemClick(View v, Model entry, int position);
    }
}
