package com.portgas.treasure;

import android.app.Application;
import com.facebook.stetho.Stetho;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.connection.FileDownloadUrlConnection;
import com.portgas.treasure.download.IdGenerator;
import com.portgas.treasure.download.FixOutputStreamCreator;

public class TreasureApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

    init();
  }

  private void init() {
    //default setting
    //FileDownloader.setup(this);

    //custom setting
    FileDownloader.setupOnApplicationOnCreate(this)
        .idGenerator(new IdGenerator())
        .outputStreamCreator(new FixOutputStreamCreator())
        .connectionCreator(new FileDownloadUrlConnection
            .Creator(new FileDownloadUrlConnection.Configuration()
            .connectTimeout(15_000) // set connection timeout.
            .readTimeout(15_000) // set read timeout.
        ))
        .commit();

    //for database debug
    Stetho.initializeWithDefaults(this);
  }

}