package allen.frame;

import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.view.TextureView;

import allen.frame.tools.Camera2Helper;
import androidx.annotation.Nullable;
import butterknife.BindView;

public class AllenFaceActivity extends AllenIMBaseActivity {
    @BindView(R2.id.camera)
    TextureView camera;
    Camera2Helper helper;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.alen_face_layout;
    }

    @Override
    protected void initBar() {

    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        helper = new Camera2Helper(context,camera);
    }

    @Override
    protected void addEvent() {
        camera.setSurfaceTextureListener(mSurfaceTextureListener);
    }
    /**
     * 处理生命周期内的回调事件
     */
    private final TextureView.SurfaceTextureListener mSurfaceTextureListener = new TextureView.SurfaceTextureListener() {

        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture texture, int width, int height) {
            helper.openCamera(width, height);
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture texture, int width, int height) {
            helper.configureTransform(width, height);
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture texture) {
            helper.closeCamera();
            return true;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture texture) {
        }
    };
}
