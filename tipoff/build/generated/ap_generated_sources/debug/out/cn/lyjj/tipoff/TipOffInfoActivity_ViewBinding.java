// Generated code from Butter Knife. Do not modify!
package cn.lyjj.tipoff;

import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class TipOffInfoActivity_ViewBinding implements Unbinder {
  private TipOffInfoActivity target;

  @UiThread
  public TipOffInfoActivity_ViewBinding(TipOffInfoActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public TipOffInfoActivity_ViewBinding(TipOffInfoActivity target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.tipInfoDw = Utils.findRequiredViewAsType(source, R.id.tip_info_dw, "field 'tipInfoDw'", AppCompatTextView.class);
    target.tipInfoGrid = Utils.findRequiredViewAsType(source, R.id.tip_info_grid, "field 'tipInfoGrid'", AppCompatTextView.class);
    target.tipInfoName = Utils.findRequiredViewAsType(source, R.id.tip_info_name, "field 'tipInfoName'", AppCompatTextView.class);
    target.tipInfoSex = Utils.findRequiredViewAsType(source, R.id.tip_info_sex, "field 'tipInfoSex'", AppCompatTextView.class);
    target.tipInfoPhone = Utils.findRequiredViewAsType(source, R.id.tip_info_phone, "field 'tipInfoPhone'", AppCompatTextView.class);
    target.tipInfoIdno = Utils.findRequiredViewAsType(source, R.id.tip_info_idno, "field 'tipInfoIdno'", AppCompatTextView.class);
    target.tipInfoAddress = Utils.findRequiredViewAsType(source, R.id.tip_info_address, "field 'tipInfoAddress'", AppCompatTextView.class);
    target.tipInfoContent = Utils.findRequiredViewAsType(source, R.id.tip_info_content, "field 'tipInfoContent'", AppCompatTextView.class);
    target.tipInfoFile = Utils.findRequiredViewAsType(source, R.id.tip_info_file, "field 'tipInfoFile'", RecyclerView.class);
    target.tipInfoProgress = Utils.findRequiredViewAsType(source, R.id.tip_info_progress, "field 'tipInfoProgress'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    TipOffInfoActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.tipInfoDw = null;
    target.tipInfoGrid = null;
    target.tipInfoName = null;
    target.tipInfoSex = null;
    target.tipInfoPhone = null;
    target.tipInfoIdno = null;
    target.tipInfoAddress = null;
    target.tipInfoContent = null;
    target.tipInfoFile = null;
    target.tipInfoProgress = null;
  }
}
