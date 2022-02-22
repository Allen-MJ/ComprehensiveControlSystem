package cn.lyj.core.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import cn.lyj.core.R;
import cn.lyj.core.entry.BmkhEntry;
import cn.lyj.core.entry.Word;

public class BmkhAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<BmkhEntry> list;
    public void setList(List<BmkhEntry> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.core_kaohe_bm_list_item, parent, false);
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

        private AppCompatTextView dw,info;
        private View view;
        public ObjectHolder(@NonNull View itemView) {
            super(itemView);
            dw = itemView.findViewById(R.id.item_bm_name);
            info = itemView.findViewById(R.id.item_kaohe_info);
            view = itemView.findViewById(R.id.item_layout);
        }
        public void bind(final BmkhEntry entry) {
            if (entry != null) {
                dw.setText(entry.getOrgname());
                info.setText(String.format("考核任务总数%s,总分%s。其中完成数量%s,得分%s分,完成占比%s," +
                        "缺报数量%s,占比%s,迟报数量%s,占比%s,主动报送数量%s,占比%s,合格数量%s,占比%s",entry.getAll(),entry.getAllscore(),entry.getFinish(),
                        entry.getFinishscore(),entry.getFinishpercent(),entry.getLack(),entry.getLackpercent(),entry.getLate(),entry.getLatepercent(),
                        entry.getSelf(),entry.getSelfpercent(),entry.getStandard(),entry.getStandardpercent()));
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

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public interface OnItemClickListener{
        void onItemClick(View v, BmkhEntry entry, int position);
    }
}
