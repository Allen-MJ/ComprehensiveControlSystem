// Generated code from Butter Knife. Do not modify!
package cn.lyjj.tipoff;

import allen.frame.widget.SearchView;
import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class TipOffListActivity_ViewBinding implements Unbinder {
  private TipOffListActivity target;

  @UiThread
  public TipOffListActivity_ViewBinding(TipOffListActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public TipOffListActivity_ViewBinding(TipOffListActivity target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.search = Utils.findRequiredViewAsType(source, R.id.search, "field 'search'", SearchView.class);
    target.rv = Utils.findRequiredViewAsType(source, R.id.rv, "field 'rv'", RecyclerView.class);
    target.refresh = Utils.findRequiredViewAsType(source, R.id.refresh, "field 'refresh'", SmartRefreshLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    TipOffListActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.search = null;
    target.rv = null;
    target.refresh = null;
  }
}
