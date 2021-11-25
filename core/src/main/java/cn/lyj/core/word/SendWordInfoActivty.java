package cn.lyj.core.word;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.List;

import allen.frame.AllenBaseActivity;
import allen.frame.adapter.CommonAdapter;
import allen.frame.adapter.ViewHolder;
import allen.frame.entry.Response;
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
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.lyj.core.ChoicePersonActivity;
import cn.lyj.core.R;
import cn.lyj.core.R2;
import cn.lyj.core.api.CoreApi;
import cn.lyj.core.entry.CoreType;
import cn.lyj.core.entry.Word;

public class SendWordInfoActivty extends AllenBaseActivity {
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.word_title)
    AppCompatEditText wordTitle;
    @BindView(R2.id.word_number)
    AppCompatEditText wordNumber;
    @BindView(R2.id.word_jjcd)
    AppCompatTextView wordJjcd;
    @BindView(R2.id.word_users)
    AppCompatTextView wordUsers;
    @BindView(R2.id.word_singn_date)
    AppCompatTextView wordSingnDate;
    @BindView(R2.id.word_content)
    AppCompatEditText wordContent;
    @BindView(R2.id.word_files)
    RecyclerView wordFiles;
    @BindView(R2.id.commit_bt)
    AppCompatButton commitBt;
    private String title,number,date,jjcd,content,files;
    private Word entry;
    private String ids,names,missiveId;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.core_send_word_info;
    }

    @Override
    protected void initBar() {
        entry = (Word) getIntent().getSerializableExtra(Constants.ObjectFirst);
        setToolbarTitle(toolbar,entry==null?"添加发文" : "编辑发文",true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==10){
                ids = data.getStringExtra(Constants.Key_1);
                names = data.getStringExtra(Constants.Key_2);
                wordUsers.setText(names);
            }
        }
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        if(entry!=null){
            missiveId = entry.getMissiveId();
            wordTitle.setText(entry.getTitle());
            wordNumber.setText(entry.getMissiveNo());
            wordJjcd.setText(entry.getEmergencyDegree());
            wordUsers.setText(entry.getReceiver());
            wordSingnDate.setText(entry.getSignTime());
            wordContent.setText(Html.fromHtml(entry.getDigest()));
            jjcd = entry.getEmergencyDegree();
            names = entry.getReceiver();
        }
    }

    @Override
    protected void addEvent() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);
                finish();
                v.setEnabled(true);
            }
        });
    }

    @OnClick({R2.id.word_jjcd, R2.id.word_users, R2.id.word_singn_date, R2.id.commit_bt})
    public void onViewClicked(View view) {
        view.setEnabled(false);
        int id = view.getId();
        if(id== R.id.word_jjcd){
            jjcd();
        }else if(id== R.id.word_users){
            startActivityForResult(new Intent(context, ChoicePersonActivity.class)
                    .putExtra(Constants.Key_1,ids)
                    .putExtra(Constants.Key_2,true),10);
        }else if(id== R.id.word_singn_date){
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear, int startDayOfMonth) {
                    wordSingnDate.setText(startYear+"-"+String.format("%02d",startMonthOfYear+1)+"-"+String.format("%02d",startDayOfMonth));
                }
            },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        }else if(id== R.id.commit_bt){
            commit();
        }
        view.setEnabled(true);
    }

    private void jjcd(){
        showProgressDialog("");
        Https.with(this).url(CoreApi.core_Type).addParam("dictName","emergency_degree").addParam("page",0).addParam("size",9999).get()
                .enqueue(new Callback<List<CoreType>>() {

                    @Override
                    public void success(final List<CoreType> data) {
                        dismissProgressDialog();
                        final CommonTypeDialog<CoreType> dialog = new CommonTypeDialog<>(context,data);
                        dialog.showDialog("请选择紧急程度", new CommonAdapter<CoreType>(context, data, R.layout.alen_type_item) {
                            @Override
                            public void convert(ViewHolder holder, CoreType entity, int position) {
                                holder.setText(R.id.name_tv,entity.getLabel());
                            }
                        }, new CommonAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, ViewHolder holder, int position) {
                                dialog.dismiss();
                                jjcd = data.get(position).getValue();
                                wordJjcd.setText(data.get(position).getLabel());
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
        title = wordTitle.getText().toString().trim();
        number = wordNumber.getText().toString().trim();
        date = wordSingnDate.getText().toString().trim();
        content = wordContent.getText().toString().trim();
        if(StringUtils.empty(title)){
            MsgUtils.showMDMessage(context,"请输入公文标题!");
            return;
        }
        if(StringUtils.empty(number)){
            MsgUtils.showMDMessage(context,"请输入公文字号!");
            return;
        }
        if(StringUtils.empty(jjcd)){
            MsgUtils.showMDMessage(context,"请选择紧急程度!");
            return;
        }
        if(StringUtils.empty(ids)){
            MsgUtils.showMDMessage(context,"请选择紧接收人!");
            return;
        }
        if(StringUtils.empty(date)){
            MsgUtils.showMDMessage(context,"请选择签发时间!");
            return;
        }
        if(StringUtils.empty(content)){
            MsgUtils.showMDMessage(context,"请输入公文内容!");
            return;
        }
        showProgressDialog("");
        Https https = Https.with(this).url(CoreApi.Missive).addParam("title",title).addParam("missiveNo",number)
                .addParam("emergencyDegree",jjcd).addParam("signTime",date).addParam("digest",content).addParam("receiveId",ids).addParam("receiver",names)
                .addParam("attachments","");
        if(entry==null){
            https.post();
        }else{
            https.addParam("missiveId",missiveId);
            https.put();
        }
        https.enqueue(new Callback<Object>() {
            @Override
            public void success(Object data) {
                dismissProgressDialog();
                MsgUtils.showMDMessage(context,"保存成功!");
                setResult(RESULT_OK,getIntent());
                finish();
            }

            @Override
            public void token() {
                dismissProgressDialog();
                MsgUtils.showMDMessage(context,"账号过期,请重新登录!");
            }

            @Override
            public void fail(Response response) {
                dismissProgressDialog();
                MsgUtils.showMDMessage(context,response.getMsg());
            }
        });
    }
}
