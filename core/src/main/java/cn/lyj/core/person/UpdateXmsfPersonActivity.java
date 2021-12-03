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
    AppCompatTextView xmsfPersonXjqk;
    @BindView(R2.id.xmsf_person_azrq)
    AppCompatTextView xmsfPersonAzrq;
    @BindView(R2.id.xmsf_person_azqk)
    AppCompatTextView xmsfPersonAzqk;
    @BindView(R2.id.xmsf_person_wazyy)
    AppCompatEditText xmsfPersonWazyy;
    @BindView(R2.id.xmsf_person_bjqk)
    AppCompatEditText xmsfPersonBjqk;
    @BindView(R2.id.xmsf_person_sfcxfz)
    AppCompatTextView xmsfPersonSfcxfz;
    @BindView(R2.id.xmsf_person_cxfzzm)
    AppCompatTextView xmsfPersonCxfzzm;
    @BindView(R2.id.commit_bt)
    AppCompatButton commitBt;
    @BindView(R2.id.person_photo)
    AppCompatImageView personPhoto;
    private XmsfPersonEntity entry;
    private String sex,nation,marriage,zzmm,edu,
            zongj,worktype,hjd,xzd,gid;
    private UploadProgressDialog dialog;
    private String photo;

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
            photo = entry.getPicture_path();
            Glide.with(this).load(Constants.url+photo)
                    .error(R.mipmap.core_default_photo)
                    .placeholder(R.mipmap.core_default_photo)
                    .into(personPhoto);
            gid = entry.getGid();
            housePersonWg.setText(entry.getGidObj().getOrgFullName());
            housePersonName.setText(entry.getB1702());
            housePersonAname.setText(entry.getB1703());
            housePersonIdnumber.setText(entry.getB1701());
            housePersonSex.setText(entry.getB1704Name());
            sex = entry.getB1704();
            housePersonBirthday.setText(entry.getB1705());
            housePersonJg.setText(entry.getB1707());
            housePersonMz.setText(entry.getB1706());
            nation = entry.getB1706();
            housePersonHyzk.setText(entry.getB1708());
            marriage = entry.getB1708();
            housePersonZzmm.setText(entry.getB1709Obj().getCodeName());
            zzmm = entry.getB1709();
            housePersonXl.setText(entry.getB1710Obj().getCodeName());
            edu = entry.getB1710();
            housePersonZjxy.setText(entry.getB1711());
            zongj = entry.getB1711();
            housePersonZylb.setText(entry.getB1712());
            worktype = entry.getB1712();
            housePersonZy.setText(entry.getB1713());
            housePersonFwcs.setText(entry.getB1714());
            housePersonPhone.setText(entry.getB1715());
            housePersonHjd.setText(entry.getB1716Obj().getCodeName());
            hjd = entry.getB1716();
            housePersonHjddz.setText(entry.getB1717());
            housePersonXzd.setText(entry.getB1718Obj().getCodeName());
            xzd = entry.getB1718();
            housePersonXzddz.setText(entry.getB1719());

            xmsfPersonSflf.setText(entry.getB1720Name());
            sflf = entry.getB1720();
            xmsfPersonYzm.setText(entry.getB1721());
            yzm = entry.getB1721();
            xmsfPersonYpxq.setText(entry.getB1722());
            xmsfPersonFxcs.setText(entry.getB1723());
            xmsfPersonSfrq.setText(entry.getB1724());
            xmsfPersonWxxpg.setText(entry.getB1725());
            wxpg = entry.getB1725();
            xmsfPersonXjrq.setText(entry.getB1726());
            xmsfPersonXjqk.setText(entry.getB1727());
            xzqk = entry.getB1727();
            xmsfPersonAzrq.setText(entry.getB1728());
            xmsfPersonAzqk.setText(entry.getB1729());
            azqk = entry.getB1729();
            xmsfPersonWazyy.setText(entry.getB1730());
            xmsfPersonBjqk.setText(entry.getB1731());
            xmsfPersonSfcxfz.setText(entry.getB1732Name());
            sfcxfz = entry.getB1732();
            xmsfPersonCxfzzm.setText(entry.getB1733());
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
            R2.id.house_person_zzmm,R2.id.house_person_xl, R2.id.house_person_zjxy, R2.id.house_person_zylb,
            R2.id.xmsf_person_sflf,R2.id.xmsf_person_yzm, R2.id.xmsf_person_sfrq, R2.id.xmsf_person_wxxpg, R2.id.xmsf_person_xjrq,
            R2.id.xmsf_person_azrq,R2.id.xmsf_person_azqk,R2.id.xmsf_person_sfcxfz, R2.id.xmsf_person_cxfzzm, R2.id.house_person_hjd,
            R2.id.house_person_xzd,R2.id.xmsf_person_xjqk, R2.id.commit_bt, R2.id.person_photo})
    public void onViewClicked(View view) {
        view.setEnabled(false);
        int id = view.getId();
        if (id == R.id.house_person_wg) {
            startActivityForResult(new Intent(context, AllenChoiceGridActivity.class),10);
        } else if (id == R.id.house_person_sex) {
            sex();
        } else if(id == R.id.house_person_birthday){
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear, int startDayOfMonth) {
                    housePersonBirthday.setText(startYear + "-" + String.format("%02d", startMonthOfYear + 1) + "-" + String.format("%02d", startDayOfMonth));
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        } else if (id == R.id.house_person_mz) {
            nation();
        } else if (id == R.id.house_person_hyzk) {
            hyzk();
        } else if (id == R.id.house_person_zzmm) {
            zzmm();
        } else if (id == R.id.house_person_xl) {
            xl();
        } else if (id == R.id.house_person_zjxy) {
            zjxy();
        } else if (id == R.id.house_person_zylb) {
            zylb();
        }else if (id == R.id.xmsf_person_sflf) {
            sflf();
        } else if (id == R.id.xmsf_person_yzm) {
            yzm();
        } else if (id == R.id.xmsf_person_sfrq) {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear, int startDayOfMonth) {
                    xmsfPersonSfrq.setText(startYear + "-" + String.format("%02d", startMonthOfYear + 1) + "-" + String.format("%02d", startDayOfMonth));
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        } else if (id == R.id.xmsf_person_wxxpg) {
            wxpg();
        } else if (id == R.id.xmsf_person_xjrq) {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear, int startDayOfMonth) {
                    xmsfPersonXjrq.setText(startYear + "-" + String.format("%02d", startMonthOfYear + 1) + "-" + String.format("%02d", startDayOfMonth));
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        } else if (id == R.id.xmsf_person_azrq) {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear, int startDayOfMonth) {
                    xmsfPersonAzrq.setText(startYear + "-" + String.format("%02d", startMonthOfYear + 1) + "-" + String.format("%02d", startDayOfMonth));
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        } else if(id==R.id.xmsf_person_azqk){
            azqk();
        }else if (id == R.id.xmsf_person_sfcxfz) {
            sfcxfz();
        } else if(id==R.id.xmsf_person_cxfzzm){
            cfzm();
        }else if(id==R.id.house_person_hjd){
            hjd();
        } else if(id==R.id.house_person_xzd){
            xzd();
        } else if(id==R.id.xmsf_person_xjqk){
            xzqk();
        }else if (id == R.id.commit_bt) {
            commit();
        } else if(id==R.id.person_photo){
            requestRunPermisssion(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10, new PermissionListener() {
                @Override
                public void onGranted(int requestCode) {
                    MultiImageSelector.create().single().showCamera(true)
                            .start(UpdateXmsfPersonActivity.this,11);
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
        if(StringUtils.empty(gid)){
            MsgUtils.showMDMessage(context,"请选择所属网格!");
            return;
        }
        String pid = housePersonIdnumber.getText().toString().trim();
        if(StringUtils.empty(pid)){
            MsgUtils.showMDMessage(context,"请输入身份证号!");
            return;
        }
        String name = housePersonName.getText().toString().trim();
        if(StringUtils.empty(name)){
            MsgUtils.showMDMessage(context,"请输入姓名!");
            return;
        }
        String uname = housePersonAname.getText().toString().trim();
        if(StringUtils.empty(sex)){
            MsgUtils.showMDMessage(context,"请选择性别!");
            return;
        }
        String birthday = housePersonBirthday.getText().toString().trim();
        if(StringUtils.empty(birthday)){
            MsgUtils.showMDMessage(context,"请输入出生日期!");
            return;
        }
        nation = housePersonMz.getText().toString().trim();
        if(StringUtils.empty(nation)){
            MsgUtils.showMDMessage(context,"请选择民族!");
            return;
        }
        String nativeplace = housePersonJg.getText().toString().trim();
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
        String work = housePersonZy.getText().toString().trim();
        String fwcs = housePersonFwcs.getText().toString().trim();
        String link = housePersonPhone.getText().toString().trim();
        if(StringUtils.empty(link)){
            MsgUtils.showMDMessage(context,"请输入联系方式!");
            return;
        }
        if(StringUtils.empty(hjd)){
            MsgUtils.showMDMessage(context,"请选择户籍地!");
            return;
        }
        String hjddetail = housePersonHjddz.getText().toString().trim();
        if(StringUtils.empty(hjddetail)){
            MsgUtils.showMDMessage(context,"请输入户籍门（楼）详址!");
            return;
        }
        if(StringUtils.empty(xzd)){
            MsgUtils.showMDMessage(context,"请选择现住地!");
            return;
        }
        String xzddetail = housePersonXzddz.getText().toString().trim();
        if(StringUtils.empty(xzddetail)){
            MsgUtils.showMDMessage(context,"请输入现住门（楼）详址!");
            return;
        }
        if(StringUtils.empty(sflf)){
            MsgUtils.showMDMessage(context,"请选择是否累犯!");
            return;
        }
        if(StringUtils.empty(yzm)){
            MsgUtils.showMDMessage(context,"请选择原罪名!");
            return;
        }
        String ypdq = xmsfPersonYpxq.getText().toString().trim();
        if(StringUtils.empty(ypdq)){
            MsgUtils.showMDMessage(context,"请输入原判刑期!");
            return;
        }
        String fxcs = xmsfPersonFxcs.getText().toString().trim();
        if(StringUtils.empty(fxcs)){
            MsgUtils.showMDMessage(context,"请输入服刑场所!");
            return;
        }
        String sfrq = xmsfPersonSfrq.getText().toString().trim();
        if(StringUtils.empty(sfrq)){
            MsgUtils.showMDMessage(context,"请输入释放日期!");
            return;
        }
        if(StringUtils.empty(wxpg)){
            MsgUtils.showMDMessage(context,"请选择危险性评估类型!");
            return;
        }
        String xzrq = xmsfPersonXjrq.getText().toString().trim();
        if(StringUtils.empty(xzrq)){
            MsgUtils.showMDMessage(context,"请选择衔接日期!");
            return;
        }
        if(StringUtils.empty(xzqk)){
            MsgUtils.showMDMessage(context,"请选择衔接情况!");
            return;
        }
        String azrq = xmsfPersonAzrq.getText().toString().trim();
        if(StringUtils.empty(azrq)){
            MsgUtils.showMDMessage(context,"请选择安置日期!");
            return;
        }
        if(StringUtils.empty(azqk)){
            MsgUtils.showMDMessage(context,"请选择安置情况!");
            return;
        }
        if(StringUtils.empty(sfcxfz)){
            MsgUtils.showMDMessage(context,"请选择是否重新犯罪!");
            return;
        }
        showProgressDialog("");
        Https https = Https.with(this);
        if(entry!=null){
            https.url(CoreApi.update_XmsfPerson).put().addParam("B1700",entry.getB1700());
        }else{
            https.url(CoreApi.add_XmsfPerson).post();
        }
        https.addParam("B1701",pid).addParam("B1702",name).addParam("B1703",uname).addParam("B1704",sex).addParam("B1705",birthday)
                .addParam("B1706",nation).addParam("B1707",nativeplace).addParam("B1708",marriage).addParam("B1709",zzmm)
                .addParam("B1710",edu).addParam("B1711",zongj).addParam("B1712",worktype).addParam("B1713",work).addParam("B1714",fwcs)
                .addParam("B1715",link).addParam("B1716",hjd).addParam("B1717",hjddetail).addParam("B1718",xzd).addParam("B1719",xzddetail)
                .addParam("B1720",sflf).addParam("B1721",yzm).addParam("B1722",ypdq).addParam("B1723",fxcs).addParam("B1724",sfrq)
                .addParam("B1725",wxpg).addParam("B1726",xzrq).addParam("B1727",xzqk).addParam("B1728",azrq).addParam("B1729",azqk)
                .addParam("B1732",sfcxfz).addParam("b1733",cfzm).addParam("picture_path",photo)
                .addParam("gid",gid).enqueue(new Callback<Object>() {
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

    private String wxpg;
    private void wxpg(){
        showProgressDialog("");
        Https.with(this).url(BaseApi.getType).addParam("dictName","risk_type")
                .addParam("page",0).addParam("size",9999).get()
                .enqueue(new Callback<List<CoreType>>() {
                    @Override
                    public void success(final List<CoreType> data) {
                        dismissProgressDialog();
                        final CommonTypeDialog<CoreType> dialog = new CommonTypeDialog<>(context,data);
                        dialog.showDialog("请选择危险性评估类型", new CommonAdapter<CoreType>(context, data, R.layout.alen_type_item) {
                            @Override
                            public void convert(ViewHolder holder, CoreType entity, int position) {
                                holder.setText(R.id.name_tv,entity.getLabel());
                            }
                        }, new CommonAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, ViewHolder holder, int position) {
                                dialog.dismiss();
                                wxpg = data.get(position).getValue();
                                xmsfPersonWxxpg.setText(data.get(position).getLabel());
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
    private String sfcxfz;
    private void sfcxfz(){
        showProgressDialog("");
        Https.with(this).url(BaseApi.getType).addParam("dictName","system_trueorfalse")
                .addParam("page",0).addParam("size",9999).get()
                .enqueue(new Callback<List<CoreType>>() {
                    @Override
                    public void success(final List<CoreType> data) {
                        dismissProgressDialog();
                        final CommonTypeDialog<CoreType> dialog = new CommonTypeDialog<>(context,data);
                        dialog.showDialog("请选择是否重新犯罪", new CommonAdapter<CoreType>(context, data, R.layout.alen_type_item) {
                            @Override
                            public void convert(ViewHolder holder, CoreType entity, int position) {
                                holder.setText(R.id.name_tv,entity.getLabel());
                            }
                        }, new CommonAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, ViewHolder holder, int position) {
                                dialog.dismiss();
                                sfcxfz = data.get(position).getValue();
                                xmsfPersonSfcxfz.setText(data.get(position).getLabel());
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
    private String yzm;
    private void yzm(){
        showProgressDialog("");
        Https.with(this).url(BaseApi.getType).addParam("dictName","charges_type")
                .addParam("page",0).addParam("size",9999).get()
                .enqueue(new Callback<List<CoreType>>() {
                    @Override
                    public void success(final List<CoreType> data) {
                        dismissProgressDialog();
                        final CommonTypeDialog<CoreType> dialog = new CommonTypeDialog<>(context,data);
                        dialog.showDialog("请选择原罪名", new CommonAdapter<CoreType>(context, data, R.layout.alen_type_item) {
                            @Override
                            public void convert(ViewHolder holder, CoreType entity, int position) {
                                holder.setText(R.id.name_tv,entity.getLabel());
                            }
                        }, new CommonAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, ViewHolder holder, int position) {
                                dialog.dismiss();
                                yzm = data.get(position).getValue();
                                xmsfPersonYzm.setText(data.get(position).getLabel());
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
    private String sflf;
    private void sflf(){
        showProgressDialog("");
        Https.with(this).url(BaseApi.getType).addParam("dictName","system_trueorfalse")
                .addParam("page",0).addParam("size",9999).get()
                .enqueue(new Callback<List<CoreType>>() {
                    @Override
                    public void success(final List<CoreType> data) {
                        dismissProgressDialog();
                        final CommonTypeDialog<CoreType> dialog = new CommonTypeDialog<>(context,data);
                        dialog.showDialog("请选择是否累犯", new CommonAdapter<CoreType>(context, data, R.layout.alen_type_item) {
                            @Override
                            public void convert(ViewHolder holder, CoreType entity, int position) {
                                holder.setText(R.id.name_tv,entity.getLabel());
                            }
                        }, new CommonAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, ViewHolder holder, int position) {
                                dialog.dismiss();
                                sflf = data.get(position).getValue();
                                xmsfPersonSflf.setText(data.get(position).getLabel());
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
    private String xzqk;
    private void xzqk(){
        showProgressDialog("");
        Https.with(this).url(BaseApi.getType).addParam("dictName","goes_on")
                .addParam("page",0).addParam("size",9999).get()
                .enqueue(new Callback<List<CoreType>>() {
                    @Override
                    public void success(final List<CoreType> data) {
                        dismissProgressDialog();
                        final CommonTypeDialog<CoreType> dialog = new CommonTypeDialog<>(context,data);
                        dialog.showDialog("请选择衔接情况", new CommonAdapter<CoreType>(context, data, R.layout.alen_type_item) {
                            @Override
                            public void convert(ViewHolder holder, CoreType entity, int position) {
                                holder.setText(R.id.name_tv,entity.getLabel());
                            }
                        }, new CommonAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, ViewHolder holder, int position) {
                                dialog.dismiss();
                                xzqk = data.get(position).getValue();
                                xmsfPersonXjqk.setText(data.get(position).getLabel());
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
    private String azqk;
    private void azqk(){
        showProgressDialog("");
        Https.with(this).url(BaseApi.getType).addParam("dictName","resettlement_situation")
                .addParam("page",0).addParam("size",9999).get()
                .enqueue(new Callback<List<CoreType>>() {
                    @Override
                    public void success(final List<CoreType> data) {
                        dismissProgressDialog();
                        final CommonTypeDialog<CoreType> dialog = new CommonTypeDialog<>(context,data);
                        dialog.showDialog("请选择安置情况", new CommonAdapter<CoreType>(context, data, R.layout.alen_type_item) {
                            @Override
                            public void convert(ViewHolder holder, CoreType entity, int position) {
                                holder.setText(R.id.name_tv,entity.getLabel());
                            }
                        }, new CommonAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, ViewHolder holder, int position) {
                                dialog.dismiss();
                                azqk = data.get(position).getValue();
                                xmsfPersonAzqk.setText(data.get(position).getLabel());
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
    private String cfzm;
    private void cfzm(){
        showProgressDialog("");
        Https.with(this).url(BaseApi.getType).addParam("dictName","charges_type")
                .addParam("page",0).addParam("size",9999).get()
                .enqueue(new Callback<List<CoreType>>() {
                    @Override
                    public void success(final List<CoreType> data) {
                        dismissProgressDialog();
                        final CommonTypeDialog<CoreType> dialog = new CommonTypeDialog<>(context,data);
                        dialog.showDialog("请选择重新犯罪罪名", new CommonAdapter<CoreType>(context, data, R.layout.alen_type_item) {
                            @Override
                            public void convert(ViewHolder holder, CoreType entity, int position) {
                                holder.setText(R.id.name_tv,entity.getLabel());
                            }
                        }, new CommonAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, ViewHolder holder, int position) {
                                dialog.dismiss();
                                cfzm = data.get(position).getValue();
                                xmsfPersonCxfzzm.setText(data.get(position).getLabel());
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
