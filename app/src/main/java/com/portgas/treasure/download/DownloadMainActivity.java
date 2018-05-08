package com.portgas.treasure.download;

import com.portgas.treasure.main.MainActivity;
import com.portgas.treasure.portgastreasure.R;

public class DownloadMainActivity extends MainActivity {

  @Override
  protected void initData() {
    addData(R.string.title_download_encryption, R.drawable.ic_launcher, EncryptionActivity.class);
    addData(R.string.title_download_single, R.drawable.ic_launcher, SingleTaskActivity.class);
    addData(R.string.title_download_dynamic, R.drawable.ic_launcher, DynamicUrlActivity.class);
  }
}