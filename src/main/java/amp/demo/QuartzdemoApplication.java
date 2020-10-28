package amp.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author licheng:2020/1/4
 */
@SpringBootApplication
@MapperScan("amp.demo.mapper")
public class QuartzdemoApplication {
    private static final Logger logger = LoggerFactory.getLogger(QuartzdemoApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(QuartzdemoApplication.class, args);
        logger.info("服務端啟動成功...");
    }

}
