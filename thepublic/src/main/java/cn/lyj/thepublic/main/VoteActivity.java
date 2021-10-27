package cn.lyj.thepublic.main;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import allen.frame.AllenBaseActivity;
import allen.frame.tools.Constants;
import allen.frame.tools.Logger;
import allen.frame.tools.MsgUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.lyj.thepublic.R;
import cn.lyj.thepublic.R2;
import cn.lyj.thepublic.adapter.VoteAdapter;
import cn.lyj.thepublic.entry.VoteEntity;
import cn.lyj.thepublic.entry.WjdcEntity;

public class VoteActivity extends AllenBaseActivity {
    @BindView(R2.id.toolbar)
    Toolbar bar;
    @BindView(R2.id.vote_end)
    AppCompatTextView voteEnd;
    @BindView(R2.id.vote_status)
    AppCompatTextView voteStatus;
    @BindView(R2.id.vote_info)
    AppCompatTextView voteInfo;
    @BindView(R2.id.rv)
    RecyclerView rv;
    @BindView(R2.id.vote_result)
    AppCompatTextView voteResult;
    @BindView(R2.id.scroll)
    NestedScrollView scroll;
    @BindView(R2.id.ok_bt)
    AppCompatButton okBt;
    @BindView(R2.id.title)
    AppCompatTextView title;
    @BindView(R2.id.tv_vote_title)
    AppCompatTextView tvVoteTitle;

    private VoteAdapter adapter;
    private List<VoteEntity> list;
    private VoteEntity.ItemListBean entry;
    private String id;
    private int statu;
    private int uid;
    private WjdcEntity wjListBean;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_vote;
    }

    @Override
    protected void initBar() {
        ButterKnife.bind(this);
        setToolbarTitle(bar, getTitle());
        setSupportActionBar(bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        uid = actHelper.getSharedPreferences().getInt(Constants.UserId, 0);
        wjListBean = (WjdcEntity) getIntent().getSerializableExtra("Wjdc");
        id = wjListBean.getPollId();
        tvVoteTitle.setText("#"+wjListBean.getPollTitle()+"#");
        boolean isManager = getIntent().getBooleanExtra(Constants.Key_1, false);
        if (isManager) {
            statu = 1;
            okBt.setVisibility(View.GONE);
        } else {
            statu = 0;
//            if (wjListBean.getDtcount() > 0) {
//                statu = 1;
//            }
//            if (wjListBean.getEndday() < 0) {
//                statu = 1;
//                voteStatus.setText("已结束");
//            }
            if (statu == 0) {
                okBt.setVisibility(View.VISIBLE);
            } else {
                okBt.setVisibility(View.GONE);
            }
        }
        loadData();
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
        adapter = new VoteAdapter(statu);
        rv.setAdapter(adapter);
        voteInfo.setText(entry.getItemName());
        voteEnd.setText("结束时间:"+wjListBean.getPollEndtime());
    }

    @Override
    protected void addEvent() {
        bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void loadData() {
//        WebHelper.init().getWjdcInfo(id, new HttpCallBack<List<Vote>>() {
//            @Override
//            public void onSuccess(List<Vote> respone) {
//                entry = respone.get(0);
//                list = entry.getTgList();
//                adapter.setList(list, wjListBean.getDtcount());
//            }
//
//            @Override
//            public void onTodo(Respone respone) {
//
//            }
//
//            @Override
//            public void tokenErro(Respone respone) {
//
//            }
//
//            @Override
//            public void onFailed(Respone respone) {
//                MsgUtils.showLongToast(context, respone.getMessage());
//            }
//        });
    }

    @OnClick(R2.id.ok_bt)
    public void onViewClicked() {
    }

    private void submit(String wjdc) {
    }

}
