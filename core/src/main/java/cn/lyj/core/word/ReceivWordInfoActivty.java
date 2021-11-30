package cn.lyj.core.word;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import java.io.File;

import allen.frame.AllenBaseActivity;
import allen.frame.adapter.AllenAllFilesAdapter;
import allen.frame.entry.Response;
import allen.frame.entry.WordFile;
import allen.frame.net.Callback;
import allen.frame.net.Https;
import allen.frame.tools.Constants;
import allen.frame.tools.FileIntent;
import allen.frame.tools.MsgUtils;
import allen.frame.tools.StringUtils;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.lyj.core.R;
import cn.lyj.core.R2;
import cn.lyj.core.api.CoreApi;
import cn.lyj.core.entry.Word;

public class ReceivWordInfoActivty extends AllenBaseActivity {
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.word_title)
    AppCompatTextView wordTitle;
    @BindView(R2.id.word_number)
    AppCompatTextView wordNumber;
    @BindView(R2.id.word_jjcd)
    AppCompatTextView wordJjcd;
    @BindView(R2.id.word_singn_date)
    AppCompatTextView wordSingnDate;
    @BindView(R2.id.word_content)
    AppCompatTextView wordContent;
    @BindView(R2.id.word_files)
    RecyclerView wordFiles;
    @BindView(R2.id.commit_bt)
    AppCompatButton commitBt;
    @BindView(R2.id.word_signer)
    AppCompatTextView wordSigner;
    @BindView(R2.id.word_opinion)
    AppCompatEditText wordOpinion;
    @BindView(R2.id.ok_bt)
    AppCompatButton okBt;
    @BindView(R2.id.cancel_bt)
    AppCompatButton cancelBt;
    @BindView(R2.id.sign_layout)
    LinearLayoutCompat signLayout;
    private String content;
    private Word entry;
    private String missiveId;
    private AllenAllFilesAdapter adapter;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.core_receiv_word_info;
    }

    @Override
    protected void initBar() {
        entry = (Word) getIntent().getSerializableExtra(Constants.ObjectFirst);
        setToolbarTitle(toolbar, "收文", true);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        wordFiles.setLayoutManager(manager);
        adapter = new AllenAllFilesAdapter(true);
        wordFiles.setAdapter(adapter);
        if (entry != null) {
            missiveId = entry.getMissiveId();
            wordTitle.setText(entry.getTitle());
            wordNumber.setText(entry.getMissiveNo());
            wordJjcd.setText(entry.getEmergencyDegreeName());
            wordSigner.setText(entry.getSigner());
            wordSingnDate.setText(entry.getSignTime());
            wordContent.setText(Html.fromHtml(entry.getDigest()));
            adapter.setData(entry.getAttachments());
            if("未签收".equals(entry.getState())){
                commitBt.setVisibility(View.VISIBLE);
            }else{
                commitBt.setVisibility(View.GONE);
            }
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
        adapter.setOnItemClickListener(new AllenAllFilesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position, WordFile file) {
                openNetFile(file.getAttachmentPath());
            }
        });
    }

    @OnClick({R2.id.commit_bt, R2.id.ok_bt, R2.id.cancel_bt})
    public void onViewClicked(View view) {
        view.setEnabled(false);
        int id = view.getId();
        if (id == R.id.commit_bt) {
            signLayout.setVisibility(View.VISIBLE);
            commitBt.setVisibility(View.GONE);
        }else if(id== R.id.ok_bt){
            commit();
        }else if(id== R.id.cancel_bt){
            signLayout.setVisibility(View.GONE);
            commitBt.setVisibility(View.VISIBLE);
        }
        view.setEnabled(true);
    }

    private void commit() {
        showProgressDialog("");
        content = wordOpinion.getText().toString().trim();
        Https.with(this).url(CoreApi.MissiveSign).addParam("id",missiveId).addParam("opinion",content).post()
            .enqueue(new Callback<Object>() {
                @Override
                public void success(Object data) {
                    dismissProgressDialog();
                    MsgUtils.showMDMessage(context, "签收成功!");
                    setResult(RESULT_OK, getIntent());
                    finish();
                }

                @Override
                public void token() {
                    dismissProgressDialog();
                    actHelper.tokenErro2Login(ReceivWordInfoActivty.this);
                }

                @Override
                public void fail(Response response) {
                    dismissProgressDialog();
                    MsgUtils.showMDMessage(context, response.getMsg());
                }
            });
    }

    private void openNetFile(String url){
        final ProgressDialog dialog = new ProgressDialog(context, allen.frame.R.style.Base_V21_Theme_AppCompat_Light_Dialog);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setCancelable(false);
        dialog.show();
        Https.with(this).url(url).download(new Callback<File>() {
            @Override
            public void success(File data) {
                dialog.dismiss();
                FileIntent.openFile(context,data);
            }

            @Override
            public void onProgress(long total, long current) {
                dialog.setProgressNumberFormat(String.format("%s/%s", StringUtils.formatFileSize(current),StringUtils.formatFileSize(current)));
            }

            @Override
            public void fail(Response response) {
                dialog.dismiss();
                MsgUtils.showMDMessage(context,response.getMsg());
            }
        });
    }

}
