package cn.lyj.ccs;

import android.os.Bundle;
import android.view.View;

import allen.frame.AllenIMBaseActivity;
import allen.frame.widget.BGABanner;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GuildActivity extends AllenIMBaseActivity {
    @BindView(R.id.guild)
    BGABanner guild;
    @BindView(R.id.tv_guide_skip)
    AppCompatTextView tvGuideSkip;
    @BindView(R.id.btn_guide_enter)
    AppCompatButton btnGuideEnter;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.guild_layout;
    }

    @Override
    protected void initBar() {

    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void addEvent() {

    }

    @OnClick({R.id.tv_guide_skip, R.id.btn_guide_enter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_guide_skip:
                break;
            case R.id.btn_guide_enter:
                break;
        }
    }
}
