package cn.lyj.core.person;

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
import butterknife.OnClick;
import cn.lyj.core.R;
import cn.lyj.core.R2;
import cn.lyj.core.entry.XmsfPersonEntity;

public class UpdateXmsfPersonActivity extends AllenBaseActivity {
    @BindView(R2.id.title)
    AppCompatTextView title;
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.house_person_wg)
    AppCompatTextView housePersonWg;
    @BindView(R2.id.house_person_name)
    AppCompatEditText housePersonName;
    @BindView(R2.id.house_person_aname)
    AppCompatEditText housePersonAname;
    @BindView(R2.id.house_person_idnumber)
    AppCompatEditText housePersonIdnumber;
    @BindView(R2.id.house_person_sex)
    AppCompatTextView housePersonSex;
    @BindView(R2.id.house_person_birthday)
    AppCompatEditText housePersonBirthday;
    @BindView(R2.id.house_person_jg)
    AppCompatEditText housePersonJg;
    @BindView(R2.id.house_person_mz)
    AppCompatTextView housePersonMz;
    @BindView(R2.id.house_person_hyzk)
    AppCompatTextView housePersonHyzk;
    @BindView(R2.id.house_person_zzmm)
    AppCompatTextView housePersonZzmm;
    @BindView(R2.id.house_person_xl)
    AppCompatTextView housePersonXl;
    @BindView(R2.id.house_person_zjxy)
    AppCompatTextView housePersonZjxy;
    @BindView(R2.id.house_person_zylb)
    AppCompatTextView housePersonZylb;
    @BindView(R2.id.house_person_zy)
    AppCompatEditText housePersonZy;
    @BindView(R2.id.house_person_fwcs)
    AppCompatEditText housePersonFwcs;
    @BindView(R2.id.house_person_phone)
    AppCompatEditText housePersonPhone;
    @BindView(R2.id.house_person_hjd)
    AppCompatEditText housePersonHjd;
    @BindView(R2.id.house_person_xzd)
    AppCompatEditText housePersonXzd;
    @BindView(R2.id.house_person_xzddz)
    AppCompatEditText housePersonXzddz;
    @BindView(R2.id.house_person_hjddz)
    AppCompatEditText housePersonHjddz;
    @BindView(R2.id.house_person_zpdz)
    AppCompatTextView housePersonZpdz;
    @BindView(R2.id.xmsf_person_sflf)
    AppCompatTextView xmsfPersonSflf;
    @BindView(R2.id.xmsf_person_yzm)
    AppCompatTextView xmsfPersonYzm;
    @BindView(R2.id.xmsf_person_ypxq)
    AppCompatEditText xmsfPersonYpxq;
    @BindView(R2.id.xmsf_person_fxcs)
    AppCompatEditText xmsfPersonFxcs;
    @BindView(R2.id.xmsf_person_sfrq)
    AppCompatTextView xmsfPersonSfrq;
    @BindView(R2.id.xmsf_person_wxxpg)
    AppCompatTextView xmsfPersonWxxpg;
    @BindView(R2.id.xmsf_person_xjrq)
    AppCompatTextView xmsfPersonXjrq;
    @BindView(R2.id.xmsf_person_xjqk)
    AppCompatEditText xmsfPersonXjqk;
    @BindView(R2.id.xmsf_person_azrq)
    AppCompatTextView xmsfPersonAzrq;
    @BindView(R2.id.xmsf_person_azqk)
    AppCompatEditText xmsfPersonAzqk;
    @BindView(R2.id.xmsf_person_wazyy)
    AppCompatEditText xmsfPersonWazyy;
    @BindView(R2.id.xmsf_person_bjqk)
    AppCompatEditText xmsfPersonBjqk;
    @BindView(R2.id.xmsf_person_sfcxfz)
    AppCompatTextView xmsfPersonSfcxfz;
    @BindView(R2.id.xmsf_person_cxfzzm)
    AppCompatEditText xmsfPersonCxfzzm;
    @BindView(R2.id.commit_bt)
    AppCompatButton commitBt;
    private XmsfPersonEntity entry;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.core_xmsf_person_update_layout;
    }

    @Override
    protected void initBar() {
        entry = (XmsfPersonEntity) getIntent().getSerializableExtra(Constants.ObjectFirst);
        setToolbarTitle(toolbar, entry == null ? "添加刑满释放人员" : "编辑刑满释放人员", true);
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



    @OnClick({R2.id.house_person_wg, R2.id.house_person_sex, R2.id.house_person_mz, R2.id.house_person_hyzk, R2.id.house_person_zzmm, R2.id.house_person_xl, R2.id.house_person_zjxy, R2.id.house_person_zylb, R2.id.house_person_zpdz, R2.id.xmsf_person_sflf, R2.id.xmsf_person_yzm, R2.id.xmsf_person_sfrq, R2.id.xmsf_person_wxxpg, R2.id.xmsf_person_xjrq, R2.id.xmsf_person_azrq, R2.id.xmsf_person_sfcxfz, R2.id.commit_bt})
    public void onViewClicked(View view) {
        view.setEnabled(false);
        int id = view.getId();
        if (id == R.id.house_person_wg) {
        } else if (id == R.id.house_person_sex) {
        } else if (id == R.id.house_person_mz) {
        } else if (id == R.id.house_person_hyzk) {
        } else if (id == R.id.house_person_zzmm) {
        } else if (id == R.id.house_person_xl) {
        } else if (id == R.id.house_person_zjxy) {
        } else if (id == R.id.house_person_zylb) {
        } else if (id == R.id.house_person_zpdz) {
        } else if (id == R.id.xmsf_person_sflf) {
        } else if (id == R.id.xmsf_person_yzm) {
        } else if (id == R.id.xmsf_person_sfrq) {
        } else if (id == R.id.xmsf_person_wxxpg) {
        } else if (id == R.id.xmsf_person_xjrq) {
        } else if (id == R.id.xmsf_person_azrq) {
        } else if (id == R.id.xmsf_person_sfcxfz) {
        } else if (id == R.id.commit_bt) {
        }
        view.setEnabled(true);
    }
}
