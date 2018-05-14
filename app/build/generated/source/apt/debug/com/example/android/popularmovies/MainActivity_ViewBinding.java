// Generated code from Butter Knife. Do not modify!
package com.example.android.popularmovies;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity_ViewBinding implements Unbinder {
  private MainActivity target;

  @UiThread
  public MainActivity_ViewBinding(MainActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MainActivity_ViewBinding(MainActivity target, View source) {
    this.target = target;

    target.connectionTextView = Utils.findRequiredViewAsType(source, R.id.no_connection, "field 'connectionTextView'", TextView.class);
    target.networkExceptionTextView = Utils.findRequiredViewAsType(source, R.id.network_exception, "field 'networkExceptionTextView'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MainActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.connectionTextView = null;
    target.networkExceptionTextView = null;
  }
}
