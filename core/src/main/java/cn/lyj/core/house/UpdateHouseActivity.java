package cn.lyj.core.house;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.List;

import allen.frame.AllenBaseActivity;
import allen.frame.AllenChoiceGridActivity;
import allen.frame.AllenChoiceUnitsActivity;
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
import butterknife.OnClick;
import cn.lyj.core.R;
import cn.lyj.core.R2;
import cn.lyj.core.api.CoreApi;
import cn.lyj.core.entry.House;

public class UpdateHouseActivity extends AllenBaseActivity {
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.house_wg)
    AppCompatTextView houseWg;
    @BindView(R2.id.house_no)
    AppCompatEditText houseNo;
    @BindView(R2.id.house_dw)
    AppCompatTextView houseDw;
    @BindView(R2.id.house_xq)
    AppCompatTextView houseXq;
    @BindView(R2.id.house_ld)
    AppCompatTextView houseLd;
    @BindView(R2.id.house_dy)
    AppCompatEditText houseDy;
    @BindView(R2.id.house_lc)
    AppCompatEditText houseLc;
    @BindView(R2.id.house_fh)
    AppCompatEditText houseFh;
    @BindView(R2.id.house_fwyt)
    AppCompatTextView houseFwyt;
    @BindView(R2.id.house_address)
    AppCompatEditText houseAddress;
    @BindView(R2.id.house_zj)
    AppCompatEditText houseZj;
    @BindView(R2.id.house_remark)
    AppCompatEditText houseRemark;
    @BindView(R2.id.commit_bt)
    AppCompatButton commitBt;
    private House entry;
    private String gid,type,cid,bid,ucode;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.core_house_update_layout;
    }

    @Override
    protected void initBar() {
        entry = (House) getIntent().getSerializableExtra(Constants.ObjectFirst);
        setToolbarTitle(toolbar, (entry==null?"添加":"编辑")+"实有房屋", true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case 10:
                    gid = data.getStringExtra(Constants.Key_1);
                    houseWg.setText(Constants.Key_2);
                    break;
                case 11:
                    ucode = data.getStringExtra(Constants.Key_2);
                    houseDw.setText(data.getStringExtra(Constants.Key_2));
                    break;
            }
        }
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        if(entry!=null){
            gid = entry.getGid();
            houseWg.setText(entry.getGidObj().getGenericName());
            houseNo.setText(entry.getHuhao());
            houseDw.setText(entry.getUcode());
            ucode = entry.getUcode();
            houseXq.setText(entry.getCidObj().getName());
            cid = entry.getCid();
            houseLd.setText(entry.getBidObj().getName());
            bid = entry.getBid();
            houseDy.setText(entry.getDanyuan());
            houseLc.setText(entry.getLouceng());
            houseFh.setText(entry.getHao());
            houseFwyt.setText(entry.getHtype());
            type = entry.getHtype();
            houseAddress.setText(entry.getAddress());
            houseAddress.setText(entry.getLandline());
            houseRemark.setText(entry.getRemark());
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

    @OnClick({R2.id.house_wg, R2.id.house_dw, R2.id.house_xq, R2.id.house_ld, R2.id.house_fwyt, R2.id.commit_bt})
    public void onViewClicked(View view) {
        int id = view.getId();
        if(id== R.id.house_wg){
            startActivityForResult(new Intent(context, AllenChoiceGridActivity.class),10);
        }else if(id== R.id.house_dw){
            startActivityForResult(new Intent(context, AllenChoiceUnitsActivity.class),11);
        }else if(id== R.id.house_xq){

        }else if(id== R.id.house_ld){

        }else if(id== R.id.house_fwyt){
            type();
        }else if(id== R.id.commit_bt){
            commit();
        }
    }

    private void commit(){
        String hh = houseNo.getText().toString().trim();
        if(StringUtils.empty(hh)){
            MsgUtils.showMDMessage(context,"请输入户号!");
            return;
        }
        if(StringUtils.empty(gid)){
            MsgUtils.showMDMessage(context,"请选择所在网格!");
            return;
        }
        if(StringUtils.empty(cid)){
            MsgUtils.showMDMessage(context,"请选择所在小区!");
            return;
        }
        if(StringUtils.empty(bid)){
            MsgUtils.showMDMessage(context,"请选择所在楼栋!");
            return;
        }
        String dy = houseDy.getText().toString().trim();
        if(StringUtils.empty(dy)){
            MsgUtils.showMDMessage(context,"请输入单元!");
            return;
        }
        String lc = houseLc.getText().toString().trim();
        if(StringUtils.empty(lc)){
            MsgUtils.showMDMessage(context,"请输入楼层!");
            return;
        }
        String fh = houseFh.getText().toString().trim();
        if(StringUtils.empty(fh)){
            MsgUtils.showMDMessage(context,"请输入房号!");
            return;
        }
        String address = houseAddress.getText().toString().trim();
        if(StringUtils.empty(address)){
            MsgUtils.showMDMessage(context,"请输入地址!");
            return;
        }
        String landline = houseZj.getText().toString().trim();
        String remark = houseRemark.getText().toString().trim();
        showProgressDialog("");
        Https https = Https.with(this);
        if(entry!=null){
            https.url(CoreApi.HouseUpdate).put().addParam("hid",entry.getHid());
        }else{
            https.url(CoreApi.HouseAdd).post();
        }
        https.addParam("huhao",hh).addParam("ucode",ucode).addParam("gid",gid).addParam("cid",cid).addParam("bid",bid)
                .addParam("danyuan",dy).addParam("louceng",lc).addParam("hao",fh)
                .addParam("htype",type).addParam("address",address).addParam("landline",landline).addParam("remark",remark)
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

    private void type(){
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
                                type = data.get(position).getValue();
                                houseFwyt.setText(data.get(position).getLabel());
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
