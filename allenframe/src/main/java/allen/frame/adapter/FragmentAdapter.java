package allen.frame.adapter;
import java.util.ArrayList;  
import java.util.List;


import android.view.ViewGroup;

import allen.frame.tools.Logger;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class FragmentAdapter extends FragmentPagerAdapter {
  
    List<Fragment> fragmentList = new ArrayList<>();
    private String[] tabs;
    public FragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);  
        this.fragmentList = fragmentList;  
    }

    public FragmentAdapter(FragmentManager fm,List<Fragment> fragmentList,String[] tabs) {
        super(fm);
        this.fragmentList = fragmentList;
        this.tabs = tabs;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }
  
    @Override  
    public int getCount() {  
        return fragmentList.size();  
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(tabs!=null){
            Logger.e("debug","FragmentAdapter:"+position);
            return tabs[position%tabs.length];
        }
        return "";
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    	// TODO Auto-generated method stub
//    	super.destroyItem(container, position, object);
    }
  
}