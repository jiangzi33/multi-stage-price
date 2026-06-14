package com.example.multi_stage_price.mapper;

import com.example.multi_stage_price.entity.PrizeRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface PrizeRecordMapper {
    void insert(PrizeRecord prizeRecord);
    List<PrizeRecord> queryByUserIdAndTime(int userId, Date startTime, Date endTime);
}
