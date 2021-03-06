package allen.frame.tools;

import com.bumptech.glide.load.resource.drawable.DrawableResource;
import com.wellee.gifdecoder.FrameSequenceDrawable;

import androidx.annotation.NonNull;

public class GifResource extends DrawableResource<FrameSequenceDrawable> {

    public GifResource(FrameSequenceDrawable drawable) {
        super(drawable);
    }

    @NonNull
    @Override
    public Class<FrameSequenceDrawable> getResourceClass() {
        return FrameSequenceDrawable.class;
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public void recycle() {
        drawable.stop();
        drawable.destroy();
    }
}
