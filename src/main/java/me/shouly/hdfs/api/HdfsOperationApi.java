package me.shouly.hdfs.api;

import lombok.extern.slf4j.Slf4j;
import me.shouly.hdfs.bean.HdfsProperties;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;

/**
 * hdfs operation api.
 *
 * @author liangbing
 * @version v1.0
 * @date 2019-07-02 16:21
 */
@Slf4j
public class HdfsOperationApi {

  private Configuration conf;

  private static final String SLASH = "/";

  private String defaultHdfsUri;

  private FileSystem fileSystem;

  public HdfsOperationApi(HdfsProperties hdfsProperties) {

    defaultHdfsUri = hdfsProperties.getUri();

    conf = new Configuration();

    conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
    conf.set("fs.defaultFS", hdfsProperties.getUri());

    try {

      fileSystem = FileSystem.get(conf);

    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }

  /**
   * build hdfs path
   *
   * @param path
   * @return
   */
  private String buildHdfsPath(String path) {

    if (path.startsWith(SLASH)) {

      defaultHdfsUri += path;
    } else {

      defaultHdfsUri = defaultHdfsUri + SLASH + path;
    }
    return defaultHdfsUri;
  }

  /**
   * 检查文件或目录是否存在
   *
   * @param path hdfs相对目录 /dir、/dir/test.txt
   * @return
   */
  public boolean checkExists(String path) {

    String hdfsPath = buildHdfsPath(path);

    try {
      return fileSystem.exists(new Path(hdfsPath));
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return false;
    }
  }

  /**
   * mkdir
   *
   * @param path
   * @return
   */
  public boolean mkdir(String path) {

    if (!checkExists(path)) {

      try {
        return fileSystem.mkdirs(new Path(buildHdfsPath(path)));
      } catch (Exception e) {
        log.error(e.getMessage(), e);
      }
    }

    return false;
  }

  /**
   * 创建文件
   *
   * @param path
   * @return
   */
  public boolean create(String path) {

    if (!checkExists(path)) {
      try {

        fileSystem.create(new Path(path));

      } catch (IOException e) {
        log.error(e.getMessage(), e);
        return false;
      }
    }

    return true;
  }

  /**
   * 删除文件或目录
   *
   * @param path
   * @return
   */
  public boolean delete(String path) {
    try {

      return fileSystem.delete(new Path(path), true);

    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }

    return false;
  }
}
