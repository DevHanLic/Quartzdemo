package amp.demo.quartz.job;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author HLC
 */
@Component("testJob02")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class TestJob02 {

    public void execute() {
        System.out.println("-------------------TestJob02任务执行开始-------------------");
        System.out.println(new Date());
        System.out.println("-------------------TestJob02任务执行结束-------------------");
    }
}