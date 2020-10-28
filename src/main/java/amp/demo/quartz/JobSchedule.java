package amp.demo.quartz;

import amp.demo.service.QuartzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class JobSchedule implements CommandLineRunner {

    @Autowired
    private QuartzService taskService;

    /**
     * 程序启动的时候执行
     * @param strings
     * @throws Exception
     */
    @Override
    public void run(String... strings) throws Exception {
        new Thread(){
            @Override
            public void run() {
                System.out.println("任务调度开始==============任务调度开始");
                taskService.timingTask();
                System.out.println("任务调度结束==============任务调度结束");
            }
        }.start();

    }
}
