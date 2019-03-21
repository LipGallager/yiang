package com.yiang.quartz02.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class MyJob2 implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDetail jobDetail =
                jobExecutionContext.getJobDetail();
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        System.out.println(new Date().toLocaleString()+"-->MyJob2参数传递name="+jobDataMap.get("name")+",score="+
                jobDataMap.get("score"));
    }
}