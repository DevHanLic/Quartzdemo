package amp.demo.service;


import amp.demo.entity.ScheduleJob;
import org.quartz.SchedulerException;

public interface QuartzService {

    /**
     * 服务器启动执行定时任务
     *
     * @author licheng
     * @date 2020/02/20
     */
    void timingTask();

    /**
     * 新增定时任务
     *
     * @author licheng
     * @date 2020/02/20
     * @param job 任务
     */
    void addJob(ScheduleJob job);

    /**
     * 修改定时任务配置
     * @param job
     */
    void update(ScheduleJob job);
    /**
     * 操作定时任务
     *
     * @author licheng
     * @date 2020/02/20
     * @param job 任务
     */
    void operateJob(String str,ScheduleJob job) throws SchedulerException;


}
