package cn.lyj.work;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import allen.frame.BaseFragment;
import allen.frame.entry.Response;
import allen.frame.net.Callback;
import allen.frame.net.Https;
import allen.frame.tools.Constants;
import allen.frame.tools.MsgUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.lyj.core.ModelData;
import cn.lyj.core.adapter.CoreModelAdapter;
import cn.lyj.core.adapter.LogAdapter;
import cn.lyj.core.api.CoreApi;
import cn.lyj.core.entry.Log;
import cn.lyj.core.entry.Model;
import cn.lyj.core.log.LogListActivity;
import cn.lyj.core.log.UpdateLogActivity;
import cn.lyj.core.task.MyTaskListActivity;

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
    private LogAdapter logAdapter;

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
        initLog();
        addEvent();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadLog();
    }

    private void initLog(){
        logAdapter = new LogAdapter();
        logAdapter.setShow(true);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        logRv.setLayoutManager(manager);
        logRv.setAdapter(logAdapter);
    }

    private void addEvent(){
        adapter.setOnItemClickListener(new CoreModelAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, Model entry, int position) {
                ModelData.init().onClickListener(getActivity(),entry);
            }
        });
        logAdapter.setOnItemClickListener(new LogAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, Log entry, int position) {
                startActivity(new Intent(getActivity(), UpdateLogActivity.class)
                        .putExtra(Constants.ObjectFirst,entry));
            }

            @Override
            public void onItemDelete(View v, Log entry, int position) {

            }
        });
    }

    @OnClick({R2.id.more_task, R2.id.more_log})
    public void onViewClicked(View view) {
        view.setEnabled(false);
        int id = view.getId();
        if(id==R.id.more_task){
            startActivity(new Intent(getActivity(), MyTaskListActivity.class));
        }else if(id==R.id.more_log){
            startActivity(new Intent(getActivity(), LogListActivity.class));
        }
        view.setEnabled(true);
    }
    private void loadModels(){
        list = ModelData.init().getWorkHome();
        adapter.setData(list);
    }

    private void loadLog() {
        Https.with(getActivity()).url(CoreApi._core_11)
                .addParam("workItem","").addParam("progress","").addParam("page",0).addParam("size",5).get()
                .enqueue(new Callback<List<Log>>() {
                    @Override
                    public void success(List<Log> data) {
                        logAdapter.setList(data);
                    }

                    @Override
                    public void token() {
                        actHelper.tokenErro2Login(HomeFragment.this);
                    }

                    @Override
                    public void fail(Response response) {
                        MsgUtils.showShortToast(getActivity(),response.getMsg());
                    }
                });
    }
}
