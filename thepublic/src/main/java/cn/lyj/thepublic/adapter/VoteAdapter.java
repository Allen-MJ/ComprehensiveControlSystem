package cn.lyj.thepublic.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.lyj.thepublic.R;
import cn.lyj.thepublic.entry.VoteEntity;


public class VoteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<VoteEntity.ItemListBean> list;

//    private int maxcount;
    private Context context;


    public VoteAdapter() {
    }

    public void setList(List<VoteEntity.ItemListBean> list) {
        this.list = list;
//        this.maxcount = maxcount;
        notifyDataSetChanged();
    }

    public List<VoteEntity.ItemListBean> getList() {
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
        return list == null ? 0 : list.size();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ObjectHolder objectHolder = (ObjectHolder) holder;
        objectHolder.bind(list.get(position), position);
    }

    public class ObjectHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView sort, title,edit;
        private RecyclerView option;
        private OptionAdapter adapter;

        public ObjectHolder(@NonNull View itemView) {
            super(itemView);
            sort = itemView.findViewById(R.id.item_sort);
            title = itemView.findViewById(R.id.item_title);
            edit = itemView.findViewById(R.id.item_Edit);
            option = itemView.findViewById(R.id.item_option_layout);
            context=itemView.getContext();
            LinearLayoutManager manager = new LinearLayoutManager(context);
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            option.setLayoutManager(manager);
        }

        public void bind(final VoteEntity.ItemListBean entry, final int position) {
            if (entry != null) {
                if (entry.getOptions()==null) {
                    entry.setOptions(entry.getValueList());
                }
                final String type=entry.getItemType();//itemType：1文本框2单选框3复选框4下拉框5文本域

                sort.setText((position + 1) + "、");

                switch (type){
                    case "1":
                        title.setText("(单项填空)" + entry.getItemName());
                        edit.setVisibility(View.VISIBLE);
                        edit.setHint("点击输入...");
                        edit.setMaxLines(1);
                        break;
                    case "2":
                        adapter = new OptionAdapter(type);
                        option.setAdapter(adapter);
                        adapter.setList(entry.getOptions());
                        title.setText( "(单选)"  + entry.getItemName());
                        break;
                    case "3":
                        adapter = new OptionAdapter(type);
                        option.setAdapter(adapter);
                        adapter.setList(entry.getOptions());
                        title.setText( "(多选)"  + entry.getItemName());
                        break;
                    case "4":
                        title.setText("(下拉选择)" + entry.getItemName());
                        edit.setVisibility(View.VISIBLE);
                        edit.setHint("点击选择");
                        edit.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0,
                              R.mipmap.ic_logo_down , 0);

                        break;
                    case "5":
                        title.setText("(填空)" + entry.getItemName());
                        edit.setVisibility(View.VISIBLE);
                        edit.setHint("点击输入...");
                        edit.setMaxLines(3);
                        break;
                }
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        if (type.equals("1")){
                            final View dialogView = LayoutInflater.from(context)
                                    .inflate(R.layout.dialog_customize,null);
                            builder.setTitle("请输入");
                            builder.setView(dialogView);
                            builder.setPositiveButton("确定",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // 获取EditView中的输入内容
                                            AppCompatEditText edit_text =dialogView.findViewById(R.id.edit_text);
                                            String s=edit_text.getText().toString();
                                            entry.setAnswer(s);
                                            edit.setText(s);

                                        }
                                    });
                            builder.show();
                        }else if (type.equals("4")){

                            builder.setTitle("请选择");
                            final String items[] = entry.getItemValue().split(";");
                            builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //1.把选中的条目取出来
                                    String item = items[which];
                                    entry.setAnswer(item);
                                    edit.setText(item);
                                    //2.然后把对话框关闭
                                    dialog.dismiss();
                                }
                            });
                            builder.show();
                        }else if (type.equals("5")){
                            final View dialogView = LayoutInflater.from(context)
                                    .inflate(R.layout.dialog_customize,null);
                            builder.setTitle("请输入");
                            builder.setView(dialogView);
                            builder.setPositiveButton("确定",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // 获取EditView中的输入内容
                                            AppCompatEditText edit_text =dialogView.findViewById(R.id.edit_text);
                                            String s=edit_text.getText().toString();
                                            entry.setAnswer(s);
                                            edit.setText(s);
                                        }
                                    });
                            builder.show();
                        }
                    }
                });
                if (adapter!=null) {
                    adapter.setOnItemClickListener(new OptionAdapter.OnItemClickListener() {
                        @Override
                        public void itemChoiceClick(int index, boolean isCheck) {
                            list.get(position).setChoice(index, isCheck);
                            notifyItemChanged(position);
                        }
                    });
                }
            }
        }
    }
}
