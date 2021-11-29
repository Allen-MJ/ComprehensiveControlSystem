package allen.frame;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;

import allen.frame.entry.FileInfo;
import allen.frame.tools.Logger;
import allen.frame.tools.MsgUtils;
import allen.frame.tools.StringUtils;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.OnClick;

public class FileSelectorActivity extends AllenBaseActivity implements FileSelectorFragment.Callback {
    @BindView(R2.id.toolbar)
    Toolbar bar;
    // Single choice
    public static final int MODE_SINGLE = 0;
    // Multi choice
    public static final int MODE_MULTI = 1;

    public static final int TYPE_FOLDER = 0;
    public static final int TYPE_FILE = 1;
    public static final String TYPE_FILE_NAME = "";


    /**
     * Select mode，{@link #MODE_MULTI} by default
     */
    public static final String EXTRA_SELECT_TITLE = "extra_select_title";
    public static final String EXTRA_SELECT_CHOICE = "extra_select_choice";
    public static final String EXTRA_SELECT_CHOICE_TYPE = "extra_select_choice_type";
    public static final String EXTRA_SELECT_CHOICE_TYPE_NAME = "extra_select_choice_type_name";
    public static final String EXTRA_SELECT_MODE = "extra_select_mode";
    public static final String EXTRA_SELECT_COUNT = "extra_select_count";
    public static final String EXTRA_RESULT = "select_result";
    @BindView(R2.id.fgt_container)
    FrameLayout fgtContainer;
    @BindView(R2.id.cancel_bt)
    AppCompatButton cancelBt;
    @BindView(R2.id.ok_bt)
    AppCompatButton okBt;
    @BindView(R2.id.choice_layout)
    LinearLayoutCompat choiceLayout;
    private boolean choice = false;
    private int mode = MODE_SINGLE;
    private int count = 0;
    private int type = TYPE_FOLDER;
    private FileInfo.FileType typeName = null;
    FileSelectorFragment fragment;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.alen_activity_file;
    }

    @Override
    protected void initBar() {
        String title = getIntent().getStringExtra(EXTRA_SELECT_TITLE);
        setToolbarTitle(bar, StringUtils.empty(title) ? "请选择文件夹" : title,true);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        choice = getIntent().getBooleanExtra(EXTRA_SELECT_CHOICE, false);
        choiceLayout.setVisibility(choice?View.VISIBLE:View.GONE);
        mode = getIntent().getIntExtra(EXTRA_SELECT_MODE, MODE_SINGLE);
        type = getIntent().getIntExtra(EXTRA_SELECT_CHOICE_TYPE, TYPE_FOLDER);
        count = getIntent().getIntExtra(EXTRA_SELECT_COUNT, 0);
        typeName = (FileInfo.FileType) getIntent().getSerializableExtra(EXTRA_SELECT_CHOICE_TYPE_NAME);
        Bundle bundle = new Bundle();
        bundle.putBoolean(EXTRA_SELECT_CHOICE, choice);
        bundle.putInt(EXTRA_SELECT_MODE, mode);
        bundle.putInt(EXTRA_SELECT_CHOICE_TYPE, type);
        bundle.putString(EXTRA_SELECT_CHOICE_TYPE_NAME, "");
        if(typeName!=null){
            bundle.putSerializable(EXTRA_SELECT_CHOICE_TYPE_NAME,typeName);
        }else{
            bundle.putSerializable(EXTRA_SELECT_CHOICE_TYPE_NAME, FileInfo.FileType.Unknown);
        }
        bundle.putInt(EXTRA_SELECT_COUNT, count);
        fragment = new FileSelectorFragment();
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fgt_container, fragment)
                .commit();
    }

    @Override
    protected void addEvent() {
        bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.back();
            }
        });
    }

    @OnClick({R2.id.cancel_bt, R2.id.ok_bt})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.cancel_bt) {
            fragment.cancel();
        } else if (id == R.id.ok_bt) {
            ArrayList<String> list = fragment.getChoiceFiles();
            Logger.e("debug","数量："+list.size());
            if(list.size()>0){
                setResult(RESULT_OK,getIntent().putStringArrayListExtra(EXTRA_RESULT,list));
                finish();
            }else{
                MsgUtils.showMDMessage(context,type==TYPE_FOLDER?"请选择文件夹!":"请选择文件!");
            }
        }
    }
}
