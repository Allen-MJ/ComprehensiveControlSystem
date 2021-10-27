package cn.lyj.work.adapter;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.lyj.work.R;
import cn.lyj.work.entry.SthEntry;


public class GdSthAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<SthEntry> list;
    public GdSthAdapter(){
    }
    public void setList(List<SthEntry> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sth_gd_item_layout,viewGroup,false);
        view.setLayoutParams(new ViewGroup
                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        return new DBHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        DBHolder hv = (DBHolder)viewHolder;
        hv.bind(list.get(i),i);
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }
    class DBHolder extends RecyclerView.ViewHolder{
        private AppCompatTextView fysx,sldw,bljg;
        private View card;
        public DBHolder(View v){
            super(v);
            fysx = v.findViewById(R.id.gd_item_sx);
            sldw = v.findViewById(R.id.gd_item_sldw);
            bljg = v.findViewById(R.id.gd_item_bljg);
            card = v.findViewById(R.id.card_layout);
        }
        public  void bind(final SthEntry entry,int i){
            if(entry!=null){
                String state = entry.getState();
                if("0".equals(state)){
                    fysx.setText(Html.fromHtml((i+1)+"."+"<font color=\"red\">[未归档]</font>&nbsp;"+entry.getFysx()));
                }else{
                    fysx.setText(Html.fromHtml((i+1)+"."+"<font color=\"green\">[已归档]</font>&nbsp;"+entry.getFysx()));
                }
                sldw.setText(entry.getSldw()+"        受理");
                bljg.setText("办理结果："+entry.getBlresult());
                card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(itemClickListener!=null){
                            itemClickListener.onItemClick(entry);
                        }
                    }
                });
            }
        }
    }
    private OnItemClickListener itemClickListener;
    public void setOnItemClickListener(OnItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }
    public interface OnItemClickListener{
        void onItemClick(SthEntry entry);
    }
}
