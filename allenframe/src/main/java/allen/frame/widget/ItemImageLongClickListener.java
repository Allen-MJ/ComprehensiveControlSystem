package allen.frame.widget;

import android.content.Context;
import android.widget.ImageView;
import java.util.List;

/**
 * @author yueban
 * Date: 2017/9/19
 * Email: fbzhh007@gmail.com
 */
public interface ItemImageLongClickListener<T> {
    boolean onItemImageLongClick(Context context, ImageView imageView, int index, List<T> list);
}
