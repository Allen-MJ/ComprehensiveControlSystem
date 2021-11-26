package cn.lyj.core.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import allen.frame.entry.Grid;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import cn.lyj.core.R;
import cn.lyj.core.entry.GridMember;


public class GridMemberAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<GridMember> list;

    public GridMemberAdapter() {
    }

    public void setList(List<GridMember> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.core_grid_member_list_item, parent, false);
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

        private AppCompatTextView name,grids;
        private View view;
        public ObjectHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_name);
            grids = itemView.findViewById(R.id.item_grids);
            view = itemView.findViewById(R.id.item_layout);
        }
        public void bind(final GridMember entry) {
            if (entry != null) {
                name.setText(entry.getUsername());
                grids.setText(entry.getGridName());
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
        void onItemClick(View v, GridMember entry, int position);
        void onItemDelete(View v, GridMember entry, int position);
    }
}
