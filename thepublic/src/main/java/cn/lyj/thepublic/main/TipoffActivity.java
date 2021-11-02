package cn.lyj.thepublic.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

import allen.frame.AllenBaseActivity;
import allen.frame.MultiImageSelector;
import allen.frame.adapter.AllenFileChoiceAdapter;
import allen.frame.entry.File;
import allen.frame.tools.Constants;
import allen.frame.tools.FileUtils;
import allen.frame.tools.StringUtils;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.lyj.thepublic.R;
import cn.lyj.thepublic.R2;

public class TipoffActivity extends AllenBaseActivity {
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.tip_dw)
    AppCompatTextView tipDw;
    @BindView(R2.id.tip_fyr)
    AppCompatEditText tipFyr;
    @BindView(R2.id.tip_phone)
    AppCompatEditText tipPhone;
    @BindView(R2.id.sex_male)
    RadioButton sexMale;
    @BindView(R2.id.sex_female)
    RadioButton sexFemale;
    @BindView(R2.id.tip_sex)
    RadioGroup tipSex;
    @BindView(R2.id.tip_grid)
    AppCompatTextView tipGrid;
    @BindView(R2.id.tip_address)
    AppCompatEditText tipAddress;
    @BindView(R2.id.tip_content)
    AppCompatEditText tipContent;
    @BindView(R2.id.tip_file)
    RecyclerView tipFile;
    @BindView(R2.id.tip_bt)
    AppCompatButton tipBt;
    private AllenFileChoiceAdapter adapter;
    private ArrayList<File> files;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.public_tipoff_layout;
    }

    @Override
    protected void initBar() {
        setToolbarTitle(toolbar,"爆料",true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==2){
                assert data != null;
                ArrayList<String> paths = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                for(String path:paths){
                    File file = new File();
                    file.setName(StringUtils.getFileNameByPath(path));
                    file.setPath(path);
                    file.setType(0);//图片
                    files.add(file);
                }
                adapter.setData(files);
            }
        }
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        files = new ArrayList<>();
        tipPhone.setText(shared.getString(Constants.UserPhone,""));
        GridLayoutManager manager = new GridLayoutManager(context,4);
        tipFile.setLayoutManager(manager);
        adapter = new AllenFileChoiceAdapter();
        tipFile.setAdapter(adapter);
    }

    @Override
    protected void addEvent() {
        adapter.setOnItemClickListener(new AllenFileChoiceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position, File file) {

            }

            @Override
            public void onItemDelete(View v, int position, File file) {
                adapter.delete(file);
            }

            @Override
            public void onAddClick() {
                MultiImageSelector.create()
                        .multi().showCamera(true).count(6)
                        .start(TipoffActivity.this,2);
            }
        });
    }

    @OnClick({R2.id.tip_dw, R2.id.tip_grid, R2.id.tip_bt})
    public void onViewClicked(View view) {
        view.setEnabled(false);
        int id = view.getId();
        if(id==R.id.tip_dw){

        }else if(id==R.id.tip_grid){

        }else if(id==R.id.tip_bt){

        }
        view.setEnabled(true);
    }
}
