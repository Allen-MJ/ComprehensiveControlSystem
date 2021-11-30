package cn.lyj.work;

import android.os.Bundle;
import android.view.View;

import java.util.List;

import allen.frame.BaseFragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.lyj.core.ModelData;
import cn.lyj.core.adapter.CoreModelAdapter;
import cn.lyj.core.entry.Model;

public class HomeFragment extends BaseFragment {
    @BindView(R2.id.rv)
    RecyclerView rv;
    @BindView(R2.id.more_task)
    AppCompatTextView moreTask;
    @BindView(R2.id.task_rv)
    RecyclerView taskRv;
    @BindView(R2.id.more_log)
    AppCompatTextView moreLog;
    @BindView(R2.id.log_rv)
    RecyclerView logRv;
    private CoreModelAdapter adapter;
    private List<Model> list;

    public static HomeFragment init(){
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.work_home_fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GridLayoutManager manager = new GridLayoutManager(getActivity(),3);
        rv.setLayoutManager(manager);
        adapter = new CoreModelAdapter();
        rv.setAdapter(adapter);
        loadModels();
        addEvent();
    }

    private void addEvent(){
        adapter.setOnItemClickListener(new CoreModelAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, Model entry, int position) {
                ModelData.init().onClickListener(getActivity(),entry);
            }
        });
    }

    @OnClick({R2.id.more_task, R2.id.more_log})
    public void onViewClicked(View view) {
        view.setEnabled(false);
        int id = view.getId();
        if(id==R.id.more_task){

        }else if(id==R.id.more_log){

        }
        view.setEnabled(true);
    }
    private void loadModels(){
        list = ModelData.init().getWorkHome();
        adapter.setData(list);
    }
}
