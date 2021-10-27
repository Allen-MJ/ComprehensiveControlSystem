package cn.lyj.thepublic.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import allen.frame.widget.CircleImageView;
import allen.frame.widget.GoodView;
import cn.lyj.thepublic.R;
import cn.lyj.thepublic.entry.Discuss;

public class DiscussAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Discuss> list;

    public DiscussAdapter(){
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

        private AppCompatTextView name,content,date;
        private CircleImageView icon;
        public ObjectHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.item_icon);
            name = itemView.findViewById(R.id.item_name);
            date = itemView.findViewById(R.id.item_date);
            content = itemView.findViewById(R.id.item_content);
        }
        public void bind(Discuss entry,int position){
            if(entry!=null){
//                name.setText(new String2Replace().replace(1,entry.getUName().length(),entry.getUName(),"*"));
//                date.setText(entry.getAddTime());
//                content.setText(entry.getCommentContent());
//                Glide.with(icon.getContext())
//                        .load(entry.getPhotoUrl())
//                        .placeholder(R.mipmap.ic_degault_photo)
//                        .error(R.mipmap.ic_degault_photo)
//                        .into(icon);
            }
        }
    }
    private OnItemClickListener listener;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
    public interface OnItemClickListener{
        void itemZanClick(View v, Discuss Discuss);
    }
}
