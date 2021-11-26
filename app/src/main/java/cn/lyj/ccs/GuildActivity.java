package cn.lyj.ccs;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import allen.frame.AllenIMBaseActivity;
import allen.frame.entry.BGALocalImageSize;
import allen.frame.widget.BGABanner;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import butterknife.BindView;

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
        processLogic();
    }

    @Override
    protected void addEvent() {
        guild.setEnterSkipViewIdAndDelegate(R.id.btn_guide_enter, R.id.tv_guide_skip, new BGABanner.GuideDelegate() {
            @Override
            public void onClickEnterOrSkip() {
                startActivity(new Intent(context, TestActivity.class));
//                startActivity(new Intent(context, LoginActivity.class));
                finish();
            }
        });
    }

    private void processLogic() {
        BGALocalImageSize localImageSize = new BGALocalImageSize(720, 1280, 320, 640);
        guild.setData(localImageSize, ImageView.ScaleType.CENTER_CROP,
                R.mipmap.guild_1);
    }
}
