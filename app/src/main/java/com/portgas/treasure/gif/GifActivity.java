package com.portgas.treasure.gif;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.portgas.treasure.gif.library.decoder.GifDecoder;
import com.portgas.treasure.gif.library.decoder.GifDecoder.BitmapProvider;
import com.portgas.treasure.gif.library.decoder.StandardGifDecoder;
import com.portgas.treasure.gif.library.decoder.bitmap.ArrayPool;
import com.portgas.treasure.gif.library.decoder.bitmap.GifBitmapProvider;
import com.portgas.treasure.gif.library.decoder.bitmap.LruArrayPool;
import com.portgas.treasure.gif.library.decoder.bitmap.LruBitmapPool;
import com.portgas.treasure.portgastreasure.R;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

public class GifActivity extends Activity {

  private static String TAG = "gif";
  private static final long MAX_BITMAP_SIZE = 10 * 1024 * 1024;  //10 MB
  private static final int MAX_ARRAY_SIZE = 100; // 100个

  private HandlerThread mThread;
  private Handler mHandler;
  private Button mDecodeBtn;
  private Button mEncodeBtn;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_gif);

    mThread = new HandlerThread("gif");
    mThread.start();
    mHandler = new Handler(mThread.getLooper());

    mDecodeBtn = findViewById(R.id.btn_decode);
    mEncodeBtn = findViewById(R.id.btn_encode);

    mDecodeBtn.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        mHandler.post(new Runnable() {
          @Override
          public void run() {
            decode();
          }
        });
      }
    });

    mEncodeBtn.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        mHandler.post(new Runnable() {
          @Override
          public void run() {
            encode();
          }
        });
      }
    });
  }

  private void decode() {
    String path = Environment.getExternalStorageDirectory() + File.separator + "gif";
    File gifFile = new File(path, "test.gif");
    InputStream inputStream = null;
    try {
      inputStream = new FileInputStream(gifFile);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    if (inputStream == null) {
      return;
    }
    LruBitmapPool mLruBitmapPool = new LruBitmapPool(MAX_BITMAP_SIZE);
    ArrayPool mArrayPool = new LruArrayPool(MAX_ARRAY_SIZE);
    BitmapProvider bitmapProvider = new GifBitmapProvider(mLruBitmapPool, mArrayPool);
    GifDecoder gifDecoder = new StandardGifDecoder(bitmapProvider);
    gifDecoder.read(inputStream, 0);
    for (int i = 0; i < gifDecoder.getFrameCount(); i++) {
      gifDecoder.advance();
      Bitmap bitmap = gifDecoder.getNextFrame();
      int delay = gifDecoder.getDelay(i);
      saveBitmapToLocal(path + File.separator + "decode", i + "_" + delay, bitmap);
      Log.d(TAG, "decodeGifPrivate: i = " + i + ",bitmap = " + bitmap);
    }
  }

  private void encode() {

  }

  public static void saveBitmapToLocal(String path, String fileName, Bitmap bitmap) {
    try {
      // 创建文件流，指向该路径，文件名叫做fileName
      File file = new File(path, fileName);
      // file其实是图片，它的父级File是文件夹，判断一下文件夹是否存在，如果不存在，创建文件夹
      File fileParent = file.getParentFile();
      if (!fileParent.exists()) {
        // 文件夹不存在
        fileParent.mkdirs();// 创建文件夹
      }
      // 将图片保存到本地
      bitmap.compress(Bitmap.CompressFormat.JPEG, 100,
          new FileOutputStream(file));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}