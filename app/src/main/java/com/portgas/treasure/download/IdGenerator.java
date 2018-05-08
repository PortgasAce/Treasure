package com.portgas.treasure.download;

import static com.liulishuo.filedownloader.util.FileDownloadUtils.formatString;

import android.text.TextUtils;
import com.liulishuo.filedownloader.util.FileDownloadHelper;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import java.io.File;

// 处理防盗链 & 动态url
// 建立 model-> 下载目录-> id的映射规则
public class IdGenerator implements FileDownloadHelper.IdGenerator {

  public static final String DYNAMIC_FLAG = "dynamic";

  @Override
  public int transOldId(int oldId, String url, String path, boolean pathAsDirectory) {

    return generateId(url, path, pathAsDirectory);
  }

  @Override
  public int generateId(String url, String path, boolean pathAsDirectory) {

    //这里只对视频缓存做id处理 /offData/文件夹
    if (path.contains(DYNAMIC_FLAG)) {
      if (TextUtils.isEmpty(url)) {
        throw new IllegalArgumentException("need auth");
      }

      File file = new File(path);
      String id = file.getName(); //文件目录为 dynamic/id/ ,取出id

      if (pathAsDirectory) {
        return FileDownloadUtils.md5(formatString("%sp%s@dir", id, path))
            .hashCode();
      } else {
        return FileDownloadUtils.md5(formatString("%sp%s", id, path))
            .hashCode();
      }
    }

    return generateDefaultId(url, path, pathAsDirectory);
  }

  private int generateDefaultId(String url, String path, boolean pathAsDirectory) {
    if (pathAsDirectory) {
      return FileDownloadUtils.md5(formatString("%sp%s@dir", url, path)).hashCode();
    } else {
      return FileDownloadUtils.md5(formatString("%sp%s", url, path)).hashCode();
    }
  }

}