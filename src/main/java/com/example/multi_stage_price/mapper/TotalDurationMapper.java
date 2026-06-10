package com.example.multi_stage_price.mapper;

import com.example.multi_stage_price.entity.TotalDuration;

import java.util.Date;

public interface TotalDurationMapper {
    void insert(TotalDuration totalDuration);
    void delete(int id);
    TotalDuration queryByUserIdAndDate(int userId, Date date);
    void modify(TotalDuration totalDuration);
}
