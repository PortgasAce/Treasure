package com.portgas.treasure.download;

import static com.portgas.treasure.download.FixOutputStreamCreator.ENCRYPTION_FLAG;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.portgas.treasure.portgastreasure.R;
import java.io.File;


/**
 * 下载图片，并在图片头部加入前缀，尾部加入后缀
 * 通过自定义的方法加载图片
 * 如果使用Glide可以自定义loader实现。
 */
public class EncryptionActivity extends Activity {

  private static final String APK_URL = "http://cdn.llsapp.com/android/LLS-v4.0-595-20160908-143200.apk";
  private static final String IMAGE_URL = "http://ow0ekshxd.bkt.clouddn.com/sketch_test1.png";

  private ImageView imageView;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_encrypt);

    imageView = findViewById(R.id.iv);
    //点击下载按钮
    findViewById(R.id.download_btn).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        download();
      }
    });
  }

  private void download() {
    FileDownloader.getImpl()
        .create(IMAGE_URL)
        .setCallbackProgressTimes(300)
        .setMinIntervalUpdateSpeed(400)
        .setPath(
            Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + ENCRYPTION_FLAG  //识别目录加密
                + File.separator + "img.png", false)
        .setListener(new FileDownloadSampleListener() {

          @Override
          protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            updateProgress(totalBytes, soFarBytes);
          }

          @Override
          protected void completed(BaseDownloadTask task) {
            updateProgress(task.getSmallFileTotalBytes(), task.getSmallFileSoFarBytes());
            String path = task.getPath();
            setImage(path);
          }
        })
        .start();
  }

  private void setImage(String path) {
    try {
      byte[] array = DownloadUtil.decodeImageFile(new File(path));
      imageView.setImageBitmap(
          BitmapFactory.decodeByteArray(array, 0, array.length));
    } catch (Throwable e) {
      e.printStackTrace();
    }
  }

  private void updateProgress(int max, int progress) {
    ProgressBar bar = findViewById(R.id.progressBar);
    bar.setMax(max);
    bar.setProgress(progress);
  }
}