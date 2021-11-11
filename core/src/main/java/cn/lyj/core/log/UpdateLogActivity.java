package cn.lyj.core.log;

import android.os.Bundle;
import android.view.View;

import java.util.List;

import allen.frame.AllenBaseActivity;
import allen.frame.adapter.CommonAdapter;
import allen.frame.adapter.ViewHolder;
import allen.frame.entry.DicType;
import allen.frame.entry.Response;
import allen.frame.net.BaseApi;
import allen.frame.net.Callback;
import allen.frame.net.Https;
import allen.frame.tools.CommonTypeDialog;
import allen.frame.tools.Constants;
import allen.frame.tools.MsgUtils;
import allen.frame.tools.StringUtils;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.OnClick;
import cn.lyj.core.R;
import cn.lyj.core.R2;
import cn.lyj.core.api.CoreApi;
import cn.lyj.core.entry.Log;

public class UpdateLogActivity extends AllenBaseActivity {
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.log_sth)
    AppCompatTextView logSth;
    @BindView(R2.id.log_progress)
    AppCompatTextView logProgress;
    @BindView(R2.id.log_des)
    AppCompatEditText logDes;
    private Log entry;
    private String workItem="",progress="";

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.core_log_update_layout;
    }

    @Override
    protected void initBar() {
        entry = (Log) getIntent().getSerializableExtra(Constants.ObjectFirst);
        setToolbarTitle(toolbar,entry==null?"添加日志":"编辑日志",true);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void addEvent() {

    }

    @OnClick({R2.id.log_sth, R2.id.log_progress})
    public void onViewClicked(View view) {
        view.setEnabled(false);
        int id = view.getId();
        if(id==R.id.log_sth){
            loadSth();
        }else if(id==R.id.log_progress){
            loadProgress();
        }
        view.setEnabled(true);
    }

    private void loadSth(){
        showProgressDialog("");
        Https.with(this).url(BaseApi.getType).addParam("dictName","work_item").addParam("page",0).addParam("size",9999).get()
                .enqueue(new Callback<List<DicType>>() {

                    @Override
                    public void success(final List<DicType> data) {
                        dismissProgressDialog();
                        final CommonTypeDialog<DicType> dialog = new CommonTypeDialog<>(context,data);
                        dialog.showDialog("请选择工作事项", new CommonAdapter<DicType>(context, R.layout.alen_type_item) {
                            @Override
                            public void convert(ViewHolder holder, DicType entity, int position) {
                                holder.setText(R.id.name_tv,entity.getLabel());
                            }
                        }, new CommonAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, ViewHolder holder, int position) {
                                dialog.dismiss();
                                logSth.setText(data.get(position).getLabel());
                                workItem = data.get(position).getValue();
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

    private void loadProgress(){
        showProgressDialog("");
        Https.with(this).url(BaseApi.getType).addParam("dictName","work_progress").addParam("page",0).addParam("size",9999).get()
                .enqueue(new Callback<List<DicType>>() {

                    @Override
                    public void success(final List<DicType> data) {
                        dismissProgressDialog();
                        final CommonTypeDialog<DicType> dialog = new CommonTypeDialog<>(context,data);
                        dialog.showDialog("请选择工作进度", new CommonAdapter<DicType>(context, R.layout.alen_type_item) {
                            @Override
                            public void convert(ViewHolder holder, DicType entity, int position) {
                                holder.setText(R.id.name_tv,entity.getLabel());
                            }
                        }, new CommonAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, ViewHolder holder, int position) {
                                dialog.dismiss();
                                logProgress.setText(data.get(position).getLabel());
                                progress = data.get(position).getValue();
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

    private void commit(){
        String des = logDes.getText().toString().trim();
        if(StringUtils.empty(workItem)){
            MsgUtils.showMDMessage(context,"请选择工作事项!");
            return;
        }
        if(StringUtils.empty(progress)){
            MsgUtils.showMDMessage(context,"请选择工作进度!");
            return;
        }
        if(StringUtils.empty(des)){
            MsgUtils.showMDMessage(context,"请输入工作描述!");
            return;
        }
        showProgressDialog("");
        Https.with(this).url(CoreApi.CoreaddLog).addParam("workItem",workItem).addParam("progress",progress).addParam("description",des).post()
                .enqueue(new Callback<Object>() {
                    @Override
                    public void success(Object data) {
                        dismissProgressDialog();
                        MsgUtils.showShortToast(context,"提交成功!");
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
}
