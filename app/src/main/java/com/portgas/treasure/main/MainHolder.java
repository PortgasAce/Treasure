package com.portgas.treasure.main;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.portgas.treasure.portgastreasure.R;

public class MainHolder extends RecyclerView.ViewHolder {

  public ImageView imageView;
  public TextView textView;

  public MainHolder(View itemView) {
    super(itemView);
    imageView = itemView.findViewById(R.id.holder_iv);
    textView = itemView.findViewById(R.id.holder_tv);
  }
}