package cn.lyj.thepublic.square;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import allen.frame.AllenBaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.lyj.thepublic.R;
import cn.lyj.thepublic.R2;
import cn.lyj.thepublic.adapter.DiscussAdapter;

public class NewsDetailActivity extends AllenBaseActivity {

    @BindView(R2.id.title)
    AppCompatTextView title;
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.detail_title)
    AppCompatTextView detailTitle;
    @BindView(R2.id.detail_date)
    AppCompatTextView detailDate;
    @BindView(R2.id.detail_sourse)
    AppCompatTextView detailSourse;
    @BindView(R2.id.detail_con)
    AppCompatTextView detailCon;
    @BindView(R2.id.rv_discuss)
    RecyclerView rvDiscuss;
    @BindView(R2.id.tv_gz)
    AppCompatTextView tvGz;
    @BindView(R2.id.tv_discuss)
    AppCompatTextView tvDiscuss;
    @BindView(R2.id.tv_zan)
    AppCompatTextView tvZan;
    private String id;
    private DiscussAdapter discussAdapter;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected void initBar() {
        ButterKnife.bind(this);
        String title = getIntent().getStringExtra("title");
        setToolbarTitle(toolbar, title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        id = getIntent().getStringExtra("id");
        loadData();
    }

    private void loadData() {
//        WebHelper.init().getMessageDetails(id, new HttpCallBack<MessageEntity>() {
//            @Override
//            public void onSuccess(MessageEntity respone) {
//                detailTitle.setText(respone.getTitle());
//                detailDate.setText(StringUtils.empty(respone.getPublishTime())?respone.getPublishTime():respone.getPublishTime().substring(0,10));
//                detailSourse.setText("来源:"+respone.getOrgName());
//                detailCon.setText(Html.fromHtml(StringUtils.null2Empty(respone.getContent()),
//                        new GlideImageGetter(context, detailCon), null));
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
//
//            }
//        });
    }

    @Override
    protected void addEvent() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    @OnClick({R2.id.tv_gz, R2.id.tv_discuss, R2.id.tv_zan})
    public void onViewClicked(View view) {
        int viewId = view.getId();
        if (viewId == R.id.tv_gz) {
        } else if (viewId == R.id.tv_discuss) {
        } else if (viewId == R.id.tv_zan) {
        }
    }
}
