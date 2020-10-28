package amp.demo.service.impl;


import amp.demo.entity.ScheduleJob;
import amp.demo.mapper.ScheduleJobMapper;
import amp.demo.quartz.QuartzFactory;
import amp.demo.service.QuartzService;
import amp.demo.service.ScheduleJobService;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class QuartzServiceImpl implements QuartzService {
    private static final Logger logger= LoggerFactory.getLogger(QuartzService.class);
    /**
     * 调度器
     */
    @Autowired
    private Scheduler scheduler;

    @Autowired
    private ScheduleJobService jobService;

    @Autowired
    private ScheduleJobMapper scheduleJobMapper;
    @Override
    public void timingTask() {
        //查询数据库是否存在需要执行的定时任务
        List<ScheduleJob> scheduleJobs = scheduleJobMapper.selectStart();
        if (scheduleJobs != null) {
            scheduleJobs.forEach(this::addJob);
        }else {
            System.out.println("当前没有无执行的定时任务");
        }
    }

    @Override
    public void addJob(ScheduleJob job) {
        try {
            //创建触发器
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName())
                    .withSchedule(CronScheduleBuilder.cronSchedule(job.getCronExpression()))
                   /* .startNow()*/
                    .build();

            //创建任务
            JobDetail jobDetail = JobBuilder.newJob(QuartzFactory.class)
                    .withIdentity(job.getJobName())
                    .build();

            //传入调度的数据，在QuartzFactory中需要使用
            jobDetail.getJobDataMap().put("scheduleJob", job);

            //调度作业
            scheduler.scheduleJob(jobDetail, trigger);

        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    @Override
    public void update(ScheduleJob job) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName());
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            if (trigger == null) {
                logger.info("触发器异常");
            }
            String oldTime = trigger.getCronExpression();
            if(!oldTime.equalsIgnoreCase(job.getCronExpression())){
                /**方法一：只修改cron表达式**/
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression())// 重新设置执行时间
                        .withMisfireHandlingInstructionDoNothing();// 不触发立即执行
                trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
                scheduler.rescheduleJob(triggerKey, trigger);
                /**方法二：修改了很多参数**/
                //JobKey jobKey = new JobKey(job.getJobName());
                //先删除
                //scheduler.resumeJob(jobKey);
                //后创建
                //addJob(job);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void operateJob(String jobOperate,ScheduleJob job) throws SchedulerException {
        JobKey jobKey = new JobKey(job.getJobName());
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            logger.info("job实例不存在");
        }
        switch (jobOperate) {
            case "RESUME":
                scheduler.resumeJob(jobKey);
                break;
            case "PAUSE":
                scheduler.pauseJob(jobKey);
                break;
            case "DELETE":
                scheduler.deleteJob(jobKey);
                break;
        }
    }
}

