package amp.demo.service.impl;

import amp.demo.entity.ScheduleJob;
import amp.demo.exception.BusinessException;
import amp.demo.mapper.ScheduleJobMapper;
import amp.demo.quartz.QuartzFactory;
import amp.demo.service.ScheduleJobService;
import amp.demo.service.QuartzService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuartzServiceImpl implements QuartzService {
    private static final Logger logger = LoggerFactory.getLogger(QuartzService.class);

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private ScheduleJobMapper scheduleJobMapper;

    @Autowired
    @Lazy
    private ScheduleJobService jobService;

    @Override
    @Transactional(readOnly = true)
    public void timingTask() {
        logger.info("开始定时任务扫描");
        List<ScheduleJob> scheduleJobs = scheduleJobMapper.selectStart();
        
        if (scheduleJobs != null && !scheduleJobs.isEmpty()) {
            scheduleJobs.forEach(this::addJob);
            logger.info("已加载 {} 个定时任务", scheduleJobs.size());
        } else {
            logger.info("当前没有需要执行的定时任务");
        }
    }

    @Override
    public void addJob(ScheduleJob job) {
        if (job == null || job.getJobName() == null) {
            throw new BusinessException("任务信息不能为空");
        }

        try {
            JobDetail jobDetail = JobBuilder.newJob(QuartzFactory.class)
                    .withIdentity(job.getJobName())
                    .build();

            jobDetail.getJobDataMap().put("scheduleJob", job);

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(job.getJobName())
                    .withSchedule(CronScheduleBuilder.cronSchedule(job.getCronExpression()))
                    .build();

            scheduler.scheduleJob(jobDetail, trigger);
            logger.info("定时任务 {} 添加成功，Cron表达式: {}", job.getJobName(), job.getCronExpression());
        } catch (Exception e) {
            logger.error("添加定时任务失败: {}", job.getJobName(), e);
            throw new BusinessException("添加定时任务失败: " + e.getMessage());
        }
    }

    @Override
    public void update(ScheduleJob job) {
        if (job == null || job.getJobName() == null) {
            throw new BusinessException("任务信息不能为空");
        }

        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName());
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            
            if (trigger == null) {
                throw new BusinessException("触发器不存在");
            }

            String oldTime = trigger.getCronExpression();
            if (!oldTime.equalsIgnoreCase(job.getCronExpression())) {
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression())
                        .withMisfireHandlingInstructionDoNothing();
                
                trigger = trigger.getTriggerBuilder()
                        .withSchedule(scheduleBuilder)
                        .build();
                
                scheduler.rescheduleJob(triggerKey, trigger);
                logger.info("定时任务 {} 执行时间已更新: {} -> {}", job.getJobName(), oldTime, job.getCronExpression());
            }
        } catch (SchedulerException e) {
            logger.error("更新定时任务失败: {}", job.getJobName(), e);
            throw new BusinessException("更新定时任务失败: " + e.getMessage());
        }
    }

    @Override
    public void operateJob(String jobOperate, ScheduleJob job) {
        if (job == null || job.getJobName() == null) {
            throw new BusinessException("任务信息不能为空");
        }

        try {
            JobKey jobKey = new JobKey(job.getJobName());
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            
            if (jobDetail == null) {
                throw new BusinessException("任务实例不存在");
            }

            JobOperation operation = JobOperation.fromCode(jobOperate);
            
            switch (operation) {
                case RESUME:
                    scheduler.resumeJob(jobKey);
                    jobService.updateJobStatus(job.getId(), 1);
                    logger.info("定时任务 {} 已恢复", job.getJobName());
                    break;
                case PAUSE:
                    scheduler.pauseJob(jobKey);
                    jobService.updateJobStatus(job.getId(), 0);
                    logger.info("定时任务 {} 已暂停", job.getJobName());
                    break;
                case DELETE:
                    scheduler.deleteJob(jobKey);
                    logger.info("定时任务 {} 已删除", job.getJobName());
                    break;
                default:
                    throw new BusinessException("不支持的操作: " + jobOperate);
            }
        } catch (SchedulerException e) {
            logger.error("操作定时任务失败: {}", job.getJobName(), e);
            throw new BusinessException("操作定时任务失败: " + e.getMessage());
        }
    }

    public enum JobOperation {
        RESUME("RESUME"),
        PAUSE("PAUSE"),
        DELETE("DELETE");

        private final String code;

        JobOperation(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        public static JobOperation fromCode(String code) {
            for (JobOperation operation : values()) {
                if (operation.code.equalsIgnoreCase(code)) {
                    return operation;
                }
            }
            throw new IllegalArgumentException("未知的操作类型: " + code);
        }
    }
}
