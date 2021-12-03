package cn.lyj.core.person;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.List;

import allen.frame.AllenBaseActivity;
import allen.frame.AllenChoiceGridActivity;
import allen.frame.adapter.CommonAdapter;
import allen.frame.adapter.CoreTypeAdapter;
import allen.frame.adapter.ViewHolder;
import allen.frame.entry.CoreType;
import allen.frame.entry.Response;
import allen.frame.net.BaseApi;
import allen.frame.net.Callback;
import allen.frame.net.Https;
import allen.frame.tools.CommonTypeDialog;
import allen.frame.tools.Constants;
import allen.frame.tools.DatePickerDialog;
import allen.frame.tools.MsgUtils;
import allen.frame.tools.StringUtils;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.OnClick;
import cn.lyj.core.R;
import cn.lyj.core.R2;
import cn.lyj.core.api.CoreApi;
import cn.lyj.core.entry.XjPersonEntity;

public class UpdateXjPersonActivity extends AllenBaseActivity {
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
    AppCompatTextView housePersonBirthday;
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
    AppCompatTextView housePersonHjd;
    @BindView(R2.id.house_person_xzd)
    AppCompatTextView housePersonXzd;
    @BindView(R2.id.house_person_xzddz)
    AppCompatEditText housePersonXzddz;
    @BindView(R2.id.house_person_hjddz)
    AppCompatEditText housePersonHjddz;
    @BindView(R2.id.house_person_zpdz)
    AppCompatTextView housePersonZpdz;
    @BindView(R2.id.commit_bt)
    AppCompatButton commitBt;
    private XjPersonEntity entry;
    private String pid,name,uname,sex,birthday,nation,nativeplace,marriage,zzmm,edu,
            zongj,worktype,work,fwcs,link,hjd,hjddetail,xzd,xzddetail,gid;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.core_xj_person_update_layout;
    }

    @Override
    protected void initBar() {
        entry = (XjPersonEntity) getIntent().getSerializableExtra(Constants.ObjectFirst);
        setToolbarTitle(toolbar, entry == null ? "添加邪教人员" : "编辑邪教人员", true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case 10:
                    gid = data.getStringExtra(Constants.Key_1);
                    housePersonWg.setText(data.getStringExtra(Constants.Key_2));
                    break;
            }
        }
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        if(entry!=null){
            gid = entry.getGid();
            housePersonWg.setText(entry.getGidObj().getOrgFullName());
            housePersonName.setText(entry.getName());
            housePersonAname.setText(entry.getUname());
            housePersonIdnumber.setText(entry.getPid());
            housePersonSex.setText(entry.getSexName());
            sex = entry.getSex();
            housePersonBirthday.setText(entry.getBirthday());
            housePersonJg.setText(entry.getNativeplace());
            housePersonMz.setText(entry.getNation());
            nation = entry.getNation();
            housePersonHyzk.setText(entry.getMarriage());
            marriage = entry.getMarriage();
            housePersonZzmm.setText(entry.getZzmmObj().getCodeName());
            zzmm = entry.getZzmm();
            housePersonXl.setText(entry.getEduObj().getCodeName());
            edu = entry.getEdu();
            housePersonZjxy.setText(entry.getZongj());
            zongj = entry.getZongj();
            housePersonZylb.setText(entry.getWorktype());
            worktype = entry.getWorktype();
            housePersonZy.setText(entry.getWork());
            housePersonFwcs.setText(entry.getFwcs());
            housePersonPhone.setText(entry.getLink());
            housePersonHjd.setText(entry.getHjdObj().getCodeName());
            hjd = entry.getHjd();
            housePersonXzd.setText(entry.getXzdObj().getCodeName());
            xzd = entry.getXzd();
            housePersonXzddz.setText(entry.getXzddetail());
            housePersonHjddz.setText(entry.getHjddetail());
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

    @OnClick({R2.id.house_person_wg, R2.id.house_person_sex, R2.id.house_person_birthday, R2.id.house_person_mz, R2.id.house_person_hyzk, R2.id.house_person_zzmm,
            R2.id.house_person_xl, R2.id.house_person_zjxy, R2.id.house_person_zylb, R2.id.house_person_zpdz, R2.id.house_person_hjd,
            R2.id.house_person_xzd, R2.id.commit_bt})
    public void onViewClicked(View view) {
        view.setEnabled(false);
        int id = view.getId();
        if(id== R.id.house_person_wg){
            startActivityForResult(new Intent(context, AllenChoiceGridActivity.class),10);
        }else if(id== R.id.house_person_sex){
            sex();
        }else if(id==R.id.house_person_birthday){
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear, int startDayOfMonth) {
                    housePersonBirthday.setText(startYear + "-" + String.format("%02d", startMonthOfYear + 1) + "-" + String.format("%02d", startDayOfMonth));
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        }else if(id== R.id.house_person_mz){
            nation();
        }else if(id== R.id.house_person_hyzk){
            hyzk();
        }else if(id== R.id.house_person_zzmm){
            zzmm();
        }else if(id== R.id.house_person_xl){
            xl();
        }else if(id== R.id.house_person_zjxy){
            zjxy();
        }else if(id== R.id.house_person_zylb){
            zylb();
        }else if(id== R.id.house_person_zpdz){

        }else if(id==R.id.house_person_hjd){
            hjd();
        }else if(id==R.id.house_person_xzd){
            xzd();
        }else if(id== R.id.commit_bt){
            commit();
        }
        view.setEnabled(true);
    }

    private void commit(){
        if(StringUtils.empty(gid)){
            MsgUtils.showMDMessage(context,"请选择所属网格!");
            return;
        }
        pid = housePersonIdnumber.getText().toString().trim();
        if(StringUtils.empty(pid)){
            MsgUtils.showMDMessage(context,"请输入身份证号!");
            return;
        }
        name = housePersonName.getText().toString().trim();
        if(StringUtils.empty(name)){
            MsgUtils.showMDMessage(context,"请输入姓名!");
            return;
        }
        uname = housePersonAname.getText().toString().trim();
        if(StringUtils.empty(sex)){
            MsgUtils.showMDMessage(context,"请选择性别!");
            return;
        }
        birthday = housePersonBirthday.getText().toString().trim();
        if(StringUtils.empty(birthday)){
            MsgUtils.showMDMessage(context,"请输入出生日期!");
            return;
        }
        nation = housePersonMz.getText().toString().trim();
        if(StringUtils.empty(nation)){
            MsgUtils.showMDMessage(context,"请选择民族!");
            return;
        }
        nativeplace = housePersonJg.getText().toString().trim();
        if(StringUtils.empty(marriage)){
            MsgUtils.showMDMessage(context,"请选择婚姻状况!");
            return;
        }
        if(StringUtils.empty(zzmm)){
            MsgUtils.showMDMessage(context,"请选择政治面貌!");
            return;
        }
        if(StringUtils.empty(edu)){
            MsgUtils.showMDMessage(context,"请选择学历!");
            return;
        }
        if(StringUtils.empty(zongj)){
            MsgUtils.showMDMessage(context,"请选择宗教信仰!");
            return;
        }
        if(StringUtils.empty(worktype)){
            MsgUtils.showMDMessage(context,"请选择职业类别!");
            return;
        }
        work = housePersonZy.getText().toString().trim();
        fwcs = housePersonFwcs.getText().toString().trim();
        link = housePersonPhone.getText().toString().trim();
        if(StringUtils.empty(link)){
            MsgUtils.showMDMessage(context,"请输入联系方式!");
            return;
        }
        if(StringUtils.empty(hjd)){
            MsgUtils.showMDMessage(context,"请选择户籍地!");
            return;
        }
        hjddetail = housePersonHjddz.getText().toString().trim();
        if(StringUtils.empty(hjddetail)){
            MsgUtils.showMDMessage(context,"请输入户籍门（楼）详址!");
            return;
        }
        if(StringUtils.empty(xzd)){
            MsgUtils.showMDMessage(context,"请选择现住地!");
            return;
        }
        xzddetail = housePersonXzddz.getText().toString().trim();
        if(StringUtils.empty(xzddetail)){
            MsgUtils.showMDMessage(context,"请输入现住门（楼）详址!");
            return;
        }
        showProgressDialog("");
        Https https = Https.with(this);
        if(entry!=null){
            https.url(CoreApi.update_FxjPerson).put().addParam("id",entry.getId());
        }else{
            https.url(CoreApi.add_FxjPerson).post();
        }
        https.addParam("gid",gid).addParam("pid",pid).addParam("name",name).addParam("uname",uname).addParam("sex",sex).addParam("birthday",birthday)
                .addParam("nation",nation).addParam("nativeplace",nativeplace).addParam("marriage",marriage).addParam("zzmm",zzmm).addParam("edu",edu)
                .addParam("zongj",zongj).addParam("worktype",worktype).addParam("work",work).addParam("fwcs",fwcs).addParam("link",link)
                .addParam("hjd",hjd).addParam("hjddetail",hjddetail).addParam("xzd",xzd).addParam("xzddetail",xzddetail)
                .enqueue(new Callback<Object>() {
            @Override
            public void success(Object data) {
                dismissProgressDialog();
                MsgUtils.showShortToast(context,"保存成功!");
                setResult(RESULT_OK,getIntent());
                finish();
            }

            @Override
            public void fail(Response response) {
                dismissProgressDialog();
                MsgUtils.showMDMessage(context,response.getMsg());
            }
        });
    }

    private void zzmm(){
        showProgressDialog("");
        Https.with(this).url(BaseApi.getTable).addParam("name","gb4762").get()
                .enqueue(new Callback<List<CoreType>>() {
                    @Override
                    public void success(final List<CoreType> data) {
                        dismissProgressDialog();
                        final CommonTypeDialog<CoreType> dialog = new CommonTypeDialog<>(context,data);
                        dialog.showDialog("请选择政治面貌", new CommonAdapter<CoreType>(context, data, R.layout.alen_type_item) {
                            @Override
                            public void convert(ViewHolder holder, CoreType entity, int position) {
                                holder.setText(R.id.name_tv,entity.getLabel());
                            }
                        }, new CommonAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, ViewHolder holder, int position) {
                                dialog.dismiss();
                                zzmm = data.get(position).getValue();
                                housePersonZzmm.setText(data.get(position).getLabel());
                            }

                            @Override
                            public boolean onItemLongClick(View view, ViewHolder holder, int position) {
                                return false;
                            }
                        }).show();
                    }

                    @Override
                    public void fail(Response response) {
                        dismissProgressDialog();
                        MsgUtils.showMDMessage(context,response.getMsg());
                    }
                });
    }
    private void xl(){
        showProgressDialog("");
        Https.with(this).url(BaseApi.getTable).addParam("name","zb64").get()
                .enqueue(new Callback<List<CoreType>>() {
                    @Override
                    public void success(final List<CoreType> data) {
                        dismissProgressDialog();
                        final CommonTypeDialog<CoreType> dialog = new CommonTypeDialog<>(context,data);
                        dialog.showDialog("请选择学历", new CommonAdapter<CoreType>(context, data, R.layout.alen_type_item) {
                            @Override
                            public void convert(ViewHolder holder, CoreType entity, int position) {
                                holder.setText(R.id.name_tv,entity.getLabel());
                            }
                        }, new CommonAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, ViewHolder holder, int position) {
                                dialog.dismiss();
                                edu = data.get(position).getValue();
                                housePersonXl.setText(data.get(position).getLabel());
                            }

                            @Override
                            public boolean onItemLongClick(View view, ViewHolder holder, int position) {
                                return false;
                            }
                        }).show();
                    }

                    @Override
                    public void fail(Response response) {
                        dismissProgressDialog();
                        MsgUtils.showMDMessage(context,response.getMsg());
                    }
                });
    }

    private void hyzk(){
        showProgressDialog("");
        Https.with(this).url(BaseApi.getType).addParam("dictName","people_marriage")
                .addParam("page",0).addParam("size",9999).get()
                .enqueue(new Callback<List<CoreType>>() {
                    @Override
                    public void success(final List<CoreType> data) {
                        dismissProgressDialog();
                        final CommonTypeDialog<CoreType> dialog = new CommonTypeDialog<>(context,data);
                        dialog.showDialog("请选择婚姻状况", new CommonAdapter<CoreType>(context, data, R.layout.alen_type_item) {
                            @Override
                            public void convert(ViewHolder holder, CoreType entity, int position) {
                                holder.setText(R.id.name_tv,entity.getLabel());
                            }
                        }, new CommonAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, ViewHolder holder, int position) {
                                dialog.dismiss();
                                marriage = data.get(position).getValue();
                                housePersonHyzk.setText(data.get(position).getLabel());
                            }

                            @Override
                            public boolean onItemLongClick(View view, ViewHolder holder, int position) {
                                return false;
                            }
                        }).show();
                    }

                    @Override
                    public void fail(Response response) {
                        dismissProgressDialog();
                        MsgUtils.showMDMessage(context,response.getMsg());
                    }
                });
    }
    private void zjxy(){
        showProgressDialog("");
        Https.with(this).url(BaseApi.getType).addParam("dictName","people_religion")
                .addParam("page",0).addParam("size",9999).get()
                .enqueue(new Callback<List<CoreType>>() {
                    @Override
                    public void success(final List<CoreType> data) {
                        dismissProgressDialog();
                        final CommonTypeDialog<CoreType> dialog = new CommonTypeDialog<>(context,data);
                        dialog.showDialog("请选择宗教信仰", new CommonAdapter<CoreType>(context, data, R.layout.alen_type_item) {
                            @Override
                            public void convert(ViewHolder holder, CoreType entity, int position) {
                                holder.setText(R.id.name_tv,entity.getLabel());
                            }
                        }, new CommonAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, ViewHolder holder, int position) {
                                dialog.dismiss();
                                zongj = data.get(position).getValue();
                                housePersonZjxy.setText(data.get(position).getLabel());
                            }

                            @Override
                            public boolean onItemLongClick(View view, ViewHolder holder, int position) {
                                return false;
                            }
                        }).show();
                    }

                    @Override
                    public void fail(Response response) {
                        dismissProgressDialog();
                        MsgUtils.showMDMessage(context,response.getMsg());
                    }
                });
    }
    private void zylb(){
        showProgressDialog("");
        Https.with(this).url(BaseApi.getType).addParam("dictName","people_worktype")
                .addParam("page",0).addParam("size",9999).get()
                .enqueue(new Callback<List<CoreType>>() {
                    @Override
                    public void success(final List<CoreType> data) {
                        dismissProgressDialog();
                        final CommonTypeDialog<CoreType> dialog = new CommonTypeDialog<>(context,data);
                        dialog.showDialog("请选择职业类别", new CommonAdapter<CoreType>(context, data, R.layout.alen_type_item) {
                            @Override
                            public void convert(ViewHolder holder, CoreType entity, int position) {
                                holder.setText(R.id.name_tv,entity.getLabel());
                            }
                        }, new CommonAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, ViewHolder holder, int position) {
                                dialog.dismiss();
                                worktype = data.get(position).getValue();
                                housePersonZylb.setText(data.get(position).getLabel());
                            }

                            @Override
                            public boolean onItemLongClick(View view, ViewHolder holder, int position) {
                                return false;
                            }
                        }).show();
                    }

                    @Override
                    public void fail(Response response) {
                        dismissProgressDialog();
                        MsgUtils.showMDMessage(context,response.getMsg());
                    }
                });
    }

    private void sex(){
        showProgressDialog("");
        Https.with(this).url(BaseApi.getType).addParam("dictName","people_sex")
                .addParam("page",0).addParam("size",9999).get()
                .enqueue(new Callback<List<CoreType>>() {
                    @Override
                    public void success(final List<CoreType> data) {
                        dismissProgressDialog();
                        final CommonTypeDialog<CoreType> dialog = new CommonTypeDialog<>(context,data);
                        dialog.showDialog("请选择性别", new CommonAdapter<CoreType>(context, data, R.layout.alen_type_item) {
                            @Override
                            public void convert(ViewHolder holder, CoreType entity, int position) {
                                holder.setText(R.id.name_tv,entity.getLabel());
                            }
                        }, new CommonAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, ViewHolder holder, int position) {
                                dialog.dismiss();
                                sex = data.get(position).getValue();
                                housePersonSex.setText(data.get(position).getLabel());
                            }

                            @Override
                            public boolean onItemLongClick(View view, ViewHolder holder, int position) {
                                return false;
                            }
                        }).show();
                    }

                    @Override
                    public void fail(Response response) {
                        dismissProgressDialog();
                        MsgUtils.showMDMessage(context,response.getMsg());
                    }
                });
    }
    private void nation(){
        showProgressDialog("");
        Https.with(this).url(BaseApi.getType).addParam("dictName","people_nation")
                .addParam("page",0).addParam("size",9999).get()
                .enqueue(new Callback<List<CoreType>>() {
                    @Override
                    public void success(final List<CoreType> data) {
                        dismissProgressDialog();
                        final CommonTypeDialog<CoreType> dialog = new CommonTypeDialog<>(context,data);
                        dialog.showDialog("请选择民族", new CommonAdapter<CoreType>(context, data, R.layout.alen_type_item) {
                            @Override
                            public void convert(ViewHolder holder, CoreType entity, int position) {
                                holder.setText(R.id.name_tv,entity.getLabel());
                            }
                        }, new CommonAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, ViewHolder holder, int position) {
                                dialog.dismiss();
                                nation = data.get(position).getValue();
                                housePersonMz.setText(data.get(position).getLabel());
                            }

                            @Override
                            public boolean onItemLongClick(View view, ViewHolder holder, int position) {
                                return false;
                            }
                        }).show();
                    }

                    @Override
                    public void fail(Response response) {
                        dismissProgressDialog();
                        MsgUtils.showMDMessage(context,response.getMsg());
                    }
                });
    }

    private List<CoreType> coreTypes;
    private void hjd(){
        if(coreTypes!=null){
            final CommonTypeDialog<CoreType> dialog = new CommonTypeDialog<>(context);
            dialog.setCoreTypes(coreTypes);
            dialog.showLevelDialog("请选择户籍地", new CoreTypeAdapter(context),
                    new CoreTypeAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View v, final CoreType mentry, int position) {
                            final List<CoreType> child = mentry.getChildren();
                            if(child==null||child.size()==0){
                                dialog.dismiss();
                                hjd = mentry.getValue();
                                housePersonHjd.setText(mentry.getLabel());
                            }else{
                                dialog.setNextData(new CoreTypeAdapter(context,child), new CoreTypeAdapter.OnItemClickListener(){
                                    @Override
                                    public void onItemClick(View v, CoreType entry, int position) {
                                        dialog.dismiss();
                                        hjd = entry.getValue();
                                        housePersonHjd.setText(mentry.getLabel()+entry.getLabel());
                                    }
                                });
                            }
                        }
                    }).show();
        }else{
            showProgressDialog("");
            Https.with(this).url(BaseApi.getTable).addParam("name","zb01").get()
                    .enqueue(new Callback<List<CoreType>>() {
                        @Override
                        public void success(final List<CoreType> data) {
                            dismissProgressDialog();
                            coreTypes = data;
                            final CommonTypeDialog<CoreType> dialog = new CommonTypeDialog<>(context);
                            dialog.setCoreTypes(coreTypes);
                            dialog.showLevelDialog("请选择户籍地", new CoreTypeAdapter(context),
                                    new CoreTypeAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View v, final CoreType mentry, int position) {
                                            final List<CoreType> child = mentry.getChildren();
                                            if(child==null||child.size()==0){
                                                dialog.dismiss();
                                                hjd = mentry.getValue();
                                                housePersonHjd.setText(mentry.getLabel());
                                            }else{
                                                dialog.setNextData(new CoreTypeAdapter(context,child), new CoreTypeAdapter.OnItemClickListener(){
                                                    @Override
                                                    public void onItemClick(View v, CoreType entry, int position) {
                                                        dialog.dismiss();
                                                        xzd = entry.getValue();
                                                        housePersonHjd.setText(mentry.getLabel()+entry.getLabel());
                                                    }
                                                });
                                            }
                                        }
                                    }).show();
                        }

                        @Override
                        public void fail(Response response) {
                            dismissProgressDialog();
                            MsgUtils.showMDMessage(context,response.getMsg());
                        }
                    });
        }
    }
    private void xzd(){
        if(coreTypes!=null){
            final CommonTypeDialog<CoreType> dialog = new CommonTypeDialog<>(context);
            dialog.setCoreTypes(coreTypes);
            dialog.showLevelDialog("请选择现住地", new CoreTypeAdapter(context),
                    new CoreTypeAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View v, final CoreType mentry, int position) {
                            final List<CoreType> child = mentry.getChildren();
                            if(child==null||child.size()==0){
                                dialog.dismiss();
                                xzd = mentry.getValue();
                                housePersonXzd.setText(mentry.getLabel());
                            }else{
                                dialog.setNextData(new CoreTypeAdapter(context,child), new CoreTypeAdapter.OnItemClickListener(){
                                    @Override
                                    public void onItemClick(View v, CoreType entry, int position) {
                                        dialog.dismiss();
                                        xzd = entry.getValue();
                                        housePersonXzd.setText(mentry.getLabel()+entry.getLabel());
                                    }
                                });
                            }
                        }
                    }).show();
        }else{
            showProgressDialog("");
            Https.with(this).url(BaseApi.getTable).addParam("name","zb01").get()
                    .enqueue(new Callback<List<CoreType>>() {
                        @Override
                        public void success(final List<CoreType> data) {
                            dismissProgressDialog();
                            coreTypes = data;
                            final CommonTypeDialog<CoreType> dialog = new CommonTypeDialog<>(context);
                            dialog.setCoreTypes(coreTypes);
                            dialog.showLevelDialog("请选择现住地", new CoreTypeAdapter(context),
                                    new CoreTypeAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View v, final CoreType mentry, int position) {
                                            final List<CoreType> child = mentry.getChildren();
                                            if(child==null||child.size()==0){
                                                dialog.dismiss();
                                                xzd = mentry.getValue();
                                                housePersonXzd.setText(mentry.getLabel());
                                            }else{
                                                dialog.setNextData(new CoreTypeAdapter(context,child), new CoreTypeAdapter.OnItemClickListener(){
                                                    @Override
                                                    public void onItemClick(View v, CoreType entry, int position) {
                                                        dialog.dismiss();
                                                        xzd = entry.getValue();
                                                        housePersonXzd.setText(mentry.getLabel()+entry.getLabel());
                                                    }
                                                });
                                            }
                                        }
                                    }).show();
                        }

                        @Override
                        public void fail(Response response) {
                            dismissProgressDialog();
                            MsgUtils.showMDMessage(context,response.getMsg());
                        }
                    });
        }
    }
}
