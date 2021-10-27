package cn.lyj.thepublic.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.lyj.thepublic.R;
import cn.lyj.thepublic.entry.VoteEntity;


public class VoteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<VoteEntity.ItemListBean> list;

    private int status;
    private int maxcount;

    public VoteAdapter(int status){
        this.status = status;
    }
    public VoteAdapter(){
    }
    public void setList(List<VoteEntity.ItemListBean> list, int maxcount){
        this.list = list;
        this.maxcount=maxcount;
        notifyDataSetChanged();
    }

    public List<VoteEntity.ItemListBean> getList(){
        return list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_vote, parent, false);
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

        private AppCompatTextView sort,title;
        private RecyclerView option;
        private OptionAdapter adapter;
        public ObjectHolder(@NonNull View itemView) {
            super(itemView);
            sort = itemView.findViewById(R.id.item_sort);
            title = itemView.findViewById(R.id.item_title);
            option = itemView.findViewById(R.id.item_option_layout);
            LinearLayoutManager manager = new LinearLayoutManager(itemView.getContext());
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            option.setLayoutManager(manager);
        }
        public void bind(VoteEntity.ItemListBean entry, final int position){
            if(entry!=null){
                adapter = new OptionAdapter(entry.getItemType(),status);
                adapter.setList(entry,maxcount);
                option.setAdapter(adapter);
                sort.setText((position+1)+"、");
                title.setText((entry.getItemType().equals("2")?"(单选)":"(多选)")+entry.getItemName());
                adapter.setOnItemClickListener(new OptionAdapter.OnItemClickListener() {
                    @Override
                    public void itemChoiceClick(int index, boolean isCheck) {
//                        list.get(position).setChoice(index,isCheck);
//                        notifyItemChanged(position);
                    }
                });
            }
        }
    }
}
