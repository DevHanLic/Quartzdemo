package amp.demo.service;

import amp.demo.entity.ScheduleJob;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ScheduleJobService {
    /**
     * 新增定时任务
     * @param job 任务
     */
    void add(ScheduleJob job);

    /**
     * 启动定时任务
     * @param id 任务id
     */
    void start(Integer id);

    /**
     * 恢复定时任务
     * @param id
     */
    void resume(Integer id);
    /**
     * 暂停定时任务
     * @param id 任务id
     */
    void pause(Integer id);

    /**
     * 删除定时任务
     * @param id 任务id
     */
    void delete(Integer id);

    /**
     * 修改定时任务
     */
    void update(ScheduleJob job);



    //展示定时任务列表
    public PageInfo<ScheduleJob> getJobAndTriggerDetails(int pageNum, int pageSize);
}
