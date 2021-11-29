package allen.frame.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import allen.frame.R;
import allen.frame.entry.File;
import allen.frame.entry.UploadFile;
import allen.frame.tools.Constants;
import allen.frame.tools.FileUtils;
import allen.frame.widget.MarqueeView;
import allen.frame.widget.SquareView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

public class AllenFileChoiceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<File> files;
    private final int TypeAdd = 0;
    private final int TypeFile = 1;

    public AllenFileChoiceAdapter(){
        files = new ArrayList<>();
    }

    public void setData(ArrayList<File> files){
        this.files = files;
        notifyDataSetChanged();
    }

    public void delete(File file){
        files.remove(file);
        notifyDataSetChanged();
    }

    public ArrayList<String> getPaths(){
        ArrayList<String> paths = new ArrayList<>();
        for(File file:files){
            paths.add(file.getPath());
        }
        return paths;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if(viewType==TypeAdd){
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.alen_choice_file_item_add, parent, false);
            v.setLayoutParams(new ViewGroup
                    .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            return new AddHolder(v);
        }else{
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.alen_choice_file_item, parent, false);
            v.setLayoutParams(new ViewGroup
                    .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            return new ObjectHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position)==TypeAdd){
            AddHolder addHolder = (AddHolder) holder;
            addHolder.bind();
        }else{
            ObjectHolder objectHolder = (ObjectHolder) holder;
            objectHolder.bind(files.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return (files==null?0:files.size())+1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position<getItemCount()-1){
            return TypeFile;
        }else{
            return TypeAdd;
        }
    }

    public class AddHolder extends RecyclerView.ViewHolder{

        private AppCompatTextView add;
        public AddHolder(@NonNull View itemView) {
            super(itemView);
            add = itemView.findViewById(R.id.item_add);
        }
        public void bind(){
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setEnabled(false);
                    if(listener!=null){
                        listener.onAddClick();
                    }
                    v.setEnabled(true);
                }
            });
        }
    }

    class ObjectHolder extends RecyclerView.ViewHolder{
        private AppCompatImageView delete;
        private MarqueeView name;
        private SquareView icon;
        public ObjectHolder(@NonNull View itemView) {
            super(itemView);
            delete = itemView.findViewById(R.id.item_delete);
            name = itemView.findViewById(R.id.item_name);
            icon = itemView.findViewById(R.id.item_file);
        }

        public void bind(final File file){
            if(file!=null){
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.setEnabled(false);
                        if(listener!=null){
                            listener.onItemDelete(v,getAdapterPosition(),file);
                        }
                        v.setEnabled(true);
                    }
                });
                name.setText(file.getName());
                if(FileUtils.isAudioFileType(file.getName())){
                    icon.setImageResource(R.mipmap.allen_file_audio);
                }else if(FileUtils.isVideoFileType(file.getName())){
                    icon.setImageResource(R.mipmap.allen_file_video);
                }else if(FileUtils.isImageFileType(file.getName())){
                    Glide.with(icon.getContext()).load(file.getPath()).into(icon);
                }else{
                    icon.setImageResource(R.mipmap.allen_file_folder);
                }
                icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.setEnabled(false);
                        if(listener!=null){
                            listener.onItemClick(v,getAdapterPosition(),file);
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

    public abstract static class OnItemClickListener{
        public void onItemClick(View v,int position,File file){}
        public void onItemDelete(View v,int position,File file){}
        public void onAddClick(){}
    }
}
