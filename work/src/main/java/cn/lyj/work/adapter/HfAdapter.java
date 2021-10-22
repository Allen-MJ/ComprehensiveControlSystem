package cn.lyj.work.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.appcompat.widget.AppCompatTextView;

import java.util.List;

import allen.frame.adapter.CommonAdapter;
import allen.frame.adapter.ViewHolder;
import allen.frame.tools.StringUtils;
import cn.lyj.work.R;
import cn.lyj.work.entry.HfEntry;

public class HfAdapter extends CommonAdapter<HfEntry> {
    private Context context;
    private boolean isShow = false;

    public HfAdapter(Context context, int layoutID, boolean isShow) {
        super(context, layoutID);
        this.isShow = isShow;
    }

    public void setData(List<HfEntry> list) {
        setDatas(list);
    }

    public void setHf(int index, String msg) {
        getDatas().get(index).setReplyContent(msg);
        notifyDataSetChanged();
    }

    @Override
    public void convert(ViewHolder holder, final HfEntry entity, final int position) {

        holder.setText(R.id.hf_item_sort, (position + 1) + "  ");
        holder.setText(R.id.hf_item_date, entity.getMessageDate());
        holder.setText(R.id.hf_item_con, entity.getName() + "：" + entity.getMessageContent());
        if (isShow) {
            if (StringUtils.empty(entity.getReplyContent())) {
                holder.setVisible(R.id.hf_item_hfbt, true);
            } else {
                holder.setVisible(R.id.hf_item_hfbt, false);
            }
        } else {
            holder.setVisible(R.id.hf_item_hfbt, false);
        }
        if (StringUtils.empty(entity.getReplyContent())) {
            holder.setVisible(R.id.hf_item_hf, true);
        } else {
            holder.setVisible(R.id.hf_item_hf, true);
            holder.setText(R.id.hf_item_hf, "回复：" + entity.getReplyContent());
        }
        holder.setOnClickListener(R.id.hf_item_hfbt,new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lisener != null) {
                    lisener.itemReplay(position, entity);
                }
            }
        });
    }

    private OnItemReplyLisener lisener;

    public void setItemReplyLisener(OnItemReplyLisener lisener) {
        this.lisener = lisener;
    }

    public interface OnItemReplyLisener {
        void itemReplay(int position, HfEntry entry);
    }
}
