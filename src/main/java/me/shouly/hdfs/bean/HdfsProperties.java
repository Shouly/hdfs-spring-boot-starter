package me.shouly.hdfs.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * HDFS config info.
 *
 * @author liangbing
 * @version v1.0
 * @date 2019-07-02 16:11
 */
@ConfigurationProperties(prefix = "spring.hdfs")
public class HdfsProperties {

  private String uri = "hdfs://localhost:9000";

  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }
}
