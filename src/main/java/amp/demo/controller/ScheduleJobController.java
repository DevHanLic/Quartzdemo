package amp.demo.controller;

import amp.demo.dto.ResultData;
import amp.demo.entity.ScheduleJob;
import amp.demo.service.QuartzService;
import amp.demo.service.ScheduleJobService;
import com.github.pagehelper.PageInfo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/quartz")
@Validated
public class ScheduleJobController {
    private static final Logger logger = LoggerFactory.getLogger(ScheduleJobController.class);

    @Autowired
    private ScheduleJobService jobService;

    @GetMapping("/list")
    public ResultData<Map<String, Object>> queryJob(
            @RequestParam(defaultValue = "1") @Min(1) Integer pageNum,
            @RequestParam(defaultValue = "10") @Min(1) Integer pageSize) {
        
        logger.info("查询定时任务列表: pageNum={}, pageSize={}", pageNum, pageSize);
        
        PageInfo<ScheduleJob> jobAndTrigger = jobService.getJobAndTriggerDetails(pageNum, pageSize);
        
        Map<String, Object> result = new HashMap<>();
        result.put("jobAndTrigger", jobAndTrigger.getList());
        result.put("total", jobAndTrigger.getTotal());
        result.put("pageNum", jobAndTrigger.getPageNum());
        result.put("pageSize", jobAndTrigger.getPageSize());
        result.put("pages", jobAndTrigger.getPages());
        
        return ResultData.success(result);
    }

    @PostMapping("/add")
    public ResultData<String> add(@Valid @RequestBody ScheduleJob job) {
        logger.info("添加定时任务: {}", job.getJobName());
        jobService.add(job);
        return ResultData.success("添加定时任务成功");
    }

    @PostMapping("/start")
    public ResultData<String> start(@RequestBody Map<String, Object> request) {
        Integer id = extractId(request);
        logger.info("启动定时任务: ID={}", id);
        jobService.start(id);
        return ResultData.success("启动定时任务成功");
    }

    @PostMapping("/update")
    public ResultData<String> update(@Valid @RequestBody ScheduleJob job) {
        logger.info("修改定时任务: {}", job.getJobName());
        jobService.update(job);
        return ResultData.success("修改定时任务配置成功");
    }

    @PostMapping("/resume")
    public ResultData<String> resume(@RequestBody Map<String, Object> request) {
        Integer id = extractId(request);
        logger.info("恢复定时任务: ID={}", id);
        jobService.resume(id);
        return ResultData.success("恢复定时任务成功");
    }

    @PostMapping("/pause")
    public ResultData<String> pause(@RequestBody Map<String, Object> request) {
        Integer id = extractId(request);
        logger.info("暂停定时任务: ID={}", id);
        jobService.pause(id);
        return ResultData.success("暂停定时任务成功");
    }

    @PostMapping("/delete")
    public ResultData<String> delete(@RequestBody Map<String, Object> request) {
        Integer id = extractId(request);
        logger.info("删除定时任务: ID={}", id);
        jobService.delete(id);
        return ResultData.success("删除定时任务成功");
    }

    private Integer extractId(Map<String, Object> request) {
        Object id = request.get("id");
        if (id == null) {
            throw new IllegalArgumentException("任务ID不能为空");
        }
        if (id instanceof Integer) {
            return (Integer) id;
        } else if (id instanceof String) {
            try {
                return Integer.valueOf((String) id);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("任务ID格式错误: " + id);
            }
        } else {
            throw new IllegalArgumentException("任务ID格式错误");
        }
    }
}
