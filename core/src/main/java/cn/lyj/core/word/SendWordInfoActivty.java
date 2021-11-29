package cn.lyj.core.word;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.DatePicker;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import allen.frame.AllenBaseActivity;
import allen.frame.FileSelector;
import allen.frame.FileSelectorActivity;
import allen.frame.adapter.AllenAllFilesAdapter;
import allen.frame.adapter.CommonAdapter;
import allen.frame.adapter.ViewHolder;
import allen.frame.entry.File;
import allen.frame.entry.FileInfo;
import allen.frame.entry.Response;
import allen.frame.entry.UploadFile;
import allen.frame.entry.WordFile;
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
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
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
    @BindView(R2.id.word_file_add)
    AppCompatTextView wordFileAdd;
    private String title, number, date, jjcd, content, files;
    private Word entry;
    private String ids, names, missiveId;
    private AllenAllFilesAdapter adapter;
    private Map<String,Boolean> keys;
    private Map<String, UploadFile> map;
    private UploadProgressDialog dialog;

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
        setToolbarTitle(toolbar, entry == null ? "添加发文" : "编辑发文", true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 10) {
                ids = data.getStringExtra(Constants.Key_1);
                names = data.getStringExtra(Constants.Key_2);
                wordUsers.setText(names);
            }else if(requestCode==11){
                ArrayList<String> paths = data.getStringArrayListExtra(FileSelectorActivity.EXTRA_RESULT);
                List<WordFile> files = new ArrayList<>();
                dialog.init(context);
                int index = 0;
                for (String path : paths) {
                    if(!adapter.getPaths().contains(path)){
                        WordFile file = new WordFile(path);
                        files.add(file);
                        if(!map.containsKey(path)){
                            upload(file);
                            index = index + 1;
                        }
                    }
                }
                adapter.add(files);
                if(index==0){
                    dialog.dismiss();
                }
            }
        }
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        dialog = new UploadProgressDialog();
        adapter = new AllenAllFilesAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        wordFiles.setLayoutManager(manager);
        wordFiles.setAdapter(adapter);
        keys = new HashMap<>();
        map = new HashMap<>();
        if (entry != null) {
            missiveId = entry.getMissiveId();
            wordTitle.setText(entry.getTitle());
            wordNumber.setText(entry.getMissiveNo());
            wordJjcd.setText(entry.getEmergencyDegreeName());
            wordUsers.setText(entry.getReceiver());
            wordSingnDate.setText(entry.getSignTime());
            wordContent.setText(Html.fromHtml(entry.getDigest()));
            jjcd = entry.getEmergencyDegree();
            names = entry.getReceiver();
            adapter.setData(entry.getAttachments());
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
            public void onItemDelete(View v, int position, WordFile file) {
                adapter.delete(file);
            }

            @Override
            public void onItemClick(View v, int position, WordFile file) {

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

    @OnClick({R2.id.word_jjcd, R2.id.word_users, R2.id.word_singn_date, R2.id.commit_bt, R2.id.word_file_add})
    public void onViewClicked(View view) {
        view.setEnabled(false);
        int id = view.getId();
        if (id == R.id.word_jjcd) {
            jjcd();
        } else if (id == R.id.word_users) {
            startActivityForResult(new Intent(context, ChoicePersonActivity.class)
                    .putExtra(Constants.Key_1, ids)
                    .putExtra(Constants.Key_2, true), 10);
        } else if (id == R.id.word_singn_date) {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear, int startDayOfMonth) {
                    wordSingnDate.setText(startYear + "-" + String.format("%02d", startMonthOfYear + 1) + "-" + String.format("%02d", startDayOfMonth));
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        } else if (id == R.id.commit_bt) {
            commit();
        } else if (id == R.id.word_file_add){
            requestRunPermisssion(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10, new PermissionListener() {
                @Override
                public void onGranted(int requestCode) {
                    FileSelector.creat()
                            .canChoice(true)
                            .setTitle("请选择文件")
                            .setMode(FileSelectorActivity.MODE_MULTI)
                            .setChoiceType(FileSelectorActivity.TYPE_FILE, FileInfo.FileType.Unknown)
                            .setCount(6)
                            .start(SendWordInfoActivty.this,11);
                }

                @Override
                public void onDenied(List<String> deniedPermission) {
                    MsgUtils.showMDMessage(context,"请开通文件读写权限!");
                }
            });
        }
        view.setEnabled(true);
    }

    private void jjcd() {
        showProgressDialog("");
        Https.with(this).url(CoreApi.core_Type).addParam("dictName", "emergency_degree").addParam("page", 0).addParam("size", 9999).get()
                .enqueue(new Callback<List<CoreType>>() {

                    @Override
                    public void success(final List<CoreType> data) {
                        dismissProgressDialog();
                        final CommonTypeDialog<CoreType> dialog = new CommonTypeDialog<>(context, data);
                        dialog.showDialog("请选择紧急程度", new CommonAdapter<CoreType>(context, data, R.layout.alen_type_item) {
                            @Override
                            public void convert(ViewHolder holder, CoreType entity, int position) {
                                holder.setText(R.id.name_tv, entity.getLabel());
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
                        MsgUtils.showMDMessage(context, response.getMsg());
                    }
                });
    }

    private void commit() {
        title = wordTitle.getText().toString().trim();
        number = wordNumber.getText().toString().trim();
        date = wordSingnDate.getText().toString().trim();
        content = wordContent.getText().toString().trim();
        if (StringUtils.empty(title)) {
            MsgUtils.showMDMessage(context, "请输入公文标题!");
            return;
        }
        if (StringUtils.empty(number)) {
            MsgUtils.showMDMessage(context, "请输入公文字号!");
            return;
        }
        if (StringUtils.empty(jjcd)) {
            MsgUtils.showMDMessage(context, "请选择紧急程度!");
            return;
        }
        if (StringUtils.empty(ids)) {
            MsgUtils.showMDMessage(context, "请选择紧接收人!");
            return;
        }
        if (StringUtils.empty(date)) {
            MsgUtils.showMDMessage(context, "请选择签发时间!");
            return;
        }
        if (StringUtils.empty(content)) {
            MsgUtils.showMDMessage(context, "请输入公文内容!");
            return;
        }
        String attachments="";
        if(adapter.getItemCount()==0){
            MsgUtils.showMDMessage(context, "请选择公文文件!");
            return;
        }else{
            List<WordFile> wordFiles = new ArrayList<>();
            for(WordFile wordFile:adapter.getFiles()){
                if(wordFile.getType()==0){
                    wordFiles.add(wordFile);
                }else{
                    UploadFile data = map.get(wordFile.getAttachmentPath());
                    WordFile entry = new WordFile();
                    entry.setName(data.getRealName());
                    entry.setAttachmentPath(data.getRelativePath());
                    wordFiles.add(entry);
                }
            }
            attachments = new Gson().toJson(wordFiles);
        }
        showProgressDialog("");
        Https https = Https.with(this).url(CoreApi.Missive).addParam("title", title).addParam("missiveNo", number)
                .addParam("emergencyDegree", jjcd).addParam("signTime", date).addParam("digest", content).addParam("receiveId", ids).addParam("receiver", names)
                .addParam("attachments", attachments);
        if (entry == null) {
            https.post();
        } else {
            https.addParam("missiveId", missiveId);
            https.put();
        }
        https.enqueue(new Callback<Object>() {
            @Override
            public void success(Object data) {
                dismissProgressDialog();
                MsgUtils.showMDMessage(context, "保存成功!");
                setResult(RESULT_OK, getIntent());
                finish();
            }

            @Override
            public void token() {
                dismissProgressDialog();
                actHelper.tokenErro2Login(SendWordInfoActivty.this);
            }

            @Override
            public void fail(Response response) {
                dismissProgressDialog();
                MsgUtils.showMDMessage(context, response.getMsg());
            }
        });
    }

    private void upload(final WordFile file) {
        Https.with(this).url(BaseApi.Upload).path(file.getAttachmentPath()).upload().enqueue(new Callback<UploadFile>() {

            @Override
            public void success(UploadFile data) {
                map.put(file.getAttachmentPath(), data);
                Logger.e("success", "success");
            }

            @Override
            public void onProgress(long total, long current) {
                Logger.e("progress", total + ":" + current);
                dialog.changeProgress(file.getName(), total, current);
            }

            @Override
            public void fail(Response response) {
                Logger.e("fail", "fail");
            }
        });
    }

}
