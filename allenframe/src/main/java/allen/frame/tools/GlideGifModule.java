package allen.frame.tools;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.wellee.gifdecoder.FrameSequenceDrawable;

import java.io.InputStream;

import androidx.annotation.NonNull;

@GlideModule
public class GlideGifModule extends AppGlideModule {

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        // 注册一个 GifResourceDecoder, 用于将 GIF 的 InputStream 转为 FrameSequenceDrawable
        registry.prepend(
                Registry.BUCKET_GIF,
                InputStream.class, FrameSequenceDrawable.class,
                new GifDecoder(glide.getBitmapPool())
        );
    }
}
