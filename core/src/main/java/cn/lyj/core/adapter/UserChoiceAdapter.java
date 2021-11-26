package cn.lyj.core.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import allen.frame.entry.User;
import allen.frame.tools.StringUtils;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import cn.lyj.core.R;

public class UserChoiceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<User> list;
    private boolean single = true;
    private Map<String,Boolean> map;
    private int[] resIds;

    public UserChoiceAdapter(boolean single) {
        this.single = single;
        map = new HashMap<>();
        if(single){
            resIds = new int[]{
                    R.mipmap.ic_single_check, R.mipmap.ic_single_uncheck
            };
        }else{
            resIds = new int[]{
                    R.mipmap.ic_multi_check, R.mipmap.ic_multi_uncheck
            };
        }
    }

    public void setList(List<User> list, String ids){
        this.list = list;
        for (User user:list){
            String id = user.getId();
            map.put(id, StringUtils.empty(ids)?false:ids.contains(id));
        }
        notifyDataSetChanged();
    }

    public String getIds(){
        StringBuffer sb = new StringBuffer();
        for(User user:list){
            if(map.get(user.getId())){
                sb.append(","+user.getId());
            }
        }
        return sb.toString().replaceFirst(",","");
    }

    public String getNames(){
        StringBuffer sb = new StringBuffer();
        for(User user:list){
            if(map.get(user.getId())){
                sb.append(","+user.getUsername());
            }
        }
        return sb.toString().replaceFirst(",","");
    }

    private void check(String id){
        if(single){
            for(Map.Entry<String,Boolean> entry:map.entrySet()){
                String key = entry.getKey();
                map.put(key,key==id);
            }
        }else{
            boolean b = map.get(id);
            map.put(id,!b);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.core_choice_person_item, parent, false);
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

        private AppCompatTextView name,phone;
        private View view;
        public ObjectHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_name);
            phone = itemView.findViewById(R.id.item_phone);
            view = itemView.findViewById(R.id.item_layout);
        }
        public void bind(final User entry) {
            if (entry != null) {
                name.setText(entry.getUsername());
                name.setCompoundDrawablesRelativeWithIntrinsicBounds(map.get(entry.getId())?resIds[0]:resIds[1],0,0,0);
                phone.setText(entry.getPhone());
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.setEnabled(false);
                        check(entry.getId());
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
        void onItemClick(View v, User entry, int position);
    }
}
