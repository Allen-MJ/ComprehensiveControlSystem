package cn.lyj.user.notice;

import android.os.Bundle;
import android.text.Html;
import android.view.View;

import allen.frame.AllenBaseActivity;
import allen.frame.HtmlImageUtil.GlideImageGetter;
import allen.frame.tools.Constants;
import allen.frame.tools.DateUtils;
import allen.frame.tools.StringUtils;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import cn.lyj.user.R;
import cn.lyj.user.R2;
import cn.lyj.user.entry.SystemNotice;

public class SystemNoticeInfoActiivty extends AllenBaseActivity {
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.notice_title)
    AppCompatTextView noticeTitle;
    @BindView(R2.id.notice_type)
    AppCompatTextView noticeType;
    @BindView(R2.id.notice_date)
    AppCompatTextView noticeDate;
    @BindView(R2.id.notice_sub)
    AppCompatTextView noticeSub;
    @BindView(R2.id.notice_content)
    AppCompatTextView noticeContent;
    private SystemNotice entry;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.ub_notice_info_layout;
    }

    @Override
    protected void initBar() {
        entry = (SystemNotice) getIntent().getSerializableExtra(Constants.ObjectFirst);
        setToolbarTitle(toolbar,"详情",true);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        noticeTitle.setText(entry.getNoticeTitle());
        noticeType.setText(entry.getNoticeObj());
        noticeDate.setText(DateUtils.getTimeFormatText(entry.getCreateTime()));
        noticeSub.setText(entry.getNoticeSubtitle());
        noticeContent.setText(Html.fromHtml(StringUtils.null2Empty(entry.getTextContent()),
                new GlideImageGetter(context, noticeContent), null));
    }

    @Override
    protected void addEvent() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
