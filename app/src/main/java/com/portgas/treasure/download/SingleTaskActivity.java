package com.portgas.treasure.download;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import com.portgas.treasure.portgastreasure.R;
import java.io.File;

/**
 * 单任务测试
 */
public class SingleTaskActivity extends Activity {

  private static final String APK_URL = "http://cdn.llsapp.com/android/LLS-v4.0-595-20160908-143200.apk";

  private int downloadId;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_single);

    //点击下载按钮
    findViewById(R.id.download_btn).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        download();
      }
    });
    findViewById(R.id.pause_btn).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        pause();
      }
    });
    findViewById(R.id.delete_btn).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        delete();
      }
    });
  }

  private void pause() {
    // not work
    //FileDownloader.getImpl().create(APK_URL).pause();

    FileDownloader.getImpl().pause(downloadId);
  }

  private void delete() {
    // 删除下载完成文件
    new File(getFilePath()).delete();
    // 删除下载中文件
    new File(FileDownloadUtils.getTempPath(getFilePath())).delete();
  }

  private void download() {
    downloadId = FileDownloader.getImpl()
        .create(APK_URL)
        .setCallbackProgressTimes(300)
        .setMinIntervalUpdateSpeed(400)
        .setPath(getFilePath(), false)
        .setListener(new FileDownloadSampleListener() {

          @Override
          protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            updateProgress(totalBytes, soFarBytes);
          }

          @Override
          protected void completed(BaseDownloadTask task) {
            updateProgress(task.getSmallFileTotalBytes(), task.getSmallFileSoFarBytes());
          }
        })
        .start();
  }

  private String getFilePath() {
    return Environment.getExternalStorageDirectory().getAbsolutePath()
        + File.separator + "single"
        + File.separator + "img.png";
  }

  private void updateProgress(int max, int progress) {
    ProgressBar bar = findViewById(R.id.progressBar);
    bar.setMax(max);
    bar.setProgress(progress);
  }
}