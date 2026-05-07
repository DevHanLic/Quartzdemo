package amp.demo.service.impl;

import amp.demo.entity.ScheduleJob;
import amp.demo.exception.BusinessException;
import amp.demo.mapper.ScheduleJobMapper;
import amp.demo.service.QuartzService;
import amp.demo.service.ScheduleJobService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ScheduleJobServiceImpl implements ScheduleJobService {
    private static final Logger logger = LoggerFactory.getLogger(ScheduleJobServiceImpl.class);

    @Autowired
    private QuartzService quartzService;

    @Autowired
    private ScheduleJobMapper scheduleJobMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(ScheduleJob job) {
        validateJob(job);
        scheduleJobMapper.save(job);
        logger.info("定时任务 {} 已添加到数据库", job.getJobName());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void start(Integer id) {
        if (id == null) {
            throw new BusinessException("任务ID不能为空");
        }

        ScheduleJob job = getJobById(id);
        job.setJobStatus(1);
        scheduleJobMapper.updateById(job);

        quartzService.addJob(job);
        logger.info("定时任务 {} 已启动", job.getJobName());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ScheduleJob job) {
        validateJob(job);
        scheduleJobMapper.updateById(job);
        quartzService.update(job);
        logger.info("定时任务 {} 已更新", job.getJobName());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resume(Integer id) {
        if (id == null) {
            throw new BusinessException("任务ID不能为空");
        }

        ScheduleJob job = getJobById(id);
        scheduleJobMapper.updateById(job);

        try {
            quartzService.operateJob("RESUME", job);
        } catch (SchedulerException e) {
            logger.error("恢复定时任务失败: {}", job.getJobName(), e);
            throw new BusinessException("恢复定时任务失败: " + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void pause(Integer id) {
        if (id == null) {
            throw new BusinessException("任务ID不能为空");
        }

        ScheduleJob job = getJobById(id);
        scheduleJobMapper.updateById(job);

        try {
            quartzService.operateJob("PAUSE", job);
        } catch (SchedulerException e) {
            logger.error("暂停定时任务失败: {}", job.getJobName(), e);
            throw new BusinessException("暂停定时任务失败: " + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        if (id == null) {
            throw new BusinessException("任务ID不能为空");
        }

        ScheduleJob job = getJobById(id);
        scheduleJobMapper.removeById(id);

        try {
            quartzService.operateJob("DELETE", job);
        } catch (SchedulerException e) {
            logger.error("删除定时任务失败: {}", job.getJobName(), e);
            throw new BusinessException("删除定时任务失败: " + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateJobStatus(Integer id, Integer status) {
        if (id == null) {
            throw new BusinessException("任务ID不能为空");
        }

        ScheduleJob job = getJobById(id);
        job.setJobStatus(status != null ? status : 0);
        scheduleJobMapper.updateById(job);
        logger.debug("定时任务 {} 状态已更新为: {}", job.getJobName(), status);
    }

    @Override
    public PageInfo<ScheduleJob> getJobAndTriggerDetails(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ScheduleJob> list = scheduleJobMapper.selectAll();
        return new PageInfo<>(list);
    }

    private ScheduleJob getJobById(Integer id) {
        ScheduleJob job = scheduleJobMapper.getById(id);
        if (job == null) {
            throw new BusinessException("任务不存在: ID=" + id);
        }
        return job;
    }

    private void validateJob(ScheduleJob job) {
        if (job == null) {
            throw new BusinessException("任务信息不能为空");
        }
        if (job.getJobName() == null || job.getJobName().trim().isEmpty()) {
            throw new BusinessException("任务名称不能为空");
        }
        if (job.getCronExpression() == null || job.getCronExpression().trim().isEmpty()) {
            throw new BusinessException("Cron表达式不能为空");
        }
        if (job.getMethodName() == null || job.getMethodName().trim().isEmpty()) {
            throw new BusinessException("任务方法名不能为空");
        }
    }
}
