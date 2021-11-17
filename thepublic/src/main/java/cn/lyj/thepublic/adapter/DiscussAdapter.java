package cn.lyj.thepublic.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.List;

import allen.frame.widget.CircleImageView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import cn.lyj.thepublic.R;
import cn.lyj.thepublic.entry.Discuss;

public class DiscussAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Discuss> list;
    private String userID;

    public DiscussAdapter(String userID){
        this.userID=userID;
    }

    public void setList(List<Discuss> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_discuss, parent, false);
        v.setLayoutParams(new ViewGroup
                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        return new ObjectHolder(v);
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ObjectHolder objectHolder = (ObjectHolder) holder;
        objectHolder.bind(list.get(position),position);
    }

    public class ObjectHolder extends RecyclerView.ViewHolder{

        private AppCompatTextView name,content,date,del;
        private CircleImageView icon;
        public ObjectHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.item_icon);
            name = itemView.findViewById(R.id.item_name);
            date = itemView.findViewById(R.id.item_date);
            content = itemView.findViewById(R.id.item_content);
            del = itemView.findViewById(R.id.item_delete);
        }
        public void bind(final Discuss entry, int position){
            if(entry!=null){
                name.setText(entry.getCreateBy());
                date.setText(entry.getCreateTime());
                content.setText(entry.getCommentContent());
                Glide.with(icon.getContext())
                        .load(entry.getPublicUserId())
                        .placeholder(R.mipmap.ic_degault_photo)
                        .error(R.mipmap.ic_degault_photo)
                        .into(icon);
                if (userID.equals(entry.getPublicUserId())) {
                    del.setVisibility(View.VISIBLE);
                }else {
                    del.setVisibility(View.GONE);
                }
                del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        view.setEnabled(false);
                        if(listener!=null){
                            listener.itemDelClick(view,entry);
                        }
                        view.setEnabled(true);
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
        void itemDelClick(View v, Discuss Discuss);
    }
}
