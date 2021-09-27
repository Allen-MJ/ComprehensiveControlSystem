package allen.frame.tools;

import android.view.View;

public interface OnAdapterItemClickListener<T> {
    void itemClick(View view, int index, T t);
}
