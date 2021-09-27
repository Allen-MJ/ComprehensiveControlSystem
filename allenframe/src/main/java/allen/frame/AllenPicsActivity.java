package allen.frame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import allen.frame.adapter.FragmentAdapter;
import allen.frame.tools.StringUtils;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

public class AllenPicsActivity extends AllenIMBaseActivity {

    @BindView(R2.id.pager)
    ViewPager pager;
    @BindView(R2.id.toolbar)
    Toolbar bar;
    @BindView(R2.id.dec)
    TextView dec;
    @BindView(R2.id.scrollView)
    NestedScrollView scrollView;
    private List<Fragment> fralist;
    private ArrayList<String> list;
    private String url;
    private FragmentAdapter adapter;
    private int index = 0;
    private String describe;
    private boolean isResPic = false;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.alen_pics_layout;
    }

    @Override
    protected void initBar() {
        String name = getIntent().getStringExtra("name");
        url = getIntent().getStringExtra("url");
        describe = getIntent().getStringExtra("describe");
        index = getIntent().getIntExtra("index", 0);
        if (StringUtils.empty(name)) {
            name = "图片";
        }
        bar.setTitle(name);
        setSupportActionBar(bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        list = getIntent().getStringArrayListExtra("list");
        if (list == null) {
            url = getIntent().getStringExtra("url");
            list = new ArrayList<>();
            list.add(url);
        }
        isResPic = getIntent().getBooleanExtra("isResPic", false);
        if (isResPic) {
            scrollView.setVisibility(View.GONE);
        }
        fralist = new ArrayList<Fragment>();
        addpager();
        adapter = new FragmentAdapter(getSupportFragmentManager(), fralist);
        pager.setAdapter(adapter);
        change(index);
    }

    @Override
    protected void addEvent() {
        bar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("fileType", "img");
                setResult(300, intent);
                finish();
            }
        });
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {
				change(position);
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
    }

    private int len;

    private void addpager() {
        len = list == null ? 0 : list.size();
        for (int i = 0; i < len; i++) {
            fralist.add(PicFragment.getInstance(list.get(i)));
        }
    }

    private void change(int index) {
        if (StringUtils.empty(describe) && len == 1) {
            dec.setVisibility(View.GONE);
        } else {
            dec.setVisibility(View.VISIBLE);
            dec.setText((index + 1) + "/" + len + "    " + describe);
        }
        pager.setCurrentItem(index, true);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("fileType", "img");
        setResult(300, intent);
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
