package com.example.multi_stage_price.mapper;

import com.example.multi_stage_price.entity.PlayRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface PlayRecordMapper {
    void insert(PlayRecord playRecord);
    List<PlayRecord> queryByUserIdAndTime(int userId, Date startTime, Date endTime);
    List<PlayRecord> queryByUserIdAndTimeWithPage(@Param("userId") int userId, @Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("pageStart") int pageStart, @Param("pageSize") int pageSize);
}
