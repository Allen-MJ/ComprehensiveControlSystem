package cn.lyj.work.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import allen.frame.widget.SquareView;
import cn.lyj.work.R;

public class ImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<String> list;
    private final int Type_Add = 0;
    private final int Type_List = 1;

    public ImageAdapter(){
        list = new ArrayList<>();
    }

    public void setList(ArrayList<String> list){
        if(list!=null){
            this.list = list;
        }
        notifyDataSetChanged();
    }

    public void addImage(ArrayList<String> list){
        if(list!=null){
            this.list.addAll(list);
        }
        notifyDataSetChanged();
    }
    public void addImage(String entry){
        list.add(entry);
        notifyDataSetChanged();
    }

    public ArrayList<String> getImages(){
        return list==null?new ArrayList<String>():list;
    }

    public void delete(int index){
        list.remove(index);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if(viewType==Type_List){
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_image, parent, false);
            v.setLayoutParams(new ViewGroup
                    .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            return new ObjectHolder(v);
        }else {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_image_add, parent, false);
            v.setLayoutParams(new ViewGroup
                    .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            return new AddHolder(v);
        }

    }

    @Override
    public int getItemCount() {
        return list==null?1:list.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        int size = list==null?0:list.size();
        if(size==0){
            return Type_Add;
        }else if(position<size){
            return Type_List;
        }else{
            return Type_Add;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position)==Type_Add){
            AddHolder addHolder = (AddHolder) holder;
            addHolder.bind();
        }else{
            ObjectHolder objectHolder = (ObjectHolder) holder;
            objectHolder.bind(list.get(position),position);
        }
    }

    public class ObjectHolder extends RecyclerView.ViewHolder{

        private AppCompatImageView delete;
        private SquareView icon;
        public ObjectHolder(@NonNull View itemView) {
            super(itemView);
            delete = itemView.findViewById(R.id.item_delete);
            icon = itemView.findViewById(R.id.item_icon);
        }
        public void bind(final String entry, final int position){
            if(entry!=null){
                icon.setImageURI(Uri.parse(entry));
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        view.setEnabled(false);
                        delete(position);
                        if(listener!=null){
                            listener.deleteClick(view,position);
                        }
                        view.setEnabled(true);
                    }
                });
            }
        }
    }
    public class AddHolder extends RecyclerView.ViewHolder{

        private View view;
        public AddHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.item_add);
        }
        public void bind(){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.setEnabled(false);
                    if(listener!=null){
                        listener.addClick(view);
                    }
                    view.setEnabled(true);
                }
            });
        }
    }
    private OnItemClickListener listener;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
    public interface OnItemClickListener{
        void deleteClick(View v, int position);
        void addClick(View v);
    }
}