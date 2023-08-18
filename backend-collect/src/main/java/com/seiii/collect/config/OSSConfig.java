package com.seiii.collect.config;

import com.aliyun.oss.OSSClient;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class OSSConfig {

    @Bean
    public OSSClient ossClient() {
        return new OSSClient(endPoint, accesskeyId, accesskeySecret);
    }

    @Value("${oss.endpoint}")
    private String endPoint;

    @Value("${oss.accessKeyId}")
    private String accesskeyId;

    @Value("${oss.accessKeySecret}")
    private String accesskeySecret;

    @Value("${oss.bucketName}")
    private String bucketName;

    @Value("${oss.dir}")
    private String dir;

    @Value("${oss.maxSize}")
    private String maxSize;

    @Value("${oss.callback}")
    private String callback;

}
