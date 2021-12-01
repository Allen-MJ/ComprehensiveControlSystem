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
import butterknife.OnClick;
import cn.lyj.core.R;
import cn.lyj.core.R2;
import cn.lyj.core.api.CoreApi;
import allen.frame.entry.CoreType;
import cn.lyj.core.entry.SocialPlaceEntity;
import cn.lyj.core.entry.UnSocialPlaceEntity;

public class UpdateUnsocialActivity extends AllenBaseActivity {


    @BindView(R2.id.title)
    AppCompatTextView title;
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.et_unsocial_00)
    AppCompatEditText etUnsocial00;
    @BindView(R2.id.et_unsocial_01)
    AppCompatEditText etUnsocial01;
    @BindView(R2.id.tv_unsocial_02)
    AppCompatTextView tvUnsocial02;
    @BindView(R2.id.tv_unsocial_03)
    AppCompatTextView tvUnsocial03;
    @BindView(R2.id.et_unsocial_04)
    AppCompatEditText etUnsocial04;
    @BindView(R2.id.et_unsocial_05)
    AppCompatEditText etUnsocial05;
    @BindView(R2.id.et_unsocial_06)
    AppCompatEditText etUnsocial06;
    @BindView(R2.id.tv_unsocial_07)
    AppCompatTextView tvUnsocial07;
    @BindView(R2.id.et_unsocial_08)
    AppCompatEditText etUnsocial08;
    @BindView(R2.id.et_unsocial_09)
    AppCompatEditText etUnsocial09;
    @BindView(R2.id.et_unsocial_10)
    AppCompatEditText etUnsocial10;
    @BindView(R2.id.et_unsocial_11)
    AppCompatEditText etUnsocial11;
    @BindView(R2.id.et_unsocial_12)
    AppCompatEditText etUnsocial12;
    @BindView(R2.id.tv_unsocial_13)
    AppCompatTextView tvUnsocial13;
    @BindView(R2.id.radio_14_y)
    AppCompatRadioButton radio14Y;
    @BindView(R2.id.radio_14_n)
    AppCompatRadioButton radio14N;
    @BindView(R2.id.tv_unsocial_15)
    AppCompatTextView tvUnsocial15;
    @BindView(R2.id.radio_16_y)
    AppCompatRadioButton radio16Y;
    @BindView(R2.id.radio_16_n)
    AppCompatRadioButton radio16N;
    @BindView(R2.id.radio_17_y)
    AppCompatRadioButton radio17Y;
    @BindView(R2.id.radio_17_n)
    AppCompatRadioButton radio17N;
    @BindView(R2.id.et_unsocial_18)
    AppCompatEditText etUnsocial18;
    @BindView(R2.id.radio_19_y)
    AppCompatRadioButton radio19Y;
    @BindView(R2.id.radio_19_n)
    AppCompatRadioButton radio19N;
    @BindView(R2.id.et_unsocial_20)
    AppCompatEditText etUnsocial20;
    @BindView(R2.id.radio_21_y)
    AppCompatRadioButton radio21Y;
    @BindView(R2.id.radio_21_n)
    AppCompatRadioButton radio21N;
    @BindView(R2.id.et_unsocial_22)
    AppCompatEditText etUnsocial22;
    @BindView(R2.id.radio_23_y)
    AppCompatRadioButton radio23Y;
    @BindView(R2.id.radio_23_n)
    AppCompatRadioButton radio23N;
    @BindView(R2.id.tv_unsocial_24)
    AppCompatTextView tvUnsocial24;
    @BindView(R2.id.et_unsocial_25)
    AppCompatEditText etUnsocial25;
    @BindView(R2.id.tv_unsocial_26)
    AppCompatTextView tvUnsocial26;
    @BindView(R2.id.tv_unsocial_27)
    AppCompatTextView tvUnsocial27;
    @BindView(R2.id.commit_bt)
    AppCompatButton commitBt;

    private static final String BUSINESS_TYPE="business_type";
    private static final String PAPERS_CODE="papers_code";
    private static final String SYSTEM_TRUE_OR_FALSE="system_trueorfalse";
    private static final String SAFETY_LOOPHOLE_TYPE="safety_loophole_type";
    private static final String CONCERN_EXTENT="concern_extent";
    private static final String ENTERPRISE_INDUSTRY="enterprise_industry";
    private UnSocialPlaceEntity entry;

    private List<CoreType> business_type;//企业类别
    private List<CoreType> papers_code;//法定代表人证件代码
    private List<CoreType> system_trueorfalse;//是否。。。
    private List<CoreType> safety_loophole_type;//安全隐患类型
    private List<CoreType> concern_extent;//关注程度
    private List<CoreType> enterprise_industry;//所属行业
    private int successCount=0;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.core_unsocial_update_layout;
    }

    @Override
    protected void initBar() {

        entry = (UnSocialPlaceEntity) getIntent().getSerializableExtra(Constants.ObjectFirst);
        setToolbarTitle(toolbar, entry == null ? "添加非公有经济组织" : "编辑非公有经济组织", true);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {

        loadAllType(BUSINESS_TYPE);
        loadAllType(PAPERS_CODE);
        loadAllType(SYSTEM_TRUE_OR_FALSE);
        loadAllType(SAFETY_LOOPHOLE_TYPE);
        loadAllType(CONCERN_EXTENT);
        loadAllType(ENTERPRISE_INDUSTRY);

    }
    private void initValue(){
            etUnsocial00.setText(entry.getB2301());
            etUnsocial01.setText(entry.getB2302());
            for (CoreType coreType:business_type
            ) {
                if (entry.getB2303().equals(coreType.getValue())){
                    tvUnsocial02.setText(coreType.getLabel());
                }
            }
            for (CoreType coreType:enterprise_industry
            ) {
                if (entry.getB2355().equals(coreType.getValue())){
                    tvUnsocial03.setText(coreType.getLabel());
                }
            }
            etUnsocial04.setText(entry.getB2304());
            etUnsocial05.setText(entry.getB2305());
            etUnsocial06.setText(entry.getB2306()+"");
            for (CoreType coreType:papers_code
            ) {
                if (entry.getB2307().equals(coreType.getValue())){
                    tvUnsocial07.setText(coreType.getLabel());
                }
            }
            etUnsocial08.setText(entry.getB2308());
            etUnsocial09.setText(entry.getB2309());
            etUnsocial10.setText(entry.getB2310());
            etUnsocial11.setText(entry.getB2311());
            etUnsocial12.setText(entry.getB2312());
            if (entry.getB2313().equals("1")){
                radio14Y.setChecked(true);
                radio14N.setChecked(false);
            }else {
                radio14Y.setChecked(false);
                radio14N.setChecked(true);
            }
            if (entry.getB2316().equals("1")){
                radio16Y.setChecked(true);
                radio16N.setChecked(false);
            }else {
                radio16Y.setChecked(false);
                radio16N.setChecked(true);
            }
            if (entry.getB2317().equals("1")){
                radio17Y.setChecked(true);
                radio17N.setChecked(false);
            }else {
                radio17Y.setChecked(false);
                radio17N.setChecked(true);
            }
            if (entry.getB2319().equals("1")){
                radio19Y.setChecked(true);
                radio19N.setChecked(false);
            }else {
                radio19Y.setChecked(false);
                radio19N.setChecked(true);
            }
            if (entry.getB2321().equals("1")){
                radio21Y.setChecked(true);
                radio21N.setChecked(false);
            }else {
                radio21Y.setChecked(false);
                radio21N.setChecked(true);
            }

            if (entry.getB2323().equals("1")){
                radio23Y.setChecked(true);
                radio23N.setChecked(false);
            }else {
                radio23Y.setChecked(false);
                radio23N.setChecked(true);
            }


            for (CoreType coreType:concern_extent
            ) {
                if (entry.getB2315().equals(coreType.getValue())){
                    tvUnsocial13.setText(coreType.getLabel());
                }
            }
            for (CoreType coreType:safety_loophole_type
            ) {
                if (entry.getB2314().equals(coreType.getValue())){
                    tvUnsocial15.setText(coreType.getLabel());
                }
            }
            etUnsocial18.setText(entry.getB2318()+"");
            etUnsocial20.setText(entry.getB2320()+"");
            etUnsocial22.setText(entry.getB2322()+"");

            tvUnsocial24.setText(entry.getB2395());
            etUnsocial25.setText(entry.getGridInfo().getGridName());

            tvUnsocial26.setText(entry.getB2390()+"");
            tvUnsocial27.setText(entry.getB2391()+"");


    }
    private void edit(String ids){
        Https.with(this).url(CoreApi._core_13)

                .put()
                .enqueue(new Callback<List<SocialPlaceEntity>>() {
                    @Override
                    public void success(List<SocialPlaceEntity> data) {
                        dismissProgressDialog();
                        MsgUtils.showMDMessage(context,"修改成功！");
                    }

                    @Override
                    public void token() {
                        MsgUtils.showShortToast(context,"账号登录过期,请重新登录!");
                    }

                    @Override
                    public void fail(Response response) {
                        dismissProgressDialog();
                        MsgUtils.showMDMessage(context,response.getMsg());
                    }
                });
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

    private void loadAllType(final String dictName){
        Https.with(this).url(CoreApi.core_Type).addParam("dictName",dictName).addParam("page",0).addParam("size",9999).get()
                .enqueue(new Callback<List<CoreType>>() {

                    @Override
                    public void success(final List<CoreType> data) {
                        switch (dictName){
                            case BUSINESS_TYPE:
                                business_type=data;
                                successCount+=1;
                                break;
                            case PAPERS_CODE:
                                papers_code=data;
                                successCount+=1;
                                break;
                            case SYSTEM_TRUE_OR_FALSE:
                                system_trueorfalse=data;
                                successCount+=1;
                                break;
                            case SAFETY_LOOPHOLE_TYPE:
                                safety_loophole_type=data;
                                successCount+=1;
                                break;
                            case CONCERN_EXTENT:
                                concern_extent=data;
                                successCount+=1;
                                break;
                             case ENTERPRISE_INDUSTRY:
                                 enterprise_industry=data;
                                 successCount+=1;
                                break;
                        }
                        if (successCount==6&&entry!=null){
                            initValue();
                        }
                    }

                    @Override
                    public void fail(Response response) {
                        MsgUtils.showMDMessage(context,response.getMsg());
                    }
                });
    }


    @OnClick({R2.id.tv_unsocial_02, R2.id.tv_unsocial_03, R2.id.tv_unsocial_07, R2.id.tv_unsocial_13, R2.id.tv_unsocial_15, R2.id.tv_unsocial_24, R2.id.commit_bt})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.tv_unsocial_02) {

        } else if (id == R.id.tv_unsocial_03) {
        } else if (id == R.id.tv_unsocial_07) {
        } else if (id == R.id.tv_unsocial_13) {
        } else if (id == R.id.tv_unsocial_15) {
        } else if (id == R.id.tv_unsocial_24) {
        } else if (id == R.id.commit_bt) {
        }
    }
}
