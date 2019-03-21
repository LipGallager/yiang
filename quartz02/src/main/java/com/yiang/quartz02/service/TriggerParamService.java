package com.yiang.quartz02.service;

import com.yiang.quartz02.entity.TriggerParam;

import java.util.List;

public interface TriggerParamService {

    /**
     * 查询出当前任务类对应所需的参数
     * @param triggerId
     * @return
     */
    List<TriggerParam> queryScheduleParamLst(Integer triggerId);
}