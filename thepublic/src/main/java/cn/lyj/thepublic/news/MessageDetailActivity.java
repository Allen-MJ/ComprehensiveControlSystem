package cn.lyj.thepublic.news;

import android.os.Bundle;
import android.text.Html;
import android.view.View;

import allen.frame.AllenBaseActivity;
import allen.frame.HtmlImageUtil.GlideImageGetter;
import allen.frame.entry.Response;
import allen.frame.net.Callback;
import allen.frame.net.Https;
import allen.frame.tools.StringUtils;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.OnClick;
import cn.lyj.thepublic.R;
import cn.lyj.thepublic.R2;
import cn.lyj.thepublic.data.API;
import cn.lyj.thepublic.entry.Notice;

public class MessageDetailActivity extends AllenBaseActivity {

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
    private String id;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_message_detail;
    }

    @Override
    protected void initBar() {
        setToolbarTitle(toolbar,"消息详情",true);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        id = getIntent().getStringExtra("id");
        loadData();
    }

    private void loadData() {
        Https.with(this).url(API._getNoticeInfo).addParam("noticeId",id).get().enqueue(new Callback<Notice>() {
            @Override
            public void success(Notice data) {
                detailTitle.setText(data.getNoticeTitle());
                detailDate.setText(StringUtils.empty(data.getUpdateTime())?data.getUpdateTime():data.getUpdateTime().substring(0,10));
                detailSourse.setText("来源:"+data.getCreateBy());
                detailCon.setText(Html.fromHtml(StringUtils.null2Empty(data.getNoticeContent()),
                        new GlideImageGetter(context, detailCon), null));
            }

            @Override
            public void fail(Response response) {

            }
        });
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
