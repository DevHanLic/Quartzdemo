package amp.demo.controller;

import amp.demo.entity.ScheduleJob;
import amp.demo.service.QuartzService;
import amp.demo.service.ScheduleJobService;
import com.github.pagehelper.PageInfo;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/**
 * @author licheng
 * 定时任务管理
 */
@RestController
@RequestMapping("/quartz")
public class ScheduleJobController {

    @Autowired
    private ScheduleJobService jobService;

    @GetMapping(value = "/list")
    public Map<String, Object> queryJob(@RequestParam(value = "pageNum") Integer pageNum, @RequestParam(value = "pageSize") Integer pageSize) {
        PageInfo<ScheduleJob> jobAndTrigger = jobService.getJobAndTriggerDetails(pageNum, pageSize);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("JobAndTrigger", jobAndTrigger);
        map.put("number", jobAndTrigger.getTotal());
        return map;
    }
    @RequestMapping("/add")
    public String add(@RequestBody ScheduleJob job) {
        jobService.add(job);
        return "添加定时任务成功";
    }

    @RequestMapping("/start")
    public String start(@RequestBody Map<String, Object> map) {
        Integer id= (Integer) map.get("id");
        jobService.start(id);
        return "启动定时任务成功";
    }

    @RequestMapping("/update")
    public String update(@RequestBody ScheduleJob job) {
        jobService.update(job);
        return "修改定时任务配置成功";
    }

    @RequestMapping("/resume")
    public String resume(@RequestBody Map<String,Object> map) {
        Integer id= (Integer) map.get("id");
        jobService.resume(id);
        return "恢复定时任务成功";
    }

    @RequestMapping("/pause")
    public String pause(@RequestBody Map<String,Object> map) {
        Integer id= (Integer) map.get("id");
        jobService.pause(id);
        return "暂停定时任务成功";
    }

    @RequestMapping("/delete")
    public String delete(@RequestBody  Map<String,Object> map) {
        Integer id= (Integer) map.get("id");
        jobService.delete(id);
        return "删除定时任务成功";
    }

}

