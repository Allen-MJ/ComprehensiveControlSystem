package allen.frame.adapter;

import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import allen.frame.R;
import allen.frame.entry.WordFile;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

public class AllenAllFilesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<WordFile> files;
    private boolean onlyShow = false;

    public AllenAllFilesAdapter(boolean onlyShow){
        files = new ArrayList<>();
        this.onlyShow = onlyShow;
    }

    public void setData(List<WordFile> files){
        this.files = files;
        notifyDataSetChanged();
    }

    public List<WordFile> getFiles(){
        return files;
    }

    public void delete(WordFile file){
        files.remove(file);
        notifyDataSetChanged();
    }

    public void add(List<WordFile> addFiles){
        files.addAll(addFiles);
        notifyDataSetChanged();
    }

    public ArrayList<String> getPaths(){
        ArrayList<String> paths = new ArrayList<>();
        for(WordFile file:files){
            if(file.getType()==1){
                paths.add(file.getAttachmentPath());
            }
        }
        return paths;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if(onlyShow){
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.alen_all_files_onlyshow_item, parent, false);
            v.setLayoutParams(new ViewGroup
                    .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            return new ShowHolder(v);
        }else{
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.alen_all_files_item, parent, false);
            v.setLayoutParams(new ViewGroup
                    .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            return new ObjectHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(onlyShow){
            ShowHolder objectHolder = (ShowHolder) holder;
            objectHolder.bind(files.get(position));
        }else{
            ObjectHolder objectHolder = (ObjectHolder) holder;
            objectHolder.bind(files.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return files==null?0:files.size();
    }

    class ObjectHolder extends RecyclerView.ViewHolder{
        private AppCompatTextView name,path,delete;
        public ObjectHolder(@NonNull View itemView) {
            super(itemView);
            delete = itemView.findViewById(R.id.item_delete);
            name = itemView.findViewById(R.id.item_name);
            path = itemView.findViewById(R.id.item_path);
        }

        public void bind(final WordFile file){
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
                path.setText(file.getAttachmentPath());
                name.setOnClickListener(new View.OnClickListener() {
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

    class ShowHolder extends RecyclerView.ViewHolder{

        private AppCompatTextView name;
        public ShowHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_name);
        }
        public void bind(final WordFile file){
            if(file!=null){
                SpannableString content = new SpannableString(file.getName());
                content.setSpan(new UnderlineSpan(), 0, file.getName().length(), 0);
                name.setText(content);
                name.setOnClickListener(new View.OnClickListener() {
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
        public void onItemClick(View v,int position,WordFile file){}
        public void onItemDelete(View v,int position,WordFile file){}
    }
}
