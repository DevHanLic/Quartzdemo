package amp.demo.quartz.job;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component("testJob03")
@Transactional
public class TestJob03 {

    public void execute() {
        System.out.println("-------------------TestJob03任务执行开始-------------------");
        System.out.println(new Date());
        System.out.println("-------------------TestJob03任务执行结束-------------------");
    }
}
