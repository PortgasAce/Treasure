package com.portgas.treasure.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import com.portgas.treasure.portgastreasure.R;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  List<MainModel> mData = new ArrayList<>();

  private RecyclerView mRecyclerView;
  private MainAdapter mAdapter;
  private LayoutManager mLayoutManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    initView();
    initData();
  }

  private void initView() {
    mRecyclerView = findViewById(R.id.rv);
    mAdapter = new MainAdapter();
    mRecyclerView.setAdapter(mAdapter);
    mLayoutManager = new GridLayoutManager(this, 3);
    mRecyclerView.setLayoutManager(mLayoutManager);
  }

  private void initData() {
    mData.add(new MainModel(R.string.title_item, R.drawable.ic_launcher, MainActivity.class));
    mData.add(new MainModel(R.string.title_item, R.drawable.ic_launcher, MainActivity.class));
    mData.add(new MainModel(R.string.title_item, R.drawable.ic_launcher, MainActivity.class));
    mData.add(new MainModel(R.string.title_item, R.drawable.ic_launcher, MainActivity.class));
    mData.add(new MainModel(R.string.title_item, R.drawable.ic_launcher, MainActivity.class));

    mAdapter.setData(mData);
  }

}
