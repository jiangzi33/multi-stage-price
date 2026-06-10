package com.example.multi_stage_price.mapper;

import com.example.multi_stage_price.entity.PlayRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface PlayRecordMapper {
    void insert(PlayRecord playRecord);
    List<PlayRecord> queryByUserIdAndTime(int userId, Date startTime, Date endTime);
}
