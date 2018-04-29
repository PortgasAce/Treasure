package com.portgas.treasure.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.portgas.treasure.portgastreasure.R;
import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter implements View.OnClickListener {

  private List<MainModel> list = new ArrayList<>();

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    @SuppressLint("InflateParams")
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.holder_main, null);
    return new MainHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    MainModel model = list.get(position);
    MainHolder mainHolder = (MainHolder) holder;
    mainHolder.imageView.setImageResource(model.imageRes);
    mainHolder.textView.setText(model.nameRes);
    mainHolder.itemView.setTag(model.activity);
    mainHolder.itemView.setOnClickListener(this);
  }

  @Override
  public int getItemCount() {
    return list.size();
  }

  @Override
  public void onClick(View v) {
    int id = v.getId();
    if (id == R.id.holder_root) {
      Class activity = (Class) v.getTag();
      openActivity(v.getContext(), activity);
    }
  }

  private void openActivity(Context context, Class activity) {
    Intent intent = new Intent(context, activity);
    context.startActivity(intent);
  }

  public void setData(List<MainModel> list) {
    this.list.addAll(list);
    notifyDataSetChanged();
  }
}