package allen.frame;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {
    private Unbinder unbinder;
    public ActivityHelper actHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResID(),container,false);
        unbinder = ButterKnife.bind(this,view);
        actHelper = new ActivityHelper(getActivity(),view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(unbinder!= Unbinder.EMPTY){
            unbinder.unbind();
        }
    }

    protected abstract int getLayoutResID();
}
