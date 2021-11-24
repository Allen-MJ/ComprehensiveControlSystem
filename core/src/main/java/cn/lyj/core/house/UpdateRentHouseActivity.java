package cn.lyj.core.house;

import android.os.Bundle;
import android.view.View;

import allen.frame.AllenBaseActivity;
import allen.frame.tools.Constants;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.lyj.core.R;
import cn.lyj.core.R2;
import cn.lyj.core.entry.RentHouse;

public class UpdateRentHouseActivity extends AllenBaseActivity {
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.house_wg)
    AppCompatTextView houseWg;
    @BindView(R2.id.house_no)
    AppCompatEditText houseNo;
    @BindView(R2.id.house_dw)
    AppCompatTextView houseDw;
    @BindView(R2.id.house_xq)
    AppCompatTextView houseXq;
    @BindView(R2.id.house_ld)
    AppCompatTextView houseLd;
    @BindView(R2.id.house_dy)
    AppCompatEditText houseDy;
    @BindView(R2.id.house_lc)
    AppCompatEditText houseLc;
    @BindView(R2.id.house_fh)
    AppCompatEditText houseFh;
    @BindView(R2.id.house_fwyt)
    AppCompatTextView houseFwyt;
    @BindView(R2.id.house_address)
    AppCompatEditText houseAddress;
    @BindView(R2.id.house_zj)
    AppCompatEditText houseZj;
    @BindView(R2.id.house_remark)
    AppCompatEditText houseRemark;
    @BindView(R2.id.commit_bt)
    AppCompatButton commitBt;
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
        setToolbarTitle(toolbar, (entry==null?"添加":"编辑")+"出租房", true);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {

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

    @OnClick({R2.id.house_wg, R2.id.house_dw, R2.id.house_xq, R2.id.house_ld, R2.id.house_fwyt, R2.id.commit_bt})
    public void onViewClicked(View view) {
        int id = view.getId();
        if(id==R.id.house_wg){

        }else if(id==R.id.house_dw){

        }else if(id==R.id.house_xq){

        }else if(id==R.id.house_ld){

        }else if(id==R.id.house_fwyt){

        }else if(id==R.id.commit_bt){

        }
    }
}
