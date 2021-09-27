package allen.frame.HtmlImageUtil;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.HashSet;
import java.util.Set;

import allen.frame.R;
import allen.frame.tools.Logger;

public class GlideImageGetter implements Html.ImageGetter, Drawable.Callback {
	private final Context mContext;

	private final TextView mTextView;

	private final Set<ImageGetterViewTarget> mTargets;

	public static GlideImageGetter get(View view) {
		return (GlideImageGetter) view.getTag(R.id.drawable_tag);
	}

	public GlideImageGetter(Context context, TextView textView) {
		this.mContext = context;
		this.mTextView = textView;

		mTargets = new HashSet<>();
		mTextView.setTag(R.id.drawable_tag, this);
	}

	@Override
	public Drawable getDrawable(String url) {
		final UrlDrawableGlide urlDrawable = new UrlDrawableGlide();
		Logger.e("html图片地址：", url);
//		MainUtil.printLogger("Downloading from: " + url);
		Glide.with(mContext).load(url).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)
				.into(new ImageGetterViewTarget(mTextView, urlDrawable));
		return urlDrawable;
	}

	@Override
	public void invalidateDrawable(Drawable who) {
		mTextView.invalidate();
	}

	@Override
	public void scheduleDrawable(Drawable who, Runnable what, long when) {

	}

	@Override
	public void unscheduleDrawable(Drawable who, Runnable what) {

	}

	private class ImageGetterViewTarget extends ViewTarget<TextView, Drawable> {

		private final UrlDrawableGlide mDrawable;

		private ImageGetterViewTarget(TextView view, UrlDrawableGlide drawable) {
			super(view);
			mTargets.add(this);
			this.mDrawable = drawable;
		}

		@Override
		public void onResourceReady(Drawable resource, Transition<? super Drawable> glideAnimation) {
			Rect rect;
			if (resource.getIntrinsicWidth() > 100) {
				float width;
				float height;
				if (resource.getIntrinsicWidth() >= getView().getWidth()) {
					float downScale = (float) resource.getIntrinsicWidth() / getView().getWidth();
					width = (float) resource.getIntrinsicWidth() / (float) downScale;
					height = (float) resource.getIntrinsicHeight() / (float) downScale;
				} else {
					width = (float) resource.getIntrinsicWidth();
					height = (float) resource.getIntrinsicHeight();
				}
				rect = new Rect(0, 0, Math.round(width), Math.round(height));
			} else {
				rect = new Rect(0, 0, resource.getIntrinsicWidth() * 2, resource.getIntrinsicHeight() * 2);
			}
			resource.setBounds(rect);

			mDrawable.setBounds(rect);
			mDrawable.setDrawable(resource);
//
//			if (resource.isAnimated()) {
//				mDrawable.setCallback(get(getView()));
//				resource.setLoopCount(Drawable.LOOP_FOREVER);
//				resource.start();
//			}

			getView().setText(getView().getText());
			getView().invalidate();
		}

		private Request request;

		@Override
		public Request getRequest() {
			return request;
		}


		@Override
		public void setRequest(Request request) {
			this.request = request;
		}
	}

}
