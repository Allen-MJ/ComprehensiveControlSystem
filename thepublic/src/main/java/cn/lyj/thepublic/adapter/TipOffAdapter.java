package cn.lyj.thepublic.adapter;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import allen.frame.tools.DateUtils;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import cn.lyj.thepublic.R;
import cn.lyj.thepublic.entry.SthEntry;
import cn.lyj.thepublic.entry.WjdcEntity;


public class TipOffAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SthEntry> list;

    public TipOffAdapter() {
    }

    public void setList(List<SthEntry> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.public_tipoff_list_item, parent, false);
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

        private AppCompatTextView name, date, status,dw,content,sldate;
        private View view;

        public ObjectHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_tip_name);
            date = itemView.findViewById(R.id.item_tip_date);
            content = itemView.findViewById(R.id.item_tip_content);
            dw = itemView.findViewById(R.id.item_tip_dw);
            status = itemView.findViewById(R.id.item_tip_status);
            sldate = itemView.findViewById(R.id.item_tip_sldate);
            view = itemView.findViewById(R.id.item_layout);
        }

        public void bind(final SthEntry entry) {
            if (entry != null) {
                name.setText(entry.getName());
                date.setText(entry.getAddTime());
                content.setText(Html.fromHtml("<font color=\"gray\">反映事项：</font>"+entry.getContent()));
                dw.setText(entry.getOrg().getOrgName());
                status.setText(entry.getState());
                sldate.setText(entry.getSeizedTime());
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.setEnabled(false);
                        if(listener!=null){
                            listener.itemClick(v,entry,getAdapterPosition());
                        }
                        v.setEnabled(true);
                    }
                });
            }
        }
    }

    private int getStatusResId(double status) {
        int resId = 0;
        if (status < 0) {
            resId = R.mipmap.ic_news_vote_status_finish;
        } else {
            resId = R.mipmap.ic_news_vote_status_start;
        }
        return resId;
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void itemClick(View v, SthEntry entry, int position);
    }
}
