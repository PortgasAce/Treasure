package com.portgas.treasure.main;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

public class MainModel {

  @StringRes
  protected int nameRes;
  @DrawableRes
  protected int imageRes;
  protected Class activity;

  public MainModel(@StringRes int nameRes, @DrawableRes int imageRes, Class activity) {
    this.nameRes = nameRes;
    this.imageRes = imageRes;
    this.activity = activity;
  }
}