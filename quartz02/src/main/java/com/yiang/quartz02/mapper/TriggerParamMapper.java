package com.yiang.quartz02.mapper;

import com.yiang.quartz02.entity.TriggerParam;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TriggerParamMapper {

    int deleteByPrimaryKey(Integer param_id);

    int insert(TriggerParam record);

    int insertSelective(TriggerParam record);

    TriggerParam selectByPrimaryKey(Integer param_id);

    int updateByPrimaryKeySelective(TriggerParam record);

    int updateByPrimaryKey(TriggerParam record);

    /**
     * 查询出当前任务类对应所需的参数
     * @param triggerId
     * @return
     */
    List<TriggerParam> queryScheduleParamLst(Integer triggerId);
}