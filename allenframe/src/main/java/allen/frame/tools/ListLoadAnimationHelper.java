package allen.frame.tools;

import android.content.Context;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.recyclerview.widget.RecyclerView;

import allen.frame.R;


public class ListLoadAnimationHelper {
	
	public static void fallDownAnimation(final RecyclerView recyclerView) {
		final Context context = recyclerView.getContext();
		final LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(context,
				R.anim.layout_animation_fall_down);

		recyclerView.setLayoutAnimation(controller);
		recyclerView.getAdapter().notifyDataSetChanged();
		recyclerView.scheduleLayoutAnimation();
	}
}
