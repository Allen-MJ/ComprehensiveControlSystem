package allen.frame.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import allen.frame.R;
import allen.frame.entry.Units;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

public class UnitsChoiceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Units> list;

    public UnitsChoiceAdapter(){
        list = new ArrayList<>();
    }

    public void setData(List<Units> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.alen_choice_units_item, parent, false);
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

    class ObjectHolder extends RecyclerView.ViewHolder{
        private AppCompatTextView name;
        private View view;
        public ObjectHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_name);
            view = itemView.findViewById(R.id.item_layout);
        }

        public void bind(final Units units){
            if(units!=null){
                name.setText(units.getOrgName());
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.setEnabled(false);
                        if(listener!=null){
                            listener.onItemClick(v,getAdapterPosition(),units);
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
        void onItemClick(View v, int position, Units units);
    }
}
