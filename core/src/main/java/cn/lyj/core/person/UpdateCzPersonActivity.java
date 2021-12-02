package cn.lyj.core.person;

import android.os.Bundle;
import android.view.View;

import allen.frame.AllenBaseActivity;
import allen.frame.tools.Constants;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import cn.lyj.core.R;
import cn.lyj.core.R2;
import cn.lyj.core.entry.CzPersonEntity;

public class UpdateCzPersonActivity extends AllenBaseActivity {
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.house_person_wg)
    AppCompatTextView housePersonWg;
    @BindView(R2.id.house_person_name)
    AppCompatTextView housePersonName;
    @BindView(R2.id.house_person_idnumber)
    AppCompatTextView housePersonIdnumber;
    @BindView(R2.id.house_person_birthday)
    AppCompatTextView housePersonBirthday;
    private CzPersonEntity entity;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.core_update_czperson_layout;
    }

    @Override
    protected void initBar() {
        entity = (CzPersonEntity) getIntent().getSerializableExtra(Constants.ObjectFirst);
        setToolbarTitle(toolbar,"常住人口",true);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        if(entity!=null){
            housePersonWg.setText(entity.getGidObj().getGenericName());
            housePersonName.setText(entity.getBcz02());
            housePersonIdnumber.setText(entity.getBcz01());
            housePersonBirthday.setText(entity.getBcz03());
        }
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
