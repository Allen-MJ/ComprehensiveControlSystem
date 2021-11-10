package cn.lyj.leader;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import allen.frame.BaseFragment;
import allen.frame.tools.Constants;
import allen.frame.tools.Logger;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import cn.lyj.core.adapter.CoreModelAdapter;
import cn.lyj.core.chart.ChartActivity;
import cn.lyj.core.entry.Model;
import cn.lyjj.tipoff.TipOffListActivity;
import cn.lyjj.tipoff.TipoffActivity;

public class ItemFragment extends BaseFragment {
    @BindView(R2.id.rv)
    RecyclerView rv;
    private CoreModelAdapter adapter;
    private List<Model> models;
    private int type = 0;

    public static ItemFragment init(int type){
        ItemFragment fragment = new ItemFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.Key_1,type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.core_home_item;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        type = getArguments().getInt(Constants.Key_1,0);
        Logger.e("type","type->"+type);
        GridLayoutManager manager = new GridLayoutManager(getActivity(),3);
        rv.setLayoutManager(manager);
        adapter = new CoreModelAdapter();
        rv.setAdapter(adapter);
        loadModel();
        addEvent();
    }
    private void loadModel(){
        models = new ArrayList<>();
        if(type==0){
            String[] ids = new String[]{"1","2","3","4","5","7","8","9","10","11","12","13"};
            String[] names = new String[]{"实有人口地区分布","特殊人口地区分布","特殊人口性别比例",
                    "特殊人口年龄结构","特殊人口文化程度","重点青少年地区分布","重点青少年性别比例",
                    "重点青少年年龄结构","重点青少年文化程度",
                    "重点青少年类型分布","出租房地区分布","出租房出租用途比例"};
            int[] resIds = new int[]{R.mipmap.tb_1,R.mipmap.tb_2,R.mipmap.tb_3,R.mipmap.tb_4,R.mipmap.tb_5,
                    R.mipmap.tb_6,R.mipmap.tb_7,R.mipmap.tb_8,R.mipmap.tb_9,R.mipmap.tb_10,R.mipmap.tb_11,
                    R.mipmap.tb_12};
            for(int i=0;i<ids.length;i++){
                Model item = new Model();
                item.setId(ids[i]);
                item.setName(names[i]);
                item.setResId(resIds[i]);
                models.add(item);
            }
        }else{
            Model bl = new Model();
            bl.setId("sth_1");
            bl.setName("上报事件");
            bl.setResId(R.drawable.tip_baoliao);
            models.add(bl);
            Model cx = new Model();
            cx.setId("sth_2");
            cx.setName("事件查询");
            cx.setResId(R.drawable.tip_jindu);
            models.add(cx);
        }
        adapter.setData(models);
    }
    private void addEvent(){
        adapter.setOnItemClickListener(new CoreModelAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, Model entry, int position) {
                String id = entry.getId();
                if(id.equals("sth_1")){
                    startActivity(new Intent(getActivity(), TipoffActivity.class).putExtra(Constants.Key_1,0));
                }else if(id.equals("sth_2")){
                    startActivity(new Intent(getActivity(), TipOffListActivity.class).putExtra(Constants.Key_1,"事件查询"));
                }else{
                    startActivity(new Intent(getActivity(), ChartActivity.class).putExtra(Constants.ObjectFirst,entry));
                }
            }
        });
    }
}
