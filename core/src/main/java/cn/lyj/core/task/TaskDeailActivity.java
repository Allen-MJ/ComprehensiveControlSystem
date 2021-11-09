package cn.lyj.core.task;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import allen.frame.AllenBaseActivity;
import allen.frame.adapter.CommonAdapter;
import allen.frame.adapter.ViewHolder;
import allen.frame.entry.Response;
import allen.frame.net.Callback;
import allen.frame.net.Https;
import allen.frame.tools.Constants;
import allen.frame.tools.MsgUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.lyj.core.R;
import cn.lyj.core.R2;
import cn.lyj.core.api.CoreApi;
import cn.lyj.core.entry.FeedEntity;
import cn.lyj.core.entry.TaskEntity;

public class TaskDeailActivity extends AllenBaseActivity {

    @BindView(R2.id.title)
    AppCompatTextView title;
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.tv_task_title)
    AppCompatTextView tvTaskTitle;
    @BindView(R2.id.tv_wg)
    AppCompatTextView tvWg;
    @BindView(R2.id.tv_task_info)
    AppCompatEditText tvTaskInfo;
    @BindView(R2.id.radio_1)
    AppCompatRadioButton radio1;
    @BindView(R2.id.radio_2)
    AppCompatRadioButton radio2;
    @BindView(R2.id.tv_zpdx)
    AppCompatTextView tvZpdx;
    @BindView(R2.id.tv_souli)
    AppCompatTextView tvSouli;
    @BindView(R2.id.tv_fankui)
    AppCompatTextView tvFankui;
    @BindView(R2.id.recyclerview)
    RecyclerView recyclerView;
    private TaskEntity taskEntity;
    private List<FeedEntity> list;
    private CommonAdapter<FeedEntity> adapter;
    private boolean isFeed=false;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_task_deail;
    }

    @Override
    protected void initBar() {
        setToolbarTitle(toolbar,"任务详情",true);

    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        taskEntity= (TaskEntity) getIntent().getSerializableExtra(Constants.Key_1);
        tvTaskTitle.setText(taskEntity.getTaskName());
        tvTaskInfo.setText(taskEntity.getTaskDesc());
        tvWg.setText(taskEntity.getCreateTime());
        tvZpdx.setText(taskEntity.getSpecifyObjName());
        if (taskEntity.getSpecifyType()==1){//1 个人   2 网格
            radio1.setChecked(true);
        }else {
            radio2.setChecked(true);
        }
        if (taskEntity.getProgress()==1){
            tvFankui.setVisibility(View.GONE);
            tvSouli.setVisibility(View.VISIBLE);
        }else if (taskEntity.getProgress()==3){
            tvFankui.setVisibility(View.VISIBLE);
            tvSouli.setVisibility(View.GONE);
        }else {
            tvFankui.setVisibility(View.VISIBLE);
            tvSouli.setVisibility(View.GONE);
            loadFeed();
        }
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        initAdapter();

    }
    private void initAdapter() {
        adapter=new CommonAdapter<FeedEntity>(context,R.layout.feed_item) {
            @Override
            public void convert(ViewHolder holder, FeedEntity entity, int position) {
                holder.setText(R.id.item_name,entity.getFeedbackPeople());
                holder.setText(R.id.item_info,entity.getFeedback());
                holder.setText(R.id.item_date,entity.getCreateTime());
            }
        };
        recyclerView.setAdapter(adapter);

    }
    @Override
    protected void addEvent() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFeed){
                    setResult(RESULT_OK);
                }
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case 100:
                    isFeed=true;
                    loadFeed();
                    break;
            }
        }
    }

    private void accept(){
        Https.with(this).url(CoreApi._task_accept).addParam("taskid",taskEntity.getTaskId()).post()
                .enqueue(new Callback() {
                    @Override
                    public void success(Object data) {
                        MsgUtils.showLongToast(context,"受理成功!");
                        setResult(RESULT_OK);
                        finish();
                    }
                    @Override
                    public void token() {
                        MsgUtils.showShortToast(context,"账号登录过期,请重新登录!");
                    }
                    @Override
                    public void fail(Response response) {
                        MsgUtils.showShortToast(context,response.getMsg());
                    }
                });
    }

    private void loadFeed(){
        Https.with(this).url(CoreApi._get_feed_list).addParam("taskid",taskEntity.getTaskId())
                .get().enqueue(new Callback<List<FeedEntity>>() {
            @Override
            public void success(List<FeedEntity> data) {
                list=data;
                adapter.setDatas(list);
            }

            @Override
            public void fail(Response response) {

            }
        });
    }

    @OnClick({R2.id.tv_souli, R2.id.tv_fankui})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.tv_souli) {
            accept();
        } else if (id == R.id.tv_fankui) {
            startActivityForResult(new Intent(context,FeedActivity.class).putExtra(Constants.Key_1,taskEntity),100);
        }
    }
}