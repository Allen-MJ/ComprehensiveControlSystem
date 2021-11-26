package cn.lyj.core.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import cn.lyj.core.R;
import cn.lyj.core.entry.House;
import cn.lyj.core.entry.RentHouse;


public class RentHouseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<RentHouse> list;

    public RentHouseAdapter() {
    }

    public void setList(List<RentHouse> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.core_renthouse_item, parent, false);
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

        private AppCompatTextView rent, wg, address, delete;
        private View view;
        public ObjectHolder(@NonNull View itemView) {
            super(itemView);
            rent = itemView.findViewById(R.id.item_house_rent);
            wg = itemView.findViewById(R.id.item_wg);
            address = itemView.findViewById(R.id.item_address);
            delete = itemView.findViewById(R.id.item_delete);
            view = itemView.findViewById(R.id.item_layout);
        }
        public void bind(final RentHouse entry) {
            if (entry != null) {
                rent.setText(entry.getB1613());
                address.setText(entry.getB1602());
                wg.setText(entry.getGidObj().getOrgFullName());
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

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, RentHouse entry, int position);
        void onItemDelete(View v, RentHouse entry, int position);
    }
}
