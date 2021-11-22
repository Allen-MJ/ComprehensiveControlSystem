// Generated code from Butter Knife. Do not modify!
package cn.lyjj.tipoff;

import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SmartTipActivity_ViewBinding implements Unbinder {
  private SmartTipActivity target;

  private View view810;

  private View view80e;

  @UiThread
  public SmartTipActivity_ViewBinding(SmartTipActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SmartTipActivity_ViewBinding(final SmartTipActivity target, View source) {
    this.target = target;

    View view;
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    view = Utils.findRequiredView(source, R.id.tip_dw, "field 'tipDw' and method 'onViewClicked'");
    target.tipDw = Utils.castView(view, R.id.tip_dw, "field 'tipDw'", AppCompatTextView.class);
    view810 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.tipContent = Utils.findRequiredViewAsType(source, R.id.tip_content, "field 'tipContent'", AppCompatEditText.class);
    target.tipFile = Utils.findRequiredViewAsType(source, R.id.tip_file, "field 'tipFile'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.tip_bt, "field 'tipBt' and method 'onViewClicked'");
    target.tipBt = Utils.castView(view, R.id.tip_bt, "field 'tipBt'", AppCompatButton.class);
    view80e = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    SmartTipActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.tipDw = null;
    target.tipContent = null;
    target.tipFile = null;
    target.tipBt = null;

    view810.setOnClickListener(null);
    view810 = null;
    view80e.setOnClickListener(null);
    view80e = null;
  }
}
