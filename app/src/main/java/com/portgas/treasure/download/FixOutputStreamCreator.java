package com.portgas.treasure.download;

import android.util.Log;
import com.liulishuo.filedownloader.stream.FileDownloadOutputStream;
import com.liulishuo.filedownloader.util.FileDownloadHelper.OutputStreamCreator;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

//文件前后加一些字节，防止文件浏览器直接查看
public class FixOutputStreamCreator implements OutputStreamCreator {

  public static final String ENCRYPTION_FLAG = "Encryption";

  @Override
  public FileDownloadOutputStream create(File file) throws IOException {
    //switch file
    if (file.getAbsolutePath().contains(ENCRYPTION_FLAG)) {
      return new FixDownloadOutputStream(file);
    }

    //default
    return new DefaultDownloadFile(file);
  }

  @Override
  public boolean supportSeek() {
    return true;
  }

  public static class FixDownloadOutputStream implements FileDownloadOutputStream {

    private static final String TAG = "777";
    public static final String PREFIX = "Treasure";
    public static final String SUFFIX = "Portgas";

    BufferedOutputStream out;
    FileDescriptor fd;
    RandomAccessFile randomAccess;
    long length;

    public FixDownloadOutputStream(File file) throws IOException {
      randomAccess = new RandomAccessFile(file, "rw");
      fd = randomAccess.getFD();
      out = new BufferedOutputStream(new FileOutputStream(randomAccess.getFD()));
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {

      // write prefix
      if (length == 0) {
        out.write(PREFIX.getBytes(), 0, PREFIX.getBytes().length);
        length += PREFIX.getBytes().length;
      }

      // write content
      out.write(b, off, len);
      length += len;

      // write suffix
      if (length == (randomAccess.length() - SUFFIX.getBytes().length)) {
        out.write(SUFFIX.getBytes(), 0, SUFFIX.getBytes().length);
        length += SUFFIX.getBytes().length;
      }

      Log.d(TAG,
          "write: off = " + off + ",len = " + len + ",randomAccess.length = " + randomAccess
              .length() + ",length = " + length);
    }

    @Override
    public void flushAndSync() throws IOException {
      out.flush();
      fd.sync();
    }

    @Override
    public void close() throws IOException {
      out.close();
      randomAccess.close();
      Log.d(TAG, "close: " + length);
    }

    @Override
    public void seek(long offset) throws IOException, IllegalAccessException {
      Log.d(TAG, "seek: offset = " + offset);
      randomAccess.seek(offset);
    }

    @Override
    public void setLength(long newLength) throws IOException, IllegalAccessException {
      Log.d(TAG, "setLength: newLength = " + newLength + ",randomAccess.length = " + randomAccess
          .length());
      randomAccess.setLength(newLength + PREFIX.getBytes().length + SUFFIX.getBytes().length);
    }
  }


  static class DefaultDownloadFile implements FileDownloadOutputStream {

    private final BufferedOutputStream out;
    private final FileDescriptor fd;
    private final RandomAccessFile randomAccess;

    DefaultDownloadFile(File file) throws IOException {
      randomAccess = new RandomAccessFile(file, "rw");
      fd = randomAccess.getFD();
      out = new BufferedOutputStream(new FileOutputStream(randomAccess.getFD()));
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
      out.write(b, off, len);
    }

    @Override
    public void flushAndSync() throws IOException {
      out.flush();
      fd.sync();
    }

    @Override
    public void close() throws IOException {
      out.close();
      randomAccess.close();
    }

    @Override
    public void seek(long offset) throws IOException {
      randomAccess.seek(offset);
    }

    @Override
    public void setLength(long totalBytes) throws IOException {
      randomAccess.setLength(totalBytes);
    }
  }
}