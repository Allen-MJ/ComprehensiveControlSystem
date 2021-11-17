package cn.lyj.core.house;

import android.os.Bundle;

import allen.frame.AllenBaseActivity;
import allen.frame.tools.Constants;
import androidx.annotation.Nullable;
import cn.lyj.core.R;
import cn.lyj.core.entry.RentHouse;

public class UpdateRentHouseActivity extends AllenBaseActivity {
    private RentHouse entry;
    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.core_renthouse_update_layout;
    }

    @Override
    protected void initBar() {
        entry = (RentHouse) getIntent().getSerializableExtra(Constants.ObjectFirst);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void addEvent() {

    }
}
