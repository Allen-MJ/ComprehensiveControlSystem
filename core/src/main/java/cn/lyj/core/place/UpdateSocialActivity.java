package cn.lyj.core.place;

import android.os.Bundle;
import android.view.View;

import java.util.List;

import allen.frame.AllenBaseActivity;
import allen.frame.entry.Response;
import allen.frame.net.Callback;
import allen.frame.net.Https;
import allen.frame.tools.Constants;
import allen.frame.tools.MsgUtils;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.lyj.core.R;
import cn.lyj.core.R2;
import cn.lyj.core.api.CoreApi;
import allen.frame.entry.CoreType;
import cn.lyj.core.entry.SocialPlaceEntity;

public class UpdateSocialActivity extends AllenBaseActivity {

    @BindView(R2.id.title)
    AppCompatTextView title;
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.social_xydm)
    AppCompatEditText socialXydm;
    @BindView(R2.id.social_djzh)
    AppCompatEditText socialDjzh;
    @BindView(R2.id.social_name)
    AppCompatEditText socialName;
    @BindView(R2.id.social_qyhy)
    AppCompatTextView socialQyhy;
    @BindView(R2.id.social_clnd)
    AppCompatTextView socialClnd;
    @BindView(R2.id.social_djjgdm)
    AppCompatEditText socialDjjgdm;
    @BindView(R2.id.social_fddbr)
    AppCompatEditText socialFddbr;
    @BindView(R2.id.social_zs)
    AppCompatEditText socialZs;
    @BindView(R2.id.social_pzrq)
    AppCompatTextView socialPzrq;
    @BindView(R2.id.social_shzzlx)
    AppCompatTextView socialShzzlx;
    @BindView(R2.id.social_zt)
    AppCompatEditText socialZt;
    @BindView(R2.id.social_fzrzjdm)
    AppCompatTextView socialFzrzjdm;
    @BindView(R2.id.social_fzrzjhm)
    AppCompatEditText socialFzrzjhm;
    @BindView(R2.id.social_fzrxm)
    AppCompatEditText socialFzrxm;
    @BindView(R2.id.social_fzr_phone)
    AppCompatEditText socialFzrPhone;
    @BindView(R2.id.social_bgdz)
    AppCompatEditText socialBgdz;
    @BindView(R2.id.social_zbfzr_name)
    AppCompatEditText socialZbfzrName;
    @BindView(R2.id.social_fbfzr_phone)
    AppCompatEditText socialFbfzrPhone;
    @BindView(R2.id.social_gzcd)
    AppCompatTextView socialGzcd;
    @BindView(R2.id.radio_1)
    AppCompatRadioButton radio1;
    @BindView(R2.id.radio_2)
    AppCompatRadioButton radio2;
    @BindView(R2.id.radio_3)
    AppCompatRadioButton radio3;
    @BindView(R2.id.radio_4)
    AppCompatRadioButton radio4;
    @BindView(R2.id.social_zgdysl)
    AppCompatEditText socialZgdysl;
    @BindView(R2.id.radio_5)
    AppCompatRadioButton radio5;
    @BindView(R2.id.radio_6)
    AppCompatRadioButton radio6;
    @BindView(R2.id.social_ghhysl)
    AppCompatEditText socialGhhysl;
    @BindView(R2.id.radio_7)
    AppCompatRadioButton radio7;
    @BindView(R2.id.radio_8)
    AppCompatRadioButton radio8;
    @BindView(R2.id.social_gqtysl)
    AppCompatEditText socialGqtysl;
    @BindView(R2.id.radio_9)
    AppCompatRadioButton radio9;
    @BindView(R2.id.radio_10)
    AppCompatRadioButton radio10;
    @BindView(R2.id.radio_11)
    AppCompatRadioButton radio11;
    @BindView(R2.id.radio_12)
    AppCompatRadioButton radio12;
    @BindView(R2.id.social_zjly)
    AppCompatEditText socialZjly;
    @BindView(R2.id.social_sswg)
    AppCompatEditText socialSswg;
    @BindView(R2.id.social_jd)
    AppCompatTextView socialJd;
    @BindView(R2.id.social_wd)
    AppCompatTextView socialWd;
    @BindView(R2.id.commit_bt)
    AppCompatButton commitBt;
    private static final String SOCIAL_ORG_TYPE = "social_org_type";
    private static final String PAPERS_CODE = "papers_code";
    private static final String SYSTEM_TRUE_OR_FALSE = "system_trueorfalse";
    private static final String CONCERN_EXTENT = "concern_extent";
    private static final String ENTERPRISE_INDUSTRY = "enterprise_industry";
    @BindView(R2.id.social_fnsl)
    AppCompatEditText socialFnsl;

    private List<CoreType> social_org_type;//社会组织类型
    private List<CoreType> papers_code;//法定代表人证件代码
    private List<CoreType> system_trueorfalse;//是否。。。
    private List<CoreType> concern_extent;//关注程度
    private List<CoreType> enterprise_industry;//所属行业
    private int successCount = 0;
    private SocialPlaceEntity entry;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.core_social_update_layout;
    }

    @Override
    protected void initBar() {
        entry = (SocialPlaceEntity) getIntent().getSerializableExtra(Constants.ObjectFirst);
        setToolbarTitle(toolbar, entry == null ? "添加社会组织" : "编辑社会组织", true);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        loadAllType(SOCIAL_ORG_TYPE);
        loadAllType(PAPERS_CODE);
        loadAllType(SYSTEM_TRUE_OR_FALSE);
        loadAllType(CONCERN_EXTENT);
        loadAllType(ENTERPRISE_INDUSTRY);


    }

    private void initValue() {
        socialXydm.setText(entry.getB2401());
        socialDjzh.setText(entry.getB2402());
        socialName.setText(entry.getB2403());
        for (CoreType coreType : enterprise_industry
        ) {
            if ((entry.getB2455()==null?"":entry.getB2455()).equals(coreType.getValue())) {
                socialQyhy.setText(coreType.getLabel());
            }
        }
        socialClnd.setText(entry.getB2495());
        socialDjjgdm.setText(entry.getB2404());
        socialFddbr.setText(entry.getB2405());
        socialZs.setText(entry.getB2406());
        socialPzrq.setText(entry.getB2407());
        for (CoreType coreType : social_org_type
        ) {
            if (entry.getB2408().equals(coreType.getValue())) {
                socialShzzlx.setText(coreType.getLabel());
            }
        }
        socialZt.setText(entry.getB2409());
        for (CoreType coreType : papers_code
        ) {
            if (entry.getB2410().equals(coreType.getValue())) {
                socialFzrzjdm.setText(coreType.getLabel());
            }
        }
        socialFzrzjhm.setText(entry.getB2411());
        socialFzrxm.setText(entry.getB2412());
        socialFzrPhone.setText(entry.getB2413());
        socialBgdz.setText(entry.getB2414());
        socialZbfzrName.setText(entry.getB2415());
        socialFbfzrPhone.setText(entry.getB2416());
        for (CoreType coreType : concern_extent
        ) {
            if (entry.getB2417().equals(coreType.getValue())) {
                socialGzcd.setText(coreType.getLabel());
            }
        }
        if (entry.getB2418().equals("1")) {
            radio1.setChecked(true);
            radio2.setChecked(false);
        } else {
            radio1.setChecked(false);
            radio2.setChecked(true);
        }
        if (entry.getB2419().equals("1")) {
            radio3.setChecked(true);
            radio4.setChecked(false);
        } else {
            radio3.setChecked(false);
            radio4.setChecked(true);
        }
        if (entry.getB2421().equals("1")) {
            radio5.setChecked(true);
            radio6.setChecked(false);
        } else {
            radio5.setChecked(false);
            radio6.setChecked(true);
        }
        if (entry.getB2423().equals("1")) {
            radio7.setChecked(true);
            radio8.setChecked(false);
        } else {
            radio7.setChecked(false);
            radio8.setChecked(true);
        }
        if (entry.getB2425().equals("1")) {
            radio9.setChecked(true);
            radio10.setChecked(false);
        } else {
            radio9.setChecked(false);
            radio10.setChecked(true);
        }
        if (entry.getB2428().equals("1")) {
            radio11.setChecked(true);
            radio12.setChecked(false);
        } else {
            radio11.setChecked(false);
            radio12.setChecked(true);
        }
        socialZgdysl.setText(entry.getB2420() + "");
        socialGhhysl.setText(entry.getB2422() + "");
        socialGqtysl.setText(entry.getB2424() + "");
        socialFnsl.setText(entry.getB2426() + "");
        socialZjly.setText(entry.getB2427());
        socialJd.setText(entry.getB2490()+"");
        socialWd.setText(entry.getB2491()+"");
        socialSswg.setText(entry.getGridInfo().getGridName());


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


    private void loadAllType(final String dictName) {
        Https.with(this).url(CoreApi.core_Type).addParam("dictName", dictName).addParam("page", 0).addParam("size", 9999).get()
                .enqueue(new Callback<List<CoreType>>() {

                    @Override
                    public void success(final List<CoreType> data) {
                        switch (dictName) {
                            case SOCIAL_ORG_TYPE:
                                social_org_type = data;
                                successCount += 1;
                                break;
                            case PAPERS_CODE:
                                papers_code = data;
                                successCount += 1;
                                break;
                            case SYSTEM_TRUE_OR_FALSE:
                                system_trueorfalse = data;
                                successCount += 1;
                                break;
                            case CONCERN_EXTENT:
                                concern_extent = data;
                                successCount += 1;
                                break;
                            case ENTERPRISE_INDUSTRY:
                                enterprise_industry = data;
                                successCount += 1;
                                break;
                        }
                        if (successCount == 5 && entry != null) {
                            initValue();
                        }
                    }

                    @Override
                    public void fail(Response response) {
                        MsgUtils.showMDMessage(context, response.getMsg());
                    }
                });
    }

    @OnClick({R2.id.social_qyhy, R2.id.social_clnd, R2.id.social_pzrq, R2.id.social_shzzlx, R2.id.social_gzcd, R2.id.commit_bt})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.social_qyhy) {
        } else if (id == R.id.social_clnd) {
        } else if (id == R.id.social_pzrq) {
        } else if (id == R.id.social_shzzlx) {
        } else if (id == R.id.social_gzcd) {
        } else if (id == R.id.commit_bt) {
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
