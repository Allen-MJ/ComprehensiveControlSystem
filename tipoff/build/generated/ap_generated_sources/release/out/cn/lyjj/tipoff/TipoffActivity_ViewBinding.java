// Generated code from Butter Knife. Do not modify!
package cn.lyjj.tipoff;

import android.view.View;
import android.widget.RadioGroup;
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

public class TipoffActivity_ViewBinding implements Unbinder {
  private TipoffActivity target;

  private View view802;

  private View view805;

  private View view800;

  @UiThread
  public TipoffActivity_ViewBinding(TipoffActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public TipoffActivity_ViewBinding(final TipoffActivity target, View source) {
    this.target = target;

    View view;
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    view = Utils.findRequiredView(source, R.id.tip_dw, "field 'tipDw' and method 'onViewClicked'");
    target.tipDw = Utils.castView(view, R.id.tip_dw, "field 'tipDw'", AppCompatTextView.class);
    view802 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.tipFyr = Utils.findRequiredViewAsType(source, R.id.tip_fyr, "field 'tipFyr'", AppCompatEditText.class);
    target.tipSfz = Utils.findRequiredViewAsType(source, R.id.tip_sfz, "field 'tipSfz'", AppCompatEditText.class);
    target.tipPhone = Utils.findRequiredViewAsType(source, R.id.tip_phone, "field 'tipPhone'", AppCompatEditText.class);
    target.tipSex = Utils.findRequiredViewAsType(source, R.id.tip_sex, "field 'tipSex'", RadioGroup.class);
    view = Utils.findRequiredView(source, R.id.tip_grid, "field 'tipGrid' and method 'onViewClicked'");
    target.tipGrid = Utils.castView(view, R.id.tip_grid, "field 'tipGrid'", AppCompatTextView.class);
    view805 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.tipAddress = Utils.findRequiredViewAsType(source, R.id.tip_address, "field 'tipAddress'", AppCompatEditText.class);
    target.tipContent = Utils.findRequiredViewAsType(source, R.id.tip_content, "field 'tipContent'", AppCompatEditText.class);
    target.tipFile = Utils.findRequiredViewAsType(source, R.id.tip_file, "field 'tipFile'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.tip_bt, "field 'tipBt' and method 'onViewClicked'");
    target.tipBt = Utils.castView(view, R.id.tip_bt, "field 'tipBt'", AppCompatButton.class);
    view800 = view;
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
    TipoffActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.tipDw = null;
    target.tipFyr = null;
    target.tipSfz = null;
    target.tipPhone = null;
    target.tipSex = null;
    target.tipGrid = null;
    target.tipAddress = null;
    target.tipContent = null;
    target.tipFile = null;
    target.tipBt = null;

    view802.setOnClickListener(null);
    view802 = null;
    view805.setOnClickListener(null);
    view805 = null;
    view800.setOnClickListener(null);
    view800 = null;
  }
}
