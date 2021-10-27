package cn.lyj.work.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatTextView;

import java.util.List;

import allen.frame.adapter.CommonAdapter;
import allen.frame.adapter.ViewHolder;
import allen.frame.tools.StringUtils;
import cn.lyj.work.R;
import cn.lyj.work.entry.BllcEntry;

public class BllcAdapter extends CommonAdapter<BllcEntry> {
    private int k = 0;

    public BllcAdapter(Context context, int layoutId) {
        super(context,layoutId);
    }

    @Override
    public void convert(ViewHolder holder, BllcEntry entity, int position) {

       holder.setText(R.id.bllc_item_sl,entity.getSldate()+"受理,用时"+entity.getYs()+"天");
        String state = blstatus(entity);
        holder.setText(R.id.bllc_item_bldw,entity.getDwname()+"   "+state);
        if(StringUtils.notEmpty(entity.getPerson())){
           holder.setVisible(R.id.bllc_item_blperson,true);
            holder.setText(R.id.bllc_item_blperson,"工作人员："+entity.getPerson()+(StringUtils.notEmpty(entity.getPhone())?"("+entity.getPhone()+")":""));
        }else{
            holder.setVisible(R.id.bllc_item_blperson,false);
        }
        /*if(position==0){
            k = k+1;
        }else{
            if(!state.contains("协办")){
                k = k+1;
            }
        }*/
        holder.setText(R.id.bllc_item_sx,(position+1)+".办理时限"+entity.getSx()+"天");
        holder.setText(R.id.bllc_item_blresult,"办理结果："+entity.getResult());
    }

    public void setData(List<BllcEntry> list){
       setDatas(list);
    }


    private String blstatus(BllcEntry entry){
        String isxb = entry.getIsXbatre();
        String ismx = entry.getIsMxvoer();
        String reg = entry.getRegsate();
        String tct = entry.getTcType();
        String isbov = entry.getIsBoverId();
        if("1".equals(isxb)){
            if("1".equals(ismx)){
                if("0".equals(reg)){
                    return "（主办）办理中";
                }else{
                    return "（主办）已办结";
                }
            }else{
                if("0".equals(reg)){
                    return "（协办）办理中";
                }else{
                    return "（协办）已办理";
                }
            }
        }else{
            if("0".equals(tct)){
                return "已受理";
            }
            if("0".equals(reg)){
                return "办理中";
            }else{
                if("1".equals(isbov)){
                    return "已办结";
                }else if("3".equals(isbov)){
                    return "重审";
                }else{
                    return "已办理";
                }
            }
        }
    }
}
