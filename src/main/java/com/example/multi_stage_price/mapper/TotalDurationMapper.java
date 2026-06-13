package com.example.multi_stage_price.mapper;

import com.example.multi_stage_price.entity.TotalDuration;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;

@Mapper
public interface TotalDurationMapper {
    void insert(TotalDuration totalDuration);
    void delete(int id);
    TotalDuration queryByUserIdAndDate(int userId, String date);
    void modify(TotalDuration totalDuration);
}
