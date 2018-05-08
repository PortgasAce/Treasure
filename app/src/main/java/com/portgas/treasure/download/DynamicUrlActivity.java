package com.portgas.treasure.download;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.portgas.treasure.portgastreasure.R;
import java.io.File;

/**
 * 处理防盗链 & 动态url：
 * 建立 model-> 下载目录-> id的映射规则，
 * FileDownload 的 IdGenerator 只能获取到 下载的url和下载目录。
 * 所以 model根据规则产生下载目录，IdGenerator根据目录判断model的下载状态，而不是根据动态的url。
 */

public class DynamicUrlActivity extends Activity {

  private static final String URL_1 = "http://ow0ekshxd.bkt.clouddn.com/remote_exception_trend1.png";
  private static final String URL_2 = "http://ow0ekshxd.bkt.clouddn.com/remote_exception_trend2.png";

  private static class Model {

    int id;
    String url;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_dynamic);

    initView();
  }

  @SuppressLint("SetTextI18n")
  private void initView() {
    TextView textView = findViewById(R.id.tv);
    textView.setText("model 1:id = 1,url = " + URL_1 + "\n"
        + "model 2:id = 1,url = " + URL_2);

    Button button1 = findViewById(R.id.download_btn1);
    Button button2 = findViewById(R.id.download_btn2);

    button1.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Model model1 = new Model();
        model1.id = 1;
        model1.url = URL_1;
        download(model1);
      }
    });

    button2.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Model model2 = new Model();
        model2.id = 1;
        model2.url = URL_2;
        download(model2);
      }
    });
  }

  private void download(final Model model) {
    long downloadId = FileDownloader.getImpl()
        .create(model.url)
        .setCallbackProgressTimes(300)
        .setMinIntervalUpdateSpeed(400)
        .setPath(
            Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator
                + IdGenerator.DYNAMIC_FLAG  //flag
                + File.separator + model.id + ".png",
            false
        )
        .setListener(new FileDownloadSampleListener() {

          @Override
          protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            ProgressBar bar = (ProgressBar) (model.url.equals(URL_1) ?
                findViewById(R.id.progressBar_1)
                : findViewById(R.id.progressBar_2));
            updateProgress(bar, totalBytes, soFarBytes);
          }

          @Override
          protected void completed(BaseDownloadTask task) {
            ProgressBar bar = (ProgressBar) (model.url.equals(URL_1) ?
                findViewById(R.id.progressBar_1)
                : findViewById(R.id.progressBar_2));
            updateProgress(bar, task.getSmallFileTotalBytes(), task.getSmallFileTotalBytes());
          }
        })
        .start();
  }

  private void updateProgress(ProgressBar bar, int max, int progress) {
    bar.setMax(max);
    bar.setProgress(progress);
  }
}