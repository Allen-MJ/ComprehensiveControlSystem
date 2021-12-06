package cn.lyj.core.person;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import allen.frame.AllenBaseActivity;
import allen.frame.AllenChoiceGridActivity;
import allen.frame.MultiImageSelector;
import allen.frame.adapter.CommonAdapter;
import allen.frame.adapter.CoreTypeAdapter;
import allen.frame.adapter.ViewHolder;
import allen.frame.entry.CoreType;
import allen.frame.entry.File;
import allen.frame.entry.Response;
import allen.frame.entry.UploadFile;
import allen.frame.net.BaseApi;
import allen.frame.net.Callback;
import allen.frame.net.Https;
import allen.frame.tools.CommonTypeDialog;
import allen.frame.tools.Constants;
import allen.frame.tools.DatePickerDialog;
import allen.frame.tools.FileIntent;
import allen.frame.tools.Logger;
import allen.frame.tools.MsgUtils;
import allen.frame.tools.PermissionListener;
import allen.frame.tools.StringUtils;
import allen.frame.tools.UploadProgressDialog;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.OnClick;
import cn.lyj.core.R;
import cn.lyj.core.R2;
import cn.lyj.core.api.CoreApi;
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
    @BindView(R2.id.person_photo)
    AppCompatImageView personPhoto;
    private TranPerson entry;
    private String gid,sex,mz,hyzk,zzmm,xl,zjxy,zylb,hjd,xzd,lryy,bzlx,czrk,zdry,zslx;
    private UploadProgressDialog dialog;
    private String photo;

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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case 10:
                    gid = data.getStringExtra(Constants.Key_1);
                    housePersonWg.setText(data.getStringExtra(Constants.Key_2));
                    break;
                case 11:
                    ArrayList<String> image = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                    String path = image.get(0);
                    File file = new File();
                    file.setName(StringUtils.getFileNameByPath(path));
                    file.setPath(path);
                    file.setType(0);//图片
                    file.setSuffix(FileIntent.getMIMEType(file.getFile()));
                    dialog.init(context);
                    upload(file);
                    break;
            }
        }
    }

    private void upload(final File file) {
        Https.with(this).url(BaseApi.Upload).file(file).upload().enqueue(new Callback<UploadFile>() {

            @Override
            public void success(UploadFile data) {
                Logger.e("success", "success");
                photo = data.getRelativePath();
                Glide.with(context).load(Constants.url+photo).error(R.mipmap.core_default_photo)
                        .placeholder(R.mipmap.core_default_photo)
                        .into(personPhoto);
            }

            @Override
            public void onProgress(long total, long current) {
                dialog.changeProgress(file.getName(), total, current);
            }

            @Override
            public void fail(Response response) {
                dialog.dismiss();
                MsgUtils.showMDMessage(context,response.getMsg());
            }
        });
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        dialog = new UploadProgressDialog();
        if(entry!=null){
            photo = entry.getPicturePath();
            Glide.with(this).load(Constants.url+photo)
                    .error(R.mipmap.core_default_photo)
                    .placeholder(R.mipmap.core_default_photo)
                    .into(personPhoto);
            gid = entry.getGid();
            housePersonWg.setText(entry.getGidObj().getGenericName());
            housePersonName.setText(entry.getB1302());
            housePersonAname.setText(entry.getB1303());
            housePersonIdnumber.setText(entry.getB1301());
            sex = entry.getB1304();
            housePersonSex.setText(entry.getB1304Name());
            housePersonBirthday.setText(entry.getB1305());
            housePersonJg.setText(entry.getB1307());
            mz = entry.getB1306();
            housePersonMz.setText(entry.getB1306());
            housePersonHyzk.setText(entry.getB1308());
            hyzk = entry.getB1308();
            housePersonZzmm.setText(entry.getB1309Obj().getCodeName());
            zzmm = entry.getB1309();
            housePersonXl.setText(entry.getB1310Obj().getCodeName());
            xl = entry.getB1310();
            housePersonZjxy.setText(entry.getB1311());
            zjxy = entry.getB1311();
            housePersonZylb.setText(entry.getB1312());
            zylb = entry.getB1312();
            housePersonZy.setText(entry.getB1313());
            housePersonFwcs.setText(entry.getB1314());
            housePersonPhone.setText(entry.getB1315());
            housePersonHjd.setText(entry.getB1316Obj().getCodeName());
            hjd = entry.getB1316();
            housePersonHjddz.setText(entry.getB1317());
            housePersonXzd.setText(entry.getB1318Obj().getCodeName());
            xzd = entry.getB1318();
            housePersonXzddz.setText(entry.getB1319());
            housePersonLryy.setText(entry.getB1320());
            lryy = entry.getB1320();
            housePersonBzlx.setText(entry.getB1321());
            bzlx = entry.getB1321();
            housePersonZjhm.setText(entry.getB1322());
            housePersonDjrq.setText(entry.getB1323());
            housePersonZjdq.setText(entry.getB1324());
            housePersonZslx.setText(entry.getB1325());
            zslx = entry.getB1325();
            housePersonSfcz.setText(entry.getB1326Name());
            czrk = entry.getB1326();
            housePersonSfzd.setText(entry.getB1327Name());
            zdry = entry.getB1327();
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
        dialog.setOnCompletListener(new UploadProgressDialog.OnCompletListener() {
            @Override
            public void onComplet(ProgressDialog dialog) {
                dialog.dismiss();
                MsgUtils.showShortToast(context, "上传成功!");
            }
        });
    }

    @OnClick({R2.id.house_person_wg, R2.id.house_person_sex, R2.id.house_person_birthday, R2.id.house_person_mz, R2.id.house_person_hyzk,
            R2.id.house_person_zzmm, R2.id.house_person_xl,R2.id.house_person_zjxy, R2.id.house_person_zylb, R2.id.house_person_lryy,
            R2.id.house_person_bzlx, R2.id.house_person_djrq, R2.id.house_person_zjdq,R2.id.house_person_sfcz, R2.id.house_person_sfzd,
            R2.id.house_person_zslx, R2.id.house_person_hjd, R2.id.house_person_xzd, R2.id.commit_bt, R2.id.person_photo})
    public void onViewClicked(View view) {
        view.setEnabled(false);
        int id = view.getId();
        if(id== R.id.house_person_wg){
            startActivityForResult(new Intent(context, AllenChoiceGridActivity.class),10);
        }else if(id== R.id.house_person_sex){
            sex();
        }else if(id == R.id.house_person_birthday){
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear, int startDayOfMonth) {
                    housePersonBirthday.setText(startYear + "-" + String.format("%02d", startMonthOfYear + 1) + "-" + String.format("%02d", startDayOfMonth));
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        }else if(id== R.id.house_person_mz){
            mz();
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
        }else if(id== R.id.house_person_lryy){
            lryy();
        }else if(id== R.id.house_person_bzlx){
            bzlx();
        }else if(id== R.id.house_person_djrq){
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear, int startDayOfMonth) {
                    housePersonDjrq.setText(startYear + "-" + String.format("%02d", startMonthOfYear + 1) + "-" + String.format("%02d", startDayOfMonth));
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        }else if(id== R.id.house_person_zjdq){
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear, int startDayOfMonth) {
                    housePersonZjdq.setText(startYear + "-" + String.format("%02d", startMonthOfYear + 1) + "-" + String.format("%02d", startDayOfMonth));
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        }else if(id== R.id.house_person_sfcz){
            czrk();
        }else if(id== R.id.house_person_sfzd){
            zdry();
        }else if(id== R.id.house_person_zslx){
            zslx();
        }else if(id == R.id.house_person_hjd){
            hjd();
        }else if(id == R.id.house_person_xzd){
            xzd();
        }else if(id== R.id.commit_bt){
            commit();
        } else if(id==R.id.person_photo){
            requestRunPermisssion(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10, new PermissionListener() {
                @Override
                public void onGranted(int requestCode) {
                    MultiImageSelector.create().single().showCamera(true)
                            .start(UpdateTranPersonActivity.this,11);
                }

                @Override
                public void onDenied(List<String> deniedPermission) {
                    MsgUtils.showMDMessage(context,"请开通需要的权限!");
                }
            });
        }
        view.setEnabled(true);
    }

    private void commit(){
        String idno = housePersonIdnumber.getText().toString().trim();
        if(StringUtils.empty(idno)){
            MsgUtils.showMDMessage(context,"请输入身份证号码");
            return;
        }
        String name = housePersonName.getText().toString().trim();
        String aname = housePersonAname.getText().toString().trim();
        if(StringUtils.empty(name)){
            MsgUtils.showMDMessage(context,"请输入姓名");
            return;
        }
        if(StringUtils.empty(sex)){
            MsgUtils.showMDMessage(context,"请选择性别");
            return;
        }
        String birth = housePersonBirthday.getText().toString().trim();
        if(StringUtils.empty(birth)){
            MsgUtils.showMDMessage(context,"请选择出生日期");
            return;
        }
        String jg = housePersonIdnumber.getText().toString().trim();
        if(StringUtils.empty(jg)){
            MsgUtils.showMDMessage(context,"请输入籍贯");
            return;
        }
        if(StringUtils.empty(mz)){
            MsgUtils.showMDMessage(context,"请选择民族");
            return;
        }
        if(StringUtils.empty(hyzk)){
            MsgUtils.showMDMessage(context,"请选择婚姻状况");
            return;
        }
        if(StringUtils.empty(zzmm)){
            MsgUtils.showMDMessage(context,"请选择政治面貌");
            return;
        }
        if(StringUtils.empty(xl)){
            MsgUtils.showMDMessage(context,"请选择学历");
            return;
        }
        if(StringUtils.empty(xl)){
            MsgUtils.showMDMessage(context,"请选择学历");
            return;
        }
        if(StringUtils.empty(zylb)){
            MsgUtils.showMDMessage(context,"请选择职业类别");
            return;
        }
        String zy = housePersonZy.getText().toString().trim();
        if(StringUtils.empty(zy)){
            MsgUtils.showMDMessage(context,"请输入职业");
            return;
        }
        String fwcs = housePersonFwcs.getText().toString().trim();
        String phone = housePersonPhone.getText().toString().trim();
        if(StringUtils.empty(phone)){
            MsgUtils.showMDMessage(context,"请输入联系方式");
            return;
        }
        if(StringUtils.empty(hjd)){
            MsgUtils.showMDMessage(context,"请选择户籍地");
            return;
        }
        String hjddz = housePersonHjddz.getText().toString().trim();
        if(StringUtils.empty(hjddz)){
            MsgUtils.showMDMessage(context,"请输入户籍门（楼）地址");
            return;
        }
        if(StringUtils.empty(xzd)){
            MsgUtils.showMDMessage(context,"请选择现住地");
            return;
        }
        String xzddz = housePersonXzddz.getText().toString().trim();
        if(StringUtils.empty(xzddz)){
            MsgUtils.showMDMessage(context,"请输入现住门（楼）地址");
            return;
        }
        String zjhm = housePersonZjhm.getText().toString().trim();
        String djrq = housePersonDjrq.getText().toString().trim();
        String zjdq = housePersonZjdq.getText().toString().trim();
        if(StringUtils.empty(zslx)){
            MsgUtils.showMDMessage(context,"请选择住所类型");
            return;
        }
        if(StringUtils.empty(czrk)){
            MsgUtils.showMDMessage(context,"请选择是否常住人口");
            return;
        }
        if(StringUtils.empty(zdry)){
            MsgUtils.showMDMessage(context,"请选择是否重点关注人员");
            return;
        }
        if(StringUtils.empty(gid)){
            MsgUtils.showMDMessage(context,"请选择所在网格");
            return;
        }
        showProgressDialog("");
        Https https = Https.with(this);
        if(entry!=null){
            https.url(CoreApi.TranPersonUpdate).addParam("",entry.getB1300()).put();
        }else{
            https.url(CoreApi.TranPersonAdd).post();
        }
        https.addParam("b1301",idno).addParam("b1302",name).addParam("b1303",aname).addParam("b1304",sex).
            addParam("b1305",birth).addParam("b1306",mz).addParam("b1307",jg).addParam("b1308",hyzk).
            addParam("b1309",zzmm).addParam("b1310",xl).addParam("b1311",zjxy).addParam("b1312",zylb).
            addParam("b1313",zy).addParam("b1314",fwcs).addParam("b1315",phone).addParam("b1316",hjd).
            addParam("b1317",hjddz).addParam("b1318",xzd).addParam("b1319",xzddz).addParam("b1320",lryy).
            addParam("b1321",bzlx).addParam("b1322",zjhm).addParam("b1323",djrq).addParam("b1324",zjdq).
            addParam("b1325",zslx).addParam("b1326",czrk).addParam("b1327",zdry).addParam("gid",gid).addParam("picture_path",photo)
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
    private void mz(){
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
                                mz = data.get(position).getValue();
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
                                hyzk = data.get(position).getValue();
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
                                xl = data.get(position).getValue();
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
                                zjxy = data.get(position).getValue();
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
                                zylb = data.get(position).getValue();
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
    private void lryy(){
        showProgressDialog("");
        Https.with(this).url(BaseApi.getType).addParam("dictName","flowinto_cause")
                .addParam("page",0).addParam("size",9999).get()
                .enqueue(new Callback<List<CoreType>>() {
                    @Override
                    public void success(final List<CoreType> data) {
                        dismissProgressDialog();
                        final CommonTypeDialog<CoreType> dialog = new CommonTypeDialog<>(context,data);
                        dialog.showDialog("请选择流入原因", new CommonAdapter<CoreType>(context, data, R.layout.alen_type_item) {
                            @Override
                            public void convert(ViewHolder holder, CoreType entity, int position) {
                                holder.setText(R.id.name_tv,entity.getLabel());
                            }
                        }, new CommonAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, ViewHolder holder, int position) {
                                dialog.dismiss();
                                lryy = data.get(position).getValue();
                                housePersonLryy.setText(data.get(position).getLabel());
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
    private void bzlx(){
        showProgressDialog("");
        Https.with(this).url(BaseApi.getType).addParam("dictName","certificate_type")
                .addParam("page",0).addParam("size",9999).get()
                .enqueue(new Callback<List<CoreType>>() {
                    @Override
                    public void success(final List<CoreType> data) {
                        dismissProgressDialog();
                        final CommonTypeDialog<CoreType> dialog = new CommonTypeDialog<>(context,data);
                        dialog.showDialog("请选择流入原因", new CommonAdapter<CoreType>(context, data, R.layout.alen_type_item) {
                            @Override
                            public void convert(ViewHolder holder, CoreType entity, int position) {
                                holder.setText(R.id.name_tv,entity.getLabel());
                            }
                        }, new CommonAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, ViewHolder holder, int position) {
                                dialog.dismiss();
                                bzlx = data.get(position).getValue();
                                housePersonBzlx.setText(data.get(position).getLabel());
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
    private void czrk(){
        showProgressDialog("");
        Https.with(this).url(BaseApi.getType).addParam("dictName","system_trueorfalse")
                .addParam("page",0).addParam("size",9999).get()
                .enqueue(new Callback<List<CoreType>>() {
                    @Override
                    public void success(final List<CoreType> data) {
                        dismissProgressDialog();
                        final CommonTypeDialog<CoreType> dialog = new CommonTypeDialog<>(context,data);
                        dialog.showDialog("请选择是否常住人口", new CommonAdapter<CoreType>(context, data, R.layout.alen_type_item) {
                            @Override
                            public void convert(ViewHolder holder, CoreType entity, int position) {
                                holder.setText(R.id.name_tv,entity.getLabel());
                            }
                        }, new CommonAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, ViewHolder holder, int position) {
                                dialog.dismiss();
                                czrk = data.get(position).getValue();
                                housePersonSfcz.setText(data.get(position).getLabel());
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
    private void zdry(){
        showProgressDialog("");
        Https.with(this).url(BaseApi.getType).addParam("dictName","system_trueorfalse")
                .addParam("page",0).addParam("size",9999).get()
                .enqueue(new Callback<List<CoreType>>() {
                    @Override
                    public void success(final List<CoreType> data) {
                        dismissProgressDialog();
                        final CommonTypeDialog<CoreType> dialog = new CommonTypeDialog<>(context,data);
                        dialog.showDialog("请选择是重点关注人员", new CommonAdapter<CoreType>(context, data, R.layout.alen_type_item) {
                            @Override
                            public void convert(ViewHolder holder, CoreType entity, int position) {
                                holder.setText(R.id.name_tv,entity.getLabel());
                            }
                        }, new CommonAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, ViewHolder holder, int position) {
                                dialog.dismiss();
                                zdry = data.get(position).getValue();
                                housePersonSfzd.setText(data.get(position).getLabel());
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
    private void zslx(){
        showProgressDialog("");
        Https.with(this).url(BaseApi.getType).addParam("dictName","domicile_type")
                .addParam("page",0).addParam("size",9999).get()
                .enqueue(new Callback<List<CoreType>>() {
                    @Override
                    public void success(final List<CoreType> data) {
                        dismissProgressDialog();
                        final CommonTypeDialog<CoreType> dialog = new CommonTypeDialog<>(context,data);
                        dialog.showDialog("请选择住所类型", new CommonAdapter<CoreType>(context, data, R.layout.alen_type_item) {
                            @Override
                            public void convert(ViewHolder holder, CoreType entity, int position) {
                                holder.setText(R.id.name_tv,entity.getLabel());
                            }
                        }, new CommonAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, ViewHolder holder, int position) {
                                dialog.dismiss();
                                zslx = data.get(position).getValue();
                                housePersonZslx.setText(data.get(position).getLabel());
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
                        public void onItemClick(View v, CoreType entry, int position) {
                            final List<CoreType> child = entry.getChildren();
                            if(child==null||child.size()==0){
                                dialog.dismiss();
                                hjd = entry.getValue();
                                housePersonHjd.setText(entry.getLabel());
                            }else{
                                dialog.setNextData(new CoreTypeAdapter(context,child), new CoreTypeAdapter.OnItemClickListener(){
                                    @Override
                                    public void onItemClick(View v, CoreType entry, int position) {
                                        dialog.dismiss();
                                        hjd = entry.getValue();
                                        housePersonHjd.setText(entry.getLabel());
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
                                                        hjd = entry.getValue();
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
                            dialog.showLevelDialog("请选择户籍地", new CoreTypeAdapter(context),
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
