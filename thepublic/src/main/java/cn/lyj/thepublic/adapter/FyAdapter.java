package cn.lyj.thepublic.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import allen.frame.entry.Type;
import allen.frame.tools.Constants;
import allen.frame.tools.StringUtils;
import cn.lyj.thepublic.R;
import cn.lyj.thepublic.entry.LifeService;

public class FyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Type> list;
    private int dpSize = 0;

    public FyAdapter(int dpSize){
        this.dpSize = dpSize;
        list = new ArrayList<>();
    }

    public void setList(List<Type> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public void setLfes(List<LifeService> lifeServices){
        list = new ArrayList<>();
        if(lifeServices!=null){
            for(LifeService service:lifeServices){
//                list.add(new Type("life"+service.getId(),service.getLName(), Constants.Url+service.getLFilePath(),service.getAppLAHref()));
            }
        }
        notifyDataSetChanged();
    }

    public void addList(List<Type> mlist){
        if(mlist!=null){
            for (Type t:mlist){
                if(!checkIshave(t)){
                    list.add(t);
                }
            }
        }
        notifyDataSetChanged();
    }

    private boolean checkIshave(Type type){
        boolean isHave = false;
        for (Type t:list){
            isHave = isHave||t.getId().equals(type.getId());
        }
        return isHave;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if(dpSize==35){
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_35dp_fy, parent, false);
        }else if(dpSize==25){
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_25dp_fy, parent, false);
        }else{
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_init_fy, parent, false);
        }
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

        private AppCompatTextView name;
        private AppCompatImageView icon;
        private View view;
        public ObjectHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.item_icon);
            name = itemView.findViewById(R.id.item_name);
            view = itemView.findViewById(R.id.item_layout);
        }
        public void bind(final Type entry, int index){
            if(entry!=null){
                name.setText(entry.getName());
                if(StringUtils.empty(entry.getUrl())){
                    Glide.with(view.getContext()).load(entry.getResId()).into(icon);
                }else{
                    Glide.with(view.getContext()).load(entry.getUrl()).into(icon);
                }
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        view.setEnabled(false);
                        if(listener!=null){
                            listener.itemClick(view,entry);
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
        void itemClick(View v, Type type);
    }
}
