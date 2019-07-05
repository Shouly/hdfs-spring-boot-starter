package me.shouly.hdfs.conf;

import me.shouly.hdfs.api.HdfsOperationApi;
import me.shouly.hdfs.bean.HdfsProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * HDFS auto configuration.
 *
 * @author liangbing
 * @version v1.0
 * @date 2019-07-02 16:16
 */
@Configuration
@ConditionalOnClass(HdfsOperationApi.class)
@EnableConfigurationProperties(HdfsProperties.class)
public class HdfsAutoConfiguration {

  @Autowired private HdfsProperties hdfsProperties;

  @Bean
  @ConditionalOnMissingBean(HdfsOperationApi.class)
  public HdfsOperationApi hdfsOperationApi() {
    return new HdfsOperationApi(hdfsProperties);
  }
}
