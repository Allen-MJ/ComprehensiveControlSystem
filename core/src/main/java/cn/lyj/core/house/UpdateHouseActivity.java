package cn.lyj.core.house;

import android.os.Bundle;

import allen.frame.AllenBaseActivity;
import allen.frame.tools.Constants;
import androidx.annotation.Nullable;
import cn.lyj.core.R;
import cn.lyj.core.entry.House;

public class UpdateHouseActivity extends AllenBaseActivity {
    private House entry;
    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.core_house_update_layout;
    }

    @Override
    protected void initBar() {
        entry = (House) getIntent().getSerializableExtra(Constants.ObjectFirst);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void addEvent() {

    }
}
