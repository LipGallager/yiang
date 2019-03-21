package com.yiang.quartz02.service.impl;

import com.yiang.quartz02.entity.TriggerParam;
import com.yiang.quartz02.mapper.TriggerParamMapper;
import com.yiang.quartz02.service.TriggerParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author 何卓（Lip·Gallager）
 * @Company yiang公司
 * @ClassName TriggerParamServiceImpl
 * @Description //TODO
 * @CreateDate 2019-03-21 22:14
 * @Version 1.0
 */
@Service
public class TriggerParamServiceImpl implements TriggerParamService {

    @Autowired
    private TriggerParamMapper triggerParamMapper;

    @Override
    public List<TriggerParam> queryScheduleParamLst(Integer triggerId) {
        return triggerParamMapper.queryScheduleParamLst(triggerId);
    }
}
