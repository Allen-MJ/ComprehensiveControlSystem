package cn.lyjj.thepublic.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import cn.lyjj.thepublic.R;
import cn.lyjj.thepublic.entry.SthProgress;


public class SthProgressAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SthProgress> list;

    public SthProgressAdapter() {
    }

    public void setList(List<SthProgress> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tipoff_progress_item, parent, false);
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

        private AppCompatTextView date, status,dw;

        public ObjectHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.item_date);
            dw = itemView.findViewById(R.id.item_dw);
            status = itemView.findViewById(R.id.item_status);
        }

        public void bind(final SthProgress entry) {
            if (entry != null) {
                date.setText(entry.getProcessTime());
                dw.setText(entry.getOrg().getOrgName());
                status.setText(entry.isProcessState()?"已办理":"办理中");
            }
        }
    }
}
