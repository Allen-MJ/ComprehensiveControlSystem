package cn.lyj.thepublic.main;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import allen.frame.AllenBaseActivity;
import allen.frame.tools.Constants;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.lyj.thepublic.R;

public class TipoffActivity extends AllenBaseActivity {
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.tip_dw)
    AppCompatTextView tipDw;
    @BindView(R2.id.tip_fyr)
    AppCompatEditText tipFyr;
    @BindView(R2.id.tip_phone)
    AppCompatEditText tipPhone;
    @BindView(R2.id.sex_male)
    RadioButton sexMale;
    @BindView(R2.id.sex_female)
    RadioButton sexFemale;
    @BindView(R2.id.tip_sex)
    RadioGroup tipSex;
    @BindView(R2.id.tip_grid)
    AppCompatTextView tipGrid;
    @BindView(R2.id.tip_address)
    AppCompatEditText tipAddress;
    @BindView(R2.id.tip_content)
    AppCompatEditText tipContent;
    @BindView(R2.id.tip_file)
    RecyclerView tipFile;
    @BindView(R2.id.tip_bt)
    AppCompatButton tipBt;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.public_tipoff_layout;
    }

    @Override
    protected void initBar() {
        setToolbarTitle(toolbar,"爆料",true);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        tipPhone.setText(shared.getString(Constants.UserPhone,""));
    }

    @Override
    protected void addEvent() {

    }

    @OnClick({R2.id.tip_dw, R2.id.tip_grid, R2.id.tip_bt})
    public void onViewClicked(View view) {
        view.setEnabled(false);
        int id = view.getId();
        if(id==R.id.tip_dw){

        }else if(id==R.id.tip_grid){

        }else if(id==R.id.tip_bt){

        }
        view.setEnabled(true);
    }
}
