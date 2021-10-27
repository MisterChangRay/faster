package com.github.misterchangray.idservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
@EnableConfigurationProperties({IDServiceConfig.class})
public class IDServiceAutoConfiguration {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean
    public IDService idService(IDServiceConfig idServiceConfig) {
        if(idServiceConfig.getAppId() == 0) {
            logger.warn("\r\n=========================== IDService =========================================\r\n" +
                    "请注意: IDService 未发现有效配置, appId=0仅用于生成临时ID或ID信息反解, 不保证全局唯一 \r\n" +
                    "如需要生成全局唯一ID， IDService 需要配置 appId, instanceId 并确保全局唯一. 请安装参考文档使用");
        }
        IDService idService = new IDService();
        idService.setAppId(idServiceConfig.getAppId());
        idService.setAppInstanceId(idServiceConfig.getAppInstanceId());
        logger.info("IDService 初始化成功, appId: {}, appInstanceId: {}!", idServiceConfig.getAppId(), idServiceConfig.getAppInstanceId());
        return idService;
    }


}
