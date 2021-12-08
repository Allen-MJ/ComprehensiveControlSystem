package cn.lyj.core.house;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.List;

import allen.frame.AllenBaseActivity;
import allen.frame.AllenChoiceGridActivity;
import allen.frame.adapter.CommonAdapter;
import allen.frame.adapter.ViewHolder;
import allen.frame.entry.CoreType;
import allen.frame.entry.Response;
import allen.frame.net.BaseApi;
import allen.frame.net.Callback;
import allen.frame.net.Https;
import allen.frame.tools.CommonTypeDialog;
import allen.frame.tools.Constants;
import allen.frame.tools.MsgUtils;
import allen.frame.tools.StringUtils;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.lyj.core.R;
import cn.lyj.core.R2;
import cn.lyj.core.api.CoreApi;
import cn.lyj.core.entry.RentHouse;

public class UpdateRentHouseActivity extends AllenBaseActivity {
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.house_wg)
    AppCompatTextView houseWg;
    @BindView(R2.id.rent_no)
    AppCompatEditText rentNo;
    @BindView(R2.id.rent_address)
    AppCompatEditText rentAddress;
    @BindView(R2.id.rent_yt)
    AppCompatTextView rentYt;
    @BindView(R2.id.rent_jzmj)
    AppCompatEditText rentJzmj;
    @BindView(R2.id.rent_zjdm)
    AppCompatTextView rentZjdm;
    @BindView(R2.id.rent_zjhm)
    AppCompatEditText rentZjhm;
    @BindView(R2.id.rent_hz)
    AppCompatEditText rentHz;
    @BindView(R2.id.rent_hzphone)
    AppCompatEditText rentHzphone;
    @BindView(R2.id.rent_hzaddress)
    AppCompatEditText rentHzaddress;
    @BindView(R2.id.rent_czyt)
    AppCompatTextView rentCzyt;
    @BindView(R2.id.rent_yhlx)
    AppCompatTextView rentYhlx;
    @BindView(R2.id.rent_czrsfz)
    AppCompatEditText rentCzrsfz;
    @BindView(R2.id.rent_czrname)
    AppCompatEditText rentCzrname;
    @BindView(R2.id.rent_czrphone)
    AppCompatEditText rentCzrphone;
    @BindView(R2.id.commit_bt)
    AppCompatButton commitBt;
    private RentHouse entry;
    private String gid,yt,zjdm,czyt,yhlx;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.core_renthouse_update_layout;
    }

    @Override
    protected void initBar() {
        entry = (RentHouse) getIntent().getSerializableExtra(Constants.ObjectFirst);
        setToolbarTitle(toolbar, (entry == null ? "添加" : "编辑") + "出租房", true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case 10:
                    gid = data.getStringExtra(Constants.Key_1);
                    houseWg.setText(data.getStringExtra(Constants.Key_2));
                    break;
            }
        }
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        if (entry != null) {
            houseWg.setText(entry.getGidObj().getOrgFullName());
            rentNo.setText(entry.getB1601());
            rentAddress.setText(entry.getB1602());
            rentYt.setText(entry.getB1603());
            yt = entry.getB1603();
            rentJzmj.setText(entry.getB1604());
            rentZjdm.setText(entry.getB1605());
            zjdm = entry.getB1605();
            rentZjhm.setText(entry.getB1606());
            rentHz.setText(entry.getB1607());
            rentHzphone.setText(entry.getB1608());
            rentHzaddress.setText(entry.getB1609());
            rentCzyt.setText(entry.getB1610());
            czyt = entry.getB1610();
            rentYhlx.setText(entry.getB1611());
            yhlx = entry.getB1611();
            rentCzrsfz.setText(entry.getB1612());
            rentCzrname.setText(entry.getB1613());
            rentCzrphone.setText(entry.getB1614());
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

    @OnClick({R2.id.house_wg, R2.id.rent_yt, R2.id.rent_zjdm, R2.id.rent_czyt, R2.id.rent_yhlx, R2.id.commit_bt})
    public void onViewClicked(View view) {
        view.setEnabled(false);
        int id = view.getId();
        if(id == R.id.house_wg){
            startActivityForResult(new Intent(context, AllenChoiceGridActivity.class),10);
        }else if(id == R.id.rent_yt){
            yt();
        }else if(id == R.id.rent_zjdm){
            zjdm();
        }else if(id == R.id.rent_czyt){
            czyt();
        }else if(id == R.id.rent_yhlx){
            yhlx();
        }else if(id == R.id.commit_bt){
            commit();
        }
        view.setEnabled(true);
    }

    private void commit(){
        if(StringUtils.empty(gid)){
            MsgUtils.showMDMessage(context,"请选择所在网格!");
            return;
        }
        String no = rentNo.getText().toString().trim();
        if(StringUtils.empty(no)){
            MsgUtils.showMDMessage(context,"请输入房屋编号!");
            return;
        }
        String address = rentAddress.getText().toString().trim();
        if(StringUtils.empty(address)){
            MsgUtils.showMDMessage(context,"请输入房屋地址!");
            return;
        }
        String area = rentJzmj.getText().toString().trim();
        String zjhm = rentZjhm.getText().toString().trim();
        if(StringUtils.empty(zjdm)){
            MsgUtils.showMDMessage(context,"请选择证件代码!");
            return;
        }
        if(StringUtils.empty(zjhm)){
            MsgUtils.showMDMessage(context,"请输入证件号码!");
            return;
        }
        String hz = rentHz.getText().toString().trim();
        if(StringUtils.empty(hz)){
            MsgUtils.showMDMessage(context,"请输入房主姓名!");
            return;
        }
        String hzphone = rentHzphone.getText().toString().trim();
        if(StringUtils.empty(hzphone)){
            MsgUtils.showMDMessage(context,"请输入房主联系方式!");
            return;
        }
        String hzaddress = rentHzaddress.getText().toString().trim();
        if(StringUtils.empty(hzaddress)){
            MsgUtils.showMDMessage(context,"请输入房主现居详址!");
            return;
        }
        if(StringUtils.empty(czyt)){
            MsgUtils.showMDMessage(context,"请选择出租用途!");
            return;
        }
        if(StringUtils.empty(yhlx)){
            MsgUtils.showMDMessage(context,"请选择隐患类型!");
            return;
        }
        String zk = rentCzrname.getText().toString().trim();
        if(StringUtils.empty(zk)){
            MsgUtils.showMDMessage(context,"请输入承租人姓名!");
            return;
        }
        String zkno = rentCzrsfz.getText().toString().trim();
        if(StringUtils.empty(zkno)){
            MsgUtils.showMDMessage(context,"请输入承租人身份证号!");
            return;
        }
        String zkphone = rentCzrphone.getText().toString().trim();
        if(StringUtils.empty(zkphone)){
            MsgUtils.showMDMessage(context,"请输入承租人联系方式!");
            return;
        }
        showProgressDialog("");
        Https https = Https.with(this);
        if(entry!=null){
            https.url(CoreApi.RentHouseUpdate).addParam("b1600",entry.getB1600()).put();
        }else{
            https.url(CoreApi.RentHouseAdd).post();
        }
        https.addParam("b1601",no).addParam("b1602",address).addParam("b1603",yt).addParam("b1604",area).addParam("b1605",zjdm)
                .addParam("b1606",zjhm).addParam("b1607",hz).addParam("b1608",hzphone).addParam("b1609",hzaddress)
                .addParam("b1610",czyt).addParam("b1611",yhlx).addParam("b1612",zkno).addParam("b1613",zk)
                .addParam("b1614",zkphone).addParam("gid",gid).enqueue(new Callback<Object>() {
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

    private void yt(){
        showProgressDialog("");
        Https.with(this).url(BaseApi.getType).addParam("dictName","construction_applications")
                .addParam("page",0).addParam("size",9999).get()
                .enqueue(new Callback<List<CoreType>>() {
                    @Override
                    public void success(final List<CoreType> data) {
                        dismissProgressDialog();
                        final CommonTypeDialog<CoreType> dialog = new CommonTypeDialog<>(context,data);
                        dialog.showDialog("请选择房屋用途", new CommonAdapter<CoreType>(context, data, R.layout.alen_type_item) {
                            @Override
                            public void convert(ViewHolder holder, CoreType entity, int position) {
                                holder.setText(R.id.name_tv,entity.getLabel());
                            }
                        }, new CommonAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, ViewHolder holder, int position) {
                                dialog.dismiss();
                                yt = data.get(position).getValue();
                                rentYt.setText(data.get(position).getLabel());
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
                        dialog.showDialog("请选择证件代码", new CommonAdapter<CoreType>(context, data, R.layout.alen_type_item) {
                            @Override
                            public void convert(ViewHolder holder, CoreType entity, int position) {
                                holder.setText(R.id.name_tv,entity.getLabel());
                            }
                        }, new CommonAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, ViewHolder holder, int position) {
                                dialog.dismiss();
                                zjdm = data.get(position).getValue();
                                rentZjdm.setText(data.get(position).getLabel());
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
    private void czyt(){
        showProgressDialog("");
        Https.with(this).url(BaseApi.getType).addParam("dictName","rental_purposes")
                .addParam("page",0).addParam("size",9999).get()
                .enqueue(new Callback<List<CoreType>>() {
                    @Override
                    public void success(final List<CoreType> data) {
                        dismissProgressDialog();
                        final CommonTypeDialog<CoreType> dialog = new CommonTypeDialog<>(context,data);
                        dialog.showDialog("请选择出租用途", new CommonAdapter<CoreType>(context, data, R.layout.alen_type_item) {
                            @Override
                            public void convert(ViewHolder holder, CoreType entity, int position) {
                                holder.setText(R.id.name_tv,entity.getLabel());
                            }
                        }, new CommonAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, ViewHolder holder, int position) {
                                dialog.dismiss();
                                czyt = data.get(position).getValue();
                                rentCzyt.setText(data.get(position).getLabel());
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
    private void yhlx(){
        showProgressDialog("");
        Https.with(this).url(BaseApi.getType).addParam("dictName","hidden_danger_type")
                .addParam("page",0).addParam("size",9999).get()
                .enqueue(new Callback<List<CoreType>>() {
                    @Override
                    public void success(final List<CoreType> data) {
                        dismissProgressDialog();
                        final CommonTypeDialog<CoreType> dialog = new CommonTypeDialog<>(context,data);
                        dialog.showDialog("请选择隐患类型", new CommonAdapter<CoreType>(context, data, R.layout.alen_type_item) {
                            @Override
                            public void convert(ViewHolder holder, CoreType entity, int position) {
                                holder.setText(R.id.name_tv,entity.getLabel());
                            }
                        }, new CommonAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, ViewHolder holder, int position) {
                                dialog.dismiss();
                                yhlx = data.get(position).getValue();
                                rentYhlx.setText(data.get(position).getLabel());
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
}
