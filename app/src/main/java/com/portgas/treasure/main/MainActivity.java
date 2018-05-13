package com.portgas.treasure.main;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import com.portgas.treasure.GlideActivity;
import com.portgas.treasure.download.DownloadMainActivity;
import com.portgas.treasure.gif.GifActivity;
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

    mAdapter.setData(mData);
  }

  private void initView() {
    mRecyclerView = findViewById(R.id.rv);
    mAdapter = new MainAdapter();
    mRecyclerView.setAdapter(mAdapter);
    mLayoutManager = new GridLayoutManager(this, 3);
    mRecyclerView.setLayoutManager(mLayoutManager);
  }

  protected void initData() {
    addData(R.string.title_download, R.drawable.ic_launcher, DownloadMainActivity.class);
    addData(R.string.title_glide, R.drawable.ic_launcher, GlideActivity.class);
    addData(R.string.title_gif, R.drawable.ic_launcher, GifActivity.class);
    addData(R.string.title_item, R.drawable.ic_launcher, MainActivity.class);
    addData(R.string.title_item, R.drawable.ic_launcher, MainActivity.class);
  }

  public void addData(@StringRes int title, @DrawableRes int icon, Class activity) {
    mData.add(new MainModel(title, icon, activity));
  }

}
