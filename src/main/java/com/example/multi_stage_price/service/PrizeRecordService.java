package com.example.multi_stage_price.service;

import com.example.multi_stage_price.controller.cmd.PlayRecordCmd;
import com.example.multi_stage_price.controller.cmd.PrizeRecordCmd;
import com.example.multi_stage_price.entity.PrizeRecord;

import java.util.Date;
import java.util.List;

public interface PrizeRecordService {
    void insert(PrizeRecordCmd cmd);
    void insert(PrizeRecord prizeRecord);
    List<PrizeRecord> queryByUserIdAndTime(int userId, Date startTime, Date endTime);
}
