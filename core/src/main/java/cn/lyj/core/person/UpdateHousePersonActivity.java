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
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.lyj.core.R;
import cn.lyj.core.R2;
import cn.lyj.core.api.CoreApi;
import cn.lyj.core.entry.HousePerson;

public class UpdateHousePersonActivity extends AllenBaseActivity {
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
    @BindView(R2.id.house_person_rhyz)
    AppCompatTextView housePersonRhyz;
    @BindView(R2.id.house_person_hh)
    AppCompatEditText housePersonHh;
    @BindView(R2.id.house_person_hzsfz)
    AppCompatEditText housePersonHzsfz;
    @BindView(R2.id.house_person_hzname)
    AppCompatEditText housePersonHzname;
    @BindView(R2.id.house_person_hzgx)
    AppCompatTextView housePersonHzgx;
    @BindView(R2.id.house_person_hzphone)
    AppCompatEditText housePersonHzphone;
    @BindView(R2.id.commit_bt)
    AppCompatButton commitBt;
    @BindView(R2.id.person_photo)
    AppCompatImageView personPhoto;
    private HousePerson entry;
    private String gid, sex, mz, hyzk, zzmm, xl, zjxy, zylb, hjd, xzd, rhyz, hzgx;
    private UploadProgressDialog dialog;
    private String photo;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.core_houseperson_update_layout;
    }

    @Override
    protected void initBar() {
        entry = (HousePerson) getIntent().getSerializableExtra(Constants.ObjectFirst);
        setToolbarTitle(toolbar, entry == null ? "添加户籍人口" : "编辑户籍人口", true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
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
        if (entry != null) {
            photo = entry.getPicture_path();
            Glide.with(this).load(Constants.url+photo)
                    .error(R.mipmap.core_default_photo)
                    .placeholder(R.mipmap.core_default_photo)
                    .into(personPhoto);
            gid = entry.getGid();
            sex = entry.getB1204();
            mz = entry.getB1206();
            hyzk = entry.getB1208();
            zzmm = entry.getB1209();
            housePersonWg.setText(entry.getGidObj().getGenericName());
            housePersonName.setText(entry.getB1202());
            housePersonAname.setText(entry.getB1203());
            housePersonIdnumber.setText(entry.getB1201());
            housePersonSex.setText(entry.getB1204Name());
            housePersonBirthday.setText(entry.getB1205());
            housePersonJg.setText(entry.getB1207());
            housePersonMz.setText(entry.getB1206());
            housePersonHyzk.setText(entry.getB1208());
            housePersonZzmm.setText(entry.getB1209Obj().getCodeName());
            xl = entry.getB1210();
            housePersonXl.setText(entry.getB1210Obj().getCodeName());
            zjxy = entry.getB1211();
            housePersonZjxy.setText(entry.getB1211());
            zylb = entry.getB1212();
            housePersonZylb.setText(entry.getB1212());
            housePersonZy.setText(entry.getB1213());
            housePersonFwcs.setText(entry.getB1214());
            housePersonPhone.setText(entry.getB1215());
            hjd = entry.getB1216();
            housePersonHjd.setText(entry.getB1216Obj().getCodeName());
            housePersonHjddz.setText(entry.getB1217());
            xzd = entry.getB1218();
            housePersonXzd.setText(entry.getB1218Obj().getCodeName());
            housePersonXzddz.setText(entry.getB1219());
            rhyz = entry.getB1220();
            housePersonRhyz.setText(entry.getB1220Name());
            housePersonHh.setText(entry.getB1221());
            housePersonHzsfz.setText(entry.getB1222());
            housePersonHzname.setText(entry.getB1223());
            hzgx = entry.getB1224();
            housePersonHzgx.setText(entry.getB1224Obj().getCodeName());
            housePersonHzphone.setText(entry.getB1225());
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

    @OnClick({R2.id.house_person_wg, R2.id.house_person_sex, R2.id.house_person_mz, R2.id.house_person_hyzk, R2.id.house_person_zzmm,
            R2.id.house_person_xl, R2.id.house_person_zjxy, R2.id.house_person_zylb, R2.id.house_person_rhyz, R2.id.house_person_hzgx,
            R2.id.house_person_hjd, R2.id.house_person_xzd, R2.id.house_person_birthday, R2.id.commit_bt, R2.id.person_photo})
    public void onViewClicked(View view) {
        view.setEnabled(false);
        int id = view.getId();
        if (id == R.id.house_person_wg) {
            startActivityForResult(new Intent(context, AllenChoiceGridActivity.class), 10);
        } else if (id == R.id.house_person_sex) {
            sex();
        } else if (id == R.id.house_person_mz) {
            mz();
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
        } else if (id == R.id.house_person_rhyz) {
            rhyz();
        } else if (id == R.id.house_person_hzgx) {
            gx();
        } else if (id == R.id.house_person_hjd) {
            hjd();
        } else if (id == R.id.house_person_xzd) {
            xzd();
        } else if (id == R.id.house_person_birthday) {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear, int startDayOfMonth) {
                    housePersonBirthday.setText(startYear + "-" + String.format("%02d", startMonthOfYear + 1) + "-" + String.format("%02d", startDayOfMonth));
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        } else if (id == R.id.commit_bt) {
            commit();
        } else if(id==R.id.person_photo){
            requestRunPermisssion(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10, new PermissionListener() {
                @Override
                public void onGranted(int requestCode) {
                    MultiImageSelector.create().single().showCamera(true)
                            .start(UpdateHousePersonActivity.this,11);
                }

                @Override
                public void onDenied(List<String> deniedPermission) {
                    MsgUtils.showMDMessage(context,"请开通需要的权限!");
                }
            });
        }
        view.setEnabled(true);
    }

    private void commit() {
        String idno = housePersonIdnumber.getText().toString().trim();
        if (StringUtils.empty(idno)) {
            MsgUtils.showMDMessage(context, "请输入身份证号码");
            return;
        }
        String name = housePersonName.getText().toString().trim();
        String aname = housePersonAname.getText().toString().trim();
        if (StringUtils.empty(name)) {
            MsgUtils.showMDMessage(context, "请输入姓名");
            return;
        }
        if (StringUtils.empty(sex)) {
            MsgUtils.showMDMessage(context, "请选择性别");
            return;
        }
        String birth = housePersonBirthday.getText().toString().trim();
        if (StringUtils.empty(birth)) {
            MsgUtils.showMDMessage(context, "请选择出生日期");
            return;
        }
        String jg = housePersonIdnumber.getText().toString().trim();
        if (StringUtils.empty(jg)) {
            MsgUtils.showMDMessage(context, "请输入籍贯");
            return;
        }
        if (StringUtils.empty(mz)) {
            MsgUtils.showMDMessage(context, "请选择民族");
            return;
        }
        if (StringUtils.empty(hyzk)) {
            MsgUtils.showMDMessage(context, "请选择婚姻状况");
            return;
        }
        if (StringUtils.empty(zzmm)) {
            MsgUtils.showMDMessage(context, "请选择政治面貌");
            return;
        }
        if (StringUtils.empty(xl)) {
            MsgUtils.showMDMessage(context, "请选择学历");
            return;
        }
        if (StringUtils.empty(xl)) {
            MsgUtils.showMDMessage(context, "请选择学历");
            return;
        }
        if (StringUtils.empty(zylb)) {
            MsgUtils.showMDMessage(context, "请选择职业类别");
            return;
        }
        String zy = housePersonZy.getText().toString().trim();
        if (StringUtils.empty(zy)) {
            MsgUtils.showMDMessage(context, "请输入职业");
            return;
        }
        String fwcs = housePersonFwcs.getText().toString().trim();
        String phone = housePersonPhone.getText().toString().trim();
        if (StringUtils.empty(phone)) {
            MsgUtils.showMDMessage(context, "请输入联系方式");
            return;
        }
        if (StringUtils.empty(hjd)) {
            MsgUtils.showMDMessage(context, "请选择户籍地");
            return;
        }
        String hjddz = housePersonHjddz.getText().toString().trim();
        if (StringUtils.empty(hjddz)) {
            MsgUtils.showMDMessage(context, "请输入户籍门（楼）地址");
            return;
        }
        if (StringUtils.empty(xzd)) {
            MsgUtils.showMDMessage(context, "请选择现住地");
            return;
        }
        String xzddz = housePersonXzddz.getText().toString().trim();
        if (StringUtils.empty(xzddz)) {
            MsgUtils.showMDMessage(context, "请输入现住门（楼）地址");
            return;
        }
        if (StringUtils.empty(rhyz)) {
            MsgUtils.showMDMessage(context, "请选择人户是否一致");
            return;
        }
        String hh = housePersonHh.getText().toString().trim();
        if (StringUtils.empty(hh)) {
            MsgUtils.showMDMessage(context, "请输入户号");
            return;
        }
        String hzno = housePersonHzsfz.getText().toString().trim();
        String hzname = housePersonHzname.getText().toString().trim();
        String hzgx = housePersonHzgx.getText().toString().trim();
        if (StringUtils.empty(hzgx)) {
            MsgUtils.showMDMessage(context, "请选择与户主关系");
            return;
        }
        String hzphone = housePersonHzphone.getText().toString().trim();
        if (StringUtils.empty(gid)) {
            MsgUtils.showMDMessage(context, "请选择所在网格");
            return;
        }
        showProgressDialog("");
        Https https = Https.with(this);
        if (entry == null) {
            https.url(CoreApi.HousePersonAdd).post();
        } else {
            https.url(CoreApi.HousePersonUpdate).addParam("b1200", entry.getB1200()).put();
        }
        https.addParam("b1204", sex).addParam("b1206", mz).addParam("b1208", hyzk).addParam("b1209", zzmm)
                .addParam("b1210", xl).addParam("b1211", zjxy).addParam("b1212", zylb).addParam("b1216", hjd)
                .addParam("b1218", xzd).addParam("b1220", rhyz).addParam("b1224", hzgx).addParam("gid", gid)
                .addParam("b1201", idno).addParam("b1202", name).addParam("b1203", aname).addParam("b1205", birth)
                .addParam("b1207", jg).addParam("b1213", zy).addParam("b1214", fwcs).addParam("b1215", phone)
                .addParam("b1217", hjddz).addParam("b1219", xzddz).addParam("b1221", hh).addParam("b1222", hzno)
                .addParam("b1223", hzname).addParam("b1225", hzphone).addParam("picture_path",photo)
                .enqueue(new Callback<Object>() {
                    @Override
                    public void success(Object data) {
                        dismissProgressDialog();
                        MsgUtils.showShortToast(context, "保存成功!");
                        setResult(RESULT_OK, getIntent());
                        finish();
                    }

                    @Override
                    public void fail(Response response) {
                        dismissProgressDialog();
                        MsgUtils.showMDMessage(context, response.getMsg());
                    }
                });
    }

    private void sex() {
        showProgressDialog("");
        Https.with(this).url(BaseApi.getType).addParam("dictName", "people_sex")
                .addParam("page", 0).addParam("size", 9999).get()
                .enqueue(new Callback<List<CoreType>>() {
                    @Override
                    public void success(final List<CoreType> data) {
                        dismissProgressDialog();
                        final CommonTypeDialog<CoreType> dialog = new CommonTypeDialog<>(context, data);
                        dialog.showDialog("请选择性别", new CommonAdapter<CoreType>(context, data, R.layout.alen_type_item) {
                            @Override
                            public void convert(ViewHolder holder, CoreType entity, int position) {
                                holder.setText(R.id.name_tv, entity.getLabel());
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
                        MsgUtils.showMDMessage(context, response.getMsg());
                    }
                });
    }

    private void mz() {
        showProgressDialog("");
        Https.with(this).url(BaseApi.getType).addParam("dictName", "people_nation")
                .addParam("page", 0).addParam("size", 9999).get()
                .enqueue(new Callback<List<CoreType>>() {
                    @Override
                    public void success(final List<CoreType> data) {
                        dismissProgressDialog();
                        final CommonTypeDialog<CoreType> dialog = new CommonTypeDialog<>(context, data);
                        dialog.showDialog("请选择民族", new CommonAdapter<CoreType>(context, data, R.layout.alen_type_item) {
                            @Override
                            public void convert(ViewHolder holder, CoreType entity, int position) {
                                holder.setText(R.id.name_tv, entity.getLabel());
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
                        MsgUtils.showMDMessage(context, response.getMsg());
                    }
                });
    }

    private void hyzk() {
        showProgressDialog("");
        Https.with(this).url(BaseApi.getType).addParam("dictName", "people_marriage")
                .addParam("page", 0).addParam("size", 9999).get()
                .enqueue(new Callback<List<CoreType>>() {
                    @Override
                    public void success(final List<CoreType> data) {
                        dismissProgressDialog();
                        final CommonTypeDialog<CoreType> dialog = new CommonTypeDialog<>(context, data);
                        dialog.showDialog("请选择婚姻状况", new CommonAdapter<CoreType>(context, data, R.layout.alen_type_item) {
                            @Override
                            public void convert(ViewHolder holder, CoreType entity, int position) {
                                holder.setText(R.id.name_tv, entity.getLabel());
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
                        MsgUtils.showMDMessage(context, response.getMsg());
                    }
                });
    }

    private void zjxy() {
        showProgressDialog("");
        Https.with(this).url(BaseApi.getType).addParam("dictName", "people_religion")
                .addParam("page", 0).addParam("size", 9999).get()
                .enqueue(new Callback<List<CoreType>>() {
                    @Override
                    public void success(final List<CoreType> data) {
                        dismissProgressDialog();
                        final CommonTypeDialog<CoreType> dialog = new CommonTypeDialog<>(context, data);
                        dialog.showDialog("请选择宗教信仰", new CommonAdapter<CoreType>(context, data, R.layout.alen_type_item) {
                            @Override
                            public void convert(ViewHolder holder, CoreType entity, int position) {
                                holder.setText(R.id.name_tv, entity.getLabel());
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
                        MsgUtils.showMDMessage(context, response.getMsg());
                    }
                });
    }

    private void zylb() {
        showProgressDialog("");
        Https.with(this).url(BaseApi.getType).addParam("dictName", "people_worktype")
                .addParam("page", 0).addParam("size", 9999).get()
                .enqueue(new Callback<List<CoreType>>() {
                    @Override
                    public void success(final List<CoreType> data) {
                        dismissProgressDialog();
                        final CommonTypeDialog<CoreType> dialog = new CommonTypeDialog<>(context, data);
                        dialog.showDialog("请选择职业类别", new CommonAdapter<CoreType>(context, data, R.layout.alen_type_item) {
                            @Override
                            public void convert(ViewHolder holder, CoreType entity, int position) {
                                holder.setText(R.id.name_tv, entity.getLabel());
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
                        MsgUtils.showMDMessage(context, response.getMsg());
                    }
                });
    }

    private void rhyz() {
        showProgressDialog("");
        Https.with(this).url(BaseApi.getType).addParam("dictName", "person_accordance")
                .addParam("page", 0).addParam("size", 9999).get()
                .enqueue(new Callback<List<CoreType>>() {
                    @Override
                    public void success(final List<CoreType> data) {
                        dismissProgressDialog();
                        final CommonTypeDialog<CoreType> dialog = new CommonTypeDialog<>(context, data);
                        dialog.showDialog("请选择人户是否一致", new CommonAdapter<CoreType>(context, data, R.layout.alen_type_item) {
                            @Override
                            public void convert(ViewHolder holder, CoreType entity, int position) {
                                holder.setText(R.id.name_tv, entity.getLabel());
                            }
                        }, new CommonAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, ViewHolder holder, int position) {
                                dialog.dismiss();
                                rhyz = data.get(position).getValue();
                                housePersonRhyz.setText(data.get(position).getLabel());
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
                        MsgUtils.showMDMessage(context, response.getMsg());
                    }
                });
    }

    private void zzmm() {
        showProgressDialog("");
        Https.with(this).url(BaseApi.getTable).addParam("name", "gb4762").get()
                .enqueue(new Callback<List<CoreType>>() {
                    @Override
                    public void success(final List<CoreType> data) {
                        dismissProgressDialog();
                        final CommonTypeDialog<CoreType> dialog = new CommonTypeDialog<>(context, data);
                        dialog.showDialog("请选择政治面貌", new CommonAdapter<CoreType>(context, data, R.layout.alen_type_item) {
                            @Override
                            public void convert(ViewHolder holder, CoreType entity, int position) {
                                holder.setText(R.id.name_tv, entity.getLabel());
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
                        MsgUtils.showMDMessage(context, response.getMsg());
                    }
                });
    }

    private void xl() {
        showProgressDialog("");
        Https.with(this).url(BaseApi.getTable).addParam("name", "zb64").get()
                .enqueue(new Callback<List<CoreType>>() {
                    @Override
                    public void success(final List<CoreType> data) {
                        dismissProgressDialog();
                        final CommonTypeDialog<CoreType> dialog = new CommonTypeDialog<>(context, data);
                        dialog.showDialog("请选择学历", new CommonAdapter<CoreType>(context, data, R.layout.alen_type_item) {
                            @Override
                            public void convert(ViewHolder holder, CoreType entity, int position) {
                                holder.setText(R.id.name_tv, entity.getLabel());
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
                        MsgUtils.showMDMessage(context, response.getMsg());
                    }
                });
    }

    private void gx() {
        showProgressDialog("");
        Https.with(this).url(BaseApi.getTable).addParam("name", "gb4761").get()
                .enqueue(new Callback<List<CoreType>>() {
                    @Override
                    public void success(final List<CoreType> data) {
                        dismissProgressDialog();
                        final CommonTypeDialog<CoreType> dialog = new CommonTypeDialog<>(context, data);
                        dialog.showDialog("请选择与户主关系", new CommonAdapter<CoreType>(context, data, R.layout.alen_type_item) {
                            @Override
                            public void convert(ViewHolder holder, CoreType entity, int position) {
                                holder.setText(R.id.name_tv, entity.getLabel());
                            }
                        }, new CommonAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, ViewHolder holder, int position) {
                                dialog.dismiss();
                                hzgx = data.get(position).getValue();
                                housePersonHzgx.setText(data.get(position).getLabel());
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
                        MsgUtils.showMDMessage(context, response.getMsg());
                    }
                });
    }

    private List<CoreType> coreTypes;

    private void hjd() {
        if (coreTypes != null) {
            final CommonTypeDialog<CoreType> dialog = new CommonTypeDialog<>(context);
            dialog.setCoreTypes(coreTypes);
            dialog.showLevelDialog("请选择户籍地", new CoreTypeAdapter(context),
                    new CoreTypeAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View v, CoreType entry, int position) {
                            final List<CoreType> child = entry.getChildren();
                            if (child == null || child.size() == 0) {
                                dialog.dismiss();
                                hjd = entry.getValue();
                                housePersonHjd.setText(entry.getLabel());
                            } else {
                                dialog.setNextData(new CoreTypeAdapter(context, child), new CoreTypeAdapter.OnItemClickListener() {
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
        } else {
            showProgressDialog("");
            Https.with(this).url(BaseApi.getTable).addParam("name", "zb01").get()
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
                                            if (child == null || child.size() == 0) {
                                                dialog.dismiss();
                                                hjd = mentry.getValue();
                                                housePersonHjd.setText(mentry.getLabel());
                                            } else {
                                                dialog.setNextData(new CoreTypeAdapter(context, child), new CoreTypeAdapter.OnItemClickListener() {
                                                    @Override
                                                    public void onItemClick(View v, CoreType entry, int position) {
                                                        dialog.dismiss();
                                                        hjd = entry.getValue();
                                                        housePersonHjd.setText(mentry.getLabel() + entry.getLabel());
                                                    }
                                                });
                                            }
                                        }
                                    }).show();
                        }

                        @Override
                        public void fail(Response response) {
                            dismissProgressDialog();
                            MsgUtils.showMDMessage(context, response.getMsg());
                        }
                    });
        }
    }

    private void xzd() {
        if (coreTypes != null) {
            final CommonTypeDialog<CoreType> dialog = new CommonTypeDialog<>(context);
            dialog.setCoreTypes(coreTypes);
            dialog.showLevelDialog("请选择现住地", new CoreTypeAdapter(context),
                    new CoreTypeAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View v, final CoreType mentry, int position) {
                            final List<CoreType> child = mentry.getChildren();
                            if (child == null || child.size() == 0) {
                                dialog.dismiss();
                                xzd = mentry.getValue();
                                housePersonXzd.setText(mentry.getLabel());
                            } else {
                                dialog.setNextData(new CoreTypeAdapter(context, child), new CoreTypeAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View v, CoreType entry, int position) {
                                        dialog.dismiss();
                                        xzd = entry.getValue();
                                        housePersonXzd.setText(mentry.getLabel() + entry.getLabel());
                                    }
                                });
                            }
                        }
                    }).show();
        } else {
            showProgressDialog("");
            Https.with(this).url(BaseApi.getTable).addParam("name", "zb01").get()
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
                                            if (child == null || child.size() == 0) {
                                                dialog.dismiss();
                                                xzd = mentry.getValue();
                                                housePersonXzd.setText(mentry.getLabel());
                                            } else {
                                                dialog.setNextData(new CoreTypeAdapter(context, child), new CoreTypeAdapter.OnItemClickListener() {
                                                    @Override
                                                    public void onItemClick(View v, CoreType entry, int position) {
                                                        dialog.dismiss();
                                                        xzd = entry.getValue();
                                                        housePersonXzd.setText(mentry.getLabel() + entry.getLabel());
                                                    }
                                                });
                                            }
                                        }
                                    }).show();
                        }

                        @Override
                        public void fail(Response response) {
                            dismissProgressDialog();
                            MsgUtils.showMDMessage(context, response.getMsg());
                        }
                    });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
