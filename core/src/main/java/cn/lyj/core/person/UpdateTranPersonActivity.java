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
import cn.lyj.core.entry.TranPerson;

public class UpdateTranPersonActivity extends AllenBaseActivity {
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
    @BindView(R2.id.house_person_lryy)
    AppCompatTextView housePersonLryy;
    @BindView(R2.id.house_person_bzlx)
    AppCompatTextView housePersonBzlx;
    @BindView(R2.id.house_person_zjhm)
    AppCompatEditText housePersonZjhm;
    @BindView(R2.id.house_person_djrq)
    AppCompatTextView housePersonDjrq;
    @BindView(R2.id.house_person_zjdq)
    AppCompatTextView housePersonZjdq;
    @BindView(R2.id.house_person_sfcz)
    AppCompatTextView housePersonSfcz;
    @BindView(R2.id.house_person_sfzd)
    AppCompatTextView housePersonSfzd;
    @BindView(R2.id.house_person_zslx)
    AppCompatTextView housePersonZslx;
    @BindView(R2.id.commit_bt)
    AppCompatButton commitBt;
    private TranPerson entry;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.core_tranperson_update_layout;
    }

    @Override
    protected void initBar() {
        entry = (TranPerson) getIntent().getSerializableExtra(Constants.ObjectFirst);
        setToolbarTitle(toolbar, entry == null ? "添加流动人口" : "编辑流动人口", true);
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

    @OnClick({R2.id.house_person_wg, R2.id.house_person_sex, R2.id.house_person_mz, R2.id.house_person_hyzk, R2.id.house_person_zzmm, R2.id.house_person_xl,
            R2.id.house_person_zjxy, R2.id.house_person_zylb, R2.id.house_person_lryy, R2.id.house_person_bzlx, R2.id.house_person_djrq, R2.id.house_person_zjdq,
            R2.id.house_person_sfcz, R2.id.house_person_sfzd, R2.id.house_person_zslx, R2.id.commit_bt})
    public void onViewClicked(View view) {
        view.setEnabled(false);
        int id = view.getId();
        if(id==R.id.house_person_wg){

        }else if(id==R.id.house_person_sex){

        }else if(id==R.id.house_person_jg){

        }else if(id==R.id.house_person_mz){

        }else if(id==R.id.house_person_hyzk){

        }else if(id==R.id.house_person_zzmm){

        }else if(id==R.id.house_person_xl){

        }else if(id==R.id.house_person_zjxy){

        }else if(id==R.id.house_person_zylb){

        }else if(id==R.id.house_person_lryy){

        }else if(id==R.id.house_person_bzlx){

        }else if(id==R.id.house_person_djrq){

        }else if(id==R.id.house_person_zjdq){

        }else if(id==R.id.house_person_sfcz){

        }else if(id==R.id.house_person_sfzd){

        }else if(id==R.id.house_person_zslx){

        }else if(id==R.id.commit_bt){

        }
        view.setEnabled(true);
    }
}
