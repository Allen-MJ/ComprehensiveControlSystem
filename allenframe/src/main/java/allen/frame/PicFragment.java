package allen.frame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView.ScaleType;

import com.bumptech.glide.Glide;

import allen.frame.tools.Constants;
import allen.frame.tools.DownLoadNewHelper;
import allen.frame.widget.PhotoView;
import butterknife.BindView;
import butterknife.OnClick;

public class PicFragment extends BaseFragment {
    @BindView(R2.id.pic)
    PhotoView pic;

    public static PicFragment getInstance(String url) {
        PicFragment fragment = new PicFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.alen_pic_item_laoyut;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String url = DownLoadNewHelper.doEncoderUrlStr(getArguments().getString("url", ""));
        pic.setScaleType(ScaleType.FIT_CENTER);
        pic.enable();
        pic.enableRotate();
        Glide.with(this).load(url).thumbnail(Glide.with(this).load(R
                .drawable.load))
                .fitCenter().dontAnimate().error(R.drawable.mis_default_error).into(pic);
    }

    @OnClick({R2.id.pic})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.pic) {
            Intent intent = new Intent();
            intent.putExtra("fileType", "img");
            getActivity().setResult(300, intent);
            getActivity().finish();
        }
    }
}
