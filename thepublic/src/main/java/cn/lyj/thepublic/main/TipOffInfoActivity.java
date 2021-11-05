package cn.lyj.thepublic.main;

import android.os.Bundle;

import allen.frame.AllenBaseActivity;
import allen.frame.adapter.AllenFileChoiceAdapter;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import cn.lyj.thepublic.R;
import cn.lyj.thepublic.R2;

public class TipOffInfoActivity extends AllenBaseActivity {
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.tip_info_dw)
    AppCompatTextView tipInfoDw;
    @BindView(R2.id.tip_info_grid)
    AppCompatTextView tipInfoGrid;
    @BindView(R2.id.tip_info_name)
    AppCompatTextView tipInfoName;
    @BindView(R2.id.tip_info_sex)
    AppCompatTextView tipInfoSex;
    @BindView(R2.id.tip_info_phone)
    AppCompatTextView tipInfoPhone;
    @BindView(R2.id.tip_info_idno)
    AppCompatTextView tipInfoIdno;
    @BindView(R2.id.tip_info_address)
    AppCompatTextView tipInfoAddress;
    @BindView(R2.id.tip_info_content)
    AppCompatTextView tipInfoContent;
    @BindView(R2.id.tip_info_file)
    RecyclerView tipInfoFile;
    private AllenFileChoiceAdapter fileChoiceAdapter;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.public_tipoff_info;
    }

    @Override
    protected void initBar() {

    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        GridLayoutManager manager = new GridLayoutManager(context,4);
        tipInfoFile.setLayoutManager(manager);
        fileChoiceAdapter = new AllenFileChoiceAdapter(true);
        tipInfoFile.setAdapter(fileChoiceAdapter);
    }

    @Override
    protected void addEvent() {
    }

}
