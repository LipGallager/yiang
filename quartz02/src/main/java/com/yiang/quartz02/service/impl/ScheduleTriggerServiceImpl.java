package com.yiang.quartz02.service.impl;

import com.yiang.quartz02.entity.TriggerParam;
import com.yiang.quartz02.entity.Triggers;
import com.yiang.quartz02.mapper.TriggerParamMapper;
import com.yiang.quartz02.mapper.TriggersMapper;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleTriggerServiceImpl {

    @Autowired
    private TriggersMapper TriggersMapper;

    @Autowired
    private TriggerParamMapper triggerParamMapper;

    @Autowired
    private Scheduler scheduler;

    @Scheduled(cron = "0/10 * * * * ?")
    public void refreshScheduler() {
        try {
            List<Triggers> scheduleTriggers =
                    TriggersMapper.queryScheduleTriggerLst();
            if (null != scheduleTriggers) {
                for (Triggers scheduleTrigger : scheduleTriggers) {
                    String cron = scheduleTrigger.getCron();  //表达式
                    String jobName = scheduleTrigger.getJob_name(); //任务名称
                    String jobGroup = scheduleTrigger.getJob_group(); //任务分组
                    String status = scheduleTrigger.getStatus();  //任务状态

                    //JobName+JobGroup=Primary Key
                    //根据jobName和jobGroup生成TriggerKey
                    TriggerKey triggerKey =
                            TriggerKey.triggerKey(jobName, jobGroup);
                    //根据TriggerKey到Scheduler调度器中获取触发器
                    CronTrigger cronTrigger = (CronTrigger)
                            scheduler.getTrigger(triggerKey);

                    if (null == cronTrigger) {
                        if (status.equals("0"))
                            continue;
                        System.out.println("创建调度器");
                        //创建任务详情
                        JobDetail jobDetail =
                                JobBuilder.newJob((Class<? extends Job>) Class.forName(jobName))
                                        .withIdentity(jobName, jobGroup)
                                        .build();

                        //往Job任务中传递参数
                        JobDataMap jobDataMap = jobDetail.getJobDataMap();
                        List<TriggerParam> params =
                                triggerParamMapper.queryScheduleParamLst(scheduleTrigger.getId());
                        for (TriggerParam param : params) {
                            jobDataMap.put(param.getName(), param.getValue());
                        }

                        //创建表达式调度器
                        CronScheduleBuilder cronSchedule =
                                CronScheduleBuilder.cronSchedule(cron);

                        //创建Trigger
                        cronTrigger = TriggerBuilder.newTrigger()
                                .withIdentity(jobName, jobGroup)
                                .withSchedule(cronSchedule)
                                .build();

                        //将jobDetail和Trigger注入到scheduler调度器中
                        scheduler.scheduleJob(jobDetail, cronTrigger);
                    } else {
//                        System.out.println("Quartz 调度任务中已存在该任务");
                        if (status.equals("0")) {
                            JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
                            scheduler.deleteJob(jobKey);
                            continue;
                        }
                        //调度器中的表达式
                        String cronExpression =
                                cronTrigger.getCronExpression();

                        if (!cron.equals(cronExpression)) {
                            //创建表达式调度器
                            CronScheduleBuilder cronSchedule =
                                    CronScheduleBuilder.cronSchedule(cron);

                            //重构
                            cronTrigger = cronTrigger.getTriggerBuilder()
                                    .withIdentity(triggerKey)
                                    .withSchedule(cronSchedule)
                                    .build();

                            //刷新调度器
                            scheduler.rescheduleJob(triggerKey, cronTrigger);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}