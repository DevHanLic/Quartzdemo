package amp.demo.mapper;

import amp.demo.entity.ScheduleJob;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ScheduleJobMapper{
    /**
     * 添加一个定时任务
     * @param job
     */
    void save(ScheduleJob job);

    /**
     * 修改定时任务
     * @param job
     */
    void updateById(ScheduleJob job);

    /**
     * 删除定时任务
     * @param id
     */
    void removeById(Integer id);

    /**
     * 根据id查询定时任务
     * @param id
     * @return
     */
    ScheduleJob getById(Integer id);

    /**
     * 程序启动时查找所有需要执行的定时任务
     * @return
     */
    List<ScheduleJob> selectStart();

    /**
     * 查询所有定时任务
     * @return
     */
    List<ScheduleJob> selectAll();
}
