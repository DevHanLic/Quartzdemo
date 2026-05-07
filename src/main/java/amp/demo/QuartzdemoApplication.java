package amp.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
@MapperScan("amp.demo.mapper")
public class QuartzdemoApplication {
    private static final Logger logger = LoggerFactory.getLogger(QuartzdemoApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(QuartzdemoApplication.class, args);
        logger.info("Quartz Demo Application 启动成功！");
        logger.info("API文档地址: http://localhost:8082/swagger-ui.html");
    }
}
