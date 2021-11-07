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
import allen.frame.widget.MarqueeView;
import allen.frame.widget.SquareView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

public class AllenFileShowAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<UploadFile> list;

    public AllenFileShowAdapter(){
        list = new ArrayList<>();
    }

    public void setData(List<UploadFile> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.alen_choice_file_item_show, parent, false);
        v.setLayoutParams(new ViewGroup
                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        return new ShowHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ShowHolder showHolder = (ShowHolder) holder;
        showHolder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    class ShowHolder extends RecyclerView.ViewHolder{
        private MarqueeView name;
        private SquareView icon;
        public ShowHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_name);
            icon = itemView.findViewById(R.id.item_file);
        }

        public void bind(final UploadFile file){
            if(file!=null){
                name.setText(file.getName());
                Glide.with(icon.getContext()).load(Constants.url+file.getRelativePath()).into(icon);
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
        public void onItemClick(View v,int position,UploadFile file){}
    }
}
