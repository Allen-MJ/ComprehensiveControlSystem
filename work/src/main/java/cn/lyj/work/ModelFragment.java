package cn.lyj.work;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import allen.frame.BaseFragment;
import allen.frame.tools.Constants;
import allen.frame.tools.Logger;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import cn.lyj.core.entry.Model;
import cn.lyj.work.adapter.ModelParentAdapter;
import cn.lyj.work.utils.ModelData;

public class ModelFragment extends BaseFragment {
    @BindView(R2.id.rv)
    RecyclerView rv;
    private ModelParentAdapter adapter;
    private List<Model> list;
    private int type = 1;

    public static ModelFragment init(int type){
        ModelFragment fragment = new ModelFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.Key_1,type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.work_home_model_layout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
        adapter = new ModelParentAdapter();
        rv.setAdapter(adapter);
        type = getArguments().getInt(Constants.Key_1,0);
        loadModel();
    }

    private void loadModel(){
        list = new ArrayList<>();
        switch (type){
            case 1:
                list = ModelData.init().getGrid();
                break;
            case 2:
                list = ModelData.init().getWork();
                break;
        }
        Logger.e("type","type"+type);
        adapter.setList(list);
    }
}
