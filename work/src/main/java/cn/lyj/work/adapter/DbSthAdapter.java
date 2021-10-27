package cn.lyj.work.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.lyj.work.R;
import cn.lyj.work.entry.SthEntry;


public class DbSthAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<SthEntry> list;
    public DbSthAdapter(){
    }
    public void setList(List<SthEntry> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sth_db_item_layout,viewGroup,false);
        view.setLayoutParams(new ViewGroup
                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        return new DBHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        DBHolder hv = (DBHolder)viewHolder;
        hv.bind(list.get(i));
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }
    class DBHolder extends RecyclerView.ViewHolder{
        private AppCompatTextView fysx,sldw,jjcd,fyr,slsj;
        private View card;
        public DBHolder(View v){
            super(v);
            fysx = v.findViewById(R.id.db_item_sx);
            sldw = v.findViewById(R.id.db_item_sldw);
            jjcd = v.findViewById(R.id.db_item_jjcd);
            fyr = v.findViewById(R.id.db_item_fyr);
            slsj = v.findViewById(R.id.db_item_slsj);
            card = v.findViewById(R.id.card_layout);
        }
        public  void bind(final SthEntry entry){
            if(entry!=null){
                fysx.setText(entry.getFysx());
                String light = entry.getLight();
                if(light.equals("1")){
                    fysx.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.lamp_green,0,0,0);
                }else if(light.equals("2")){
                    fysx.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.lamp_yelloy,0,0,0);
                }else if(light.equals("3")){
                    fysx.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.lamp_red,0,0,0);
                }else{
                    fysx.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.lamp_blue,0,0,0);
                }
                sldw.setText(entry.getSldw()+"        受理");
                jjcd.setText(entry.getJjcd());
                fyr.setText("反映人："+entry.getFyr());
                jjcd.setText("紧急程度："+entry.getJjcd());
                slsj.setText("受理时间："+entry.getSldate());
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
        void onItemClick(SthEntry SthEntry);
    }
}
