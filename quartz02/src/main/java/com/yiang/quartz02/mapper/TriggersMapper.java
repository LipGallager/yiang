package com.yiang.quartz02.mapper;

import com.yiang.quartz02.entity.Triggers;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TriggersMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Triggers record);

    int insertSelective(Triggers record);

    Triggers selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Triggers record);

    int updateByPrimaryKey(Triggers record);

    /**
     * 查询触发器中包含的所有任务
     *
     * @return
     */
    List<Triggers> queryScheduleTriggerLst();
}