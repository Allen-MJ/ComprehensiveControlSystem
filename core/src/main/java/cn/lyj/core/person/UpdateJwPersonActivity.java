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
import cn.lyj.core.entry.JwPersonEntity;

public class UpdateJwPersonActivity extends AllenBaseActivity {
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.house_person_wg)
    AppCompatTextView housePersonWg;
    @BindView(R2.id.jw_ww_xing)
    AppCompatEditText jwWwXing;
    @BindView(R2.id.jw_ww_ming)
    AppCompatEditText jwWwMing;
    @BindView(R2.id.jw_zw_name)
    AppCompatEditText jwZwName;
    @BindView(R2.id.jw_sex)
    AppCompatTextView jwSex;
    @BindView(R2.id.jw_birthday)
    AppCompatTextView jwBirthday;
    @BindView(R2.id.jw_gjdq)
    AppCompatTextView jwGjdq;
    @BindView(R2.id.jw_zjxy)
    AppCompatTextView jwZjxy;
    @BindView(R2.id.jw_zjdm)
    AppCompatTextView jwZjdm;
    @BindView(R2.id.jw_zjhm)
    AppCompatEditText jwZjhm;
    @BindView(R2.id.jw_zjyxq)
    AppCompatTextView jwZjyxq;
    @BindView(R2.id.jw_phone)
    AppCompatEditText jwPhone;
    @BindView(R2.id.jw_lhmd)
    AppCompatTextView jwLhmd;
    @BindView(R2.id.jw_zylb)
    AppCompatTextView jwZylb;
    @BindView(R2.id.jw_zys)
    AppCompatEditText jwZys;
    @BindView(R2.id.jw_fwcs)
    AppCompatEditText jwFwcs;
    @BindView(R2.id.house_person_xzd)
    AppCompatTextView housePersonXzd;
    @BindView(R2.id.house_person_xzddz)
    AppCompatEditText housePersonXzddz;
    @BindView(R2.id.jw_ddrq)
    AppCompatTextView jwDdrq;
    @BindView(R2.id.jw_yjlk)
    AppCompatTextView jwYjlk;
    @BindView(R2.id.house_person_sfzd)
    AppCompatTextView housePersonSfzd;
    @BindView(R2.id.commit_bt)
    AppCompatButton commitBt;
    @BindView(R2.id.person_photo)
    AppCompatImageView personPhoto;
    private JwPersonEntity entity;
    private String gid,sex,xzd,zdry,zjxy,zjdm,zylb,lhmd,gjdq;
    private UploadProgressDialog dialog;
    private String photo;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.core_update_jwperson_layout;
    }

    @Override
    protected void initBar() {
        entity = (JwPersonEntity) getIntent().getSerializableExtra(Constants.ObjectFirst);
        setToolbarTitle(toolbar, entity == null ? "??????????????????" : "??????????????????", true);
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
                    file.setType(0);//??????
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
        if(entity!=null){
            photo = entity.getPicturePath();
            Glide.with(this).load(Constants.url+photo)
                    .error(R.mipmap.core_default_photo)
                    .placeholder(R.mipmap.core_default_photo)
                    .into(personPhoto);
            jwWwXing.setText(entity.getB1501());
            jwWwMing.setText(entity.getB1502());
            jwZwName.setText(entity.getB1503());
            jwSex.setText(entity.getB1504Name());
            sex = entity.getB1504();
            jwBirthday.setText(entity.getB1505());
            jwGjdq.setText(entity.getB1506Obj().getCodeName());
            gjdq = entity.getB1506();
            jwZjxy.setText(entity.getB1507());
            zjxy = entity.getB1507();
            jwZjdm.setText(entity.getB1508());
            zjdm = entity.getB1508();
            jwZjhm.setText(entity.getB1509());
            jwZjyxq.setText(entity.getB1510());
            jwPhone.setText(entity.getB1511());
            jwLhmd.setText(entity.getB1512());
            lhmd = entity.getB1512();
            jwZylb.setText(entity.getB1513());
            zylb = entity.getB1513();
            jwZys.setText(entity.getB1514());
            jwFwcs.setText(entity.getB1515());
            housePersonXzd.setText(entity.getB1516Obj().getCodeName());
            xzd = entity.getB1516();
            housePersonXzddz.setText(entity.getB1517());
            jwDdrq.setText(entity.getB1518());
            jwYjlk.setText(entity.getB1519());
            housePersonSfzd.setText(entity.getB1520Name());
            zdry = String.valueOf(entity.getB1520());
            housePersonWg.setText(entity.getGidObj().getGenericName());
            gid = entity.getGid();
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
                MsgUtils.showShortToast(context, "????????????!");
            }
        });
    }

    @OnClick({R2.id.house_person_wg, R2.id.jw_sex, R2.id.jw_birthday, R2.id.jw_gjdq, R2.id.jw_lhmd, R2.id.jw_zjxy, R2.id.jw_zjdm,
            R2.id.jw_zjyxq, R2.id.jw_zylb, R2.id.house_person_xzd, R2.id.jw_ddrq,
            R2.id.jw_yjlk, R2.id.house_person_sfzd, R2.id.commit_bt, R2.id.person_photo})
    public void onViewClicked(View view) {
        view.setEnabled(false);
        int id = view.getId();
        if(id == R.id.house_person_wg){
            startActivityForResult(new Intent(context, AllenChoiceGridActivity.class),10);
        }else if(id == R.id.jw_sex){
            sex();
        }else if(id == R.id.jw_birthday){
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear, int startDayOfMonth) {
                    jwBirthday.setText(startYear + "-" + String.format("%02d", startMonthOfYear + 1) + "-" + String.format("%02d", startDayOfMonth));
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        }else if(id == R.id.jw_gjdq){
            gjdq();
        }else if(id == R.id.jw_lhmd){
            lhmd();
        }else if(id == R.id.jw_zjxy){
            zjxy();
        }else if(id == R.id.jw_zjdm){
            zjdm();
        }else if(id == R.id.jw_zjyxq){
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear, int startDayOfMonth) {
                    jwZjyxq.setText(startYear + "-" + String.format("%02d", startMonthOfYear + 1) + "-" + String.format("%02d", startDayOfMonth));
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        }else if(id == R.id.jw_zylb){
            zylb();
        }else if(id == R.id.house_person_xzd){
            xzd();
        }else if(id == R.id.jw_ddrq){
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear, int startDayOfMonth) {
                    jwDdrq.setText(startYear + "-" + String.format("%02d", startMonthOfYear + 1) + "-" + String.format("%02d", startDayOfMonth));
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        }else if(id == R.id.jw_yjlk){
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear, int startDayOfMonth) {
                    jwYjlk.setText(startYear + "-" + String.format("%02d", startMonthOfYear + 1) + "-" + String.format("%02d", startDayOfMonth));
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        }else if(id == R.id.house_person_sfzd){
            zdry();
        }else if(id == R.id.commit_bt){
            commit();
        } else if(id==R.id.person_photo){
            requestRunPermisssion(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10, new PermissionListener() {
                @Override
                public void onGranted(int requestCode) {
                    MultiImageSelector.create().single().showCamera(true)
                            .start(UpdateJwPersonActivity.this,11);
                }

                @Override
                public void onDenied(List<String> deniedPermission) {
                    MsgUtils.showMDMessage(context,"????????????????????????!");
                }
            });
        }
        view.setEnabled(true);
    }

    private void commit(){
        String xing = jwWwXing.getText().toString().trim();
        if(StringUtils.empty(xing)){
            MsgUtils.showMDMessage(context,"??????????????????!");
            return;
        }
        String ming = jwWwMing.getText().toString().trim();
        if(StringUtils.empty(ming)){
            MsgUtils.showMDMessage(context,"??????????????????!");
            return;
        }
        String name = jwZwName.getText().toString().trim();
        String birth = jwBirthday.getText().toString().trim();
        if(StringUtils.empty(birth)){
            MsgUtils.showMDMessage(context,"?????????????????????!");
            return;
        }
        if(StringUtils.empty(gid)){
            MsgUtils.showMDMessage(context,"?????????????????????!");
            return;
        }
        if(StringUtils.empty(gjdq)){
            MsgUtils.showMDMessage(context,"???????????????????????????!");
            return;
        }
        if(StringUtils.empty(zjxy)){
            MsgUtils.showMDMessage(context,"?????????????????????!");
            return;
        }
        if(StringUtils.empty(zjdm)){
            MsgUtils.showMDMessage(context,"?????????????????????!");
            return;
        }
        String zjhm = jwZjhm.getText().toString().trim();
        if(StringUtils.empty(zjhm)){
            MsgUtils.showMDMessage(context,"?????????????????????!");
            return;
        }
        String zjyxq = jwZjyxq.getText().toString().trim();
        if(StringUtils.empty(zjyxq)){
            MsgUtils.showMDMessage(context,"????????????????????????!");
            return;
        }
        String phone = jwPhone.getText().toString().trim();
        if(StringUtils.empty(phone)){
            MsgUtils.showMDMessage(context,"?????????????????????!");
            return;
        }

        if(StringUtils.empty(lhmd)){
            MsgUtils.showMDMessage(context,"?????????????????????!");
            return;
        }
        if(StringUtils.empty(zylb)){
            MsgUtils.showMDMessage(context,"?????????????????????!");
            return;
        }
        String zy = jwZys.getText().toString().trim();
        String fwcs = jwFwcs.getText().toString().trim();
        if(StringUtils.empty(xzd)){
            MsgUtils.showMDMessage(context,"??????????????????!");
            return;
        }
        String xzddz = housePersonXzddz.getText().toString().trim();
        if(StringUtils.empty(xzddz)){
            MsgUtils.showMDMessage(context,"?????????????????????????????????!");
            return;
        }
        String ddrq = jwDdrq.getText().toString().trim();
        if(StringUtils.empty(ddrq)){
            MsgUtils.showMDMessage(context,"?????????????????????!");
            return;
        }
        String yjlk = jwYjlk.getText().toString().trim();
        if(StringUtils.empty(yjlk)){
            MsgUtils.showMDMessage(context,"???????????????????????????!");
            return;
        }
        if(StringUtils.empty(zdry)){
            MsgUtils.showMDMessage(context,"?????????????????????????????????!");
            return;
        }
        showProgressDialog("");
        Https https = Https.with(this);
        if(entity!=null){
            https.url(CoreApi.update_JwjPerson).put().addParam("b1500",entity.getB1500());
        }else{
            https.url(CoreApi.add_JwjPerson).post();
        }
        https.addParam("b1501",xing).addParam("b1502",ming).addParam("b1503",name).addParam("b1504",sex).addParam("b1505",birth)
                .addParam("b1506",gjdq).addParam("b1507",zjxy)
                .addParam("b1508",zjdm).addParam("b1509",zjhm).addParam("b1510",zjyxq).addParam("b1511",phone).addParam("b1512",lhmd)
                .addParam("b1513",zylb).addParam("b1514",zy)
                .addParam("b1515",fwcs).addParam("b1516",xzd).addParam("b1517",xzddz).addParam("b1518",ddrq).addParam("b1519",yjlk)
                .addParam("b1520",zdry).addParam("gid",gid).addParam("picture_path",photo).enqueue(new Callback<Object>() {
            @Override
            public void success(Object data) {
                dismissProgressDialog();
                MsgUtils.showShortToast(context,"????????????!");
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

    private void gjdq(){
        showProgressDialog("");
        Https.with(this).url(BaseApi.getTable).addParam("name","zb05").get()
                .enqueue(new Callback<List<CoreType>>() {
                    @Override
                    public void success(final List<CoreType> data) {
                        dismissProgressDialog();
                        final CommonTypeDialog<CoreType> dialog = new CommonTypeDialog<>(context,data);
                        dialog.showDialog("???????????????????????????", new CommonAdapter<CoreType>(context, data, R.layout.alen_type_item) {
                            @Override
                            public void convert(ViewHolder holder, CoreType entity, int position) {
                                holder.setText(R.id.name_tv,entity.getLabel());
                            }
                        }, new CommonAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, ViewHolder holder, int position) {
                                dialog.dismiss();
                                gjdq = data.get(position).getValue();
                                jwGjdq.setText(data.get(position).getLabel());
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
    private void lhmd(){
        showProgressDialog("");
        Https.with(this).url(BaseApi.getType).addParam("dictName","come_china_purpose")
                .addParam("page",0).addParam("size",9999).get()
                .enqueue(new Callback<List<CoreType>>() {
                    @Override
                    public void success(final List<CoreType> data) {
                        dismissProgressDialog();
                        final CommonTypeDialog<CoreType> dialog = new CommonTypeDialog<>(context,data);
                        dialog.showDialog("?????????????????????", new CommonAdapter<CoreType>(context, data, R.layout.alen_type_item) {
                            @Override
                            public void convert(ViewHolder holder, CoreType entity, int position) {
                                holder.setText(R.id.name_tv,entity.getLabel());
                            }
                        }, new CommonAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, ViewHolder holder, int position) {
                                dialog.dismiss();
                                lhmd = data.get(position).getValue();
                                jwLhmd.setText(data.get(position).getLabel());
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
                        dialog.showDialog("?????????????????????", new CommonAdapter<CoreType>(context, data, R.layout.alen_type_item) {
                            @Override
                            public void convert(ViewHolder holder, CoreType entity, int position) {
                                holder.setText(R.id.name_tv,entity.getLabel());
                            }
                        }, new CommonAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, ViewHolder holder, int position) {
                                dialog.dismiss();
                                zylb = data.get(position).getValue();
                                jwZylb.setText(data.get(position).getLabel());
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
    private void zjdm(){
        showProgressDialog("");
        Https.with(this).url(BaseApi.getType).addParam("dictName","papers_code")
                .addParam("page",0).addParam("size",9999).get()
                .enqueue(new Callback<List<CoreType>>() {
                    @Override
                    public void success(final List<CoreType> data) {
                        dismissProgressDialog();
                        final CommonTypeDialog<CoreType> dialog = new CommonTypeDialog<>(context,data);
                        dialog.showDialog("?????????????????????", new CommonAdapter<CoreType>(context, data, R.layout.alen_type_item) {
                            @Override
                            public void convert(ViewHolder holder, CoreType entity, int position) {
                                holder.setText(R.id.name_tv,entity.getLabel());
                            }
                        }, new CommonAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, ViewHolder holder, int position) {
                                dialog.dismiss();
                                zjdm = data.get(position).getValue();
                                jwZjdm.setText(data.get(position).getLabel());
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
                        dialog.showDialog("?????????????????????", new CommonAdapter<CoreType>(context, data, R.layout.alen_type_item) {
                            @Override
                            public void convert(ViewHolder holder, CoreType entity, int position) {
                                holder.setText(R.id.name_tv,entity.getLabel());
                            }
                        }, new CommonAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, ViewHolder holder, int position) {
                                dialog.dismiss();
                                zjxy = data.get(position).getValue();
                                jwZjxy.setText(data.get(position).getLabel());
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
                        dialog.showDialog("???????????????", new CommonAdapter<CoreType>(context, data, R.layout.alen_type_item) {
                            @Override
                            public void convert(ViewHolder holder, CoreType entity, int position) {
                                holder.setText(R.id.name_tv,entity.getLabel());
                            }
                        }, new CommonAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, ViewHolder holder, int position) {
                                dialog.dismiss();
                                sex = data.get(position).getValue();
                                jwSex.setText(data.get(position).getLabel());
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
                        dialog.showDialog("??????????????????????????????", new CommonAdapter<CoreType>(context, data, R.layout.alen_type_item) {
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

    private List<CoreType> coreTypes;
    private void xzd(){
        if(coreTypes!=null){
            final CommonTypeDialog<CoreType> dialog = new CommonTypeDialog<>(context);
            dialog.showLevelDialog("??????????????????", coreTypes, new CommonTypeDialog.OnItemClickListener() {
                @Override
                public void itemClick(String name, String code) {
                    dialog.dismiss();
                    xzd = code;
                    housePersonXzd.setText(name);
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
                            dialog.showLevelDialog("??????????????????", coreTypes, new CommonTypeDialog.OnItemClickListener() {
                                @Override
                                public void itemClick(String name, String code) {
                                    dialog.dismiss();
                                    xzd = code;
                                    housePersonXzd.setText(name);
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
