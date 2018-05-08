package com.portgas.treasure.download;

import static com.portgas.treasure.download.FixOutputStreamCreator.FixDownloadOutputStream.PREFIX;
import static com.portgas.treasure.download.FixOutputStreamCreator.FixDownloadOutputStream.SUFFIX;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class DownloadUtil {

  public static byte[] decodeImageFile(File file) throws Exception {
    InputStream inputStream;
    if (!file.exists()) {
      return null;
    }

    inputStream = new FileInputStream(file);

    int totalLength = inputStream.available();
    int startLength = PREFIX.getBytes().length;
    int endLength = SUFFIX.getBytes().length;
    byte[] bufferStart = new byte[startLength];
    byte[] bufferEnd = new byte[endLength];

    int contentLength = totalLength - (startLength + endLength);
    if (contentLength > 0) {
      byte[] bufferReal = new byte[contentLength];
      //先读出头部的内容
      inputStream.read(bufferStart, 0, startLength);
      //再读出图片存储的流的正式内容
      inputStream.read(bufferReal, 0, contentLength);
      //最后读出结尾的标识
      inputStream.read(bufferEnd, 0, endLength);
      //读完关闭
      inputStream.close();
      return bufferReal;
    } else {
      //完全没有按照加密的方式存的文件，直接关闭流，返回null
      inputStream.close();
    }
    return null;
  }


}