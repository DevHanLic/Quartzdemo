package amp.demo.service.impl;

import amp.demo.entity.ScheduleJob;
import amp.demo.mapper.ScheduleJobMapper;
import amp.demo.service.QuartzService;
import amp.demo.service.ScheduleJobService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class ScheduleJobServiceImpl  implements ScheduleJobService {
    private static final String resume="RESUME";
    private static final String pause="PAUSE";
    private static final String delete="DELETE";
    @Autowired
    private QuartzService quartzService;
    @Autowired
    private ScheduleJobMapper scheduleJobMapper;
    @Override
    public void add(ScheduleJob job) {
        //此处省去数据验证
        scheduleJobMapper.save(job);
    }

    @Override
    public void start(Integer id) {
        //此处省去数据验证
        ScheduleJob job = scheduleJobMapper.getById(id);
        job.setJobStatus(1);
        scheduleJobMapper.updateById(job);
        //启动job
        try {
            quartzService.addJob(job);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(ScheduleJob job) {
        scheduleJobMapper.updateById(job);
        //修改job
        try{
            quartzService.update(job);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void resume(Integer id) {
        //此处省去数据验证
        ScheduleJob job = scheduleJobMapper.getById(id);
        job.setJobStatus(1);
        scheduleJobMapper.updateById(job);
        //恢复job
        try {
            quartzService.operateJob(resume, job);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void pause(Integer id) {
        //此处省去数据验证
        ScheduleJob job = scheduleJobMapper.getById(id);
        job.setJobStatus(1);
        scheduleJobMapper.updateById(job);
        //停止job
        try {
            quartzService.operateJob(pause, job);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        //此处省去数据验证
        ScheduleJob job = scheduleJobMapper.getById(id);
        scheduleJobMapper.removeById(id);
        //删除job
        try {
            quartzService.operateJob(delete, job);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }



    @Override
    public PageInfo<ScheduleJob> getJobAndTriggerDetails(int pageNum, int pageSize) {
        //使用分页助手进行分页
        PageHelper.startPage(pageNum, pageSize);
        List<ScheduleJob> list = scheduleJobMapper.selectAll();
        PageInfo<ScheduleJob> page = new PageInfo<ScheduleJob>(list);
        return page;
    }
}
